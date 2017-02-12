package window;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InputPanel extends JPanel
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final int arcsSize = 50;
    private final Color backgroundColor = Color.white;
    private final Color borderColor = Color.red;
    private final float bgAlpha = 0.75f;

    private final String[] levels = new String[] { "", "Beginner / Débutant", "Intermediate / Intermédiaire",
            "Advanced / Avancée", "Native / Langue maternelle" };

    private JTextField nameTextField;
    private JTextField emailTextField;
    private JComboBox<String> levelComboBox;
    private JScrollPane commentsArea;
    private JTextArea textArea;
    private JButton additionalButtonForSubmission;

    private int textSize = 16;
    private JButton submitButton;

    private boolean editable;

    InputPanel()
    {
        editable = true;
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel panelName = namePanel();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(20, 50, 10, 50);
        add(panelName, c);
        c.gridy = 1;
        c.insets = new Insets(10, 50, 10, 50);
        add(emailPanel(), c);
        c.gridy = 2;
        add(levelPanel(), c);
        c.gridy = 3;
        add(commentPanel(), c);
        c.gridy = 4;
        c.weighty = 0.5;
        add(submissionPanel(), c);

        nameTextField.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                emailTextField.requestFocusInWindow();
            }
        });

        emailTextField.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                levelComboBox.requestFocusInWindow();
            }
        });

        levelComboBox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                textArea.requestFocusInWindow();
            }
        });

    }

    private JPanel namePanel()
    {

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        namePanel.setOpaque(false);

        JLabel nameLabel = new JLabel("Name / Nom *");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, textSize));

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        namePanel.add(nameLabel, c);

        nameTextField = new JTextField(20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        namePanel.add(nameTextField, c);

        return namePanel;
    }

    private JPanel emailPanel()
    {
        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        emailPanel.setOpaque(false);

        JLabel emailLabel = new JLabel("E-mail *");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, textSize));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        emailPanel.add(emailLabel, c);

        emailTextField = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        emailPanel.add(emailTextField, c);

        return emailPanel;
    }

    private JPanel levelPanel()
    {
        JPanel levelPanel = new JPanel();
        levelPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        levelPanel.setOpaque(false);

        JLabel levelLabel = new JLabel("Current Japanese Level / Niveau de Japonais *");
        levelLabel.setFont(new Font("Arial", Font.PLAIN, textSize));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        levelPanel.add(levelLabel, c);

        levelComboBox = new JComboBox<String>(levels);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        levelPanel.add(levelComboBox, c);

        return levelPanel;
    }

    private JPanel commentPanel()
    {
        JPanel commentPanel = new JPanel();
        commentPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        commentPanel.setOpaque(false);

        JLabel commentLabel = new JLabel("Comments / Commentaires");
        commentLabel.setFont(new Font("Arial", Font.PLAIN, textSize));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        commentPanel.add(commentLabel, c);

        textArea = new JTextArea(5, 30);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        commentsArea = new JScrollPane(textArea);
        commentPanel.add(commentsArea, c);

        return commentPanel;
    }

    private JPanel submissionPanel()
    {
        JPanel submissionPanel = new JPanel();
        submissionPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        submissionPanel.setOpaque(false);

        submitButton = new JButton("Verify");
        submitButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {

                changeEditableState();

            }

        });

        additionalButtonForSubmission = new JButton("Submit");
        additionalButtonForSubmission.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    writeToFile();
                    resetAplication();
                } catch (IOException e1)
                {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        submissionPanel.add(submitButton, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0;
        submissionPanel.add(additionalButtonForSubmission, c);
        additionalButtonForSubmission.setVisible(false);

        return submissionPanel;

    }

    protected void setApplicationEditable(boolean visible)
    {
        editable = visible;
        nameTextField.setEnabled(visible);
        emailTextField.setEnabled(visible);
        levelComboBox.setEnabled(visible);
        textArea.setEnabled(visible);

        additionalButtonForSubmission.setVisible(!visible);
        if (visible)
        {
            submitButton.setText("Verify");
        } else
        {
            submitButton.setText("Back");
        }

    }

    private void changeEditableState()
    {
        if (!editable)
        {
            setApplicationEditable(true);
            return;
        }

        String nameInfo = nameTextField.getText();
        String emailInfo = emailTextField.getText();
        String commentInfo = textArea.getText();

        boolean nameIsEmpty = nameInfo.trim().equals("");
        boolean emailIsEmpty = emailInfo.trim().equals("");
        boolean levelIsEmpty = levelComboBox.getSelectedIndex() == 0;

        boolean containsIllegalChar = nameInfo.contains("#") || emailInfo.contains("#") || commentInfo.contains("#");

        if (nameIsEmpty || emailIsEmpty || levelIsEmpty)
        {
            String errorText = "Please Enter:\n";
            if (nameIsEmpty)
                errorText += "Name / Nom\n";
            if (emailIsEmpty)
                errorText += "E-mail\n";
            if (levelIsEmpty)
                errorText += "Current Japanese Level / Niveau de Japonais\n";
            JOptionPane.showMessageDialog(this, errorText, null, JOptionPane.ERROR_MESSAGE);
        } else if (containsIllegalChar)
        {
            JOptionPane.showMessageDialog(this, "Illegal character: \" # \"", null, JOptionPane.ERROR_MESSAGE);
        } else
        {
            setApplicationEditable(false);
        }

    }

    private void resetAplication()
    {
        setApplicationEditable(true);
        nameTextField.setText("");
        emailTextField.setText("");
        levelComboBox.setSelectedIndex(0);
        textArea.setText("");
        JOptionPane.showMessageDialog(this, "Thank you for registering :)", null, JOptionPane.PLAIN_MESSAGE);
    }

    private void writeToFile() throws IOException
    {

        String nameInfo = nameTextField.getText();
        String emailInfo = emailTextField.getText();
        String levelInfo = (String) levelComboBox.getSelectedItem();
        String commentsInfo = textArea.getText();

        File f = new File(System.getProperty("java.class.path"));
        File dir = f.getAbsoluteFile().getParentFile();
        String projectPath = dir.toString();

        File file = new File(projectPath + "/file/output.txt");
        File fileBackup = new File(projectPath + "/file/backup.txt");

        // if file doesnt exists, then create it
        if (!file.exists())
        {
            file.createNewFile();
            System.out.println("file created at " + projectPath + "/file/output.txt");
        }
        
        if (!fileBackup.exists())
        {
            fileBackup.createNewFile();
            System.out.println("file created at " + projectPath + "/file/backup.txt");
        }

        Scanner fileScanner = new Scanner(file);

        ArrayList<String> lines = new ArrayList<>();

        while (fileScanner.hasNextLine())
        {
            lines.add(fileScanner.nextLine());
        }

        fileScanner.close();

        int numberOfPeople = Integer.parseInt(lines.get(0));
        numberOfPeople++;
        lines.set(0, numberOfPeople + "");

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        for (int i = 0; i < lines.size(); i++)
        {
            bw.write(lines.get(i) + "\n");
        }

        bw.write(nameInfo + "\n");
        bw.write(emailInfo + "\n");
        bw.write(levelInfo + "\n");
        bw.write(commentsInfo.replace("\n", " ") + "\n");
        bw.close();
        
        FileWriter fwBackup = new FileWriter(fileBackup.getAbsoluteFile(), true);
        BufferedWriter bwBackup = new BufferedWriter(fwBackup);

        bwBackup.write(nameInfo + "\n");
        bwBackup.write(emailInfo + "\n");
        bwBackup.write(levelInfo + "\n");
        bwBackup.write(commentsInfo.replace("\n", " ") + "\n");
        bwBackup.close();
        
        
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Dimension arcs = new Dimension(arcsSize, arcsSize);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draws the rounded panel with borders.
        graphics.setColor(backgroundColor);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, bgAlpha));
        graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);// paint

        float thickness = 3;
        Stroke oldStroke = graphics.getStroke();
        graphics.setStroke(new BasicStroke(thickness));

        graphics.setColor(borderColor);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);

        graphics.setStroke(oldStroke);
    }
}
