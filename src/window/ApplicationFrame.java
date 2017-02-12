package window;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ApplicationFrame extends JFrame
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ApplicationPanels applicationPanels;

    ApplicationFrame(String title, int dimX, int dimY)
    {

        setTitle(title);
        setSize(dimX, dimY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loadComponents();

        setJMenuBar(new ApplicationMenu(this));
        setLocationRelativeTo(null);
        setVisible(true);

    }

    // init components of the Frame
    private void loadComponents()
    {
        applicationPanels = new ApplicationPanels();
        JPanel mainPanel = (JPanel) applicationPanels;
        getContentPane().add(mainPanel);

    }

    public void menuItemSelected(int item)
    {
        switch (item)
        {
        case ApplicationMenu.ITEM_VIEW:
            applicationPanels.changeCards();
            break;

        default:
            break;
        }
    }

}
