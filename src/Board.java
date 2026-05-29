import java.util.HashMap;
import java.util.Map;

public class Board {
    private static final int SIZE = 100;

    private final Map<Integer, Snake> snakes;
    private final Map<Integer, Ladder> ladders;

    public Board() {
        snakes = new HashMap<>();
        ladders = new HashMap<>();
        initializeSnakes();
        initializeLadders();
    }

    public int getFinalPosition(int position) {
        // HashMap lookup keeps snake and ladder checks fast and simple.
        if (ladders.containsKey(position)) {
            Ladder ladder = ladders.get(position);
            System.out.println("Great! Ladder from " + ladder.getStart() + " to " + ladder.getEnd() + ".");
            return ladder.getEnd();
        }

        if (snakes.containsKey(position)) {
            Snake snake = snakes.get(position);
            System.out.println("Oops! Snake from " + snake.getHead() + " to " + snake.getTail() + ".");
            return snake.getTail();
        }

        return position;
    }

    public int getSize() {
        return SIZE;
    }

    public Map<Integer, Snake> getSnakes() {
        return snakes;
    }

    public Map<Integer, Ladder> getLadders() {
        return ladders;
    }

    private void initializeSnakes() {
        addSnake(new Snake(99, 54));
        addSnake(new Snake(70, 55));
        addSnake(new Snake(52, 42));
        addSnake(new Snake(25, 2));
        addSnake(new Snake(95, 72));
    }

    private void initializeLadders() {
        addLadder(new Ladder(6, 25));
        addLadder(new Ladder(11, 40));
        addLadder(new Ladder(60, 85));
        addLadder(new Ladder(46, 90));
        addLadder(new Ladder(17, 69));
    }

    private void addSnake(Snake snake) {
        snakes.put(snake.getHead(), snake);
    }

    private void addLadder(Ladder ladder) {
        ladders.put(ladder.getStart(), ladder);
    }
}
