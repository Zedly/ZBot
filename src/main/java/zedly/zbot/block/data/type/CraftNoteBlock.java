/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.block.data.type;

import org.json.simple.JSONObject;
import zedly.zbot.BlockFace;
import zedly.zbot.Instrument;
import zedly.zbot.Material;
import zedly.zbot.Note;
import zedly.zbot.block.data.CraftBlockData;

/**
 *
 * @author Dennis
 */
public class CraftNoteBlock extends CraftBlockData implements NoteBlock {

    private final Note note;
    private final Instrument instrument;
    private final boolean powered;

    public CraftNoteBlock(JSONObject json, Material mat) {
        super(json, mat);
        note = Note.values()[getInt(json, "note")];
        instrument = Instrument.valueOf(getEnumString(json, "instrument"));
        powered = getBoolean(json, "powered");
    }

    @Override
    public Instrument getInstrument() {
        return instrument;
    }

    @Override
    public Note getNote() {
        return note;
    }

    @Override
    public boolean isPowered() {
        return powered;
    }
}
