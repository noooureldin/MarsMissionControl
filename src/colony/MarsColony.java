package colony;

import agents.Agent;
import emergencies.Emergency;
import facilities.Facility;
import missions.Mission;
import common.Capability;
import common.ResourceType;

import java.util.*;

public class MarsColony {
    private final List<Agent> agents = new ArrayList<>();
    private final List<Facility> facilities = new ArrayList<>();
    private final EnvironmentManager environmentManager;

    public MarsColony(EnvironmentManager environmentManager) {
        this.environmentManager = Objects.requireNonNull(environmentManager, "environmentManager");
    }

    private Mission activeMission = null;

    public void addAgent(Agent agent) { agents.add(agent); }
    public void addFacility(Facility facility) { facilities.add(facility); }
    public List<Agent> getAgents() { return agents; }
    public List<Facility> getFacilities() { return facilities; }
    public EnvironmentManager getEnvironmentManager() { return environmentManager; }
    public Mission getActiveMission() { return activeMission; }

    public int getTotalColonyResource(ResourceType type) {
        int total = 0;
        for (Facility f : facilities) {
            if (f.isOperational()) {
                total += f.getResourceAmount(type);
            }
        }
        return total;
    }

    public boolean consumeColonyResource(ResourceType type, int amount) {
        if (getTotalColonyResource(type) < amount) {
            return false;
        }
        int remainingToDeduct = amount;
        for (Facility f : facilities) {
            if (f.isOperational()) {
                int avail = f.getResourceAmount(type);
                if (avail > 0) {
                    int deduct = Math.min(avail, remainingToDeduct);
                    f.getStorage().forceDeduct(type, deduct);
                    remainingToDeduct -= deduct;
                    if (remainingToDeduct == 0) break;
                }
            }
        }
        return true;
    }

    public boolean submitMission(Mission mission, List<Agent> proposedAgents) {
        Objects.requireNonNull(mission, "mission");
        Objects.requireNonNull(proposedAgents, "proposedAgents");

        if (proposedAgents.isEmpty()) {
            mission.fail("Rejected: At least one agent is required.");
            return false;
        }

        if (activeMission != null && (activeMission.getState() == MissionState.IN_PROGRESS
                || activeMission.getState() == MissionState.ASSIGNED)) {
            mission.fail("Rejected: Another mission (" + activeMission.getId() + ") is currently active.");
            return false;
        }

        if (!mission.canStartUnderEnvironment(Objects.requireNonNull(
                environmentManager.getCurrentCondition(), "current environment condition"))) {
            mission.fail("Rejected: Environmental conditions (" + environmentManager.getCurrentCondition() + ") restrict this operation.");
            return false;
        }

        for (Agent agent : proposedAgents) {
            Objects.requireNonNull(agent, "proposedAgents cannot contain null");
            if (!agent.isAvailable()) {
                mission.fail("Rejected: Agent " + agent.getName() + " is currently unavailable.");
                return false;
            }
            if (!agent.canPerform(mission.getRequiredCapability())) {
                mission.fail("Rejected: Agent " + agent.getName()
                        + " lacks the required capability (" + mission.getRequiredCapability() + ").");
                return false;
            }
        }

        for (Map.Entry<ResourceType, Integer> entry : mission.getRequiredResources().entrySet()) {
            if (getTotalColonyResource(entry.getKey()) < entry.getValue()) {
                mission.fail("Rejected: Insufficient " + entry.getKey() + " in colony totals.");
                return false;
            }
        }

        for (Map.Entry<ResourceType, Integer> entry : mission.getRequiredResources().entrySet()) {
            consumeColonyResource(entry.getKey(), entry.getValue());
        }

        for (Agent agent : proposedAgents) {
            agent.assign();
        }

        mission.assignAgents(proposedAgents);
        mission.start();
        this.activeMission = mission;
        return true;
    }

    public boolean handleEmergency(Emergency emergency) {
        emergency.onEmergencyTriggered();

        List<Agent> chosenResponders = new ArrayList<>();
        for (Capability cap : emergency.getRequiredCapabilities()) {
            Agent matched = null;
            for (Agent agent : agents) {
                if (agent.isAvailable() && agent.canPerform(cap) && !chosenResponders.contains(agent)) {
                    matched = agent;
                    break;
                }
            }
            if (matched != null) {
                chosenResponders.add(matched);
            }
        }

        if (chosenResponders.size() < emergency.getRequiredCapabilities().size()) {
            emergency.markUnresolved("Required responders with suitable capabilities are unavailable.");
            emergency.onUnresolved();
            return false;
        }

        for (Map.Entry<ResourceType, Integer> entry : emergency.getRequiredResources().entrySet()) {
            if (getTotalColonyResource(entry.getKey()) < entry.getValue()) {
                emergency.markUnresolved("Insufficient response resources: " + entry.getKey());
                emergency.onUnresolved();
                return false;
            }
        }

        for (Map.Entry<ResourceType, Integer> entry : emergency.getRequiredResources().entrySet()) {
            consumeColonyResource(entry.getKey(), entry.getValue());
        }

        for (Agent r : chosenResponders) {
            if (emergency.getLocation() != null) {
                r.setLocation(emergency.getLocation().getLocation());
            }
        }

        emergency.resolve();
        return true;
    }

    public void advanceTime(int units) {
        if (activeMission != null && activeMission.getState() == MissionState.IN_PROGRESS) {
            activeMission.advanceTime(units, environmentManager.getCurrentCondition());
            if (activeMission.getState() == MissionState.COMPLETED
                    || activeMission.getState() == MissionState.FAILED) {
                for (Agent a : activeMission.getAssignedAgents()) {
                    a.release();
                }
                this.activeMission = null;
            }
        }
    }

    public void printColonyReport() {
        System.out.println("============== MARS COLONY STATUS REPORT ==============");
        System.out.println("Environment: " + environmentManager.getCurrentCondition());
        System.out.println("Active Mission: " + (activeMission != null
                ? activeMission.getId() + " [" + activeMission.getState() + "]"
                : "None"));
        System.out.println("--- Colony Aggregate Resources ---");
        for (ResourceType type : ResourceType.values()) {
            System.out.println(" - " + type + ": " + getTotalColonyResource(type));
        }
        System.out.println("=====================================================");
    }
}