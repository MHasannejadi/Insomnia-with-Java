
import java.awt.*;
import javax.swing.*;

/**
 * right panel of app with 2 parts
 */

public class InfoPanel extends JPanel {

    public InfoPanel() {
        super(new BorderLayout(1, 1));
        this.setPreferredSize(new Dimension(700, 800));
        InfoBar infoBar = new InfoBar();
        ResponsePanel respa = new ResponsePanel();

        setBackground(new Color(50, 50, 50));

        this.add(infoBar, BorderLayout.NORTH);
        this.add(respa, BorderLayout.CENTER);

    }
}