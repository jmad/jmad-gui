package cern.accsoft.steering.jmad.gui.config;

import cern.accsoft.steering.jmad.conf.JMadServiceConfiguration;
import cern.accsoft.steering.jmad.gui.JMad;
import cern.accsoft.steering.jmad.gui.JMadGui;
import cern.accsoft.steering.jmad.gui.manage.JMadGuiPreferences;
import cern.accsoft.steering.jmad.gui.manage.impl.JMadGuiPreferencesImpl;
import cern.accsoft.steering.jmad.gui.mark.MarkedElementsManager;
import cern.accsoft.steering.jmad.gui.mark.MarkedElementsManagerImpl;
import cern.accsoft.steering.jmad.gui.panels.JMadPanelFactory;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.service.JMadService;
import org.jmad.modelpack.gui.conf.JMadModelSelectionDialogConfiguration;
import org.jmad.modelpack.gui.conf.JMadModelSelectionDialogFactory;
import org.jmad.modelpack.service.conf.JMadModelPackageServiceConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring configuration for a standalone JMad GUI. It imports all the necessary configurations. If you just need to
 * configure the jmad-gui project, you can use {@link JMadGuiConfiguration}
 */
@Configuration
@Import({JMadServiceConfiguration.class, JMadModelPackageServiceConfiguration.class, JMadModelSelectionDialogConfiguration.class, JMadGuiConfiguration.class })
public class JMadGuiStandaloneConfiguration {

    @Bean("modelManager")
    public JMadModelManager modelManager(JMadService jMadService) {
        return jMadService.getModelManager();
    }

    @Bean("markedElementsManager")
    public MarkedElementsManager markedElementsManager() {
        return new MarkedElementsManagerImpl();
    }

}
