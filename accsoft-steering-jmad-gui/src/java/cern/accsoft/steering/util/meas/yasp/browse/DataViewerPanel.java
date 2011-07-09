/**
 * 
 */
package cern.accsoft.steering.util.meas.yasp.browse;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import cern.accsoft.steering.util.acc.BeamNumber;
import cern.accsoft.steering.util.gui.dv.ds.AbstractJmadDataSet;
import cern.accsoft.steering.util.gui.dv.ds.Aloha2DChart;
import cern.accsoft.steering.util.gui.dv.ds.ColorConstants;
import cern.accsoft.steering.util.gui.dv.ds.ValidityDataSet;
import cern.accsoft.steering.util.gui.dv.ds.Aloha2DChart.ChartRendererRole;
import cern.accsoft.steering.util.meas.data.Plane;
import cern.accsoft.steering.util.meas.data.yasp.CorrectorValue;
import cern.accsoft.steering.util.meas.data.yasp.MonitorValue;
import cern.accsoft.steering.util.meas.data.yasp.ReadingData;
import cern.accsoft.steering.util.meas.read.ReaderException;
import cern.accsoft.steering.util.meas.read.ReadingDataReader;
import cern.accsoft.steering.util.meas.read.yasp.YaspFileReader;
import cern.jdve.data.DataSet;
import cern.jdve.data.DefaultDataSource;
import cern.jdve.viewer.DVView;
import cern.jdve.viewer.DataView;
import cern.jdve.viewer.DataViewer;

/**
 * The panel to display the yasp-traj data
 * 
 * @author kfuchsbe
 */
public class DataViewerPanel extends JPanel {
    private static final long serialVersionUID = -8543734363603875904L;

    /** the logger for this class */
    private final static Logger logger = Logger.getLogger(DataViewerPanel.class);

    private ReadingDataReader reader = new YaspFileReader();

    /**
     * maps to store the datasets
     */
    private Map<String, MonitorValueDataSet> monitorValueDataSets = new HashMap<String, MonitorValueDataSet>();
    private Map<String, CorrectorValuesDataSet> correctorValueDataSets = new HashMap<String, CorrectorValuesDataSet>();

    /**
     * the default constructor, which initializes all the components
     */
    public DataViewerPanel() {
        initComponenets();
    }

    private void initComponenets() {

        for (Plane plane : Plane.values()) {
            for (BeamNumber beamNumber : BeamNumber.values()) {
                this.monitorValueDataSets
                        .put(createKey(plane, beamNumber),
                                new MonitorValueDataSet(beamNumber.toString() + ", " + plane.toString()
                                        + " - monitor readings"));
                this.correctorValueDataSets.put(createKey(plane, beamNumber),
                        new CorrectorValuesDataSet(beamNumber.toString() + ", " + plane.toString()
                                + " - corrector kicks"));
            }
        }

        setLayout(new BorderLayout());

        /*
         * The DataViewer
         */
        DataViewer dataViewer = new DataViewer();
        dataViewer.setPreferredSize(new Dimension(500, 500));
        add(dataViewer, BorderLayout.CENTER);

        /*
         * the views
         */
        DVView view1 = new DVView("Beam 1");
        view1.setLayout(DVView.VERTICAL_LAYOUT);
        dataViewer.addView(view1);

        DVView view2 = new DVView("Beam 2");
        view2.setLayout(DVView.VERTICAL_LAYOUT);
        dataViewer.addView(view2);

        /*
         * the charts for beam 1
         */

        for (Plane plane : Plane.values()) {
            Aloha2DChart chart = new Aloha2DChart();
            DataSet dataSet = this.monitorValueDataSets.get(createKey(plane, BeamNumber.BEAM_1));
            chart.setRenderDataSource(ChartRendererRole.MEAS_DATA, new DefaultDataSource(dataSet));
            chart.getArea().setBackground(ColorConstants.CHARTBG_BEAM_1);
            view1.addDataView(new DataView(chart));

            chart = new Aloha2DChart();
            dataSet = this.correctorValueDataSets.get(createKey(plane, BeamNumber.BEAM_1));
            chart.setRenderDataSource(ChartRendererRole.MEAS_DATA, new DefaultDataSource(dataSet));
            chart.getArea().setBackground(ColorConstants.CHARTBG_BEAM_1);
            view1.addDataView(new DataView(chart));
        }

        /*
         * and for beam 2
         */
        for (Plane plane : Plane.values()) {
            Aloha2DChart chart = new Aloha2DChart();
            DataSet dataSet = this.monitorValueDataSets.get(createKey(plane, BeamNumber.BEAM_2));
            chart.setRenderDataSource(ChartRendererRole.MEAS_DATA, new DefaultDataSource(dataSet));
            chart.getArea().setBackground(ColorConstants.CHARTBG_BEAM_2);
            view2.addDataView(new DataView(chart));

            chart = new Aloha2DChart();
            dataSet = this.correctorValueDataSets.get(createKey(plane, BeamNumber.BEAM_2));
            chart.setRenderDataSource(ChartRendererRole.MEAS_DATA, new DefaultDataSource(dataSet));
            chart.getArea().setBackground(ColorConstants.CHARTBG_BEAM_2);
            view2.addDataView(new DataView(chart));
        }

    }

    private String createKey(Plane plane, BeamNumber beamNumber) {
        return plane.toString() + "-" + beamNumber.toString();
    }

    public void setYaspFile(File file) {
        ReadingData readingData = null;
        try {
            readingData = reader.read(file, null);
        } catch (ReaderException e) {
            logger.warn("Error while reading file '" + file.getAbsolutePath() + "'. Maybe it is not a yasp file?", e);
        }

        if (readingData != null) {
            for (Plane plane : Plane.values()) {
                for (BeamNumber beamNumber : BeamNumber.values()) {
                    MonitorValueDataSet monitorValueDataSet = this.monitorValueDataSets
                            .get(createKey(plane, beamNumber));
                    if (monitorValueDataSet != null) {
                        monitorValueDataSet.setMonitorValues(readingData.getMonitorValues(plane, beamNumber));
                    } else {
                        logger.warn("no MonitorValueDataset for plane '" + plane.toString() + "' and BeamNumber '"
                                + beamNumber.toString() + "'.");
                    }

                    CorrectorValuesDataSet correctorValuesDataSet = this.correctorValueDataSets.get(createKey(plane,
                            beamNumber));
                    if (correctorValuesDataSet != null) {
                        correctorValuesDataSet.setCorrectorValues(readingData.getCorrectorValues(plane, beamNumber));
                    } else {
                        logger.warn("no CorrectorValueDataset for plane '" + plane.toString() + "' and BeamNumber '"
                                + beamNumber.toString() + "'.");
                    }
                }
            }
        }
    }

    /**
     * the dataset for monitor-values
     * 
     * @author kfuchsbe
     */
    private class MonitorValueDataSet extends AbstractJmadDataSet implements ValidityDataSet {

        /** the data to display */
        private List<MonitorValue> monitorValues = new ArrayList<MonitorValue>();

        protected MonitorValueDataSet(String name) {
            super(name);
        }

        @Override
        public int getDataCount() {
            return monitorValues.size();
        }

        @Override
        public double getY(int index) {
            return monitorValues.get(index).getBeamPosition();
        }

        @Override
        public boolean getValidity(int index) {
            return monitorValues.get(index).isOk();
        }

        @Override
        public boolean hasValidityInformation() {
            return true;
        }

        @Override
        public String getDataLabel(int index) {
            return this.monitorValues.get(index).getName();
        }

        private void setMonitorValues(List<MonitorValue> monitorValues) {
            this.monitorValues = monitorValues;
            refresh();
        }
    }

    /**
     * the dataset for corrector-values
     * 
     * @author kfuchsbe
     */
    private class CorrectorValuesDataSet extends AbstractJmadDataSet {

        /** the data to display */
        private List<CorrectorValue> correctorValues = new ArrayList<CorrectorValue>();

        protected CorrectorValuesDataSet(String name) {
            super(name);
        }

        @Override
        public int getDataCount() {
            return this.correctorValues.size();
        }

        @Override
        public double getY(int index) {
            return this.correctorValues.get(index).kick;
        }

        private void setCorrectorValues(List<CorrectorValue> correctorValues) {
            this.correctorValues = correctorValues;
            refresh();
        }

        @Override
        public String getDataLabel(int index) {
            return this.correctorValues.get(index).getName();
        }

    }

}
