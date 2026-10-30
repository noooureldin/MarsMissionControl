package colony;

public class Simulation {
    private final MarsColony colony;

    public Simulation(MarsColony colony) {
        this.colony = colony;
    }

    public void advanceTime(int units) {
        colony.advanceTime(units);
    }

    public MarsColony getColony() {
        return colony;
    }
}