package solid.ocp;

import static java.util.Arrays.asList;

public class Main {
    public void displayEngineType() {
        final GazolineCar gazolineCar = new GazolineCar();
        final EletricCar eletricCar = new EletricCar();
        final HydrogeneCar hydrogeneCar = new HydrogeneCar();

        asList(gazolineCar, eletricCar, hydrogeneCar)
                .stream()
                .forEach(Car::printEngineType);
    }
}
