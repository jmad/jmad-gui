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

import cern.accsoft.gui.beans.spi.SplashScreen;
import cern.accsoft.steering.jmad.gui.config.JMadGuiStandaloneConfiguration;
import cern.accsoft.steering.jmad.gui.dialog.JMadOptionPane;
import cern.accsoft.steering.jmad.gui.manage.SplashFactory;
import cern.accsoft.steering.jmad.gui.mark.MarkedElementsManager;
import cern.accsoft.steering.jmad.gui.panels.JMadPanelFactory;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.service.JMadService;
import cern.accsoft.steering.jmad.util.JMadPreferences;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.spi.RootLogger;
import org.jmad.modelpack.gui.conf.JMadModelSelectionDialogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.Frame;

public class JMad {

    private static final Supplier<ApplicationContext> CONTEXT_SUPPLIER = Suppliers.memoize(() -> new AnnotationConfigApplicationContext(JMadGuiStandaloneConfiguration.class));
    private static final Supplier<JMadGui> JMAD_GUI = Suppliers.memoize(() -> CONTEXT_SUPPLIER.get().getBean(JMadGui.class));;
    private static final Supplier<MarkedElementsManager> MARKED_ELEMENTS_MANAGER = Suppliers.memoize(() -> CONTEXT_SUPPLIER.get().getBean(MarkedElementsManager.class));;
    private static final Supplier<JMadModelSelectionDialogFactory> MODELPACK_DIALOG_FACTORY = Suppliers.memoize(() -> CONTEXT_SUPPLIER.get().getBean(JMadModelSelectionDialogFactory.class));;
    private static final Supplier<JMadService> JMAD_SERVICE = Suppliers.memoize(() -> CONTEXT_SUPPLIER.get().getBean(JMadService.class));;

    public static JMadModel showCreateModelDialog() {
        return JMadOptionPane.showCreateModelDialog(MODELPACK_DIALOG_FACTORY.get(), JMAD_SERVICE.get());
    }

    public static void showExportModelDefinitionDialog(Frame frame) {
        JMadOptionPane.showExportModelDefinitionDialog(frame, JMAD_SERVICE.get());
    }

    public static JMadModel showImportModelDefinitionDialog(Frame frame) {
        return JMadOptionPane.showImportModelDefinitionDialog(frame, JMAD_SERVICE.get());
    }

    public static JMadService getJMadService() {
        return JMAD_SERVICE.get();
    }

    public static JMadModelManager getModelManager() {
        return JMAD_SERVICE.get().getModelManager();
    }

    public static MarkedElementsManager getMarkedElementsManager() {
        return MARKED_ELEMENTS_MANAGER.get();
    }

    public static JMadPreferences getJMadPreferences() {
        return JMAD_SERVICE.get().getPreferences();
    }

    public static JMadPanelFactory getJMadPanelFactory() {
        return CONTEXT_SUPPLIER.get().getBean(JMadPanelFactory.class);
    }

    public static SplashScreen getJMadGuiSplashScreen() {
        return SplashFactory.getSplashScreen();
    }

    public static JMadGui getJMadGui() {
        return JMAD_GUI.get();
    }

    public static void main(String[] args) {
        configureLogger();
        setupLookAndFeel();

        SplashScreen splashScreen = JMad.getJMadGuiSplashScreen();
        JMad.getJMadGui().show();
    }

    private static void configureLogger() {
        BasicConfigurator.configure();
        RootLogger.getRootLogger().setLevel(Level.INFO);
    }

    private static void setupLookAndFeel() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            /* Default look and feel */
        }
    }
}
