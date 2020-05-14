package Game;

import java.util.Random;

/**
 * An advanced cheat strategy:
 * 1. Decision on whether to cheat - The Thinker should cheat if it has to.
 *    It should also occasionally cheat when it doesn’t have to.
 * 2. Choose hand - If cheating, the Thinker is more likely to
 *    choose higher cards to discard than low cards. If not cheating, it
 *    usually plays all its cards but occasionally play a random number.
 * 3. Calling Cheat - The Thinker attempts to make an informed
 *    decision to call cheat on another player. It stores all of its own
 *    cards it has placed in the discard, then examine this record (in
 *    conjunction with the current hand) to decide on whether to call cheat. It
 *    always calls cheat if the bid is not possible based on previous
 *    known play. It should then call cheat with a small probability p (set as
 *    a parameter) dependent on how many of the current rank are in the
 *    current discard pile.
 *
 * @author White, Robin
 */
public class ThinkerStrategy implements Strategy {

    //Used to track what has been discarded, in order to identify if a
    //player has cheated.
    Hand discardHand = new Hand();

    /**
     * Determine whether to cheat, cheat if there are no suitable ranks to
     * follow the current bid, cheat 50% of the time randomly and don't
     * cheat if there is a suitable rank available
     * @param b   the current bid
     * @param h	  the players current hand
     * @return true if we want to cheat, false if we want to play fairly
     */
    @Override
    public boolean cheat(Bid b, Hand h) {
        Random rand = new Random();
        int  cheatChance = rand.nextInt(100) + 1;
        Card.Rank rank = b.getRank();

        //Cheats if there are no suitable ranks.
        if(h.countRank(rank) < 1 && h.countRank(rank.getNext()) < 1)
            return true;
        else {
            //Cheats 50% of the time, even if there is no need to.
            if(cheatChance > 50)
                return true;
            else
                return false;
        }
    }


    /**
     * Decides what bid to make when cheating and playing fairly, in the
     * case of playing a cheat, sort the cards,
     * @param b   the current bid
     * @param h	  the players current hand
     * @param cheat true if the Game.Strategy has decided to cheat
     *
     * @return the new bid
     */
    @Override
    public Bid chooseBid(Bid b, Hand h, boolean cheat) {
        h.sortDescending();

        if (cheat) {
            Random rand = new Random();
            int  shouldChooseHigh = rand.nextInt(100) + 1;

            //Generates random number in range of first half of the handSize.
            int n = rand.nextInt((h.handSize() / 2) + 1);
            Card card;

            //Choose the higher value cards 30% of the time.
            if (shouldChooseHigh > 70) {
                //Choose card in first half of hand.
                card = (Card) h.hand.toArray()[n];
            }
            else {
                //Choose card in seconds half of hand.
                card = (Card) h.hand.toArray()[h.handSize() - n - 1];
            }

            Hand newHand = new Hand();
            h.remove(card);
            newHand.add(card);
            discardHand.add(card);
            Bid newBid = new Bid(newHand, b.r.getNext());
            return newBid;
        }

        else {
            Random rand2 = new Random();
            int  shouldPlayAllCards = rand2.nextInt(100) + 1;

            Card.Rank lastRank = b.getRank();
            Card.Rank rank;

            if(h.countRank(lastRank) < h.countRank(lastRank.getNext()))
                rank = lastRank.getNext();
            else
                rank = lastRank;

            Hand newHand = new Hand();

            for (Object card : h) {
                Card newCard = (Card) card;
                if(newCard.getRank() == rank) {
                    newHand.add(newCard);
                    discardHand.add(newCard);
                }
            }

            //Removes a random amount of cards from the hand, 30% of the time.
            if(shouldPlayAllCards > 70) {
                Random rand3 = new Random();
                int  cardsToRemove = rand3.nextInt(newHand.handSize());

                //Remove random ammount of cards that are to be played.
                for (int i = 0; i<cardsToRemove; i++)
                {
                    newHand.remove(i);
                }
            }

            h.remove(newHand);
            Bid newBid = new Bid(newHand, rank);
            return newBid;
        }
    }

    @Override
    public boolean callCheat(Hand h, Bid b) {
        int numOfRank = h.countRank(b.getRank());
        int cardsBid = b.getCount();
        boolean shouldCheat = false;

        //Calculates a probability based on how many of a certain rank are in
        //the discard hand. Used to decide if should cheat.
        if(discardHand.handSize() != 0) {
            int  probability = ((discardHand.countRank(b.r) + 1) + 1
                    / discardHand.handSize()) * 100;

            Random rand = new Random();
            shouldCheat = rand.nextInt(100)+1 <= probability;
        }

        if ((cardsBid + numOfRank) > 4)
            return true;
            //If its obvious they've cheated based on the discards.
        else if (discardHand.countRank(b.r) + b.h.handSize() > 4)
            return true;
        else if (shouldCheat)
            return true;
        return false;
    }

    /**
     * Removes all card from the discard hand.
     */
    public void resetDiscardHand() {
        discardHand.remove(discardHand);
    }
}
