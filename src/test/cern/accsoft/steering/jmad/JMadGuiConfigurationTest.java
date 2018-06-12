package cern.accsoft.steering.jmad;


import cern.accsoft.steering.jmad.gui.JMadGui;
import cern.accsoft.steering.jmad.gui.config.JMadGuiStandaloneConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Regression test that makes sure the Spring configuration is correctly loaded
 */
public class JMadGuiConfigurationTest {

    @Test(timeout = 2 * 60 * 1000)
    public void testStandAloneConfigurationCanInitialize() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(JMadGuiStandaloneConfiguration.class);
        Assertions.assertThat(ctx.getBean(JMadGui.class)).isNotNull();
    }

}
