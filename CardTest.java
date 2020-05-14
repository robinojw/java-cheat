package Game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Simple Application to test functionality:
 *
 * 1. Create a deck of cards (unshuffled) and use your an Iterator to first traverse
 *    the cards in the order they will be dealt, then to traverse it in odd/even order using
 *    OddEvenIterator, printing out each card;
 * 2. Shuffle the deck and deal all the cards out to the four hands using the Deck
 *    methods deal and size. Use a for … each loop to print the cards in each
 *    hand;
 * 3. Save all hands to file;
 * 4. Display the number of each suit and rank and the total of the values in each hand;
 * 5. Display whether each hand is a straight or a flush.
 * 6. Sort the first two hands into ascending order, the third into descending order and
 *    the fourth into suit order;
 * 7. Load the hands from file;
 * 8. Iterate over the first hand with an iterator, removing each card and adding it to the
 *    second hand;
 * 9. Rearrange the cards in the hands so that each hand contains all 13 cards of a
 *    single suit in ascending order;
 *
 * @author White, Robin
 */
public class CardTest {

    public static void printHands(Hand hand1, Hand hand2, Hand hand3, Hand hand4, String message){

        //Generic code to print all hands after an event
        System.out.println("\n" + message);
        System.out.println("-------------------------------\n");
        System.out.println("Hand 1: \n"+hand1.toString());
        System.out.println("Hand 2: \n"+hand2.toString());
        System.out.println("Hand 3: \n"+hand3.toString());
        System.out.println("Hand 4: \n"+hand4.toString());
//        System.out.println("-------------------------------\n");
    }


   public static void main(String args[]) throws Exception {

       //Initialize a new unshuffled deck
       Deck deck = new Deck();

       //Initialize a new iterator
        Iterator<Card> itr = deck.iterator();

       //Loop through the deck in the order it was created and print it
       //to the console
       System.out.println("Deck traversed in the order it will be dealt: ");
       System.out.println("--------------------------------------------");
       while(itr.hasNext()){
           System.out.println(itr.next());
       }

       //Loop through the deck using the odd even iterator and print to the console
       Deck newDeck = new Deck();
       System.out.println("\nDeck traversed in odd/even order: ");
       System.out.println("-----------------------------------\n");

       itr = deck.oddEvenIterator();    //Switch to iterating in odd/even order

       while(itr.hasNext()){
           System.out.println(itr.next());
       }

       //Shuffle deck
       deck.shuffle();

       //Initialize player hands
       Hand hand1 = new Hand();
       Hand hand2 = new Hand();
       Hand hand3 = new Hand();
       Hand hand4 = new Hand();

       //Dealing the all the cards to four players
       for (int i = 0; i < deck.size(); i=i+4) {
           hand1.add(deck.deal());
           hand2.add(deck.deal());
           hand3.add(deck.deal());
           hand4.add(deck.deal());
       }

//       //For each loops to print each players hands
//       System.out.println("\nPlayer One's Hand: ");
//       for (Card c : hand1) {
//           System.out.println(c);
//       }
//       System.out.println("-------------------------------");
//
//       System.out.println("\nPlayer Two's Hand: ");
//       for (Card c : hand2) {
//           System.out.println(c);
//       }
//       System.out.println("-------------------------------");
//
//       System.out.println(divider);
//
//       System.out.println("\nPlayer Three's Hand: ");
//       for (Card c : hand3) {
//           System.out.println(c);
//       }
//       System.out.println("-------------------------------");
//
//       System.out.println(divider);
//
//       System.out.println("\nPlayer Four's Hand: ");
//       for (Card c : hand4) {
//           System.out.println(c);
//       }
//       System.out.println("-------------------------------");

       //Storing hands in a temp list
       ArrayList<Hand> handList = new ArrayList();
       handList.add(hand1);
       handList.add(hand2);
       handList.add(hand3);
       handList.add(hand4);
       //try catch to catch any exceptions that occur when saving the hands
       try{
           //for loop to save every hand from the temp array
           for (int i = 0; i < handList.size(); i++)
           {
               String filename = "hand"+i+".ser";
               FileOutputStream fos = new  FileOutputStream (filename);
               ObjectOutputStream out = new  ObjectOutputStream (fos);
               out.writeObject(handList.get(i));
               out.close();

           }

       } catch (Exception ex) {
           ex.printStackTrace();
       }

       System.out.println("\nHands Saved to hands.ser \n");
       System.out.println();


       //printing rank and suit counts
       System.out.println("\nRank and Suit counts for each hand: ");
       System.out.println("-------------------------------------\n");
       int count = 1;
       for (Hand hand : handList) {
           System.out.println("\nHand"+count+" Rank occurrences:");
           System.out.println("Number of 2's: "+hand.countRank(Card.Rank.TWO));
           System.out.println("Number of 3's: "+hand.countRank(Card.Rank.THREE));
           System.out.println("Number of 4's: "+hand.countRank(Card.Rank.FOUR));
           System.out.println("Number of 5's: "+hand.countRank(Card.Rank.FIVE));
           System.out.println("Number of 6's: "+hand.countRank(Card.Rank.SIX));
           System.out.println("Number of 7's: "+hand.countRank(Card.Rank.SEVEN));
           System.out.println("Number of 8's: "+hand.countRank(Card.Rank.EIGHT));
           System.out.println("Number of 9's: "+hand.countRank(Card.Rank.NINE));
           System.out.println("Number of 10's: "+hand.countRank(Card.Rank.TEN));
           System.out.println("Number of Jacks: "+hand.countRank(Card.Rank.JACK));
           System.out.println("Number of Queens: "+hand.countRank(Card.Rank.QUEEN));
           System.out.println("Number of Kings: "+hand.countRank(Card.Rank.KING));
           System.out.println("Number of Aces: "+hand.countRank(Card.Rank.ACE));

           System.out.println("\nHand"+count+" Suit occurrences:");
           System.out.println("Number of Clubs: "+hand.countSuit(Card.Suit.CLUBS));
           System.out.println("Number of Diamonds: "+hand.countSuit(Card.Suit.DIAMONDS));
           System.out.println("Number of Hearts: "+hand.countSuit(Card.Suit.HEARTS));
           System.out.println("Number of Spades: "+hand.countSuit(Card.Suit.SPADES));

           count++; //increment the count
       }


       //Displaying whether the hands are flushes or straights
       System.out.println("\nDisplay whether hands are flushes or straights: ");
       System.out.println("-----------------------------------------------");
       System.out.println("\nHand 1:");
       System.out.println("\nFlush:"+hand1.isFlush());
       System.out.println("Straight:"+hand1.isStraight());
       System.out.println("\nHand 2:");
       System.out.println("\nFlush:"+hand2.isFlush());
       System.out.println("Straight:"+hand2.isStraight());
       System.out.println("\nHand 3:");
       System.out.println("\nFlush:"+hand3.isFlush());
       System.out.println("Straight:"+hand3.isStraight());
       System.out.println("\nHand 4:");
       System.out.println("\nFlush:"+hand4.isFlush());
       System.out.println("Straight:"+hand4.isStraight()+"\n");

       //Print the changes to hand to the console
       System.out.println("Total values of each hand:");
       System.out.println("--------------------------");
       System.out.println("Total value of hand1: " + hand1.handValue());
       System.out.println("Total value of hand2: " + hand2.handValue());
       System.out.println("Total value of hand3: " + hand3.handValue());
       System.out.println("Total value of hand4: " + hand4.handValue() + "\n");

       //Sort the first two hands into ascending order, the third into descending order and
       //the fourth into suit order
       hand1.sortAscending();
       hand2.sortAscending();
       hand3.sortDescending();
       hand4.sortSuit();

       //Print the changes to hand to the console
       printHands(hand1, hand2, hand3, hand4, "Hands after sorting: ");

       int size = handList.size();
       handList.clear();
       //Loading hands which where saved earlier
       //try catch to catch any exceptions that occur when loading the hands
       try{
           //for loop to load every hand into the arraylist
           for (int i = 0; i < size; i++)
           {
               String filename = "hand"+i+".ser";
               FileInputStream fis = new FileInputStream(filename);
               ObjectInputStream in = new ObjectInputStream(fis);
               handList.add((Hand)in.readObject());
               in.close();

           }

       }catch (Exception ex) {
           ex.printStackTrace();
       }

       //re-initialising the hands from the arraylist
       hand1 = handList.get(0);
       hand2 = handList.get(1);
       hand3 = handList.get(2);
       hand4 = handList.get(3);

       //Print the changes to hand to the console
       printHands(hand1, hand2, hand3, hand4, "Hands after deserialization: ");

       //Iterate over the first hand with an iterator, removing each card and adding it to the
       //second hand
       Iterator<Card> itr2 = hand1.iterator();

       while(itr2.hasNext())
       {
           Card temp2 = itr2.next();
           itr2.remove();
           hand2.add(temp2);

       }


       //Print the changes to hand to the console
       printHands(hand1, hand2, hand3, hand4, "Hands after removing all cards from hand 1 " +
               "and adding them to hand 2: ");

       //Rearrange the cards in the hands so that each hand contains all 13 cards of a
       //single suit in ascending order

       //Rearranging the hands so each hand 2 to Ace of the same suit
       hand2.add(hand3);
       hand2.add(hand4);
       //Reinitialising 3 hands
       hand1 = new Hand();
       hand3 = new Hand();
       hand4 = new Hand();

       Card tempCard = new Card();

       //Iterator used to iterate through the hand
       Iterator<Card> it3 = hand2.iterator();
       //Iteration through every card within hand2
       while(it3.hasNext())
       {
           //Storing the current card as the the tempCard
           tempCard = it3.next();
           //Checking the ordinal value of every card which represents the suit
           //0 = clubs, 1 Diamonds, 2 Hearts and 3 Spades
           //If a club, remove from hand 2 and add to hand 1
           //Process is repeated for 3 suits leaving spades in the iterated hand
           if(tempCard.getSuit().ordinal()==0){
               it3.remove();
               hand1.add(tempCard);
           }else if(tempCard.getSuit().ordinal()==1){
               it3.remove();
               hand3.add(tempCard);
           }else if(tempCard.getSuit().ordinal()==2){
               it3.remove();
               hand4.add(tempCard);
           }

       }
       //Sorts the hands into ascending order 2 to Ace
       hand1.sortAscending();
       hand2.sortAscending();
       hand3.sortAscending();
       hand4.sortAscending();

       //Print the changes to hand to the console
       printHands(hand1, hand2, hand3, hand4, "Hands after being sorted into suits 2 to Ace: ");


   }

}
