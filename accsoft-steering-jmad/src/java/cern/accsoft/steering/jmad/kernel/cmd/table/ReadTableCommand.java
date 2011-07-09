/**
 * 
 */
package cern.accsoft.steering.jmad.kernel.cmd.table;

import java.io.File;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * represents the readtable command of madx
 * 
 * @author kaifox
 */
public class ReadTableCommand extends AbstractTableCommand {

    /** the name of the command */
    private static final String CMD_NAME = "readtable";

    /**
     * the default constructor with the file
     * 
     * @param file the file from which to load the table
     */
    public ReadTableCommand(File file) {
        super(file);
    }

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        return super.createParameters();
    }

}
