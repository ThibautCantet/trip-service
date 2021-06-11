package solid.lsp;

import java.util.List;

import static java.util.Arrays.asList;

public class Main {
    public int sum() {
        final MyChildClassOne myChildClassOne = new MyChildClassOne();
        final MyChildClassTwo myChildClassTwo = new MyChildClassTwo();

        final List<MySuperClass> mySuperClasses = asList(myChildClassOne, myChildClassTwo);
        return mySuperClasses.stream()
                .map(MySuperClass::compute)
                .reduce(Integer::sum)
                .orElse(0);
    }
}
