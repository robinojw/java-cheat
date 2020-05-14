package Game;

/**
 * An interface which includes three abstract methods needed in order to play a
 * game of cheat.
 *
 * @author White, Robin
 */
public interface CardGame {

    /**
     * Initialise the card game
     */
    public void initialise();

    /**
     * Plays a single turn of the game
     * @return true if play made
     */
    public boolean playTurn();

    /**
     * Determines who the winner of the game is.
     * @return an integer representing the winner
     */
    public int winner();
}
