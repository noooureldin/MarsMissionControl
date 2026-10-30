package agents;

import common.Capability;
import common.EmergencyType;

import java.util.ArrayList;
import java.util.List;

public abstract class CrewMember extends Agent {
    private final List<String> skills;
    private int fatigue;

    public CrewMember(int id, String name, String location) {
        super(id, name, location);
        this.skills = new ArrayList<>();
        this.fatigue = 0;
    }

    public void addSkill(String skill) {
        if (!skills.contains(skill)) {
            skills.add(skill);
        }
    }

    public void addSkill(Capability capability) {
        addSkill(capability.name());
    }

    public boolean hasSkill(String skill) {
        return skills.contains(skill);
    }

    public boolean hasSkill(Capability capability) {
        return hasSkill(capability.name());
    }

    public void increaseFatigue(int amount) {
        fatigue = Math.min(100, fatigue + amount);
    }

    public void rest(int amount) {
        fatigue = Math.max(0, fatigue - amount);
    }

    public int getFatigue() {
        return fatigue;
    }

    public List<String> getSkills() {
        return new ArrayList<>(skills);
    }

    @Override
    public boolean canPerform(Capability capability) {
        return hasSkill(capability);
    }

    @Override
    public boolean canHandle(EmergencyType emergencyType) {
        return isAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable() && fatigue < 100;
    }

    @Override
    public String toString() {
        return super.toString() + " | fatigue=" + fatigue + " | skills=" + skills;
    }
}