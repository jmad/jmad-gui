package cern.accsoft.steering.jmad.gui.config;

import cern.accsoft.steering.jmad.gui.executor.AsyncExecutor;
import cern.accsoft.steering.jmad.gui.panels.GuiLogPanel;
import org.apache.log4j.Appender;
import org.apache.log4j.AsyncAppender;
import org.apache.log4j.spi.RootLogger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

/**
 * Spring configuration for the jmad-gui beans. It expects the other necessary beans already in the context.
 * If you need a ready to use version use {@link JMadGuiStandaloneConfiguration}
 */
@Configuration
@ImportResource("cern/accsoft/steering/jmad/gui/config/app-ctx-jmad-gui.xml")
public class JMadGuiConfiguration {
    /* Java configuration for hiding the xml */

    @Bean("guiLogPanel")
    @Lazy
    public GuiLogPanel guiLogPanel() {
        return new GuiLogPanel();
    }

    @Bean("guiLogAppender")
    @Lazy
    public Appender guiLogAppender(GuiLogPanel guiLogPanel) {
        Appender guiLogAppender = guiLogPanel.getGuiLogAppender();
        AsyncAppender asyncAppender = new AsyncAppender();
        asyncAppender.addAppender(guiLogAppender);
        return asyncAppender;
    }

    @Bean("asyncExecutor")
    public AsyncExecutor asyncExecutor() {
        return new AsyncExecutor();
    }

    @EventListener
    public void setupLog4jLoggersAfterInit(ContextRefreshedEvent evt) {
        evt.getApplicationContext().getBeansOfType(Appender.class).values().forEach(a -> RootLogger.getRootLogger().addAppender(a));
        LoggerFactory.getLogger(JMadGuiConfiguration.class).info("Context initialized successfully");
    }

}
