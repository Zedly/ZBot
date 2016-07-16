package zedly.zbot.api.event.entity;

import zedly.zbot.api.event.Event;

public class EntityActionEvent extends Event {

    private final int entityId, actionId, actionData;

    public EntityActionEvent(int entityId, int actionId, int actionData) {
        this.entityId = entityId;
        this.actionId = actionId;
        this.actionData = actionData;
    }

    public int getEntityId() {
        return entityId;
    }

    public int getActionId() {
        return actionId;
    }

    public int getActionData() {
        return actionData;
    }
}
