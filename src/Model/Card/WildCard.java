package src.Model.Card;

/**
 * WildCard lets a player change the next color in the game,
 * as well as the direction of the game.
 */

import java.util.ArrayList;

public class WildCard extends ChangeColorCard 
{
    /**
     * WildCard constructor
     */
    public WildCard()
    {
        super(CardColors.WILD);
        this.setNextColor(CardColors.WILD);
    }

    
    @Override
    public boolean isLegal(Card topCard, CardColors currentColor, 
        int drawCardsNumber, ArrayList<CardColors> playerCardColors) 
    {
        //If the player has selected next color and there are 
        //no pending penalities
        return (drawCardsNumber == 0 && this.getNextColor() != CardColors.WILD);
    }
}
