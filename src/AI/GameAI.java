package src.AI;

/**
 * Game AI defines a template for AI player
 */

import src.Model.Card.Card;
import src.Model.Card.ChangeColorCard;
import src.Model.Card.NumberCard;
import src.Model.Card.Card.CardColors;
import src.Model.GamePlay.Game;

import src.Model.GamePlay.Player;

public abstract class GameAI 
{
    /** Game that an AI belongs to */
    public Game GameModel; 


    /**
     * @param game Game this AI is assigned to
     */
    public GameAI(Game game)
    {
        this.GameModel = game;
    }

    
    /**
     * @return Gets the game AI belongs to
     */
    public Game getCurrentGame()
    {
        return this.GameModel;
    }


    /**
     * @param player Player that wants to play
     * @return If game is not over and it is this player's turn then return true
     */
    public boolean checkPlayerTurn(Player player)
    {
        return player.getPlayerID() == 
            this.GameModel.getCurrentPlayer().getPlayerID() 
            && !this.GameModel.isGameOver();
    }


    /**
     * @param card The card whose number is to be found
     * @return Gets the number on the card
     */
    public int getCardNumber(Card card) 
    {
        return (card instanceof NumberCard) ? 
            (((NumberCard)card).getCardNumber()) : -1;
    }


    /**
     * @param player Player who wants to play
     * @return Card index that should by played. 
     * -1 to draw cards
     */
    public int getCardIdxToPlayOnTopCard(Player player)
    {
        Card topCard = this.GameModel.getTopCard();
        int topCardNumber = this.getCardNumber(topCard);
        CardColors currentColor = this.GameModel.getCurrentColor();
        int changeColorCardIdx = -1;

        //Set Non-Number card value to -2
        topCardNumber = (topCardNumber == -1) ? -2 : topCardNumber;

        for(int i = 0; i < player.getNumberOfCards(); ++i)
        {
            Card playerCard = player.getCard(i);

            if(playerCard.getCardColor() == currentColor || 
                (topCardNumber == this.getCardNumber(playerCard)))
            {
                return i;
            }
            else if(playerCard instanceof ChangeColorCard)
            {
                changeColorCardIdx = i;
            }
        }

        return changeColorCardIdx;
    }


    /**
     * @param player Player that wants to take turn
     * @return The card index that needs to be played.
     * -1 to draw cards. -2 indicates this game is over
     * or it is not this player's turn
     */
    public abstract int playCard(Player player);
}
