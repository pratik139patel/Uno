package src.View;

/**
 * View class serves as an abstract class to all the views
 * during the gameplay
 */

import javax.swing.JFrame;

public abstract class View 
{
    /**Main frame of the view */
    public JFrame ViewFrame;


    /**
     * Default constructor of the view class to initialize
     * the components of the frame
     */
    public View()
    {
        this.initializeComponents();
    }


    /**
     * Destory the frame
     */
    public void destroyFrame()
    {
        this.ViewFrame.dispose();
    }


    /**Initialize all the components to be put on the frame */
    public abstract void initializeComponents();


    /**Desploy the frame to be viewed by the players */
    public abstract void launchFrame();

    
    /**Insert components into the frame */
    public abstract void insertComponentsIntoFrame();
}
