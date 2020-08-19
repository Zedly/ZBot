package zedly.zbot.entity;

import zedly.zbot.EntityType;
import zedly.zbot.Location;
import zedly.zbot.entity.Entity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import zedly.zbot.entity.Player;
import zedly.zbot.event.Event;
import zedly.zbot.event.PlayerFinishEatingEvent;
import zedly.zbot.event.entity.EntityOnFireEvent;
import zedly.zbot.event.entity.PlayerSneakEvent;
import zedly.zbot.event.entity.PlayerSprintEvent;

public abstract class CraftEntity implements Entity {

    protected int entityId = 0;
    protected Location location;
    protected boolean onFire = false;
    protected boolean sneaking = false;
    protected boolean sprinting = false;
    protected boolean swimming = false;
    protected boolean invisible = false;
    protected boolean glowing = false;
    protected boolean gliding = false;
    protected int air = 300;
    protected String customName = null;
    protected boolean customNameVisible = false;
    protected boolean silent = false;
    protected boolean noGravity = false;
    protected int poseId = 0;

    @Override
    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }

    public Event setStatus(int status) {
        return null;
    }

    @Override
    public abstract EntityType getType();

    public List<Event> setMeta(HashMap<Integer, EntityMeta> metaMap) {
        List<Event> list = new LinkedList<>();
        if (metaMap.containsKey(0)) {
            int flags = metaMap.get(0).asInt();
            boolean nOnFire = (flags & 0x01) != 0;
            if (nOnFire != onFire) {
                list.add(new EntityOnFireEvent(this, nOnFire));
            }
            onFire = nOnFire;

            boolean nSneaking = (flags & 0x02) != 0;
            if (this instanceof Player && nSneaking != sneaking) {
                list.add(new PlayerSneakEvent((Player) this, nSneaking));
            }
            sneaking = nSneaking;

            boolean nSprinting = (flags & 0x08) != 0;
            if (this instanceof Player && nSprinting != sprinting) {
                list.add(new PlayerSprintEvent((Player) this, nSprinting));
            }
            sprinting = nSprinting;

            swimming = (flags & 0x10) != 0;
            invisible = (flags & 0x20) != 0;
            glowing = (flags & 0x40) != 0;
            gliding = (flags & 0x80) != 0;
        }
        if (metaMap.containsKey(1)) {
            air = metaMap.get(1).asInt();
        }
        if (metaMap.containsKey(2)) {
            customName = metaMap.get(2).asString();
        }
        if (metaMap.containsKey(3)) {
            customNameVisible = metaMap.get(3).asBoolean();
        }
        if (metaMap.containsKey(4)) {
            silent = metaMap.get(4).asBoolean();
        }
        if (metaMap.containsKey(5)) {
            noGravity = metaMap.get(5).asBoolean();
        }
        if (metaMap.containsKey(6)) {
            poseId = metaMap.get(6).asInt();
        }
        return list;
    }

    @Override
    public boolean isOnFire() {
        return onFire;
    }

    @Override
    public boolean isSneaking() {
        return sneaking;
    }

    @Override
    public boolean isSprinting() {
        return sprinting;
    }

    @Override
    public boolean isSwimming() {
        return swimming;
    }

    @Override
    public boolean isInvisible() {
        return invisible;
    }

    @Override
    public boolean isGlowing() {
        return glowing;
    }

    @Override
    public boolean isGliding() {
        return gliding;
    }

    @Override
    public int getAir() {
        return air;
    }

    @Override
    public boolean hasCustomName() {
        return customName != null;
    }

    @Override
    public String getCustomName() {
        return customName;
    }

    @Override
    public boolean isNameVisible() {
        return customNameVisible;
    }

    @Override
    public boolean isSilent() {
        return silent;
    }

    @Override
    public int getPoseId() {
        return poseId;
    }
    
    
    @Override
    public boolean hasGravity() {
        return !noGravity;
    }
    
    public String toString() {
        return getType() + " " + getEntityId();
    }
    
}
