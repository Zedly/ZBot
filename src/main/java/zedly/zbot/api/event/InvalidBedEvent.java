package zedly.zbot.api.event;

public class InvalidBedEvent extends Event {

    private float value;

    public InvalidBedEvent(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }
}
