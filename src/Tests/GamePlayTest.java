package src.Tests;

/**
 * This class tests all the functions of the card classes 
 * as well as game and player class
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import java.awt.Color;
import java.util.ArrayList;
import org.junit.Test;

import src.AI.BaselineAI;
import src.AI.StrategicAI;
import src.Controller.GameController;
import src.Model.Card.Card;
import src.Model.Card.DrawTwoCard;
import src.Model.Card.NumberCard;
import src.Model.Card.ReverseCard;
import src.Model.Card.SkipCard;
import src.Model.Card.WildCard;
import src.Model.Card.WildDrawFourCard;
import src.Model.Card.Card.CardColors;
import src.Model.GamePlay.*;
import src.Model.GamePlay.Game.Direction;
import src.Model.GamePlay.Player.PlayerType;
import src.View.ChoosePlayerView;
import src.View.GameOverView;
import src.View.GameView;

public class GamePlayTest
{
    private final int NUMBER_OF_TESTS = 100;


    /**
     * Test game initializer functions and initial game state
     */
	@Test
	public void testGameInitializer()
	{
        for(int i = 0; i < NUMBER_OF_TESTS; ++i)
        {
            //Test game works for minimum number of people
            Game Game1 = new Game(2, 0, 0);
            ArrayList<Player> players1 = Game1.getAllPlayers();
            assertEquals(108 - 2*Game1.getInitialNumberOfCards(), 
                Game1.getNumberOfCardsInCardDeck() + Game1.getNumberOfCardsInDiscardDeck());
            assertNotEquals(players1.get(0).getPlayerID(), players1.get(1).getPlayerID());
            assertEquals(Game1.getInitialNumberOfCards(), players1.get(0).getNumberOfCards());
            assertEquals(Game1.getInitialNumberOfCards(), players1.get(1).getNumberOfCards());
            assertFalse(players1.get(0).hasPlayerWon());
            assertEquals(Direction.CLOCKWISE, Game1.getCurrentDirection());

            //Test game works for maximum number of people
            Game Game2 = new Game(9, 2, 1);
            ArrayList<Player> players2 = Game2.getAllPlayers();
            assertEquals(108 - 9*Game2.getInitialNumberOfCards(), 
                Game2.getNumberOfCardsInCardDeck() + Game2.getNumberOfCardsInDiscardDeck());
            assertNotEquals(Game2.getPlayer(0).getPlayerID(), Game2.getPlayer(1).getPlayerID());
            assertEquals(Game2.getInitialNumberOfCards(), players2.get(0).getNumberOfCards());
            assertEquals(Direction.CLOCKWISE, Game2.getCurrentDirection());
            assertFalse(Game2.isGameOver());
            assertNotNull(Game2.getTopCard());
            assertThrows(IllegalAccessException.class, () -> {Game2.checkCurrentPlayerAndGameState(-1);});

            assertEquals(9, Game.getMaximumPlayers());
            assertEquals(2, Game.getMinimumPlayers());
            assertEquals(9, Game.getMaximumAIPlayers());
            assertEquals(0, Game.getMinimumAIPlayers());

            //Test game throws error for too many people
            assertThrows(IllegalArgumentException.class, () -> { new Game(10, 0, 0); });
        }
	}
	

    /**
     * Test wild card functions
     */
	@Test
	public void testWildCard()
	{
        for(int i = 0; i < NUMBER_OF_TESTS; ++i)
        {
            WildCard card1 = new WildCard();
            card1.setNextColor(CardColors.BLUE);
            card1.setNextDirection(Direction.CLOCKWISE);
            assertEquals(CardColors.WILD, card1.getCardColor());
            assertEquals(CardColors.BLUE, card1.getNextColor());
            assertEquals(Direction.CLOCKWISE, card1.getNextDirection());
            assertFalse(card1.isLegal(new WildCard(), CardColors.RED, 
                2, new ArrayList<CardColors>()));
        }
	}


	/**
     * Test skip card functions
     */
	@Test
	public void testSkipCard()
	{
        for(int i = 0; i < NUMBER_OF_TESTS; ++i)
        {
            SkipCard card1 = new SkipCard(CardColors.YELLOW);
            assertEquals(CardColors.YELLOW, card1.getCardColor());
            assertFalse(card1.isLegal(new WildCard(), CardColors.RED, 
                0, new ArrayList<CardColors>()));
        }
	}
	

    /**
     * Test reverse card functions
     */
	@Test
	public void testReverseCard()
	{
        for(int i = 0; i < NUMBER_OF_TESTS; ++i)
        {
            ReverseCard card1 = new ReverseCard(CardColors.GREEN);
            assertEquals(CardColors.GREEN, card1.getCardColor());
            assertFalse(card1.isLegal(new DrawTwoCard(CardColors.GREEN), 
                CardColors.GREEN, 4, new ArrayList<CardColors>()));
        }
	}
	

    /**
     * Test draw two card functions
     */
	@Test
	public void testDrawTwoCard()
	{
        for(int i = 0; i < NUMBER_OF_TESTS; ++i)
        {
            DrawTwoCard card1 = new DrawTwoCard(CardColors.BLUE);
            assertEquals(CardColors.BLUE, card1.getCardColor());
            assertFalse(card1.isLegal(new WildDrawFourCard(), 
                CardColors.BLUE, 4, new ArrayList<CardColors>()));
        }
	}
	

    /**
     * Test draw four card functions
     */
	@Test
	public void testWildDrawFourCardAndCustomRule1()
	{
        for(int i = 0; i < NUMBER_OF_TESTS; ++i)
        {
            WildDrawFourCard card1 = new WildDrawFourCard();
            card1.setNextColor(CardColors.GREEN);
            card1.setNextDirection(Direction.COUNTERCLOCKWISE);
            assertEquals(CardColors.WILD, card1.getCardColor());
            assertEquals(CardColors.GREEN, card1.getNextColor());
            assertEquals(Direction.COUNTERCLOCKWISE, card1.getNextDirection());
            assertFalse(card1.isLegal(new DrawTwoCard(CardColors.GREEN), 
                CardColors.GREEN, 2, new ArrayList<CardColors>()));
            ArrayList<CardColors> playerCardColors = new ArrayList<CardColors>();
            playerCardColors.add(CardColors.BLUE);
            assert(card1.isLegal(new WildDrawFourCard(), 
                CardColors.BLUE, 0, playerCardColors));
        }
	}
	

    /**
     * Test number card functions
     */
	@Test
	public void testNumberCard()
	{
        for(int i = 0; i < NUMBER_OF_TESTS; ++i)
        {
            NumberCard card1 = new NumberCard(CardColors.BLUE, 9);
            assertEquals(CardColors.BLUE, card1.getCardColor());
            assertEquals(9, card1.getCardNumber());
            assert(card1.isLegal(new WildDrawFourCard(), CardColors.BLUE, 
                0, new ArrayList<CardColors>()));
            assertFalse(card1.isLegal(new DrawTwoCard(CardColors.BLUE), 
                CardColors.BLUE, 2, new ArrayList<CardColors>()));
            assert(card1.isLegal(new NumberCard(CardColors.BLUE, 1), 
                CardColors.BLUE, 0, new ArrayList<CardColors>()));
            assert(card1.isLegal(new NumberCard(CardColors.RED, 9), 
                CardColors.RED, 0, new ArrayList<CardColors>()));
        }
	}
	

    /**
     * Test player drawing card without prior penalties
     */
	@Test
	public void testPlayerDrawingCardsWithoutPenalty() throws IllegalAccessException
	{
        for(int i = 0; i < NUMBER_OF_TESTS; ++i)
        {
            Game Game1 = new Game(2, 0, 0);
            ArrayList<Player> players = Game1.getAllPlayers();
            assertThrows(IllegalArgumentException.class, 
                () -> {players.get(1).playCard(-2);}); 
            players.get(Game1.getCurrentPlayerIndex()).playCard(-1);
        }
	}

	
    /**
     * Test if player win function work correctly
     */
	@Test
	public void testPlayerWin()
	{
        for(int i = 0; i < NUMBER_OF_TESTS; ++i)
        {
        	ArrayList<Card> playerCards = new ArrayList<Card>();
        	playerCards.add(new ReverseCard(CardColors.RED));
        	playerCards.add(new ReverseCard(CardColors.GREEN));
        	playerCards.add(new ReverseCard(CardColors.BLUE));
        	playerCards.add(new ReverseCard(CardColors.YELLOW));
            Player player1 = new Player(new Game(2, 0, 0), 0
                , playerCards, PlayerType.HUMAN);

            //Aseert player does not win with cards being left
            assertFalse(player1.hasPlayerWon());
            assert(player1.getAllCards().size() != 0);
            assertNotNull(player1.getCard(0));

            player1.getMaximumCardColor();
            assertEquals(PlayerType.HUMAN, player1.getPlayerType());

            //Assert player wins if no cards are left
            assert((new Player(new Game(2, 0, 0), 0, new ArrayList<Card>()
                , PlayerType.HUMAN)).hasPlayerWon());
        }
    }
	

    /**
     * Test game update state function after cards are played
     * @throws IllegalAccessException
     */
	@Test
	public void testUpdateGameState() throws IllegalAccessException
	{
        for(int i = 0; i < NUMBER_OF_TESTS; ++i)
        {
            Game Game1 = new Game(2, 0, 0);
            int discardPileNumber = Game1.getNumberOfCardsInDiscardDeck();
            Game1.updateGameState(Game1.getCurrentPlayer().getPlayerID(), 
                new NumberCard(Game1.getCurrentColor(), 1), new ArrayList<CardColors>());
            discardPileNumber += 1;
            assertEquals(discardPileNumber, Game1.getNumberOfCardsInDiscardDeck());
            assertEquals(Direction.CLOCKWISE, Game1.getCurrentDirection());
            Game1.updateGameState(Game1.getCurrentPlayer().getPlayerID(), 
                new ReverseCard(Game1.getCurrentColor()), new ArrayList<CardColors>());
            assertEquals(discardPileNumber + 1, Game1.getNumberOfCardsInDiscardDeck());
            assertEquals(Direction.COUNTERCLOCKWISE, Game1.getCurrentDirection());
            Game1.updateGameState(Game1.getCurrentPlayer().getPlayerID(), 
                new DrawTwoCard(Game1.getCurrentColor()), new ArrayList<CardColors>());
            
            Game Game2 = new Game(2, 0, 0);
            WildDrawFourCard card1 = new WildDrawFourCard();
            card1.setNextColor(CardColors.BLUE);
            Game2.updateGameState(Game2.getCurrentPlayer().getPlayerID(), 
                card1, new ArrayList<CardColors>());
        }
    }

	
    /**
     * Test if game can automatically reshuffle cards from discard pile
     * @throws IllegalAccessException
     */
	@Test
	public void testRepopulateDeckAndCustomRule2() throws IllegalAccessException
	{
        for(int i = 0; i < NUMBER_OF_TESTS; ++i)
        {
            Game Game1 = new Game(2, 0, 0);
            WildCard Card1 = new WildCard();
            Card1.setNextColor(CardColors.RED);
            Game1.updateGameState(Game1.getCurrentPlayer().getPlayerID(), 
                Card1, new ArrayList<CardColors>());
            // Consequtive stacking of reverse cards
            Game1.updateGameState(Game1.getCurrentPlayer().getPlayerID(), 
                new ReverseCard(CardColors.RED), new ArrayList<CardColors>());
            Game1.updateGameState(Game1.getCurrentPlayer().getPlayerID(), 
                new ReverseCard(CardColors.RED), new ArrayList<CardColors>());
            int CurrentPlayerIndex = Game1.getCurrentPlayerIndex();
            int currPlayerCards;
            int j = 0;

            //Consequtive stacking of skip cards
            for(int k = 0; k < 10; ++k)
            {
                Game1.updateGameState(Game1.getCurrentPlayer().getPlayerID(), 
                    new SkipCard(CardColors.RED), new ArrayList<CardColors>());
                assertEquals(CurrentPlayerIndex, Game1.getCurrentPlayerIndex());
            }
            
            // Consequtive stacking of wild draw four cards
            for(; j < Game1.getNumberOfCardsInCardDeck(); j += 4)
            {
                assertEquals(j, Game1.getDrawCardsNumber());
                WildDrawFourCard Card2 = new WildDrawFourCard();
                Card2.setNextColor(CardColors.RED);
                Game1.updateGameState(Game1.getCurrentPlayer().getPlayerID(), 
                    Card2, new ArrayList<CardColors>());
            }

            assertEquals(j, Game1.drawCardsFromDeck
                (Game1.getCurrentPlayer().getPlayerID()).size());

            //Test playing card by the player
            for(j = Game1.getCurrentPlayer().getNumberOfCards() - 1; j > -1; --j)
            {
                CurrentPlayerIndex = Game1.getCurrentPlayerIndex();
                currPlayerCards = Game1.getCurrentPlayer().getNumberOfCards();

                //Check if the player gets a penalty when an illegal card is played
                if(Game1.getCurrentPlayer().getCard(j).isLegal(Game1.getTopCard(), 
                    Game1.getCurrentColor(), Game1.getDrawCardsNumber(), 
                        Game1.getCurrentPlayer().getCardColors()))
                {
                    try { Game1.getCurrentPlayer().playCard(j); } catch(Exception ex) {}
                    assertEquals(currPlayerCards, 
                        Game1.getPlayer(CurrentPlayerIndex).getNumberOfCards() + 1);
                }
                else
                {
                    try { Game1.getCurrentPlayer().playCard(j); } catch(Exception ex) {}
                    assertNotEquals(currPlayerCards - 1, 
                        Game1.getPlayer(CurrentPlayerIndex).getNumberOfCards());
                }
            }
        }
    }


    /**
     * Test the choose player view frame and components
     */
    @Test
    public void testChoosePlayerView()
    {
        ChoosePlayerView view = new ChoosePlayerView();
        assertNotNull(view.startButton);
        assertNotNull(view.totalNumberOfPlayersSlider);
        assertNotNull(view.totalNumberOfPlayerField);
        assertNotNull(view.ViewFrame);
        view.launchFrame();
        view.destroyFrame();
    }


    /**
     * Test the game over view frame and components
     */
    @Test
    public void testGameOverView()
    {
        GameOverView view = new GameOverView();
        assertNotNull(view.ViewFrame);
        assertNotNull(view.messageLabel);
        assertNotNull(view.okButton);
        view.insertComponentsIntoFrame();
        view.launchFrame();
        view.destroyFrame();
    }


    /**
     * Test the game view frame and components
     */
    @Test
    public void testGameView()
    {
        GameView view = new GameView();
        assertNotNull(view.ViewFrame);
        view.insertComponentsIntoFrame();
        view.setSizeOfComponents();
        view.stopGame();
        assertFalse(view.skipDrawButton.isEnabled());
        view.launchFrame();
        view.destroyFrame();
    }


    /**
     * Test the card color and output colors match
     */
    @Test
    public void testGetColorFromCardColor()
    {
        assertEquals(Color.BLUE,
            new GameController().getColorFromCardColor(CardColors.BLUE));
        assertEquals(Color.RED, 
            new GameController().getColorFromCardColor(CardColors.RED));
        assertEquals(new Color(0,130,0), 
            new GameController().getColorFromCardColor(CardColors.GREEN));
        assertEquals(new Color(153,153,0), 
            new GameController().getColorFromCardColor(CardColors.YELLOW));
        assertEquals(Color.BLACK, 
            new GameController().getColorFromCardColor(CardColors.WILD));
    }


    /**Test game contoller and relevant components */
    @Test
    public void testGameController1()
    {
        GameController controller = new GameController();
        assert(controller.getCurrentGame() == null);
        controller.getChoosePlayerView().totalNumberOfPlayersSlider.setValue(5);
        controller.getChoosePlayerView().startButton.doClick();
        controller.getGameView().hideShowCardsButton.doClick();
        controller.getGameView().skipDrawButton.doClick();
        controller.getCurrentGame().getCurrentPlayer().
            getAllCards().add(0, new ReverseCard(controller.getCurrentGame().getCurrentColor()));
        controller.getGameView().playCardButton.doClick();
        controller.getCurrentGame().getCurrentPlayer().
            getAllCards().add(0, new WildDrawFourCard());

        for(int j = 0; j < controller.getCurrentGame().getInitialNumberOfCards(); ++j)
        {
            controller.getGameView().playCardButton.doClick();
            controller.getGameView().seekRightPlayerCardsButton.doClick();
            controller.getGameView().seekLeftPlayerCardsButton.doClick();
            controller.getGameView().seekRightPlayerCardsButton.doClick();
        }

        controller.getGameView().autoHideCheckBox.doClick();
        controller.getGameView().playCardButton.doClick();
        
        controller.getChoosePlayerView().numberOfAIPlayersSlider.setValue(2);
        controller.getChoosePlayerView().startButton.doClick();
        controller.launchGameOverView();
        controller.updateGameViewComponents();

        controller = new GameController();
        controller.getChoosePlayerView().startButton.doClick();
        controller.getCurrentGame().setGameOver();
        controller.getGameView().skipDrawButton.doClick();

        // GameController.main(new String [1]);
    }


    /**Test controller AI gameplay */
    @Test
    public void testGameController2() throws InterruptedException
    {
        Thread thread = new Thread()
        {
            public void run()
            {
                GameController controller = new GameController();
                controller.getChoosePlayerView().totalNumberOfPlayersSlider.setValue(5);
                controller.getChoosePlayerView().numberOfAIPlayersSlider.setValue(5);
                controller.getChoosePlayerView().numberOfStrategicAIPlayersSlider.setValue(3);
                controller.getChoosePlayerView().startButton.doClick();
            }
        };

        thread.start();
        Thread.sleep(5000);
        thread.interrupt();
    }


    /**Test baseline AI gameplay */
    @Test
    public void testBaselineAI() throws IllegalAccessException, IndexOutOfBoundsException
    {
        BaselineAI ai = new BaselineAI(new Game(2,0,0));
        ai.getCurrentGame().updateGameState(ai.getCurrentGame().getCurrentPlayer().getPlayerID(), 
            ai.getCurrentGame().getCurrentPlayer().getCard(ai.playCard(ai.getCurrentGame().getCurrentPlayer())), 
                new ArrayList<CardColors>());
        ai.getCurrentGame().getCurrentPlayer().getAllCards().clear();
        ai.getCurrentGame().getCurrentPlayer().getAllCards().add(new WildCard());
        ai.playCard(ai.getCurrentGame().getCurrentPlayer());
        ai.getCurrentGame().setDrawCardNumber(2);
        ai.playCard(ai.getCurrentGame().getCurrentPlayer());
        ai.getCurrentGame().setGameOver();
        ai.playCard(ai.getCurrentGame().getCurrentPlayer());
        assert(ai.getCurrentGame().isGameOver());
    }


    /**Test strategic AI gameplay */
    @Test
    public void testStrategicAI1() throws IllegalAccessException, IndexOutOfBoundsException
    {
        for(int i = 0; i < NUMBER_OF_TESTS; ++i)
        {
            StrategicAI ai = new StrategicAI(new Game(2,0,0));
            ai.getCurrentGame().getCurrentPlayer().getAllCards().clear();
            ai.getCurrentGame().getCurrentPlayer().getAllCards().add(new WildCard());
            ai.getCurrentGame().getCurrentPlayer().getAllCards().add(new WildCard());
            ai.getCurrentGame().updateGameState(ai.getCurrentGame().getCurrentPlayer().getPlayerID(), 
                ai.getCurrentGame().getCurrentPlayer().getCard(ai.playCard(ai.getCurrentGame().getCurrentPlayer())), 
                    new ArrayList<CardColors>());
            ai.getCurrentGame().getCurrentPlayer().getAllCards().clear();
            ai.getCurrentGame().getCurrentPlayer().getAllCards().add(new WildDrawFourCard());
            ai.getCurrentGame().getCurrentPlayer().getAllCards().add(new WildDrawFourCard());
            ai.getCurrentGame().updateGameState(ai.getCurrentGame().getCurrentPlayer().getPlayerID(), 
                ai.getCurrentGame().getCurrentPlayer().getCard(ai.playCard(ai.getCurrentGame().getCurrentPlayer())), 
                    new ArrayList<CardColors>());
            assertEquals(4, ai.getCurrentGame().getDrawCardsNumber());
            ai.getCurrentGame().getCurrentPlayer().getAllCards().clear();
            ai.getCurrentGame().getCurrentPlayer().getAllCards().add(new WildDrawFourCard());
            ai.getCurrentGame().updateGameState(ai.getCurrentGame().getCurrentPlayer().getPlayerID(), 
                ai.getCurrentGame().getCurrentPlayer().getCard(ai.playCard(ai.getCurrentGame().getCurrentPlayer())), 
                    new ArrayList<CardColors>());
            ai.getCurrentGame().setGameOver();
            assertEquals(-2, ai.playCard(ai.getCurrentGame().getCurrentPlayer()));
        }
    }


    /**Test draw card logic in strategic AI gameplay */
    @Test
    public void testStrategicAI2() throws IllegalAccessException, IndexOutOfBoundsException
    {
        for(int i = 0; i < NUMBER_OF_TESTS; ++i)
        {
            StrategicAI ai = new StrategicAI(new Game(2,0,0));
            ai.getCurrentGame().getCurrentPlayer().getAllCards().clear();
            ai.getCurrentGame().getCurrentPlayer().getAllCards().add(new DrawTwoCard(ai.getCurrentGame().getCurrentColor()));
            ai.getCurrentGame().getCurrentPlayer().getAllCards().add(new DrawTwoCard(ai.getCurrentGame().getCurrentColor()));
            ai.getCurrentGame().updateGameState(ai.getCurrentGame().getCurrentPlayer().getPlayerID(), 
                ai.getCurrentGame().getCurrentPlayer().getCard(ai.playCard(ai.getCurrentGame().getCurrentPlayer())), 
                    new ArrayList<CardColors>());
            ai.getCurrentGame().getCurrentPlayer().getAllCards().clear();
            ai.getCurrentGame().getCurrentPlayer().getAllCards().add(new DrawTwoCard(ai.getCurrentGame().getCurrentColor()));
            ai.getCurrentGame().updateGameState(ai.getCurrentGame().getCurrentPlayer().getPlayerID(), 
                ai.getCurrentGame().getCurrentPlayer().getCard(ai.playCard(ai.getCurrentGame().getCurrentPlayer())), 
                    new ArrayList<CardColors>());
            assertEquals(4, ai.getCurrentGame().getDrawCardsNumber());
            assert(ai.getCurrentGame().isGameOver());
        }
    }
}
