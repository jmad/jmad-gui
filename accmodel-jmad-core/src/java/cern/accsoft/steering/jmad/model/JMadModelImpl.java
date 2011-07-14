// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 * 
 * JMad is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JMad is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JMad.  If not, see <http://www.gnu.org/licenses/>.
 * 
 ******************************************************************************/
// @formatter:on
package cern.accsoft.steering.jmad.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.JMadException;
import cern.accsoft.steering.jmad.domain.aperture.Aperture;
import cern.accsoft.steering.jmad.domain.beam.Beam;
import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.domain.elem.ElementAttributeReader;
import cern.accsoft.steering.jmad.domain.elem.ElementListener;
import cern.accsoft.steering.jmad.domain.elem.MadxElementType;
import cern.accsoft.steering.jmad.domain.elem.impl.AbstractElement;
import cern.accsoft.steering.jmad.domain.elem.impl.ElementFactory;
import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.file.CallableModelFile;
import cern.accsoft.steering.jmad.domain.file.ModelFile;
import cern.accsoft.steering.jmad.domain.file.TableModelFile;
import cern.accsoft.steering.jmad.domain.file.CallableModelFile.ParseType;
import cern.accsoft.steering.jmad.domain.knob.AbstractKnob;
import cern.accsoft.steering.jmad.domain.knob.Knob;
import cern.accsoft.steering.jmad.domain.knob.KnobListener;
import cern.accsoft.steering.jmad.domain.knob.attribute.ElementAttribute;
import cern.accsoft.steering.jmad.domain.knob.strength.SimpleStrength;
import cern.accsoft.steering.jmad.domain.knob.strength.Strength;
import cern.accsoft.steering.jmad.domain.machine.ApertureDefinition;
import cern.accsoft.steering.jmad.domain.machine.Range;
import cern.accsoft.steering.jmad.domain.machine.RangeDefinition;
import cern.accsoft.steering.jmad.domain.machine.RangeListener;
import cern.accsoft.steering.jmad.domain.machine.SequenceDefinition;
import cern.accsoft.steering.jmad.domain.misalign.MisalignmentConfiguration;
import cern.accsoft.steering.jmad.domain.misalign.MisalignmentConfigurationListener;
import cern.accsoft.steering.jmad.domain.optics.Optic;
import cern.accsoft.steering.jmad.domain.optics.OpticImpl;
import cern.accsoft.steering.jmad.domain.result.Result;
import cern.accsoft.steering.jmad.domain.result.ResultType;
import cern.accsoft.steering.jmad.domain.result.StrengthResult;
import cern.accsoft.steering.jmad.domain.result.match.MatchResult;
import cern.accsoft.steering.jmad.domain.result.match.MatchResultImpl;
import cern.accsoft.steering.jmad.domain.result.match.MatchResultRequest;
import cern.accsoft.steering.jmad.domain.result.match.input.MadxVaryParameter;
import cern.accsoft.steering.jmad.domain.result.match.input.MatchConstraint;
import cern.accsoft.steering.jmad.domain.result.match.input.MatchConstraintLocal;
import cern.accsoft.steering.jmad.domain.result.match.output.MadxVaryResult;
import cern.accsoft.steering.jmad.domain.result.match.output.MatchConstraintResultGlobal;
import cern.accsoft.steering.jmad.domain.result.match.output.MatchConstraintResultLocal;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResult;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResultRequest;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResultRequestImpl;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsSummary;
import cern.accsoft.steering.jmad.domain.result.track.DynapResult;
import cern.accsoft.steering.jmad.domain.result.track.DynapResultRequest;
import cern.accsoft.steering.jmad.domain.result.track.TrackResult;
import cern.accsoft.steering.jmad.domain.result.track.TrackResultRequest;
import cern.accsoft.steering.jmad.domain.track.TrackInitialCondition;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditions;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditionsImpl;
import cern.accsoft.steering.jmad.domain.twiss.TwissListener;
import cern.accsoft.steering.jmad.domain.var.custom.StrengthVarSet;
import cern.accsoft.steering.jmad.domain.var.custom.StrengthVarSetImpl;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;
import cern.accsoft.steering.jmad.io.ApertureReader;
import cern.accsoft.steering.jmad.io.ApertureReaderImpl;
import cern.accsoft.steering.jmad.io.StrengthFileParser;
import cern.accsoft.steering.jmad.io.StrengthFileParserException;
import cern.accsoft.steering.jmad.kernel.JMadKernel;
import cern.accsoft.steering.jmad.kernel.JMadKernelImpl;
import cern.accsoft.steering.jmad.kernel.cmd.CallCommand;
import cern.accsoft.steering.jmad.kernel.cmd.Command;
import cern.accsoft.steering.jmad.kernel.cmd.EOptionCommand;
import cern.accsoft.steering.jmad.kernel.cmd.FreeText;
import cern.accsoft.steering.jmad.kernel.cmd.SaveBetaCommand;
import cern.accsoft.steering.jmad.kernel.cmd.SetEqual;
import cern.accsoft.steering.jmad.kernel.cmd.SetListEqual;
import cern.accsoft.steering.jmad.kernel.cmd.UseCommand;
import cern.accsoft.steering.jmad.kernel.cmd.ptc.PtcEndCommand;
import cern.accsoft.steering.jmad.kernel.cmd.table.ReadMyTableCommand;
import cern.accsoft.steering.jmad.kernel.cmd.table.ReadTableCommand;
import cern.accsoft.steering.jmad.kernel.task.CycleSequence;
import cern.accsoft.steering.jmad.kernel.task.GetValues;
import cern.accsoft.steering.jmad.kernel.task.RunMatch;
import cern.accsoft.steering.jmad.kernel.task.RunTwiss;
import cern.accsoft.steering.jmad.kernel.task.SetBeam;
import cern.accsoft.steering.jmad.kernel.task.SetMisalignment;
import cern.accsoft.steering.jmad.kernel.task.ptc.InitPtcTask;
import cern.accsoft.steering.jmad.kernel.task.ptc.RunPtcTwiss;
import cern.accsoft.steering.jmad.kernel.task.track.DynapTask;
import cern.accsoft.steering.jmad.kernel.task.track.TrackTask;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinition;
import cern.accsoft.steering.jmad.modeldefs.io.ModelFileFinder;
import cern.accsoft.steering.jmad.modeldefs.io.ModelFileFinderManager;
import cern.accsoft.steering.jmad.util.FileUtil;

public class JMadModelImpl implements JMadModel, ElementAttributeReader {

    /** The logger for the class */
    protected static final Logger LOGGER = Logger.getLogger(JMadModelImpl.class);

    /** The definition of the model */
    private JMadModelDefinition modelDefinition = null;

    /** The last loaded optics definition */
    private OpticsDefinition activeOpticsDefinition = null;

    /** The last loaded range definition */
    private RangeDefinition activeRangeDefinition = null;

    /** The active Range */
    private Range activeRange = null;

    /** The actually loaded aperture data. Might be null. */
    private Aperture aperture = null;

    /** The kernel for this model to be injected by spring */
    private JMadKernel kernel;

    /** The last calculated optics-values */
    private Optic optics = new OpticImpl();

    /** The startup configuration defines which optics are loaded per default */
    private JMadModelStartupConfiguration startupConfiguration = new JMadModelStartupConfiguration();

    /** The Manager which keeps all model-specific knobs */
    private final KnobManager knobManager;

    /** The file finder for model files to be injected by spring */
    private ModelFileFinderManager modelFileFinderManager;

    /** The listeners */
    private final List<JMadModelListener> listeners = new ArrayList<JMadModelListener>();

    /** contains all the strengths and variables for the model */
    private final StrengthVarSet strengthVarSet = new StrengthVarSetImpl();

    /** true, if some strengthes have been set since last recalc of optics */
    protected boolean dirtyModel = false;

    /**
     * the twiss initial conditions. Shall also be present, if no active range is selected. Therefore they are set to
     * empty ones by default.
     */
    private TwissInitialConditions twissInitialConditions = new TwissInitialConditionsImpl();

    /** The mode defines which kind of twiss is used */
    private ModelMode modelMode = ModelMode.MADX;

    /**
     * The listener, which is added to all elemnts in order to update the madx-instance correctly, when element values
     * change.
     */
    private final ElementListener elementListener = new ElementListener() {
        @Override
        public void changedAttribute(Element element, String attributeName) {
            String name = element.getName() + Element.ATTR_SEPARATOR + attributeName;
            try {
                setValue(name, element.getAttribute(attributeName));
            } catch (JMadModelException e) {
                LOGGER.error("Error while passing new element-attribute value '" + name + "' to MadX.", e);
            }
        }
    };

    /**
     * The listener which is added to all strengths in order to update the madx-instance, when one of the element values
     * chages.
     */
    private final KnobListener strengthListener = new KnobListener() {
        @Override
        public void changedValue(Knob strength) {
            try {
                set(strength);
            } catch (JMadModelException e) {
                LOGGER.error("Error while passing new value for strength '" + strength.getName() + "' to MadX.", e);
            }
        }
    };

    /**
     * the listener to be notified when the twiss-initial conditions are changed.
     */
    private final TwissListener twissListener = new TwissListener() {

        /**
         * @param twiss
         */
        @Override
        public void changedTwiss(TwissInitialConditions twiss) {
            dirtyModel = true;
            fireBecameDirty();
        }
    };

    public JMadModelImpl() {
        twissInitialConditions.addListener(this.twissListener);
        this.knobManager = new KnobManagerImpl(this);
    }

    /**
     * ensures, that the model is initialized.
     * 
     * @throws JMadModelException if the initialization fails
     */
    private void ensureInit() throws JMadModelException {
        if (!isInitialized()) {
            LOGGER.debug("Model is not yet initialized. Starting init.");
            init();
        }
    }

    public void setModelDefinition(JMadModelDefinition modelDefinition) {
        this.modelDefinition = modelDefinition;
    }

    @Override
    public void init() throws JMadModelException {
        LOGGER.debug("initializing model.");

        /*
         * first we have to start the kernel
         */
        try {
            getKernel().start();
        } catch (JMadException e) {
            throw new JMadModelException("Error while initializing model.", e);
        }

        /*
         * if we have no model definition then we are done
         */
        if (getModelDefinition() == null) {
            return;
        }

        /*
         * If we have a model definition then we first of all run all the init files.
         */
        processModelFiles(getModelDefinition().getInitFiles());

        /* load the available beams for later use to the model */
        for (SequenceDefinition sequenceDefinition : getModelDefinition().getSequenceDefinitions()) {
            Beam beam = sequenceDefinition.getBeam();

            if (beam != null) {
                /* set sequence in beam for resbeam command */
                beam.setSequence(sequenceDefinition.getName());
                try {
                    getKernel().execute(new SetBeam(beam));
                } catch (JMadException e) {
                    LOGGER.error("Error defining beam for sequence [" + sequenceDefinition.getName()
                            + "] during model initialization.", e);
                }
            }
        }

        /*
         * then we decide if we should load some default ranges and optics.
         */

        OpticsDefinition opticsDefinition = getStartupConfiguration().getInitialOpticsDefinition();
        if ((opticsDefinition == null) && getStartupConfiguration().isLoadDefaultOptics()) {
            opticsDefinition = getModelDefinition().getDefaultOpticsDefinition();
        }
        if (opticsDefinition != null) {
            setActiveOpticsDefinition(opticsDefinition);
        }

        RangeDefinition rangeDefinition = getStartupConfiguration().getInitialRangeDefinition();
        if ((rangeDefinition == null) && getStartupConfiguration().isLoadDefaultRange()) {
            rangeDefinition = getModelDefinition().getDefaultRangeDefinition();
        }
        if (rangeDefinition != null) {
            setActiveRangeDefinition(rangeDefinition);
        }

        calcOptics();
    }

    private void processModelFiles(List<ModelFile> modelFiles) {
        boolean containsStrengthFile = false;
        for (ModelFile modelFile : modelFiles) {
            if ((modelFile instanceof CallableModelFile)
                    && (ParseType.STRENGTHS == ((CallableModelFile) modelFile).getParseType())) {
                containsStrengthFile = true;
                break;
            }
        }

        if (containsStrengthFile) {
            for (Strength strength : this.strengthVarSet.getStrengths()) {
                strength.removeListener(this.strengthListener);
            }
        }

        for (ModelFile modelFile : modelFiles) {
            File file = getModelFileFinder().getFile(modelFile);
            if (modelFile instanceof CallableModelFile) {
                this.call(file);
                if (ParseType.STRENGTHS == ((CallableModelFile) modelFile).getParseType()) {
                    parseStrengths(file);
                }
            } else if (modelFile instanceof TableModelFile) {
                this.readTable(file, ((TableModelFile) modelFile).getTableName());
            } else {
                LOGGER.error("Do not know what to do with modelFile of class '" + modelFile.getClass().getName() + "'");
            }
        }
        if (containsStrengthFile) {
            for (Strength strength : this.strengthVarSet.getStrengths()) {
                strength.addListener(this.strengthListener);
            }
        }
    }

    /**
     * instructs madx to read a table from a file.
     * <p>
     * If a tableName is given, then the {@link ReadMyTableCommand} is used and the table is stored in the given table
     * name. If the name is <code>null</code>, then the tablename must be given in the file.
     * 
     * @param file the file from which to load the table
     * @param tableName the name of the table (lowercase!)
     */
    public void readTable(File file, String tableName) {
        Command cmd;
        if (tableName == null) {
            cmd = new ReadTableCommand(file);
        } else {
            cmd = new ReadMyTableCommand(file, tableName);
        }
        try {
            this.kernel.execute(cmd);
        } catch (JMadException e) {
            LOGGER.error("Could not load table '" + file.getAbsolutePath() + "'");
        }
    }

    @Override
    public void reset() throws JMadModelException {
        cleanup();
        init();
    }

    /**
     * @return true if the model is initialized, false otherwise.
     */
    @Override
    public boolean isInitialized() {
        /*
         * is satisfying at the moment, might be refined sometime
         */
        return getKernel().isMadxRunning();
    }

    @Override
    public void cleanup() throws JMadModelException {
        try {
            if (getKernel().isMadxRunning()) {
                getKernel().stop();
            }
        } catch (JMadException e) {
            throw new JMadModelException("Error while stopping MadX-Kernel.", e);
        }

        this.activeOpticsDefinition = null;
        this.activeRangeDefinition = null;
        this.activeRange = null;

        optics = new OpticImpl();
        this.knobManager.cleanup();
    }

    @Override
    public synchronized JMadKernel getKernel() {
        return this.kernel;
    }

    @Override
    public List<Double> getValues(List<String> valueNames) throws JMadModelException {
        return this.getValuesResult(valueNames).getDoubleValues();
    }

    @Override
    public Map<String, Double> getValueMap(Collection<String> valueNames) throws JMadModelException {
        Map<String, Double> mapping = new HashMap<String, Double>();
        for (Strength strength : this.getValuesResult(valueNames).getValues()) {
            mapping.put(strength.getMadxName(), strength.getTotalValue());
        }
        return mapping;
    }

    private StrengthResult getValuesResult(Collection<String> valueNames) throws JMadModelException {

        ensureInit();

        StrengthResult valuesResult = null;

        try {
            Result result = getKernel().execute(new GetValues(valueNames));
            if (ResultType.VALUES_RESULT != result.getResultType()) {
                throw new JMadModelException("GetValues returned wrong type of result!");
            }
            valuesResult = (StrengthResult) result;
        } catch (JMadException e) {
            throw new JMadModelException("Error executing getValues.", e);
        }
        return valuesResult;
    }

    @Override
    public double getValue(String valueName) throws JMadModelException {
        List<Double> values = getValues(Collections.singletonList(valueName));
        if (values.size() != 1) {
            throw new JMadModelException("Result contains " + values.size() + " values, but should contain one.");
        }
        return values.get(0);
    }

    /**
     * calculates the actual optics-values and stores them in the internal Optics-object. This can be retrieved by
     * <code>getOptics()</code>
     * 
     * @return the optics-object
     * @throws JMadModelException if the calculation of the optics fails
     */
    private Optic calcOptics() throws JMadModelException {
        if (getActiveRange() == null) {
            return optics;
        }

        this.optics = OpticUtil.calcOptic(this);
        dirtyModel = false;
        fireOpticsChanged();
        return optics;
    }

    /**
     * creates all the element-objects for the active range by twissing and reading in the names and types of the
     * elements.
     * 
     * @throws JMadModelException if the reading of the active range fails
     */
    private void readActiveRange() throws JMadModelException {
        activeRange.clear();
        this.knobManager.cleanup();

        TfsResultRequestImpl resultRequest = new TfsResultRequestImpl();

        /* request all elements */
        resultRequest.addElementFilter(".*");

        /* request name, type and position. */
        resultRequest.addVariable(MadxTwissVariable.NAME);
        resultRequest.addVariable(MadxTwissVariable.KEYWORD);
        resultRequest.addVariable(MadxTwissVariable.S);

        TfsResult tfsResult = twiss(resultRequest);
        List<String> names = tfsResult.getStringData(MadxTwissVariable.NAME);
        List<String> keywords = tfsResult.getStringData(MadxTwissVariable.KEYWORD);
        List<Double> positions = tfsResult.getDoubleData(MadxTwissVariable.S);

        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            String keyword = keywords.get(i);
            MadxElementType madxElementType = MadxElementType.fromMadXName(keyword);
            Element element = ElementFactory.createElement(madxElementType, name);
            if (element != null) {
                if (element instanceof AbstractElement) {
                    ((AbstractElement) element).setAttributesReader(this);
                }
                element.setPosition(positions.get(i));
                activeRange.add(element);
                /* finally we register this model as listener */
                element.addListener(this.elementListener);
            }
        }
        dirtyModel = true;
        fireBecameDirty();
        fireElementsChanged();
    }

    @Override
    public void readAttributes(Element element) throws JMadModelException {
        readAttributes(Arrays.asList(new Element[] { element }));
    }

    @Override
    public void readAttributes(Collection<Element> elements) throws JMadModelException {
        ensureInit();

        /*
         * first of all we deactivate the listeners.
         */
        for (Element element : elements) {
            element.setListenersEnabled(false);
        }

        ArrayList<String> valueNames = new ArrayList<String>();

        for (Element element : elements) {
            List<String> attributeNames = element.getAttributeNames();

            for (String attributeName : attributeNames) {
                valueNames.add(element.getName() + Element.ATTR_SEPARATOR + attributeName);
            }
        }

        List<Double> values = getValues(valueNames);

        if (values.size() != valueNames.size()) {
            throw new JMadModelException("Amount of returned Values (" + values.size()
                    + ") differs from requested ones (" + valueNames.size() + ").");
        }

        int index = 0;
        for (Element element : elements) {
            List<String> attributeNames = element.getAttributeNames();
            for (String attributeName : attributeNames) {
                element.setAttribute(attributeName, values.get(index));
                index++;
            }
        }

        /* finally we re-activate the listeners */
        for (Element element : elements) {
            element.setAttributesInitialized(true);
            element.setListenersEnabled(true);
        }
    }

    /**
     * sends the misalignment-commands to madx
     * 
     * @param misalignmentConfiguration the misalignment-configuration from which to create the commands
     */
    protected void setMisalignment(MisalignmentConfiguration misalignmentConfiguration) {

        if (getKernel().isMadxRunning()) {
            try {
                getKernel().execute(new SetMisalignment(misalignmentConfiguration));
            } catch (JMadException e) {
                LOGGER.error("Error while setting Misalignment.", e);
            }
        }
        dirtyModel = true;
        fireBecameDirty();
    }

    //
    // twiss methods
    //

    @Override
    public TfsResult twiss(TfsResultRequest resultRequest) throws JMadModelException {
        return this.twiss(resultRequest, getTwissInitialConditions());
    }

    @Override
    public TfsResult twissToFile(TfsResultRequest resultRequest, File tfsFile) throws JMadModelException {
        return this.twissToFile(resultRequest, getTwissInitialConditions(), tfsFile);
    }

    @Override
    public TfsResult twissToFile(TfsResultRequest resultRequest, TwissInitialConditions customTwissInitialConditions,
            File tfsFile) throws JMadModelException {

        boolean keepOutput = ((JMadKernelImpl) this.getKernel()).isKeepOutputFile();
        ((JMadKernelImpl) this.getKernel()).setKeepOutputFile(true);

        TfsResult tfsResult = this.twiss(resultRequest, customTwissInitialConditions);

        try {
            FileUtil.copyFile(this.getKernel().getOutputFile(), tfsFile);
            this.getKernel().getOutputFile().delete();
            ((JMadKernelImpl) this.getKernel()).setKeepOutputFile(keepOutput);

        } catch (Exception e) {
            throw new JMadModelException("Could not write twiss output to File [" + tfsFile.getAbsolutePath() + "]", e);
        }

        return tfsResult;
    }

    @Override
    public TfsResult twiss(TfsResultRequest resultRequest, TwissInitialConditions customTwissInitialConditions)
            throws JMadModelException {
        ensureInit();

        Result result = null;
        try {
            if (ModelMode.PTC == getMode()) {
                getKernel().execute(new InitPtcTask());
                processModelFiles(getActiveOpticsDefinition().getPostPtcUniverseFiles());
                result = getKernel().execute(new RunPtcTwiss(customTwissInitialConditions, resultRequest));
                getKernel().execute(new PtcEndCommand());
            } else {
                result = getKernel().execute(new RunTwiss(customTwissInitialConditions, resultRequest));
            }
        } catch (JMadException e) {
            throw new JMadModelException("Error executing twiss.", e);
        }

        if ((resultRequest != null) && (result == null)) {
            throw new JMadModelException("ResultRequest was not null, but twiss returned no result!");
        }

        if ((result != null) && (ResultType.TFS_RESULT != result.getResultType())) {
            throw new JMadModelException("Twiss returned wrong type of result!");
        }

        return (TfsResult) result;

    }

    @Override
    public DynapResult dynap(DynapResultRequest dynapResultRequest, TrackInitialCondition trackInitialCondition)
            throws JMadModelException {
        Result result = null;
        try {
            result = this.getKernel().execute(new DynapTask(trackInitialCondition, dynapResultRequest));
        } catch (JMadException e) {
            throw new JMadModelException("Error executing dynap.", e);
        }

        if ((result != null) && ((ResultType.DYNAP_RESULT != result.getResultType()))) {
            throw new JMadModelException("Dynap returned wrong type of result!");
        }

        return (DynapResult) result;
    }

    @Override
    public TrackResult track(TrackResultRequest trackResultRequest, TrackInitialCondition trackInitialCondition)
            throws JMadModelException {
        Result result = null;
        try {
            result = this.getKernel().execute(new TrackTask(trackInitialCondition, trackResultRequest));
        } catch (JMadException e) {
            throw new JMadModelException("Error executing tracking.", e);
        }

        if ((result != null) && ((ResultType.TRACK_RESULT != result.getResultType()))) {
            throw new JMadModelException("Tracking returned wrong type of result!");
        }

        return (TrackResult) result;
    }

    /**
     * @return the actual twiss
     */
    @Override
    public TwissInitialConditions getTwissInitialConditions() {
        return this.twissInitialConditions;
    }

    public void set(Knob strength) throws JMadModelException {
        /** here we have to set always the total value (value+offset) */
        setValue(strength.getName(), strength.getTotalValue());
    }

    @Override
    public void setValue(String name, double value) throws JMadModelException {
        ensureInit();
        dirtyModel = true;
        try {
            getKernel().execute(new SetEqual(name, value));
        } catch (JMadException e) {
            throw new JMadModelException("Unable to set value '" + name + "' with value " + value, e);
        }
        fireBecameDirty();
    }

    @Override
    public void setValues(Map<String, Double> valueNamePairs) throws JMadModelException {
        ensureInit();
        dirtyModel = true;
        try {
            getKernel().execute(new SetListEqual(valueNamePairs));
        } catch (JMadException e) {
            throw new JMadModelException("Unable to set values from give Value-Name Map", e);
        }
        fireBecameDirty();
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            cleanup();
        } catch (JMadModelException e) {
            LOGGER.warn("Error during model - cleanup!", e);
        }
        LOGGER.debug("model was garbage-collected.");
        super.finalize();
    }

    public Optic getOptics() throws JMadModelException {
        calcOpticsIfDirty();
        return optics;
    }

    public void calcOpticsIfDirty() throws JMadModelException {
        if (dirtyModel) {
            calcOptics();
        }
    }

    public KnobManager getKnobManager() {
        return this.knobManager;
    }

    public String getName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getModelDefinition().getName());

        RangeDefinition rangeDefinition = this.getActiveRangeDefinition();
        if (rangeDefinition != null) {
            stringBuilder.append(" - " + rangeDefinition.getSequenceDefinition().getName());
        }

        return stringBuilder.toString();
    }

    @Override
    public void setActiveOpticsDefinition(OpticsDefinition newActiveOpticsDefinition) throws JMadModelException {
        if (newActiveOpticsDefinition == null) {
            return;
        }
        this.knobManager.cleanup();

        if (isInitialized()) {
            processModelFiles(newActiveOpticsDefinition.getInitFiles());
        }
        this.activeOpticsDefinition = newActiveOpticsDefinition;

        /*
         * ensure current active range is active XXX: this is only required as the current ABP Optics are not matched
         * correctly. during matching the lhcb2 sequence is always the last active sequence and inside madx it is not
         * possible to determine which sequence is active. therefore reload the last active range on java level.
         */
        RangeDefinition rangeDefinition = getActiveRangeDefinition();
        if (rangeDefinition != null) {
            this.setActiveRangeDefinition(getActiveRangeDefinition());
        }

        LOGGER.info("Set new active optics: '" + this.activeOpticsDefinition.getName() + "'.");

        this.dirtyModel = true;
        fireOpticsDefinitionChanged();
    }

    public OpticsDefinition getActiveOpticsDefinition() {
        return activeOpticsDefinition;
    }

    @Override
    public void setActiveRangeDefinition(RangeDefinition rangeDefinition) throws JMadModelException {

        Range range = new Range(rangeDefinition);

        if (getKernel().isMadxRunning()) {
            Beam beam = rangeDefinition.getSequenceDefinition().getBeam();

            try {
                if (beam == null) {
                    LOGGER.warn("No active beam. This is ok, " + "if beam command was provided in an init-command!?");
                } else {
                    /* set sequence in beam for resbeam command */
                    beam.setSequence(rangeDefinition.getSequenceDefinition().getName());
                    getKernel().execute(new SetBeam(beam));
                }

                if (rangeDefinition.getStartElementName() != null) {
                    getKernel().execute(new CycleSequence(rangeDefinition));
                }

                getKernel().execute(
                        new UseCommand(rangeDefinition.getSequenceDefinition().getName(), rangeDefinition
                                .getMadxRange()));
                /* ensure, that the ealigns are not added together */
                getKernel().execute(new EOptionCommand(null, false));

                processModelFiles(rangeDefinition.getPostUseFiles());

            } catch (JMadException e) {
                throw new JMadModelException("could not set active Range to '" + range.getName() + "'.", e);
            }
        }

        /**
         * range is still the same as before --> most likely optic change! We stop here.
         */
        if (this.activeRangeDefinition == rangeDefinition) {
            return;
        }

        this.aperture = null;
        this.activeRangeDefinition = rangeDefinition;
        activeRange = range;

        activeRange.addListener(new RangeListener() {
            @Override
            public void addedMisalignments(MisalignmentConfiguration misalignmentConfiguration) {
                misalignmentConfiguration.addListener(new MisalignmentConfigurationListener() {

                    @Override
                    public void changedMisalignmentValues(MisalignmentConfiguration changedMisalignmentConfiguration) {
                        setMisalignment(changedMisalignmentConfiguration);

                    }
                });
                setMisalignment(misalignmentConfiguration);
            }
        });

        TwissInitialConditions newTwissInitialConditions;
        newTwissInitialConditions = rangeDefinition.getTwiss().clone();
        setTwissInitialConditions(newTwissInitialConditions);

        if (getKernel().isMadxRunning()) {
            readActiveRange();
        }

        fireRangeChanged(activeRange);
    }

    private void setTwissInitialConditions(TwissInitialConditions twissInitialConditions) {
        this.twissInitialConditions = twissInitialConditions;
        this.twissInitialConditions.addListener(this.twissListener);
    }

    @Override
    public Range getActiveRange() {
        return activeRange;
    }

    @Override
    public void addListener(JMadModelListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(JMadModelListener listener) {
        listeners.remove(listener);
    }

    /**
     * notifies all listeners, that the elements were changed
     */
    private void fireElementsChanged() {
        for (JMadModelListener listener : listeners) {
            listener.elementsChanged();
        }
    }

    /**
     * notifies all listeners, that some (or all) optics values have changed.
     */
    private void fireOpticsChanged() {
        for (JMadModelListener listener : listeners) {
            listener.opticsChanged();
        }
    }

    /**
     * notifies all listeners, that a different range is now active
     * 
     * @param range the new active range
     */
    private void fireRangeChanged(Range range) {
        for (JMadModelListener listener : listeners) {
            listener.rangeChanged(range);
        }
    }

    @Override
    public void execute(String cmd) {
        FreeText textCmd = new FreeText();
        textCmd.setText(cmd);
        try {
            getKernel().execute(textCmd);
        } catch (JMadException e) {
            LOGGER.error("error while executing command + '" + cmd + "' in madx.", e);
        }
    }

    @Override
    public void call(File file) {
        try {
            getKernel().execute(new CallCommand(file));
        } catch (JMadException e) {
            LOGGER.error("Error while calling file '" + file.getAbsolutePath() + "'.", e);
        }
    }

    private void parseStrengths(File file) {
        StrengthFileParser parser = new StrengthFileParser(file);
        try {
            parser.parse();
        } catch (StrengthFileParserException e) {
            LOGGER.warn("Could not parse Strength file '" + file.getAbsolutePath() + "'.", e);
            return;
        }

        strengthVarSet.addAllStrengths(parser.getStrengths());
        strengthVarSet.addAllVariables(parser.getVariables());
    }

    /**
     * notify all listeners, that some values changed, so that the actual data is no longer valid.
     */
    protected void fireBecameDirty() {
        for (JMadModelListener listener : listeners) {
            listener.becameDirty();
        }
    }

    private void fireOpticsDefinitionChanged() {
        for (JMadModelListener listener : listeners) {
            listener.opticsDefinitionChanged();
        }
    }

    @Override
    public JMadModelDefinition getModelDefinition() {
        return this.modelDefinition;
    }

    @Override
    public RangeDefinition getActiveRangeDefinition() {
        return this.activeRangeDefinition;
    }

    @Override
    public MatchResult match(MatchResultRequest resultRequest) throws JMadModelException {

        if (resultRequest.getMadxVaryParameters().size() < 1) {
            throw new JMadModelException("At least one Vary Parameter is needed for Matching");
        }

        if (resultRequest.getMatchConstraints().size() < 1) {
            throw new JMadModelException("At least one Constraint is needed for Matching");
        }

        ensureInit();

        if (resultRequest.getSequenceName() == null) {
            resultRequest.setSequenceName(this.activeRangeDefinition.getSequenceDefinition().getName());
        }

        Result result = null;
        try {
            result = getKernel().execute(new RunMatch(resultRequest));
        } catch (JMadException e) {
            throw new JMadModelException("Error executing matching.", e);
        }

        if (result == null) {
            throw new JMadModelException("Matching returned no result!");
        }

        if (ResultType.MATCH_RESULT != result.getResultType()) {
            throw new JMadModelException("Matching returned wrong type of result!");
        }

        MatchResultImpl matchResult = (MatchResultImpl) result;

        // Check if successful
        if (matchResult.getFinalPenalty() <= resultRequest.getMatchMethod().getTolerance()) {
            matchResult.setSuccessful(true);
        } else {
            matchResult.setSuccessful(false);
        }

        // Get global Optics Values from TwissSummary
        TfsSummary twissSummary = this.calcTwissSummary();

        // Prepare TfsRequest for local Constraints
        TfsResultRequestImpl tfsReq = new TfsResultRequestImpl();
        Map<String, Map<String, Double>> localConstraints = new HashMap<String, Map<String, Double>>(0);

        for (MatchConstraint mC : resultRequest.getMatchConstraints()) {
            if (mC.isGlobal()) {
                for (String gP : mC.getParameterSettings().keySet()) {
                    MatchConstraintResultGlobal mCrG = new MatchConstraintResultGlobal(gP, twissSummary
                            .getDoubleValue(gP));
                    mCrG.setTargetValue(mC.getParameterSettings().get(gP));
                    matchResult.addConstrainParameterResult(mCrG);
                }
            } else {
                MatchConstraintLocal mCL = (MatchConstraintLocal) mC;

                // Only read out local Constraints if they are for a single
                // Element... Copy Element/ConstraintNames and Values to Map for
                // readOut and update of TargetValue
                if (mCL.getMadxRange().isElement()) {
                    localConstraints.put(mCL.getMadxRange().getMadxString(), mCL.getParameterSettings());
                }
            }
        }

        if (!localConstraints.isEmpty()) {

            for (Entry<String, Map<String, Double>> entry : localConstraints.entrySet()) {

                tfsReq.addElementFilter(entry.getKey());

                for (String var : entry.getValue().keySet()) {
                    MadxTwissVariable newVar = MadxTwissVariable.fromMadxName(var);

                    if (!tfsReq.getResultVariables().contains(newVar)) {
                        tfsReq.addVariable(newVar);
                    }
                }
            }

            // Better add this, otherwise no iteration
            // through names possible, obviously ;)
            tfsReq.addVariable(MadxTwissVariable.NAME);

            TfsResult twissResult = this.twiss(tfsReq);

            for (Entry<String, Map<String, Double>> gP : localConstraints.entrySet()) {

                int elIdx = twissResult.getElementIndex(gP.getKey());

                for (Entry<String, Double> entry : gP.getValue().entrySet()) {
                    MadxTwissVariable newVar = MadxTwissVariable.fromMadxName(entry.getKey());

                    MatchConstraintResultLocal mCrL = new MatchConstraintResultLocal(gP + Element.ATTR_SEPARATOR
                            + entry.getKey(), //
                            twissResult.getDoubleData(newVar).get(elIdx));
                    mCrL.setTargetValue(entry.getValue());

                    matchResult.addConstrainParameterResult(mCrL);
                }
            }
        }

        // update Model
        for (MadxVaryResult rPar : ((MatchResult) result).getVaryParameterResults()) {
            for (MadxVaryParameter vPar : resultRequest.getMadxVaryParameters()) {
                if (vPar.getName().equals(rPar.getName())) {
                    if (vPar.getMadxParameter() instanceof ElementAttribute) {
                        ((ElementAttribute) vPar.getMadxParameter()).setValue(rPar.getFinalValue());
                    } else if (vPar.getMadxParameter() instanceof SimpleStrength) {
                        ((AbstractKnob) vPar.getMadxParameter()).setValue(rPar.getFinalValue());
                    }
                }
            }
        }

        this.dirtyModel = true;

        return (MatchResult) result;
    }

    @Override
    public void saveBeta(String name, String location, boolean runDummyTwiss) throws JMadModelException {

        ensureInit();

        try {
            getKernel().execute(
                    new SaveBetaCommand(name, location, this.activeRangeDefinition.getSequenceDefinition().getName()));
            if (runDummyTwiss) {
                this.execute("twiss;");
            }
        } catch (JMadException e) {
            throw new JMadModelException("Error executing saveBeta.", e);
        }
    }

    @Override
    public TfsSummary calcTwissSummary() throws JMadModelException {
        TfsResult result = twiss(TfsResultRequestImpl.createSummaryOnlyRequest());
        return result.getSummary();
    }

    @Override
    public Aperture getAperture() {
        return this.aperture;
    }

    @Override
    public void loadAperture() {
        if (this.aperture != null) {
            LOGGER.info("Aperture already loaded. Nothing to do.");
            return;
        }
        ApertureDefinition definition = getActiveRangeDefinition().getApertureDefinition();
        if (definition == null) {
            LOGGER.warn("No aperture defined for current range. Nothing to do.");
            return;
        }

        ApertureReader reader = new ApertureReaderImpl();

        ModelFileFinder finder = getModelFileFinder();
        Aperture newAperture = reader.readIndex(finder.getFile(definition.getIndexFile()));

        for (ModelFile modelFile : definition.getPartFiles()) {
            reader.readValues(finder.getFile(modelFile), newAperture);
        }
        this.aperture = newAperture;
    }

    @Override
    public ModelFileFinder getModelFileFinder() {
        if (getModelFileFinderManager() == null) {
            LOGGER.warn("ModelDefinitionManager not configured!");
            return null;

        }
        return getModelFileFinderManager().getModelFileFinder(getModelDefinition());
    }

    @Override
    public StrengthVarSet getStrengthsAndVars() {
        return this.strengthVarSet;
    }

    public void setKernel(JMadKernel kernel) {
        this.kernel = kernel;
    }

    @Override
    public JMadModelStartupConfiguration getStartupConfiguration() {
        return startupConfiguration;
    }

    public void setModelFileFinderManager(ModelFileFinderManager modelFileFinderManager) {
        this.modelFileFinderManager = modelFileFinderManager;
    }

    public ModelFileFinderManager getModelFileFinderManager() {
        return modelFileFinderManager;
    }

    @Override
    public void setStartupConfiguration(JMadModelStartupConfiguration startupConfiguration) {
        this.startupConfiguration = startupConfiguration;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getDescription() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Model [" + this.getModelDefinition().getName() + "]");

        RangeDefinition rangeDefinition = this.getActiveRangeDefinition();
        if (rangeDefinition != null) {
            stringBuilder.append(" - Sequence [" + rangeDefinition.getSequenceDefinition().getName() + "]");
            stringBuilder.append(" - Range [" + rangeDefinition.getName() + "]");
        }

        OpticsDefinition opticDefinition = this.getActiveOpticsDefinition();
        if (opticDefinition != null) {
            stringBuilder.append(" - Optic [" + opticDefinition.getName() + "]");
        }

        return stringBuilder.toString();
    }

    @Override
    public ModelMode getMode() {
        return this.modelMode;
    }

    @Override
    public void setMode(ModelMode modelMode) {
        this.modelMode = modelMode;
    }

    @Override
    public void setTitel(String title) {
        this.execute("title,\"" + title + "\";");
    }
}
