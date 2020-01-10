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
import java.util.UUID;
import net.minecraft.server.NBTBase;
import net.minecraft.server.NBTTagCompound;
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
    private static final CraftChunk AIR_CHUNK = new CraftChunk();

    private final HashMap<Integer, CraftEntity> entities = new HashMap<>();
    private final BiMap<UUID, String> playerNameCache = HashBiMap.create();
    private final HashMap<Long, CraftChunk> chunks = new HashMap<>();
    private int worldType = 1;
    private int difficulty = 0;
    private long timeOfDay = 0;
    private long worldAge = 0;

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

    public int getDifficulty() {
        return difficulty;
    }

    public int getNumberOfLoadedChunks() {
        return chunks.size();
    }

    public long getTimeOfDay() {
        return timeOfDay;
    }

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

    public void loadChunkColumn(byte[] rawData, int chunkX, int chunkZ, boolean groundUpContinuous, int primaryBitMask, NBTBase[] blockEntities) {
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

        for (NBTBase tile : blockEntities) {
            NBTTagCompound compound = (NBTTagCompound) tile;
            int x = compound.getInteger("x");
            int y = compound.getInteger("y");
            int z = compound.getInteger("z");
            setTileAt(x, y, z, compound);
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
        if (entityTypeMap.containsKey(typeId)) {
            try {
                CraftObject ce = (CraftObject) entityTypeMap.get(typeId).newInstance();
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

    static {
        entityTypeMap.put(0, CraftAreaEffectCloud.class);
        entityTypeMap.put(1, CraftArmorStand.class);
        entityTypeMap.put(2, CraftArrow.class);
        entityTypeMap.put(3, CraftBat.class);
        entityTypeMap.put(4, CraftBlaze.class);
        entityTypeMap.put(5, CraftBoat.class);
        entityTypeMap.put(6, CraftCat.class);
        entityTypeMap.put(7, CraftCaveSpider.class);
        entityTypeMap.put(8, CraftChicken.class);
        entityTypeMap.put(9, CraftCod.class);
        entityTypeMap.put(10, CraftCow.class);
        entityTypeMap.put(11, CraftCreeper.class);
        entityTypeMap.put(12, CraftDonkey.class);
        entityTypeMap.put(13, CraftDolphin.class);
        entityTypeMap.put(14, CraftDragonFireball.class);
        entityTypeMap.put(15, CraftDrowned.class);
        entityTypeMap.put(16, CraftElderGuardian.class);
        entityTypeMap.put(17, CraftEnderCrystal.class);
        entityTypeMap.put(18, CraftEnderdragon.class);
        entityTypeMap.put(19, CraftEnderman.class);
        entityTypeMap.put(20, CraftEndermite.class);
        entityTypeMap.put(21, CraftEvocationFangs.class);
        entityTypeMap.put(22, CraftEvoker.class);
        entityTypeMap.put(23, CraftExperienceOrb.class);
        entityTypeMap.put(24, CraftEyeOfEnderSignal.class);
        entityTypeMap.put(25, CraftFallingBlock.class);
        entityTypeMap.put(26, CraftFirework.class);
        entityTypeMap.put(27, CraftFox.class);
        entityTypeMap.put(28, CraftGhast.class);
        entityTypeMap.put(29, CraftGiant.class);
        entityTypeMap.put(30, CraftGuardian.class);
        entityTypeMap.put(31, CraftHorse.class);
        entityTypeMap.put(32, CraftHusk.class);
        entityTypeMap.put(33, CraftIllusioner.class);
        entityTypeMap.put(34, CraftItem.class);
        entityTypeMap.put(35, CraftItemFrame.class);
        entityTypeMap.put(36, CraftFireball.class);
        entityTypeMap.put(37, CraftLeashKnot.class);
        entityTypeMap.put(38, CraftLlama.class);
        entityTypeMap.put(39, CraftLlamaSpit.class);
        entityTypeMap.put(40, CraftLavaSlime.class);
        entityTypeMap.put(41, CraftMinecartRideable.class);
        entityTypeMap.put(42, CraftMinecartChest.class);
        entityTypeMap.put(43, CraftMinecartCommandBlock.class);
        entityTypeMap.put(44, CraftMinecartFurnace.class);
        entityTypeMap.put(45, CraftMinecartHopper.class);
        entityTypeMap.put(46, CraftMinecartSpawner.class);
        entityTypeMap.put(47, CraftMinecartTNT.class);
        entityTypeMap.put(48, CraftMule.class);
        entityTypeMap.put(49, CraftMooshroom.class);
        entityTypeMap.put(50, CraftOcelot.class);
        entityTypeMap.put(51, CraftPainting.class);
        entityTypeMap.put(52, CraftPanda.class);
        entityTypeMap.put(53, CraftParrot.class);
        entityTypeMap.put(54, CraftPig.class);
        entityTypeMap.put(55, CraftPufferfish.class);
        entityTypeMap.put(56, CraftPigZombie.class);
        entityTypeMap.put(57, CraftPolarBear.class);
        entityTypeMap.put(58, CraftPrimedTNT.class);
        entityTypeMap.put(59, CraftRabbit.class);
        entityTypeMap.put(60, CraftSalmon.class);
        entityTypeMap.put(61, CraftSheep.class);
        entityTypeMap.put(62, CraftShulker.class);
        entityTypeMap.put(63, CraftShulkerBullet.class);
        entityTypeMap.put(64, CraftSilverfish.class);
        entityTypeMap.put(65, CraftSkeleton.class);
        entityTypeMap.put(66, CraftSkeletonHorse.class);
        entityTypeMap.put(67, CraftSlime.class);
        entityTypeMap.put(68, CraftSmallFireball.class);
        entityTypeMap.put(69, CraftSnowman.class);
        entityTypeMap.put(70, CraftSnowball.class);
        entityTypeMap.put(71, CraftSpectralArrow.class);
        entityTypeMap.put(72, CraftSpider.class);
        entityTypeMap.put(73, CraftSquid.class);
        entityTypeMap.put(74, CraftStray.class);
        entityTypeMap.put(75, CraftTraderLlama.class);
        entityTypeMap.put(76, CraftTropicalFish.class);
        entityTypeMap.put(77, CraftTurtle.class);
        entityTypeMap.put(78, CraftThrownEgg.class);
        entityTypeMap.put(79, CraftThrownEnderpearl.class);
        entityTypeMap.put(80, CraftThrownExperienceBottle.class);
        entityTypeMap.put(81, CraftThrownPotion.class);
        entityTypeMap.put(82, CraftTrident.class);
        entityTypeMap.put(83, CraftVex.class);
        entityTypeMap.put(84, CraftVillager.class);
        entityTypeMap.put(85, CraftVillagerGolem.class);
        entityTypeMap.put(86, CraftVindicator.class);
        entityTypeMap.put(87, CraftPillager.class);
        entityTypeMap.put(88, CraftWanderingTrader.class);
        entityTypeMap.put(89, CraftWitch.class);
        entityTypeMap.put(90, CraftWither.class);
        entityTypeMap.put(91, CraftWitherSkeleton.class);
        entityTypeMap.put(92, CraftWitherSkull.class);
        entityTypeMap.put(93, CraftWolf.class);
        entityTypeMap.put(94, CraftZombie.class);
        entityTypeMap.put(95, CraftZombieHorse.class);
        entityTypeMap.put(96, CraftZombieVillager.class);
        entityTypeMap.put(97, CraftPhantom.class);
        entityTypeMap.put(98, CraftRavager.class);
        entityTypeMap.put(99, CraftLightningBolt.class);
        entityTypeMap.put(100, CraftPlayer.class);
        entityTypeMap.put(101, CraftFishingHook.class);
    }
}
