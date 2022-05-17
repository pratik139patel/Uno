package src.View;

/**
 * Game over view defines the window look of the end of the game view
 */

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.Font;

public class GameOverView extends View
{
    /**Message to show which player won */
    public JLabel messageLabel;
    /**Button to terminate the current game */
    public JButton okButton;
    /**Constraints to specify location of the component in the JFrame */
    private GridBagConstraints constr;
    

    /**
     * Constructor of the GameOverView
     */
    public GameOverView()
    {
        super();
    }


    /**
     * Inserts the components in the GameOverView JFrame
     */
    @Override
    public void insertComponentsIntoFrame() 
    {
        this.constr.insets = new Insets(10, 10, 10, 10);
        this.ViewFrame.add(this.messageLabel, this.constr);
        this.constr.gridy = 1;
        this.ViewFrame.add(this.okButton, this.constr);
    }


    /**
     * Initializes all the components in the GameOverView
     */
    @Override
    public void initializeComponents() 
    {
        this.ViewFrame = new JFrame("GAME OVER");
        this.ViewFrame.setLayout(new GridBagLayout());
        this.okButton = new JButton("OK");
        this.messageLabel = new JLabel();
        this.messageLabel.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        this.constr = new GridBagConstraints();
    }


    /**
     * Deploy the GameOverView to the user
     */
    @Override
    public void launchFrame()
    {
        this.ViewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.ViewFrame.pack();
        this.ViewFrame.setLocationRelativeTo(null);
        this.ViewFrame.setVisible(true);
    }
}
