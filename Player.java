package Game;

/**
 * An interface which includes abstract methods needed for each player of the
 * game
 *
 * @author White, Robin
 */
public interface Player {

    /**
     * add to the players hand
     * @param c: Game.Card to add
     */
    void addCard(Card c);

    /**
     * add all the cards in h to the players hand
     * @param h: hand to add
     */
    void addHand(Hand h);

    /**
     * @return number of cards left in the players hand
     */
    int cardsLeft();

    /**
     * @param g: the player should contain a reference to the game it is
     *           playing in
     */
    void setGame(CardGame g);

    /**
     * @param s: the player should contain a reference to its strategy
     */
    void setStrategy(Strategy s);

    /**
     * @return the strategy of this player
     */
    Strategy getStrategy();

    /**
     * @return the hand of this player
     */
    Hand getHand();

    /**
     * Constructs a bid when asked to by the game
     * @param b: the last bid accepted by the game
     * @return the players bid
     */
    Bid playHand(Bid b);

    /**
     * Determines if the player has called the last player a cheat
     * @param b: the last players bid
     * @return true if calling the last player a cheat
     *         false if not calling the last player a cheat
     */
    boolean callCheat(Bid b);
}
