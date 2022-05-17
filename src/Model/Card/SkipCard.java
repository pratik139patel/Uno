package src.Model.Card;

/**
 * SkipCard allows the player in the next turn to get skipped
 */

import java.util.ArrayList;

public class SkipCard extends Card 
{
    /**
     * @param cardColor Create skip card with the given color
     */
    public SkipCard(CardColors cardColor)
    {
        super(cardColor);
    }

    
    @Override
    public boolean isLegal(Card topCard, CardColors currentColor, 
        int drawCardsNumber, ArrayList<CardColors> playerCardColors) 
    {
        //There are no pending penalties and either the card color 
        //matches or the topcard is a SkipCard
        return (drawCardsNumber == 0 && (currentColor == this.getCardColor() 
                    || topCard instanceof SkipCard));
    }
}
