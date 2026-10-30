package facilities;

import common.ResourceType;

public class StorageFacility extends Facility {

    public StorageFacility(String id, String name, String location, int capacity) {
        super(id, name, location, capacity);
        addResource(ResourceType.OXYGEN, 1000);
        addResource(ResourceType.WATER, 1000);
        addResource(ResourceType.FOOD, 1000);
        addResource(ResourceType.SPARE_PARTS, 200);
        addResource(ResourceType.MEDICAL_SUPPLIES, 100);
    }
}