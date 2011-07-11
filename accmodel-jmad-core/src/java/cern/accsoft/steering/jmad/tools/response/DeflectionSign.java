/**
 * 
 */
package cern.accsoft.steering.jmad.tools.response;

public enum DeflectionSign {
    PLUS("plus"), MINUS("minus");

    private final String tag;

    private DeflectionSign(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

}