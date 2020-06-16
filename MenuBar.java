
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * menubar of app
 */

public class MenuBar extends JMenuBar {

    private JMenu app, view, help;
    private JMenuItem exit, options, togfulscr, togsidebar, helpItem, about;
    private JCheckBox ct;
    private JCheckBox cf;
    private File file;

    public MenuBar() {
        super();
        ButtonHandler listener = new ButtonHandler();

        ct = new JCheckBox("System tray");
        ct.addActionListener(listener);
        cf = new JCheckBox("Follow Redirect");
        cf.addActionListener(listener);

        app = new JMenu("Application");
        app.setMnemonic('A');
        view = new JMenu("View");
        view.setMnemonic('V');
        help = new JMenu("Help");
        help.setMnemonic('H');

        exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.SHIFT_MASK));
        exit.addActionListener(listener);
        options = new JMenuItem("Options");
        options.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.SHIFT_MASK));
        options.addActionListener(listener);
        togfulscr = new JMenuItem("Toggle Full Screen");
        togfulscr.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.SHIFT_MASK));
        togfulscr.addActionListener(listener);
        helpItem = new JMenuItem("Help");
        helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.SHIFT_MASK));
        helpItem.addActionListener(listener);
        about = new JMenuItem("About");
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.SHIFT_MASK));
        about.addActionListener(listener);

        app.add(exit);
        app.add(options);
        view.add(togfulscr);
        //view.add(togsidebar);
        help.add(helpItem);
        help.add(about);

        this.setBackground(new Color(50, 50, 50));

        this.add(app);
        this.add(view);
        this.add(help);

    }
    /**
     * handler of menu items
     */
    private class ButtonHandler implements ActionListener, FocusListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource().equals(about)) {
                JFrame newFrame = new JFrame("About");
                newFrame.setLocationRelativeTo(about);
                newFrame.setSize(500, 400);
                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JTextArea text = new JTextArea(
                        "Author : Mohammad Hassannejadi \nID: 9831020\nEmail: hasannejadi80@gmail.com");
                text.setEditable(false);
                newFrame.add(text);
                newFrame.setVisible(true);
                // revalidate();

            } else if (e.getSource().equals(helpItem)) {

                JFrame newFrame = new JFrame("Help");
                newFrame.setLocationRelativeTo(helpItem);
                JTextArea text = new JTextArea("more information in https://support.insomnia.rest/");
                newFrame.add(text);
                newFrame.setSize(500, 400);
                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                newFrame.setVisible(true);

            } else if (e.getSource().equals(togfulscr)) {
                Insomnia.maximize();
            }

            else if (e.getSource().equals(options)) {
                JFrame newFrame = new JFrame("Options");
                newFrame.setLocationRelativeTo(options);
                newFrame.setSize(500, 80);
                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JPanel newPanel = new JPanel(new FlowLayout(5, 10, 10));

                file = new File("options.txt");
                try {
                    if (file.createNewFile()) {
                        System.out.println("File created: " + file.getName());
                    } else if(file.length() != 0){
                        System.out.println("File already exists.");
                        Scanner myReader = new Scanner(file);
                        int c1 = myReader.nextInt();
                        int c2 = myReader.nextInt();
                        if (c1 == 0) {
                            ct.setSelected(false);
                        } else {
                            ct.setSelected(true);
                        }
                        if (c2 == 0) {
                            cf.setSelected(false);
                        } else {
                            cf.setSelected(true);
                        }
                    }
                } catch (IOException e1) {
                    System.out.println("Something went wrong!");
                    e1.printStackTrace();
                }

                newPanel.add(ct);
                newPanel.add(cf);

                newFrame.add(newPanel);
                newFrame.setVisible(true);

            }

            else if (e.getSource().equals(exit)) {
                if (ct.isSelected()) {
                    try {
                        FileWriter myWriter = new FileWriter("options.txt");
                        if(ct.isSelected()){
                            myWriter.write("1 ");
                        }
                        else{
                            myWriter.write("0 ");
                        }
                        if(cf.isSelected()){
                            myWriter.write("1");
                        }
                        else{
                            myWriter.write("0");
                        }
                        myWriter.close();    
                       
                    } catch (IOException e1) {
                        System.out.println("An error occurred.");
                        e1.printStackTrace();
                    }
                    Insomnia.trayFrame();
                    revalidate();

                } else {
                    
                    try {
                        FileWriter myWriter = new FileWriter("options.txt");
                        if(ct.isSelected()){
                            myWriter.write("1 ");
                        }
                        else{
                            myWriter.write("0 ");
                        }
                        if(cf.isSelected()){
                            myWriter.write("1");
                        }
                        else{
                            myWriter.write("0");
                        }
                        myWriter.close();    
                       
                    } catch (IOException e1) {
                        System.out.println("An error occurred.");
                        e1.printStackTrace();
                    }
                    System.exit(0);
                }
            }

            else if(e.getSource().equals(ct)){
                try {
                    FileWriter myWriter = new FileWriter("options.txt");
                    if(ct.isSelected()){
                        myWriter.write("1 ");
                    }
                    else{
                        myWriter.write("0 ");
                    }
                    if(cf.isSelected()){
                        myWriter.write("1");
                    }
                    else{
                        myWriter.write("0");
                    }
                    myWriter.close();    
                   
                } catch (IOException e1) {
                    System.out.println("An error occurred.");
                    e1.printStackTrace();
                }
            }
            else if(e.getSource().equals(cf)){
                try {
                    FileWriter myWriter = new FileWriter("options.txt");
                    if(ct.isSelected()){
                        myWriter.write("1 ");
                    }
                    else{
                        myWriter.write("0 ");
                    }
                    if(cf.isSelected()){
                        myWriter.write("1");
                    }
                    else{
                        myWriter.write("0");
                    }
                    myWriter.close();    
                   
                } catch (IOException e1) {
                    System.out.println("An error occurred.");
                    e1.printStackTrace();
                }
            }



        }

		@Override
		public void focusGained(FocusEvent e) {
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			displayMessage("Focus lost", e);
		}

		void displayMessage(String prefix, FocusEvent e) {
	        System.out.println(prefix
	            + (e.isTemporary() ? " (temporary):" : ":")
	            + e.getComponent().getClass().getName()
	            + "; Opposite component: "
	            + (e.getOppositeComponent() != null ? e.getOppositeComponent().getClass().getName()
	                : "null"));
	      }
    }

}