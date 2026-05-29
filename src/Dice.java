import java.util.Random;

public class Dice {
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 6;

    private final Random random;

    public Dice() {
        random = new Random();
    }

    public int roll() {
        // nextInt(6) gives 0 to 5, so add 1 to make it 1 to 6.
        return random.nextInt(MAX_VALUE) + MIN_VALUE;
    }
}
