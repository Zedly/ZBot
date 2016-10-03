/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network;

import zedly.zbot.inventory.ItemStack;
import net.minecraft.server.NBTBase;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 *
 * @author Tatious
 */
public class ExtendedDataOutputStream extends DataOutputStream {

    public ExtendedDataOutputStream(OutputStream out) {
        super(out);
    }

    public void writeString(String str) throws IOException {
        byte[] bts = str.getBytes("UTF-8");
        writeVarInt(bts.length);
        for (byte bt : bts) {
            write(bt);
        }
    }

    public void writeVarInt(int value) throws IOException {
        while (true) {
            if ((value & ~0x7F) == 0) {
                write(value);
                return;
            } else {
                write((value & 0x7F) | 0x80);
                value >>>= 7;
            }
        }
    }
    
    public void writeVarLong(long value) throws IOException {
        while (true) {
            if ((value & ~0x7F) == 0) {
                write((int) value);
                return;
            } else {
                write((int) ((value & 0x7F) | 0x80));
                value >>>= 7;
            }
        }
    }

    public void writePosition(int x, int y, int z) throws IOException {
        long raw = ((long) x & 0x3FFFFFF) << 38 | (((long) y & 0xFFF) << 26) | ((long) z & 0x3FFFFFF);
        writeLong(raw);
    }

    public void writeUUID(UUID uuid) throws IOException {
        writeLong(uuid.getMostSignificantBits());
        writeLong(uuid.getLeastSignificantBits());
    }

    public void writeSlot(ItemStack item) throws IOException {
        if(item == null) {
            writeShort(-1);
            return;
        }
        writeShort(item.getTypeId());
        if (item.getTypeId() == -1) {
            return;
        }
        writeByte(item.getAmount());
        writeShort(item.getDamageValue());
        writeNBT(item.getNbt());
    }
    
    public void writeNBT(NBTBase nbt) throws IOException {
        NBTBase.writeNamedTag(nbt, this);
    }
}
