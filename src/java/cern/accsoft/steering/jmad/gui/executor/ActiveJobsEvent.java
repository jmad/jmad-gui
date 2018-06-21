package cern.accsoft.steering.jmad.gui.executor;

import java.util.Objects;

/**
 * Event that contains information about the jobs that are active on a {@link AsyncExecutor}.
 */
public class ActiveJobsEvent {
    private final int activeJobsCount;

    public ActiveJobsEvent(int activeJobsCount) {
        this.activeJobsCount = activeJobsCount;
    }

    public int getActiveJobsCount() {
        return activeJobsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActiveJobsEvent that = (ActiveJobsEvent) o;
        return activeJobsCount == that.activeJobsCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(activeJobsCount);
    }

    @Override
    public String toString() {
        return "ActiveJobsEvent{" +
                "activeJobsCount=" + activeJobsCount +
                '}';
    }
}
