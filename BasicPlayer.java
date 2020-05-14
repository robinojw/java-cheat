package Game;

/**
 * Model of a basic player
 *
 * @author White, Robin
 */
public class BasicPlayer implements Player {
    Strategy strategy;
    CardGame game;
    Hand hand = new Hand();

    /**
     * Creates a new instance of Game.BasicPlayer
     * @param strategy
     * @param game
     */
    public BasicPlayer (Strategy strategy, CardGame game)
    {
        this.strategy = strategy;
        this.game = game;
        this.hand = new Hand();
    }

    @Override
    public void addCard(Card c) { hand.add(c); }

    @Override
    public void addHand(Hand h) { hand.add(h); }

    @Override
    public int cardsLeft() { return hand.handSize(); }

    @Override
    public void setGame(CardGame g) { this.game = g; }

    @Override
    public void setStrategy(Strategy s) { this.strategy = s; }


    @Override
    public Bid playHand(Bid b) {
        boolean shouldCheat = this.strategy.cheat(b, hand);
        return this.strategy.chooseBid(b, hand, shouldCheat);
    }

    @Override
    public boolean callCheat(Bid b) {
        return strategy.callCheat(hand,b);
    }

    @Override
    public Hand getHand() {
        return this.hand;
    }

    @Override
    public Strategy getStrategy() {
        return this.strategy;
    }
}
