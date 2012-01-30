package cern.accsoft.steering.jmad.domain.elem;

public class RelativePosition extends Position {

    private String element;
	
	public RelativePosition(double position) {
		super(position);
	}
	
	public RelativePosition(String element, double position) {
		super(position);
		this.setElement(element);
	}
	
    public void setElement(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }

}
