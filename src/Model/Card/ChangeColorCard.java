package src.Model.Card;

/**
 * ChangeColorCard is an abstract card which helps the player change the
 * next color in the game
 */

import src.Model.GamePlay.Game.Direction;

public abstract class ChangeColorCard extends Card
{
    /** Next color chosen by the player */
    private CardColors NextColor;
    /** Direction of order chosen by the player */
    private Direction NextDirection = Direction.DEFAULT;


    /**
     * ChangeColorCard constructor
     */
    public ChangeColorCard(CardColors cardColor) 
    {
        super(cardColor);
    }


    /**
     * @param nextColor Set the next color to be played
     */
    public void setNextColor(CardColors nextColor)
    {
        this.NextColor = nextColor;
    }


    /**
     * @return Get the next color to be played
     */
    public CardColors getNextColor()
    {
        return this.NextColor;
    }

    
    /**
     * @param nextColor Set the direction of order
     */
    public void setNextDirection(Direction nextDirection)
    {
        this.NextDirection = nextDirection;
    }

    
    /**
     * @return Get the next direction to be played
     */
    public Direction getNextDirection()
    {
        return this.NextDirection;
    }
}
