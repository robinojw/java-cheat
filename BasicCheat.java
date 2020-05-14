package Game;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the card game interface. Includes the main method to play
 * the game.
 *
 * @author White, Robin
 */
public class BasicCheat implements CardGame {
    private Player[] players;
    private int nosPlayers;

    public static final int MINPLAYERS=5;
    private int currentPlayer;
    private Hand discards;
    private Bid currentBid;

    /**
     * Creates a new instance of Game.BasicCheat.
     */
    public BasicCheat(){
        this(MINPLAYERS);
    }

    /**
     * Creates a new instance of Game.BasicCheat.
     * @param n
     */
    public BasicCheat(int n){
        nosPlayers = n;
        players = new Player[nosPlayers];
        for(int i = 0; i < nosPlayers; i++) {
            players[i] = (new BasicPlayer(new StrategyFactory().setStrategy
                    (StrategyFactory.StrategyEnum.BASIC),this));
        }
    }

    @Override
    public boolean playTurn(){
        //Ask player for a play,
        System.out.println("current bid = " + currentBid);
        currentBid=players[currentPlayer].playHand(currentBid);

        System.out.println("Game.Player bid = " + currentBid);

        //Add hand played to discard pile
        discards.add(currentBid.getHand());

        //Offer all other players the chance to call cheat
        boolean cheat = false;

        for(int i = 0; i<players.length && !cheat; i++){
            if(i != currentPlayer){
                cheat = players[i].callCheat(currentBid);
                if(cheat){
                    System.out.println("Game.Player called cheat by Game.Player "+(i+1));
                    if(isCheat(currentBid)){
                        //CHEAT CALLED CORRECTLY
                        //Give the discard pile of cards to currentPlayer who
                        //then has to play again
                        players[currentPlayer].addHand(discards);
                        System.out.println("Game.Player cheats!");
                        System.out.println("Adding cards to player "+
                                (currentPlayer+1));
                    }
                    else {
                        //CHEAT CALLED INCORRECTLY
                        //Give cards to caller i who is new currentPlayer
                        System.out.println("Game.Player Honest");
                        currentPlayer = i;
                        players[currentPlayer].addHand(discards);
                        System.out.println("Adding cards to player "+
                                (currentPlayer+1));
                    }
                    //If cheat is called, current bid reset to an empty bid
                    //with rank two whatever the outcome.
                    currentBid = new Bid();

                    //Discards now reset to empty
                    discards = new Hand();
                }
            }
        }

        for(Player player : players) {
            if (player.getStrategy().getClass().toString().
                    equals("class CheatGame.Game.ThinkerStrategy"))
            {
                ThinkerStrategy thinker = (ThinkerStrategy)player.getStrategy();
                thinker.resetDiscardHand();
            }

        }

        if(!cheat){
            //Go to the next player
            System.out.println("No Cheat Called");
            currentPlayer = (currentPlayer+1)%nosPlayers;
        }
        return true;
    }

    @Override
    public int winner(){
        for(int i = 0; i < nosPlayers; i++){
            if(players[i].cardsLeft() == 0)
                return i;
        }
        return -1;
    }

    @Override
    public void initialise(){
        //Create Game.Deck of cards
        Deck d = null;
        try {
            d = new Deck();
        } catch (IOException ex) {
            Logger.getLogger(BasicCheat.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        d.shuffle();
        //Deal cards to players
        Iterator<Card> it = d.iterator();
        int count=0;
        while(it.hasNext()){
            players[count%nosPlayers].addCard(it.next());
            count++;
        }

        //Initialise Discards
        discards = new Hand();

        //Chose first player
        currentPlayer = 0;
        currentBid = new Bid();
        currentBid.setRank(Card.Rank.TWO);
        Card twoClubs = new Card(Card.Rank.TWO, Card.Suit.CLUBS);

        //Checks who has TWO of CLUBS and makes them the starting player.
        int playerSearch = 0;
        for(Player player : players) {
            for(Object card : player.getHand()) {
                Card currentCard = (Card) card;
                if (currentCard.compareTo(twoClubs) == 0)
                    currentPlayer = playerSearch;
            }
            playerSearch++;
        }
    }

    public void playGame(){
        initialise();
        int c = 0;
        Scanner in = new Scanner(System.in);
        boolean finished = false;
        while(!finished){
            //Play a hand
            System.out.println(" Cheat turn for player "+(currentPlayer+1));
            playTurn();
            System.out.println(" Current discards =\n"+discards);
            c++;
            System.out.println(" Turn "+c+ " Complete. Press any key to "
                    + "continue or enter Q to quit>");
            String str = in.nextLine();

            if(str.equals("Q")||str.equals("q")||str.equals("quit"))
                finished = true;

            int w = winner();
            if(w >= 0){
                System.out.println("The Winner is Game.Player "+(w+1));
                finished=true;
            }
        }
    }

    /**
     * Determines if the player has cheated or not.
     * @param b
     * @return true if the player has cheated.
     *         false if the player hasn't cheated.
     */
    public static boolean isCheat(Bid b){
        for(Object c: b.getHand()){
            Card card = (Card) c;
            if(card.getRank() != b.r)
                return true;
        }
        return false;
    }

    public static void main(String[] args){
        BasicCheat cheat = new BasicCheat();
        cheat.playGame();
    }
}
