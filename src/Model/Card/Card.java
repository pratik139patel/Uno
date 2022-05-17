package src.Model.Card;

/**
 * Card class is an abstract class which defines a structure of a card
 */

import java.util.ArrayList;

public abstract class Card 
{
    /**
     * Type of color a card could have
     */
	public enum CardColors
    {
        WILD, RED, YELLOW, GREEN, BLUE
    };


    /** This card color */
    private CardColors CardColor;


    /**
     * @param cardColor Create this card with the specified color
     */
    public Card(CardColors cardColor)
    {
        this.CardColor = cardColor;
    }


    /**
     * Get this card's color
     */
    public CardColors getCardColor()
    {
        return this.CardColor;
    }

    
    /**
     * Checks if the move is legal
     * @param topCard Topcard in the discard card deck
     * @param currentColor Current color to be used
     * @param drawCardsNumber Number of cards that is due for drawing
     * @return If the move is legal than true else false
     */
    public abstract boolean isLegal(Card topCard, CardColors currentColor, 
        int drawCardsNumber, ArrayList<CardColors> playerCardColors);
}
