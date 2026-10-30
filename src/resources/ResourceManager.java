package resources;

import common.ResourceType;
import facilities.Facility;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ResourceManager {

    public Map<ResourceType, Integer> calculateColonyTotals(List<Facility> facilities) {
        Map<ResourceType, Integer> totals = new EnumMap<>(ResourceType.class);
        for (ResourceType type : ResourceType.values()) {
            totals.put(type, 0);
        }

        for (Facility facility : facilities) {
            if (facility.isOperational()) {
                Map<ResourceType, Integer> inventory = facility.getStorage().getAllResources();
                for (Map.Entry<ResourceType, Integer> entry : inventory.entrySet()) {
                    totals.put(entry.getKey(), totals.get(entry.getKey()) + entry.getValue());
                }
            }
        }
        return totals;
    }

    public int getColonyResourceAmount(ResourceType type, List<Facility> facilities) {
        int total = 0;
        for (Facility facility : facilities) {
            if (facility.isOperational()) {
                total += facility.getStorage().getQuantity(type);
            }
        }
        return total;
    }

    public boolean consumeResources(Map<ResourceType, Integer> required, List<Facility> facilities) {
        Map<ResourceType, Integer> totals = calculateColonyTotals(facilities);
        for (Map.Entry<ResourceType, Integer> req : required.entrySet()) {
            if (totals.getOrDefault(req.getKey(), 0) < req.getValue()) {
                return false;
            }
        }

        for (Map.Entry<ResourceType, Integer> req : required.entrySet()) {
            ResourceType type = req.getKey();
            int needed = req.getValue();

            for (Facility facility : facilities) {
                if (needed <= 0) break;
                if (!facility.isOperational()) continue;

                int available = facility.getStorage().getQuantity(type);
                if (available > 0) {
                    int toDeduct = Math.min(available, needed);
                    facility.getStorage().consumeResource(type, toDeduct);
                    needed -= toDeduct;
                }
            }
        }
        return true;
    }
}