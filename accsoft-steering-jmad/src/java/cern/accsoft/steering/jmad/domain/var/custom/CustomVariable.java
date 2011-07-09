/**
 * 
 */
package cern.accsoft.steering.jmad.domain.var.custom;

import cern.accsoft.steering.jmad.domain.var.TwissVariable;

/**
 * This is the interface of a variable. It represents a madx definition of a key-value pair. A Variable may be e.g.
 * parsed from a strength file and then plotted in a gui. For the moment the expression of a variable can not be
 * explicitely set and the name can not be changed.
 * 
 * @author kfuchsbe
 */
public interface CustomVariable extends TwissVariable {

    /**
     * retrieve the expression of the variable. (The string which is used to calculate the variable within madx)
     * 
     * @return the expression as string
     */
    public String getExpression();

    /**
     * The comment for this variable. It is retrieved from the comment string in the same line as the variable
     * definition. (If there is one)
     * 
     * @return a comment concerning this variable
     */
    public String getComment();

    /**
     * @return true if the variable is defined by ":=", false if it is defined with "=".
     */
    public boolean isLateAssigned();

    /**
     * @return a unified key
     */
    public String getKey();
}
