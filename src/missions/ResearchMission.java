package missions;

import common.Capability;
import common.ResourceType;
import facilities.Facility;

public class ResearchMission extends Mission {

    public ResearchMission(String id, String description, int duration, Facility facility) {
        super(id, description, duration, facility);
        addRequiredResource(ResourceType.BATTERY_POWER, 15);
    }

    @Override
    public Capability getRequiredCapability() {
        return Capability.SCIENTIFIC_RESEARCH;
    }
}