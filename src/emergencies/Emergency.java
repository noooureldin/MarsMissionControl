package emergencies;

import common.Capability;
import common.ResourceType;
import facilities.Facility;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public abstract class Emergency {
    private final String id;
    private final String description;
    private final Facility location;
    private boolean isResolved;
    private String failureReason;
    protected final Map<ResourceType, Integer> requiredResources = new EnumMap<>(ResourceType.class);

    public Emergency(String id, String description, Facility location) {
        this.id = id;
        this.description = description;
        this.location = location;
        this.isResolved = false;
    }

    public String getId() { return id; }
    public String getDescription() { return description; }
    public Facility getLocation() { return location; }
    public boolean isResolved() { return isResolved; }
    public String getFailureReason() { return failureReason; }
    public Map<ResourceType, Integer> getRequiredResources() { return requiredResources; }

    public abstract List<Capability> getRequiredCapabilities();

    public void resolve() {
        this.isResolved = true;
    }

    public void markUnresolved(String reason) {
        this.isResolved = false;
        this.failureReason = reason;
    }

    public abstract void onEmergencyTriggered();
    public abstract void onUnresolved();
}