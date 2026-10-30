package facilities;

import common.ResourceType;

public class Laboratory extends Facility {
    public Laboratory(String id, String name, String location, int capacity) {
        super(id, name, location, capacity);
        addResource(ResourceType.SPARE_PARTS, 50);
        addResource(ResourceType.BATTERY_POWER, 200);
    }
}