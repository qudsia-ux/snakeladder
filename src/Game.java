import java.util.List;

public class Game {
    private static final int WINNING_POSITION = 100;

    private final Board board;
    private final Dice dice;
    private final List<Player> players;
    private int currentPlayerIndex;
    private boolean gameWon;

    public Game(Board board, Dice dice, List<Player> players) {
        this.board = board;
        this.dice = dice;
        this.players = players;
        this.currentPlayerIndex = 0;
        this.gameWon = false;
    }

    public void play() {
        System.out.println("\nGame started!");
        showAllPlayerPositions();

        // Continue rotating through players until one player reaches exactly 100.
        while (!gameWon) {
            playNextTurn();
        }
    }

    public TurnResult playNextTurn() {
        if (gameWon) {
            Player winner = players.get(currentPlayerIndex);
            return new TurnResult(winner, 0, winner.getPosition(), winner.getPosition(),
                    winner.getPosition(), false, true);
        }

        Player currentPlayer = players.get(currentPlayerIndex);
        TurnResult result = playTurn(currentPlayer);
        gameWon = result.hasWon();

        if (!gameWon) {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }

        return result;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    private TurnResult playTurn(Player player) {
        int diceValue = dice.roll();
        int currentPosition = player.getPosition();
        int expectedPosition = currentPosition + diceValue;
        int finalPosition = currentPosition;

        System.out.println("\n" + player.getName() + "'s turn");
        System.out.println(player.getName() + " rolled a " + diceValue + ".");

        if (expectedPosition > WINNING_POSITION) {
            // Exact win rule: a move beyond 100 is ignored.
            System.out.println(player.getName() + " needs exactly "
                    + (WINNING_POSITION - currentPosition) + " to win, so they stay at "
                    + currentPosition + ".");
            showAllPlayerPositions();
            return new TurnResult(player, diceValue, currentPosition, expectedPosition,
                    finalPosition, true, false);
        }

        player.setPosition(expectedPosition);
        System.out.println(player.getName() + " moved from " + currentPosition + " to " + expectedPosition + ".");

        // After the normal dice move, check whether the player hit a snake or ladder.
        finalPosition = board.getFinalPosition(expectedPosition);
        if (finalPosition != expectedPosition) {
            player.setPosition(finalPosition);
            System.out.println(player.getName() + " is now at " + finalPosition + ".");
        }

        showAllPlayerPositions();

        if (player.getPosition() == WINNING_POSITION) {
            System.out.println("\nCongratulations! " + player.getName() + " wins the game!");
            return new TurnResult(player, diceValue, currentPosition, expectedPosition,
                    finalPosition, false, true);
        }

        return new TurnResult(player, diceValue, currentPosition, expectedPosition,
                finalPosition, false, false);
    }

    private void showAllPlayerPositions() {
        System.out.println("\nCurrent Positions:");
        for (Player player : players) {
            System.out.println(player.getName() + " -> " + player.getPosition());
        }
    }

    public static class TurnResult {
        private final Player player;
        private final int diceValue;
        private final int startPosition;
        private final int attemptedPosition;
        private final int finalPosition;
        private final boolean moveSkipped;
        private final boolean won;

        public TurnResult(Player player, int diceValue, int startPosition, int attemptedPosition,
                int finalPosition, boolean moveSkipped, boolean won) {
            this.player = player;
            this.diceValue = diceValue;
            this.startPosition = startPosition;
            this.attemptedPosition = attemptedPosition;
            this.finalPosition = finalPosition;
            this.moveSkipped = moveSkipped;
            this.won = won;
        }

        public Player getPlayer() {
            return player;
        }

        public int getDiceValue() {
            return diceValue;
        }

        public int getStartPosition() {
            return startPosition;
        }

        public int getAttemptedPosition() {
            return attemptedPosition;
        }

        public int getFinalPosition() {
            return finalPosition;
        }

        public boolean isMoveSkipped() {
            return moveSkipped;
        }

        public boolean hasWon() {
            return won;
        }
    }
}
