package cern.accsoft.steering.jmad.gui;

import javax.swing.*;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.spi.RootLogger;

/**
 * Entry point for JMad as stand alone application
 */
public class JMadMain {

    public static void main(String[] args) {
        configureLogger();
        setupLookAndFeel();
        JMad.createStandaloneJMad().getJMadGui().showGui();
    }

    private static void configureLogger() {
        BasicConfigurator.configure();
        RootLogger.getRootLogger().setLevel(Level.INFO);
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
