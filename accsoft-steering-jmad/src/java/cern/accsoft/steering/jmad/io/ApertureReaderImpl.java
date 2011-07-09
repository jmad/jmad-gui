/**
 * 
 */
package cern.accsoft.steering.jmad.io;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.aperture.AperType;
import cern.accsoft.steering.jmad.domain.aperture.Aperture;
import cern.accsoft.steering.jmad.domain.aperture.ApertureImpl;
import cern.accsoft.steering.jmad.domain.aperture.ApertureSlice;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResult;
import cern.accsoft.steering.jmad.domain.var.enums.MadxApertureVariable;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;

/*
 * XXX a lot!
 * 
 * XXX (Using constants/enums instead of names ) This is still only a rough adaption of the copied version from the
 * online model
 * 
 * XXX detect for copy paste! (update/load!?)
 */
/**
 * this is the implementation of the loader for aperture data
 * 
 * @author kfuchsbe
 */
public class ApertureReaderImpl implements ApertureReader {

    /** The logger for the class */
    private static final Logger LOGGER = Logger.getLogger(ApertureImpl.class);

    private static final double MARKER_VALUE = 9.0;

    /** allowed PositionError of 100 um **/
    private static final double POSITION_ERROR = 1e-4;

    /** the factor on position to identify beam 4 */
    private static final double POSITION_FACTOR_BEAM4 = -1e3;

    /** the default value for aperture */
    /* XXX GM please check */
    private static final double DEFAULT_APERTURE_VALUE = 0.05;

    @Override
    public Aperture readIndex(File file) {
        return loadApertureIndexFile(file);
    }

    @Override
    public void readValues(File file, Aperture aperture) {
        loadAperturePartFile(file, aperture);
    }

    /**
     * Read in the correct Name/Position relation from an ApertureIndexFile
     * <p>
     * which also contains APERTYPE and the ON_AP/ON_ELEM flags... such that we initialize the Sequence of
     * ApertureElements with the right positions according to the machine we are using...
     * <p>
     * All VacuumMarkers are skipped --> ElementNames starts with V
     * 
     * @param indexFile The file from which to read the aperture-index
     * @return an object containing all the aperture information
     */
    public Aperture loadApertureIndexFile(File indexFile) {
        TfsFileParser parser = new TfsFileParser(indexFile);
        try {
            parser.parse();
        } catch (TfsFileParserException e) {
            LOGGER.error("Error while loading aperture index file '" + indexFile + "'", e);
            return null;
        }
        TfsResult result = parser.getResult();

        LOGGER
                .info("Loading ApertureModel with ApertureIndex File --> need to add Aperture Values by Add -> Aperture Part!!!");
        return this.loadApertureInformation(result, false);

    }

    /**
     * Fill the Aperture Model with Element Positions, ApertureTypes and filter non-Aperture Elements as well as Markers
     * <p>
     * depending on the s-position all Slices are added at the right position to the model... and the model is updated
     * accordingly
     * 
     * @param tfsResult the {@link TfsResult} to read from
     * @param readAperVals if <code>true</code> read also ApertureValues to the model
     * @return an object containing the aperture information
     */
    private Aperture loadApertureInformation(TfsResult tfsResult, boolean readAperVals) {

        ApertureImpl aperture = new ApertureImpl();

        List<String> names = tfsResult.getStringData(MadxTwissVariable.NAME);
        List<Double> sValues = tfsResult.getDoubleData(MadxTwissVariable.S);

        List<Double> isAp = tfsResult.getDoubleData(MadxApertureVariable.ON_AP);
        List<Double> isElem = tfsResult.getDoubleData(MadxApertureVariable.ON_ELEM);

        List<Double> aper1 = tfsResult.getDoubleData(MadxApertureVariable.APER_1);
        List<Double> aper2 = tfsResult.getDoubleData(MadxApertureVariable.APER_2);
        List<Double> aper3 = tfsResult.getDoubleData(MadxApertureVariable.APER_3);
        List<Double> aper4 = tfsResult.getDoubleData(MadxApertureVariable.APER_4);

        List<String> aperTypeStrings = tfsResult.getStringData(MadxApertureVariable.APERTYPE);

        if (names == null || sValues == null || aperTypeStrings == null || isAp == null || isElem == null) {
            LOGGER.error("Missing Columns in ApertureIndex Aperture Index should contain: "
                    + "[name, s, apertype, on_ap, on_elem]");
        }

        List<Double> xValues = null;
        List<Double> yValues = null;

        if (readAperVals) {
            xValues = tfsResult.getDoubleData(MadxTwissVariable.X);
            yValues = tfsResult.getDoubleData(MadxTwissVariable.Y);
        }

        ApertureSlice newSlice = null;
        for (int i = 0; i < sValues.size(); i++) {

            // Filter all VacuumMarkers
            if (names.get(i).toUpperCase().startsWith("V")) {
                continue;
            }

            // it is a marker... we are interested in ;) -->
            // DispersionSuppressors and such
            if ((isAp.get(i) < 0)
                    && (isElem.get(i) == 1)
                    || ((aper1.get(i) >= MARKER_VALUE) || (aper2.get(i) >= MARKER_VALUE)
                            || (aper3.get(i) >= MARKER_VALUE) || (aper4.get(i) >= MARKER_VALUE))
                    || names.get(i).toUpperCase().contains(".DS.")) {
                aperture.addMarker(names.get(i), sValues.get(i));
            }

            // Either its a Drift or no ApertureInformation provided
            if ((isAp.get(i) < 1) || (isElem.get(i) < 1)) {
                continue;
            }

            if (readAperVals) { // --> only create ApertureIndex

                if ((aper1.get(i) >= MARKER_VALUE) || (aper2.get(i) >= MARKER_VALUE) || (aper3.get(i) >= MARKER_VALUE)
                        || (aper4.get(i) >= MARKER_VALUE)) {

                    // Found a marker --> put in Markers List...
                    // addMarker(names[i], s[i]);

                    // put Marker into Sequence for update ApertureValues as
                    // Reference point if not full apertures are loaded but only
                    // parts...
                    if (i == 0) {
                        // first Element in Sequence --> create dummyAperture
                        newSlice = new ApertureSlice(AperType.valueOf(aperTypeStrings.get(i)), MARKER_VALUE,
                                MARKER_VALUE, MARKER_VALUE, MARKER_VALUE, sValues.get(i));
                    } else {
                        // create new Slice with Values of the previous Aperture
                        newSlice = new ApertureSlice(newSlice.getType(), newSlice.getAper1(), newSlice.getAper2(),
                                newSlice.getAper3(), newSlice.getAper4(), sValues.get(i));
                    }

                } else {
                    newSlice = new ApertureSlice(AperType.valueOf(aperTypeStrings.get(i)), aper1.get(i), aper2.get(i),
                            aper3.get(i), aper4.get(i), sValues.get(i));
                }

                newSlice.setX(xValues.get(i));
                newSlice.setY(yValues.get(i));
            } else {
                /* only read aper index */
                newSlice = new ApertureSlice(AperType.valueOf(aperTypeStrings.get(i)), sValues.get(i));
            }

            aperture.addSlice(newSlice);
        }
        LOGGER.info("ApertureIndex loaded...");
        return aperture;
    }

    /**
     * Read ApertureValues to the already defined MachineApertureSequence
     * 
     * @param read the {@link TfsResult} to read from
     * @param aperture the aperture data-object to update
     */
    private void updateApertureValues(TfsResult read, ApertureImpl aperture) {

        boolean isBeam4 = false;

        List<String> names = read.getStringData("NAME");
        List<Double> sValues = read.getDoubleData("S");

        List<Double> aper1 = read.getDoubleData("APER_1");
        List<Double> aper2 = read.getDoubleData("APER_2");
        List<Double> aper3 = read.getDoubleData("APER_3");
        List<Double> aper4 = read.getDoubleData("APER_4");
        List<Double> xValues = read.getDoubleData("X");
        List<Double> yValues = read.getDoubleData("Y");

        int index;
        int dataReadStart = -1;
        int dataReadEnd = -1;
        double modelUpdateStartPos = -1.0;

        double modelStart = -1.0;
        double modelDiff = -1.0;
        double dataStart = -1.0;
        double dataDiff = -1.0;
        String actElement = null;
        // Check for ordering of Markers to detect a reverse order (B2 <--> B4)
        for (index = 0; index < names.size(); index++) {

            // Filter all VacuumMarkers
            if (names.get(index).toUpperCase().startsWith("V")) {
                continue;
            }

            actElement = names.get(index).toUpperCase();
            Double markerPos = aperture.getMarkerPos(actElement);
            if (markerPos == null) {
                if ((aper1.get(index) >= MARKER_VALUE) || (aper2.get(index) >= MARKER_VALUE)
                        || (aper3.get(index) >= MARKER_VALUE) || (aper4.get(index) >= MARKER_VALUE)) {
                    LOGGER.warn("Load ApertureValues --> MARKER [" + actElement + "] not loaded to Model...");

                    // Track missed markers...
                    aperture.getMissedMarkers().add(actElement);
                }
            } else {
                if (modelStart < 0.0) {
                    modelStart = markerPos;
                    dataStart = sValues.get(index);

                    // Save the DataIndex of the first DataMarker
                    // which was found in the Model
                    dataReadStart = index;

                    // and the MarkerPosition of the model
                    // where to start updating too
                    modelUpdateStartPos = modelStart;
                } else {
                    modelDiff = -modelStart;
                    modelStart = markerPos;
                    modelDiff += (modelStart);

                    dataDiff = -dataStart;
                    dataStart = sValues.get(index);
                    dataDiff += dataStart;

                    // Save Index of the last DataMarker
                    // which was found in the Model
                    dataReadEnd = index;

                    if (modelDiff < (POSITION_FACTOR_BEAM4 * POSITION_ERROR)) {

                        // more then 0.1m negative PositionDifference between
                        // identical Markers --> assuming BEAM4 (B2 Sequence
                        // starting from the opposite end)

                        isBeam4 = true;
                        modelDiff *= -1;

                    } else {
                        isBeam4 = false;
                    }

                    if (Math.abs(modelDiff - dataDiff) > POSITION_ERROR) {
                        LOGGER.warn("Model to NewData Distance Error between two Markers = "
                                + Math.abs(modelDiff - dataDiff) + " @MARKER [" + actElement + "]");
                    }
                }
            }
        }

        if (isBeam4) {
            LOGGER.info("Detected BEAM4 Sequence was loaded as ApertureIndex --> nomX = -x "
                    + "and ApertureValues are read in reverse Order");

            // Start from the index of the last
            // Marker found in the Model and B2 Sequence
            index = dataReadEnd;

            // swap dataReadEnd and dataReadStart for ease of reading
            dataReadEnd = dataReadStart;
        } else {
            index = dataReadStart;
        }

        ApertureSlice lastSlice = null;
        boolean reading = false;
        for (ApertureSlice actSlice : aperture.getApertureSlices()) {

            if (actSlice.getS() == modelUpdateStartPos) {
                reading = true;
            } else if (actSlice.getS() < modelUpdateStartPos) {
                lastSlice = actSlice; // keep the lastSlice info...
            }

            if (reading) {

                if (((index > dataReadEnd) && !isBeam4) || ((index < dataReadEnd) && isBeam4)) {
                    break;
                }

                System.out.println(names.get(index));
                // Filter all VacuumMarkers
                while (names.get(index).toUpperCase().startsWith("V")) {
                    // move Index
                    if (isBeam4) {
                        index--;
                    } else {
                        index++;
                    }
                }

                // NO Vacuum Marker so --> Set nominal Trajectory
                if (isBeam4) {
                    actSlice.setX(-xValues.get(index));
                } else {
                    actSlice.setX(xValues.get(index));
                }
                actSlice.setY(yValues.get(index));

                // Check if Marker
                if ((aper1.get(index) >= MARKER_VALUE) || (aper2.get(index) >= MARKER_VALUE)
                        || (aper3.get(index) >= MARKER_VALUE) || (aper4.get(index) >= MARKER_VALUE)) {

                    // Found a marker --> put in Markers List...
                    aperture.addMarker(names.get(index).toUpperCase(), actSlice.getS());

                    // only update ApertureSlice if Marker is already known...
                    // otherwise iterator is screwed up...
                    if (!aperture.getMissedMarkers().contains(names.get(index))) {
                        if (lastSlice == null) {
                            actSlice.setApertureValues(DEFAULT_APERTURE_VALUE, DEFAULT_APERTURE_VALUE,
                                    DEFAULT_APERTURE_VALUE, DEFAULT_APERTURE_VALUE);
                        } else {
                            actSlice.setApertureValues(lastSlice.getAper1(), lastSlice.getAper2(),
                                    lastSlice.getAper3(), lastSlice.getAper4());
                        }
                    }

                } else {
                    actSlice.setApertureValues(aper1.get(index), aper2.get(index), aper3.get(index), aper4.get(index));
                }

                System.out.println("Element [" + names.get(index) + "] @ " + actSlice.getS() + "with idx = " + index);

                // move Index
                if (isBeam4) {
                    index--;
                } else {
                    index++;
                }

                // keep reference to last Slice for Markers
                lastSlice = actSlice;
            }
        }

    }

    /**
     * Load in (full) ApertureInformation from the specified TFS-File
     * <p>
     * If ApertureIndex was read before, only ApertureInformation are updated for the specified Sequence, otherwise
     * ApertureIndex is generated if TFS-File contains ALL Information (apertype, on_ap, on_elem, name, s)
     * 
     * @param file the file from which to load the information
     */
    public void loadApertureFile(File file) {
        TfsFileParser parser = new TfsFileParser(file);
        try {
            parser.parse();
        } catch (TfsFileParserException e) {
            LOGGER.error("Error while loading file '" + file.getAbsolutePath() + "'", e);
            return;
        }
        TfsResult read = parser.getResult();
        List<String> keys = read.getKeys();

        if (!keys.contains("NAME") || !keys.contains("S") || !keys.contains("APER_1") || !keys.contains("APER_2")
                || !keys.contains("APER_3") || !keys.contains("APER_4") || !keys.contains("APERTYPE")
                || !keys.contains("ON_AP") || !keys.contains("ON_ELEM")) {

            LOGGER.error("Missing Columns in Aperture File: [" + file.getAbsolutePath()
                    + "]\nFull Aperture Table should contain: "
                    + "[name, s, apertype, on_ap, on_elem, aper_1, aper_2, aper_3, aper_4]"
                    + " or you need to load an appropriate ApertureIndex file before!!");
            return;

        }

        LOGGER.info("Updating ApertureModel by loading ApertureSlices from provided full ApertureFile");

        // Load ApertureIndex and Update ApertureInformations
        this.loadApertureInformation(read, true);
    }

    /**
     * loads the aperture information from the given file into the data object.
     * 
     * @param file The file from which to load the data
     * @param aperture the object to update with the information
     */
    public void loadAperturePartFile(File file, Aperture aperture) {

        if (!(aperture instanceof ApertureImpl)) {
            LOGGER.warn("aperture object is not a instance of '" + ApertureImpl.class.getCanonicalName()
                    + "'. cannot add data to it.");
            return;
        }

        TfsFileParser parser = new TfsFileParser(file);
        try {
            parser.parse();
        } catch (TfsFileParserException e) {
            LOGGER.error("Error while loading file '" + file.getAbsolutePath() + "'", e);
            return;
        }
        TfsResult read = parser.getResult();
        List<String> keys = read.getKeys();

        if (!keys.contains("NAME") || !keys.contains("S") || !keys.contains("APER_1") || !keys.contains("APER_2")
                || !keys.contains("APER_3") || !keys.contains("APER_4")) {
            LOGGER.error("Missing Columns in AperturePart File: [" + file.getAbsolutePath()
                    + "]\nAperture Table should contain: " + "[name, s, aper_1, aper_2, aper_3, aper_4]");
            return;
        }

        if (!((ApertureImpl) aperture).isIndexLoaded()) {
            LOGGER.error("You need to load an appropriate ApertureIndex file before loading Aperture Parts!!");
            return;

        }
        LOGGER.info("Updating ApertureModel by loading ApertureValues APER_[1..4] and extracting MarkerInformations");

        // Update new ApertureValues to existing ApertureIndex
        this.updateApertureValues(read, (ApertureImpl) aperture);
    }

}
