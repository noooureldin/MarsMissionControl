package facilities;

import common.ResourceType;

public class PowerStation extends Facility {
    public PowerStation(String id, String name, String location, int capacity) {
        super(id, name, location, capacity);
        addResource(ResourceType.FUEL, 1000);
        addResource(ResourceType.BATTERY_POWER, 2000);
    }
}