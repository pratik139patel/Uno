package src.Model.GamePlay;

/**
 * Player class encapsulates the cards the player has, the game the 
 * player belongs to, as well as the ID of the player. A player can 
 * access methods of this class to play the card or draw from deck.
 */

import java.util.ArrayList;

import src.Model.Card.Card;
import src.Model.Card.Card.CardColors;

public class Player 
{
    /** Each player has unique ID */
	private int PlayerID;
    /** Cards each player has */
    private ArrayList<Card> PlayerCards;
    /** Game that a player belongs to */
    private Game CurrentGame;
    /** AI or human player */
    private PlayerType PlayerKind;


    /**
     * Type of player
     */
    public enum PlayerType
    {
        HUMAN, STRATEGIC_AI, BASELINE_AI
    }


    /**
     * @param currentGame Game that a player belongs to
     * @param PID Each player has unique ID
     * @param playerCards Cards each player has
     * @param playerType Type of player
     */
    public Player(Game currentGame, int PID, ArrayList<Card> playerCards, PlayerType playerType)
    {
        this.CurrentGame = currentGame;
        this.PlayerID = PID;
        this.PlayerCards = playerCards;
        this.PlayerKind = playerType;
    }


    /**
     * @return Gets this player kind
     */
    public PlayerType getPlayerType()
    {
        return this.PlayerKind;
    }


    /**
     * @return Gets the unique ID of the player
     */
    public int getPlayerID()
    {
        return this.PlayerID;
    }


    /**
     * @return Gets all the colored cards the player has
     */
    public ArrayList<CardColors> getCardColors()
    {
        ArrayList<CardColors> cardColors = new ArrayList<CardColors>();

        //Get the colors the players has except including wild color
        for(Card card : this.PlayerCards)
        {
            //Add no duplicates
            if(card.getCardColor() != CardColors.WILD 
                && !cardColors.contains(card.getCardColor())) 
            {
                cardColors.add(card.getCardColor());

                if(cardColors.size() == 4)
                {
                    break;
                }
            }
        }

        return cardColors;
    }


    /**
     * @param cardIndex The card index player wants to play, -1 if the player 
     * does not want to play any card
     * @throws IllegalArgumentException If the cardindex is illegal
     */
    public void playCard(int cardIndex) 
        throws IllegalArgumentException, IllegalAccessException
    {
        if(cardIndex < -1 || cardIndex > this.PlayerCards.size())
        {
            throw new IllegalArgumentException("Invalid Card Index!");
        }

        try
        {
            //If the player decides not to play any card
            if(cardIndex == -1)
            {
                this.PlayerCards.addAll
                    (CurrentGame.drawCardsFromDeck(this.PlayerID));
            }
            else
            {
                CurrentGame.updateGameState(this.getPlayerID(), 
                    this.PlayerCards.get(cardIndex), this.getCardColors());
                this.PlayerCards.remove(cardIndex);
            }
        }
        catch(IllegalArgumentException | IllegalAccessException ex)
        {
            //If the move was illegal, then punish the player
            this.PlayerCards.add(CurrentGame.penalizePlayer());
            throw ex;
        }
    }


    /**
     * @return If player has zero cards then true else false
     */
    public boolean hasPlayerWon()
    {
        return this.getNumberOfCards() == 0;
    }


    /**
     * @return Number of cards the player has
     */
    public int getNumberOfCards()
    {
        return this.PlayerCards.size();
    }


    /**
     * @param playerCardIndex Player's card index
     * @return Player's card at the given index
     * @throws IndexOutOfBoundsException If playerCardIndex is invalid
     */
    public Card getCard(int playerCardIndex) 
        throws IndexOutOfBoundsException
    {
        return this.PlayerCards.get(playerCardIndex);
    }


    /**
     * @return ArrayList of all the cards of the player
     */
    public ArrayList<Card> getAllCards() 
    {
        return this.PlayerCards;
    }


    /**
     * @return Return the card color most occured in the player deck
     */
    public CardColors getMaximumCardColor() 
    {
        int[] cardColorCount = new int[CardColors.values().length];

        for(CardColors c : CardColors.values())
        {
            cardColorCount[c.ordinal()] = 0;
        }

        cardColorCount[CardColors.WILD.ordinal()] = -1;

        for(Card card : this.PlayerCards)
        {
            if(card.getCardColor() != CardColors.WILD)
            {
                cardColorCount[card.getCardColor().ordinal()] += 1;
            } 
        }

        int maxIdx = 0;

        for(int i = 0; i < cardColorCount.length; ++i)
        {
            maxIdx = (cardColorCount[i] > cardColorCount[maxIdx]) ? 
                (i) : (maxIdx);
        }

        return CardColors.values()[maxIdx];
    }
}
