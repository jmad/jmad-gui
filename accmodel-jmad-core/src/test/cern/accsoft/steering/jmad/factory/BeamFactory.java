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

package cern.accsoft.steering.jmad.factory;

import cern.accsoft.steering.jmad.MadXConstants;
import cern.accsoft.steering.jmad.domain.beam.Beam;

public final class BeamFactory {

    private BeamFactory() {
        // only static methods
    }

    /**
     * creates the beam which can be used by default for LHC sequences
     * 
     * @return the beam
     */
    public static Beam createDefaultLhcBeam() {
        Double energy = 450.0; // energy in GeV
        Double gamma = energy / MadXConstants.MASS_PROTON; // beta
        Double emittance = 3.5e-06; // normalized emittance
        Double xEmittance = emittance / (gamma);
        Double yEmittance = emittance / (gamma);

        Beam beam = new Beam();
        beam.setParticle(Beam.Particle.PROTON);
        beam.setEnergy(energy);
        beam.setBunchLength(0.077);
        beam.setDirection(Beam.Direction.PLUS);
        beam.setParticleNumber(1.1E11);
        beam.setRelativeEnergySpread(5e-4);
        beam.setHorizontalEmittance(xEmittance);
        beam.setVerticalEmittance(yEmittance);
        return beam;
    }
}
