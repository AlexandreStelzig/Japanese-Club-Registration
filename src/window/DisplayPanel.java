package window;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class DisplayPanel extends JPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // background
    private final int arcsSize = 50;
    private final Color backgroundColor = Color.white;
    private final Color borderColor = Color.red;
    private final float bgAlpha = 0.75f;

    // table
    private JTable table;

    DisplayPanel()
    {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        DefaultTableModel model = new DefaultTableModel()
        {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column)
            {
                // all cells false
                return false;
            }
        };
        table = new JTable(model);
        table.setOpaque(false);

        ((DefaultTableCellRenderer) table.getDefaultRenderer(Object.class)).setOpaque(false);
        table.setSelectionForeground(Color.BLACK);

        RowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);

        table.setRowSorter(sorter);
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);

        model.addColumn("#");
        model.addColumn("Name");
        model.addColumn("E-mail");
        model.addColumn("Japanese Level");
        model.addColumn("Comments");

        
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(0).setMinWidth(20);
        
        table.getColumnModel().getColumn(3).setPreferredWidth(125);
        
        loadData();

        setLayout(new BorderLayout());
        JScrollPane jsp = new JScrollPane(table);
        jsp.setOpaque(false);
        jsp.setBorder(BorderFactory.createEmptyBorder());
        jsp.getViewport().setOpaque(false);

        add(jsp, BorderLayout.CENTER);

    }

    // panel design
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

    public void loadData()
    {
        // delete all current rows
        DefaultTableModel dm = (DefaultTableModel) table.getModel();
        int rowCount = dm.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--)
        {
            dm.removeRow(i);
        }

        // add all rows from the folder
        File f = new File(System.getProperty("java.class.path"));
        File dir = f.getAbsoluteFile().getParentFile();
        String projectPath = dir.toString();

        File file = new File(projectPath + "/file/output.txt");

        // if file doesnt exists, then create it
        if (!file.exists())
        {
            try
            {
                file.createNewFile();

                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write("0\n");
                bw.close();
                
            } catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("file created at " + projectPath + "/file/output.txt");
        }
        
        
        Scanner fileScanner = null;
        try
        {
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ArrayList<String> lines = new ArrayList<>();
        
        while (fileScanner.hasNextLine())
        {
            lines.add(fileScanner.nextLine());
        }

        fileScanner.close();
        
        int numberOfPeople = Integer.parseInt(lines.get(0));

      
        
        for (int i = 0; i < numberOfPeople; i++)
        {
            int number = i + 1;
            String nameInfo = lines.get(1 + i * 4);
            String emailInfo = lines.get(2 + i * 4);
            String levelInfo = lines.get(3 + i * 4);
            String commentsInfo = lines.get(4 + i * 4);

            dm.addRow(new Object[] { number, nameInfo, emailInfo, levelInfo, commentsInfo });

        }

    }
}
