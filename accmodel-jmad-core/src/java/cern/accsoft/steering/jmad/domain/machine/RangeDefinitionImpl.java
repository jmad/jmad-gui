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

package cern.accsoft.steering.jmad.domain.machine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cern.accsoft.steering.jmad.domain.file.ModelFile;
import cern.accsoft.steering.jmad.domain.machine.filter.NameFilter;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditionsImpl;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * this class defines a range in a sequence, by defining the first and the last element.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
@XStreamAlias("range")
public class RangeDefinitionImpl implements RangeDefinition, Cloneable {

    /** the name of the range */
    @XStreamAlias("name")
    @XStreamAsAttribute
    private String name = null;

    /**
     * the range as used by madx.
     */
    @XStreamAlias("madx-range")
    private MadxRange madxRange = new MadxRange();

    /** the twiss with the correct initial values for this range */
    @XStreamAlias("twiss-initial-conditions")
    private TwissInitialConditionsImpl twiss = null;

    /* filters, which define the monitors which shall be inverted */
    @XStreamAlias("monitor-invert-filters")
    private List<NameFilter> monitorInvertFilters = new ArrayList<NameFilter>();

    @XStreamAlias("corrector-invert-filters")
    private List<NameFilter> correctorInvertFilters = new ArrayList<NameFilter>();

    /** files that shall be executed after the use-command. */
    @XStreamAlias("post-use-files")
    private List<ModelFile> postUseFiles = new ArrayList<ModelFile>();

    /**
     * The name of the start element. If non-null then the sequence is rotatet before use.
     */
    @XStreamAlias("start-element-name")
    private String startElementName = null;

    /** the definition of the aperture for this range. NOTE: this might be null! */
    @XStreamOmitField
    private ApertureDefinition apertureDefinition = null;

    /** the sequence definition this {@link RangeDefinition} belongs to */
    @XStreamOmitField
    private SequenceDefinition sequenceDefinition = null;

    /**
     * default constructor. Necessary for xstream
     */
    public RangeDefinitionImpl() {
        super();
    }

    /**
     * The default constructor. creates a range, which covers the whole Sequence.
     * 
     * @param sequenceDefinition The sequence definition to which this range belongs to
     * @param name the name of this range
     * @param twiss the initial conditions for the twiss-command in this range
     */
    public RangeDefinitionImpl(SequenceDefinition sequenceDefinition, String name, TwissInitialConditionsImpl twiss) {
        this.sequenceDefinition = sequenceDefinition;
        this.name = name;
        this.twiss = twiss;
    }

    /**
     * an alternative constructor, with the possibility to provide the name of the first and the last element.
     * 
     * @param sequenceDefinition The sequence definition to which this range belongs to
     * @param name a verbose name for the range
     * @param madxRange the range as it has to be defined for MadX
     * @param twiss the initial conditions for the twiss command in this range
     */
    public RangeDefinitionImpl(SequenceDefinition sequenceDefinition, String name, MadxRange madxRange,
            TwissInitialConditionsImpl twiss) {
        this(sequenceDefinition, name, twiss);
        this.madxRange = madxRange;
    }

    @Override
    public RangeDefinitionImpl clone() throws CloneNotSupportedException {
        RangeDefinitionImpl newRangeDefinition = (RangeDefinitionImpl) super.clone();
        newRangeDefinition.sequenceDefinition = sequenceDefinition;
        newRangeDefinition.madxRange = madxRange;
        newRangeDefinition.twiss = twiss;
        newRangeDefinition.monitorInvertFilters = new ArrayList<NameFilter>(this.monitorInvertFilters);
        newRangeDefinition.correctorInvertFilters = new ArrayList<NameFilter>(this.correctorInvertFilters);
        newRangeDefinition.apertureDefinition = apertureDefinition;
        newRangeDefinition.startElementName = startElementName;
        newRangeDefinition.postUseFiles = new ArrayList<ModelFile>(postUseFiles);
        return newRangeDefinition;
    }

    public final TwissInitialConditionsImpl getTwiss() {
        return twiss;
    }

    public String getName() {
        return name;
    }

    /**
     * adds a filter which defines that some monitors shall be inverted
     * 
     * @param filter the filter to add
     */
    public void addMonitorInvertFilter(NameFilter filter) {
        this.monitorInvertFilters.add(filter);
    }

    public final List<NameFilter> getMonitorInvertFilters() {
        return monitorInvertFilters;
    }

    /**
     * adds a filter, which defines that some correctors shall be inverted.
     * 
     * @param filter the filter to add
     */
    public void addCorrectorInvertFilter(NameFilter filter) {
        this.correctorInvertFilters.add(filter);
    }

    public final List<NameFilter> getCorrectorInvertFilters() {
        return correctorInvertFilters;
    }

    /**
     * adds a file that shall be executed after the use-command.
     * 
     * @param postUseFile the postUseFile to add
     */
    public void addPostUseFile(ModelFile postUseFile) {
        this.postUseFiles.add(postUseFile);
    }

    @Override
    public List<ModelFile> getPostUseFiles() {
        return postUseFiles;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public SequenceDefinition getSequenceDefinition() {
        return this.sequenceDefinition;
    }

    public void setSequenceDefinition(SequenceDefinition sequenceDefinition) {
        this.sequenceDefinition = sequenceDefinition;
    }

    @Override
    public ApertureDefinition getApertureDefinition() {
        return this.apertureDefinition;
    }

    public void setApertureDefinition(ApertureDefinition apertureDefinition) {
        this.apertureDefinition = apertureDefinition;
    }

    @Override
    public Collection<ModelFile> getRequiredFiles() {
        return getPostUseFiles();
    }

    public void setMadxRange(MadxRange madxRange) {
        this.madxRange = madxRange;
    }

    @Override
    public MadxRange getMadxRange() {
        return madxRange;
    }

    @Override
    public String getStartElementName() {
        return this.startElementName;
    }

    public void setStartElementName(String startElementName) {
        this.startElementName = startElementName;
    }

    /**
     * this method is called before the object is written to xml. We do some cleanups here to avoid writing empty lists
     * to xml.
     * 
     * @return the cleaned up object.
     */
    private Object writeReplace() {
        RangeDefinitionImpl writtenObj;
        try {
            writtenObj = clone();
        } catch (CloneNotSupportedException e) {
            return this;
        }

        if (monitorInvertFilters.isEmpty()) {
            /* PMD: Has to be set to null to get a clean xml */
            writtenObj.monitorInvertFilters = null; // NOPMD by kaifox on
            // 10/6/10 8:16 PM
        }
        if (correctorInvertFilters.isEmpty()) {
            /* PMD: Has to be set to null to get a clean xml */
            writtenObj.correctorInvertFilters = null; // NOPMD by kaifox on
            // 10/6/10 8:16 PM
        }
        if (postUseFiles.isEmpty()) {
            /* PMD: Has to be set to null to get a clean xml */
            writtenObj.postUseFiles = null; // NOPMD by kaifox on 10/6/10 8:16
            // PM
        }
        return writtenObj;
    }

    /**
     * is called after creating this object from xml. Some additional initialization is done here, since no emplty lists
     * are stored to xml.
     * 
     * @return the this object, fully configured.
     */
    private Object readResolve() {
        if (monitorInvertFilters == null) {
            monitorInvertFilters = new ArrayList<NameFilter>();
        }
        if (correctorInvertFilters == null) {
            correctorInvertFilters = new ArrayList<NameFilter>();
        }
        if (postUseFiles == null) {
            postUseFiles = new ArrayList<ModelFile>();
        }
        return this;
    }

}
