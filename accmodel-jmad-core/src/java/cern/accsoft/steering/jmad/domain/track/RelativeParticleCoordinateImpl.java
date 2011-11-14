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
 * Standard implementation of RelativeParticleCoordinate
 * 
 * @author xbuffat
 */

public class RelativeParticleCoordinateImpl implements RelativeParticleCoordinate {

    private double x = 0.0;
    private double px = 0.0;
    private double y = 0.0;
    private double py = 0.0;
    private double t = 0.0;
    private double pt = 0.0;
    
    private boolean actionAngle = false;

    public RelativeParticleCoordinateImpl() {

    }

    public RelativeParticleCoordinateImpl(double x, double px, double y, double py, double t, double pt) {
        this.setXRelatviePosition(x);
        this.setXRelativeMomentum(px);
        this.setYRelativePosition(y);
        this.setYRelatvieMomentum(py);
        this.setRelativeTimeDiffence(t);
        this.setRelativeEnergyError(pt);
    }

    @Override
    public double getRelativeEnergyError() {
        return pt;
    }

    @Override
    public double getRelativeTimeDifference() {
        return t;
    }

    @Override
    public double getXRelativeMomentum() {
        return px;
    }

    @Override
    public double getXRelatviePosition() {
        return x;
    }

    @Override
    public double getYRelativePosition() {
        return y;
    }

    @Override
    public double getYRelatvieMomentum() {
        return py;
    }

    @Override
    public void setRelativeEnergyError(double pt) {
        this.pt = pt;

    }

    @Override
    public void setRelativeTimeDiffence(double t) {
        this.t = t;

    }

    @Override
    public void setXRelativeMomentum(double px) {
        this.px = px;

    }

    @Override
    public void setXRelatviePosition(double x) {
        this.x = x;

    }

    @Override
    public void setYRelativePosition(double y) {
        this.y = y;

    }

    @Override
    public void setYRelatvieMomentum(double py) {
        this.py = py;

    }

    @Override
	public void setActionAngle(boolean actionAngle) {
		this.actionAngle = actionAngle;
	}

    @Override
	public boolean isActionAngle() {
		return actionAngle;
	}

}
