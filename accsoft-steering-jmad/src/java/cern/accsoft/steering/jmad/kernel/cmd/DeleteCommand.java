/*
 * $Id $
 * 
 * $Date$ $Revision$ $Author$
 * 
 * Copyright CERN ${year}, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.kernel.cmd;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public class DeleteCommand extends AbstractCommand {

    /** The madx-name of the command */
    private final static String COMMAND_NAME = "delete";

    /** The table name to delete */
    private String table;

    /** The sequence name to delete */
    private String sequence;

    /**
     * a constructor to simplifz setting the table name
     * 
     * @param tableName the name of the table to delete
     */
    public DeleteCommand(String tableName) {
        this.table = tableName;
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new GenericParameter<String>("sequence", this.getSequence()));
        parameters.add(new GenericParameter<String>("table", this.table));
        return parameters;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getTable() {
        return table;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getSequence() {
        return sequence;
    }
}
