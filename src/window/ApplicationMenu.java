package window;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

public class ApplicationMenu extends JMenuBar
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static final int ITEM_VIEW = 0;

    private static final String VIEW = "View";
    private static final String INFO = "Export";
    private static final String ABOUT = "About";

    ApplicationMenu(ApplicationFrame applicationFrame)
    {

        // init menu items
        JMenu fileMenu = new JMenu("File");
        this.add(fileMenu);

        JMenuItem viewMenuItem = new JMenuItem(VIEW);
        JMenuItem exportMenuItem = new JMenuItem(INFO);
        JMenuItem aboutMenuItem = new JMenuItem(ABOUT);
        fileMenu.add(viewMenuItem);
        fileMenu.add(exportMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(aboutMenuItem);

        // view menu item action
        viewMenuItem.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                applicationFrame.menuItemSelected(ITEM_VIEW);

            }
        });

        // about menu item action
        aboutMenuItem.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                aboutClicked();

            }
        });

        // export menu item action
        exportMenuItem.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                exportClicked();

            }
        });

    }

    // export menu item action
    private void exportClicked()
    {
        // open JOptionPane for what to export
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        UIManager.put("OptionPane.background", new ColorUIResource(255, 255, 255));
        UIManager.put("Panel.background", new ColorUIResource(255, 255, 255));

        panel.setLayout(new GridLayout(6, 1));

        // what to export choices
        JLabel prompt = new JLabel("Select data to open:");
        panel.add(prompt);
        JRadioButton rbName = new JRadioButton("Name");
        JRadioButton rbEmail = new JRadioButton("E-mail");
        JRadioButton rbLevel = new JRadioButton("Level");
        JRadioButton rbComments = new JRadioButton("Comments");
        panel.add(rbName);
        panel.add(rbEmail);
        panel.add(rbLevel);
        panel.add(rbComments);

        // export button
        JButton okButton = new JButton("Open");
        okButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (rbName.isSelected() || rbEmail.isSelected() || rbLevel.isSelected() || rbComments.isSelected())
                {

                    try
                    {
                        // write and open selected choices
                        openData(rbName.isSelected(), rbEmail.isSelected(), rbLevel.isSelected(),
                                rbComments.isSelected());
                    } catch (FileNotFoundException e1)
                    {

                        e1.printStackTrace();
                    } catch (IOException e1)
                    {

                        e1.printStackTrace();
                    }
                    // force close of JOptionPane after choices
                    JOptionPane.getRootFrame().dispose();
                }
            }
        });

        panel.add(okButton);
        // show options to export
        JOptionPane.showOptionDialog(null, panel, "Get Information", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, new Object[] {}, null);
    }

    // write and open information
    private void openData(boolean openName, boolean openEmail, boolean openLevel, boolean openComments)
            throws IOException
    {
        // get information form data file
        File f = new File(System.getProperty("java.class.path"));
        File dir = f.getAbsoluteFile().getParentFile();
        String projectPath = dir.toString();
        File file = new File(projectPath + "/file/output.txt");

        // read and get all info
        Scanner fileScanner = new Scanner(file);
        ArrayList<String> lines = new ArrayList<>();

        while (fileScanner.hasNextLine())
        {
            lines.add(fileScanner.nextLine());
        }

        fileScanner.close();

        // create view file
        File viewFile = new File(projectPath + "/file/view.txt");
        if (!viewFile.exists())
        {
            viewFile.createNewFile();
            System.out.println("file created at " + projectPath + "/file/view.txt");
        }

        FileWriter fw = new FileWriter(viewFile.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        int numberOfPeople = Integer.parseInt(lines.get(0));

        // write to view file depending of user choice
        for (int i = 0; i < numberOfPeople; i++)
        {
            if (openName)
            {
                bw.write(lines.get(1 + i * 4) + "\t");
            }
            if (openEmail)
            {
                bw.write(lines.get(2 + i * 4) + "\t");
            }
            if (openLevel)
            {
                bw.write(lines.get(3 + i * 4) + "\t");
            }
            if (openComments)
            {
                bw.write(lines.get(4 + i * 4) + "\t");
            }
            bw.write("\n");
        }

        // write message if no information
        if (lines.size() < 2)
        {
            bw.write("No information collected");
        }

        bw.close();

        // open file with default user file
        Desktop.getDesktop().open(viewFile);

    }

    // about menu item clicked
    private void aboutClicked()
    {
        // create JOptionPane look
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        UIManager.put("OptionPane.background", new ColorUIResource(255, 255, 255));
        UIManager.put("Panel.background", new ColorUIResource(255, 255, 255));

        panel.setLayout(new GridLayout(2, 1));

        // information and contact
        JLabel about = new JLabel("Created by Alexandre Stelzig");
        panel.add(about);
        JLabel contact = new JLabel("Contact: 819-208-3662 or astel021@uottawa.ca");
        panel.add(contact);

        InputStream stream = getClass().getResourceAsStream("/icon/Jello.png");
        ImageIcon icon = null;
        try
        {
            icon = new ImageIcon(ImageIO.read(stream));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, panel, "Information", JOptionPane.INFORMATION_MESSAGE, icon);
    }

}
