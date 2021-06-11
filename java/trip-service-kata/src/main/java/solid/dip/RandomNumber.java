package solid.dip;

import java.util.Random;

public class RandomNumber implements Number {
    @Override
    public int generate() {
        return new Random().nextInt();
    }
}
