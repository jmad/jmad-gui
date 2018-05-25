package cern.accsoft.steering.jmad.gui.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Spring configuration for the jmad-gui beans. It expects the other necessary beans already in the context.
 * If you need a ready to use version use {@link JMadGuiStandaloneConfiguration}
 */
@Configuration
@ImportResource("cern/accsoft/steering/jmad/gui/config/app-ctx-jmad-gui.xml")
public class JMadGuiConfiguration {
    /* Java configuration for hiding the xml */
}
