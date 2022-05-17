package src.Model.Card;

/**
 * A player can reverse the order of turn using this card
 */

import java.util.ArrayList;

public class ReverseCard extends Card
{
    /**
     * @param cardColor Create Reverse card with the given color
     */
    public ReverseCard(CardColors cardColor)
    {
        super(cardColor);
    }

    
    @Override
    public boolean isLegal(Card topCard, CardColors currentColor, 
        int drawCardsNumber, ArrayList<CardColors> playerCardColors) 
    {
        //There are no pending penalties and either the card color matches or the 
        //topcard is a ReverseCard
        return (drawCardsNumber == 0 && (currentColor == this.getCardColor() 
                    || topCard instanceof ReverseCard));
    }
}
