/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.mappings;

import com.google.common.io.Resources;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.Charsets;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import zedly.zbot.EntityType;
import zedly.zbot.entity.*;

/**
 *
 * @author Dennis
 */
public class EntityTypeIds {

    private static final ArrayList<Class<? extends CraftEntity>> KNOWN_ENTITY_CLASSES = new ArrayList<>();
    private static final HashMap<EntityType, Class<? extends CraftEntity>> ENTITY_TYPE_MAP = new HashMap<>();
    private static final HashMap<Integer, EntityType> PROTOCOL_ID_MAP = new HashMap<>();

    public static Class<? extends CraftEntity> forEntityType(EntityType type) {
        return ENTITY_TYPE_MAP.getOrDefault(type, CraftUnknown.class);
    }

    public static Class<? extends CraftEntity> forProtocolId(int protocolId) {
        return forEntityType(PROTOCOL_ID_MAP.getOrDefault(protocolId, EntityType.UNKNOWN));
    }

    private static void parseAllInstances() {
        try {
            RegistryProtocolIdMapper.mapFlatRegistry("minecraft:entity_type", (namespacedKey, protocolId) -> {
                try {
                    String enumValueString = namespacedKey.replace("minecraft:", "").toUpperCase();
                    EntityType type = EntityType.valueOf(enumValueString);
                    PROTOCOL_ID_MAP.put(protocolId, type);
                    if (ENTITY_TYPE_MAP.get(type) == null) {
                        System.err.println("Incomplete Entity Type mapping for " + namespacedKey);
                    }
                } catch (IllegalArgumentException ex) {
                    System.err.println("No Entity Type mapping for " + namespacedKey);
                }
            });
        } catch (IOException | ParseException ex) {
        }
    }

    static {
        KNOWN_ENTITY_CLASSES.add(CraftAreaEffectCloud.class);
        KNOWN_ENTITY_CLASSES.add(CraftArmorStand.class);
        KNOWN_ENTITY_CLASSES.add(CraftArrow.class);
        KNOWN_ENTITY_CLASSES.add(CraftBat.class);
        KNOWN_ENTITY_CLASSES.add(CraftBee.class);
        KNOWN_ENTITY_CLASSES.add(CraftBlaze.class);
        KNOWN_ENTITY_CLASSES.add(CraftBoat.class);
        KNOWN_ENTITY_CLASSES.add(CraftCat.class);
        KNOWN_ENTITY_CLASSES.add(CraftCaveSpider.class);
        KNOWN_ENTITY_CLASSES.add(CraftChicken.class);
        KNOWN_ENTITY_CLASSES.add(CraftCod.class);
        KNOWN_ENTITY_CLASSES.add(CraftCow.class);
        KNOWN_ENTITY_CLASSES.add(CraftCreeper.class);
        KNOWN_ENTITY_CLASSES.add(CraftDolphin.class);
        KNOWN_ENTITY_CLASSES.add(CraftDonkey.class);
        KNOWN_ENTITY_CLASSES.add(CraftDragonFireball.class);
        KNOWN_ENTITY_CLASSES.add(CraftDrowned.class);
        KNOWN_ENTITY_CLASSES.add(CraftElderGuardian.class);
        KNOWN_ENTITY_CLASSES.add(CraftEnderCrystal.class);
        KNOWN_ENTITY_CLASSES.add(CraftEnderdragon.class);
        KNOWN_ENTITY_CLASSES.add(CraftEnderman.class);
        KNOWN_ENTITY_CLASSES.add(CraftEndermite.class);
        KNOWN_ENTITY_CLASSES.add(CraftEvocationFangs.class);
        KNOWN_ENTITY_CLASSES.add(CraftEvoker.class);
        KNOWN_ENTITY_CLASSES.add(CraftExperienceOrb.class);
        KNOWN_ENTITY_CLASSES.add(CraftEyeOfEnderSignal.class);
        KNOWN_ENTITY_CLASSES.add(CraftFallingBlock.class);
        KNOWN_ENTITY_CLASSES.add(CraftFireball.class);
        KNOWN_ENTITY_CLASSES.add(CraftFirework.class);
        KNOWN_ENTITY_CLASSES.add(CraftFishingHook.class);
        KNOWN_ENTITY_CLASSES.add(CraftFox.class);
        KNOWN_ENTITY_CLASSES.add(CraftGhast.class);
        KNOWN_ENTITY_CLASSES.add(CraftGiant.class);
        KNOWN_ENTITY_CLASSES.add(CraftGuardian.class);
        KNOWN_ENTITY_CLASSES.add(CraftHorse.class);
        KNOWN_ENTITY_CLASSES.add(CraftHusk.class);
        KNOWN_ENTITY_CLASSES.add(CraftIllusioner.class);
        KNOWN_ENTITY_CLASSES.add(CraftIronGolem.class);
        KNOWN_ENTITY_CLASSES.add(CraftItem.class);
        KNOWN_ENTITY_CLASSES.add(CraftItemFrame.class);
        KNOWN_ENTITY_CLASSES.add(CraftLavaSlime.class);
        KNOWN_ENTITY_CLASSES.add(CraftLeashKnot.class);
        KNOWN_ENTITY_CLASSES.add(CraftLightningBolt.class);
        KNOWN_ENTITY_CLASSES.add(CraftLlama.class);
        KNOWN_ENTITY_CLASSES.add(CraftLlamaSpit.class);
        KNOWN_ENTITY_CLASSES.add(CraftMinecartChest.class);
        KNOWN_ENTITY_CLASSES.add(CraftMinecartCommandBlock.class);
        KNOWN_ENTITY_CLASSES.add(CraftMinecartFurnace.class);
        KNOWN_ENTITY_CLASSES.add(CraftMinecartHopper.class);
        KNOWN_ENTITY_CLASSES.add(CraftMinecartPowered.class);
        KNOWN_ENTITY_CLASSES.add(CraftMinecartRideable.class);
        KNOWN_ENTITY_CLASSES.add(CraftMinecartSpawner.class);
        KNOWN_ENTITY_CLASSES.add(CraftMinecartTNT.class);
        KNOWN_ENTITY_CLASSES.add(CraftMooshroom.class);
        KNOWN_ENTITY_CLASSES.add(CraftMule.class);
        KNOWN_ENTITY_CLASSES.add(CraftOcelot.class);
        KNOWN_ENTITY_CLASSES.add(CraftPainting.class);
        KNOWN_ENTITY_CLASSES.add(CraftPanda.class);
        KNOWN_ENTITY_CLASSES.add(CraftParrot.class);
        KNOWN_ENTITY_CLASSES.add(CraftPhantom.class);
        KNOWN_ENTITY_CLASSES.add(CraftPig.class);
        KNOWN_ENTITY_CLASSES.add(CraftPigZombie.class);
        KNOWN_ENTITY_CLASSES.add(CraftPillager.class);
        KNOWN_ENTITY_CLASSES.add(CraftPlayer.class);
        KNOWN_ENTITY_CLASSES.add(CraftPolarBear.class);
        KNOWN_ENTITY_CLASSES.add(CraftPrimedTNT.class);
        KNOWN_ENTITY_CLASSES.add(CraftPufferfish.class);
        KNOWN_ENTITY_CLASSES.add(CraftRabbit.class);
        KNOWN_ENTITY_CLASSES.add(CraftRavager.class);
        KNOWN_ENTITY_CLASSES.add(CraftSalmon.class);
        KNOWN_ENTITY_CLASSES.add(CraftSheep.class);
        KNOWN_ENTITY_CLASSES.add(CraftShulker.class);
        KNOWN_ENTITY_CLASSES.add(CraftShulkerBullet.class);
        KNOWN_ENTITY_CLASSES.add(CraftSilverfish.class);
        KNOWN_ENTITY_CLASSES.add(CraftSkeleton.class);
        KNOWN_ENTITY_CLASSES.add(CraftSkeletonHorse.class);
        KNOWN_ENTITY_CLASSES.add(CraftSlime.class);
        KNOWN_ENTITY_CLASSES.add(CraftSmallFireball.class);
        KNOWN_ENTITY_CLASSES.add(CraftSnowball.class);
        KNOWN_ENTITY_CLASSES.add(CraftSnowman.class);
        KNOWN_ENTITY_CLASSES.add(CraftSpectralArrow.class);
        KNOWN_ENTITY_CLASSES.add(CraftSpider.class);
        KNOWN_ENTITY_CLASSES.add(CraftSquid.class);
        KNOWN_ENTITY_CLASSES.add(CraftStray.class);
        KNOWN_ENTITY_CLASSES.add(CraftThrownEgg.class);
        KNOWN_ENTITY_CLASSES.add(CraftThrownEnderpearl.class);
        KNOWN_ENTITY_CLASSES.add(CraftThrownExperienceBottle.class);
        KNOWN_ENTITY_CLASSES.add(CraftThrownPotion.class);
        KNOWN_ENTITY_CLASSES.add(CraftTippedArrow.class);
        KNOWN_ENTITY_CLASSES.add(CraftTraderLlama.class);
        KNOWN_ENTITY_CLASSES.add(CraftTrident.class);
        KNOWN_ENTITY_CLASSES.add(CraftTropicalFish.class);
        KNOWN_ENTITY_CLASSES.add(CraftTurtle.class);
        KNOWN_ENTITY_CLASSES.add(CraftUnknown.class);
        KNOWN_ENTITY_CLASSES.add(CraftVex.class);
        KNOWN_ENTITY_CLASSES.add(CraftVillager.class);
        KNOWN_ENTITY_CLASSES.add(CraftVillagerGolem.class);
        KNOWN_ENTITY_CLASSES.add(CraftVindicator.class);
        KNOWN_ENTITY_CLASSES.add(CraftWanderingTrader.class);
        KNOWN_ENTITY_CLASSES.add(CraftWitch.class);
        KNOWN_ENTITY_CLASSES.add(CraftWither.class);
        KNOWN_ENTITY_CLASSES.add(CraftWitherSkeleton.class);
        KNOWN_ENTITY_CLASSES.add(CraftWitherSkull.class);
        KNOWN_ENTITY_CLASSES.add(CraftWolf.class);
        KNOWN_ENTITY_CLASSES.add(CraftZombie.class);
        KNOWN_ENTITY_CLASSES.add(CraftZombieHorse.class);
        KNOWN_ENTITY_CLASSES.add(CraftZombieVillager.class);

        for (Class<? extends CraftEntity> cls : KNOWN_ENTITY_CLASSES) {
            try {
                CraftEntity ent = cls.newInstance();
                ENTITY_TYPE_MAP.put(ent.getType(), cls);
            } catch (IllegalAccessException | InstantiationException ex) {
                System.out.println("Bad Entity class in EntityTypeIds enumeration: " + cls);
            }
        }

        parseAllInstances();
    }
}
