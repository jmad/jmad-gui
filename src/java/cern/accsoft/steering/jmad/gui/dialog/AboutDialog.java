package cern.accsoft.steering.jmad.gui.dialog;

import cern.accsoft.steering.jmad.gui.icons.Icon;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Objects;

/**
 * Simple about box
 */
public class AboutDialog {
    private static final Dimension PREFERRED_SIZE = new Dimension(700, 250);

    private final JDialog dialog;
    private final JPanel contentPane;

    public AboutDialog(JFrame parentFrame) {
        dialog = new JDialog(parentFrame);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setPreferredSize(PREFERRED_SIZE);
        contentPane = new JPanel(new BorderLayout(10, 10));
        dialog.setContentPane(contentPane);
    }

    public void setIcon(ImageIcon imageIcon) {
        Objects.requireNonNull(imageIcon);

        dialog.setIconImage(imageIcon.getImage());
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(imageIcon);
        iconLabel.setPreferredSize(new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight()));
        contentPane.add(iconLabel, BorderLayout.WEST);
    }

    public void setText(String title, String product, String extra) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<h1>" + title + "</h1>");
        sb.append("<table cellSpacing=0 cellPadding=0 border=0>");
        sb.append("<tr><td><b>Product:&nbsp;</b></td><td>" + product + "</td></tr>");
        sb.append("</table><br>");
        sb.append(extra + "<br>");
        sb.append("</html>");

        JPanel box = new JPanel();
        box.add(new JLabel(sb.toString()));

        contentPane.add(box, BorderLayout.CENTER);
    }

    public void show() {
        dialog.pack();
        dialog.setVisible(true);
    }

}
