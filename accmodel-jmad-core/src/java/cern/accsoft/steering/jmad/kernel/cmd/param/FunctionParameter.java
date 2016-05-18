/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package cern.accsoft.steering.jmad.kernel.cmd.param;

/**
 * Gives the possibility to assign a function (by function name) with given function value like tgauss(1.2) or ranf(); <br>
 * <br>
 * NOTE: function value of null results into no values such that: tgauss(null) -> tgauss();
 * 
 * @author agorzaws
 */
public class FunctionParameter extends AbstractParameter {

    private String name;
    private String functionName;
    private Double value;
    private Double functionValue;

    /**
     * @param name name of the parameter
     * @param functionName name of the function
     * @param value value of the parameter
     * @param functionValue value of the function (<b>null</b> if none)
     */
    public FunctionParameter(String name, String functionName, Double value, Double functionValue) {
        super();
        this.name = name;
        this.functionName = functionName;
        this.functionValue = functionValue;
        this.value = value;
    }

    @Override
    public String compose() {
        if (value != null) {
            String functionCall = functionName + "(" + (functionValue == null ? "" : functionValue.doubleValue()) + ")";
            return name + ":=" + functionCall + "*" + value;
        }
        return "";
    }

    @Override
    public boolean isSet() {
        return (value != null);
    }

}
