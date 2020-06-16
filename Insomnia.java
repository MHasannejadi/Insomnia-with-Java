
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.color.*;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * manage the most important activities of this request manager application
 * 
 * @author Mohammad Hassannejadi , email: hasannejadiam@gmail.com
 * @version 1.0
 * 
 */

public class Insomnia{

    private ArrayList<HTTPRequest> requestsList;
    private static JFrame mainFrame;
    private JPanel mainPanel;
    private HTTPRequest stateReauest;
    int showResponse;

    public Insomnia() {
        showResponse = 0;

        requestsList = new ArrayList<>();
        try {
            File requestsFile = new File("requestsFile.txt");
            FileInputStream fileIn = new FileInputStream(requestsFile);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object obj = objectIn.readObject();
            requestsList = (ArrayList<HTTPRequest>) obj;
            objectIn.close();
            
        } catch (EOFException e) {
            System.out.println("End of file reached");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        } catch (IOException e){
            System.out.println("Error in transporting data");
        }
        
        mainFrame = new JFrame("Insomnia");
        ImageIcon img = new ImageIcon(getClass().getResource("img.png"));
        mainFrame.setIconImage(img.getImage());
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        mainFrame.setBackground(new Color(50, 50, 50));

        mainPanel = new JPanel(new BorderLayout(1, 1));
        mainPanel.setBackground(new Color(0,200,200));

        RequestListPanel requlistPanel = new RequestListPanel(requestsList);

        RequestPanel requPanel = new RequestPanel(requestsList);
        stateReauest = new HTTPRequest();
        try {

            File state = new File("responseState.txt");
            FileInputStream fileIn = new FileInputStream(state);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object obj = objectIn.readObject();
            showResponse = (int) obj;
            if(showResponse == 1){
                requPanel.sendThread();
            }
            objectIn.close();

        } catch (EOFException e) {
            
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        } catch (IOException e){
            System.out.println("Error in transporting data");
        }

        try {

            File state = new File("state.txt");
            FileInputStream fileIn = new FileInputStream(state);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object obj = objectIn.readObject();
            stateReauest = (HTTPRequest) obj;
            RequestPanel.loadRequest(stateReauest);
            objectIn.close();

        } catch (EOFException e) {
            
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        } catch (IOException e){
            System.out.println("Error in transporting data");
        }
        
        InfoPanel infoPanel = new InfoPanel();
        

        mainPanel.add(requlistPanel, BorderLayout.WEST);
        mainPanel.add(requPanel, BorderLayout.CENTER);
        mainPanel.add(infoPanel, BorderLayout.EAST);
        mainFrame.add(mainPanel);
        mainFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                requPanel.saveState();
                
                e.getWindow().dispose();
            }
        });

        mainFrame.setSize(800, 600);
        mainFrame.getContentPane().setBackground(Color.DARK_GRAY);
        maximize();
        mainFrame.setVisible(true);

    }

    /**
     * maximize main frame of app
     */
    public static void maximize() {
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setVisible(true);
    }

    /**
     * activate tray frame
     */
    public static void trayFrame(){
        mainFrame.setExtendedState(JFrame.ICONIFIED);
    }
}