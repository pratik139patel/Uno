package src.AI;

/**
 * Strategic AI is customed AI which playes card strategically
 * to win the game instaed of naively
 */

import java.util.Random;

import src.Model.Card.ChangeColorCard;
import src.Model.Card.DrawTwoCard;
import src.Model.Card.WildDrawFourCard;
import src.Model.GamePlay.Game;

import src.Model.GamePlay.Player;
import src.Model.GamePlay.Game.Direction;

public class StrategicAI extends GameAI
{
    /**
     * @param game Game this AI belongs to
     */
    public StrategicAI(Game game) 
    {
        super(game);
    }


    /**
     * Sets the card color to most popular card color in player's hand
     * and randomely selects direction of the game
     * @param player Player whose ChangeColor cards needs to be used
     * @param index Index of changeColor card in player's hand
     */
    private void setChangeColorCardNextColorAndDirection(Player player, int index)
    {
        ((ChangeColorCard) player.getCard(index))
            .setNextColor(player.getMaximumCardColor());
        ((ChangeColorCard) player.getCard(index)).setNextDirection
            (Direction.values()[new Random().nextInt(Direction.values().length)]);
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

                if(playCardIdx != -1 && player.getCard(playCardIdx) instanceof ChangeColorCard)
                {
                    this.setChangeColorCardNextColorAndDirection(player, playCardIdx);
                }
            }
            else
            {
                //If top card is wild draw four then try to find wild draw four
                if(this.GameModel.getTopCard() instanceof WildDrawFourCard)
                {
                    for(int i = 0; i < player.getNumberOfCards(); ++i)
                    {
                        if(player.getCard(i) instanceof WildDrawFourCard)
                        {
                            this.setChangeColorCardNextColorAndDirection(player, i);
                            return i;
                        }
                    }
                }
                //If top card is draw two, then try to find draw two from the deck
                else
                {
                    for(int i = 0; i < player.getNumberOfCards(); ++i)
                    {
                        if(player.getCard(i) instanceof DrawTwoCard)
                        {
                            return i;
                        }
                    }
                }
            }

            return playCardIdx;
        }

        return -2;
    }
}
