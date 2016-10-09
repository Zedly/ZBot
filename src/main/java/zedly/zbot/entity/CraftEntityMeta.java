package zedly.zbot.entity;

import java.io.IOException;
import java.util.HashMap;
import zedly.zbot.Location;
import zedly.zbot.inventory.ItemStack;

import java.util.UUID;
import zedly.zbot.Direction;
import zedly.zbot.StringUtil;
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
        return ((VectorMeta) this).vector;
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
            stringValue = StringUtil.interpretJson(edis.readString());
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

    public static class VectorMeta extends CraftEntityMeta {

        private final float[] vector = new float[3];

        @Override
        public void read(ExtendedDataInputStream edis) throws IOException {
            vector[0] = edis.readFloat();
            vector[1] = edis.readFloat();
            vector[2] = edis.readFloat();
        }

        public String toString() {
            return "VectorMeta: {" + vector[0] + ", " + vector[1] + ", " + vector[2] + "}";
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

    static {
        dataMap.put(0, ByteMeta.class);
        dataMap.put(1, IntMeta.class);
        dataMap.put(2, FloatMeta.class);
        dataMap.put(3, StringMeta.class);
        dataMap.put(4, ChatMeta.class);
        dataMap.put(5, ItemStackMeta.class);
        dataMap.put(6, BooleanMeta.class);
        dataMap.put(7, VectorMeta.class);
        dataMap.put(8, LocationMeta.class);
        dataMap.put(9, OptionalLocationMeta.class);
        dataMap.put(10, IntMeta.class);
        dataMap.put(11, OptionalUUIDMeta.class);
        dataMap.put(12, IntMeta.class);
    }

}
