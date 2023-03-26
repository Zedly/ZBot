package zedly.zbot.network;

import java.io.ByteArrayInputStream;
import zedly.zbot.network.packet.serverbound.Packet01EncryptionResponse;
import zedly.zbot.network.packet.clientbound.Packet02LoginSuccess;
import zedly.zbot.network.packet.clientbound.Packet01EncryptionRequest;
import zedly.zbot.network.packet.serverbound.Packet00LoginStart;
import zedly.zbot.network.packet.clientbound.*;
import zedly.zbot.network.packet.serverbound.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import zedly.zbot.Util;

public final class PacketInputStream extends ExtendedDataInputStream {

    private static final HashMap<Integer, Class<? extends ServerBoundPacket>> STATUS_OUT;
    private static final HashMap<Integer, Class<? extends ClientBoundPacket>> STATUS_IN;

    private static final HashMap<Integer, Class<? extends ServerBoundPacket>> LOGIN_OUT;
    private static final HashMap<Integer, Class<? extends ClientBoundPacket>> LOGIN_IN;

    private static final HashMap<Integer, Class<? extends ServerBoundPacket>> PLAY_OUT;
    private static final HashMap<Integer, Class<? extends ClientBoundPacket>> PLAY_IN;

    private final Inflater inflater = new Inflater();
    private final int[] lastOps = new int[16];
    private final int[] lastBytes = new int[2048];

    private boolean compressionEnabled = false;
    private int lastOpPtr = 0;
    private int lastBytePtr = 0;
    private StreamState state;
    private HashMap<Integer, Class<? extends ClientBoundPacket>> mapIn;
    //private HashMap<Integer, Class<? extends ServerBoundPacket>> mapOut;

    @Override
    public int read() throws IOException {
        int nextByte = super.read();
        lastBytes[lastBytePtr] = nextByte;
        lastBytePtr = (lastBytePtr + 1) % 2048;
        return nextByte;
    }

    private ClientBoundPacket newPacketForId(int op) throws InstantiationException, IllegalAccessException {
        Class<? extends ClientBoundPacket> clazz = mapIn.get(op);
        if (clazz == null) {
            throw new IllegalArgumentException("Unknown protocol id " + op);
        }
        return clazz.newInstance();
    }

    public void setState(StreamState state) {
        this.state = state;
        switch (state) {
            case STATUS:
                mapIn = STATUS_IN;
                //mapOut = STATUS_OUT;
                break;
            case LOGIN:
                mapIn = LOGIN_IN;
                //mapOut = LOGIN_OUT;
                break;
            case PLAY:
                mapIn = PLAY_IN;
                //mapOut = PLAY_OUT;
                break;
        }
    }

    public ClientBoundPacket getInstanceClientBound(int op, StreamState state) throws InstantiationException, IllegalAccessException {
        Class<? extends ClientBoundPacket> clazz = mapIn.get(op);
        if (clazz == null) {
            throw new IllegalArgumentException("Unknown protocol id " + op);
        }
        return clazz.newInstance();
    }

    public PacketInputStream(InputStream is) {
        super(is);
    }

    public PacketInputStream(InputStream is, StreamState state) {
        super(is);
        this.state = state;
        setState(state);
    }

    public ClientBoundPacket readPacket() throws IOException, InstantiationException, DataFormatException, IllegalAccessException {
        int op;
        ClientBoundPacket p;
        int packetLength = readVarInt();
        //System.out.println("Play Debug: Len " + len);
        if (compressionEnabled) {
            int dataLength = readVarInt();
            //System.out.println("Play Debug: CLen " + clen);
            if (dataLength != 0) {
                //System.out.println("Compressed Debug: Len " + len + " clen " + clen);
                byte[] compressedData = new byte[packetLength - Util.getVarIntLength(dataLength)];
                byte[] data = new byte[dataLength];
                readFully(compressedData);
                inflater.setInput(compressedData);
                inflater.inflate(data);
                long actualLength = inflater.getBytesWritten();
                if (actualLength != dataLength) {
                    System.err.println("Compressed packet claiming length " + dataLength + ", actual len " + actualLength);
                }

                inflater.reset();
                ByteArrayInputStream bbis = new ByteArrayInputStream(data);
                ExtendedDataInputStream bis = new ExtendedDataInputStream(bbis);

                op = bis.readVarInt();
                lastOps[(lastOpPtr++) % 16] = op;
                //System.out.println("Play Debug: Op " + Integer.toHexString(op));
                p = newPacketForId(op);
                p.readPacket(bis, (int) actualLength - 2);
                if (bbis.available() != 0) {
                    System.err.println("Dropped " + bbis.available() + " bytes in compressed packet " + Integer.toHexString(op) + " of size " + dataLength + "!");
                }
            } else {
                op = readVarInt();
                lastOps[(lastOpPtr++) % 16] = op;
                //System.out.println("Play Debug: Op " + Integer.toHexString(op));
                p = newPacketForId(op);
                p.readPacket(this, packetLength - 2);
                op = op;
            }
        } else {
            op = readVarInt();
            lastOps[(lastOpPtr++) % 16] = op;
            //System.out.println("Play Debug: Op " + Integer.toHexString(op));
            p = newPacketForId(op);
            p.readPacket(this, packetLength - 2);
            op = op;
        }

        checkStateChange(p);

        return p;
    }

    private void checkStateChange(ClientBoundPacket p) {
        if (p instanceof Packet03SetCompression) {
            compressionEnabled = true;
            System.out.println("Set Compression Threshold " + ((Packet03SetCompression) p).getThreshold());
        } else if (p instanceof Packet02LoginSuccess) {
            System.out.println("DIS joined");
            setState(StreamState.PLAY);
        }
    }

    public int getLastOp() {
        return lastOps[lastOpPtr % 16];
    }

    public int[] getLastOps() {
        return lastOps;
    }

    public int getNumPacketsReceived() {
        return lastOpPtr;
    }

    public void printCrashInfo() {
        System.out.println("Crash Info:");
        System.out.println("Died after " + lastOpPtr + " packets");
        System.out.print("\nLast received Packets: ");
        for (int i = lastOpPtr; i < lastOpPtr + 16; i++) {
            System.out.print(Integer.toHexString(lastOps[i % 16]).toUpperCase() + " ");
        }
        System.out.print("\r\n");
    }

    static {
        STATUS_OUT = new HashMap<>();
        STATUS_IN = new HashMap<>();
        LOGIN_OUT = new HashMap<>();
        LOGIN_IN = new HashMap<>();
        PLAY_OUT = new HashMap<>();
        PLAY_IN = new HashMap<>();

        // Status, Clientbound
        STATUS_IN.put((0x00), Packet00StatusResponse.class);
        STATUS_IN.put((0x01), Packet01PingResponse.class);

        // Status, Serverbound
        STATUS_OUT.put((0x00), Packet00StatusRequest.class);
        STATUS_OUT.put((0x01), Packet01PingRequest.class);

        //Login, Client
        LOGIN_IN.put((0x00), Packet00Disconnect.class);
        LOGIN_IN.put((0x01), Packet01EncryptionRequest.class);
        LOGIN_IN.put((0x02), Packet02LoginSuccess.class);
        LOGIN_IN.put((0x03), Packet03SetCompression.class);
        LOGIN_IN.put((0x04), Packet04LoginPluginRequest.class);

        //Login, Server
        LOGIN_OUT.put((0x00), Packet00LoginStart.class);
        LOGIN_OUT.put((0x01), Packet01EncryptionResponse.class);
        LOGIN_OUT.put((0x01), Packet02LoginPluginResponse.class);

        PLAY_IN.put(0x01, Packet01SpawnEntity.class);
        PLAY_IN.put(0x02, Packet02SpawnExperienceOrb.class);
        PLAY_IN.put(0x03, Packet03SpawnPlayer.class);
        PLAY_IN.put(0x04, Packet04EntityAnimation.class);
        PLAY_IN.put(0x06, Packet06AcknowledgeBlockChange.class);
        PLAY_IN.put(0x07, Packet07SetBlockDestroyStage.class);
        PLAY_IN.put(0x08, Packet08BlockEntityData.class);
        PLAY_IN.put(0x09, Packet09BlockAction.class);
        PLAY_IN.put(0x0A, Packet0ABlockUpdate.class);
        PLAY_IN.put(0x0C, Packet0CChangeDifficulty.class);
        PLAY_IN.put(0x0E, Packet0EClearTitles.class);
        PLAY_IN.put(0x11, Packet11CloseContainer.class);
        PLAY_IN.put(0x12, Packet12SetContainerContent.class);
        PLAY_IN.put(0x13, Packet13SetContainerProperty.class);
        PLAY_IN.put(0x14, Packet14SetContainerSlot.class);
        PLAY_IN.put(0x15, Packet15SetCooldown.class);
        PLAY_IN.put(0x16, Packet16ChatSuggestions.class);
        PLAY_IN.put(0x1A, Packet1ADisconnect.class);
        PLAY_IN.put(0x1B, Packet1BDisguisedChatMessage.class);
        PLAY_IN.put(0x1C, Packet1CEntityEvent.class);
        PLAY_IN.put(0x1E, Packet1EUnloadChunk.class);
        PLAY_IN.put(0x1F, Packet1FGameEvent.class);
        PLAY_IN.put(0x20, Packet20OpenHorseScreen.class);
        PLAY_IN.put(0x22, Packet22InitializeWorldBorder.class);
        PLAY_IN.put(0x23, Packet23KeepAlive.class);
        PLAY_IN.put(0x25, Packet25WorldEvent.class);
        PLAY_IN.put(0x2B, Packet2BUpdateEntityPosition.class);
        PLAY_IN.put(0x2C, Packet2CUpdateEntityPositionandRotation.class);
        PLAY_IN.put(0x2D, Packet2DUpdateEntityRotation.class);
        PLAY_IN.put(0x2E, Packet2EMoveVehicle.class);
        PLAY_IN.put(0x2F, Packet2FOpenBook.class);
        PLAY_IN.put(0x30, Packet30OpenScreen.class);
        PLAY_IN.put(0x31, Packet31OpenSignEditor.class);
        PLAY_IN.put(0x32, Packet32Ping.class);
        PLAY_IN.put(0x33, Packet33PlaceGhostRecipe.class);
        PLAY_IN.put(0x34, Packet34PlayerAbilities.class);
        PLAY_IN.put(0x36, Packet36EndCombat.class);
        PLAY_IN.put(0x38, Packet38CombatDeath.class);
        PLAY_IN.put(0x3C, Packet3CSynchronizePlayerPosition.class);
        PLAY_IN.put(0x3F, Packet3FRemoveEntityEffect.class);
        PLAY_IN.put(0x40, Packet40ResourcePack.class);
        PLAY_IN.put(0x42, Packet42SetHeadRotation.class);
        PLAY_IN.put(0x44, Packet44SelectAdvancementsTab.class);
        PLAY_IN.put(0x45, Packet45ServerData.class);
        PLAY_IN.put(0x46, Packet46SetActionBarText.class);
        PLAY_IN.put(0x47, Packet47SetBorderCenter.class);
        PLAY_IN.put(0x48, Packet48SetBorderLerpSize.class);
        PLAY_IN.put(0x49, Packet49SetBorderSize.class);
        PLAY_IN.put(0x4A, Packet4ASetBorderWarningDelay.class);
        PLAY_IN.put(0x4B, Packet4BSetBorderWarningDistance.class);
        PLAY_IN.put(0x4C, Packet4CSetCamera.class);
        PLAY_IN.put(0x4D, Packet4DSetHeldItem.class);
        PLAY_IN.put(0x4F, Packet4FSetRenderDistance.class);
        PLAY_IN.put(0x50, Packet50SetDefaultSpawnPosition.class);
        PLAY_IN.put(0x51, Packet51DisplayObjective.class);
        PLAY_IN.put(0x52, Packet52SetEntityMetadata.class);
        PLAY_IN.put(0x53, Packet53LinkEntities.class);
        PLAY_IN.put(0x54, Packet54SetEntityVelocity.class);
        PLAY_IN.put(0x56, Packet56SetExperience.class);
        PLAY_IN.put(0x57, Packet57SetHealth.class);
        PLAY_IN.put(0x5C, Packet5CSetSimulationDistance.class);
        PLAY_IN.put(0x5D, Packet5DSetSubtitleText.class);
        PLAY_IN.put(0x5E, Packet5EUpdateTime.class);
        PLAY_IN.put(0x5F, Packet5FSetTitleText.class);
        PLAY_IN.put(0x60, Packet60SetTitleAnimationTimes.class);
        PLAY_IN.put(0x64, Packet64SystemChatMessage.class);
        PLAY_IN.put(0x65, Packet65SetTabListHeaderAndFooter.class);
        PLAY_IN.put(0x66, Packet66TagQueryResponse.class);
        PLAY_IN.put(0x67, Packet67PickupItem.class);
        PLAY_IN.put(0x68, Packet68TeleportEntity.class);
        PLAY_IN.put(0x6C, Packet6CEntityEffect.class);

        PLAY_OUT.put(0x01, Packet01QueryBlockEntityTag.class);
        PLAY_OUT.put(0x02, Packet02ChangeDifficulty.class);
        PLAY_OUT.put(0x03, Packet03MessageAcknowledgment.class);
        PLAY_OUT.put(0x07, Packet07ClientCommand.class);
        PLAY_OUT.put(0x08, Packet08ClientInformation.class);
        PLAY_OUT.put(0x09, Packet09CommandSuggestionsRequest.class);
        PLAY_OUT.put(0x0A, Packet0AClickContainerButton.class);
        PLAY_OUT.put(0x0C, Packet0CCloseContainer.class);
        PLAY_OUT.put(0x0E, Packet0EEditBook.class);
        PLAY_OUT.put(0x0F, Packet0FQueryEntityTag.class);
        PLAY_OUT.put(0x11, Packet11JigsawGenerate.class);
        PLAY_OUT.put(0x12, Packet12KeepAlive.class);
        PLAY_OUT.put(0x13, Packet13LockDifficulty.class);
        PLAY_OUT.put(0x14, Packet14SetPlayerPosition.class);
        PLAY_OUT.put(0x15, Packet15SetPlayerPositionandRotation.class);
        PLAY_OUT.put(0x16, Packet16SetPlayerRotation.class);
        PLAY_OUT.put(0x17, Packet17SetPlayerOnGround.class);
        PLAY_OUT.put(0x18, Packet18MoveVehicle.class);
        PLAY_OUT.put(0x19, Packet19PaddleBoat.class);
        PLAY_OUT.put(0x1A, Packet1APickItem.class);
        PLAY_OUT.put(0x1B, Packet1BPlaceRecipe.class);
        PLAY_OUT.put(0x1C, Packet1CPlayerAbilities.class);
        PLAY_OUT.put(0x1D, Packet1DPlayerAction.class);
        PLAY_OUT.put(0x1E, Packet1EPlayerCommand.class);
        PLAY_OUT.put(0x1F, Packet1FPlayerInput.class);
        PLAY_OUT.put(0x20, Packet20Pong.class);
        PLAY_OUT.put(0x21, Packet21ChangeRecipeBookSettings.class);
        PLAY_OUT.put(0x22, Packet22SetSeenRecipe.class);
        PLAY_OUT.put(0x23, Packet23RenameItem.class);
        PLAY_OUT.put(0x24, Packet24ResourcePack.class);
        PLAY_OUT.put(0x26, Packet26SelectTrade.class);
        PLAY_OUT.put(0x28, Packet28SetHeldItem.class);
        PLAY_OUT.put(0x2A, Packet2AProgramCommandBlockMinecart.class);
        PLAY_OUT.put(0x2B, Packet2BSetCreativeModeSlot.class);
        PLAY_OUT.put(0x2C, Packet2CProgramJigsawBlock.class);
        PLAY_OUT.put(0x2E, Packet2EUpdateSign.class);
        PLAY_OUT.put(0x2F, Packet2FSwingArm.class);
        PLAY_OUT.put(0x30, Packet30TeleportToEntity.class);
        PLAY_OUT.put(0x31, Packet31UseItemOn.class);
        PLAY_OUT.put(0x32, Packet32UseItem.class);
    }
}
