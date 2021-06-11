package solid.ocp;

public class GazolineCar extends Car {
    public GazolineCar() {
        super("gazoline");
    }

    @Override
    public String printEngineType() {
        return "new hydrogene technology";
    }
}
