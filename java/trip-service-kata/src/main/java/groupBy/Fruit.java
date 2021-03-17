package groupBy;

public class Fruit {
    private final String name;
    private final Integer calories;

    public Fruit(String name, Integer calories) {
        this.name = name;
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public Integer getCalories() {
        return calories;
    }
}
