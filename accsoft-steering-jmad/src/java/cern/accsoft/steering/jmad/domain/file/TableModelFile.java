/**
 * 
 */
package cern.accsoft.steering.jmad.domain.file;

/**
 * a file for the model-definition that will be loaded as table.
 * 
 * @author kaifox
 */
public interface TableModelFile extends ModelFile {

    /**
     * return the name of the table which will hold the data after loadting.
     * <p>
     * If this is <code>null</code> then readtyble will be used. Otherwise 'readmytable' will be used in madx.
     * 
     * @return the name of the table where to load the file into
     */
    public String getTableName();

}
