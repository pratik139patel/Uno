package src.Model.GamePlay;

/**
 * Game class handles the game of UNO and all the players who are playing the 
 * game. The game will validate the moves and reorganize all the card deck and 
 * card distribution to all the players.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import src.Model.Card.Card;
import src.Model.Card.ChangeColorCard;
import src.Model.Card.DrawTwoCard;
import src.Model.Card.NumberCard;
import src.Model.Card.ReverseCard;
import src.Model.Card.SkipCard;
import src.Model.Card.WildCard;
import src.Model.Card.WildDrawFourCard;
import src.Model.Card.Card.CardColors;
import src.Model.GamePlay.Player.PlayerType;

public class Game 
{
    /** Players in the game */
	private ArrayList<Player> Players = new ArrayList<Player>();
    /** Current player to play */
    private int CurrentPlayerIndex;
    /** Card deck to draw from for players */
	private ArrayList<Card> CardDeck = new ArrayList<Card>();
    /** Cards already used in the game */
	private ArrayList<Card> DiscardDeck = new ArrayList<Card>();
    /** Gameplay direction */
	private Direction GameDirection = Direction.CLOCKWISE;
    /** Count of cards to draw */
    private int DrawCardsNumber = 0;
    /** Current color to be played */
    private CardColors CurrentColor;
    /** Keeps track of wheather the game is over or not */
    private boolean GameOver = false;
    /** Number of cards to draw for each player initially */
    private static final int INITIAL_NUM_CARDS = 7;
    /** Minimum number of players */
    private static final int MINIMUM_PLAYERS = 2;
    /** Maximum number of players */
    private static final int MAXIMUM_PLAYERS = 9;
    /** Minimum number of AI players */
    private static final int MINIMUM_AI_PLAYERS = 0;
    /** Maximum number of AI players */
    private static final int MAXIMUM_AI_PLAYERS = 9;


    /**
     * @return Gets the minimum number of players allowed
     */
    public static int getMinimumPlayers()
    {
        return MINIMUM_PLAYERS;
    }


    /**
     * @return Gets the minimum number of AI players allowed
     */
    public static int getMinimumAIPlayers()
    {
        return MINIMUM_AI_PLAYERS;
    }


    /**
     * @return Gets the top card in the game
     */
    public Card getTopCard()
    {
        return this.DiscardDeck.get(0);
    }


    /**
     * @return Gets the maximum number of players allowed
     */
    public static int getMaximumPlayers()
    {
        return MAXIMUM_PLAYERS;
    }


    /**
     * @return Gets the maximum number of AI players allowed
     */
    public static int getMaximumAIPlayers()
    {
        return MAXIMUM_AI_PLAYERS;
    }


    /**
     * @return Gets the number of cards the player needs to draw
     */
    public int getDrawCardsNumber()
    {
        return this.DrawCardsNumber;
    }


    /**
     * @return Gets the index of the current player
     */
    public int getCurrentPlayerIndex()
    {
        return this.CurrentPlayerIndex;
    }
    

    /**
     * @return Gets the current color being played
     */
    public CardColors getCurrentColor()
    {
        return this.CurrentColor;
    }


    /**
     * @return Gets the number of cards left in the card deck
     */
    public int getNumberOfCardsInCardDeck()
    {
        return this.CardDeck.size();
    }
    

    /**
     * @return Gets the number of cards in the discard deck
     */
    public int getNumberOfCardsInDiscardDeck()
    {
        return this.DiscardDeck.size();
    }


    /**
     * @return Gets the number of cards delt to each player initially
     */
    public int getInitialNumberOfCards()
    {
        return INITIAL_NUM_CARDS;
    }


    /**
     * Game turn direction
     */
	public enum Direction 
    { 
		DEFAULT, CLOCKWISE, COUNTERCLOCKWISE
	};

	
    /**
     * @param numPlayers Number of players in the game
     * @param numAIPlayers Number of AI players in the game
     * @param numStrategicAIPlayers Number of Strategic AI players in the game
     */
	public Game(int numPlayers, int numAIPlayers, int numStrategicAIPlayers)
	{
        this.initializeCards();
		this.initializePlayers(numPlayers, numAIPlayers, numStrategicAIPlayers);
		this.initializeDiscardDeck();
	}


    /**
     * @return All the players in the game
     */
    public ArrayList<Player> getAllPlayers()
    {
        return this.Players;
    }


    /**
     * @param playerIdx The index of the desired player
     * @return The player from input index
     * @throws IndexOutOfBoundsException If player index is invalid
     */
    public Player getPlayer(int playerIdx) throws IndexOutOfBoundsException
    {
        return this.Players.get(playerIdx);
    }


    /**
     * @return Get the direction of the game
     */
    public Direction getCurrentDirection()
    {
        return this.GameDirection;
    }


    /**
     * Shuffle card deck
     */
    private void shuffleDeck()
    {
        Collections.shuffle(this.CardDeck);
    }


    /**
     * Shuffle player order
     */
    private void shufflePlayers()
    {
        Collections.shuffle(this.Players);
    }


    /**
     * Generate and shuffle the deck
     */
    private void initializeCards()
    {
        CardColors[] colors = CardColors.values();

        for(int i = 1; i < colors.length; ++i)
        {
            //Zero card, wild card, wild draw four card for each color 
            //(Red, Green, Yellow, Blue)
            this.CardDeck.add(new NumberCard(colors[i], 0));
            this.CardDeck.add(new WildCard());
            this.CardDeck.add(new WildDrawFourCard());

            for(int j = 0; j < 2; ++j)
            {
                //Skip card, reverse card, draw two card, 2 for each color 
                //(Red, Green, Yellow, Blue)
                this.CardDeck.add(new SkipCard(colors[i]));
                this.CardDeck.add(new ReverseCard(colors[i]));
                this.CardDeck.add(new DrawTwoCard(colors[i]));

                //Number card 1-9, 2 for each color (Red, Green, Yellow, Blue)
                for(int k = 1; k < 10; ++k)
                {
                    this.CardDeck.add(new NumberCard(colors[i], k));
                }
            }
        }

        this.shuffleDeck();
    }


    /**
     * Initialize the discard deck with proper card
     */
    private void initializeDiscardDeck()
    {
        //Make sure the top card in discard pile is not a wild card 
        //or wild draw four card
        do
        {
            this.DiscardDeck.add(0, this.CardDeck.remove(0));
            this.CurrentColor = this.DiscardDeck.get(0).getCardColor();
        } while(this.DiscardDeck.get(0) instanceof WildCard 
                    || this.DiscardDeck.get(0) instanceof WildDrawFourCard);
    }
	

    /**
     * Creates an array of players with unique id and hand collection
     * @param numPlayers Number of players in the game
     * @param numAIPlayers Number of AI players in the game
     * @param numStrategicAIPlayers Number of Strategic AI players in the game
     * @throws IllegalArgumentException If number of players is less than 1
     */
	private void initializePlayers(int numPlayers, int numAIPlayers, int numStrategicAIPlayers) 
        throws IllegalArgumentException
	{
        if(MINIMUM_PLAYERS > numPlayers || numPlayers > MAXIMUM_PLAYERS 
            || MINIMUM_AI_PLAYERS > numAIPlayers || numAIPlayers > MAXIMUM_AI_PLAYERS
                || numAIPlayers > numPlayers || numStrategicAIPlayers > numAIPlayers
                    || numStrategicAIPlayers < MINIMUM_AI_PLAYERS) 
        { 
            throw new IllegalArgumentException("Number Of Players Cannot Be Less Than " + 
                Integer.toString(MINIMUM_PLAYERS) + " and cannot be more than " +  
                    Integer.toString(MAXIMUM_PLAYERS) + "! Number Of AI Players Cannot Be Less Than "+ 
                        Integer.toString(MINIMUM_AI_PLAYERS) + " and cannot be more than " +  
                            Integer.toString(MAXIMUM_AI_PLAYERS)); 
        }
		
        //Create players with their id and initial set of cards
        this.createPlayers(numPlayers - numAIPlayers, PlayerType.HUMAN);
        this.createPlayers(numAIPlayers - numStrategicAIPlayers, PlayerType.BASELINE_AI);
        this.createPlayers(numStrategicAIPlayers, PlayerType.STRATEGIC_AI);

        //Shuffle the player order and randomize the current player turn
        this.shufflePlayers();
        this.CurrentPlayerIndex = (new Random()).nextInt(numPlayers);
	}


    /**
     * Creates number of player which can be any player kind
     * @param numPlayers Number of players to create
     * @param playerKind Type of player to create
     */
    private void createPlayers(int numPlayers, PlayerType playerKind)
    {
        int playerIDOffset = this.Players.size();

		for(int i = 1; i <= numPlayers; ++i)
		{
            ArrayList<Card> playerCards = new ArrayList<Card>();

            for(int j = 0; j < INITIAL_NUM_CARDS; ++j)
            {
                playerCards.add(this.CardDeck.remove(0));
            }

			this.Players.add(new Player(this, i + playerIDOffset, playerCards, playerKind));
		}
    }


    /**
     * Next player in the round
     * @param increment Choose next player in the order given by increment
     */
    private void incrementCurrentPlayer(int increment)
    {
        if(this.GameDirection == Direction.COUNTERCLOCKWISE)
        {
            if(this.CurrentPlayerIndex < increment)
            {
                this.CurrentPlayerIndex += this.Players.size();
            }

            this.CurrentPlayerIndex -= increment;
        }
        else
        {
            this.CurrentPlayerIndex = 
                (this.CurrentPlayerIndex + increment) % this.Players.size();
        }
    }


    /**
     * Check if the game is not over and it is this player's turn
     * @param playerID Player's ID that wants to take the turn
     * @throws IllegalAccessException If it is not this player's turn, or the game 
     * is over, or the player tries to play the wrong card
     */
    public void checkCurrentPlayerAndGameState(int playerID) 
        throws IllegalAccessException
    {
        if(this.Players.get(this.CurrentPlayerIndex).getPlayerID() != playerID)
        {
            throw new IllegalAccessException("Not This Player's Turn!");
        }

        if(this.GameOver)
        {
            throw new IllegalAccessException("Game Over!");
        }
    }


    /**
     * Changes the direction of the game play
     */
    private void changeGameDirection()
    {
        this.GameDirection = (this.GameDirection == Direction.CLOCKWISE) 
            ? (Direction.COUNTERCLOCKWISE) : (Direction.CLOCKWISE);
    }


    /**
     * Updates the game state if the move is legal
     * @param playerID Player's ID that wants to take the turn
     * @param card Card that player wants to play
     * @param playerCardColors Card Colors the player has
     * @throws IllegalAccessException If it is not this player's turn, or the game 
     * is over, or the player tries to play the wrong card
     */
    public void updateGameState(final int playerID, Card card, 
        ArrayList<CardColors> playerCardColors) throws IllegalAccessException
    {
        this.checkCurrentPlayerAndGameState(playerID);

        if(card.isLegal(this.DiscardDeck.get(0), this.CurrentColor, 
            this.DrawCardsNumber, playerCardColors))
        {
            if(card instanceof ChangeColorCard)
            {
                if(card instanceof WildDrawFourCard)
                {
                    this.DrawCardsNumber += 4;
                }

                //Set the next color of the game, and reset the wild card color before 
                //putting it into the discard deck
                this.CurrentColor = ((ChangeColorCard) card).getNextColor();
                ((ChangeColorCard) card).setNextColor(CardColors.WILD);

                //Set the next direction of the game, and reset the wild card direction 
                //before putting it into the discard deck
                if(((ChangeColorCard) card).getNextDirection() != Direction.DEFAULT)
                {
                    this.GameDirection = ((ChangeColorCard) card).getNextDirection();
                    ((ChangeColorCard) card).setNextDirection(Direction.DEFAULT);
                }
            }
            else
            {
                if(card instanceof DrawTwoCard)
                {
                    this.DrawCardsNumber += 2;
                }

                this.CurrentColor = card.getCardColor();
            }

            if(card instanceof ReverseCard)
            {
                this.changeGameDirection();
            }

            if(this.getCurrentPlayer().getNumberOfCards() == 1)
            {
                setGameOver();
            }
            else
            {
                this.incrementCurrentPlayer((card instanceof SkipCard) ? 2 : 1);
            }
            
            //Add the card played to the top of the discard deck
            this.DiscardDeck.add(0, card);
        }
        else
        {
            throw new IllegalAccessException("Illegal Move! 1 Card Penalty!");
        }
    }


    /**
     * Reorganize the ard deck if it is empty using dircard deck
     */
    private void repopulateCardDeck()
    {
        //Keep the top card in the discard deck and take the rest
        for(int i = this.DiscardDeck.size() - 1; i > 0; --i)
        {
            this.CardDeck.add(this.DiscardDeck.remove(i));
        }

        this.shuffleDeck();
    }

    
    /**
     * @return Top card from the card deck
     */
    private Card pickTopCardFromDeck()
    {
        if(this.CardDeck.size() == 0)
        {
            //If the card deck is empty, then repopulate the card 
            //deck using discard deck cards
            this.repopulateCardDeck();
        }
        
        return this.CardDeck.remove(0);
    }


    /**
     * @param playerID Player's ID that wants to draw cards
     * @return List of cards drawn from the card deck
     * @throws IllegalAccessException If it is not this player's turn, 
     * or the game is over, or the player tries to play the wrong card
     */
    public ArrayList<Card> drawCardsFromDeck(int playerID) 
        throws IllegalAccessException 
    {
        this.checkCurrentPlayerAndGameState(playerID);

        ArrayList<Card> drawnCards = new ArrayList<Card>();
        
        //If the DrawCardsNumber and the player wants to draw the top card 
        //from the deck
        if(this.DrawCardsNumber == 0)
        {
            drawnCards.add(this.pickTopCardFromDeck());
            
            try
            {
                this.updateGameState(playerID, this.pickTopCardFromDeck(), 
                    new ArrayList<CardColors>());

                if(this.GameOver)
                {
                    this.GameOver = false;
                    this.incrementCurrentPlayer
                        ((drawnCards.get(0) instanceof SkipCard) ? 2 : 1);
                }
                    
                //Card is valid to be played, so the player will not get 
                //the card back
                drawnCards.remove(0);
            }
            catch(IllegalAccessException ex)
            {
                //Picked up card is not valid to be played, so the player 
                //will get the card back
                this.incrementCurrentPlayer(1);
            }
        }
        else
        {
            //Draw cards which are due for drawing from the deck for the player
            for(; this.DrawCardsNumber != 0; --this.DrawCardsNumber)
            {
                drawnCards.add(this.pickTopCardFromDeck());
            }
            
            this.incrementCurrentPlayer(1);
        }
        
        return drawnCards;
    }


    /**
     * @return Player has to pick 1 card for illegal moves
     */
    public Card penalizePlayer() 
    {
        return this.pickTopCardFromDeck();
    }


    /**
     * @return Get the current player whose turn is now
     * @throws IndexOutOfBoundsException If the player index is invalid
     */
    public Player getCurrentPlayer() throws IndexOutOfBoundsException
    {
        return this.Players.get(this.CurrentPlayerIndex);
    }


    /**
     * @return If a player has won, then true
     */
    public boolean isGameOver()
    {
        return this.GameOver;
    }


    /**
     * Sets the game over to true
     */
    public void setGameOver()
    {
        this.GameOver = true;
    }


    /**
     * @param drawCardNumber The number of
     */
    public void setDrawCardNumber(int drawCardNumber)
    {
        this.DrawCardsNumber = drawCardNumber;
    }
}
