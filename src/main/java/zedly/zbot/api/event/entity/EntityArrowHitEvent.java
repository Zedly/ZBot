package zedly.zbot.api.event.entity;

import zedly.zbot.api.event.Event;

public class EntityArrowHitEvent extends Event {

    private float value;

    public EntityArrowHitEvent(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }
}
