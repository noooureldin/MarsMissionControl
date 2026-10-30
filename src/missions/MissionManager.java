package missions;

import agents.Agent;
import common.ResourceType;
import environment.EnvironmentalCondition;
import colony.MissionState;
import java.util.List;
import java.util.Map;

public class MissionManager {
    private Mission activeMission;

    public Mission getActiveMission() {
        return activeMission;
    }

    public void setActiveMission(Mission mission) {
        this.activeMission = mission;
    }

    public boolean hasActiveMission() {
        return activeMission != null && 
               (activeMission.getState() == MissionState.ASSIGNED || activeMission.getState() == MissionState.IN_PROGRESS);
    }

    public boolean submitMission(Mission mission, List<Agent> requestedAgents, Map<ResourceType, Integer> colonyResources, EnvironmentalCondition envCondition) {
        if (hasActiveMission()) {
            mission.fail("Another mission is currently active.");
            return false;
        }

        if (!mission.canStartUnderEnvironment(envCondition)) {
            mission.fail("Environmental conditions (" + envCondition + ") prevent mission start.");
            return false;
        }

        for (Agent agent : requestedAgents) {
            if (!agent.isAvailable()) {
                mission.fail("Required agent " + agent.getId() + " is not available.");
                return false;
            }
            if (!agent.canPerform(mission.getRequiredCapability())) {
                mission.fail("Agent " + agent.getId() + " does not possess the required capability: " + mission.getRequiredCapability());
                return false;
            }
        }

        for (Map.Entry<ResourceType, Integer> requirement : mission.getRequiredResources().entrySet()) {
            int available = colonyResources.getOrDefault(requirement.getKey(), 0);
            if (available < requirement.getValue()) {
                mission.fail("Insufficient resource: " + requirement.getKey() + " (Required: " + requirement.getValue() + ", Available: " + available + ")");
                return false;
            }
        }

        mission.assignAgents(requestedAgents);
        this.activeMission = mission;
        return true;
    }

    public void advanceActiveMission(int units, EnvironmentalCondition condition) {
        if (hasActiveMission()) {
            if (activeMission.getState() == MissionState.ASSIGNED) {
                activeMission.start();
            }

            activeMission.advanceTime(units, condition);

            if (activeMission.getState() == MissionState.COMPLETED || activeMission.getState() == MissionState.FAILED) {
                this.activeMission = null;
            }
        }
    }
}