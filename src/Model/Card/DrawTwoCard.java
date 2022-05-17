package src.Model.Card;

/**
 * DrawTwoCard adds 2 to the penalty for the next player in the turn
 */

import java.util.ArrayList;

public class DrawTwoCard extends Card 
{
    /**
     * @param cardColor Create Draw-Two card with the given color
     */
    public DrawTwoCard(CardColors cardColor)
    {
        super(cardColor);
    }

    
    @Override
    public boolean isLegal(Card topCard, CardColors currentColor, 
        int drawCardsNumber, ArrayList<CardColors> playerCardColors) 
    {
        //The topcard is a DrawTwoCard or the card color matches the current 
        //color and there are no penalties due 
        return (topCard instanceof DrawTwoCard || 
            (this.getCardColor() == currentColor && drawCardsNumber == 0));
    }
}
