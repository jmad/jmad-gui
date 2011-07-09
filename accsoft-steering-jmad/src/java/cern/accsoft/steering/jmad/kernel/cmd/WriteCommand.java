package cern.accsoft.steering.jmad.kernel.cmd;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * Command write,table=tableName,file=fileName;
 * 
 * @author xbuffat
 *
 */

public class WriteCommand extends AbstractCommand {

	private static final String CMD_NAME = "write";
	
	private String tableName;
	private String file;
	
	public WriteCommand(String tableName) {
		this(tableName,null);
	}
	
	public WriteCommand(String tableName, String file) {
		this.tableName = tableName;
		this.file = file;
	}
	
	@Override
	public String getName() {
		return WriteCommand.CMD_NAME;
	}
	
	@Override
	public List<Parameter> getParameters() {
		List<Parameter> parameters = new ArrayList<Parameter>();
		
		parameters.add(new GenericParameter<String>("table",this.tableName,true));
		if(file!=null)
		{
			parameters.add(new GenericParameter<String>("file",this.file,true));
		}
		return parameters;
	}

}
