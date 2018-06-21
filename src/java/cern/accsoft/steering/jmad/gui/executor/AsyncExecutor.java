package cern.accsoft.steering.jmad.gui.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Wrapper around the execution of tasks asynchronously.
 */
public class AsyncExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncExecutor.class);
    private static final int JOBS_WARN_LEVEL = 20;

    private final ExecutorService executor = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("async-executor-%d").build());
    private final AtomicInteger jobsCount = new AtomicInteger(0);
    private final ApplicationEventPublisher eventPublisher;

    public AsyncExecutor(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void submitAsync(Runnable job) {
        submitAsync("unnamed-job-" + Instant.now(), job);
    }

    public void submitAsync(String name, Runnable job) {
        if(jobsCount.get() >= JOBS_WARN_LEVEL) {
            LOGGER.warn("Active jobs count >= {}. No limit is enforced, but performance may suffer", JOBS_WARN_LEVEL);
        }

        LOGGER.debug("Starting async job {}", name);
        publishJobsCount(jobsCount.incrementAndGet());
        CompletableFuture<Void> future = CompletableFuture.runAsync(job, executor);
        future.whenComplete((res, exc) -> publishJobsCount(jobsCount.decrementAndGet()));
    }

    private void publishJobsCount(int count) {
        LOGGER.info("Active jobs count {}", count);
        eventPublisher.publishEvent(new ActiveJobsEvent(count));
    }

}
