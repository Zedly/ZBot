/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.environment;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.Collections;
import zedly.zbot.entity.CraftEntity;

import java.util.HashMap;
import java.util.UUID;
import zedly.zbot.Location;
import zedly.zbot.entity.Entity;
import zedly.zbot.entity.*;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
public class CraftEnvironment implements Environment {

    private static final HashMap<Integer, Class> entityTypeMap = new HashMap<>();
    private static final HashMap<Integer, Class> objectTypeMap = new HashMap<>();
    private static final CraftChunk AIR_CHUNK = new CraftChunk();

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

    @Override
    public String getPlayerNameByUUID(UUID uuid) {
        return playerNameCache.get(uuid);
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

    public int getDifficulty() {
        return difficulty;
    }

    public int getNumberOfLoadedChunks() {
        return chunks.size();
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

    // Non-API methods start here
    
    public void loadChunkColumn(int x, int z, CraftChunk[] chunkArray, boolean completeWithAirChunks) {
        for (int i = 0; i < 16; i++) {
            long chunkId = chunkCoordinatesToChunkLong(x, i, z);
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
                    chunks.put(chunkCoordinatesToChunkLong(chunkX, i, chunkZ), edis.readChunkSection(worldType == 0));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                chunks.put(chunkCoordinatesToChunkLong(chunkX, i, chunkZ), new CraftChunk());
            }
        }
    }

    public void reset(int worldType) {
        chunks.clear();
        this.worldType = worldType;
        System.out.println("Teleported to new world: Type " + worldType);
    }

    public CraftChunk getChunkAt(int x, int y, int z) {
        if (y < 0 || y >= 256) {
            return AIR_CHUNK;
        }
        long chunkLong = blockCoordinatesToChunkLong(x, y, z);
        if (chunks.containsKey(chunkLong)) {
            return chunks.get(chunkLong);
        }
        return AIR_CHUNK;
    }

    public void setBlockAt(int x, int y, int z, int typeId, int blockData) {
        CraftChunk cc = getChunkAt(x, y, z);
        if (cc == null) {
            return;
        }
        cc.setBlockAt(x, y, z, typeId, blockData);
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

    private long chunkCoordinatesToChunkLong(long chunkX, long chunkY, long chunkZ) {
        return (chunkX & 0xFFFFFF) | ((chunkY & 0xF) << 24) | ((chunkZ & 0xFFFFFF) << 28);
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

    static {
        entityTypeMap.put(1, CraftItem.class);
        //entityTypeMap.put(2, CraftExperienceOrb.class); // TODO: Implement
        entityTypeMap.put(3, CraftAreaEffectCloud.class);
        //entityTypeMap.put(4, CraftElderGuardian.class);
        //entityTypeMap.put(5, CraftWitherSkeleton.class);
        //entityTypeMap.put(6, CraftStray.class);
        entityTypeMap.put(7, CraftThrownEgg.class);
        entityTypeMap.put(8, CraftLeashKnot.class);
        //entityTypeMap.put(9, CraftPainting.class); // TODO: Implement
        entityTypeMap.put(10, CraftArrow.class);
        entityTypeMap.put(11, CraftSnowball.class);
        entityTypeMap.put(12, CraftFireball.class);
        entityTypeMap.put(13, CraftSmallFireball.class);
        entityTypeMap.put(14, CraftThrownEnderpearl.class);
        entityTypeMap.put(15, CraftEyeOfEnderSignal.class);
        entityTypeMap.put(16, CraftThrownPotion.class);
        entityTypeMap.put(17, CraftThrownExperienceBottle.class);
        entityTypeMap.put(18, CraftItemFrame.class);
        entityTypeMap.put(19, CraftWitherSkull.class);
        entityTypeMap.put(20, CraftPrimedTNT.class);
        entityTypeMap.put(21, CraftFallingBlock.class);
        entityTypeMap.put(22, CraftFirework.class);
        //entityTypeMap.put(23, CraftHusk.class);
        entityTypeMap.put(24, CraftArrowSpectral.class);
        entityTypeMap.put(25, CraftShulkerBullet.class);
        entityTypeMap.put(26, CraftDragonFireball.class);
        //entityTypeMap.put(1, CraftZombieVillager.class);
        //entityTypeMap.put(1, CraftSkeletonHorse.class);
        //entityTypeMap.put(1, CraftZombieHorse.class);
        //entityTypeMap.put(1, CraftDonkey.class);
        //entityTypeMap.put(1, CraftMule.class);
        //entityTypeMap.put(1, CraftEvocationFangs.class);
        //entityTypeMap.put(1, CraftEvocationIllager.class);
        //entityTypeMap.put(1, CraftVex.class);
        //entityTypeMap.put(1, CraftVindicationIllager.class);
        //entityTypeMap.put(1, CraftMinecartCommandBlock.class);
        entityTypeMap.put(1, CraftBoat.class);
        //entityTypeMap.put(1, CraftMinecartRideable.class);
        //entityTypeMap.put(1, CraftMinecartChest.class);
        //entityTypeMap.put(1, CraftMinecartFurnace.class);
        //entityTypeMap.put(1, CraftMinecartTNT.class);
        //entityTypeMap.put(1, CraftMinecartHopper.class);
        //entityTypeMap.put(1, CraftMinecartSpawner.class);

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
        entityTypeMap.put(100, CraftAbstractHorse.class);
        entityTypeMap.put(101, CraftRabbit.class);
        //entityTypeMap.put(102, CraftPolarBear.class);
        //entityTypeMap.put(103, CraftLlama.class);
        entityTypeMap.put(104, CraftRabbit.class);
        entityTypeMap.put(120, CraftVillager.class);
        entityTypeMap.put(200, CraftEnderCrystal.class);

        objectTypeMap.put(1, CraftBoat.class);
        objectTypeMap.put(2, CraftItem.class);
        objectTypeMap.put(3, CraftAreaEffectCloud.class);
        objectTypeMap.put(10, CraftMinecart.class);
        objectTypeMap.put(50, CraftPrimedTNT.class);
        objectTypeMap.put(51, CraftEnderCrystal.class);
        objectTypeMap.put(60, CraftArrow.class);
        objectTypeMap.put(61, CraftSnowball.class);
        objectTypeMap.put(62, CraftThrownEgg.class);
        objectTypeMap.put(63, CraftFireball.class);
        objectTypeMap.put(64, CraftSmallFireball.class);
        objectTypeMap.put(65, CraftThrownEnderpearl.class);
        objectTypeMap.put(66, CraftWitherSkull.class);
        objectTypeMap.put(67, CraftShulkerBullet.class);
        objectTypeMap.put(70, CraftFallingBlock.class);
        objectTypeMap.put(71, CraftItemFrame.class);
        objectTypeMap.put(72, CraftEyeOfEnderSignal.class);
        objectTypeMap.put(73, CraftThrownPotion.class);
        objectTypeMap.put(74, CraftDragonEgg.class);
        objectTypeMap.put(75, CraftThrownExperienceBottle.class);
        objectTypeMap.put(76, CraftFirework.class);
        objectTypeMap.put(77, CraftLeashKnot.class);
        objectTypeMap.put(78, CraftArmorStand.class);
        objectTypeMap.put(90, CraftFishingHook.class);
        objectTypeMap.put(91, CraftArrowSpectral.class);
        objectTypeMap.put(93, CraftDragonFireball.class);
    }
}
