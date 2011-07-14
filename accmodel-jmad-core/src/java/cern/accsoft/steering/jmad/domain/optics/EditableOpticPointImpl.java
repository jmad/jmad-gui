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
package cern.accsoft.steering.jmad.domain.optics;

import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;

/**
 * The general implementation of an optics point.
 * 
 * @author kfuchsbe
 */
public class EditableOpticPointImpl extends OpticPointImpl implements EditableOpticPoint {

    public EditableOpticPointImpl(String name) {
        super(name);
    }

    @Override
    public void setBetx(Double betx) {
        setValue(MadxTwissVariable.BETX, betx);
    }

    @Override
    public void setBety(Double bety) {
        setValue(MadxTwissVariable.BETY, bety);
    }

    @Override
    public void setMux(Double mux) {
        setValue(MadxTwissVariable.MUX, mux);
    }

    @Override
    public void setMuy(Double muy) {
        setValue(MadxTwissVariable.MUY, muy);
    }

    @Override
    /* short name to use the same as madx */
    public void setDx(Double dx) { // NOPMD by kaifox on 6/25/10 5:51 PM
        setValue(MadxTwissVariable.DX, dx);
    }

    @Override
    /* short name to use the same as madx */
    public void setDy(Double dy) { // NOPMD by kaifox on 6/25/10 5:51 PM
        setValue(MadxTwissVariable.DY, dy);
    }

    @Override
    public void setAlfx(Double alfx) {
        setValue(MadxTwissVariable.ALFX, alfx);
    }

    @Override
    public void setAlfy(Double alfy) {
        setValue(MadxTwissVariable.ALFY, alfy);
    }

    @Override
    public void setDdx(Double ddx) {
        setValue(MadxTwissVariable.DDX, ddx);
    }

    @Override
    public void setDpx(Double dpx) {
        setValue(MadxTwissVariable.DPX, dpx);
    }

    @Override
    public void setDpy(Double dpy) {
        setValue(MadxTwissVariable.DPY, dpy);
    }

    @Override
    public void setDdy(Double ddy) {
        setValue(MadxTwissVariable.DDY, ddy);
    }

    @Override
    public void setDdpx(Double ddpx) {
        setValue(MadxTwissVariable.DDPX, ddpx);
    }

    @Override
    public void setDdpy(Double ddpy) {
        setValue(MadxTwissVariable.DDPY, ddpy);
    }

    @Override
    /* short name to use the same as madx */
    public void setX(Double x) { // NOPMD by kaifox on 6/25/10 5:51 PM
        setValue(MadxTwissVariable.X, x);
    }

    @Override
    /* short name to use the same as madx */
    public void setPx(Double px) { // NOPMD by kaifox on 6/25/10 5:51 PM
        setValue(MadxTwissVariable.PX, px);
    }

    @Override
    /* PMD: short name to use the same as madx */
    public void setY(Double y) { // NOPMD by kaifox on 6/25/10 5:51 PM
        setValue(MadxTwissVariable.Y, y);
    }

    @Override
    /* PMD: short name to use the same as madx */
    public void setPy(Double py) { // NOPMD by kaifox on 10/6/10 6:19 PM
        setValue(MadxTwissVariable.PY, py);
    }

}
