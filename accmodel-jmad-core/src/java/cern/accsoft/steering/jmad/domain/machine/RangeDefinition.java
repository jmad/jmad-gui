package cern.accsoft.steering.jmad.domain.machine;

import java.util.List;

import cern.accsoft.steering.jmad.domain.file.ModelFile;
import cern.accsoft.steering.jmad.domain.file.ModelFileDependant;
import cern.accsoft.steering.jmad.domain.machine.filter.NameFilter;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditionsImpl;

public interface RangeDefinition extends ModelFileDependant {

	/**
	 * @return the name
	 */
	public abstract String getName();

	/**
	 * @return the range for madx
	 */
	public MadxRange getMadxRange();

	/**
	 * If this is non-null, then the sequence is rotated to start at the element
	 * of the given name before use.
	 * 
	 * @return the name of an element or <code>null</code>.
	 */
	public String getStartElementName();

	/**
	 * @return the twiss
	 */
	public abstract TwissInitialConditionsImpl getTwiss();

	/**
	 * @return the monitorInvertFilters
	 */
	public abstract List<NameFilter> getMonitorInvertFilters();

	/**
	 * @return the correctorInvertFilters
	 */
	public abstract List<NameFilter> getCorrectorInvertFilters();

	/**
	 * @return the postUseFiles
	 */
	public abstract List<ModelFile> getPostUseFiles();

	/**
	 * @return the aperture-definition of this range
	 */
	public abstract ApertureDefinition getApertureDefinition();

	/**
	 * @return the sequenceDefinition this range belongs to
	 */
	public abstract SequenceDefinition getSequenceDefinition();

}