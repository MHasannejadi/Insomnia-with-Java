import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * middle part of app that recieving and sending request or save it
 */

public class RequestPanel extends JPanel {

    static ArrayList<HTTPRequest> requestsList;
    private static JPanel urlPanel;
    private static URLButtonHandler urlbuttonHandler;
    private static JButton sendButton;
    private static JButton saveButton;
    private static JTextField URLField;
    private static JComboBox<String> typeCombo;
    private static String[] types;
    private static JPanel bodyTab;
    private static HeaderButtonHandler headerhandler;
    private static HashMap<String, String> bodyMap;
    private static ArrayList<JPanel> bodyBordersList;
    private static JTextField key1;
    private static JTextField value1;
    private static JTextField key2;
    private static JTextField value2;
    private static JCheckBox checkBox;
    private static JButton delete;
    private static JPanel headerTab;
    private static BodyButtonHandler bodyhandler;
    private static ArrayList<JPanel> headerBordersList;
    private static HashMap<String, String> headersMap;
    private static JTabbedPane tp;
    private static long time1;
    private int showResponse;

    public RequestPanel(ArrayList<HTTPRequest> requestsList) {
        // manage tab panel
        super(new BorderLayout(1, 1));
        showResponse = 0;
        this.setBackground(new Color(50, 50, 50));

        this.requestsList = requestsList;

        urlbuttonHandler = new URLButtonHandler();
        bodyhandler = new BodyButtonHandler();
        headerhandler = new HeaderButtonHandler();

        bodyMap = new HashMap<>();
        headersMap = new HashMap<>();

        tp = new JTabbedPane();
        tp.setBounds(1, 1, 500, 800);

        bodyTab = new JPanel(new GridLayout(17, 4));
        bodyBordersList = new ArrayList<>();
        bodyTab.setPreferredSize(new Dimension(700, 800));
        bodyTab.setBackground(new Color(50, 50, 50));

        JPanel border = new JPanel(new FlowLayout(5, 5, 5));
        border.setPreferredSize(new Dimension(700, 50));

        key1 = new JTextField("new name ");
        key1.setPreferredSize(new Dimension(290, 40));
        key1.addActionListener(bodyhandler);
        key1.addFocusListener(bodyhandler);
        key1.setForeground(new Color(255, 255, 255));
        key1.setBackground(new Color(50, 50, 50));
        border.add(key1);

        value1 = new JTextField("new value ");
        value1.setPreferredSize(new Dimension(290, 40));
        value1.addActionListener(bodyhandler);
        value1.addFocusListener(bodyhandler);
        value1.setForeground(new Color(255, 255, 255));
        value1.setBackground(new Color(50, 50, 50));
        border.add(value1);

        checkBox = new JCheckBox();
        border.add(checkBox);

        delete = new JButton(new ImageIcon("trash.png"));
        delete.addActionListener(bodyhandler);
        
        border.add(delete);
        setBackground(new Color(50, 50, 50));
        border.setBackground(new Color(50, 50, 50));
        bodyBordersList.add(border);
        bodyTab.add(border);

        headerTab = new JPanel(new GridLayout(17, 4));
        headerTab.setBackground(new Color(50, 50, 50));
        headerBordersList = new ArrayList<>();
        headerTab.setPreferredSize(new Dimension(700, 800));
        border = new JPanel(new FlowLayout(5, 5, 5));
        border.setPreferredSize(new Dimension(700, 50));

        key2 = new JTextField("new name ");
        key2.setPreferredSize(new Dimension(290, 40));
        key2.addActionListener(headerhandler);
        key2.addFocusListener(headerhandler);
        key2.setForeground(new Color(255, 255, 255));
        key2.setBackground(new Color(50, 50, 50));
        border.add(key2);

        value2 = new JTextField("new value ");
        value2.setPreferredSize(new Dimension(290, 40));
        value2.addActionListener(headerhandler);
        value2.addFocusListener(headerhandler);
        value2.setForeground(new Color(255, 255, 255));
        value2.setBackground(new Color(50, 50, 50));
        border.add(value2);

        checkBox = new JCheckBox();
        border.add(checkBox);

        delete = new JButton(new ImageIcon("trash.png"));
        delete.addActionListener(headerhandler);
        
        border.add(delete);
        setBackground(new Color(50, 50, 50));
        border.setBackground(new Color(50, 50, 50));
        headerBordersList.add(border);
        headerTab.add(border);

        urlPanel = new JPanel(new FlowLayout(5, 20, 10));

        // manage combo box
        types = new String[] { "GET", "DELETE", "POST", "PUT" };
        typeCombo = new JComboBox<>(types);

        // manage textfield
        URLField = new JTextField("add a URL ...");
        URLField.setPreferredSize(new Dimension(400, 30));
        // URLField.addActionListener(urlbuttonHandler);
        // URLField.addFocusListener(urlbuttonHandler);

        // manage button
        sendButton = new JButton("Send");
        sendButton.addActionListener(urlbuttonHandler);
        sendButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sendButton.setBackground(Color.GREEN);
            }
        
            public void mouseExited(java.awt.event.MouseEvent evt) {
                sendButton.setBackground(UIManager.getColor("control"));
            }
        });
        saveButton = new JButton("Save");
        saveButton.addActionListener(urlbuttonHandler);
        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                saveButton.setBackground(new Color(0,200,200));
            }
        
            public void mouseExited(java.awt.event.MouseEvent evt) {
                saveButton.setBackground(UIManager.getColor("control"));
            }
        });
        // manage layout
        setBackground(new Color(255, 255, 255));
        typeCombo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                typeCombo.setBackground(new Color(255,0,150));
            }
        
            public void mouseExited(java.awt.event.MouseEvent evt) {
                typeCombo.setBackground(UIManager.getColor("control"));
            }
        });
        urlPanel.add(typeCombo);
        urlPanel.add(URLField);
        urlPanel.add(sendButton);
        urlPanel.add(saveButton);

        tp.add("Body", bodyTab);
        tp.add("Header", headerTab);
        tp.setBackground(new Color(50, 50, 50));
        setBackground(new Color(50, 50, 50));
        this.add(urlPanel, BorderLayout.NORTH);
        this.add(tp, BorderLayout.CENTER);
    }

    /**
     * add a new pair of body key and value when a pair ends
     */
    public void addBodyBorder() {
        JPanel border = new JPanel(new FlowLayout(5, 5, 5));
        key1 = new JTextField("new name ");
        key1.setPreferredSize(new Dimension(290, 40));
        key1.addActionListener(bodyhandler);
        key1.addFocusListener(bodyhandler);
        key1.setForeground(new Color(255, 255, 255));
        key1.setBackground(new Color(50, 50, 50));
        border.add(key1);

        value1 = new JTextField("new value ");
        value1.setPreferredSize(new Dimension(290, 40));
        value1.addActionListener(bodyhandler);
        value1.addFocusListener(bodyhandler);
        value1.setForeground(new Color(255, 255, 255));
        value1.setBackground(new Color(50, 50, 50));
        border.add(value1);

        checkBox = new JCheckBox();
        border.add(checkBox);

        delete = new JButton(new ImageIcon("trash.png"));
        delete.addActionListener(bodyhandler);
        delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                delete.setBackground(Color.RED);
            }
        
            public void mouseExited(java.awt.event.MouseEvent evt) {
                delete.setBackground(UIManager.getColor("control"));
            }
        });
        border.add(delete);
        border.setBackground(new Color(50, 50, 50));
        bodyBordersList.add(border);
        bodyTab.add(border);
    }

    /**
     * remove a body key and value with delete button
     */
    public void removeBodyBorder(int i) {
        bodyTab.remove(i);
    }

    /**
     * add a new pair of header key and value when a pair ends
     */
    public void addHeaderBorder() {
        JPanel border = new JPanel(new FlowLayout(5, 5, 5));
        key2 = new JTextField("new name ");
        key2.setPreferredSize(new Dimension(290, 40));
        key2.addActionListener(headerhandler);
        key2.addFocusListener(headerhandler);
        key2.setForeground(new Color(255, 255, 255));
        key2.setBackground(new Color(50, 50, 50));
        border.add(key2);

        value2 = new JTextField("new value ");
        value2.setPreferredSize(new Dimension(290, 40));
        value2.addActionListener(headerhandler);
        value2.addFocusListener(headerhandler);
        value2.setForeground(new Color(255, 255, 255));
        value2.setBackground(new Color(50, 50, 50));
        border.add(value2);

        checkBox = new JCheckBox();
        border.add(checkBox);

        delete = new JButton(new ImageIcon("trash.png"));
        delete.addActionListener(headerhandler);
        
        border.add(delete);
        border.setBackground(new Color(50, 50, 50));
        headerBordersList.add(border);
        headerTab.add(border);
    }

    /**
     * remove a header key and value with delete button
     */
    public void removeHeaderBorder(int i) {
        headerTab.remove(i);
    }

    /**
     * set a requset in middle panel when a request in left panel is selected
     * @param requ request to set
     * @throws IOException
     */
    public static void loadRequest(HTTPRequest requ) throws IOException{

        bodyTab.removeAll();
        headerTab.removeAll();
        urlPanel.removeAll();
        bodyBordersList = new ArrayList<>();
        headerBordersList = new ArrayList<>();
        
        // manage textfield
        URLField.setText(requ.getUrlString());
        URLField.setPreferredSize(new Dimension(500, 30));
        // URLField.addActionListener(urlbuttonHandler);
        // URLField.addFocusListener(urlbuttonHandler);
        typeCombo.setSelectedItem(requ.getMethod());

        sendButton = new JButton("Send");
        saveButton = new JButton("Save");
        // manage button
        sendButton.addActionListener(urlbuttonHandler);
        sendButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sendButton.setBackground(Color.GREEN);
            }
        
            public void mouseExited(java.awt.event.MouseEvent evt) {
                sendButton.setBackground(UIManager.getColor("control"));
            }
        });
        saveButton.addActionListener(urlbuttonHandler);
        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                saveButton.setBackground(new Color(0,200,200));
            }
        
            public void mouseExited(java.awt.event.MouseEvent evt) {
                saveButton.setBackground(UIManager.getColor("control"));
            }
        });
        // manage layout
        urlPanel.setBackground(new Color(255, 255, 255));
        typeCombo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                typeCombo.setBackground(new Color(255,0,150));
            }
        
            public void mouseExited(java.awt.event.MouseEvent evt) {
                typeCombo.setBackground(UIManager.getColor("control"));
            }
        });
        urlPanel.add(typeCombo);
        urlPanel.add(URLField);
        urlPanel.add(sendButton);
        urlPanel.add(saveButton);

        for (String key : requ.getBody().keySet()) {
            JPanel border = new JPanel(new FlowLayout(5, 5, 5));
            key1 = new JTextField(key);
            key1.setPreferredSize(new Dimension(290, 40));
            key1.setForeground(new Color(255, 255, 255));
            key1.setBackground(new Color(50, 50, 50));
            border.add(key1);

            value1 = new JTextField(requ.getBody().get(key));
            value1.setPreferredSize(new Dimension(290, 40));
            value1.setForeground(new Color(255, 255, 255));
            value1.setBackground(new Color(50, 50, 50));
            border.add(value1);

            checkBox = new JCheckBox();
            border.add(checkBox);

            delete = new JButton(new ImageIcon("trash.png"));
            delete.addActionListener(bodyhandler);
            
            border.add(delete);
            border.setBackground(new Color(50, 50, 50));
            bodyBordersList.add(border);
            bodyTab.add(border);
        }
        JPanel border1 = new JPanel(new FlowLayout(5, 5, 5));
        key1 = new JTextField("new name ");
        key1.setPreferredSize(new Dimension(290, 40));
        key1.addActionListener(bodyhandler);
        key1.addFocusListener(bodyhandler);
        key1.setForeground(new Color(255, 255, 255));
        key1.setBackground(new Color(50, 50, 50));
        border1.add(key1);
        value1 = new JTextField("new value ");
        value1.setPreferredSize(new Dimension(290, 40));
        value1.addActionListener(bodyhandler);
        value1.addFocusListener(bodyhandler);
        value1.setForeground(new Color(255, 255, 255));
        value1.setBackground(new Color(50, 50, 50));
        border1.add(value1);
        checkBox = new JCheckBox();
        border1.add(checkBox);
        delete = new JButton(new ImageIcon("trash.png"));
        delete.addActionListener(bodyhandler);
        
        border1.add(delete);
        border1.setBackground(new Color(50, 50, 50));
        bodyBordersList.add(border1);
        bodyTab.add(border1);

        for (String key : requ.getHeaders().keySet()) {
            JPanel border = new JPanel(new FlowLayout(5, 5, 5));
            key2 = new JTextField(key);
            key2.setPreferredSize(new Dimension(290, 40));
            key2.setForeground(new Color(255, 255, 255));
            key2.setBackground(new Color(50, 50, 50));
            border.add(key2);

            value2 = new JTextField(requ.getHeaders().get(key));
            value2.setPreferredSize(new Dimension(290, 40));
            value2.setForeground(new Color(255, 255, 255));
            value2.setBackground(new Color(50, 50, 50));
            border.add(value2);

            checkBox = new JCheckBox();
            border.add(checkBox);

            delete = new JButton(new ImageIcon("trash.png"));
            delete.addActionListener(headerhandler);
           
            border.add(delete);
            border.setBackground(new Color(50, 50, 50));
            headerBordersList.add(border);
            headerTab.add(border);
        }
        JPanel border2 = new JPanel(new FlowLayout(5, 5, 5));
        key2 = new JTextField("new name ");
        key2.setPreferredSize(new Dimension(290, 40));
        key2.addActionListener(headerhandler);
        key2.addFocusListener(headerhandler);
        key2.setForeground(new Color(255, 255, 255));
        key2.setBackground(new Color(50, 50, 50));
        border2.add(key2);
        value2 = new JTextField("new value ");
        value2.setPreferredSize(new Dimension(290, 40));
        value2.addActionListener(headerhandler);
        value2.addFocusListener(headerhandler);
        value2.setForeground(new Color(255, 255, 255));
        value2.setBackground(new Color(50, 50, 50));
        border2.add(value2);
        checkBox = new JCheckBox();
        border2.add(checkBox);
        delete = new JButton(new ImageIcon("trash.png"));
        delete.addActionListener(headerhandler);
       
        border2.add(delete);
        border2.setBackground(new Color(50, 50, 50));
        headerBordersList.add(border2);
        headerTab.add(border2);


    }

    private class BodyButtonHandler implements ActionListener, FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            if (e.getSource().equals(value1)) {
                value1.setText("");
                addBodyBorder();
            } else if (e.getSource().equals(key1)) {
                key1.setText("");
            }
            revalidate();
        }

        @Override
        public void focusLost(FocusEvent e) {
            //displayMessage("Focus lost", e);

        }

        void displayMessage(String prefix, FocusEvent e) {
            System.out.println(prefix + (e.isTemporary() ? " (temporary):" : ":")
                    + e.getComponent().getClass().getName() + "; Opposite component: "
                    + (e.getOppositeComponent() != null ? e.getOppositeComponent().getClass().getName() : "null"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < bodyBordersList.size(); i++) {
                if (e.getSource().equals(bodyBordersList.get(i).getComponent(3))) {
                    if (bodyBordersList.size() > 1 && i != (bodyBordersList.size() - 1)) {
                        bodyBordersList.remove(i);
                        removeBodyBorder(i);
                        revalidate();
                        repaint();
                    }
                }
            }
        }
    }

    private class HeaderButtonHandler implements ActionListener, FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            if (e.getSource().equals(value2)) {
                value2.setText("");
                addHeaderBorder();
            } else if (e.getSource().equals(key2)) {
                key2.setText("");
            }
            revalidate();
        }

        @Override
        public void focusLost(FocusEvent e) {
            //displayMessage("Focus lost", e);

        }

        void displayMessage(String prefix, FocusEvent e) {
            System.out.println(prefix + (e.isTemporary() ? " (temporary):" : ":")
                    + e.getComponent().getClass().getName() + "; Opposite component: "
                    + (e.getOppositeComponent() != null ? e.getOppositeComponent().getClass().getName() : "null"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < headerBordersList.size(); i++) {
                if (e.getSource().equals(headerBordersList.get(i).getComponent(3))) {
                    if (headerBordersList.size() > 1 && i != headerBordersList.size() - 1) {
                        headerBordersList.remove(i);
                        removeHeaderBorder(i);
                        revalidate();
                    }

                }
            }
        }
    }

    private class URLButtonHandler implements ActionListener, FocusListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(URLField)) {
                System.out.println("urlfield");
            } else if (e.getSource().equals(sendButton)) {
                sendThread();
            } else if (e.getSource().equals(typeCombo)) {
                System.out.println("combo");
            } else if (e.getSource().equals(saveButton)) {
                
                saveThread();
            }

        }

        @Override
        public void focusGained(FocusEvent e) {
            displayMessage("Focus gained", e);
            if (e.getSource().equals(URLField)) {
                URLField.setText("");
            }
            revalidate();
        }

        @Override
        public void focusLost(FocusEvent e) {
            //displayMessage("Focus lost", e);
        }

        void displayMessage(String prefix, FocusEvent e) {
            System.out.println(prefix + (e.isTemporary() ? " (temporary):" : ":")
                    + e.getComponent().getClass().getName() + "; Opposite component: "
                    + (e.getOppositeComponent() != null ? e.getOppositeComponent().getClass().getName() : "null"));
        }
    }

    /**
     * method to send a data for server
     */
    public static void bufferOutFormData(HashMap<String, String> map, String boundary,
            BufferedOutputStream bufferedOutputStream) throws IOException, NullPointerException {
        for (String key : map.keySet()) {
            bufferedOutputStream.write(("--" + boundary + "\r\n").getBytes());
            if (key.contains("file")) {
                bufferedOutputStream.write(("Content-Disposition: form-data; filename=\""
                        + (new File(map.get(key))).getName() + "\"\r\nContent-Type: Auto\r\n\r\n").getBytes());
                try {
                    BufferedInputStream tempBufferedInputStream = new BufferedInputStream(
                            new FileInputStream(new File(map.get(key))));
                    int nRead = -1;
                    byte[] data1 = new byte[1024];
                    while ((nRead = tempBufferedInputStream.read(data1)) != -1) {
                        bufferedOutputStream.write(data1, 0, nRead);
                    }
                    bufferedOutputStream.write("\r\n".getBytes());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                bufferedOutputStream.write(("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n").getBytes());
                bufferedOutputStream.write((map.get(key) + "\r\n").getBytes());
            }
        }
        bufferedOutputStream.write(("--" + boundary + "--\r\n").getBytes());
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }

    public HashMap<String, String> getBodyMap() {
        for (JPanel border : bodyBordersList) {
            if (((JCheckBox) (border.getComponent(2))).isSelected()) {
                bodyMap.put(((JTextField) (border.getComponent(0))).getText(),
                        ((JTextField) (border.getComponent(1))).getText());
            }
        }
        return bodyMap;
    }

    public HashMap<String, String> getHeadersMap() {
        for (JPanel border : headerBordersList) {
            if (((JCheckBox) (border.getComponent(2))).isSelected()) {
                headersMap.put(((JTextField) (border.getComponent(0))).getText(),
                        ((JTextField) (border.getComponent(1))).getText());
            }
        }
        return headersMap;
    }

    public static HashMap<String, String> getAllBodyMap() {
        bodyMap = new HashMap<>();
        for (JPanel border : bodyBordersList) {

            bodyMap.put(((JTextField) (border.getComponent(0))).getText(),
                    ((JTextField) (border.getComponent(1))).getText());

        }
        bodyMap.remove("new name ");
        bodyMap.remove("");
        return bodyMap;
    }

    public static HashMap<String, String> getAllHeadersMap() {
        headersMap = new HashMap<>();
        for (JPanel border : headerBordersList) {

            headersMap.put(((JTextField) (border.getComponent(0))).getText(),
                    ((JTextField) (border.getComponent(1))).getText());

        }
        headersMap.remove("new name ");
        headersMap.remove("");
        return headersMap;
    }

    /**
     * a backgruond method to send a request
     */
    public void sendThread() {

        SwingWorker sw = new SwingWorker() {

            @Override
            protected String doInBackground() throws Exception, NullPointerException {
                showResponse = 1;
                URL url;
                HTTPRequest request = new HTTPRequest();

                try {

                    url = new URL(URLField.getText());
                    request.setUrl(url);
                    request.setMethod(typeCombo.getItemAt(typeCombo.getSelectedIndex()));
                    request.setBody(getBodyMap());
                    request.setHeaders(getHeadersMap());

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    time1 = System.currentTimeMillis();
                    String method = request.getMethod();

                    con.setRequestMethod(method);
                    if (!method.equals("GET") && !method.equals("DELETE")) {
                        con.setDoOutput(true);
                    }

                    for (Map.Entry<String, String> entry : headersMap.entrySet()) {
                        con.setRequestProperty(entry.getKey(), entry.getValue());
                    }

                    if (method.equals("GET")) {
                        try {

                            if (con.getContentType().contains("jpeg") || con.getContentType().contains("png")) {

                                InputStream inputStream = con.getInputStream();
                                FileOutputStream outputStream = new FileOutputStream(new File("pic.png"));
                                int bytesRead = -1;
                                byte[] buffer = new byte[1024];
                                while ((bytesRead = inputStream.read(buffer)) != -1) {
                                    outputStream.write(buffer, 0, bytesRead);
                                }
                                outputStream.close();
                                inputStream.close();
                            }
                        } catch (Exception e) {

                        }

                    } else if (method.equals("POST")) {
                        try {
                            String boundary = System.currentTimeMillis() + "";
                            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                            BufferedOutputStream request1 = new BufferedOutputStream(con.getOutputStream());
                            bufferOutFormData(getBodyMap(), boundary, request1);

                        } catch (Exception e) {
                            System.out.println("method error");
                        }
                    } else if (method.equals("PUT")) {
                        try {
                            String boundary = System.currentTimeMillis() + "";
                            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                            BufferedOutputStream request1 = new BufferedOutputStream(con.getOutputStream());
                            bufferOutFormData(getBodyMap(), boundary, request1);
                        } catch (Exception e) {
                            System.out.println("method error");
                        }

                    } else if (method.equals("DELETE")) {
                        try {
                            String boundary = System.currentTimeMillis() + "";
                            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                            BufferedOutputStream request1 = new BufferedOutputStream(con.getOutputStream());
                            bufferOutFormData(getBodyMap(), boundary, request1);

                        } catch (Exception e) {
                            System.out.println("method error");
                        }
                    }

                    try {
                        try {
                            InfoBar.addInfos(con,time1);
                            ResponsePanel.addElements(con);
                        } catch (UnknownHostException e) {

                        }
                    } catch (IOException e) {
                        // e.printStackTrace();
                    }

                    revalidate();

                    String res = "Finished Execution";
                    return res;
                } catch (MalformedURLException e) {
                    System.out.println("url problem");
                }

                return null;

            }

            @Override
            protected void done() {

            }
        };

        sw.execute();
    }

    /**
     * a backgruond method to save a request
     */
    public void saveThread() {

        SwingWorker sw = new SwingWorker() {

            @Override
            protected String doInBackground() throws Exception, IOException, NullPointerException {
                URL url;
                HTTPRequest request = new HTTPRequest();
                try {
                    url = new URL(URLField.getText());
                    request.setUrl(url);
                    request.setUrlString(URLField.getText());
                } catch (MalformedURLException e) {

                }
                request.setMethod(typeCombo.getItemAt(typeCombo.getSelectedIndex()));
                request.setBody(getAllBodyMap());
                request.setHeaders(getAllHeadersMap());
                
                try {
                    File requestsFile = new File("requestsFile.txt");
                    requestsList.add(request);
                    
                    
                    FileOutputStream outStream = new FileOutputStream(requestsFile);
                    ObjectOutputStream out = new ObjectOutputStream(outStream);
                    out.writeObject(requestsList);
                    out.flush();
                    outStream.flush();
                    out.close();
                    outStream.close();

                } catch (Exception ex) {
                    //ex.printStackTrace();
                }
                RequestListPanel.update(requestsList);
                revalidate();
                String res = "Finished Execution";
                return res;
            }

            @Override
            protected void done() {

            }

        };

        sw.execute();
    }

    /**
     * a backgruond method to save a state
     */
    public void saveState() {

        SwingWorker sw = new SwingWorker() {

            @Override
            protected String doInBackground() throws Exception, IOException, NullPointerException {
                URL url;
                HTTPRequest request = new HTTPRequest();
                try {
                    url = new URL(URLField.getText());
                    request.setUrl(url);
                    request.setUrlString(URLField.getText());
                } catch (MalformedURLException e) {

                }
                request.setMethod(typeCombo.getItemAt(typeCombo.getSelectedIndex()));
                request.setBody(getAllBodyMap());
                request.setHeaders(getAllHeadersMap());
                
                try {
                    File state = new File("state.txt");
                
                    FileOutputStream outStream = new FileOutputStream(state);
                    ObjectOutputStream out = new ObjectOutputStream(outStream);
                    out.writeObject(request);
                    out.flush();
                    outStream.flush();
                    out.close();
                    outStream.close();

                } catch (Exception ex) {
                    //ex.printStackTrace();
                }

                try {
                    File state = new File("responseState.txt");
                
                    FileOutputStream outStream = new FileOutputStream(state);
                    ObjectOutputStream out = new ObjectOutputStream(outStream);
                    out.writeObject(showResponse);
                    out.flush();
                    outStream.flush();
                    out.close();
                    outStream.close();

                } catch (Exception ex) {
                    //ex.printStackTrace();
                }
                String res = "Finished Execution";
                return res;
            }

            @Override
            protected void done() {

            }

        };

        sw.execute();
    }

    public int getShowResponse() {
        return showResponse;
    }
}
