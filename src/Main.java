import agents.Engineer;
import agents.ExplorationRover;
import agents.Scientist;
import common.ResourceType;
import colony.MarsColony;
import colony.MutableEnvironmentManager;
import environment.EnvironmentalCondition;
import facilities.Habitat;
import facilities.StorageFacility;
import missions.ExplorationMission;
import missions.ResearchMission;
import emergencies.OxygenLeak;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   MARS-OPS SIMULATION DEMONSTRATION SCENARIOS    ");
        System.out.println("==================================================\n");

        // -----------------------------------------------------------------
        // SCENARIO 1: SUCCESSFUL EXPLORATION
        // -----------------------------------------------------------------
        System.out.println("--- Scenario 1: Successful Exploration ---");
        MutableEnvironmentManager environmentManager =
                new MutableEnvironmentManager(EnvironmentalCondition.NORMAL);
        MarsColony colony = new MarsColony(environmentManager);
        
        Habitat habitatA = new Habitat("HAB-1", "Alpha Habitat", "Sector 1", 10);
        StorageFacility storage = new StorageFacility("STO-1", "Main Storage", "Sector 1", 50);
        storage.addResource(ResourceType.BATTERY_POWER, 100);
        colony.addFacility(habitatA);
        colony.addFacility(storage);

        ExplorationRover rover = new ExplorationRover(1, "Curiosity II", "Sector 1", 100);
        colony.addAgent(rover);

        System.out.println("Rover initialized: " + rover);
        ExplorationMission expMission = new ExplorationMission("M-101", "Surface Survey", 2);

        boolean submit1 = colony.submitMission(expMission, List.of(rover));
        System.out.println("Mission Submitted: " + submit1 + " | State: " + expMission.getState());
        if (!submit1) {
            System.out.println("Failure Reason: " + expMission.getFailureReason());
        }

        colony.advanceTime(2);
        System.out.println("Mission State after 2 time units: " + expMission.getState());
        System.out.println("Rover Available: " + rover.isAvailable());
        System.out.println();

        // -----------------------------------------------------------------
        // SCENARIO 2: MISSION REJECTION (UNSUITABLE AGENT)
        // -----------------------------------------------------------------
        System.out.println("--- Scenario 2: Mission Rejection (Unsuitable Agent) ---");
        Engineer engineer = new Engineer(2, "John Engineer", "Sector 1");
        colony.addAgent(engineer);

        ResearchMission researchMissionFail = new ResearchMission("M-102", "Geology Analysis", 3, habitatA);
        boolean submit2 = colony.submitMission(researchMissionFail, List.of(engineer));
        
        System.out.println("Mission Submitted: " + submit2);
        System.out.println("Mission State: " + researchMissionFail.getState());
        System.out.println("Failure Reason: " + researchMissionFail.getFailureReason());
        System.out.println();

        // -----------------------------------------------------------------
        // SCENARIO 3: RESOURCE SHORTAGE
        // -----------------------------------------------------------------
        System.out.println("--- Scenario 3: Resource Shortage ---");
        Scientist scientist = new Scientist(1, "Dr. Vance", "Sector 1");
        colony.addAgent(scientist);

        ResearchMission heavyMission = new ResearchMission("M-103", "Deep Core Testing", 5, habitatA);
        heavyMission.addRequiredResource(ResourceType.BATTERY_POWER, 1000000); // Exceeds supply

        boolean submit3 = colony.submitMission(heavyMission, List.of(scientist));
        System.out.println("Mission Submitted: " + submit3);
        System.out.println("Mission State: " + heavyMission.getState());
        System.out.println("Failure Reason: " + heavyMission.getFailureReason());
        System.out.println();

        // -----------------------------------------------------------------
        // SCENARIO 4: EMERGENCY HANDLING (OXYGEN LEAK)
        // -----------------------------------------------------------------
        System.out.println("--- Scenario 4: Emergency Handling (Oxygen Leak) ---");
        System.out.println("Habitat Oxygen Before Leak: " + habitatA.getResourceAmount(ResourceType.OXYGEN));

        // Simulate an oxygen leak
        OxygenLeak leak = new OxygenLeak("EMG-1", habitatA, 100);
        colony.handleEmergency(leak);

        System.out.println("Emergency Handled: " + leak.isResolved());
        System.out.println("Habitat Oxygen After Leak Resolution: " + habitatA.getResourceAmount(ResourceType.OXYGEN));
        System.out.println();

        // -----------------------------------------------------------------
        // SCENARIO 5: ENVIRONMENTAL EFFECT (DUST STORM)
        // -----------------------------------------------------------------
        System.out.println("--- Scenario 5: Environmental Effect (Dust Storm) ---");
        environmentManager.setCurrentCondition(EnvironmentalCondition.DUST_STORM);

        ExplorationMission stormMission = new ExplorationMission("M-104", "Storm Exploration", 2);
        boolean submit5 = colony.submitMission(stormMission, List.of(rover));

        System.out.println("Mission Submitted during Dust Storm: " + submit5);
        System.out.println("Mission State: " + stormMission.getState());
        System.out.println("Failure Reason: " + stormMission.getFailureReason());
        System.out.println("\n==================================================");
    }
}