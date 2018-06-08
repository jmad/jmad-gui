package cern.accsoft.steering.jmad.gui.panels;

import com.google.common.collect.ImmutableMap;
import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Panel that includes a logger appender for displaying the logs in the GUI
 */
public class GuiLogPanel extends JPanel {
    private static final String SHOW_LOGS = "Show logs";
    private static final String HIDE_LOGS = "Hide logs";
    private static final int EXPANDED_HEIGHT = 300;
    private static final int CLOSED_HEIGHT = 0;
    private final GuiLogAppender guiLogAppender;
    private final AtomicBoolean isExpanded = new AtomicBoolean(false);

    public GuiLogPanel() {
        setLayout(new BorderLayout());

        JTextPane fullLogText = new JTextPane();
        fullLogText.setEditable(false);
        JTextField lastEventText = new JTextField();
        lastEventText.setEditable(false);

        JScrollPane fullLogPane = new JScrollPane(fullLogText);

        JButton showLogsButton = new JButton(SHOW_LOGS);
        showLogsButton.addActionListener(c -> {
            if(isExpanded.get()) {
                setHeight(fullLogPane, CLOSED_HEIGHT);
                showLogsButton.setText(SHOW_LOGS);
            } else {
                setHeight(fullLogPane, EXPANDED_HEIGHT);
                showLogsButton.setText(HIDE_LOGS);
            }
            isExpanded.set(!isExpanded.get());
            JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, GuiLogPanel.this);
            frame.pack();
        });

        setHeight(fullLogPane, CLOSED_HEIGHT);

        add(fullLogPane, BorderLayout.NORTH);
        add(lastEventText, BorderLayout.CENTER);
        add(showLogsButton, BorderLayout.EAST);

        guiLogAppender = new GuiLogAppender(lastEventText, fullLogText);
    }

    private static void setHeight(JScrollPane fullLogPane, int closedHeight) {
        fullLogPane.setPreferredSize(new Dimension(fullLogPane.getWidth(), closedHeight));
        fullLogPane.setSize(new Dimension(fullLogPane.getWidth(), closedHeight));
    }

    public Appender getGuiLogAppender() {
        return guiLogAppender;
    }

    public static class GuiLogAppender extends AppenderSkeleton {
        private static final Map<Level, Color> LEVEL_COLOR = ImmutableMap.of(Level.ERROR, Color.red, Level.INFO, Color.green, Level.WARN, Color.orange);
        private static final Color DEFAULT_BACKGROUND = Color.white;
        private static final int BACKGROUND_RESET_TIMEOUT_MS = 4000;

        private final JTextField lastEventPane;
        private final JTextPane fullEventPane;
        private final Timer lastEventBackgroundResetTime;

        GuiLogAppender(JTextField lastEventPane, JTextPane fullEventPane) {
            super(true);

            this.lastEventPane = lastEventPane;
            this.fullEventPane = fullEventPane;

            lastEventPane.setBorder(BorderFactory.createEmptyBorder());

            setThreshold(Level.DEBUG);
            setLayout(new PatternLayout("%d{ABSOLUTE} %-5p [%t]: %m %n"));

            lastEventBackgroundResetTime = new Timer(BACKGROUND_RESET_TIMEOUT_MS, a -> lastEventPane.setBackground(DEFAULT_BACKGROUND));
        }

        @Override
        protected void append(LoggingEvent event) {
            String msg = layout.format(event);
            Level level = event.getLevel();
            /* It's in theory possible that the logs messages are printed not in order. */
            /* It's ok since they are just informative and the overhead would be a bit too much */
            SwingUtilities.invokeLater(() -> {
                lastEventBackgroundResetTime.restart();
                lastEventPane.setText(msg);
                fullEventPane.setText(fullEventPane.getText() + msg);
                lastEventPane.setBackground(LEVEL_COLOR.getOrDefault(level, DEFAULT_BACKGROUND));
            });
        }

        @Override
        public void close() {

        }

        @Override
        public boolean requiresLayout() {
            return false;
        }

    }

}
