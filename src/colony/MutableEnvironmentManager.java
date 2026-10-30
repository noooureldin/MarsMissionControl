package colony;

import environment.EnvironmentalCondition;

import java.util.Objects;

public class MutableEnvironmentManager implements EnvironmentManager {
    private EnvironmentalCondition currentCondition;

    public MutableEnvironmentManager(EnvironmentalCondition initialCondition) {
        this.currentCondition = Objects.requireNonNull(initialCondition, "initialCondition");
    }

    @Override
    public EnvironmentalCondition getCurrentCondition() {
        return currentCondition;
    }

    public void setCurrentCondition(EnvironmentalCondition condition) {
        this.currentCondition = Objects.requireNonNull(condition, "condition");
    }
}
