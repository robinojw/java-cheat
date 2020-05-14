package Game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Class to model a players hand
 *
 * @author White, Robin
 */
public class Hand implements Serializable, Iterable {
    static final long serialVersionUID = 102;

    Collection<Card> hand = new ArrayList<>();
    private int[] rankCount;
    private int[] suitCount;
    private int handValue;

    //Create a new instance of hand
    public Hand() {
        this.suitCount = new int[13];
        this.rankCount = new int[13];
        this.handValue = 0;
        this.hand = new ArrayList();
        serializeHand();
    }

    /**
     * Creates a new instance of Game.Hand and fills it with a given array of cards
     * @param cards
     */
    public Hand(Card[] cards) {
        this.suitCount = new int[13];
        this.rankCount = new int[13];
        this.handValue = 0;
        this.hand = new ArrayList();
        for (Card card : cards) {
            hand.add(card);
        }
        serializeHand();
    }

    /**
     * Creates a new instance of Game.Hand and adds a given hand to the current
     * hand
     * @param newHand
     */
    public Hand(Hand newHand) {
        this.suitCount = new int[13];
        this.rankCount = new int[13];
        this.handValue = 0;
        this.hand = new ArrayList();
        for (Object card : newHand) {
            hand.add((Card) card);
        }
        serializeHand();
    }

    /**
     * Serialize the hand and store it in a .ser file.
     */
    public void serializeHand() {
        String filename = "hand.ser";

        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fos);

            ArrayList<Card> serializedHand = new ArrayList<>();
            Iterator itr = hand.iterator();
            while(itr.hasNext()) {
                Card card = (Card) itr.next();
                serializedHand.add(card);
            }
            out.writeObject(this);
            out.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Add a single card to the hand
     * @param card - card to be added
     */
    public void add(Card card) {
        increaseCounts(card); //Update counts
        hand.add(card);
    }

    /**
     * Add a collection of cards to the hand
     * @param cardCollection - collection of cards to be added
     */
    public void add(Collection<Card> cardCollection) {
        for (Card card : cardCollection) {
            increaseCounts(card);  //Update counts
            hand.add(card);
        }
    }

    /**
     * Add a given hand to the current hand
     * @param newHand - hand to be added
     */
    public void add(Hand newHand) {
        //Loop through new hand and add each one to the current hand
        for (Object card : newHand) {
            Card newCard = (Card) card;
            increaseCounts(newCard);   //Update counts
            hand.add(newCard);
        }
    }


    /**
     * Remove a given card from the hand.
     * @param dealtCard //Game.Card to be removed
     * @return true if given card is successfully removed.
     *         false if given card isn't removed.
     */
    public boolean remove(Card dealtCard) {
        for (Card card : hand) {
            //Loop through hand and remove matching cards
            if(dealtCard == card) {
                decreaseCounts(card); //Decrease counts by 1
                hand.remove(card);
                return true;
            }
        }
        return false;
    }

    /**
     * Remove all cards that match a given hand from the current hand
     * @param givenHand //Game.Hand to be removed
     * @return true if all matching cards from the given hand were removed,
     *         false if the given hand was not removed.
     */
    public boolean remove(Hand givenHand) {
        int removedCards = 0;

        //loop through hand, if hand contains givenHand,
        //remove it and update the counts
        for (Object card : givenHand) {
            Card givenCard = (Card) card;
            if(hand.contains(givenCard)) {
                decreaseCounts(givenCard);
                hand.remove(givenCard);
                removedCards++;
            }
        }
        //Only return true if all cards given were removed.
        if (removedCards == givenHand.handSize())
            return true;
        return false;
    }

    /**
     * Remove a card at the given index in the current hand
     * @param givenIndex
     * @return the deleted card or null if the card wasn't found.
     */
    public Card remove(int givenIndex) {
        int index = 0;
        //Loop through hand, if index is equal to the given index
        //remove the card in that position then update counts
        for (Card card : hand) {
            if(index == givenIndex) {
                decreaseCounts(card);
                hand.remove(card);
                return card;
            }
            index++;
        }
        return null;
    }

    /**
     * Increase the rank, suit and hand values when a new card is added
     * @param card  //That has been added to the hand
     */
    public void increaseCounts(Card card) {
        rankCount[card.getRank().ordinal()]++;
        suitCount[card.getSuit().ordinal()]++;
        handValue += card.getRank().getValue();
    }

    /**
     * Decrease the rank, suit and hand values when a card is removed from the hand
     * @param card  //That has been removed from the hand
     */
    public void decreaseCounts(Card card) {
        rankCount[card.getRank().ordinal()]--;
        suitCount[card.getSuit().ordinal()]--;
        handValue -= card.getRank().getValue();
    }

    /**
     * An iterator that traverses the deck in the current order
     * @return instance of the iterator.
     */
    @Override
    public Iterator<Card> iterator() {
        Iterator<Card> it = new Iterator<Card>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                if (currentIndex < hand.size())
                    return true;
                return false;
            }

            @Override
            public Card next() {
                if (hasNext())
                    return (Card) hand.toArray()[currentIndex++];
                return null;
            }
        };
        return it;
    }


     //Sort the hand into ascending order by suit
    public void sortAscending() {
        Comparator cmpSuit = new Card.compareSuit();
        Collections.sort((ArrayList<Card>) hand, cmpSuit);
    }


     //Sort the hand into descending order by rank
    public void sortDescending() {
        Comparator cmpDecending = new Card.compareDescending();
        Collections.sort((ArrayList<Card>) hand, cmpDecending);
    }

    //Sort the hand into suit order
    public void sortSuit(){
        Collections.sort((ArrayList<Card>) hand, new Card.compareSuit());
    }

    /**
     * Count the instances of a given suit in the current hand
     * @param givenSuit
     * @return total count of the given suit in the current hand
     */
    public int countSuit(Card.Suit givenSuit) {
        int suitCount = 0;
        //Loop though hand, if the cards suit at the current index
        //matches that of the given suit, add to the count
        for (Card card : hand) {
            Card.Suit suit = card.getSuit();
            if(givenSuit == suit)
                suitCount++;
        }
        return suitCount;
    }

    /**
     * Count the instances of a given rank in the current hand
     * @param givenRank
     * @return total count of the given rank in the hand
     */
    public int countRank(Card.Rank givenRank) {
        int rankCount = 0;
        //Loop through hand, if the cards rank at the current index
        //matches the given rank, increase the rank count
        for (Card card : hand) {
            Card.Rank rank = card.getRank();
            if(givenRank == rank)
                rankCount++;
        }
        return rankCount;
    }

    /**
     * @return the value of the given hand
     */
    public int getHandValue() {
        return this.handValue;
    }

    /**
     * Method to calculate the current value of the hand
     * @return total value of the hand
     */
    public int handValue() {
        int totalValue = 0;
        //Loop through the hand, add the rank value to the total value
        for (Card card : hand) {
            int rankValue = card.getRank().getValue();
            totalValue += rankValue;
        }
        return totalValue;
    }

    /**
     * Method to determine whether all cards in the hand are the same suit
     * @return true if flush.
     *         false if not flush.
     */
    public boolean isFlush() {
        //null check hand
        if (hand.isEmpty()) {
            return false;
        }
        else {
            Card card = (Card) hand.toArray()[0];
            Card.Suit suit = card.getSuit(); //Get first suit

            //Loop through hand, if all cards are of the same suit return true,
            //else return false
            for(int i = 1; i < hand.size() - 1; i++)
            {
                Card nextCard = (Card) hand.toArray()[i];
                Card.Suit nextSuit = nextCard.getSuit();
                if(suit != nextSuit) //Compare current suit with next suit
                    return false;
            }
            return true;
        }
    }

    /**
     * Method to determine if the current hand is in consecutive order
     * @return true if straight.
     *         false if not straight.
     */
    public boolean isStraight() {
        Collections.sort((ArrayList<Card>) hand);

        //Loop through hand
        for(int i = 0; i < hand.size() - 1; i++)
        {
            Card card = (Card) hand.toArray()[i];
            Card nextCard = (Card) hand.toArray()[i+1];

            //Return false if card ranks aren't consecutive
            if (card.getRank().ordinal() != nextCard.getRank().ordinal() + 1)
                return false;
        }
        return true;
    }

    /**
     * @return size of this hand.
     */
    public int handSize() {
        return hand.size();
    }

    /**
     * Deserialize's a hand from file and recreates it in memory,
     * allows for iteration of the hand in the order it was originally created
     * @param filename
     * @return Game.Hand - Game.Hand that was stored in the .ser file
     */
    public Hand deserializeHand(String filename) {
        Hand serializedHand = null;
        //Restore serialized hand to memory
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))){
            serializedHand = (Hand)ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return serializedHand;
    }

    /**
     * Appends each card in the hand to a string and returns it
     */
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();

        for (Card card : hand) {
            str.append(card).append("\n");
        }

        return str.toString();
    }
}
