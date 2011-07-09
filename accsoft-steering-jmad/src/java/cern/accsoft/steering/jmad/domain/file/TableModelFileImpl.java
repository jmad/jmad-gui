/**
 * 
 */
package cern.accsoft.steering.jmad.domain.file;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * A model file which will be loaded as table
 * 
 * @author kaifox
 */
@XStreamAlias("table-file")
public class TableModelFileImpl extends AbstractModelFile implements TableModelFile {

    /** The name of the table to load the file into */
    @XStreamAlias("table-name")
    @XStreamAsAttribute
    private String tableName = null;

    /**
     * default constructor. Necessary for XStream
     */
    public TableModelFileImpl() {
        this(null, null);
    }

    public TableModelFileImpl(String path, ModelFileLocation location) {
        super(path, location);
    }

    public TableModelFileImpl(String path, ModelFileLocation location, String tableName) {
        this(path, location);
        this.tableName = tableName;
    }

    @Override
    public String getTableName() {
        return this.tableName;
    }

}
