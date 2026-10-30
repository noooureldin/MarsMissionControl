package missions;
import colony.MissionState;
import agents.Agent;
import common.Capability;
import common.ResourceType;
import environment.EnvironmentalCondition;
import facilities.Facility;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class Mission {
    private final String id;
    private final String description;
    private final int totalDuration;
    private int remainingDuration;
    private MissionState state;
    private String failureReason;
    private final List<Agent> assignedAgents = new ArrayList<>();
    private final Map<ResourceType, Integer> requiredResources = new EnumMap<>(ResourceType.class);
    private final Facility targetFacility;

    public Mission(String id, String description, int totalDuration, Facility targetFacility) {
        this.id = Objects.requireNonNull(id, "id");
        this.description = Objects.requireNonNull(description, "description");
        if (totalDuration <= 0) {
            throw new IllegalArgumentException("totalDuration must be positive");
        }
        this.totalDuration = totalDuration;
        this.remainingDuration = totalDuration;
        this.state = MissionState.ASSIGNED;
        this.targetFacility = targetFacility;
    }

    public String getId() { return id; }
    public String getDescription() { return description; }
    public int getTotalDuration() { return totalDuration; }
    public int getRemainingDuration() { return remainingDuration; }
    public MissionState getState() { return state; }
    public String getFailureReason() { return failureReason; }
    public List<Agent> getAssignedAgents() { return assignedAgents; }
    public Map<ResourceType, Integer> getRequiredResources() { return requiredResources; }
    public Facility getTargetFacility() { return targetFacility; }

    public void addRequiredResource(ResourceType type, int amount) {
        Objects.requireNonNull(type, "type");
        if (amount <= 0) {
            throw new IllegalArgumentException("Resource amount must be positive");
        }
        requiredResources.put(type, amount);
    }

    public void assignAgents(List<Agent> agents) {
        this.assignedAgents.addAll(agents);
        for (Agent a : agents) {
            a.assign();
        }
    }

    public abstract Capability getRequiredCapability();

    public boolean canStartUnderEnvironment(EnvironmentalCondition condition) {
        return true;
    }

    public void start() {
        if (state == MissionState.ASSIGNED) {
            this.state = MissionState.IN_PROGRESS;
        }
    }

    public void advanceTime(int units, EnvironmentalCondition currentEnv) {
        if (state != MissionState.IN_PROGRESS) return;
        if (units <= 0) {
            throw new IllegalArgumentException("Time units must be positive");
        }

        if (!canStartUnderEnvironment(currentEnv)) {
            fail("Environment state changed (" + currentEnv + ") which disrupts active mission.");
            return;
        }

        this.remainingDuration = Math.max(0, this.remainingDuration - units);
        if (this.remainingDuration == 0) {
            complete();
        }
    }

    public void complete() {
        this.state = MissionState.COMPLETED;
        releaseAgents();
    }

    public void fail(String reason) {
        this.state = MissionState.FAILED;
        this.failureReason = reason;
        releaseAgents();
    }

    private void releaseAgents() {
        for (Agent a : assignedAgents) {
            a.release();
        }
    }
}