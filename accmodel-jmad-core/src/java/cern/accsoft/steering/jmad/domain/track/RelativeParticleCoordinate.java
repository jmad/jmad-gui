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
 * describes the position in 6D phase space of a particle relatively to a reference one.
 * 
 * @author xbuffat
 */

public interface RelativeParticleCoordinate {

    /**
     * @return X
     */
    double getXRelatviePosition();

    /**
     * @return PX = px/p0
     */
    double getXRelativeMomentum();

    /**
     * @return Y
     */
    double getYRelativePosition();

    /**
     * @return PY = py/p0
     */
    double getYRelatvieMomentum();

    /**
     * @return T = -c*t
     */
    double getRelativeTimeDifference();

    /**
     * @return PT = delta(E)/ps*c
     */
    double getRelativeEnergyError();

    void setXRelatviePosition(double x);

    void setXRelativeMomentum(double px);

    void setYRelativePosition(double y);

    void setYRelatvieMomentum(double py);

    void setRelativeTimeDiffence(double t);

    void setRelativeEnergyError(double pt);
}
