package emergencies;

import agents.ExplorationRover;
import common.Capability;
import common.ResourceType;

import java.util.Collections;
import java.util.List;

public class RoverBreakdown extends Emergency {
    private final ExplorationRover rover;

    public RoverBreakdown(String id, ExplorationRover rover) {
        super(id, "Rover Breakdown: " + rover.getName(), null);
        this.rover = rover;
        requiredResources.put(ResourceType.SPARE_PARTS, 3);
        requiredResources.put(ResourceType.BATTERY_POWER, 10);
    }

    @Override
    public List<Capability> getRequiredCapabilities() {
        return Collections.singletonList(Capability.REPAIR);
    }

    @Override
    public void onEmergencyTriggered() {
        rover.breakDown();
    }

    @Override
    public void onUnresolved() {
        rover.breakDown();
    }

    @Override
    public void resolve() {
        super.resolve();
        rover.setHealth(100);
        rover.release();
    }
}