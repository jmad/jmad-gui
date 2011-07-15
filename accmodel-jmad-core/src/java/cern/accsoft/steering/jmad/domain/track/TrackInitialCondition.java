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

package cern.accsoft.steering.jmad.domain.track;

/**
 * Defines initial values to enter tracking module. All values have default
 * 
 * @author xbuffat
 */

public interface TrackInitialCondition {

    /**
     * @param onePass if true, no stability test, ie. no closed-orbit search (default false);
     */
    void setOnePass(boolean onePass);

    /**
     * @return if true, no stability test, ie. no closed-orbit search
     */
    boolean isOnePass();

    /**
     * @param dump If true, write the particle coordinates in files (default false)
     */
    void setWriteAtEachTurn(boolean dump);

    /**
     * @return If true, write the particle coordinates in files
     */
    boolean isWriteAtEachTurn();

    /**
     * @param deltap relative momentum offset for reference closed orbit (default 0.0)
     */
    void setDeltaP(double deltap);

    /**
     * @return relative momentum offset for reference closed orbit
     */
    double getDeltaP();

    /**
     * @param damp I true, introduce synchrotron damping (default false)
     */
    void setSynchrotronDamped(boolean damp);

    /**
     * @return I true, introduce synchrotron damping
     */
    boolean isSynchrotronDamped();

    /**
     * @param quantum If true, introduce quantum excitation (default false)
     */
    void setQuantumExcited(boolean quantum);

    /**
     * @return If true, introduce quantum excitation
     */
    boolean isQuantumExcited();

    /**
     * @param aperture If true, particles are lost if their trajectories are outside the aperture of the current element
     *            (default false)
     */
    void setCheckAperture(boolean aperture);

    /**
     * @return If true, particles are lost if their trajectories are outside the aperture of the current element
     */
    boolean isCheckAperture();

    /**
     * @param onetable write all particle coordinates in a single file (default true)
     */
    void setOneTable(boolean onetable);

    /**
     * @return write all particle coordinates in a single file
     */
    boolean isOneTable();

    /**
     * @param recloss If true, creates a file with coordinates of lost particles (default false)
     */
    void setCreateLossParticleFile(boolean recloss);

    /**
     * @return If true, creates a file with coordinates of lost particles
     */
    boolean isCreateLossParticleFile();
}
