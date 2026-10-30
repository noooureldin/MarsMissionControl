package emergencies;

import common.Capability;
import common.ResourceType;
import facilities.Facility;

import java.util.Collections;
import java.util.List;

public class OxygenLeak extends Emergency {
    private final int leakAmount;

    public OxygenLeak(String id, Facility location, int leakAmount) {
        super(id, "Oxygen Leak in " + location.getName(), location);
        this.leakAmount = leakAmount;
        requiredResources.put(ResourceType.SPARE_PARTS, 2);
    }

    @Override
    public List<Capability> getRequiredCapabilities() {
        return Collections.singletonList(Capability.REPAIR);
    }

    @Override
    public void onEmergencyTriggered() {
        getLocation().getStorage().forceDeduct(ResourceType.OXYGEN, leakAmount);
    }

    @Override
    public void onUnresolved() {
        getLocation().getStorage().forceDeduct(ResourceType.OXYGEN, leakAmount);
    }
}