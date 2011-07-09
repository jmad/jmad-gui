package cern.accsoft.steering.jmad.kernel.cmd;

import cern.accsoft.steering.jmad.kernel.AbstractJMadExecutable;

public class FreeText extends AbstractJMadExecutable {

    private String text = null;

    @Override
    public String compose() {
        return text;
    }

    @Override
    public String toString() {
        return compose();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
