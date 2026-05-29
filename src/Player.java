public class Player {
    private final String name;
    private int position;

    public Player(String name) {
        this.name = name;
        this.position = 0;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        if (position < 0 || position > 100) {
            throw new IllegalArgumentException("Position must be between 0 and 100.");
        }
        this.position = position;
    }
}
