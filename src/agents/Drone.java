package agents;

import common.Capability;
import common.EmergencyType;

public class Drone extends Robot {

    public Drone(int id, String name, String location, int battery) {
        super(id, name, location, battery);
    }

    @Override
    public boolean canPerform(Capability capability) {
        return capability == Capability.AERIAL_RECONNAISSANCE || capability == Capability.EXPLORATION || capability == Capability.RESCUE;
    }

    @Override
    public boolean canHandle(EmergencyType emergencyType) {
        return false;
    }

    @Override
    public boolean canPerform(String missionType) {
        return missionType.equalsIgnoreCase("EXPLORATION") || missionType.equalsIgnoreCase("RESCUE") || missionType.equalsIgnoreCase("AERIAL_RECONNAISSANCE");
    }

    @Override
    public boolean canHandle(String emergencyType) {
        return false;
    }

    @Override
    protected void setAvailable(boolean b) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setAvailable'");
    }
}