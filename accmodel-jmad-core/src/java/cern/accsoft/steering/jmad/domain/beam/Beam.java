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

package cern.accsoft.steering.jmad.domain.beam; // NOPMD by kaifox on 6/25/10 5:26 PM

import cern.accsoft.steering.jmad.domain.types.MadxValue;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Represents all the attributes a particle beam in MadX can have. Detailed information can be found <a
 * href="http://mad.web.cern.ch/mad/Introduction/beam.html">here</a>.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
@XStreamAlias("beam")
/*
 * Many fields. This is an intrinsic fact, which results from the MadX beam-command and can not be changed.
 */
public class Beam { // NOPMD by kaifox on 6/25/10 5:26 PM

    /*
     * Parameter names and their member-fields:
     */

    /** (predefined) Particle type */
    @XStreamAlias("particle")
    @XStreamAsAttribute
    private Particle particle = null;

    /**
     * if no predefined particle is set, then the following otions are allowed:
     */
    @XStreamAlias("particle-name")
    @XStreamAsAttribute
    private String particleName = null;

    /** mass of particle, if not predefined */
    @XStreamAlias("mass")
    @XStreamAsAttribute
    private Double mass = null;

    /** charge of particle, if not predefined */
    @XStreamAlias("charge")
    @XStreamAsAttribute
    private Double charge = null;

    /**
     * ENERGY: The total energy per particle in GeV. If given, it must be greater then the particle restmass.
     */
    @XStreamAlias("energy")
    @XStreamAsAttribute
    private Double energy = null;

    /**
     * PC: The momentum per particle in GeV/c. If given, it must be greater than zero.
     */
    @XStreamAlias("pc")
    @XStreamAsAttribute
    private Double momentum = null;

    /**
     * GAMMA: The ratio between total energy and rest energy of the particles: GAMMA = E / m0. If given, it must be
     * greater than one. If the restmass is changed a new value for the energy should be entered. Otherwise the energy
     * remains unchanged, and the momentum PC and the quantity GAMMA are recalculated.
     */
    @XStreamAlias("gamma")
    @XStreamAsAttribute
    private Double gamma = null;

    /*
     * The emittances are defined by:
     */
    /**
     * EX: The horizontal emittance Ex (default: 1 m).
     */
    @XStreamAlias("ex")
    @XStreamAsAttribute
    private Double horizontalEmittance = null;

    /** EY: The vertical emittance Ey (default: 1 m). */
    @XStreamAlias("ey")
    @XStreamAsAttribute
    private Double verticalEmittance = null;

    /** ET: The longitudinal emittance Et (default: 1 m). */
    @XStreamAlias("et")
    @XStreamAsAttribute
    private Double longitudinalEmittance = null;

    /*
     * The emittances can be replaced by the normalised emittances and the energy spread:
     */
    /**
     * EXN: The normalised horizontal emittance [m]: Exn = 4 (GAMMA2 - 1)1/2 Ex (ignored if Ex is given).
     */
    @XStreamAlias("exn")
    @XStreamAsAttribute
    private Double normalisedHorizontalEmittance = null;

    /**
     * EYN: The normalised vertical emittance [m]: Eyn = 4 (GAMMA2 - 1)1/2 Ey (ignored if Ex is given).
     */
    @XStreamAlias("eyn")
    @XStreamAsAttribute
    private Double normalisedVerticalEmittance = null;

    /** SIGT: The bunch length c sigma(t) in [m]. */
    @XStreamAlias("sigt")
    @XStreamAsAttribute
    private Double bunchLength = null;

    /** SIGE: The relative energy spread sigma(E)/E in [1]. */
    @XStreamAlias("sige")
    @XStreamAsAttribute
    private Double relativeEnergySpread = null;

    /*
     * further parameters:
     */
    /** KBUNCH: The number of particle bunches in the machine (default: 1). */
    @XStreamAlias("kbunch")
    @XStreamAsAttribute
    private Integer bunchNumber = null;

    /** NPART: The number of particles per bunch (default: 0). */
    @XStreamAlias("npart")
    @XStreamAsAttribute
    private Double particleNumber = null;

    /** BCURRENT: The bunch current (default: 0 A). */
    @XStreamAlias("bcurrent")
    @XStreamAsAttribute
    private Double bunchCurrent = null;

    /**
     * BUNCHED: A logical flag. If set, the beam is treated as bunched whenever this makes sense.
     */
    @XStreamAlias("bunched")
    @XStreamAsAttribute
    private Boolean bunched = null;

    /**
     * RADIATE: A logical flag. If set, synchrotron radiation is considered in all bipolar magnets.
     */
    @XStreamAlias("radiate")
    @XStreamAsAttribute
    private Boolean radiate = null;

    /**
     * BV: an integer specifying the direction of the particle movement in a beam line; either +1 (default), or -1. For
     * a detailed explanation see under bv flag.
     */
    @XStreamAlias("bv")
    @XStreamAsAttribute
    private Direction direction = null;

    /**
     * SEQUENCE: this attaches the beam command to a specific sequence; if the name is omitted, the BEAM command refers
     * to the default beam always present. Sequences without attached beam use this default beam. When updating a beam,
     * the corresponding sequence name, if any, must always be mentioned.
     */
    @XStreamAlias("sequence")
    @XStreamAsAttribute
    private String sequence = null;

    /**
     * Represents the direction of the beam in MadX. This is the java equivalent to the <a
     * href="http://mad.web.cern.ch/mad/Introduction/bv_flag.html">bv flag</a> in MadX. It was introduced in MadX
     * especially for the needs of LHC design in order to cope with two beams circulating in opposite directions.
     * 
     * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
     */
    public enum Direction implements MadxValue {
        /**
         * Represents a beam going in the direction of as the sequence is defined. (e.g. LHC beam 1)
         */
        PLUS(1),
        /**
         * Represents a beam going in the opposite direction as the sequence is defined. (e.g.LHC beam 2)
         */
        MINUS(-1);

        private Integer direction;

        private Direction(Integer direction) {
            this.direction = direction;
        }

        @Override
        public String getMadxString() {
            return direction.toString();
        }
    }

    /**
     * represents one of the possible particles that can be used to define a MadX beam.
     * 
     * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
     */
    public enum Particle implements MadxValue {
        POSITRON("positron"), ELECTRON("electron"), PROTON("proton"), ANTIPROTON("antiproton"), POSMUON("posmuon"), NEGMUON(
                "negmuon");

        private String name;

        private Particle(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String getMadxString() {
            return getName();
        }
    }

    public Particle getParticle() {
        return particle;
    }

    public void setParticle(Particle particle) {
        this.particle = particle;
    }

    public String getParticleName() {
        return particleName;
    }

    public void setParticleName(String particleName) {
        this.particleName = particleName;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public Double getCharge() {
        return charge;
    }

    public void setCharge(Double charge) {
        this.charge = charge;
    }

    public Double getEnergy() {
        return energy;
    }

    public void setEnergy(Double energy) {
        this.energy = energy;
    }

    public Double getMomentum() {
        return momentum;
    }

    public void setMomentum(Double momentum) {
        this.momentum = momentum;
    }

    public Double getGamma() {
        return gamma;
    }

    public void setGamma(Double gamma) {
        this.gamma = gamma;
    }

    public Double getHorizontalEmittance() {
        return horizontalEmittance;
    }

    public void setHorizontalEmittance(Double horizontalEmittance) {
        this.horizontalEmittance = horizontalEmittance;
    }

    public Double getVerticalEmittance() {
        return verticalEmittance;
    }

    public void setVerticalEmittance(Double verticalEmittance) {
        this.verticalEmittance = verticalEmittance;
    }

    public Double getLongitudinalEmittance() {
        return longitudinalEmittance;
    }

    public void setLongitudinalEmittance(Double longitudinalEmittance) {
        this.longitudinalEmittance = longitudinalEmittance;
    }

    public Double getNormalisedHorizontalEmittance() {
        return normalisedHorizontalEmittance;
    }

    public void setNormalisedHorizontalEmittance(Double normalisedHorizontalEmittance) {
        this.normalisedHorizontalEmittance = normalisedHorizontalEmittance;
    }

    public Double getNormalisedVerticalEmittance() {
        return normalisedVerticalEmittance;
    }

    public void setNormalisedVerticalEmittance(Double normalisedVerticalEmittance) {
        this.normalisedVerticalEmittance = normalisedVerticalEmittance;
    }

    public Double getBunchLength() {
        return bunchLength;
    }

    public void setBunchLength(Double bunchLength) {
        this.bunchLength = bunchLength;
    }

    public Double getRelativeEnergySpread() {
        return relativeEnergySpread;
    }

    public void setRelativeEnergySpread(Double relativeEnergySpread) {
        this.relativeEnergySpread = relativeEnergySpread;
    }

    public Integer getBunchNumber() {
        return bunchNumber;
    }

    public void setBunchNumber(Integer bunchNumber) {
        this.bunchNumber = bunchNumber;
    }

    public Double getParticleNumber() {
        return particleNumber;
    }

    public void setParticleNumber(Double particleNumber) {
        this.particleNumber = particleNumber;
    }

    public Double getBunchCurrent() {
        return bunchCurrent;
    }

    public void setBunchCurrent(Double bunchCurrent) {
        this.bunchCurrent = bunchCurrent;
    }

    public Boolean getBunched() {
        return bunched;
    }

    public void setBunched(Boolean bunched) {
        this.bunched = bunched;
    }

    public Boolean getRadiate() {
        return radiate;
    }

    public void setRadiate(Boolean radiate) {
        this.radiate = radiate;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

}
