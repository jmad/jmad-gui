package demo;

import cern.accsoft.steering.jmad.gui.JMadGui;
import cern.accsoft.steering.jmad.gui.config.JMadGuiStandaloneConfiguration;
import cern.accsoft.steering.jmad.gui.executor.AsyncExecutor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AsyncExecutorDemo {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(JMadGuiStandaloneConfiguration.class);

        ctx.getBean(JMadGui.class).showGui();

        AsyncExecutor executor = ctx.getBean(AsyncExecutor.class);

        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        JButton buttonLongJob = new JButton("Submit 5s job");
        JButton buttonShortJob = new JButton("Submit 1s job");

        buttonLongJob.addActionListener(e -> executor.submitAsync("Long job", unchecked(() -> Thread.sleep(5000))));
        buttonShortJob.addActionListener(e -> executor.submitAsync(unchecked(() -> Thread.sleep(1000))));

        box.add(buttonLongJob);
        box.add(buttonShortJob);

        JFrame demoFrame = new JFrame();
        demoFrame.setContentPane(box);
        demoFrame.pack();
        demoFrame.setVisible(true);
    }

    private static Runnable unchecked(UncheckedRunnable run) {
        return () -> { try { run.run(); } catch (Exception e) {throw new RuntimeException(e);}};
    }

    private interface UncheckedRunnable {
        void run() throws Exception;
    }

}
