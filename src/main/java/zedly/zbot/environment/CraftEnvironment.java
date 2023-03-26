/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.environment;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.Collections;
import zedly.zbot.entity.CraftEntity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import net.minecraft.server.NBTBase;
import net.minecraft.server.NBTTagCompound;
import zedly.zbot.BitSet;
import zedly.zbot.Location;
import zedly.zbot.entity.Entity;
import zedly.zbot.entity.*;
import zedly.zbot.network.ExtendedDataInputStream;
import zedly.zbot.network.mappings.EntityTypeIds;

/**
 *
 * @author Dennis
 */
public class CraftEnvironment implements Environment {

    private static final CraftChunk AIR_CHUNK = new CraftChunk();

    private final HashMap<Integer, CraftEntity> entities = new HashMap<>();
    private final BiMap<UUID, String> playerNameCache = HashBiMap.create();
    private final HashMap<Long, CraftChunk> chunks = new HashMap<>();
    private Location spawnPoint = null;
    private NBTBase worldType;
    private int difficulty = 0;
    private long timeOfDay = 0;
    private long worldAge = 0;

    @Override
    public Collection<Entity> getEntities() {
        HashSet<Entity> ents = new HashSet<>();
        ents.addAll(entities.values());
        return ents;
    }

    @Override
    public <T extends Entity> Collection<T> getEntities(Class<T> cls) {
        HashSet<T> ents = new HashSet<>();
        entities.forEach((i, e) -> {
            if (cls.isInstance(e)) {
                ents.add(cls.cast(e));
            }
        });
        return ents;
    }

    @Override
    public CraftEntity getEntityById(int entityId) {
        return entities.get(entityId);
    }

    @Override
    public String getPlayerNameByUUID(UUID uuid) {
        return playerNameCache.get(uuid);
    }

    @Override
    public UUID getUUIDByPlayerName(String name) {
        return playerNameCache.inverse().get(name);
    }

    @Override
    public CraftBlock getBlockAt(int x, int y, int z) {
        return new CraftBlock(this, x, y, z);
    }

    @Override
    public CraftBlock getBlockAt(Location loc) {
        return getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    public boolean isChunkLoaded(int x, int y, int z) {
        long chunkLong = blockCoordinatesToChunkLong(x, y, z);
        return chunks.containsKey(chunkLong);
    }

    @Override
    public int getDifficulty() {
        return difficulty;
    }

    public int getNumberOfLoadedChunks() {
        return chunks.size();
    }

    @Override
    public long getTimeOfDay() {
        return timeOfDay;
    }

    @Override
    public long getWorldAge() {
        return worldAge;
    }

    public void addEntity(CraftEntity ent) {
        entities.put(ent.getEntityId(), ent);
    }

    public void removeEntity(int entityId) {
        entities.remove(entityId);
    }

    public void addPlayerNameAndUUID(UUID uuid, String name) {
        playerNameCache.put(uuid, name);
    }

    public void setTimeOfDay(long timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public void setWorldAge(long worldAge) {
        this.worldAge = worldAge;
    }

    public void loadChunkColumn(byte[] blockData, int chunkX, int chunkZ, BitSet skyLightMask, BitSet blockLightMask, BitSet emptySkyLightMask, BitSet emptyBlockLightMask, List<byte[]> skyLightArrays, List<byte[]> blockLightArrays, NBTBase[] blockEntities) {
        ExtendedDataInputStream edis = new ExtendedDataInputStream(new ByteArrayInputStream(blockData));
        for (int i = 0; i < 16; i++) {
            long chunkLong = chunkCoordinatesToChunkLong(chunkX, i, chunkZ);
            try {
                chunks.put(chunkLong, edis.readChunkSection());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        for (NBTBase tile : blockEntities) {
            NBTTagCompound compound = (NBTTagCompound) tile;
            int x = compound.getInteger("x");
            int y = compound.getInteger("y");
            int z = compound.getInteger("z");
            setTileAt(x, y, z, compound);
        }
    }

    public void reloadChunkBiomes(int chunkX, int chunkZ, byte[] biomeData) {
    }

    public void reset(NBTBase worldType) {
        chunks.clear();
        this.worldType = worldType;
        System.out.println("Teleported to new world: Type " + worldType);
    }

    public CraftChunk getChunkAt(int x, int y, int z) {
        if (y < 0 || y >= 256) {
            return AIR_CHUNK;
        }
        long chunkLong = blockCoordinatesToChunkLong(x, y, z);
        return getChunkAt(chunkLong);
    }

    private CraftChunk getChunkAt(long chunkLong) {
        if (chunks.containsKey(chunkLong)) {
            return chunks.get(chunkLong);
        }
        return AIR_CHUNK;
    }

    public void setBlockAt(int x, int y, int z, int typeId) {
        CraftChunk cc = getChunkAt(x, y, z);
        if (cc == null) {
            return;
        }
        cc.setBlockAt(x, y, z, typeId);
    }

    public void setTileAt(int x, int y, int z, NBTTagCompound tile) {
        setTileAt(new Location(x, y, z), tile);
    }

    public void setTileAt(Location loc, NBTTagCompound tile) {
        CraftChunk cc = getChunkAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        if (cc == null) {
            return;
        }
        cc.setTileAt(loc, tile);
    }

    public CraftEntity spawnEntity(int typeId, int entityId, Location loc) {
        CraftEntity ce;
        try {
            ce = EntityTypeIds.forProtocolId(typeId).newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            System.err.println("Exception creating new entity: Type " + typeId);
            ex.printStackTrace();
            ce = new CraftUnknown();
        }
        ce.setEntityId(entityId);
        ce.setLocation(loc);
        entities.put(entityId, ce);
        return ce;
    }

    public <T extends CraftEntity> T spawnEntity(Class<T> cl, int entityId, Location loc) {
        try {
            T craftEntity = (T) cl.newInstance();
            craftEntity.setEntityId(entityId);
            craftEntity.setLocation(loc);
            entities.put(entityId, craftEntity);
            return craftEntity;
        } catch (InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private long chunkCoordinatesToChunkLong(long chunkX, long chunkY, long chunkZ) {
        return new Location(chunkX, chunkY, chunkZ).toLong();
    }

    private long blockCoordinatesToChunkLong(long x, long y, long z) {
        long chunkX, chunkY, chunkZ;
        if (x < 0) {
            chunkX = (x + 1) / 16 - 1;
        } else {
            chunkX = x / 16;
        }
        chunkY = y / 16;
        if (z < 0) {
            chunkZ = (z + 1) / 16 - 1;
        } else {
            chunkZ = z / 16;
        }
        return chunkCoordinatesToChunkLong(chunkX, chunkY, chunkZ);
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * @return the spawnPoint
     */
    public Location getSpawnPoint() {
        return spawnPoint;
    }

    /**
     * @param spawnPoint the spawnPoint to set
     */
    public void setSpawnPoint(Location spawnPoint) {
        this.spawnPoint = spawnPoint;
    }
}
