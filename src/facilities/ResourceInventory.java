package facilities;

import common.ResourceType;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class ResourceInventory {
    private final Map<ResourceType, Integer> quantities;

    public ResourceInventory() {
        this.quantities = new EnumMap<>(ResourceType.class);
        for (ResourceType type : ResourceType.values()) {
            quantities.put(type, 0);
        }
    }

    public int getQuantity(ResourceType type) {
        Objects.requireNonNull(type, "type");
        return quantities.getOrDefault(type, 0);
    }

    public void addResource(ResourceType type, int amount) {
        Objects.requireNonNull(type, "type");
        if (amount < 0) {
            throw new IllegalArgumentException("Resource amount cannot be negative");
        }
        if (amount > 0) {
            quantities.put(type, getQuantity(type) + amount);
        }
    }

    public boolean consumeResource(ResourceType type, int amount) {
        Objects.requireNonNull(type, "type");
        if (amount <= 0) return false;
        int current = getQuantity(type);
        if (current >= amount) {
            quantities.put(type, current - amount);
            return true;
        }
        return false;
    }

    public int forceDeduct(ResourceType type, int amount) {
        Objects.requireNonNull(type, "type");
        if (amount <= 0) return 0;
        int current = getQuantity(type);
        int actualLost = Math.min(current, amount);
        quantities.put(type, current - actualLost);
        return actualLost;
    }

    public Map<ResourceType, Integer> getAllResources() {
        return Collections.unmodifiableMap(quantities);
    }

    public int get(ResourceType type) {
        return getQuantity(type);
    }

    public void add(ResourceType type, int amount) {
        addResource(type, amount);
    }

    public boolean remove(ResourceType type, int amount) {
        return consumeResource(type, amount);
    }

    public int deductAndClamp(ResourceType type, int amount) {
        return forceDeduct(type, amount);
    }

    public Map<ResourceType, Integer> getAll() {
        return getAllResources();
    }

    @Override
    public String toString() {
        return quantities.toString();
    }
}