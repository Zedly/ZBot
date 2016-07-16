package zedly.zbot.api.event;

public class EnterCreditsEvent extends Event {

    private float value;

    public EnterCreditsEvent(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }
}
