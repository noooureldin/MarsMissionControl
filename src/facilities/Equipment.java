package facilities;

public class Equipment {
    private String id;
    private String name;
    private boolean isFunctional;

    public Equipment(String id, String name) {
        this.id = id;
        this.name = name;
        this.isFunctional = true;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public boolean isFunctional() { return isFunctional; }
    public void setFunctional(boolean functional) { isFunctional = functional; }
}