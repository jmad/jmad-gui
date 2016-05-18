package cern.accmodel.jmad.core;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.result.match.MatchResult;
import cern.accsoft.steering.jmad.domain.result.match.MatchResultRequest;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResult;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResultRequest;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsSummary;
import cern.accsoft.steering.jmad.domain.result.track.DynapResult;
import cern.accsoft.steering.jmad.domain.result.track.DynapResultRequest;
import cern.accsoft.steering.jmad.domain.result.track.TrackResult;
import cern.accsoft.steering.jmad.domain.result.track.TrackResultRequest;
import cern.accsoft.steering.jmad.domain.track.TrackInitialCondition;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditions;
import cern.accsoft.steering.jmad.model.ModelMode;

/**
 * The new interface for the next JMad refactoring. This should not yet be used!
 * 
 * @deprecated not for production yet
 */
@Deprecated
public interface JMadModel {

    /**
     * runs the twiss with the given given ResultRequest and the initial conditions defined in the model.
     * 
     * @param resultRequest the requested result
     * @return the result of the twiss
     * @throws JMadModelException if the twiss calculations fail
     */
    public TfsResult twiss(TfsResultRequest resultRequest) throws JMadModelException;

    /**
     * run a twiss with the given given ResultRequest and the initial conditions defined in the model and write to file.
     * 
     * @param resultRequest the requested result
     * @param tfsFile the file to write the twiss to
     * @return The tfsresult from the twiss
     * @throws JMadModelException if the twiss calculations fail
     */
    public TfsResult twiss(TfsResultRequest resultRequest, File tfsFile) throws JMadModelException;

    /**
     * runs the twiss with the given initial conditions. NOTE: This changes nothing in the state of the model
     * 
     * @param resultRequest the requested result
     * @param twissInitialConditions the initial conditions to use
     * @return the result of the twiss
     * @throws JMadModelException if the twiss calculations fail
     */
    public TfsResult twiss(TfsResultRequest resultRequest, TwissInitialConditions twissInitialConditions)
            throws JMadModelException;

    /**
     * run a twiss with the given initial conditions. And write to file. NOTE: This changes nothing in the state of the
     * model
     * 
     * @param resultRequest the requested result
     * @param twissInitialConditions the initial conditions to use
     * @param tfsFile the file to write the twiss to
     * @return The tfs result, which is also written to the file
     * @throws JMadModelException if the twiss calculations fail
     */
    public TfsResult twiss(TfsResultRequest resultRequest, TwissInitialConditions twissInitialConditions, File tfsFile)
            throws JMadModelException;

    /**
     * Do a tracking with the given initial condition. Note : This changes nothing in the state of the model
     * 
     * @param trackResultRequest
     * @param trackInitialCondition
     * @return The result from the tracking
     * @throws JMadModelException if tracking fails
     */
    public TrackResult track(TrackResultRequest trackResultRequest, TrackInitialCondition trackInitialCondition)
            throws JMadModelException;

    /**
     * run a dynamic aperture test with the given initial condition. Note : This changes nothing to the stat of the
     * model
     * 
     * @param dynapResultRequest
     * @param trackInitialCondition
     * @return The result from the dynap command
     * @throws JMadModelException if the command fails
     */
    public DynapResult dynap(DynapResultRequest dynapResultRequest, TrackInitialCondition trackInitialCondition)
            throws JMadModelException;;

    /**
     * calls a file from madx.
     * 
     * @param file the file to call
     */
    public void call(File file);

    /**
     * sets a value as defined by its name directly in madx.
     * 
     * @param name the name of the value to set
     * @param value the value to set
     * @throws JMadModelException if setting the value to MadX fails
     */
    void setValue(String name, double value) throws JMadModelException;

    /**
     * sets a List of value - name pairs directly in madx
     * 
     * @param valueNamePairs the Map containing madx parameter names and values to assign
     * @throws JMadModelException if setting the values to MadX fails
     */
    void setValues(Map<String, Double> valueNamePairs) throws JMadModelException;

    /**
     * retrieves the value as defined by its name from madx.
     * 
     * @param valueName the name of the value
     * @return the actual value
     * @throws JMadModelException if the retrieval of the values fails
     */
    double getValue(String valueName) throws JMadModelException;

    /**
     * reads multiple values from the model given by their names to a map. The non-null map contains the mappings
     * between valueName and read value, if the read operation was successful.
     * 
     * @param valueNames the names of the values to read
     * @return a guaranteed non-null map, containing the mappings of the read values
     * @throws JMadModelException
     */
    public Map<String, Double> getValueMap(Collection<String> valueNames) throws JMadModelException;

    /**
     * Run a MadX Matching Command on the JMad Model
     * <p>
     * If no Sequence is specified in the {@link MatchResultRequest} the actual Sequence will be used for matching as
     * enabled with the last setActiveRange command.
     * <p>
     * After the Matching changes to the model are updated accordingly
     * 
     * @param resultRequest the Matching Request containing relevant parameters
     * @return the {@link MatchResult} as retrieved from MadX
     * @throws JMadModelException if the matching fails
     */
    public MatchResult match(MatchResultRequest resultRequest) throws JMadModelException;

    /**
     * Function issues a single twiss and returns only the summary of the resulting TfsResult. This is nothing else than
     * a shortcut to
     * <p>
     * <code> 
     * twiss(TfsResultRequest.createSummaryOnlyRequest()).getSummary()
     * </code>
     * 
     * @return the summary part of the actually valid twiss result.
     * @throws JMadModelException if the twiss fails.
     */
    public abstract TfsSummary calcTwissSummary() throws JMadModelException;

    /**
     * @param modelMode defines if normal madx or ptc is used
     */
    public abstract void setMode(ModelMode modelMode);

    /**
     * @return the current mode
     */
    public abstract ModelMode getMode();

}
