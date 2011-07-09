/**
 * 
 */
package cern.accsoft.steering.jmad.domain.result.match.output;

import cern.accsoft.steering.jmad.domain.elem.Element;

/**
 * @author muellerg
 */
public class MatchConstraintResultLocal extends AbstractMatchConstraintResult {

    private final String elemName;

    public MatchConstraintResultLocal(String constraint, double finalValue) {
        super(constraint, finalValue);
        this.elemName = constraint.split(Element.ATTR_SEPARATOR)[0];
    }

    public String getElementName() {
        return this.elemName;
    }

    @Override
    public boolean isGlobal() {
        return false;
    }
}
