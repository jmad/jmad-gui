package cern.accsoft.steering.util.meas.yasp.browse;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * displays the data contained in a yasp file.
 * 
 * @author kaifox
 */
public class YaspPreviewPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /** The panel to display the orbits */
    private DataViewerPanel dvPanel = new DataViewerPanel();

    /** The panel to display the header data of a yasp file */
    private YaspHeaderPanel headerPanel = new YaspHeaderPanel();

    /**
     * the constructor, which simply initializes all the components
     */
    public YaspPreviewPanel() {
        initComponenets();
    }

    /**
     * creates all the components and places them in the panel
     */
    private void initComponenets() {
        setLayout(new BorderLayout());

        JTabbedPane tabPane = new JTabbedPane();
        add(tabPane, BorderLayout.CENTER);

        tabPane.addTab("data", dvPanel);
        tabPane.addTab("header", headerPanel);
    }

}
