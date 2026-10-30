package missions;

import common.Capability;
import common.ResourceType;
import environment.EnvironmentalCondition;

public class ExplorationMission extends Mission {

    public ExplorationMission(String id, String description, int duration) {
        super(id, description, duration, null);
        addRequiredResource(ResourceType.BATTERY_POWER, 20);
    }

    @Override
    public Capability getRequiredCapability() {
        return Capability.EXPLORATION;
    }

    @Override
    public boolean canStartUnderEnvironment(EnvironmentalCondition condition) {
        return condition != EnvironmentalCondition.DUST_STORM;
    }
}