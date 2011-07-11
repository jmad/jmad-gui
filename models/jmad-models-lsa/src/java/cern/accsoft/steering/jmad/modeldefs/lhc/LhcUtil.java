package cern.accsoft.steering.jmad.modeldefs.lhc;

import cern.accsoft.steering.util.acc.BeamNumber;

/*
 * Copyright CERN 2010, All Rights Reserved.
 */

/**
 * Utility functions for the LHC.
 * 
 * @author muellerg
 */
public final class LhcUtil {

    /** The name of the range to load per default */
    public static final String DEFAULT_RANGE_NAME = "ALL";

    /** the name of the thick modeldefinition within JMad */
    public static final String NOMINAL_MODEL_DEFINITION_NAME = "LHC (LSA)";

    /** the name of the thin modeldefinition within JMad */
    public static final String THIN_MODEL_DEFINITION_NAME = "LHC (LSA) THIN";
    
    /** the name of the thin modeldefinition within JMad */
    public static final String ATS_MODEL_DEFINITION_NAME = "LHC ATS";
    
    /** The sequence name for beam 1 */
    public static final String SEQUENCE_NAME_BEAM1 = "lhcb1";

    /** The sequence name for beam 2 */
    public static final String SEQUENCE_NAME_BEAM2 = "lhcb2";

    /** the lsa device name for beam 1 */
    public static final String LSA_DEVICE_BEAM1 = "LHCBEAM1";
    /** the lsa device name for beam 2 */
    public static final String LSA_DEVICE_BEAM2 = "LHCBEAM2";

    /** the tag used in lsa twiss outputs for beam 1 */
    public static final String LSA_TWISS_BEAM1 = "B1";
    /** the tag used in lsa twiss outputs for beam 2 */
    public static final String LSA_TWISS_BEAM2 = "B2";

    /** the madx strength name ending for beam 1 */
    public static final String STRENGTH_ENDING_BEAM1 = "b1";
    /** the madx strength name ending for beam 2 */
    public static final String STRENGTH_ENDING_BEAM2 = "b2";

    private LhcUtil() {
        /** only static methods */
    }

    public static String getSequenceName(BeamNumber beamNumber) {
        if (BeamNumber.BEAM_1.equals(beamNumber)) {
            return SEQUENCE_NAME_BEAM1;
        } else if (BeamNumber.BEAM_2.equals(beamNumber)) {
            return SEQUENCE_NAME_BEAM2;
        }

        throw new IllegalArgumentException("Unknown LHC beam Number!");
    }

    public static BeamNumber getBeamNumber(String sequenceName) {
        String input = sequenceName.toLowerCase();
        if (SEQUENCE_NAME_BEAM1.equals(input)) {
            return BeamNumber.BEAM_1;
        } else if (SEQUENCE_NAME_BEAM2.equals(input)) {
            return BeamNumber.BEAM_2;
        }

        throw new IllegalArgumentException("Unknown LHC sequence name!");
    }

    /**
     * Retrieve the {@link BeamNumber} for the tag used for the two lhc beams in the twiss-output tables in LSA.
     * 
     * @param twissTag the tag to retrieve the {@link BeamNumber} for
     * @return the {@link BeamNumber}
     * @throws IllegalArgumentException in case a unknown tag is passed.
     */
    public static BeamNumber getBeamNumberFromLsaTwissTag(String twissTag) {
        if (LSA_TWISS_BEAM1.equals(twissTag)) {
            return BeamNumber.BEAM_1;
        } else if (LSA_TWISS_BEAM2.equals(twissTag)) {
            return BeamNumber.BEAM_2;
        }

        throw new IllegalArgumentException("Unknown LSA twiss outputs beam tag!");
    }
    
    /**
     * Retrieve the the lsa lhc beam tag used in the twiss output tables.
     * @param beamNumber the beamnumber to get the tag for
     * @return the tag, either 'B1' or 'B2'
     */
    public static String getLsaTwissTag(BeamNumber beamNumber) {
        if (BeamNumber.BEAM_1.equals(beamNumber)) {
            return LSA_TWISS_BEAM1;
        } else if (BeamNumber.BEAM_2.equals(beamNumber)) {
            return LSA_TWISS_BEAM2;
        }

        throw new IllegalArgumentException("Unknown LHC beam Number!");
    }

    /**
     * Retrieve the madx strength name ending for the lsa device name
     * 
     * @param deviceName the lhc beam device name (LHCBEAM is not supported!!)
     * @return the ending or throws an {@link IllegalArgumentException}
     */
    public static String getStrengthNameEnding(String deviceName) {
        String device = deviceName.toUpperCase();
        if (LSA_DEVICE_BEAM1.equals(device)) {
            return STRENGTH_ENDING_BEAM1;
        } else if (LSA_DEVICE_BEAM2.equals(device)) {
            return STRENGTH_ENDING_BEAM2;
        }

        throw new IllegalArgumentException("Unknown LHC beam device name!");
    }

    /**
     * Retrieve the {@link BeamNumber} for the lsa device name
     * 
     * @param deviceName the lhc beam device name (LHCBEAM is not supported!!)
     * @return the {@link BeamNumber} or throw an {@link IllegalArgumentException}
     */
    public static BeamNumber getBeamNumberForDeviceName(String deviceName) {
        String device = deviceName.toUpperCase();
        if (LSA_DEVICE_BEAM1.equals(device)) {
            return BeamNumber.BEAM_1;
        } else if (LSA_DEVICE_BEAM2.equals(device)) {
            return BeamNumber.BEAM_2;
        }

        throw new IllegalArgumentException("Unknown LHC beam device name!");
    }
}
