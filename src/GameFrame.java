import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GameFrame extends JFrame {
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 6;

    private final Board board;
    private final Dice dice;
    private final Game game;
    private final BoardPanel boardPanel;
    private final JLabel turnLabel;
    private final JLabel diceLabel;
    private final JLabel statusLabel;
    private final JButton rollButton;

    public GameFrame() {
        List<Player> players = createPlayers();
        board = new Board();
        dice = new Dice();
        game = new Game(board, dice, players);

        setTitle("Snake and Ladder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(12, 12));
        getContentPane().setBackground(new Color(245, 247, 250));

        boardPanel = new BoardPanel(board, game.getPlayers());
        turnLabel = new JLabel();
        diceLabel = new JLabel("Dice: -");
        statusLabel = new JLabel("Roll the dice to begin.", SwingConstants.CENTER);
        rollButton = new JButton("Roll Dice");

        add(createHeader(), BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.SOUTH);

        rollButton.addActionListener(event -> rollDice());
        updateTurnLabel();

        pack();
        setMinimumSize(new Dimension(760, 820));
        setLocationRelativeTo(null);
    }

    private List<Player> createPlayers() {
        int playerCount = askPlayerCount();
        List<Player> players = new ArrayList<>();

        for (int i = 1; i <= playerCount; i++) {
            String name = JOptionPane.showInputDialog(
                    null,
                    "Enter name for Player " + i + ":",
                    "Player Setup",
                    JOptionPane.PLAIN_MESSAGE);

            if (name == null || name.trim().isEmpty()) {
                name = "Player " + i;
            }

            players.add(new Player(name.trim()));
        }

        return players;
    }

    private int askPlayerCount() {
        while (true) {
            String input = JOptionPane.showInputDialog(
                    null,
                    "Enter number of players (2 to 6):",
                    "Snake and Ladder",
                    JOptionPane.QUESTION_MESSAGE);

            if (input == null) {
                System.exit(0);
            }

            try {
                int count = Integer.parseInt(input.trim());
                if (count >= MIN_PLAYERS && count <= MAX_PLAYERS) {
                    return count;
                }
            } catch (NumberFormatException e) {
                // The dialog below gives the user a clear retry path.
            }

            JOptionPane.showMessageDialog(
                    null,
                    "Please enter a number between 2 and 6.",
                    "Invalid Player Count",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout(8, 8));
        header.setBackground(new Color(31, 41, 55));
        header.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 18));

        JLabel titleLabel = new JLabel("Snake and Ladder");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));

        turnLabel.setForeground(new Color(219, 234, 254));
        turnLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        turnLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        header.add(titleLabel, BorderLayout.WEST);
        header.add(turnLabel, BorderLayout.EAST);
        return header;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 14, 14, 14));

        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 24, 6));
        topRow.setOpaque(false);

        diceLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        rollButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        rollButton.setFocusPainted(false);

        topRow.add(diceLabel);
        topRow.add(rollButton);

        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        statusLabel.setForeground(new Color(55, 65, 81));

        panel.add(topRow, BorderLayout.NORTH);
        panel.add(statusLabel, BorderLayout.SOUTH);
        return panel;
    }

    private void rollDice() {
        if (game.isGameWon()) {
            return;
        }

        Game.TurnResult result = game.playNextTurn();
        diceLabel.setText("Dice: " + result.getDiceValue());
        statusLabel.setText(buildStatusText(result));
        boardPanel.repaint();

        if (result.hasWon()) {
            rollButton.setEnabled(false);
            updateTurnLabel();
            JOptionPane.showMessageDialog(
                    this,
                    "Congratulations! " + result.getPlayer().getName() + " wins the game!",
                    "Winner",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            updateTurnLabel();
        }
    }

    private String buildStatusText(Game.TurnResult result) {
        Player player = result.getPlayer();

        if (result.isMoveSkipped()) {
            return player.getName() + " rolled " + result.getDiceValue()
                    + " and needs exactly " + (board.getSize() - result.getStartPosition()) + " to win.";
        }

        if (result.getAttemptedPosition() != result.getFinalPosition()) {
            String moveType = result.getFinalPosition() > result.getAttemptedPosition() ? "climbed a ladder" : "met a snake";
            return player.getName() + " rolled " + result.getDiceValue() + ", moved to "
                    + result.getAttemptedPosition() + ", then " + moveType + " to "
                    + result.getFinalPosition() + ".";
        }

        return player.getName() + " rolled " + result.getDiceValue() + " and moved from "
                + result.getStartPosition() + " to " + result.getFinalPosition() + ".";
    }

    private void updateTurnLabel() {
        if (game.isGameWon()) {
            turnLabel.setText("Game over");
        } else {
            turnLabel.setText("Turn: " + game.getCurrentPlayer().getName());
        }
    }
}
