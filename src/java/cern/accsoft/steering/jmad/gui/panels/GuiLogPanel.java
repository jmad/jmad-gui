package cern.accsoft.steering.jmad.gui.panels;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import cern.accsoft.steering.jmad.gui.executor.ActiveJobsEvent;
import com.google.common.collect.ImmutableMap;
import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;
import org.springframework.context.event.EventListener;

/**
 * Panel that includes a logger appender for displaying the logs in the GUI
 */
public class GuiLogPanel extends JPanel {
    private static final String SHOW_LOGS = "Show logs";
    private static final String HIDE_LOGS = "Hide logs";
    private static final int EXPANDED_HEIGHT = 300;
    private static final int CLOSED_HEIGHT = 0;
    private static final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    private final GuiLogAppender guiLogAppender;
    private final AtomicBoolean isExpanded = new AtomicBoolean(false);
    private final JLabel activeJobsLabel;

    public GuiLogPanel() {
        setLayout(new BorderLayout());

        JTextPane fullLogText = new JTextPane();
        fullLogText.setEditable(false);
        JTextField lastEventText = new JTextField();
        lastEventText.setEditable(false);

        JScrollPane fullLogPane = new JScrollPane(fullLogText);

        JButton showLogsButton = new JButton(SHOW_LOGS);
        showLogsButton.setPreferredSize(new Dimension(120, showLogsButton.getPreferredSize().height));
        showLogsButton.addActionListener(c -> {
            if (isExpanded.get()) {
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

        activeJobsLabel = new JLabel();
        activeJobsLabel.setPreferredSize(new Dimension(120, activeJobsLabel.getPreferredSize().height));
        activeJobsLabel.setBackground(DEFAULT_BACKGROUND_COLOR);

        JPanel southBar = new JPanel();
        southBar.setLayout(new BoxLayout(southBar, BoxLayout.X_AXIS));
        southBar.setBackground(DEFAULT_BACKGROUND_COLOR);
        southBar.add(lastEventText);
        southBar.add(activeJobsLabel);
        southBar.add(showLogsButton);

        add(fullLogPane, BorderLayout.NORTH);
        add(southBar, BorderLayout.SOUTH);

        guiLogAppender = new GuiLogAppender(lastEventText, fullLogText);
    }

    public void init() {
        setActiveJobText(0);
    }

    @EventListener
    public void activeJobsCountChanged(ActiveJobsEvent e) {
        SwingUtilities.invokeLater(() -> setActiveJobText(e.getActiveJobsCount()));
    }

    private void setActiveJobText(int activeJobs) {
        activeJobsLabel.setText(formatActiveJobsCount(activeJobs));
        activeJobsLabel.setForeground(backgroundForActiveJobsCount(activeJobs));
    }

    private static Color backgroundForActiveJobsCount(int activeJobs) {
        if (activeJobs == 0) {
            return Color.GRAY;
        }
        return Color.BLUE.brighter();
    }

    private static String formatActiveJobsCount(int count) {
        if (count == 0) {
            return "No active jobs";
        }

        return String.format("Active jobs: %2d", count);
    }

    private static void setHeight(JScrollPane fullLogPane, int closedHeight) {
        fullLogPane.setPreferredSize(new Dimension(fullLogPane.getWidth(), closedHeight));
        fullLogPane.setSize(new Dimension(fullLogPane.getWidth(), closedHeight));
    }

    public Appender getGuiLogAppender() {
        return guiLogAppender;
    }

    public static class GuiLogAppender extends AppenderSkeleton {
        private static final Map<Level, Color> LEVEL_STATUS_COLOR = ImmutableMap.of( //
                Level.ERROR, Color.RED, //
                Level.INFO, Color.GREEN, //
                Level.WARN, Color.ORANGE);
        private static final Map<Level, Color> LEVEL_TEXT_COLOR = ImmutableMap.of( //
                Level.ERROR, Color.RED, //
                Level.INFO, Color.BLUE, //
                Level.WARN, Color.ORANGE.darker());

        private static final int BACKGROUND_RESET_TIMEOUT_MS = 4000;

        private final JTextField lastEventPane;
        private final JTextPane fullEventPane;
        private final Timer lastEventBackgroundResetTime;

        private final Map<Level, Style> levelStyles;
        private final Style defaultStyle;

        GuiLogAppender(JTextField lastEventPane, JTextPane fullEventPane) {
            super(true);

            this.lastEventPane = lastEventPane;
            this.fullEventPane = fullEventPane;

            ImmutableMap.Builder<Level, Style> levelStyleBuilder = ImmutableMap.builder();
            LEVEL_TEXT_COLOR.forEach((level, color) -> {
                Style style = fullEventPane.addStyle(level.toString(), null);
                StyleConstants.setForeground(style, color);
                levelStyleBuilder.put(level, style);
            });
            this.levelStyles = levelStyleBuilder.build();
            Style style = fullEventPane.addStyle("DEFAULT", null);
            StyleConstants.setForeground(style, Color.BLACK);
            this.defaultStyle = style;

            lastEventPane.setBorder(BorderFactory.createEmptyBorder());

            setThreshold(Level.INFO);
            setLayout(new PatternLayout("%d{ABSOLUTE} %-5p [%t]: %m %n"));

            lastEventBackgroundResetTime = new Timer(BACKGROUND_RESET_TIMEOUT_MS,
                    a -> lastEventPane.setBackground(DEFAULT_BACKGROUND_COLOR));
        }

        @Override
        protected void append(LoggingEvent event) {
            String msg = layout.format(event);
            String exceptionInfo = Optional.ofNullable(event.getThrowableStrRep()) //
                    .map(lines -> String.join("\n", lines)) //
                    .orElse("");
            Level level = event.getLevel();
            /* It's in theory possible that the logs messages are printed not in order. */
            /* It's ok since they are just informative and the overhead would be a bit too much */
            SwingUtilities.invokeLater(() -> {
                lastEventBackgroundResetTime.restart();
                lastEventPane.setText(msg);
                Document document = fullEventPane.getDocument();
                try {
                    document.insertString(document.getLength(), msg + exceptionInfo,
                            levelStyles.getOrDefault(level, defaultStyle));
                } catch (BadLocationException e) {
                    e.printStackTrace(); /* on purpose, do not use logger to prevent infinite loops! */
                }
                lastEventPane.setBackground(LEVEL_STATUS_COLOR.getOrDefault(level, DEFAULT_BACKGROUND_COLOR));
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
