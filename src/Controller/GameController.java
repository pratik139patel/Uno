package src.Controller;

/**
 * This it the controller of the game that implements the main
 * game loop as well as defines the action in each view, and
 * kays out the gameplay of the game
 */

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Image;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import src.AI.BaselineAI;
import src.AI.GameAI;
import src.AI.StrategicAI;

import javax.swing.ImageIcon;
import javax.swing.JSlider;

import src.Model.Card.Card;
import src.Model.Card.ChangeColorCard;
import src.Model.Card.DrawTwoCard;
import src.Model.Card.NumberCard;
import src.Model.Card.ReverseCard;
import src.Model.Card.WildDrawFourCard;
import src.Model.Card.Card.CardColors;
import src.Model.GamePlay.Game;
import src.Model.GamePlay.Game.Direction;
import src.Model.GamePlay.Player.PlayerType;
import src.View.ChoosePlayerView;
import src.View.GameOverView;
import src.View.GameView;


public class GameController 
{
    /**Number of player selection */
    private ChoosePlayerView choosePlayerView;
    /**View when the game is in process */
    private GameView gameView;
    /**View when the game is over */
    private GameOverView gameOverView;
    /**The game model */
    private Game game;
    /**The index of the card which the plaer is viewing */
    private int playerCardIndex = 0;
    /**Images of player cards */
    private ArrayList<ImageIcon> playerCards;
    /**Strategic AI robot to play the game */
    private GameAI GameStrategicAIPlayer;
    /**Baseline AI robot to play the game */
    private GameAI GameBaselineAIPlayer;
    /**Put AI to sleep */
    private int AISleepTime = 1000;


    /**
     * @param sleepTime Number of ms AI should sleep
     */
    public void setAISleepTime(int sleepTime)
    {
        this.AISleepTime = sleepTime;
    }


    /**
     * Start number of player selection menu
     */
    public GameController()
    {
        this.choosePlayerView = new ChoosePlayerView();
        this.addActionTochoosePlayerView();
        this.choosePlayerView.launchFrame();
    }


    /**
     * @return Gets Choose Player view
     */
    public ChoosePlayerView getChoosePlayerView()
    {
        return this.choosePlayerView;
    }


    /**
     * @return Gets game view of the game
     */
    public GameView getGameView()
    {
        return this.gameView;
    }


    /**
     * Add action to choosePlayerView frame
     */
    private void addActionTochoosePlayerView() 
    {
        //User can select the number of players
        this.choosePlayerView.totalNumberOfPlayersSlider
            .addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e) 
            {
                choosePlayerView.totalNumberOfPlayerField.setText
                    (Integer.toString(((JSlider)e.getSource()).getValue()));

                choosePlayerView.numberOfAIPlayersSlider.setMaximum
                    (((JSlider)e.getSource()).getValue());
            }
        });

        //User can select the number of AI players within total number of players
        this.choosePlayerView.numberOfAIPlayersSlider
            .addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e) 
            {
                choosePlayerView.numberOfAIPlayerField.setText
                    (Integer.toString(((JSlider)e.getSource()).getValue()));

                choosePlayerView.numberOfStrategicAIPlayersSlider.setMaximum
                    (((JSlider)e.getSource()).getValue());
            }
        });

        //User can select the number of Strategic AI players within total AI players
        this.choosePlayerView.numberOfStrategicAIPlayersSlider
            .addChangeListener(new ChangeListener()
            {
                @Override
                public void stateChanged(ChangeEvent e) 
                {
                    choosePlayerView.numberOfStrategicAIPlayerField.setText
                        (Integer.toString(((JSlider)e.getSource()).getValue()));
                }
                
            });

        //Player can start the game with designated number of players
        this.choosePlayerView.startButton
            .addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                launchUNO();              
            }
        });
    }


    /**
     * Add action to GameView frame
     */
    private void addActionToGameMenu() 
    {
        this.addActionToLeftAndRightSeekButtonInGameMenu();
        this.addActionToHideShowAndskipDrawButtonInGameView();
        this.addActionToPlayButtonInGameView();
    }


    /**
     * Adds action to play button in game view
     */
    private void addActionToPlayButtonInGameView() 
    {
        //Player can play card of their choice and the game state is updated
        this.gameView.playCardButton
            .addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if(game.getCurrentPlayer().getCard(playerCardIndex) 
                    instanceof ChangeColorCard)
                {
                    //Set the card's next color and next direction if it is a wild 
                    //or wilddrawfour card
                    ((ChangeColorCard) game.getCurrentPlayer().getCard(playerCardIndex))
                        .setNextColor((String) gameView.changeColorDropDownBox.getSelectedItem() 
                            == "DEFAULT" ? game.getCurrentColor() : src.Model.Card.Card.CardColors.valueOf
                                ((String) gameView.changeColorDropDownBox.getSelectedItem()));
                    ((ChangeColorCard) game.getCurrentPlayer().getCard(playerCardIndex))
                        .setNextDirection(Direction.valueOf(
                            (String) gameView.changeDirectionDropDownBox.getSelectedItem()));
                }

                playCardAndUpdateGameViewComponents(playerCardIndex);
            }
        });
    }


    /**
     * @param playerCardIndex Card index to play from the player hand
     */
    private void playCardAndUpdateGameViewComponents(int playerCardIndex)
    {
        playRequestedCard(playerCardIndex);

        if(this.gameView.autoHideCheckBox.isSelected()) 
        { 
            this.gameView.hideShowCardsButton.setText("SHOW");
        }

        updateGameViewComponents();
    }


    /**
     * @return Gets the game held by the controller
     */
    public Game getCurrentGame()
    {
        return this.game;
    }


    /**
     * Adds function to skip and hide buttons in game view frame
     */
    private void addActionToHideShowAndskipDrawButtonInGameView() 
    {
        //Player can skip their turn or draw cards
        this.gameView.skipDrawButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                playCardAndUpdateGameViewComponents(-1);
            }
        });

        //Player can hide/Show their cards
        this.gameView.hideShowCardsButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                gameView.hideShowCardsButton.setText(
                        (gameView.hideShowCardsButton.getText() == "HIDE") 
                            ? ("SHOW") : ("HIDE"));
                updateSelectedPlayerCardInGameView();
            }
        });
    }


    /**
     * Adds function to left and right seek button in game view
     */
    private void addActionToLeftAndRightSeekButtonInGameMenu() 
    {
        //Player can seek the cards in their hand to the left
        this.gameView.seekLeftPlayerCardsButton
            .addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if(gameView.seekLeftPlayerCardsButton.isEnabled())
                {
                    if(playerCardIndex - 1 <= 0)
                    {
                        gameView.seekLeftPlayerCardsButton.setEnabled(false);
                    }

                    gameView.seekRightPlayerCardsButton.setEnabled(true);
                    playerCardIndex -= 1;
                    updateSelectedPlayerCardInGameView();
                }
            }
        });

        //Player can seek the cards in their hand to the right
        this.gameView.seekRightPlayerCardsButton
            .addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if(gameView.seekRightPlayerCardsButton.isEnabled())
                {
                    if(playerCardIndex + 2 >= game.getCurrentPlayer().getNumberOfCards())
                    {
                        gameView.seekRightPlayerCardsButton.setEnabled(false);
                    }
                    
                    gameView.seekLeftPlayerCardsButton.setEnabled(true);
                    playerCardIndex += 1;
                    updateSelectedPlayerCardInGameView();
                }
            }
        });
    }


    /**
     * @param cardIdx Card index to play from the player hand
     */
    private void playRequestedCard(int cardIdx)
    {
        try
        {
            this.game.getCurrentPlayer().playCard(cardIdx);
            this.gameView.errorMessageField.setText("");
        }
        catch(IllegalArgumentException | IllegalAccessException ex)
        {
            this.gameView.errorMessageField.setText(ex.getMessage());
        }
    }


    /**
     * Show the player's selected card in the hand
     */
    private void updateSelectedPlayerCardInGameView()
    {
        if(this.gameView.hideShowCardsButton.getText() == "HIDE")
        {
            //If cards are not hidden then show the cards to the player
            this.gameView.playCardButton.setEnabled(true);
            this.gameView.selectedPlayerCard.setIcon
                (this.playerCards.get(this.playerCardIndex));

            if(game.getCurrentPlayer().getCard(this.playerCardIndex) 
                instanceof ChangeColorCard)
            {
                this.gameView.changeColorDropDownBox.setEnabled(true);
                this.gameView.changeDirectionDropDownBox.setEnabled(true);
            }
            else
            {
                this.gameView.changeColorDropDownBox.setEnabled(false);
                this.gameView.changeDirectionDropDownBox.setEnabled(false);
            }
        }
        else
        {
            //If cards are hidden then show a blank image and disable play button
            this.hidePlayerCards();
        }
    }


    /**
     * Hide player cards and diable play button
     */
    private void hidePlayerCards() 
    {
        this.gameView.selectedPlayerCard.setIcon(this.gameView.blankCardImage);
        this.gameView.changeColorDropDownBox.setEnabled(false);
        this.gameView.changeDirectionDropDownBox.setEnabled(false);
        this.gameView.playCardButton.setEnabled(false);
    }


    /**
     * Update the game view frame
     */
    public void updateGameViewComponents()
    {
        this.updatePenaltiesCounterLabelInGameView();
        this.updateCurrentColorLabelInGameView();
        this.updateCurrentDirectionLabelInGameView();
        this.updateTopCardInGameView();

        if(this.game.isGameOver())
        {
            this.launchGameOverView();
        }
        else
        {
            this.playerCardIndex = 0;
            this.updateCurrentPlayerLabelInGameView();
            this.gameView.seekLeftPlayerCardsButton.setEnabled(false);

            if(this.game.getCurrentPlayer().getPlayerType() != PlayerType.HUMAN) 
            {
                letAIplay();
            }
            else
            {
                letHumanPlay();
            }
        }
    }


    /**
     * Allow human player to play
     */
    private void letHumanPlay()
    {
        this.gameView.skipDrawButton.setEnabled(true);
        this.gameView.hideShowCardsButton.setEnabled(true);
        this.updatePlayerCardsInGameView();
        this.updateSelectedPlayerCardInGameView();
    }


    /**
     * Allow AI player to play
     */
    private void letAIplay() 
    {
        this.hidePlayerCards();
        this.gameView.skipDrawButton.setEnabled(false);
        this.gameView.hideShowCardsButton.setEnabled(false);
        this.gameView.seekRightPlayerCardsButton.setEnabled(false);
        this.gameView.errorMessageField.setText
            (((this.game.getCurrentPlayer().getPlayerType() == PlayerType.STRATEGIC_AI) ? 
                ("Strategic") : ("Baseline")) + " AI's Turn!");
        
        new Thread()
        {
            public void run()
            {
                try
                {
                    Thread.sleep(AISleepTime);
                } 
                catch(InterruptedException ex) 
                {
                    /**If there is interruption, then continue with AI playing card */
                }

                //Decide which AI has to play
                if(game.getCurrentPlayer().getPlayerType() == 
                    PlayerType.STRATEGIC_AI)
                {
                    playCardAndUpdateGameViewComponents
                        (GameStrategicAIPlayer.playCard(game.getCurrentPlayer()));
                }
                else
                {
                    playCardAndUpdateGameViewComponents
                        (GameBaselineAIPlayer.playCard(game.getCurrentPlayer()));
                }
            }
        }.start();
    }


    /**
     * Adds button action in game over view frame
     */
    private void addActionToGameOverMenu()
    {
        this.gameOverView.okButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                gameOverView.destroyFrame();
                gameView.destroyFrame();
            }
        });
    }


    /**
     * Launch the game over view frame
     */
    public void launchGameOverView() 
    {
        this.gameView.selectedPlayerCard.setIcon(this.gameView.blankCardImage);
        this.gameView.stopGame();
        this.gameOverView = new GameOverView();
        this.gameOverView.messageLabel.setText("WINNER OF THIS GAME IS PLAYER: " 
            + Integer.toString(this.game.getCurrentPlayer().getPlayerID()));
        this.addActionToGameOverMenu();
        this.gameOverView.insertComponentsIntoFrame();
        this.gameOverView.launchFrame();
    }


    /**
     * Launch the game view frame
     */
    private void launchUNO()
    {
        if(this.gameView != null)
        {
            this.gameView.destroyFrame();
        }

        this.game = new Game(this.choosePlayerView.totalNumberOfPlayersSlider.getValue(), 
            this.choosePlayerView.numberOfAIPlayersSlider.getValue(), 
                this.choosePlayerView.numberOfStrategicAIPlayersSlider.getValue());
        this.gameView = new GameView();
        this.playerCards = new ArrayList<ImageIcon>();
        this.GameBaselineAIPlayer = new BaselineAI(this.game);
        this.GameStrategicAIPlayer = new StrategicAI(this.game);
        this.addActionToGameMenu();
        this.updateGameViewComponents();
        this.gameView.setSizeOfComponents();
        this.gameView.launchFrame();
    }


    /**
     * Update the player cards in game view
     */
    private void updatePlayerCardsInGameView() 
    {
        this.playerCards.clear();

        for(Card card : this.game.getCurrentPlayer().getAllCards())
        {
            this.playerCards.add(this.getCardImageIconFromCard(card));
        }

        this.gameView.seekRightPlayerCardsButton.setEnabled
            (this.game.getCurrentPlayer().getNumberOfCards() > 1);
    }


    /**
     * Update the penalties label in the game view
     */
    private void updateCurrentPlayerLabelInGameView()
    {
        this.gameView.currentPlayerLabel.setText("PLAYER: " + 
            Integer.toString(this.game.getCurrentPlayer().getPlayerID()));
    }


    /**
     * Update the penalties label in the game view
     */
    private void updatePenaltiesCounterLabelInGameView()
    {
        this.gameView.penaltiesCounterLabel.setText("PENALTIES: " + 
            Integer.toString(this.game.getDrawCardsNumber()));

        this.gameView.skipDrawButton.setText
            (this.game.getDrawCardsNumber() == 0 ? "SKIP" : "DRAW");
    }


    /**
     * Update the current color label in the game view
     */
    private void updateCurrentColorLabelInGameView()
    {
        this.gameView.currentColorLabel.setText("COLOR: " + 
            this.game.getCurrentColor().toString());
        this.gameView.currentColorLabel.setForeground(
            this.getColorFromCardColor(this.game.getCurrentColor()));
    }


    /**
     * Converts CardColors into Color
     * @param color CardColors to be tranformed into a Color
     * @return Corrosponding Color of CardColor
     */
    public Color getColorFromCardColor(CardColors color)
    {
        switch(color)
        {
            case BLUE: return Color.BLUE;
            case GREEN: return new Color(0,130,0);
            case RED: return Color.RED;
            case YELLOW: return new Color(153,153,0);
            default: return Color.BLACK;
        }
    }


    /**
     * Update the direction label of the game view
     */
    private void updateCurrentDirectionLabelInGameView()
    {
        if(this.game.getCurrentDirection() == Direction.CLOCKWISE)
        {
            this.gameView.currentDirectionLabel.setText("DIRECTION: CLOCK");
        }
        else
        {
            this.gameView.currentDirectionLabel.setText("DIRECTION: COUNTER");
        }
    }


    /**
     * Update the top card in the game view
     */
    private void updateTopCardInGameView()
    {
        this.gameView.topCard.setIcon
            (this.getCardImageIconFromCard(this.game.getTopCard()));
    }


    /**
     * Gets the card image address from the card provided
     * @param card Card that the user wants the address of
     * @return The image of the card
     */
    private ImageIcon getCardImageIconFromCard(Card card)
    {
        String imageAddress = "src/Resources/CardImages/";

        if(card instanceof ChangeColorCard)
        {
            imageAddress += "WILD";

            if(card instanceof WildDrawFourCard)
            {
                imageAddress += "D";
            }
        }
        else
        { 
            imageAddress += card.getCardColor().toString();

            if(card instanceof NumberCard)
            {
                imageAddress += Integer.toString(
                    ((NumberCard) card).getCardNumber());
            }
            else if(card instanceof DrawTwoCard)
            {
                imageAddress += "D";
            }
            else if(card instanceof ReverseCard)
            {
                imageAddress += "R";
            }
            else
            {
                imageAddress += "S";
            }
        }

        //Return the image of the card in the file location after rescaling it
        return new ImageIcon(new ImageIcon(imageAddress + ".png").getImage()
            .getScaledInstance(160, 240, Image.SCALE_SMOOTH));
    }
}
