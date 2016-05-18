// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

package cern.accsoft.steering.jmad.model;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import cern.accsoft.steering.jmad.domain.aperture.Aperture;
import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.machine.Range;
import cern.accsoft.steering.jmad.domain.machine.RangeDefinition;
import cern.accsoft.steering.jmad.domain.misalign.MisalignmentConfiguration;
import cern.accsoft.steering.jmad.domain.optics.Optic;
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
import cern.accsoft.steering.jmad.domain.var.custom.StrengthVarSet;
import cern.accsoft.steering.jmad.kernel.JMadKernel;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinition;
import cern.accsoft.steering.jmad.modeldefs.io.ModelFileFinder;

/**
 * This is the facade of a JMad - model It provides methods to retrieve values from the model in a save form and also
 * some wrappers to low-level functions which should be used with care.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public interface JMadModel {

    //
    // the properties of the model.
    //

    /**
     * @return the name of the model containing
     *         <ul>
     *         <li>ModelDefinition Name
     *         <li>current Sequence Name
     *         </ul>
     */
    public String getName();

    /**
     * @return the current description of the Model containing
     *         <ul>
     *         <li>ModelDefinition Name
     *         <li>current Sequence Name
     *         <li>current Range Name
     *         <li>current Optic Name
     *         </ul>
     */
    public String getDescription();

    /**
     * @return the model-definition, from which the model was created.
     */
    public JMadModelDefinition getModelDefinition();

    //
    // Methods to determine and change the state of model
    //

    /**
     * sets the active range as the range corresponding to the given definition
     * 
     * @param rangeDefinition the {@link RangeDefinition} which shall be used to set the newly active range
     * @throws JMadModelException if the change of the range fails
     */
    public void setActiveRangeDefinition(RangeDefinition rangeDefinition) throws JMadModelException;

    /**
     * applies the values of the strengths given by the provided {@link OpticsDefinition} to the model. after loading of
     * the optic it reloads the active {@link RangeDefinition}. This behavior is necessary, as some of the optics
     * require re-matching of both beams combined. That can result in a different Range being active after the loading
     * of the optics.
     * 
     * @param newOpticsDefinition the optics definition to set as the active one
     * @throws JMadModelException in case setting the active range fails
     */
    public void setActiveOpticsDefinition(OpticsDefinition newOpticsDefinition) throws JMadModelException;

    /**
     * @return the actually used {@link OpticsDefinition}, or <code>null</code> if no optic is loaded.
     */
    public OpticsDefinition getActiveOpticsDefinition();

    /**
     * @return the currently active {@link RangeDefinition}
     */
    public RangeDefinition getActiveRangeDefinition();

    /**
     * @return the currently active {@link Range}
     */
    public Range getActiveRange();

    /**
     * @return the manager, which contains all the model specific knobs.
     */
    public KnobManager getKnobManager();

    //
    // initialization and cleanup stuff
    //

    /**
     * inits the values. Does not initialize the elements!
     * 
     * @throws JMadModelException if the initialization fails
     */
    public abstract void init() throws JMadModelException;

    /**
     * @return true, if the model is correctly initialized, false if not
     */
    public boolean isInitialized();

    /**
     * resets the model. Preserves the elements-state: if they are initialized, they will be reinitialized, otherwise
     * not.
     * 
     * @throws JMadModelException if the reset fails
     */
    public abstract void reset() throws JMadModelException;

    /**
     * cleanup all the stuff and free memory
     * 
     * @throws JMadModelException if the cleanup fails
     */
    public abstract void cleanup() throws JMadModelException;

    //
    // The methods for the calculation of the twiss-values and retrieving data.
    //

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
    public TfsResult twissToFile(TfsResultRequest resultRequest, File tfsFile) throws JMadModelException;

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
    public TfsResult twissToFile(TfsResultRequest resultRequest, TwissInitialConditions twissInitialConditions,
            File tfsFile) throws JMadModelException;

    /**
     * @return the actual twiss initial-conditions. These can be changed in order to start subsequent twiss-commands
     *         with the new initial conditions.
     */
    public TwissInitialConditions getTwissInitialConditions();

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
            throws JMadModelException;

    /**
     * Calculate the optics if the model became dirty.
     * 
     * @throws JMadModelException if the calculation of the optics fails
     */
    public void calcOpticsIfDirty() throws JMadModelException;

    /**
     * @return the actual optics values for all elements in the active range.
     * @throws JMadModelException if the calculation of the optics fails
     */
    public Optic getOptics() throws JMadModelException;

    //
    // Listener handling
    //

    /**
     * add a ModelListener
     * 
     * @param listener the listener to add
     */
    public void addListener(JMadModelListener listener);

    /**
     * remove a listener
     * 
     * @param listener the listener to remove
     */
    public void removeListener(JMadModelListener listener);

    /**
     * This method retrieves the {@link JMadKernel} to which the model sends its commands. Use with care!
     * 
     * @return the kernel of the model.
     */
    public JMadKernel getKernel();

    /**
     * calls a file from madx.
     * 
     * @param file the file to call
     */
    public void call(File file);

    /**
     * executes the given string directly in madx. (must also contain ";" at the end!) Use with care!
     * 
     * @param cmd the string to execute
     */
    void execute(String cmd);

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
     * reads multiple values from the model which are given by their names
     * 
     * @param valueNames the names of the values to read
     * @return the values
     * @throws JMadModelException if the retrieval of the values fails
     */
    public List<Double> getValues(List<String> valueNames) throws JMadModelException;

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
     * Execute the MadX SaveBeta Function, which saves the Optical Functions Values for a given location with the next
     * twiss command inside MadX
     * 
     * @param name the MadX internal Name for the SaveBeta
     * @param location the location in the sequence where to save the Optical Functions Values
     * @param runDummyTwiss if true a 'twiss;' command is send directly after the saveBeta
     * @throws JMadModelException
     */
    /*
     * XXX (KF) to be discussed (grundsaetzlich)!
     * 
     * 1) location must be Element, no string!
     * 
     * 2) wofuer ist der dummy-twiss?
     * 
     * 3) name sollte ueberhaupt wegfallen.
     * 
     * 4) evtl stattdessen ein objekt zurueckliefern, das die twisswerte (OpticsPoint) und evtl einen automatisch
     * generierten Key zurueckliefert.
     */
    public abstract void saveBeta(String name, String location, boolean runDummyTwiss) throws JMadModelException;

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
     * loads all the aperture-files to the model aperture
     * 
     * @throws JMadModelException if loading of the aperture fails
     */
    public void loadAperture() throws JMadModelException;

    /**
     * @return the actually loaded aperture
     */
    public abstract Aperture getAperture();

    /**
     * @return the actually valid strengths and variables.
     */
    public abstract StrengthVarSet getStrengthsAndVars();

    /**
     * @return the model file finder
     */
    public abstract ModelFileFinder getModelFileFinder();

    /**
     * @return the startup configuration which will be used when initializing and resetting the model
     */
    public abstract JMadModelStartupConfiguration getStartupConfiguration();

    /**
     * @param startupConfiguration the new startup-configuration
     */
    public abstract void setStartupConfiguration(JMadModelStartupConfiguration startupConfiguration);

    /**
     * @param modelMode defines if normal madx or ptc is used
     */
    public abstract void setMode(ModelMode modelMode);

    /**
     * @return the current mode
     */
    public abstract ModelMode getMode();

    /**
     * @param title the title to set in MadX for the following twiss-commands
     */
    public void setTitel(String title);

    /**
     * NOTE: due to incubation form of this method ONLY QUADRUPOLE elements are extracted!
     * 
     * @return a complete list of an actual machine imperfections accordingly to the requested definition (see: {@link Range}
     *         addMisalignments(), addMisalignment())
     */
    List<MisalignmentConfiguration> getMisalignments();
}
