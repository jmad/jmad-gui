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

import cern.accsoft.steering.jmad.gui.config.JMadGuiStandaloneConfiguration;
import cern.accsoft.steering.jmad.gui.mark.MarkedElementsManager;
import cern.accsoft.steering.jmad.gui.panels.JMadPanelFactory;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.service.JMadService;
import cern.accsoft.steering.jmad.util.JMadPreferences;
import org.jmad.modelpack.service.JMadModelPackageRepositoryManager;
import org.jmad.modelpack.service.JMadModelPackageService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * JMad class that exposes the services that can be used. Each instance of this class will create the full JMad environment (e.g. a different {@link JMadService}).
 */
public class JMad {

    private final ApplicationContext ctx;

    private JMad() {
        this.ctx = new AnnotationConfigApplicationContext(JMadGuiStandaloneConfiguration.class);
    }

    public static JMad createStandaloneJMad() {
        return new JMad();
    }

    public JMadModelPackageRepositoryManager getJMadModelPackageRepositoryManager() {
        return ctx.getBean(JMadModelPackageRepositoryManager.class);
    }

    public JMadModelPackageService getJMadModelPackageService() {
        return ctx.getBean(JMadModelPackageService.class);
    }

    public JMadService getJMadService() {
        return ctx.getBean(JMadService.class);
    }

    public JMadModelManager getModelManager() {
        return getJMadService().getModelManager();
    }

    public MarkedElementsManager getMarkedElementsManager() {
        return ctx.getBean(MarkedElementsManager.class);
    }

    public JMadPreferences getJMadPreferences() {
        return getJMadService().getPreferences();
    }

    public JMadPanelFactory getJMadPanelFactory() {
        return ctx.getBean(JMadPanelFactory.class);
    }

    public JMadGui getJMadGui() {
        return ctx.getBean(JMadGui.class);
    }

}
