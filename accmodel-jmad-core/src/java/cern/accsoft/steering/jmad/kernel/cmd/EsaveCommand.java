/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package cern.accsoft.steering.jmad.kernel.cmd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * A command that allows to save all machine imperfections into separate table such that after model treatment they can
 * be accessed and an absolute value of imperfection is returned.
 * 
 * @author agorzaws
 */
public class EsaveCommand extends AbstractCommand {

    private final File outputFile;

    private static final String CMD_NAME = "esave";

    public EsaveCommand(File file) {
        this.outputFile = file;
    }

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new GenericParameter<String>("file", outputFile.getAbsolutePath()));
        return parameters;
    }

}
