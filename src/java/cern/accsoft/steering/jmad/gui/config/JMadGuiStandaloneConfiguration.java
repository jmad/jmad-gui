package cern.accsoft.steering.jmad.gui.config;

import cern.accsoft.steering.jmad.conf.JMadServiceConfiguration;
import org.jmad.modelpack.gui.conf.JMadModelSelectionDialogConfiguration;
import org.jmad.modelpack.service.conf.JMadModelPackageServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring configuration for a standalone JMad GUI. It imports all the necessary configurations. If you just need to
 * configure the jmad-gui project, you can use {@link JMadGuiConfiguration}
 */
@Configuration
@Import({JMadServiceConfiguration.class, JMadModelPackageServiceConfiguration.class, JMadModelSelectionDialogConfiguration.class, JMadGuiConfiguration.class })
public class JMadGuiStandaloneConfiguration {
    /* meta-configuration */
}
