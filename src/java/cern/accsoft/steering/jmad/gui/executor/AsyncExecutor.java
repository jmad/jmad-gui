package cern.accsoft.steering.jmad.gui.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncExecutor.class);

    private final ExecutorService executor = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("async-executor-%d").build());

    public void submitAsync(Runnable job) {
        executor.submit(job);
    }

    public void submitAsync(String name, Runnable job) {
        LOGGER.debug("Starting async job {}", name);
        executor.submit(job);
    }

}
