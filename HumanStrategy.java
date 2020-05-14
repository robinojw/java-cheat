package Game;

import java.util.Scanner;

/**
 *  Allows the user to play using the console. The user can
 *  decide whether to cheat and what cards to bid.
 *
 * @author White, Robin
 */
public class HumanStrategy implements Strategy {

    //Init scanner
    Scanner scan = new Scanner(System.in);

    /**
     * Prompts the user to choose a strategy of play
     * @param b current bid
     * @param h current hand
     * @return true if the user wants to cheat, false if not
     */
    @Override
    public boolean cheat(Bid b, Hand h) {
        h.sortAscending();  //Sort the players hand
        System.out.println("The last bid was: " + b.toString());
        System.out.println("Your current hand is: ");
        System.out.println(h.toString());   //Print the current hand

        //Get the users strategy choice
        System.out.println("Do you want to cheat?");
        String cheatChoice = scan.next();

        return cheatChoice.equals("yes");
    }

    /**
     * Method to execute a humans bid, prompts the user to make a choice
     * for their next bid, if the choice is valid, execute, if not repeat
     * until the human makes a valid choice
     *
     * @param b current bid
     * @param h current hand
     * @param cheat to determine whether the user needs to cheat
     * @return the humans new bid
     */
    @Override
    public Bid chooseBid(Bid b, Hand h, boolean cheat) {
        System.out.println("What rank would you like to play?");
        int rankChoice = scan.nextInt();
        Card chosenCard = (Card) h.hand.toArray()[rankChoice - 1];
        Card.Rank chosenRank = chosenCard.getRank();
        Hand newHand = new Hand();

        //If cheating add chosen card to hand but claim to have
        //played the correct rank
        if(cheat) {
            h.remove(chosenCard);
            newHand.add(chosenCard);
            Bid newBid = new Bid(newHand, b.r.getNext());
            return newBid;
        }

        //If not cheating choose correct card and add all the cards of that same
        //rank to the hand. If the chosen card is unplayable on the current bid
        //prompt the user to choose again
        else {
            //Checks to see if card is playable at the current time
            if(chosenRank.ordinal() != b.getRank().ordinal() &&
                    chosenRank.ordinal() != b.getRank().getNext().ordinal())
            {
                int validChoice = 0;
                //Keep prompting the user for a new choice until they play a valid card
                while(validChoice == 0)
                {
                    System.out.println("You can't play this card at this time "
                            + "please select a new card");
                    rankChoice = scan.nextInt();
                    chosenCard = (Card) h.hand.toArray()[rankChoice - 1];
                    chosenRank = chosenCard.getRank();

                    //Determine whether new choice can be played
                    if(chosenRank.ordinal() == chosenRank.ordinal() ||
                            chosenRank.ordinal() == chosenRank.getNext().ordinal())
                        validChoice++;
                }

            }
            //Loop through the hand and add all cards of the same rank
            for (Object card : h) {
                Card newCard = (Card) card;
                if(newCard.getRank() == chosenRank)
                    //Adds all same rank to new hand.
                    newHand.add(newCard);
            }

            h.remove(newHand);
            Bid newBid = new Bid(newHand, chosenRank);
            return newBid;
        }
    }

    /**
     * Prompt the user to choose whether they want to call cheat.
     * @param h current hand
     * @param b current bid
     * @return true if the human wants to call cheat, false if not.
     */
    @Override
    public boolean callCheat(Hand h, Bid b) {
        System.out.println("Do you want to call cheat?");
        String callCheatChoice = scan.next();

        if(callCheatChoice.equals("yes"))
            return true;
        return false;
    }
}
