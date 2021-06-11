package solid.ocp;

public class EletricCar extends Car {
    public EletricCar() {
        super("electric");
    }

    @Override
    public String printEngineType() {
        return "electrical technology";
    }
}
