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
package cern.accsoft.steering.jmad.domain.result.match;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.result.match.input.MadxVaryParameter;
import cern.accsoft.steering.jmad.domain.result.match.input.MatchConstraint;
import cern.accsoft.steering.jmad.domain.result.match.methods.MatchMethod;
import cern.accsoft.steering.jmad.domain.result.match.methods.MatchMethodLmdif;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditions;

public class MatchResultRequestImpl implements MatchResultRequest {

    private String sequenceName = null;
    private MatchMethod matchMethod = new MatchMethodLmdif();
    private final List<MadxVaryParameter> varyParameters = new ArrayList<MadxVaryParameter>();
    private final List<MatchConstraint> matchConstraints = new ArrayList<MatchConstraint>();

    private TwissInitialConditions initTwiss = null;
    private String saveBetaName = null;

    @Override
    public List<MadxVaryParameter> getMadxVaryParameters() {
        return this.varyParameters;
    }

    public void addMadxVaryParameter(MadxVaryParameter varyParameter) {
        this.varyParameters.add(varyParameter);
    }

    @Override
    public List<MatchConstraint> getMatchConstraints() {
        return this.matchConstraints;
    }

    public void addMatchConstraint(MatchConstraint matchConstraint) {
        this.matchConstraints.add(matchConstraint);
    }

    @Override
    public MatchMethod getMatchMethod() {
        return this.matchMethod;
    }

    public void setMatchMethod(MatchMethod matchMethod) {
        this.matchMethod = matchMethod;
    }

    @Override
    public String getSequenceName() {
        return this.sequenceName;
    }

    @Override
    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public void setInitTwiss(TwissInitialConditions initTwiss) {
        /* TODO: Set non available values for matching to zero if necessary */
        this.initTwiss = initTwiss;
    }

    @Override
    public TwissInitialConditions getInitialOpticsValues() {
        return this.initTwiss;
    }

    public void setSaveBetaName(String saveBetaName) {
        this.saveBetaName = saveBetaName;
    }

    @Override
    public String getSaveBetaName() {
        return this.saveBetaName;
    }
}
