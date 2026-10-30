package agents;

import common.Capability;
import common.EmergencyType;

public class Scientist extends CrewMember {

    public Scientist(int id, String name, String location) {
        super(id, name, location);
        addSkill(Capability.SCIENTIFIC_RESEARCH);
        addSkill(Capability.EXPLORATION);
    }

    @Override
    public boolean canPerform(Capability capability) {
        return capability == Capability.SCIENTIFIC_RESEARCH || capability == Capability.EXPLORATION;
    }

    @Override
    public boolean canHandle(EmergencyType emergencyType) {
        return false;
    }

    @Override
    public boolean canPerform(String missionType) {
        return missionType.equalsIgnoreCase("RESEARCH") || missionType.equalsIgnoreCase("EXPLORATION");
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