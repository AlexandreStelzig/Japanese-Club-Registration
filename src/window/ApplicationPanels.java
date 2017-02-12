package window;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ApplicationPanels extends JPanel
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final int GAPX = 100;
    private final int GAPY = 20;
    private Image bg;

    private final String TITLE1 = "Japanese Enthusiasts Language Learning Organisation /";
    private final String TITLE2 = "Association des enthousiastes de la langue japonaise";

    private CardLayout cardsLayout;
    private JPanel cardsPanel;
    private DisplayPanel dp;

    ApplicationPanels()
    {
        loadBg();
        setLayout(new BorderLayout(GAPX, GAPY));
        initEmptyPanels();

        cardsPanel = new JPanel();
        cardsPanel.setLayout(new CardLayout());
        cardsPanel.setOpaque(false);

        JPanel inputPanel = new InputPanel();
        inputPanel.setOpaque(false);

        
        dp = new DisplayPanel();
        JPanel displayPanel = (DisplayPanel) dp;
        displayPanel.setOpaque(false);

        cardsPanel.add(inputPanel, "input");
        cardsPanel.add(displayPanel, "display");

        add(cardsPanel, BorderLayout.CENTER);

        cardsLayout = (CardLayout) cardsPanel.getLayout();

    }

    private void loadBg()
    {
        bg = new ImageIcon(getClass().getClassLoader().getResource("background/Enregistrement.jpg")).getImage();

    }

    // create an empty and invisible space on all sides
    private void initEmptyPanels()
    {
        JPanel northPanel = northPanel();
        JPanel southPanel = new JPanel();
        JPanel eastPanel = new JPanel();
        JPanel westPanel = new JPanel();

        northPanel.setOpaque(false);
        southPanel.setOpaque(false);
        eastPanel.setOpaque(false);
        westPanel.setOpaque(false);

        add(northPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);
        add(eastPanel, BorderLayout.EAST);
        add(westPanel, BorderLayout.WEST);
    }

    // north panel with tile information
    private JPanel northPanel()
    {
        JLabel label = new JLabel(TITLE1);
        label.setForeground(Color.white);
        label.setFont(new Font("Arial", Font.PLAIN, 25));

        JLabel label2 = new JLabel(TITLE2);
        label2.setForeground(Color.white);
        label2.setFont(new Font("Arial", Font.PLAIN, 25));

        label.setHorizontalAlignment(JLabel.CENTER);
        label2.setHorizontalAlignment(JLabel.CENTER);

        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(label);
        panel.add(label2);
        return panel;
    }

    // paint background
    protected void paintComponent(Graphics g)
    {

        super.paintComponent(g);
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
    }

    // change between inscription and information panels
    public void changeCards()
    {
        cardsLayout.next(cardsPanel);
        dp.loadData();
    }

}
