package src.Model.Card;

/**
 * NumberCard is a simple numbered card which can be played by the player
 */

import java.util.ArrayList;

public class NumberCard extends Card
{
    /** Number on the card */
    private int CardNumber;
    

    /**
     * @param cardColor Create number card with the given color
     * @param cardNumber The number on the card
     */
    public NumberCard(CardColors cardColor, int cardNumber)
    {
        super(cardColor);
        this.CardNumber = cardNumber;
    }


    /**
     * @return Get the number on the card
     */
    public int getCardNumber()
    {
        return this.CardNumber;
    }


    @Override
    public boolean isLegal(Card topCard, CardColors currentColor, 
        int drawCardsNumber, ArrayList<CardColors> playerCardColors) 
    {
        //If drawCardsNumber is 0 and either the current color matches or 
        //current number then return true
        return (drawCardsNumber == 0 && (currentColor == this.getCardColor() 
                    || (topCard instanceof NumberCard && 
                        ((NumberCard) topCard).getCardNumber() == this.getCardNumber())));
    }
}
