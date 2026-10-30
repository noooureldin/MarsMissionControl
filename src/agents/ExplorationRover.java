package agents;

import common.Capability;
import common.EmergencyType;

public class ExplorationRover extends Robot {

    private boolean available;

    public ExplorationRover(int id, String name, String location, int battery) {
        super(id, name, location, battery);
    }

    @Override
    public boolean canPerform(Capability capability) {
        return capability == Capability.EXPLORATION || capability == Capability.RESCUE;
    }

    @Override
    public boolean canHandle(EmergencyType emergencyType) {
        return false;
    }

    @Override
    public boolean canPerform(String missionType) {
        return missionType.equalsIgnoreCase("EXPLORATION") || missionType.equalsIgnoreCase("RESCUE");
    }

    @Override
    public boolean canHandle(String emergencyType) {
        return false;
    }

    public void breakDown() {
        setHealth(0);
    }

    @Override
    protected void setAvailable(boolean b) {
        this.available = b;
    }
}