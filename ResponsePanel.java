import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.awt.event.*;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

/**
 * right tabs of application for responses
 */

public class ResponsePanel extends JTabbedPane {

    static String textMesg;
    static JPanel headerTab;
    static JPanel bodyTab;
    static JScrollPane headerScroll;
    static JComboBox<String> combo;
    static boolean showPreview;
    static JButton copyRaw;
    static JButton copyPreview;
    static JButton copyHeader;
    static String headerString;

    ButtonHandler handler;
    copyHandler cHandler;

    public ResponsePanel() {
        super();
        this.setBounds(1, 1, 600, 900);

        showPreview = false;

        headerTab = new JPanel(new FlowLayout(20, 10, 5));
        headerTab.setPreferredSize(new Dimension(350, 100));
        headerTab.setBackground(new Color(50, 50, 50));
        headerScroll = new JScrollPane(headerTab);
        headerScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        bodyTab = new JPanel(new FlowLayout(20, 10, 5));
        bodyTab.setPreferredSize(new Dimension(350, 700));
        bodyTab.setBackground(new Color(50, 50, 50));

        String[] types = new String[] { "Raw", "Preview" };

        handler = new ButtonHandler();
        combo = new JComboBox<String>(types);
        combo.addActionListener(handler);
        bodyTab.add(combo);

        setBackground(new Color(70, 70, 70));
        this.add("Header", headerScroll);
        this.add("Body", bodyTab);

        cHandler = new copyHandler();
        copyRaw = new JButton("Copy To Clipboard");
        copyRaw.setBackground(new Color(50, 50, 50));
        copyRaw.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                copyRaw.setBackground(Color.BLUE);
            }
        
            public void mouseExited(java.awt.event.MouseEvent evt) {
                copyRaw.setBackground(new Color(50, 50, 50));
            }
        });
        copyRaw.setForeground(new Color(255, 255, 255));
        
        copyRaw.setPreferredSize(new Dimension(200, 40));
        copyRaw.addActionListener(cHandler);

        copyHeader = new JButton("Copy To Clipboard");
        copyHeader.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                copyHeader.setBackground(Color.BLUE);
            }
        
            public void mouseExited(java.awt.event.MouseEvent evt) {
                copyHeader.setBackground(new Color(50, 50, 50));
            }
        });
        copyHeader.setForeground(new Color(255, 255, 255));
        copyHeader.setBackground(new Color(50, 50, 50));
        copyHeader.setPreferredSize(new Dimension(200, 40));
        copyHeader.addActionListener(cHandler);

        copyPreview = new JButton("Copy To Clipboard");
        
        copyPreview.setForeground(new Color(255, 255, 255));
        copyPreview.setBackground(new Color(50, 50, 50));
        copyPreview.setPreferredSize(new Dimension(200, 40));
        copyPreview.addActionListener(cHandler);
        copyPreview.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                copyPreview.setBackground(Color.BLUE);
            }
        
            public void mouseExited(java.awt.event.MouseEvent evt) {
                copyPreview.setBackground(new Color(50, 50, 50));
            }
        });
        copyPreview.setForeground(new Color(255, 255, 255));
        copyPreview.setBackground(new Color(50, 50, 50));

    }

    /**
     * an static method to add components of this panel when send button is pressed
     * @param connection to show its information
     * @throws IOException 
     */
    public static void addElements(HttpURLConnection con) throws IOException {

        headerString = ""+con.getHeaderFields();

        headerTab.removeAll();
        bodyTab.removeAll();
        if (con.getContentType().contains("png") || con.getContentType().contains("jpeg")) {
            showPreview = true;
        }
        else{
            showPreview = false;
        }

        for (String key : con.getHeaderFields().keySet()) {
            JTextField name = new JTextField(key);
            name.setPreferredSize(new Dimension(320, 40));
            name.setAutoscrolls(true);
            name.setEditable(false);
            name.setForeground(new Color(255, 255, 255));
            name.setBackground(new Color(50, 50, 50));
            JTextField value = new JTextField((con.getHeaderFields()).get(key).toString());
            value.setPreferredSize(new Dimension(320, 40));
            value.setAutoscrolls(true);
            value.setEditable(false);
            value.setForeground(new Color(255, 255, 255));
            value.setBackground(new Color(50, 50, 50));
            headerTab.add(name);
            headerTab.add(value);
        }
        headerTab.add(new JLabel());
        
        headerTab.add(copyHeader);
        headerTab.setBackground(new Color(70, 70, 70));

        bodyTab.add(combo);

        textMesg = null;
        InputStream ip;
        ip = con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(ip));
        StringBuilder responseBuilder = new StringBuilder();
        String responseSingle = null;
        try {
            while ((responseSingle = br.readLine()) != null) {
                responseBuilder.append(responseSingle);
            }
        } catch (IOException e1) {
           
        }
        textMesg = responseBuilder.toString();
        try {
            br.close();
        } catch (IOException e) {
           
        }

        JTextArea raw = new JTextArea(textMesg,20,50);
        raw.setLineWrap(true);
        raw.setForeground(new Color(255, 255, 255));
        raw.setBackground(new Color(50, 50, 50));
        raw.setEditable(false);
        
        bodyTab.setBackground(new Color(70, 70, 70));
        JScrollPane scrollableTextArea = new JScrollPane(raw);  
        //scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollableTextArea.setPreferredSize(new Dimension(650, 700));  
        bodyTab.add(scrollableTextArea);

        bodyTab.add(copyRaw);
        bodyTab.setBackground(new Color(70, 70, 70));
    
    }

    /**
     * a handler class to handle switch between raw or preview show
     */
    private class ButtonHandler implements ActionListener, FocusListener {

    	@Override
        public void actionPerformed(ActionEvent e) {
            if(((String)combo.getSelectedItem()).equals("Preview")){
                
                bodyTab.removeAll();
                bodyTab.add(combo);
                JLabel pic;
                if(showPreview){
                    pic = new JLabel(new ImageIcon("pic.png"));
                }
                else{
                    pic = new JLabel();
                }
                pic.setPreferredSize(new Dimension(650, 700));
                bodyTab.add(pic);
                bodyTab.add(copyRaw);
                bodyTab.setBackground(new Color(70, 70, 70));
                revalidate();
                repaint();
            }
            else{
                
                bodyTab.removeAll();
                bodyTab.add(combo);

                JTextArea raw = new JTextArea(textMesg,20,50);
                raw.setLineWrap(true);
                raw.setForeground(new Color(255, 255, 255));
                raw.setBackground(new Color(50, 50, 50));
                raw.setEditable(false);
                
                bodyTab.setBackground(new Color(70, 70, 70));
                JScrollPane scrollableTextArea = new JScrollPane(raw);
                scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrollableTextArea.setPreferredSize(new Dimension(650, 700));  
                bodyTab.add(scrollableTextArea);
        
                bodyTab.add(copyRaw);
                bodyTab.setBackground(new Color(70, 70, 70));
                revalidate();
                repaint();
            }
        }

		@Override
		public void focusGained(FocusEvent e) {
			
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			
			
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

    /**
     * handler of copy button to copy a content to clipboard
     */
    private class copyHandler implements ActionListener, FocusListener {

    	@Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource().equals(copyRaw)){
                StringSelection stringSelection = new StringSelection(textMesg);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
            else if(e.getSource().equals(copyHeader)){
                StringSelection stringSelection = new StringSelection(headerString);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        }

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			
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
