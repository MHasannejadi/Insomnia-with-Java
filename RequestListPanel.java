
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * left panel of app with with 3 main parts
 */

public class RequestListPanel extends JPanel {

    private static JPanel mainPanel;
    private static ArrayList<JButton> requestsButtons;
    private static ArrayList<HTTPRequest> requestsList;
    private static ButtonHandler handler;
    private static MenuBar bar;
    private static JTextField insomnia;
    static int c;

    /**
     * constructor to first input managing
     */
    public RequestListPanel(ArrayList<HTTPRequest> requestsList) {
        super(new BorderLayout());
        
        mainPanel = new JPanel(new GridLayout(35, 1));
        c = 1;
        handler = new ButtonHandler();
        requestsButtons = new ArrayList<>();
        bar = new MenuBar();
        mainPanel.add(bar);
        insomnia = new JTextField("        Insomnia");
        insomnia.setEditable(false);
        Font myFont = new Font("Serif", Font.BOLD, 8);
        Font newFont = myFont.deriveFont(20F);
        insomnia.setBackground(new Color(102, 0, 153));
        insomnia.setForeground(new Color(255, 255, 255));
        insomnia.setFont(newFont);
        insomnia.setPreferredSize(new Dimension(200, 100));
        mainPanel.add(insomnia);
        mainPanel.add(new JLabel());
        for (int i = 0; i < requestsList.size(); i++) {
            JButton button = new JButton();
            button.setBackground(new Color(50, 50, 50));
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(Color.BLUE);
                }
            
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(50, 50, 50));
                }
            });
            //button.setBackground(new Color(50, 50, 50));
            int j = i + 1;
            button.setText(requestsList.get(i).getMethod() + "       request " + j);
            button.setForeground(new Color(255, 255, 255));
            button.addActionListener(handler);
            requestsButtons.add(button);
            mainPanel.add(button);
        }
        this.add(mainPanel);
        mainPanel.setBackground(new Color(50, 50, 50));

    }

    /**
     * an inner class to handling evevts of every button
     */
    private class ButtonHandler implements ActionListener, FocusListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            requestsList = new ArrayList<>();
            try {
                File requestsFile = new File("requestsFile.txt");
                FileInputStream fileIn = new FileInputStream(requestsFile);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                Object obj = objectIn.readObject();
                requestsList = (ArrayList<HTTPRequest>) obj;
                
            } catch (EOFException ex) {
                System.out.println("End of file reached");
            } catch (ClassNotFoundException ex) {
                System.out.println("Class not found");
            } catch (FileNotFoundException ex){
                System.out.println("File not found");
            } catch (IOException ex){
                System.out.println("Error in transporting data");
            }
            
            for (int i = 0; i < requestsButtons.size(); i++) {
                if (e.getSource().equals(requestsButtons.get(i))) {
                    try {
                        RequestPanel.loadRequest(requestsList.get(i));
                    } catch (IOException e1) {
        
                    }
                    revalidate();
                    repaint();
                }
            }

        }

        @Override
        public void focusGained(FocusEvent e) {

        }

        @Override
        public void focusLost(FocusEvent e) {

        }

        void displayMessage(String prefix, FocusEvent e) {

        }

    }

    /**
     * mehtod that update requests list when a new requests is saved
     * @param requlist list of requests that includes new request
     */
    public static void update(ArrayList<HTTPRequest> requlist) {
        
        mainPanel.removeAll();
        requestsButtons = new ArrayList<>();
        mainPanel.add(bar);
        JTextField insomnia = new JTextField("        Insomnia");
        insomnia.setEditable(false);
        Font myFont = new Font("Serif", Font.BOLD, 8);
        Font newFont = myFont.deriveFont(20F);
        insomnia.setBackground(new Color(102, 0, 153));
        insomnia.setForeground(new Color(255, 255, 255));
        insomnia.setFont(newFont);
        insomnia.setPreferredSize(new Dimension(200, 100));
        mainPanel.add(insomnia);
        mainPanel.add(new JLabel());
        for (int i = 0; i < requlist.size(); i++) {
            JButton button = new JButton();
            button.setBackground(new Color(50, 50, 50));
            int j = i + 1;
            button.setText(requlist.get(i).getMethod() + "       request " + j);
            button.setForeground(new Color(255, 255, 255));
            button.addActionListener(handler);
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(Color.BLUE);
                }
            
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(50, 50, 50));
                }
            });
            button.setForeground(new Color(255, 255, 255));
            requestsButtons.add(button);
            mainPanel.add(button);
        }
    }
}