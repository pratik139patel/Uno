package src.AI;

/**
 * Baseline AI can automatically play random(if there is more
 * than one) legal cards for each round and draw cards when no 
 * legal cards are present
 */

import src.Model.Card.ChangeColorCard;
import src.Model.GamePlay.Game;

import src.Model.GamePlay.Player;

public class BaselineAI extends GameAI
{
    /**
     * @param game Game this AI belongs to
     */
    public BaselineAI(Game game)
    {
        super(game);
    }


    @Override
    public int playCard(Player player) 
    {
        if(this.checkPlayerTurn(player))
        {
            int playCardIdx = -1;

            if(this.GameModel.getDrawCardsNumber() == 0)
            {
                playCardIdx = this.getCardIdxToPlayOnTopCard(player);

                //Set the current direction and current color
                if(playCardIdx != -1 && player.getCard(playCardIdx) 
                    instanceof ChangeColorCard)
                {
                    ((ChangeColorCard) player.getCard(playCardIdx))
                        .setNextColor(this.GameModel.getCurrentColor());
                    ((ChangeColorCard) player.getCard(playCardIdx))
                        .setNextDirection(this.GameModel.getCurrentDirection());
                }
            }
            
            return playCardIdx;
        }

        return -2;
    }
}
