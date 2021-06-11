package solid.ocp;

public class Car {
    protected String type;

    public Car(String type) {
        this.type = type;
    }

    public String printEngineType() {
        String result = "invalid type";
        switch (type) {
            case "gazoline":
            {
                result = "thermic";
                break;
            }
            case "electric": {
                result = "electrical technology";
                break;
            }
            case "hydrogene": {
                result = "new hydrogene technology";
                break;
            }
        }
        return result;
    }
}
