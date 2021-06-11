package solid.ocp;

public class HydrogeneCar extends Car {
    public HydrogeneCar() {
        super("hydrogene");
    }

    @Override
    public String printEngineType() {
        return "gazoline";
    }
}
