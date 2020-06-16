

import javax.swing.UIManager;
/**
 * main class to manage program generally
 */
public class Main {
    
    public static void main(String[] args) {

        try { 
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
        } catch(Exception ignored){}

        Insomnia insomnia = new Insomnia();
    }
}