package Game;

/**
 * Game.Strategy factory that determines the strategy a player would like to use
 *
 * @author White, Robin
 */
public class StrategyFactory {
    public enum StrategyEnum {
        //Strategies of play
        BASIC, HUMAN, THINKER, MY
    }

    /**
     * Take in the strategy choice and set it to the current strategy
     * @param strat
     * @return Game.Strategy
     */
    public Strategy setStrategy(StrategyEnum strat){
        Strategy returnStrat;
        switch(strat){
            case MY:
                returnStrat = new MyStrategy();
                break;
            case BASIC:
                returnStrat = new BasicStrategy();
                break;
            case HUMAN:
                returnStrat = new HumanStrategy();
                break;
            case THINKER:
                returnStrat = new ThinkerStrategy();
                break;
            default:
                returnStrat = null;
                break;
        }
        return returnStrat;
    }
}
