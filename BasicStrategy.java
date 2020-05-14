package Game;

import java.util.Random;

/**
 * The basic strategy of playing the game.
 * 1. Never cheat unless you have to. If a cheat is required, play a
 *    single card selected randomly.
 * 2. If not cheating, always play the maximum number of cards
 *    possible of the lowest rank possible.
 * 3. Call another player a cheat only when certain they are cheating.
 *
 * @author White, Robin
 * */

public class BasicStrategy implements Strategy {

    /**
     * Determine whether the player has to cheat with their current hand
     * @param b current bid
     * @param h current hand
     * @return true of the player has to cheat, false if they can play truthfully
     */
    @Override
    public boolean cheat(Bid b, Hand h) {
        Card.Rank rank = b.getRank();
        if(h.countRank(rank) < 1 && h.countRank(rank.getNext()) < 1)
            return true;
        return false;
    }

    /**
     * Method to choose the players next bid, if they have to cheat, choose a random card to
     * cheat with, remove it from the hand and make a new false bid. Otherwise play a card from
     * the current hand.
     * @param b current bid
     * @param h current hand
     * @param cheat to determine whether the player needs to cheat
     * @return a cheat of valid bid
     */
    @Override
    public Bid chooseBid(Bid b, Hand h, boolean cheat) {
        if (cheat) {
            Random rand = new Random();
            int  n = rand.nextInt(h.handSize());

            //Choose random card to cheat with.
            Card card = (Card) h.hand.toArray()[n];
            Hand newHand = new Hand();

            h.remove(card);
            newHand.add(card);

            //Adds wrong card to hand but claims it was the correct rank.
            Bid newBid = new Bid(newHand, b.r.getNext());
            return newBid;
        }
        else
        {
            Card.Rank lastRank = b.getRank();
            Card.Rank rank;

            //Choose higher of two ranks to play.
            if(h.countRank(lastRank) < h.countRank(lastRank.getNext()))
                rank = lastRank.getNext();
            else
                rank = lastRank;

            Hand newHand = new Hand();

            for (Object card : h) {
                Card newCard = (Card) card;
                if(newCard.getRank() == rank)
                    newHand.add(newCard);
            }

            h.remove(newHand);
            Bid newBid = new Bid(newHand, rank);
            return newBid;
        }
    }

    /**
     * Only call cheat if the current bid is impossible due to the cards
     * in the current hand and the cards that have been played
     * @param h current hand
     * @param b current bid
     * @return true if the player is cheating
     */
    @Override
    public boolean callCheat(Hand h, Bid b) {
        int numOfRank = h.countRank(b.getRank());
        int cardsBid = b.getCount();
        //If the cards in the players hand and the humans hand of the same rank
        //are greater than four, they must be cheating.
        return (cardsBid + numOfRank) > 4;
    }
}
