// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

package cern.accsoft.steering.jmad.gui;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.log4j.BasicConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cern.accsoft.gui.beans.spi.SplashScreen;
import cern.accsoft.steering.jmad.gui.manage.JMadGuiPreferences;
import cern.accsoft.steering.jmad.gui.manage.SplashFactory;
import cern.accsoft.steering.jmad.gui.manage.impl.JMadGuiPreferencesImpl;
import cern.accsoft.steering.jmad.gui.mark.MarkedElementsManager;
import cern.accsoft.steering.jmad.gui.mark.MarkedElementsManagerImpl;
import cern.accsoft.steering.jmad.gui.menu.JMadMenuFactory;
import cern.accsoft.steering.jmad.gui.panels.JMadPanelFactory;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.service.JMadService;
import cern.accsoft.steering.jmad.service.JMadServiceFactory;

public class JMad {

    /*
     * bean names
     */
    private final static String BEAN_NAME_JMAD_GUI = "jmadGui";
    private final static String BEAN_NAME_MODEL_MANAGER = "modelManager";
    private final static String BEAN_NAME_JMAD_GUI_PREFERENCES = "jmadGuiPreferences";
    private final static String BEAN_NAME_JMAD_PANEL_FACTORY = "jmadPanelFactory";
    private final static String BEAN_NAME_JMAD_MENU_FACTORY = "jmadMenuFactory";
    private final static String BEAN_NAME_MARKED_ELEMENTS_MANAGER = "markedElementsManager";
    private final static String BEAN_NAME_SPLASH_SCREEN = "splashScreen";
    private final static String BEAN_NAME_JMAD_SERVICE = "jmadService";

    /**
     * the app-context of the gui.
     */
    private ApplicationContext appCtx = null;

    /**
     * the default constructor
     */
    public JMad() {
        this(null, null, null);
    }

    /**
     * the constructor, for which one can provide a JMadService, a modelManager and preferences.
     * 
     * @param jmadService the service to which to attach
     * @param jmadGuiPreferences the preferences that define the behaviour of the GUI
     * @param markedElementsManager the manager which keeps track of marked elements
     */
    public JMad(JMadService jmadService, JMadGuiPreferences jmadGuiPreferences,
            MarkedElementsManager markedElementsManager) {
        super();
        /* show the Splash - screen */
        SplashFactory.getSplashScreen();

        if (jmadService == null) {
            jmadService = JMadServiceFactory.createJMadService();
        }
        JMadContextInjector.putBean(BEAN_NAME_JMAD_SERVICE, jmadService);
        JMadContextInjector.putBean(BEAN_NAME_MODEL_MANAGER, jmadService.getModelManager());

        if (jmadGuiPreferences == null) {
            jmadGuiPreferences = new JMadGuiPreferencesImpl();
        }
        JMadContextInjector.putBean(BEAN_NAME_JMAD_GUI_PREFERENCES, jmadGuiPreferences);

        if (markedElementsManager == null) {
            markedElementsManager = new MarkedElementsManagerImpl();
        }
        JMadContextInjector.putBean(BEAN_NAME_MARKED_ELEMENTS_MANAGER, markedElementsManager);

        /* creating the application - context. */
        this.appCtx = new ClassPathXmlApplicationContext(new String[] { "app-ctx-jmad-gui.xml" });
    }

    public JMad(JMadGuiPreferences jmadGuiPreferences) {
        this(null, jmadGuiPreferences, null);
    }

    public void show() {
        JMadGui jmadGui = getJMadGuiBean();
        if (jmadGui != null) {
            jmadGui.show();
        }
    }

    private Object getBean(String beanName) {
        if (this.appCtx == null) {
            return null;
        }
        return this.appCtx.getBean(beanName);
    }

    /**
     * @return the {@link JMadGui} bean from the application-context
     */
    private JMadGui getJMadGuiBean() {
        return (JMadGui) getBean(BEAN_NAME_JMAD_GUI);
    }

    public MarkedElementsManager getMarkedElementsManager() {
        return (MarkedElementsManager) getBean(BEAN_NAME_MARKED_ELEMENTS_MANAGER);
    }

    /**
     * @return the menu factory
     */
    public JMadMenuFactory getJMadMenuFactory() {
        return (JMadMenuFactory) getBean(BEAN_NAME_JMAD_MENU_FACTORY);
    }

    public JMadModelManager getModelManager() {
        return (JMadModelManager) getBean(BEAN_NAME_MODEL_MANAGER);
    }

    public SplashScreen getSplashScreen() {
        return (SplashScreen) getBean(BEAN_NAME_SPLASH_SCREEN);
    }

    public JMadPanelFactory getJMadPanelFactory() {
        return (JMadPanelFactory) getBean(BEAN_NAME_JMAD_PANEL_FACTORY);
    }

    /*
     * the main method
     * 
     * @param args
     */
    public static void main(String[] args) {
        /* configure the log4j - system */
        BasicConfigurator.configure();
        
        setupLookAndFeel();

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                JMad jmad = new JMad();
                jmad.show();

            }
        });
    }

    private static void setupLookAndFeel() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
        } catch (ClassNotFoundException e) {
            // handle exception
        } catch (InstantiationException e) {
            // handle exception
        } catch (IllegalAccessException e) {
            // handle exception
        }
    }
}
