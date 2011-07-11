/**
 * 
 */
package cern.accsoft.steering.jmad.kernel.cmd.table;

import java.io.File;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * represents the <a href='http://mad.web.cern.ch/mad/control/general.html#readmytable'>'readmytable'</a> command of madx.
 * <p>
 * This command provides the possibility to load into a table with a specific name.
 * 
 * @author kaifox
 */
public class ReadMyTableCommand extends AbstractTableCommand {
    /** the name of the command */
    private static final String CMD_NAME = "readmytable";

    /** the name of the table to read the table into */
    private String tableName;

    public ReadMyTableCommand(File file, String tableName) {
        super(file);
        this.tableName = tableName;
    }

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        List<Parameter> parameters = super.createParameters();
        parameters.add(new GenericParameter<String>("table", tableName));
        return parameters;
    }
}
