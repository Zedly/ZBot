package zedly.zbot.api.event;

public class ChangeGamemodeEvent extends Event {

    private float value;

    public ChangeGamemodeEvent(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }
}
