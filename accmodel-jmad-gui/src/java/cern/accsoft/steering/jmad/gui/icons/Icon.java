package cern.accsoft.steering.jmad.gui.icons;

import javax.swing.ImageIcon;

public enum Icon {
    MADX("madx.png"), JMAD("jmad.png"), SPLASH("jmad-splash.jpg"), NEW("new.png"), REFRESH("refresh.png"), DELETE(
            "delete.png"), CHART("chart.png"), EXIT("exit.gif");

    private final static String PATH_PREFIX = "data/";

    private String name = null;

    private Icon(String name) {
        this.name = name;
    }

    public ImageIcon getImageIcon() {
        return new ImageIcon(Icon.class.getResource(PATH_PREFIX + name));
    }
}
