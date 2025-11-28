/**
 * The type Player.
 */
public class Player {
    private String name;
    private int score = 0;
    /**
     * The Owned tiles.
     */
    public String[] ownedTiles = new String[7];

    /**
     * Instantiates a new Player.
     */
    public Player() {
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets score.
     *
     * @param score the score
     */
    public void setScore(int score) {
        this.score = score;
    }
}
