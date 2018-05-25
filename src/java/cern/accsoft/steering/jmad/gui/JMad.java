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

import cern.accsoft.steering.jmad.conf.JMadServiceConfiguration;
import cern.accsoft.steering.jmad.gui.config.JMadGuiConfiguration;
import cern.accsoft.steering.jmad.gui.config.JMadGuiStandaloneConfiguration;
import cern.accsoft.steering.jmad.gui.manage.JMadGuiPreferences;
import cern.accsoft.steering.jmad.gui.manage.SplashFactory;
import cern.accsoft.steering.jmad.gui.manage.impl.JMadGuiPreferencesImpl;
import cern.accsoft.steering.jmad.gui.mark.MarkedElementsManager;
import cern.accsoft.steering.jmad.gui.mark.MarkedElementsManagerImpl;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.service.JMadService;
import cern.accsoft.steering.jmad.service.JMadServiceFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.spi.RootLogger;
import org.jmad.modelpack.gui.conf.JMadModelSelectionDialogConfiguration;
import org.jmad.modelpack.service.JMadModelPackageService;
import org.jmad.modelpack.service.conf.JMadModelPackageServiceConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class JMad {

    private final JMadGui jmadGui;
    private final JMadModelManager jmadModelManager;

    public JMad(JMadService service, JMadModelPackageService modelPackageService, JMadGuiPreferences guiPref, MarkedElementsManager elementsManager) {
        this(createBaseCtx(service, modelPackageService, guiPref, elementsManager));
    }

    public JMad(JMadService service, JMadGuiPreferences guiPref, MarkedElementsManager elementsManager) {
        this(createBaseCtx(service, guiPref, elementsManager));
    }

    public JMad() {
        this(createBaseCtx());
    }

    private JMad(ApplicationContext baseCtx) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.setParent(baseCtx);
        ctx.register(JMadGuiConfiguration.class, JMadModelSelectionDialogConfiguration.class);
        ctx.refresh();

        jmadGui = ctx.getBean(JMadGui.class);
        jmadModelManager = ctx.getBean(JMadModelManager.class);
    }

    private static ApplicationContext createBaseCtx() {
        AnnotationConfigApplicationContext fullServiceCtx = new AnnotationConfigApplicationContext(JMadServiceConfiguration.class, JMadModelPackageServiceConfiguration.class);
        JMadService service = fullServiceCtx.getBean(JMadService.class);
        JMadModelPackageService modelpackService = fullServiceCtx.getBean(JMadModelPackageService.class);
        fullServiceCtx.close();

        return createBaseCtx(service, modelpackService, new JMadGuiPreferencesImpl(), new MarkedElementsManagerImpl());
    }

    private static ApplicationContext createBaseCtx(JMadService service, JMadGuiPreferences guiPref, MarkedElementsManager elementsManager) {
        AnnotationConfigApplicationContext modelpackCtx = new AnnotationConfigApplicationContext();
        modelpackCtx.registerBean("jmadService", JMadService.class, () -> service);
        modelpackCtx.register(JMadModelPackageServiceConfiguration.class);
        modelpackCtx.refresh();
        JMadModelPackageService modelPackageService = modelpackCtx.getBean(JMadModelPackageService.class);
        modelpackCtx.close();

        return createBaseCtx(service, modelPackageService, guiPref, elementsManager);
    }

    private static ApplicationContext createBaseCtx(JMadService service, JMadModelPackageService modelPackageService, JMadGuiPreferences guiPref, MarkedElementsManager elementsManager) {
        GenericApplicationContext ctx = new GenericApplicationContext();
        ctx.registerBean("jmadService", JMadService.class, () -> service);
        ctx.registerBean("modelManager", JMadModelManager.class, service::getModelManager);
        ctx.registerBean("jmadGuiPreferences", JMadGuiPreferences.class, () -> guiPref);
        ctx.registerBean("markedElementsManager", MarkedElementsManager.class, () -> elementsManager);
        ctx.registerBean("jmadModelPackageService", JMadModelPackageService.class, () -> modelPackageService);
        ctx.refresh();
        return ctx;
    }

    public JMadModelManager getModelManager() {
        return jmadModelManager;
    }

    public void show() {
        SwingUtilities.invokeLater(jmadGui::show);
    }

    public static void main(String[] args) {
        configureLogger();
        setupLookAndFeel();

        SplashFactory.getSplashScreen();
        new JMad(JMadServiceFactory.createJMadService(), new JMadGuiPreferencesImpl(), null).show();
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
