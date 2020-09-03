package cern.accsoft.steering.jmad.gui.executor;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

/**
 * Wrapper around the execution of tasks asynchronously.
 */
public class AsyncExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncExecutor.class);
    private static final int JOBS_WARN_LEVEL = 20;

    private final ExecutorService executor = Executors
            .newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("async-executor-%d").build());
    private final AtomicInteger jobsCount = new AtomicInteger(0);
    private final ApplicationEventPublisher eventPublisher;

    public AsyncExecutor(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void submitAsync(Runnable job) {
        submitAsync("unnamed-job-" + Instant.now(), job);
    }

    public void submitAsync(String name, Runnable job) {
        if (jobsCount.get() >= JOBS_WARN_LEVEL) {
            LOGGER.warn("Active jobs count >= {}. No limit is enforced, but performance may suffer", JOBS_WARN_LEVEL);
        }

        LOGGER.info("Starting job '{}' ...", name);
        publishJobsCount(jobsCount.incrementAndGet());
        CompletableFuture<Void> future = CompletableFuture.runAsync(job, executor);
        future.whenComplete((res, exc) -> {
            publishJobsCount(jobsCount.decrementAndGet());
            if (exc == null) {
                LOGGER.info("Job '{}' completed.", name);
            } else {
                LOGGER.error("Error in job '{}': {}", name, exc.getMessage(), exc);
            }
        });
    }

    private void publishJobsCount(int count) {
        eventPublisher.publishEvent(new ActiveJobsEvent(count));
    }

}
