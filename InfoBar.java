
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.UnknownHostException;

/**
 * bar in above of response tab for status code and etc
 */

public class InfoBar extends JPanel {

    private static JTextField stcode;
    private static JTextField restime;
    private static JTextField messize;
    private static long time2;


    public InfoBar() {
        super(new FlowLayout(50, 110, 10));

        stcode = new JTextField();
        stcode.setText("           ");
        restime = new JTextField();
        restime.setText("           ");
        messize = new JTextField();
        messize.setText("           ");
            
        setBackground(new Color(255, 255, 255));

        this.add(stcode);
        this.add(restime);
        this.add(messize);
        this.setPreferredSize(new Dimension(300, 50));
    }

    /**
     * add information of connection and request to info bar
     * @param con connection
     * @param time1 time of sending request
     * @throws UnknownHostException
     */
    public static void addInfos(HttpURLConnection con, long time1) throws UnknownHostException{
        // status code
        try {
            stcode.setText(con.getResponseCode() + " " + con.getResponseMessage());
        } catch (IOException e) {
            
        }
        time2  = System.currentTimeMillis();
        stcode.setForeground(Color.WHITE);
        stcode.setBackground(new Color(0,200,0));
        stcode.setPreferredSize(new Dimension(100,30));
        stcode.setEditable(false);
        //response time 
        restime.setText((time2-time1)+" ms");
        restime.setForeground(Color.BLACK);
        restime.setBackground(Color.LIGHT_GRAY);
        restime.setPreferredSize(new Dimension(100,30));
        restime.setEditable(false);
        //message size of file
        if(con.getContentLength()==-1){
            messize.setText("unknown size");
        }
        else{
            messize.setText(con.getContentLength()+" KB");
        }
        
        messize.setForeground(Color.BLACK);
        messize.setBackground(Color.LIGHT_GRAY);
        messize.setPreferredSize(new Dimension(100,30));
        messize.setEditable(false);
        
    }
}