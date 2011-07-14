// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, Kajetan Fuchsberger. All rights reserved.
 * 
 * JMad is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JMad is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JMad.  If not, see <http://www.gnu.org/licenses/>.
 * 
 ******************************************************************************/
// @formatter:on
package cern.accsoft.steering.util.gui;

import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import org.apache.log4j.Level;

import cern.accsoft.gui.beans.spi.SplashScreen;
import cern.accsoft.gui.frame.ExternalFrame;
import cern.accsoft.gui.frame.FrameManager;
import cern.accsoft.steering.util.gui.log.AccsoftLogAppender;

/**
 * This class combines the main elements of the gui: lsa-frame and custom panels.
 * 
 * @author kfuchsbe
 */
public class DefaultAccsoftGui {

    /** the x-size default */
    private final static int DEFAULT_SIZE_X = 800;

    /** the y-size default */
    private final static int DEFAULT_SIZE_Y = 600;

    /** the main panel to use */
    private JPanel mainPanel;

    /** the menu bar to use */
    private JMenuBar menuBar;

    /** the toolbar to use */
    private JToolBar toolBar;

    /** extra components for the console-panel */
    private Map<String, JComponent> extraConsoleTabs = new HashMap<String, JComponent>();

    /** x- size of the gui */
    private int sizeX = DEFAULT_SIZE_X;

    /** y - size of the gui */
    private int sizeY = DEFAULT_SIZE_Y;

    /** the instance of the LogAppender */
    private AccsoftLogAppender logAppender;

    /** the logLevel for the console */
    private Level consoleLogLevel = Level.DEBUG;

    /**
     * the splash-screen. We want to set the mainFrame to it, so that it closes finally
     */
    private SplashScreen splashScreen;

    /**
     * here we store the mainFrame, must be initialized from the beginning, to be able to set to the splash-screen
     */
    private ExternalFrame frame = null;

    /** the title of the application */
    private String title = "TITLE NOT SET!";

    /** if true, the gui uses the mainframe, if not it uses a new external fram */
    private boolean mainFrame = true;

    /**
     * initialization method for spring. This method also initializes the accsoft gui-framework.
     */
    public final void init() {
        callbackBeforeInit();

        if (this.mainFrame) {
            this.frame = FrameManager.getInstance().getMainFrame();
        } else {
            this.frame = FrameManager.getInstance().createExternalFrame();
        }

        WindowListener wndListener = getWindowListener();
        if (wndListener != null) {
            getJFrame().addWindowListener(wndListener);
        }

        ImageIcon icon = getImageIcon();
        if (icon != null) {
            getJFrame().setIconImage(icon.getImage());
        }

        if (splashScreen != null) {
            splashScreen.setFrame(getJFrame());
        }
        if (mainPanel != null) {
            frame.setRootComponent(mainPanel);
        }
        if (menuBar != null) {
            frame.setJMenuBar(menuBar);
        }
        if (toolBar != null) {
            frame.addToolBar(toolBar);
        }
        for (String key : getExtraConsoleTabs().keySet()) {
            JComponent component = getExtraConsoleTabs().get(key);
            frame.getConsoleTabbedPane().addTab(key, component);
        }

        frame.setTitle(title);
        frame.setSize(getSizeX(), getSizeY());
        frame.setConsoleInside(true);

        logAppender = new AccsoftLogAppender(getMainFrame().getConsoleLogger(), getMainFrame().getStatusLine());
        logAppender.setMinLevel(getConsoleLogLevel());

        callbackAfterInit();
    }

    /**
     * this method may be overriden by derived classes, if there is something to do before init.
     */
    protected void callbackBeforeInit() {
    }

    /**
     * this method may be overriden by derived classes, if there is something todo after init.
     */
    protected void callbackAfterInit() {
    };

    /**
     * @return the {@link JFrame} of the mainFrame
     */
    public JFrame getJFrame() {
        return frame.getJFrame();
    }

    /**
     * this method may be overridden, if needed.
     * 
     * @return returns a window listener to be appended to the mainFrame
     */
    protected WindowListener getWindowListener() {
        return null;
    }

    /**
     * this method may be overridden, if needed.
     * 
     * @return returns an icon, set to the mainFrame.
     */
    protected ImageIcon getImageIcon() {
        return null;
    }

    /**
     * displays the gui
     */
    public void show() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (frame != null) {
                    frame.setVisible(true);
                }
            }
        });
    }

    /**
     * setter method for Spring
     * 
     * @param mainPanel the mainPanel to set
     */
    public final void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    /**
     * Setter method used by spring to inject the menubar.
     * 
     * @param menuBar
     */
    public final void setMenuBar(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }

    /**
     * Setter method used by spring to inject the toolbar.
     * 
     * @param toolBar
     */
    public final void setToolBar(JToolBar toolBar) {
        this.toolBar = toolBar;
    }

    /**
     * Setter method used by Spring to set the application title.
     * 
     * @param title
     */
    public final void setTitle(String title) {
        this.title = title;
        if (frame != null) {
            frame.setTitle(title);
        }
    }

    /**
     * Setter for spring, to set the splashScreen
     * 
     * @param splashScreen
     */
    public final void setSplashScreen(SplashScreen splashScreen) {
        this.splashScreen = splashScreen;
    }

    /**
     * @param sizeX the sizeX to set
     */
    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    /**
     * @return the sizeX
     */
    public int getSizeX() {
        return sizeX;
    }

    /**
     * @param sizeY the sizeY to set
     */
    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    /**
     * @return the sizeY
     */
    public int getSizeY() {
        return sizeY;
    }

    /**
     * @param extraConsoleTabs the extraConsoleTabs to set
     */
    public void setExtraConsoleTabs(Map<String, JComponent> extraConsoleTabs) {
        this.extraConsoleTabs = extraConsoleTabs;
    }

    /**
     * @return the extraConsoleTabs
     */
    private Map<String, JComponent> getExtraConsoleTabs() {
        return extraConsoleTabs;
    }

    protected ExternalFrame getMainFrame() {
        return this.frame;
    }

    public void setConsoleLogLevel(Level consoleLogLevel) {
        this.consoleLogLevel = consoleLogLevel;
        if (this.logAppender != null) {
            this.logAppender.setMinLevel(consoleLogLevel);
        }
    }

    private Level getConsoleLogLevel() {
        return consoleLogLevel;
    }

    public void setMainFrame(boolean mainFrame) {
        this.mainFrame = mainFrame;
    }

    public boolean isMainFrame() {
        return mainFrame;
    }
}
