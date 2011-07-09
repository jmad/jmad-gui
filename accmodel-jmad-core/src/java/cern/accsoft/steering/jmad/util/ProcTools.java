package cern.accsoft.steering.jmad.util;

public final class ProcTools {

    private ProcTools() {
        /* only static methods */
    }

    /**
     * @param process the process to test.
     * @return true, if the thread was started before, false otherwise
     */
    public static boolean isRunning(Process process) {
        if (process == null) {
            return false;
        }
        /*
         * XXX some dirty trick: Tries to get the exit value. If this is possible, then the thread already terminated.
         * If its not possible, then the process is still running. TODO better solution.
         */
        try {
            process.exitValue();
            return false;
        } catch (IllegalThreadStateException e) {
            return true;
        }
    }
}
