package facilities;

import common.ResourceType;

public abstract class Facility {
    private final String id;
    private final String name;
    private final String location;
    private final int capacity;
    private boolean isOperational;
    protected final ResourceInventory storage;

    public Facility(String id, String name, String location, int capacity) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.isOperational = true;
        this.storage = new ResourceInventory();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public int getCapacity() { return capacity; }
    public boolean isOperational() { return isOperational; }
    public void setOperational(boolean operational) { isOperational = operational; }
    public ResourceInventory getStorage() { return storage; }

    public int getResourceAmount(ResourceType type) {
        return isOperational ? storage.get(type) : 0;
    }

    public void addResource(ResourceType type, int quantity) {
        storage.add(type, quantity);
    }
}