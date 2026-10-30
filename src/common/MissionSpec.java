package common;

import java.util.Map;

public class MissionSpec {
    private Capability requiredCapability;
    private Map<ResourceType, Integer> requiredResources;
    private int duration;

    public MissionSpec(Capability requiredCapability, Map<ResourceType, Integer> requiredResources, int duration) {
        this.requiredCapability = requiredCapability;
        this.requiredResources = requiredResources;
        this.duration = duration;
    }

    public Capability getRequiredCapability() { return requiredCapability; }
    public Map<ResourceType, Integer> getRequiredResources() { return requiredResources; }
    public int getDuration() { return duration; }
}