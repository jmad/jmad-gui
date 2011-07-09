package cern.accsoft.steering.jmad.kernel.cmd.param;

public abstract class AbstractParameter implements Parameter {
    /*
     * not much special at the moment
     */
    @Override
    public String toString() {
        return compose();
    }
}
