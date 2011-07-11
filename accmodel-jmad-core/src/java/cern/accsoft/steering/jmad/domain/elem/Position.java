package cern.accsoft.steering.jmad.domain.elem;

public class Position {

	private String element;
	private double position;
	private boolean relative;
	
	public Position(double position) {
		this.position = position;
		this.relative = false;
	}
	
	public Position(String element,double position) {
		this.setElement(element);
		this.setPosition(position);
		this.setRelative(true);
	}

	public void setElement(String element) {
		this.element = element;
	}

	public String getElement() {
		return element;
	}

	public void setPosition(Double position) {
		this.position = position;
	}

	public double getValue() {
		return position;
	}

	public void setRelative(boolean relative) {
		this.relative = relative;
	}

	public boolean isRelative() {
		return relative;
	}
	
}