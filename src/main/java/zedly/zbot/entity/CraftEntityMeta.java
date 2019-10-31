package zedly.zbot.entity;

import java.io.IOException;
import java.util.HashMap;
import zedly.zbot.Location;
import zedly.zbot.inventory.ItemStack;

import java.util.UUID;
import net.minecraft.server.NBTBase;
import zedly.zbot.Direction;
import zedly.zbot.Util;
import zedly.zbot.inventory.CraftItemStack;
import zedly.zbot.network.ExtendedDataInputStream;

public abstract class CraftEntityMeta implements EntityMeta {

    private static final HashMap<Integer, Class> dataMap = new HashMap<>();

    public static CraftEntityMeta getByType(int type) throws Exception {
        return (CraftEntityMeta) dataMap.get(type).newInstance();
    }

    public abstract void read(ExtendedDataInputStream edis) throws IOException;

    public final int asInt() {
        return ((IntMeta) this).intValue;
    }

    public final float asFloat() {
        return ((FloatMeta) this).floatValue;
    }

    public final String asString() {
        return ((StringMeta) this).stringValue;
    }

    public final CraftItemStack asSlot() {
        return ((ItemStackMeta) this).itemStackValue;
    }

    public final boolean asBoolean() {
        return ((BooleanMeta) this).booleanValue;
    }

    public final float[] asVector() {
        return ((Vector3Meta) this).vector;
    }

    public final Direction asDirection() {
        int d = ((IntMeta) this).intValue;
        switch (d) {
            case 0:
                return Direction.DOWN;
            case 1:
                return Direction.UP;
            case 2:
                return Direction.NORTH;
            case 3:
                return Direction.SOUTH;
            case 4:
                return Direction.WEST;
            case 5:
                return Direction.EAST;
        }
        return null;
    }

    public final NBTBase asNBT() {
        return ((NBTMeta) this).nbtValue;
    }
    
    public final Location asLocation() {
        return ((LocationMeta) this).location;
    }

    public String toString() {
        return "UndefinedMeta";
    }

    public final UUID asUUID() {
        return ((OptionalUUIDMeta) this).uuidValue;
    }

    public static class IntMeta extends CraftEntityMeta {

        protected int intValue;

        @Override
        public void read(ExtendedDataInputStream edis) throws IOException {
            intValue = edis.readVarInt();
        }

        public String toString() {
            return "IntMeta: " + intValue;
        }
    }

    public static class ByteMeta extends IntMeta {

        @Override
        public void read(ExtendedDataInputStream edis) throws IOException {
            intValue = edis.readByte();
        }

        public String toString() {
            return "ByteMeta: " + intValue;
        }
    }

    public static class FloatMeta extends CraftEntityMeta {

        private float floatValue;

        @Override
        public void read(ExtendedDataInputStream edis) throws IOException {
            floatValue = edis.readFloat();
        }

        public String toString() {
            return "FloatMeta: " + floatValue;
        }
    }

    public static class StringMeta extends CraftEntityMeta {

        public String stringValue;

        @Override
        public void read(ExtendedDataInputStream edis) throws IOException {
            stringValue = edis.readString();
        }

        public String toString() {
            return "StringMeta: " + stringValue;
        }
    }

    public static class ChatMeta extends StringMeta {

        @Override
        public void read(ExtendedDataInputStream edis) throws IOException {
            stringValue = Util.interpretJson(edis.readString());
        }

        public String toString() {
            return "ChatMeta: " + stringValue;
        }
    }

    public static class OptionalChatMeta extends StringMeta {

        @Override
        public void read(ExtendedDataInputStream edis) throws IOException {
            if (edis.readBoolean()) {
                stringValue = Util.interpretJson(edis.readString());
            } else {
                stringValue = null;
            }
        }

        public String toString() {
            return "ChatMeta: " + stringValue;
        }
    }

    public static class ItemStackMeta extends CraftEntityMeta {

        private CraftItemStack itemStackValue;

        @Override
        public void read(ExtendedDataInputStream edis) throws IOException {
            itemStackValue = edis.readSlot();
        }

        public String toString() {
            if (itemStackValue == null) {
                return "ItemStackMeta: Empty item";
            }
            return "ItemStackMeta: " + itemStackValue.toString();
        }
    }

    public static class BooleanMeta extends CraftEntityMeta {

        private boolean booleanValue;

        @Override
        public void read(ExtendedDataInputStream edis) throws IOException {
            booleanValue = edis.readBoolean();
        }

        public String toString() {
            return "BooleanMeta: " + booleanValue;
        }
    }

    public static class Vector3Meta extends CraftEntityMeta {

        private final float[] vector = new float[3];

        @Override
        public void read(ExtendedDataInputStream edis) throws IOException {
            vector[0] = edis.readFloat();
            vector[1] = edis.readFloat();
            vector[2] = edis.readFloat();
        }

        public String toString() {
            return "Vector3Meta: {" + vector[0] + ", " + vector[1] + ", " + vector[2] + "}";
        }
    }

    public static class Vector4Meta extends CraftEntityMeta {

        private final float[] vector = new float[4];

        @Override
        public void read(ExtendedDataInputStream edis) throws IOException {
            vector[0] = edis.readFloat();
            vector[1] = edis.readFloat();
            vector[2] = edis.readFloat();
            vector[3] = edis.readFloat();
        }

        public String toString() {
            return "Vector4Meta: {" + vector[0] + ", " + vector[1] + ", " + vector[2] + ", " + vector[3] + "}";
        }
    }

    public static class LocationMeta extends CraftEntityMeta {

        public Location location;

        @Override
        public void read(ExtendedDataInputStream edis) throws IOException {
            location = new Location(edis.readLong());
        }

        public String toString() {
            return "LocationMeta: " + location;
        }
    }

    public static class OptionalLocationMeta extends LocationMeta {

        private boolean booleanValue;

        @Override
        public void read(ExtendedDataInputStream edis) throws IOException {
            booleanValue = edis.readBoolean();
            if (booleanValue) {
                location = new Location(edis.readLong());
            }
        }

        public String toString() {
            return "OptionalLocationMeta: " + location;
        }
    }

    public static class OptionalUUIDMeta extends CraftEntityMeta {

        private boolean booleanValue;
        private UUID uuidValue;

        @Override
        public void read(ExtendedDataInputStream edis) throws IOException {
            booleanValue = edis.readBoolean();
            if (booleanValue) {
                uuidValue = edis.readUUID();
            }
        }

        public boolean hasValue() {
            return booleanValue;
        }

        public String toString() {
            return "OptionalUUIDMeta: " + uuidValue;
        }
    }

    public static class NBTMeta extends CraftEntityMeta {

        private NBTBase nbtValue;

        @Override
        public void read(ExtendedDataInputStream edis) throws IOException {
            nbtValue = edis.readNBT();
        }

        public String toString() {
            return "NBTMeta: " + nbtValue;
        }
    }

    public static class ParticleMeta extends CraftEntityMeta {

        private int particleId;
        private CraftEntityMeta optionalMeta = null;

        public ParticleMeta() {
        }

        public ParticleMeta(int particleId) {
            this.particleId = particleId;
        }

        @Override
        public void read(ExtendedDataInputStream edis) throws IOException {
            particleId = edis.readVarInt();
            readOptionalMeta(edis);
        }

        public void readOptionalMeta(ExtendedDataInputStream edis) throws IOException {
            switch (particleId) {
                case 3:
                    optionalMeta = new IntMeta();
                    optionalMeta.read(edis);
                    break;
                case 14:
                    optionalMeta = new Vector4Meta();
                    optionalMeta.read(edis);
                    break;
                case 23:
                    optionalMeta = new IntMeta();
                    optionalMeta.read(edis);
                    break;
                case 32:
                    optionalMeta = new ItemStackMeta();
                    optionalMeta.read(edis);
                    break;
                default:
                    break;
            }
        }

        public String toString() {
            return "ParticleMeta: {" + optionalMeta + "}";
        }
    }

    public static class VillagerMeta extends CraftEntityMeta {

        private int villagerType, profession, level;

        @Override
        public void read(ExtendedDataInputStream edis) throws IOException {
            villagerType = edis.readVarInt();
            profession = edis.readVarInt();
            level = edis.readVarInt();
        }

        public String toString() {
            return "VillagerMeta: {" + villagerType + ", " + profession + ", " + level + "}";
        }
    }

    static {
        dataMap.put(0, ByteMeta.class);
        dataMap.put(1, IntMeta.class);
        dataMap.put(2, FloatMeta.class);
        dataMap.put(3, StringMeta.class);
        dataMap.put(4, ChatMeta.class);
        dataMap.put(5, OptionalChatMeta.class);
        dataMap.put(6, ItemStackMeta.class);
        dataMap.put(7, BooleanMeta.class);
        dataMap.put(8, Vector3Meta.class);
        dataMap.put(9, LocationMeta.class);
        dataMap.put(10, OptionalLocationMeta.class);
        dataMap.put(11, IntMeta.class);
        dataMap.put(12, OptionalUUIDMeta.class);
        dataMap.put(13, IntMeta.class);
        dataMap.put(14, NBTMeta.class);
        dataMap.put(15, ParticleMeta.class);
        dataMap.put(16, VillagerMeta.class);
        dataMap.put(17, IntMeta.class);
        dataMap.put(18, IntMeta.class);
    }

}
