package src.View;

/**
 * Game view lays out the foundation of the look during the game play.
 */

import src.Model.Card.Card.CardColors;
import src.Model.GamePlay.Game.Direction;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;

public class GameView extends View
{
    /**Constraints to specify location of the component in the JFrame */
    private GridBagConstraints contr;
    /**Label to keep track of current player */
    public JLabel currentPlayerLabel;
    /**Label to keep track of current penalties */
    public JLabel penaltiesCounterLabel;
    /**Label to keep track of current color */
    public JLabel currentColorLabel;
    /**Label to keep track of current direction */
    public JLabel currentDirectionLabel;
    /**Label to signify top card */
    public JLabel topCardLabel;
    /**Top card image in the discard pile */
    public JLabel topCard;
    /**Selected card image of the player */
    public JLabel selectedPlayerCard;
    /**Button to skip turn or draw cards */
    public JButton skipDrawButton;
    /**Button to play the card */
    public JButton playCardButton;
    /**Button to seek left cards */
    public JButton seekLeftPlayerCardsButton;
    /**Button to seek right cards */
    public JButton seekRightPlayerCardsButton;
    /**Toggle to hide/show cards */
    public JButton hideShowCardsButton;
    /**Error message for the player */
    public JTextField errorMessageField;
    /**Drop down box to select next direction */
    public JComboBox<String> changeDirectionDropDownBox;
    /**Drop down box to select next color */
    public JComboBox<String> changeColorDropDownBox;
    /**Label to indicate changeDirectionDropDownBox */
    public JLabel changeDirectionLabel;
    /**Label to indicate changeColorDropDownBox */
    public JLabel changeColorLabel;
    /**Auto hide check box */
    public JCheckBox autoHideCheckBox;
    /**Blank card to mask the player's cards */
    public final ImageIcon blankCardImage = new ImageIcon
        (new ImageIcon("src/Resources/CardImages/BLANK.png").getImage()
            .getScaledInstance(160, 240, Image.SCALE_SMOOTH));


    /**
     * Initialize the GameView
     */
    public GameView()
    {
        super();
        this.centerAlignText();
        this.setFontOfLabels();;
        this.enableLabelOpacity();
        this.setLabelBackground();
        this.beautifyErrorMessageField();
        this.insertComponentsIntoFrame();
    }


    /**
     * Make error message field pretty
     */
    private void beautifyErrorMessageField() 
    {
        this.errorMessageField.setFont(this.currentPlayerLabel.getFont());
        this.errorMessageField.setBackground(Color.RED);
        this.errorMessageField.setForeground(Color.WHITE);
        this.errorMessageField.setEditable(false);
    }


    /**
     * Deploy the GameView to the players
     */
    @Override
    public void launchFrame() 
    {
        this.ViewFrame.pack();
        this.ViewFrame.setLocationRelativeTo(null);
        this.ViewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.ViewFrame.setVisible(true);
    }


    /**
     * Add all the components to the GameView JFrame
     */
    @Override
    public void insertComponentsIntoFrame() 
    {
        this.contr.insets = new Insets(10,10,5,5);
        this.ViewFrame.add(this.currentPlayerLabel, this.contr);
        this.contr.insets.left = 5;
        this.contr.gridx = 1;
        this.ViewFrame.add(this.penaltiesCounterLabel, this.contr);
        this.contr.gridx = 3;
        this.ViewFrame.add(this.currentDirectionLabel, this.contr);
        this.contr.insets.right = 10;
        this.contr.gridx = 4;
        this.ViewFrame.add(this.currentColorLabel, this.contr);
        this.contr.gridy = 1;
        this.contr.gridx = 2;
        this.contr.insets.top = 15;
        this.contr.insets.right = 5;
        this.ViewFrame.add(this.topCardLabel, this.contr);
        this.contr.gridy = 2;
        this.contr.insets.top = 5;
        this.ViewFrame.add(this.topCard, this.contr);
        this.contr.gridy = 3;
        this.contr.gridx = 1;
        this.ViewFrame.add(this.changeColorLabel, this.contr);
        this.contr.gridx = 3;
        this.ViewFrame.add(this.changeDirectionLabel, this.contr);
        this.contr.gridx = 4;
        this.ViewFrame.add(this.autoHideCheckBox, this.contr);
        this.contr.gridy = 4;
        this.contr.gridx = 0;
        this.contr.insets.left = 10;
        this.ViewFrame.add(this.skipDrawButton, this.contr);
        this.contr.insets.left = 5;
        this.contr.gridx = 1;
        this.ViewFrame.add(this.changeColorDropDownBox, this.contr);
        this.contr.gridx = 2;
        this.ViewFrame.add(this.playCardButton, this.contr);
        this.contr.gridx = 3;
        this.ViewFrame.add(this.changeDirectionDropDownBox, this.contr);
        this.contr.insets.right = 10;
        this.contr.gridx = 4;
        this.ViewFrame.add(this.hideShowCardsButton, this.contr);
        this.contr.gridy = 5;
        this.contr.gridx = 1;
        this.contr.insets.right = 5;
        this.ViewFrame.add(this.seekLeftPlayerCardsButton, this.contr);
        this.contr.gridx = 2;
        this.contr.insets.bottom = 10;
        this.ViewFrame.add(this.selectedPlayerCard, this.contr);
        this.contr.gridx = 3;
        this.contr.insets.bottom = 5;
        this.ViewFrame.add(this.seekRightPlayerCardsButton, this.contr);
        this.contr.gridy = 6;
        this.contr.gridx = 0;
        this.contr.gridwidth = 5;
        this.ViewFrame.add(this.errorMessageField, this.contr);
    }


    /**
     * Sets the background of all the label to have a shaded look
     */
    private void setLabelBackground() 
    {
        this.currentPlayerLabel.setBackground(new Color(220,220,220));
        this.penaltiesCounterLabel.setBackground
            (this.currentPlayerLabel.getBackground());
        this.currentColorLabel.setBackground
            (this.currentPlayerLabel.getBackground());
        this.currentDirectionLabel.setBackground
            (this.currentPlayerLabel.getBackground());
        this.topCardLabel.setBackground
            (this.currentPlayerLabel.getBackground());
        this.changeDirectionLabel.setBackground
            (this.currentPlayerLabel.getBackground());
        this.changeColorLabel.setBackground
            (this.currentPlayerLabel.getBackground());
    }


    /**
     * Set the opacity of the labels to true in order to show 
     * the background color
     */
    private void enableLabelOpacity() 
    {
        this.currentPlayerLabel.setOpaque(true);
        this.currentColorLabel.setOpaque(true);
        this.penaltiesCounterLabel.setOpaque(true);
        this.currentDirectionLabel.setOpaque(true);
        this.topCardLabel.setOpaque(true);
        this.changeDirectionLabel.setOpaque(true);
        this.changeColorLabel.setOpaque(true);
    }


    /**
     * Change font of the JLabels in GameView
     */
    private void setFontOfLabels() 
    {
        this.currentPlayerLabel.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        this.penaltiesCounterLabel.setFont(this.currentPlayerLabel.getFont());
        this.currentColorLabel.setFont(this.currentPlayerLabel.getFont());
        this.currentDirectionLabel.setFont(this.currentPlayerLabel.getFont());
        this.topCardLabel.setFont(this.currentPlayerLabel.getFont());
        this.changeDirectionLabel.setFont(this.currentPlayerLabel.getFont());
        this.changeColorLabel.setFont(this.currentPlayerLabel.getFont());
        this.autoHideCheckBox.setFont(new Font(Font.SERIF, Font.BOLD, 12));
    }


    /**
     * Initializes all the fields of GameView
     */
    @Override
    public void initializeComponents() 
    {
        this.ViewFrame = new JFrame("UNO");
        this.currentPlayerLabel = new JLabel();
        this.changeDirectionLabel = new JLabel("CHANGE DIRECTION:");
        this.changeColorLabel = new JLabel("CHANGE COLOR:");
        this.penaltiesCounterLabel = new JLabel();
        this.currentColorLabel = new JLabel();
        this.currentDirectionLabel = new JLabel();
        this.topCardLabel = new JLabel("TOP CARD:");
        this.topCard = new JLabel();
        this.contr = new GridBagConstraints();
        this.skipDrawButton = new JButton("SKIP");
        this.hideShowCardsButton = new JButton("SHOW");
        this.seekLeftPlayerCardsButton = new JButton("LEFT");
        this.seekRightPlayerCardsButton = new JButton("RIGHT");
        this.playCardButton = new JButton("PLAY");
        this.changeDirectionDropDownBox = new JComboBox<String>();
        this.changeColorDropDownBox = new JComboBox<String>();
        this.selectedPlayerCard = new JLabel();
        this.ViewFrame.setLayout(new GridBagLayout());
        this.errorMessageField = new JTextField();
        this.autoHideCheckBox = new JCheckBox("AUTO HIDE");

        //Populate changeDirectionDropDownBox with all direction
        for(Direction direction : Direction.values())
        {
            this.changeDirectionDropDownBox.addItem(direction.toString());
        }

        //Populates changeColorDropDownBox with all cardcolors except WILD
        CardColors[] cardColors = CardColors.values();
        this.changeColorDropDownBox.addItem("DEFAULT");
        for(int i = 1; i < cardColors.length; ++i)
        {
            this.changeColorDropDownBox.addItem(cardColors[i].toString());
        }
    }


    /**
     * Center aligns all the text within the JLabel and JComboBox
     */
    private void centerAlignText() 
    {
        this.currentColorLabel.setHorizontalAlignment(JLabel.CENTER);
        this.penaltiesCounterLabel.setHorizontalAlignment(JLabel.CENTER);
        this.topCardLabel.setHorizontalAlignment(JLabel.CENTER);
        this.currentDirectionLabel.setHorizontalAlignment(JLabel.CENTER);
        this.errorMessageField.setHorizontalAlignment(JLabel.CENTER);
        this.currentPlayerLabel.setHorizontalAlignment(JLabel.CENTER);
        this.changeDirectionLabel.setHorizontalAlignment(JLabel.CENTER);
        this.changeColorLabel.setHorizontalAlignment(JLabel.CENTER);
        this.autoHideCheckBox.setHorizontalAlignment(JLabel.CENTER);

        ((JLabel)this.changeColorDropDownBox.getRenderer())
            .setHorizontalAlignment(JLabel.CENTER);
        ((JLabel)this.changeDirectionDropDownBox.getRenderer())
            .setHorizontalAlignment(JLabel.CENTER);
    }


    /**
     * Uniform size is set of all components
     */
    public void setSizeOfComponents() 
    {
        this.currentDirectionLabel.setPreferredSize(new Dimension(
            this.currentDirectionLabel.getPreferredSize().width + 73, 
                this.currentDirectionLabel.getPreferredSize().height));
        this.errorMessageField.setPreferredSize(new Dimension(
            this.currentDirectionLabel.getPreferredSize().width * 5, 
                this.errorMessageField.getPreferredSize().height));
        this.changeColorDropDownBox.setPreferredSize
            (new Dimension(this.currentDirectionLabel.getPreferredSize().width, 
                this.changeColorDropDownBox.getPreferredSize().height));
        this.skipDrawButton.setPreferredSize
            (new Dimension(this.currentDirectionLabel.getPreferredSize().width,
                this.skipDrawButton.getPreferredSize().height));

        this.hideShowCardsButton.setPreferredSize
            (this.skipDrawButton.getPreferredSize());
        this.seekRightPlayerCardsButton.setPreferredSize
            (this.skipDrawButton.getPreferredSize());
        this.playCardButton.setPreferredSize
            (this.skipDrawButton.getPreferredSize());
        this.seekLeftPlayerCardsButton.setPreferredSize
            (this.skipDrawButton.getPreferredSize());
        this.changeDirectionDropDownBox.setPreferredSize
            (this.changeColorDropDownBox.getPreferredSize());
        this.currentPlayerLabel.setPreferredSize
            (this.currentDirectionLabel.getPreferredSize());
        this.penaltiesCounterLabel.setPreferredSize
            (this.currentDirectionLabel.getPreferredSize());
        this.currentColorLabel.setPreferredSize
            (this.currentDirectionLabel.getPreferredSize());
        this.topCardLabel.setPreferredSize
            (this.currentDirectionLabel.getPreferredSize());
        this.changeDirectionLabel.setPreferredSize
            (this.currentDirectionLabel.getPreferredSize());
        this.changeColorLabel.setPreferredSize
            (this.currentDirectionLabel.getPreferredSize());
        this.autoHideCheckBox.setPreferredSize
            (this.currentDirectionLabel.getPreferredSize());
    }


    /**
     * Stop the game, and freeze the GameView JFrame
     */
    public void stopGame() 
    {
        this.skipDrawButton.setEnabled(false);
        this.seekLeftPlayerCardsButton.setEnabled(false);
        this.seekRightPlayerCardsButton.setEnabled(false);
        this.hideShowCardsButton.setEnabled(false);
        this.changeColorDropDownBox.setEnabled(false);
        this.changeDirectionDropDownBox.setEnabled(false);
        this.playCardButton.setEnabled(false);
        this.autoHideCheckBox.setEnabled(false);
        this.errorMessageField.setText("Game Over!!!");
    }
}
