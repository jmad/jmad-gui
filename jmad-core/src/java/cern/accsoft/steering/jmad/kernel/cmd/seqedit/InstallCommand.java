package cern.accsoft.steering.jmad.kernel.cmd.seqedit;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.domain.elem.Position;
import cern.accsoft.steering.jmad.kernel.cmd.AbstractCommand;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public class InstallCommand extends AbstractCommand {

	private static final String CMD_NAME = "install";
	
	private String elementName;
	private String elementClass;
	private Position elementPosition;
	
	public InstallCommand(Element element) {
		this.elementName = element.getName();
		this.elementClass = element.getMadxElementType().getMadxName();
		this.elementPosition = element.getPosition();
	}
	
	@Override
	public String getName() {
		return InstallCommand.CMD_NAME;
	}

	@Override
	public List<Parameter> getParameters() {
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new GenericParameter<String>("element",this.elementName));
		parameters.add(new GenericParameter<String>("class",this.elementClass));
		parameters.add(new GenericParameter<Double>("at",this.elementPosition.getValue()));
		if(this.elementPosition.isRelative())
		{
			parameters.add(new GenericParameter<String>("from",this.elementPosition.getElement()));
		}
		
		return parameters;
	}
	
}
