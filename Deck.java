package Game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Class that models a deck of 52 cards, including methods to iterate through and
 * manipulate/access the deck
 *
 * @author White, Robin
 */
public class Deck implements Serializable, Iterable {
    static final long serialVersionUID = 101;

    //Initialize deck
    private final ArrayList<Card> deck = new ArrayList<>();

    /**
     * Create a new deck and generate all 52 cards
     * @throws java.io.IOException
     */
    public Deck() throws IOException{
        //Fill the deck with cards
        for (Card.Suit suit: Card.Suit.values()){
            for (Card.Rank rank : Card.Rank.values()){
                deck.add(new Card(rank, suit));
            }
        }

        //Output current newly generated deck to a serialized file
        String filename = "deck.ser";

        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            ArrayList<Card> serializedDeck = new ArrayList<>();

            Iterator itr = deck.iterator(); //Iterator to traverse the deck

            //Iterate through deck of cards and add each one to the serialized array list
            while(itr.hasNext()) {
                Card card = (Card) itr.next();
                serializedDeck.add(card);
            }
            //Write serialized deck to file
            out.writeObject(this);
            out.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Accessor method for the current deck
     * @return this deck.
     */
    public ArrayList<Card> getDeck(){
        return this.deck;
    }

    /**
     * Shuffles the deck, randomising the order of all 52 cards
     */
    public void shuffle(){
        int size = deck.size();
        Random random = new Random();
        random.nextInt();

        //Loop through deck and swap elements into randomly generated positions
        for (int i = 0; i < size; i++) {
            //add the current position to the next integer from the
            //random generator within the range size - i to create a random position
            int change = i + random.nextInt(size - i);

            // Swap the elements of the deck
            Card temp = deck.get(i);
            deck.set(i, deck.get(change));
            deck.set(change, temp);
        }
    }

    /**
     * Removes the top card from the deck and returns it
     * @return Game.Card - Game.Card to be removed
     */
    public Card deal(){
        return deck.remove(0);
    }

    /**
     * @return the size of the current deck
     */
    public int size(){
        return deck.size();
    }

    /**
     * @return a new instance of a Game.Deck
     * @throws java.io.IOException
     */
    public Deck newDeck() throws IOException{
        return new Deck();
    }

    /**
     * Nested Iterator to traverse the deck as it would be dealt
     * @return dealIterator
     */
    @Override
    public Iterator<Card> iterator() {
        return new Deck.dealIterator();
    }

    /**
     * Deal iterator to simulate the order a deck of cards would be
     * dealt to a location, starting with the top of the deck and
     * iterating to the back
     */
    private class dealIterator implements Iterator<Card> {
        //Set the start index to the card at the top of the deck
        int index = deck.size();
        /**
         * hasNext method for dealIterator
         * @return true or false
         */
        @Override
        public boolean hasNext() {
            //If index is equal to 0, return false
            if (index >= 1) {
                return true;

            }
            return false;
        }
        /**
         * Get the next card in the iteration
         * @return next Card
         */
        @Override
        public Card next() {
            index--;
            return deck.get(index);

        }
        //Removes the card at the current iteration
        @Override
        public void remove(){
            deck.remove(index);
        }

    }


    /**
     * Nested iterator that traverses all odd positions in the deck,
     * then goes through all even positions.
     * @return new instance of the OddEvenIterator
     */
    public Iterator<Card> oddEvenIterator() {
        return new OddEvenIterator(deck);
    }

    private static class OddEvenIterator implements Iterator<Card> {
        private int nextCard;
        private int oddEven;
        private ArrayList<Card> deck;

        public OddEvenIterator(ArrayList<Card> deck)
        {
            this.deck = deck;
            this.oddEven = 0;
            this.nextCard = -2; //Start at -2 so next will return 0.
        }

        @Override
        public boolean hasNext() {
            if (nextCard < deck.size() - 2)
                return true;
            //Check whether all even positions have been traversed
            else if (oddEven == 0)
            {
                //Reset iterator starting index
                nextCard = -1;
                oddEven = 1;
                return true;
            }
            return false;
        }

        @Override
        public Card next() {
            if (hasNext())
                return deck.get(nextCard+=2);
            return null;
        }

    }

    /**
     * Opens a file and restore the serialized deck to memory using deserialization
     * @param filename
     * @return Game.Deck - deck of cards that were serialized to deck.ser
     */
    public Deck deserializeDeck(String filename) {
        Deck serializedDeck = null; //Reset serialized deck to null

        try (ObjectInputStream ois
                     = new ObjectInputStream(new FileInputStream(filename))){

            //Set the current serialized deck to the one read from file
            serializedDeck = (Deck)ois.readObject();

            //If there is no file available or the operation
            //cannot be made, return exception. Use printStackTrace
            //to easily determine where the fault is raised.
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //Return new deck read from file
        return serializedDeck;
    }


    /**
     * Appends each card in the deck to a string and returns it
     */
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();

        for (Card card: deck) {
            str.append(card).append("\n");
        }

        return str.toString();
    }
}
