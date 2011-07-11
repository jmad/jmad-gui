/**
 * 
 */
package cern.accsoft.steering.jmad.kernel.cmd;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * @author kfuchsbe
 */
public class OptionCommand extends AbstractCommand {

    /** the name of the command */
    private static final String CMD_NAME = "option";

    private final Option option;
    private final Boolean value;

    public OptionCommand(Option option, Boolean value) {
        this.option = option;
        this.value = value;
    }

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        List<Parameter> parameters = new ArrayList<Parameter>();
        if ((option != null) && (value != null)) {
            parameters.add(new GenericParameter<String>(option.getName(), value.toString()));
        }
        return parameters;
    }

    /**
     * this enum represents the possible option commands
     * 
     * @author kfuchsbe
     */
    public enum Option {
        // default | meaning if true
        BBORBIT, // false the closed orbit is modified by beam-beam kicks
        SYMPL, // false all element matrices are symplectified in Twiss
        ECHO, // true echoes the input on the standard output file
        TRACE, // false prints the system time after each command
        VERIFY, // false issues a warning if an undefined variable is used
        WARN, // true issues warnings
        INFO, // true issues informations
        TELL, // false prints the current value of all options
        RESET, // false resets all options to their defaults
        RBARC, // true converts the RBEND straight length into the arc length
        THIN_FOC, // true if false suppresses the 1(rho**2) focusing of thin
        // dipoles
        NO_FATAL_STOP; // false Prevents madx from stopping in case of a fatal
        // error. Use at your own risk.

        private String getName() {
            return toString().toLowerCase();
        }
    }

}