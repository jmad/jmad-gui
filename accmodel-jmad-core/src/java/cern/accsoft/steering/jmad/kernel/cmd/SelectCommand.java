package cern.accsoft.steering.jmad.kernel.cmd;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public class SelectCommand extends AbstractCommand {

    private static final String CMD_NAME = "select";

    private String flag = null;
    private String range = null;
    private String elementClass = null;
    private String pattern = null;
    private String column = null;
    private Boolean full = null;
    private Boolean clear = null;

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();

        parameters.add(new GenericParameter<String>("flag", flag));
        parameters.add(new GenericParameter<String>("range", range));
        parameters.add(new GenericParameter<String>("class", elementClass));
        parameters.add(new GenericParameter<String>("pattern", pattern, true));
        parameters.add(new GenericParameter<String>("column", column));
        parameters.add(new GenericParameter<Boolean>("full", full));
        parameters.add(new GenericParameter<Boolean>("clear", clear));

        return parameters;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getElementClass() {
        return elementClass;
    }

    public void setElementClass(String elementClass) {
        this.elementClass = elementClass;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Boolean getFull() {
        return full;
    }

    public void setFull(Boolean full) {
        this.full = full;
    }

    public Boolean getClear() {
        return clear;
    }

    public void setClear(Boolean clear) {
        this.clear = clear;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

}
