package src.Model.Card;

/**
 * WildDrawFourCard lets a player to add 4 to the penalities of 
 * the next player, change direction of the game as well as 
 * change the next color in the game.
 */

import java.util.ArrayList;

public class WildDrawFourCard extends ChangeColorCard 
{
    /**
     * WildDrawFourCard constructor
     */
    public WildDrawFourCard()
    {
        super(CardColors.WILD);
        this.setNextColor(CardColors.WILD);
    }

    
    @Override
    public boolean isLegal(Card topCard, CardColors currentColor, 
        int drawCardsNumber, ArrayList<CardColors> playerCardColors) 
    {
        //Legal if player selected new card color, and player does not have any 
        //card with current color being played, and either the top card is wild 
        //draw four card or the draw stack is 0
        return (((drawCardsNumber == 0 && !playerCardColors.contains(currentColor)) 
                    || topCard instanceof WildDrawFourCard) && 
                        this.getNextColor() != CardColors.WILD);
    }
}
