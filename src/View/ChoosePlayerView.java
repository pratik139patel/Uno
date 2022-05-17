package src.View;

/**
 * Choose Player View lays out view for the game where
 * a player can choose the number of player for the game
 */

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JTextField;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;

import src.Model.GamePlay.Game;

public class ChoosePlayerView extends View
{
    /**Silder to choose total number of players */
    public JSlider totalNumberOfPlayersSlider;
    /**Silder to choose number of AI players */
    public JSlider numberOfAIPlayersSlider;
    /**Silder to choose number of Strategic AI players */
    public JSlider numberOfStrategicAIPlayersSlider;
    /**Constraints to specify location of the component in the JFrame */
    private GridBagConstraints constr;
    /**Button to start the UNO game */
    public JButton startButton;
    /**Number of total players selected by the user */
    public JTextField totalNumberOfPlayerField;
    /**Number of AI player selected by the user */
    public JTextField numberOfAIPlayerField;
    /**Number of Strategic AI player selected by the user */
    public JTextField numberOfStrategicAIPlayerField;
    /**Number of player label */
    public JLabel totalNumberOfPlayerLabel;
    /**Number of AI player label */
    public JLabel numberOfAIPlayerLabel;
    /**Number of Strategic AI player label */
    public JLabel numberOfStrategicAIPlayerLabel;


    /**
     * ChoosePlayerView constructor
     */
    public ChoosePlayerView()
    {
        super();
        this.beautifyJSlider();
        this.beautifyJTextField();
        this.beautifyJLabels();
        this.insertComponentsIntoFrame();
    }


    /**
     * Deploy the frame to be viewd by the user
     */
    @Override
    public void launchFrame() 
    {
        this.ViewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.ViewFrame.pack();
        this.ViewFrame.setLocationRelativeTo(null);
        this.ViewFrame.setVisible(true);
    }


    /**
     * Insert all the components in the JFrame
     */
    @Override
    public void insertComponentsIntoFrame() 
    {
        this.constr.insets = new Insets(5,10,5,5);
        this.ViewFrame.add(this.totalNumberOfPlayerLabel, this.constr);
        this.constr.gridx = 1;
        this.ViewFrame.add(this.totalNumberOfPlayersSlider, this.constr);
        this.constr.gridy = 1;
        this.constr.gridx = 0;
        this.ViewFrame.add(this.numberOfAIPlayerLabel, this.constr);
        this.constr.gridx = 1;
        this.ViewFrame.add(this.numberOfAIPlayersSlider, this.constr);
        this.constr.gridy = 2;
        this.constr.gridx = 0;
        this.ViewFrame.add(this.numberOfStrategicAIPlayerLabel, this.constr);
        this.constr.gridx = 1;
        this.ViewFrame.add(this.numberOfStrategicAIPlayersSlider, this.constr);
        this.constr.gridy = 0;
        this.constr.insets.left = 5;
        this.constr.insets.right = 10;
        this.constr.gridx = 2;
        this.ViewFrame.add(this.totalNumberOfPlayerField, this.constr);
        this.constr.gridy = 1;
        this.ViewFrame.add(this.numberOfAIPlayerField, this.constr);
        this.constr.gridy = 2;
        this.ViewFrame.add(this.numberOfStrategicAIPlayerField, this.constr);
        this.constr.insets.left = 10;
        this.constr.insets.bottom = 10;
        this.constr.gridx = 0;
        this.constr.gridy = 3;
        this.constr.gridwidth = 3;
        this.ViewFrame.add(this.startButton, this.constr);
    }


    /**
     * Add touches to the slider to make it look good
     */
    private void beautifyJSlider()
    {
        this.totalNumberOfPlayersSlider.setMajorTickSpacing(1);
        this.totalNumberOfPlayersSlider.setPaintTicks(true);
        this.totalNumberOfPlayersSlider.setPaintLabels(true);
        this.totalNumberOfPlayersSlider.setOrientation(JSlider.HORIZONTAL);
        this.totalNumberOfPlayersSlider.setPreferredSize
            (new Dimension(this.totalNumberOfPlayersSlider.getPreferredSize().width+50, 
                this.totalNumberOfPlayersSlider.getPreferredSize().height));
        this.numberOfAIPlayersSlider.setMajorTickSpacing(1);
        this.numberOfAIPlayersSlider.setPaintTicks(true);
        this.numberOfAIPlayersSlider.setPaintLabels(true);
        this.numberOfAIPlayersSlider.setOrientation(JSlider.HORIZONTAL);
        this.numberOfAIPlayersSlider.setPreferredSize
            (this.totalNumberOfPlayersSlider.getPreferredSize());
        this.numberOfStrategicAIPlayersSlider.setMajorTickSpacing(1);
        this.numberOfStrategicAIPlayersSlider.setPaintTicks(true);
        this.numberOfStrategicAIPlayersSlider.setPaintLabels(true);
        this.numberOfStrategicAIPlayersSlider.setOrientation(JSlider.HORIZONTAL);
        this.numberOfStrategicAIPlayersSlider.setPreferredSize
            (this.totalNumberOfPlayersSlider.getPreferredSize());
    }


    /**
     * Initilaize all the components to be put on the JFrame
     */
    @Override
    public void initializeComponents()
    {
        this.ViewFrame = new JFrame("Choose Players");
        this.ViewFrame.setLayout(new GridBagLayout());
        this.totalNumberOfPlayersSlider = 
            new JSlider(Game.getMinimumPlayers(), Game.getMaximumPlayers(), 
                Game.getMinimumPlayers());
        this.numberOfAIPlayersSlider = 
            new JSlider(Game.getMinimumAIPlayers(), 
                Game.getMinimumPlayers(), Game.getMinimumAIPlayers());
        this.numberOfStrategicAIPlayersSlider = 
            new JSlider(Game.getMinimumAIPlayers(), 
                this.numberOfAIPlayersSlider.getValue(), Game.getMinimumAIPlayers());
        this.constr = new GridBagConstraints();
        this.startButton = new JButton("Start");
        this.totalNumberOfPlayerField = new JTextField(3);
        this.numberOfAIPlayerField = new JTextField(3);
        this.numberOfStrategicAIPlayerField = new JTextField(3);
        this.totalNumberOfPlayerLabel = new JLabel("Total Players: ");
        this.numberOfAIPlayerLabel = new JLabel("AI Players: ");
        this.numberOfStrategicAIPlayerLabel = new JLabel("Strategic AI: ");
    }


    /**
     * Add touches to the TextField to make it look good
     */
    private void beautifyJTextField()
    {
        this.totalNumberOfPlayerField.setText(Integer.toString(Game.getMinimumPlayers()));
        this.totalNumberOfPlayerField.setEditable(false);
        this.totalNumberOfPlayerField.setHorizontalAlignment(JTextField.CENTER);
        this.totalNumberOfPlayerField.setBackground(Color.BLACK);
        this.totalNumberOfPlayerField.setForeground(Color.YELLOW);
        this.totalNumberOfPlayerField.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        this.numberOfAIPlayerField.setText(Integer.toString(Game.getMinimumAIPlayers()));
        this.numberOfAIPlayerField.setEditable(false);
        this.numberOfAIPlayerField.setHorizontalAlignment(JTextField.CENTER);
        this.numberOfAIPlayerField.setBackground(Color.BLACK);
        this.numberOfAIPlayerField.setForeground(Color.GREEN);
        this.numberOfAIPlayerField.setFont(this.totalNumberOfPlayerField.getFont());
        this.numberOfStrategicAIPlayerField.setText(Integer.toString(Game.getMinimumAIPlayers()));
        this.numberOfStrategicAIPlayerField.setEditable(false);
        this.numberOfStrategicAIPlayerField.setHorizontalAlignment(JTextField.CENTER);
        this.numberOfStrategicAIPlayerField.setBackground(Color.BLACK);
        this.numberOfStrategicAIPlayerField.setForeground(Color.RED);
        this.numberOfStrategicAIPlayerField.setFont(this.totalNumberOfPlayerField.getFont());
    }

    
    /**
     * Add touches to the label to make it look good
     */
    private void beautifyJLabels()
    {
        this.numberOfAIPlayerLabel.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        this.totalNumberOfPlayerLabel.setFont(this.numberOfAIPlayerLabel.getFont());
        this.numberOfStrategicAIPlayerLabel.setFont(this.numberOfAIPlayerLabel.getFont());
    }
}
