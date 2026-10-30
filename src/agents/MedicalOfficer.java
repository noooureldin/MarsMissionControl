package agents;

import common.Capability;
import common.EmergencyType;

public class MedicalOfficer extends CrewMember {

    public MedicalOfficer(int id, String name, String location) {
        super(id, name, location);
        addSkill(Capability.MEDICAL_RESPONSE);
        addSkill(Capability.RESCUE);
    }

    @Override
    public boolean canPerform(Capability capability) {
        return capability == Capability.RESCUE || capability == Capability.MEDICAL_RESPONSE;
    }

    @Override
    public boolean canHandle(EmergencyType emergencyType) {
        return isAvailable() && (
               emergencyType == EmergencyType.MEDICAL_EMERGENCY
            || emergencyType == EmergencyType.FIRE
        );
    }

    @Override
    public boolean canPerform(String missionType) {
        return missionType.equalsIgnoreCase("RESCUE") || missionType.equalsIgnoreCase("MEDICAL_RESPONSE");
    }

    @Override
    public boolean canHandle(String emergencyType) {
        return isAvailable() && (
               emergencyType.equalsIgnoreCase("MEDICAL_EMERGENCY")
            || emergencyType.equalsIgnoreCase("FIRE")
        );
    }

    public void treat(CrewMember patient, int healAmount) {
        patient.setHealth(patient.getHealth() + healAmount);
    }

    @Override
    protected void setAvailable(boolean b) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setAvailable'");
    }
}