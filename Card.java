package Game;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author White, Robin
 */
public class Card implements Comparable<Card>, Serializable {

    private static final long serialVersionUID = 100;

    private Suit suit;
    private Rank rank;

    public enum Rank {

        // Values for the rank of a card
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10),
        ACE(11);

        public int value;

        // Constructor for a card rank
        Rank(int value) {
            this.value = value;
        }

        // Gets the value of a card
        public int getValue() {
            return value;
        }

        // Gets the next rank in the Rank enum
        public Rank getNext() {
            if (this.equals(Rank.ACE))
                return Rank.TWO;
            else
                return Rank.values()[this.ordinal() + 1];
        }
    }

    // Values for the suit of a card
    enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES
    }

    // Constructor with Rank and Suit passed as parameters
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    //Constructor for an empty card
    public Card(){}

    // Accessor methods
    public Rank getRank() {
        return this.rank;
    }
    public Suit getSuit() {
        return this.suit;
    }

    /**
     * Compares the current card and a given cards rank and suit
     * @param card
     * @return return 0 if the cards are the same, otherwise return int cardComp
     */
    @Override
    public int compareTo(Card card) {
        int cardComp = this.suit.compareTo(card.suit);
        if (cardComp == 0) {
            cardComp = this.rank.compareTo(card.rank);
        }
        return cardComp;
    }

    /**
     * Calculates the difference in rank between two cards
     * @param card1
     * @param card2
     * @return int - rank difference
     */
    public static int difference(Card card1, Card card2) {
        //Retrieves the Rank enum index's and calculates the difference.
        return Math.abs(card1.rank.ordinal() - card2.rank.ordinal());
    }

    /**
     * Calculates the difference in values between two cards
     * @param card1
     * @param card2
     * @return int value difference.
     */
    public static int differenceValue(Card card1, Card card2) {
        //Calculates the absolute value after calculating the value difference
        return Math.abs(card1.rank.getValue() - card2.rank.getValue());
    }

    /**
     * A comparator nested class that sorts the cards into descending order by Rank
     */
    public static class compareDescending implements Comparator {

        /**
         * Compares the two cards by their rank index
         * @param a
         * @param b
         * @return int - difference in cards rank
         */
        @Override
        public int compare(Object a, Object b){
            return ((Card)b).rank.ordinal()- ((Card)a).rank.ordinal();
        }
    }

    /**
     * A comparator nested class that sorts the cards into ascending order by Suit
     */
    public static class compareSuit implements Comparator {

        /**
         * Compares two given cards.
         * @param a
         * @param b
         * @return int - difference in card values.
         */
        @Override
        public int compare(Object a, Object b){
            return ((Card)a).compareTo((Card)b);
        }
    }

    /**
     * Returns card as a string
     */
    @Override
    public String toString(){
        return rank + " of " + suit;
    }
}
