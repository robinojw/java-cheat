package Game;

/**
 * An interface for the basic strategy methods of each play, determines whether
 * the player wants to cheat, what bid they'd like to make and whether they
 * want to call cheat on the current bid
 *
 * @author White, Robin
 */
public interface Strategy {

    /**
     * Decides on whether to cheat or play fairly
     * @param b   the current bid
     * @param h	  the players current hand
     * @return true if the player wants to cheat, false if not
     */
    public boolean cheat(Bid b, Hand h);

    /**
     * @param b   the current bid
     * @param h	  the players current hand
     * @param cheat true if the Game.Strategy has decided to cheat
     *
     * @return a new bid
     */
    public Bid chooseBid(Bid b, Hand h, boolean cheat);

    /**
     *
     * @param h current hand
     * @param b the current bid
     * @return true if the player wants to call cheat on the current bid
     */

    public boolean callCheat(Hand h,Bid b);
}
