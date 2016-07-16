/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.environment;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.Collections;
import zedly.zbot.api.environment.Environment;
import zedly.zbot.entity.CraftEntity;

import java.util.HashMap;
import java.util.UUID;
import zedly.zbot.Location;
import zedly.zbot.api.entity.Entity;
import zedly.zbot.block.CraftBlock;
import zedly.zbot.entity.*;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
public class CraftEnvironment implements Environment {

    private static final HashMap<Integer, Class> entityTypeMap = new HashMap<>();
    private static final HashMap<Integer, Class> objectTypeMap = new HashMap<>();

    private final HashMap<Integer, CraftEntity> entities = new HashMap<>();
    private final HashMap<UUID, String> playerNameCache = new HashMap<>();
    private final HashMap<Long, CraftChunk> chunks = new HashMap<>();
    private int worldType = 1;
    private int difficulty = 0;

    @Override
    public Collection<Entity> getEntities() {
        return Collections.unmodifiableCollection(entities.values());
    }

    @Override
    public CraftEntity getEntityById(int entityId) {
        return entities.get(entityId);
    }

    public void addEntity(CraftEntity ent) {
        entities.put(ent.getEntityId(), ent);
    }

    public void removeEntity(int entityId) {
        entities.remove(entityId);
    }

    @Override
    public String getPlayerNameByUUID(UUID uuid) {
        return playerNameCache.get(uuid);
    }

    public void addPlayerNameAndUUID(UUID uuid, String name) {
        playerNameCache.put(uuid, name);
    }

    public void resetWorld(int type) {
        chunks.clear();
        this.worldType = type;
    }

    public void loadChunkColumn(int x, int z, CraftChunk[] chunkArray, boolean completeWithAirChunks) {
        for (int i = 0; i < 16; i++) {
            long chunkId = toChunkLong(x, i, z);
            if (chunkArray[i] != null) {
                chunks.put(chunkId, chunkArray[i]);
            } else {
                chunks.put(chunkId, new CraftChunk());
            }
        }
    }

    public void loadChunkColumn(byte[] rawData, int chunkX, int chunkZ, boolean groundUpContinuous, int primaryBitMask) {
        ExtendedDataInputStream edis = new ExtendedDataInputStream(new ByteArrayInputStream(rawData));
        for (int i = 0; i < 16; i++) {
            if (((1 << i) & primaryBitMask) != 0) {
                try {
                    chunks.put(toChunkLong(chunkX, i, chunkZ), edis.readChunkSection(worldType == 1));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                chunks.put(toChunkLong(chunkX, i, chunkZ), new CraftChunk());
            }
        }
    }

    public void reset(int worldType) {
        chunks.clear();
        this.worldType = worldType;
    }

    @Override
    public CraftBlock getBlockAt(int x, int y, int z) {
        int chunkX, chunkZ, blockX, blockZ;
        if (x < 0) {
            chunkX = (x + 1) / 16 - 1;
            blockX = 15 + ((x + 1) % 16);
        } else {
            chunkX = x / 16;
            blockX = x % 16;
        }
        if (z < 0) {
            chunkZ = (z + 1) / 16 - 1;
            blockZ = 15 + ((z + 1) % 16);
        } else {
            chunkZ = z / 16;
            blockZ = z % 16;
        }
        long chunkLong = toChunkLong(chunkX, y / 16, chunkZ);
        CraftChunk cc = chunks.get(chunkLong);
        if (!chunks.containsKey(chunkLong)) {
            return null;
        }
        return cc.getBlockAt(blockX, y % 16, blockZ);
    }

    @Override
    public CraftBlock getBlockAt(Location loc) {
        return getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    public void setBlockAt(int x, int y, int z, int typeId, int blockData) {
        int chunkX, chunkZ, blockX, blockZ;
        if (x < 0) {
            chunkX = (x + 1) / 16 - 1;
            blockX = 15 + ((x + 1) % 16);
        } else {
            chunkX = x / 16;
            blockX = x % 16;
        }
        if (z < 0) {
            chunkZ = (z + 1) / 16 - 1;
            blockZ = 16 + ((z + 1) % 16);
        } else {
            chunkZ = z / 16;
            blockZ = z % 16;
        }
        long chunkLong = toChunkLong(chunkX, y / 16, chunkZ);
        CraftChunk cc = chunks.get(chunkLong);
        if (chunks.containsKey(chunkLong)) {
            cc.setBlockAt(blockX, y % 16, blockZ, typeId, blockData);
        }
    }

    public CraftEntity spawnEntity(int typeId, int entityId, Location loc) {
        CraftEntity ce;
        if (entityTypeMap.containsKey(typeId)) {
            try {
                ce = (CraftEntity) entityTypeMap.get(typeId).newInstance();
                ce.setEntityId(entityId);
                ce.setLocation(loc);
                entities.put(entityId, ce);
                return ce;
            } catch (InstantiationException | IllegalAccessException ex) {
                System.err.println("Exception creating new entity: Type " + typeId);
                ex.printStackTrace();
            }
        }
        ce = new CraftUnknown();
        ce.setEntityId(entityId);
        ce.setLocation(loc);
        entities.put(entityId, ce);
        return ce;
    }

    public CraftEntity spawnEntity(Class<? extends CraftEntity> cl, int entityId, Location loc) {
        try {
            CraftEntity ce = (CraftEntity) cl.newInstance();
            ce.setEntityId(entityId);
            ce.setLocation(loc);
            entities.put(entityId, ce);
            return ce;
        } catch (InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public CraftObject spawnObject(int typeId, int entityId, int objectData, Location loc) {
        if (objectTypeMap.containsKey(typeId)) {
            try {
                CraftObject ce = (CraftObject) objectTypeMap.get(typeId).newInstance();
                ce.setEntityId(entityId);
                ce.setObjectData(objectData);
                ce.setLocation(loc);
                entities.put(entityId, ce);
                return ce;
            } catch (InstantiationException | IllegalAccessException ex) {
                System.err.println("Exception creating new entity: Type " + typeId);
                ex.printStackTrace();
            }
        }
        CraftUnknownObject ce = new CraftUnknownObject();
        ce.setEntityId(entityId);
        ce.setObjectData(objectData);
        ce.setLocation(loc);
        ce.setEntityTypeId(typeId);
        entities.put(entityId, ce);
        return ce;
    }

    private long toChunkLong(long x, long y, long z) {
        return (x & 0xFFFFFF) + ((y & 0xF) << 24) + ((z & 0xFFFFFF) << 28);
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getNumberOfLoadedChunks() {
        return chunks.size();
    }

    static {
        entityTypeMap.put(50, CraftCreeper.class);
        entityTypeMap.put(51, CraftSkeleton.class);
        entityTypeMap.put(52, CraftSpider.class);
        entityTypeMap.put(53, CraftGiant.class);
        entityTypeMap.put(54, CraftZombie.class);
        entityTypeMap.put(55, CraftSlime.class);
        entityTypeMap.put(56, CraftGhast.class);
        entityTypeMap.put(57, CraftZombiePig.class);
        entityTypeMap.put(58, CraftEnderman.class);
        entityTypeMap.put(59, CraftCaveSpider.class);
        entityTypeMap.put(60, CraftSilverfish.class);
        entityTypeMap.put(61, CraftBlaze.class);
        entityTypeMap.put(62, CraftMagmaCube.class);
        entityTypeMap.put(63, CraftEnderdragon.class);
        entityTypeMap.put(64, CraftWither.class);
        entityTypeMap.put(65, CraftBat.class);
        entityTypeMap.put(66, CraftWitch.class);
        entityTypeMap.put(67, CraftEndermite.class);
        entityTypeMap.put(68, CraftGuardian.class);
        entityTypeMap.put(69, CraftShulker.class);
        entityTypeMap.put(90, CraftPig.class);
        entityTypeMap.put(91, CraftSheep.class);
        entityTypeMap.put(92, CraftCow.class);
        entityTypeMap.put(93, CraftChicken.class);
        entityTypeMap.put(94, CraftSquid.class);
        entityTypeMap.put(95, CraftWolf.class);
        entityTypeMap.put(96, CraftMooshroom.class);
        entityTypeMap.put(97, CraftSnowman.class);
        entityTypeMap.put(98, CraftOcelot.class);
        entityTypeMap.put(99, CraftIronGolem.class);
        entityTypeMap.put(100, CraftHorse.class);
        entityTypeMap.put(101, CraftRabbit.class);
        entityTypeMap.put(120, CraftVillager.class);

        objectTypeMap.put(1, CraftBoat.class);
        objectTypeMap.put(2, CraftItem.class);
        objectTypeMap.put(3, CraftAreaEffectCloud.class);
        objectTypeMap.put(10, CraftMinecart.class);
        objectTypeMap.put(50, CraftPrimedTNT.class);
        objectTypeMap.put(51, CraftEnderCrystal.class);
        objectTypeMap.put(60, CraftArrow.class);
        objectTypeMap.put(61, CraftSnowball.class);
        objectTypeMap.put(62, CraftEgg.class);
        objectTypeMap.put(63, CraftFireball.class);
        objectTypeMap.put(64, CraftFireCharge.class);
        objectTypeMap.put(65, CraftEnderPearl.class);
        objectTypeMap.put(66, CraftWitherSkull.class);
        objectTypeMap.put(67, CraftShulkerBullet.class);
        objectTypeMap.put(70, CraftFallingBlock.class);
        objectTypeMap.put(71, CraftItemFrame.class);
        objectTypeMap.put(72, CraftEyeOfEnder.class);
        objectTypeMap.put(73, CraftPotion.class);
        objectTypeMap.put(74, CraftDragonEgg.class);
        objectTypeMap.put(75, CraftExperienceBottle.class);
        objectTypeMap.put(76, CraftFirework.class);
        objectTypeMap.put(77, CraftLeashKnot.class);
        objectTypeMap.put(78, CraftArmorStand.class);
        objectTypeMap.put(90, CraftFishingHook.class);
        objectTypeMap.put(91, CraftArrowSpectral.class);
        objectTypeMap.put(92, CraftArrowTipped.class);
        objectTypeMap.put(93, CraftDragonFireball.class);
    }
}
