package cern.accsoft.steering.jmad.gui;

import javax.swing.*;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.spi.RootLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entry point for JMad as stand alone application
 */
public class JMadMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(JMadMain.class);

    public static void main(String[] args) {
        configureLogger();
        setupLookAndFeel();
        JMad.createStandaloneJMad().getJMadGui().showGui();
    }

    private static void configureLogger() {
        BasicConfigurator.configure();
        RootLogger.getRootLogger().setLevel(Level.INFO);
        Thread.setDefaultUncaughtExceptionHandler((th, e) -> LOGGER
                .error("Unhandled {} in thread {}: {}", e.getClass().getSimpleName(), th.getName(), e.getMessage(), e));
    }

    private static void setupLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
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
