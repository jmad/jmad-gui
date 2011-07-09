/**
 * 
 */
package cern.accsoft.steering.jmad.gui.panels;

import java.awt.BorderLayout;

import cern.accsoft.steering.jmad.domain.result.tfs.TfsResult;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsSummary;
import cern.accsoft.steering.jmad.domain.var.enums.MadxGlobalVariable;
import cern.accsoft.steering.util.gui.dv.chart.TuneDiagramChart;

/**
 * listens to the TfsResultManager and displays the tune-values in the tune diagram, if the result is updated.
 * 
 * @author kfuchsbe
 */
public class TuneDiagramPanel extends AbstractTfsDataSetManagerResultPanel {
    private static final long serialVersionUID = 1L;

    /** The special chart for the tune diagram */
    private TuneDiagramChart tuneDiagramChart;

    /**
     * init method called by spring
     */
    public void init() {
        initComponents();
    }

    /**
     * creates all the components
     */
    private void initComponents() {
        setLayout(new BorderLayout());
        this.tuneDiagramChart = new TuneDiagramChart(10);
        add(this.tuneDiagramChart, BorderLayout.CENTER);
    }

    @Override
    protected void update(TfsResult tfsResult) {
        TfsSummary tfsSummary = tfsResult.getSummary();
        
        double qX = tfsSummary.getDoubleValue(MadxGlobalVariable.Q1);
        double qY = tfsSummary.getDoubleValue(MadxGlobalVariable.Q2);

        this.tuneDiagramChart.setValues(qX, qY);
    }

}
