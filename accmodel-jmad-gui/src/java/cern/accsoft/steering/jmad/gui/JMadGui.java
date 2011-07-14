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
package cern.accsoft.steering.jmad.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.machine.Range;
import cern.accsoft.steering.jmad.gui.icons.Icon;
import cern.accsoft.steering.jmad.gui.manage.JMadGuiPreferences;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.JMadModelListener;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.model.manage.JMadModelManagerAdapter;
import cern.accsoft.steering.util.gui.DefaultAccsoftGui;

/**
 * this class represents the main frame for the jmad-gui standalone application
 * 
 * @author kfuchsbe
 */
public class JMadGui extends DefaultAccsoftGui {
    private static final long serialVersionUID = -8292677890152652172L;

    private static Logger logger = Logger.getLogger(DefaultAccsoftGui.class);

    /** the model manager, necessary for a final cleanup */
    private JMadModelManager modelManager = null;

    /** the preferences */
    private JMadGuiPreferences jmadGuiPreferences;

    private final static String TITLE_BASE = "jmad ";

    @Override
    protected WindowListener getWindowListener() {
        WindowListener wndCloser = new WindowAdapter() {
            public void windowClosing(@SuppressWarnings("unused") WindowEvent e) {
                if (getJmadGuiPreferences() != null) {
                    if (getJmadGuiPreferences().isCleanupOnClose()) {
                        cleanup();
                    }
                    if (getJmadGuiPreferences().isExitOnClose()) {
                        System.exit(0);
                    }
                } else {
                    /* By default we cleanup everything. */
                    cleanup();
                    System.exit(0);
                }
            }
        };
        return wndCloser;
    }

    /**
     * closes the model.
     */
    protected void cleanup() {
        if (this.modelManager != null) {
            JMadModel model = this.modelManager.getActiveModel();
            if (model != null) {
                try {
                    model.cleanup();
                } catch (JMadModelException e) {
                    logger.error("Error while cleaning up model", e);
                }
            }
        }
    }

    /**
     * setter used by spring
     * 
     * @param modelManager the {@link JMadModelManager} to set
     */
    public void setModelManager(JMadModelManager modelManager) {
        this.modelManager = modelManager;

        this.modelManager.addListener(new JMadModelManagerAdapter() {

            @Override
            public void changedActiveModel(JMadModel newModel) {
                setModel(newModel);
            }
        });

        if (modelManager.getActiveModel() != null) {
            setModel(modelManager.getActiveModel());
        }
    }

    protected void setModel(JMadModel model) {

        if (model != null) {
            model.addListener(new JMadModelListener() {

                @Override
                public void becameDirty() {
                    /* nothing to do */
                }

                @Override
                public void elementsChanged() {
                    /* nothing to do */
                }

                @Override
                public void opticsChanged() {
                    /* nothing to do */
                }

                @Override
                public void opticsDefinitionChanged() {
                    updateTitle();
                }

                @Override
                public void rangeChanged(@SuppressWarnings("unused") Range newRange) {
                    updateTitle();
                }

            });
        }
        updateTitle();
    }

    protected void updateTitle() {
        if (this.modelManager == null) {
            return;
        }

        JMadModel model = this.modelManager.getActiveModel();

        if (model == null) {
            setTitle(TITLE_BASE);
        } else {
            setTitle(TITLE_BASE + model.getDescription());
        }
    }

    @Override
    protected ImageIcon getImageIcon() {
        return Icon.JMAD.getImageIcon();
    }

    public void setJmadGuiPreferences(JMadGuiPreferences jmadGuiPreferences) {
        this.jmadGuiPreferences = jmadGuiPreferences;
    }

    protected JMadGuiPreferences getJmadGuiPreferences() {
        return jmadGuiPreferences;
    }

    @Override
    protected void callbackBeforeInit() {
        if (getJmadGuiPreferences() != null) {
            setMainFrame(getJmadGuiPreferences().isMainFrame());
        }
    }

}
