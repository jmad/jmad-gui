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

/**
 * 
 */
package cern.accsoft.steering.jmad.domain.machine;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.beam.Beam;
import cern.accsoft.steering.jmad.util.xml.converters.NameRefConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * The default implementation for a sequence-definition. A Sequence definition represents a set of files that define a
 * <a href="http://mad.web.cern.ch/mad/Introduction/sequence.html">SEQUENCE</a> in MadX.
 * <p>
 * This sequence definition also contains several ranges and the beam command associated with the sequence.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
@XStreamAlias("sequence")
public class SequenceDefinitionImpl implements SequenceDefinition {

    /** The name of the sequence */
    @XStreamAlias("name")
    @XStreamAsAttribute
    private final String name;

    /** The beam to use for the sequence */
    @XStreamAlias("beam")
    private final Beam beam;

    /** The predefined Ranges for this sequence */
    @XStreamAlias("ranges")
    private final List<RangeDefinition> rangeDefinitions = new ArrayList<RangeDefinition>();

    /** the default range definition */
    @XStreamOmitField
    private RangeDefinition defaultRangeDefinition = null;

    @XStreamAlias("default-range")
    @XStreamConverter(NameRefConverter.class)
    private String defaultRangeDefinitionName = null;

    /**
     * default constructor for xstream
     */
    public SequenceDefinitionImpl() {
        this(null, null);
    }

    /**
     * the constructor, which enforces to set a name and a beam.
     * 
     * @param name the name of the Sequence
     * @param beam the beam for this sequence. Occasionally this might be <code>null</code>.
     */
    public SequenceDefinitionImpl(String name, Beam beam) {
        this.name = name;
        this.beam = beam;
    }

    @Override
    public Beam getBeam() {
        return this.beam;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<RangeDefinition> getRangeDefinitions() {
        return this.rangeDefinitions;
    }

    @Override
    public RangeDefinition getDefaultRangeDefinition() {
        return this.defaultRangeDefinition;
    }

    public void setDefaultRangeDefinition(RangeDefinition rangeDefinition) {
        if (rangeDefinition == null) {
            return;
        }

        if (!this.rangeDefinitions.contains(rangeDefinition)) {
            addRangeDefinition(rangeDefinition);
        }

        /* only set it, if the addition worked */
        if (this.rangeDefinitions.contains(rangeDefinition)) {
            this.defaultRangeDefinition = rangeDefinition;
            this.defaultRangeDefinitionName = rangeDefinition.getName();
        }
    }

    /**
     * adds a {@link RangeDefinition} to the sequence
     * 
     * @param rangeDefinition the {@link RangeDefinition} to add
     */
    public void addRangeDefinition(RangeDefinition rangeDefinition) {
        this.rangeDefinitions.add(rangeDefinition);
    }

    @Override
    public final RangeDefinition getRangeDefinition(String rangeName) {
        if (rangeName == null) {
            return null;
        }
        for (RangeDefinition rangeDefinition : getRangeDefinitions()) {
            if (rangeName.equals(rangeDefinition.getName())) {
                return rangeDefinition;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return getName();
    }

    /**
     * this method is called after deserialization.
     * 
     * @return this
     */
    private Object readResolve() {

        /*
         * we have to set the back-references correctly
         */
        for (RangeDefinition rangeDefinition : this.rangeDefinitions) {
            if (rangeDefinition instanceof RangeDefinitionImpl) {
                ((RangeDefinitionImpl) rangeDefinition).setSequenceDefinition(this);
            }
        }

        /*
         * and set the default range definition if any
         */
        if (this.defaultRangeDefinitionName != null) {
            setDefaultRangeDefinition(getRangeDefinition(this.defaultRangeDefinitionName));
        }

        return this;
    }
}
