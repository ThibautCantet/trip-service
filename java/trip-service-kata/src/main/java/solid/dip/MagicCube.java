package solid.dip;

public class MagicCube {
    private Number number;

    public MagicCube(Number number) {
        this.number = number;
    }

    public void shake() {
        final int generatedNumber = number.generate();
        System.out.println(generatedNumber);
    }
}
