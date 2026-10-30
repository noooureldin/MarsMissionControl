package environment;

public class Environment {
    private EnvironmentalCondition condition;

    public Environment() {
        this.condition = EnvironmentalCondition.NORMAL;
    }

    public EnvironmentalCondition getCondition() {
        return condition;
    }

    public void setCondition(EnvironmentalCondition condition) {
        this.condition = condition;
    }
}