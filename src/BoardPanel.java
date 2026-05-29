import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

public class BoardPanel extends JPanel {
    private static final int GRID_SIZE = 10;
    private static final int PADDING = 24;
    private static final Color[] TOKEN_COLORS = {
        new Color(220, 38, 38),
        new Color(37, 99, 235),
        new Color(22, 163, 74),
        new Color(234, 88, 12),
        new Color(147, 51, 234),
        new Color(8, 145, 178)
    };

    private final Board board;
    private final List<Player> players;

    public BoardPanel(Board board, List<Player> players) {
        this.board = board;
        this.players = players;
        setPreferredSize(new Dimension(700, 700));
        setBackground(new Color(245, 247, 250));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int boardSize = Math.min(getWidth(), getHeight()) - (PADDING * 2);
        int startX = (getWidth() - boardSize) / 2;
        int startY = (getHeight() - boardSize) / 2;
        int cellSize = boardSize / GRID_SIZE;
        int actualBoardSize = cellSize * GRID_SIZE;

        drawCells(g2, startX, startY, cellSize);
        drawLadders(g2, board.getLadders(), startX, startY, cellSize);
        drawSnakes(g2, board.getSnakes(), startX, startY, cellSize);
        drawPlayers(g2, startX, startY, cellSize);

        g2.setColor(new Color(17, 24, 39));
        g2.setStroke(new BasicStroke(3f));
        g2.drawRect(startX, startY, actualBoardSize, actualBoardSize);
        g2.dispose();
    }

    private void drawCells(Graphics2D g2, int startX, int startY, int cellSize) {
        g2.setFont(new Font("SansSerif", Font.BOLD, 13));
        FontMetrics metrics = g2.getFontMetrics();

        for (int position = 1; position <= board.getSize(); position++) {
            Point cell = getCellTopLeft(position, startX, startY, cellSize);
            boolean lightCell = ((cell.x + cell.y) / cellSize) % 2 == 0;

            g2.setColor(lightCell ? new Color(240, 253, 244) : new Color(219, 234, 254));
            g2.fillRect(cell.x, cell.y, cellSize, cellSize);
            g2.setColor(new Color(148, 163, 184));
            g2.drawRect(cell.x, cell.y, cellSize, cellSize);

            String number = String.valueOf(position);
            g2.setColor(new Color(31, 41, 55));
            g2.drawString(number, cell.x + 6, cell.y + metrics.getAscent() + 4);
        }
    }

    private void drawLadders(Graphics2D g2, Map<Integer, Ladder> ladders, int startX, int startY, int cellSize) {
        g2.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        for (Ladder ladder : ladders.values()) {
            Point start = getCellCenter(ladder.getStart(), startX, startY, cellSize);
            Point end = getCellCenter(ladder.getEnd(), startX, startY, cellSize);

            drawLadder(g2, start, end);
        }
    }

    private void drawLadder(Graphics2D g2, Point start, Point end) {
        double dx = end.x - start.x;
        double dy = end.y - start.y;
        double length = Math.max(1, Math.hypot(dx, dy));
        double offsetX = -dy / length * 8;
        double offsetY = dx / length * 8;

        int x1a = (int) Math.round(start.x + offsetX);
        int y1a = (int) Math.round(start.y + offsetY);
        int x1b = (int) Math.round(end.x + offsetX);
        int y1b = (int) Math.round(end.y + offsetY);
        int x2a = (int) Math.round(start.x - offsetX);
        int y2a = (int) Math.round(start.y - offsetY);
        int x2b = (int) Math.round(end.x - offsetX);
        int y2b = (int) Math.round(end.y - offsetY);

        g2.setColor(new Color(120, 53, 15));
        g2.drawLine(x1a, y1a, x1b, y1b);
        g2.drawLine(x2a, y2a, x2b, y2b);

        g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(new Color(180, 83, 9));
        for (int i = 1; i <= 5; i++) {
            double t = i / 6.0;
            int railAX = (int) Math.round(x1a + (x1b - x1a) * t);
            int railAY = (int) Math.round(y1a + (y1b - y1a) * t);
            int railBX = (int) Math.round(x2a + (x2b - x2a) * t);
            int railBY = (int) Math.round(y2a + (y2b - y2a) * t);
            g2.drawLine(railAX, railAY, railBX, railBY);
        }
        g2.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    }

    private void drawSnakes(Graphics2D g2, Map<Integer, Snake> snakes, int startX, int startY, int cellSize) {
        Stroke oldStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        for (Snake snake : snakes.values()) {
            Point head = getCellCenter(snake.getHead(), startX, startY, cellSize);
            Point tail = getCellCenter(snake.getTail(), startX, startY, cellSize);
            int midX = (head.x + tail.x) / 2 + (head.y - tail.y) / 5;
            int midY = (head.y + tail.y) / 2 + (tail.x - head.x) / 5;

            g2.setColor(new Color(21, 128, 61));
            g2.drawLine(head.x, head.y, midX, midY);
            g2.drawLine(midX, midY, tail.x, tail.y);

            g2.setColor(new Color(22, 101, 52));
            int headSize = Math.max(14, cellSize / 4);
            g2.fillOval(head.x - headSize / 2, head.y - headSize / 2, headSize, headSize);
            g2.setColor(new Color(248, 250, 252));
            g2.fillOval(head.x - 4, head.y - 3, 3, 3);
            g2.fillOval(head.x + 2, head.y - 3, 3, 3);
        }

        g2.setStroke(oldStroke);
    }

    private void drawPlayers(Graphics2D g2, int startX, int startY, int cellSize) {
        int tokenSize = Math.max(16, cellSize / 4);
        int[][] offsets = {
            {-12, -12}, {12, -12}, {-12, 12}, {12, 12}, {0, -18}, {0, 18}
        };

        g2.setFont(new Font("SansSerif", Font.BOLD, 10));

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            Point center = player.getPosition() == 0
                    ? new Point(startX + (i * (tokenSize + 8)) + tokenSize, startY + (GRID_SIZE * cellSize) + 18)
                    : getCellCenter(player.getPosition(), startX, startY, cellSize);

            int offsetX = player.getPosition() == 0 ? 0 : offsets[i][0];
            int offsetY = player.getPosition() == 0 ? 0 : offsets[i][1];
            int x = center.x + offsetX - tokenSize / 2;
            int y = center.y + offsetY - tokenSize / 2;

            g2.setColor(TOKEN_COLORS[i]);
            g2.fill(new Ellipse2D.Double(x, y, tokenSize, tokenSize));
            g2.setColor(Color.WHITE);
            g2.drawString(String.valueOf(i + 1), x + tokenSize / 3, y + (tokenSize * 2 / 3));
            g2.setColor(new Color(17, 24, 39));
            g2.drawOval(x, y, tokenSize, tokenSize);
        }
    }

    private Point getCellCenter(int position, int startX, int startY, int cellSize) {
        Point topLeft = getCellTopLeft(position, startX, startY, cellSize);
        return new Point(topLeft.x + cellSize / 2, topLeft.y + cellSize / 2);
    }

    private Point getCellTopLeft(int position, int startX, int startY, int cellSize) {
        int zeroBased = position - 1;
        int rowFromBottom = zeroBased / GRID_SIZE;
        int column = zeroBased % GRID_SIZE;

        if (rowFromBottom % 2 == 1) {
            column = GRID_SIZE - 1 - column;
        }

        int row = GRID_SIZE - 1 - rowFromBottom;
        return new Point(startX + column * cellSize, startY + row * cellSize);
    }
}
