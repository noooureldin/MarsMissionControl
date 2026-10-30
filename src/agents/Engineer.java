package agents;

import common.Capability;
import common.EmergencyType;

public class Engineer extends CrewMember {

    public Engineer(int id, String name, String location) {
        super(id, name, location);
        addSkill(Capability.REPAIR);
    }

    @Override
    public boolean canPerform(Capability capability) {
        return capability == Capability.REPAIR;
    }

    @Override
    public boolean canHandle(EmergencyType emergencyType) {
        return isAvailable() && (
               emergencyType == EmergencyType.OXYGEN_LEAK
            || emergencyType == EmergencyType.FIRE
            || emergencyType == EmergencyType.ROVER_BREAKDOWN
        );
    }

    @Override
    public boolean canPerform(String missionType) {
        return missionType.equalsIgnoreCase("REPAIR");
    }

    @Override
    public boolean canHandle(String emergencyType) {
        return isAvailable() && (
               emergencyType.equalsIgnoreCase("OXYGEN_LEAK")
            || emergencyType.equalsIgnoreCase("FIRE")
            || emergencyType.equalsIgnoreCase("ROVER_BREAKDOWN")
        );
    }

    @Override
    protected void setAvailable(boolean b) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setAvailable'");
    }
}