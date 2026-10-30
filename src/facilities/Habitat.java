package facilities;

import common.ResourceType;

public class Habitat extends Facility {
    private final LifeSupportSystem lifeSupport;

    public Habitat(String id, String name, String location, int capacity) {
        super(id, name, location, capacity);
        this.lifeSupport = new LifeSupportSystem();
        addResource(ResourceType.OXYGEN, 500);
        addResource(ResourceType.WATER, 300);
        addResource(ResourceType.FOOD, 300);
    }

    public LifeSupportSystem getLifeSupport() {
        return lifeSupport;
    }
}