// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, Kajetan Fuchsberger. All rights reserved.
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
package cern.accsoft.steering.jmad.domain.track;

/**
 * Standard implementation of TrackInitialCondition
 * 
 * @author xbuffat
 *
 */

public class TrackInitialConditionImpl implements TrackInitialCondition {

	private double deltaP = 0.0;
	private boolean checkAperture = false;
	private boolean createLossParticleFile = false;
	private boolean onePass = false;
	private boolean oneTable = true;
	private boolean quantumExcited = false;
	private boolean synchrotronDamped = false;
	private boolean writeAtEachTurn = false;

	@Override
	public double getDeltaP() {
		return deltaP;
	}
	
	@Override
	public boolean isCheckAperture() {
		return checkAperture;
	}

	@Override
	public boolean isCreateLossParticleFile() {
		return createLossParticleFile;
	}

	@Override
	public boolean isOnePass() {
		return onePass;
	}

	@Override
	public boolean isOneTable() {
		return oneTable;
	}

	@Override
	public boolean isQuantumExcited() {
		return quantumExcited;
	}

	@Override
	public boolean isSynchrotronDamped() {
		return synchrotronDamped;
	}

	@Override
	public boolean isWriteAtEachTurn() {
		return writeAtEachTurn;
	}

	@Override
	public void setCheckAperture(boolean aperture) {
		this.checkAperture = aperture;

	}

	@Override
	public void setCreateLossParticleFile(boolean recloss) {
		this.createLossParticleFile = recloss;

	}

	@Override
	public void setDeltaP(double deltaP) {
		this.deltaP = deltaP;

	}

	@Override
	public void setOnePass(boolean onePass) {
		this.onePass = onePass;

	}

	@Override
	public void setOneTable(boolean oneTable) {
		this.oneTable = oneTable;

	}

	@Override
	public void setQuantumExcited(boolean quantum) {
		this.quantumExcited = quantum;

	}

	@Override
	public void setSynchrotronDamped(boolean damp) {
		this.synchrotronDamped = damp;

	}

	@Override
	public void setWriteAtEachTurn(boolean dump) {
		this.writeAtEachTurn = dump;

	}

}
