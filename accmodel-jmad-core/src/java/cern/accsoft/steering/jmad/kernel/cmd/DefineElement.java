package cern.accsoft.steering.jmad.kernel.cmd;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.kernel.AbstractJMadExecutable;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public class DefineElement extends AbstractJMadExecutable implements Command {

	private Element element;
	
	public DefineElement(Element element) {
		this.element = element;
	}
	
	@Override
	public String compose() {
		String retVal = this.getName()+": "+this.element.getMadxElementType().getMadxName();
		for(Parameter parameter : this.getParameters())
		{
			retVal+=", "+parameter.compose();
		}
		retVal+=";";
		return retVal;
	}

	@Override
	public String getName() {
		return this.element.getName();
	}

	@Override
	public List<Parameter> getParameters() {
		List<Parameter> parameters = new ArrayList<Parameter>();
		for(String attribute : this.element.getAttributeNames())
		{
			if(this.element.getAttribute(attribute) != null)
			{
				parameters.add(new GenericParameter<Double>(attribute,this.element.getAttribute(attribute)));
			}
		}
		return parameters;
	}

}
