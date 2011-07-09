/**
 * 
 */
package cern.accsoft.steering.jmad.domain.var;

/**
 * This interface represents a variable which can result in a column of a twiss table.
 * 
 * @author kfuchsbe
 */
public interface TwissVariable extends MadxVariable {

    /**
     * @return true, if this variable shall be plotted as aperture (second line with minus-sign)
     */
    public boolean isApertureVariable();

}
