package Game;

/**
 * Models a bid made by a player
 *
 * @author White, Robin
 */
public class Bid {
    Hand h;
    Card.Rank r;

    /**
     * Creates a new instance of Game.Bid if the player has cheated.
     */
    public Bid(){
        h=new Hand();
        r=Card.Rank.TWO; //Changed from ACE to TWO to match game rules.
    }

    /**
     * Creates a new instance of Game.Bid using a given hand and rank
     * @param hand
     * @param bidRank
     */
    public Bid(Hand hand,Card.Rank bidRank){
        h=hand;
        r=bidRank;
    }

    /**
     * Set the given hand
     */
    public void setHand(Hand hand){ h=hand;}

    /**
     * Set the given rank
     * @param rank
     */
    public void setRank(Card.Rank rank){ r=rank;}

    /**
     * @return the hand of this bid
     */
    public Hand getHand(){ return h;}

    /**
     * @return the size of the hand of this bid
     */
    public int getCount(){ return h.handSize();}

    /**
     * @return the rank of this bid
     */
    public Card.Rank getRank(){ return r;}

    /**
     * @return the bid as a string
     */
    public String toString(){ return h.handSize()+" x "+r; }
}
