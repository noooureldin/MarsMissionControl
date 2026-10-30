package common;

public class ResourceRequest {
    private final ResourceType type;
    private final int amount;

    public ResourceRequest(ResourceType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public ResourceType getType() { return type; }
    public int getAmount() { return amount; }
}