package zedly.zbot.network;

import zedly.zbot.entity.CraftEntityMeta;
import zedly.zbot.inventory.ItemStack;
import net.minecraft.server.NBTBase;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import zedly.zbot.Advancement;
import zedly.zbot.AdvancementDisplay;
import zedly.zbot.AdvancementProgress;
import zedly.zbot.Location;
import zedly.zbot.entity.EntityMeta;
import zedly.zbot.inventory.CraftItemStack;
import zedly.zbot.environment.CraftChunk;

/**
 *
 * @author Tatious
 */
public class ExtendedDataInputStream extends DataInputStream {

    public ExtendedDataInputStream(InputStream in) {
        super(in);
    }

    public String readString() throws IOException {
        int len = readVarInt();
        byte[] bts = new byte[len];
        for (int x = 0; x < len; x++) {
            bts[x] = readByte();
        }
        return new String(bts, "UTF-8");
    }

    public long readVarLong() throws IOException {
        int idx = 0;
        long longValue = 0L;
        byte b = 0;
        do {
            b = readByte();
            long lowByte = b & 0x7F;
            lowByte <<= idx * 7;
            longValue |= lowByte;
            idx++;
        } while (b < 0 && idx < 8);
        return longValue;
    }
    
    public int readVarInt() throws IOException {
        return (int) readVarLong();
    }

    public Location readPosition() throws IOException {
        long raw = readLong();
        return new Location(raw);
    }

    public UUID readUUID() throws IOException {
        return new UUID(readLong(), readLong());
    }

    public CraftItemStack readSlot() throws IOException {
        CraftItemStack stack = new CraftItemStack();
        stack.setItemId(readShort());
        if (stack.getTypeId() == -1) {
            return stack;
        }
        stack.setItemCount(readByte());
        stack.setData(readShort());
        stack.setNbt(readNBT());
        return stack;
    }

    public NBTBase readNBT() throws IOException {
        NBTBase nbt = NBTBase.readNamedTag(this);
        return nbt;
    }

    public CraftChunk readChunkSection(boolean readSkyLight) throws IOException {
        int bitsPerBlock = readUnsignedByte();

        int[] palette;
        if (bitsPerBlock == 0) {
            palette = null;
        } else {
            int length = readVarInt();
            palette = new int[length];
            for (int i = 0; i < length; i++) {
                palette[i] = readVarInt();
            }
        }
        int dataLength = readVarInt();
        long[] blockData = new long[dataLength];
        for (int i = 0; i < dataLength; i++) {
            blockData[i] = readLong();
        }
        byte[] blockLight = new byte[2048];
        byte[] skyLight;
        readFully(blockLight);
        if (readSkyLight) {
            skyLight = new byte[2048];
            readFully(skyLight);
        } else {
            skyLight = null;
        }
        if (bitsPerBlock == 0) {
            return new CraftChunk(blockData, blockLight, skyLight);
        } else {
            return new CraftChunk(blockData, bitsPerBlock, palette, blockLight, skyLight);
        }
    }

    public HashMap<Integer, EntityMeta> readEntityMetaData() throws IOException {
        HashMap<Integer, EntityMeta> metaMap = new HashMap<>();
        while (true) {
            int id = read();
            if (id == 0xff) {
                return metaMap;
            }
            int type = read();
            try {
                CraftEntityMeta emd = CraftEntityMeta.getByType(type);
                emd.read(this);
                metaMap.put(id, emd);
            } catch (Exception ex) {
                throw new IOException("Unrecognized EntityMeta type " + id);
            }
        }
    }
    
    public Advancement readAdvancement() throws IOException {
        boolean hasParent = readBoolean();
        String parent = null;
        if(hasParent) {
            parent = readString();
        }
        AdvancementDisplay displayData = null;
        boolean hasAdvancementDisplay = readBoolean();
        if(hasAdvancementDisplay) {
            displayData = readAdvancementDisplay();
        }
        int numberOfCriteria = readVarInt();
        HashMap<String, Integer> criteria = new HashMap<>();
        for(int i = 0; i < numberOfCriteria; i++) {
            criteria.put(readString(), 0);
        }
        int arrayLength = readVarInt();
        List<List<String>> requirements = new LinkedList<>();
        for(int i = 0; i < arrayLength; i++) {
            int arrayLength1 = readVarInt();
            List<String> requirement = new LinkedList<>();
            for(int j = 0; j < arrayLength1; j++) {
                requirement.add(readString());
            }
            requirements.add(requirement);
        }
        return new Advancement(parent, displayData, criteria, requirements);
    }
    
    
    public AdvancementProgress readAdvancementProgress() throws IOException {
        int size = readVarInt();
        HashMap<String, Long> progresses = new HashMap<>();
        for(int i = 0; i < size; i++) {
            String name = readString();
            if(readBoolean()) {
                progresses.put(name, readLong());
            }
        }
        return new AdvancementProgress(progresses);
    }
    private AdvancementDisplay readAdvancementDisplay() throws IOException {
        String title = readString();
        String description = readString();
        ItemStack icon = readSlot();
        int frameType = readVarInt();
        int flags = readInt();
        String backgroundTexture = flags == 1 ? readString() : null;
        double xCoord = readFloat();
        double yCoord = readFloat();
        return new AdvancementDisplay(title, description, icon, frameType, flags, backgroundTexture, xCoord, yCoord);
    }
    
    
}
