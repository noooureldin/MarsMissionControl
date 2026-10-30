package emergencies;

import colony.MarsColony;

public class EmergencyManager {
    public boolean handleEmergency(Emergency emergency, MarsColony colony) {
        return colony.handleEmergency(emergency);
    }
}