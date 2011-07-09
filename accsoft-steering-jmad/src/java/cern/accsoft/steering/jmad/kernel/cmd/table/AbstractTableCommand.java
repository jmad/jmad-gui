package cern.accsoft.steering.jmad.kernel.cmd.table;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.AbstractCommand;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public abstract class AbstractTableCommand extends AbstractCommand {

    /** The file from which to load the table */
    private File file;

    public AbstractTableCommand(File file) {
        super();
        this.file = file;
    }

    protected List<Parameter> createParameters() {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new GenericParameter<String>("file", this.file.getAbsolutePath(), true));
        return parameters;
    }

}