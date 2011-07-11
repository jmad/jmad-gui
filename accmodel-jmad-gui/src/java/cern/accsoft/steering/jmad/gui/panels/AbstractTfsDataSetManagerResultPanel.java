package cern.accsoft.steering.jmad.gui.panels;

import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import cern.accsoft.steering.jmad.domain.result.tfs.TfsResult;
import cern.accsoft.steering.jmad.gui.data.TfsResultDataSet;
import cern.accsoft.steering.jmad.gui.manage.DataSetManager;
import cern.accsoft.steering.jmad.gui.manage.DataSetManagerListener;

public abstract class AbstractTfsDataSetManagerResultPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /**
     * listens for updates and triggers a refresh
     */
    private DataSetManagerListener dataSetManagerListener = new DataSetManagerListener() {

        @Override
        public void twissCalculated(TfsResult tfsResult) {
            update(tfsResult);
        }

        @Override
        public void createdDataSets(String name, String label, Map<Integer, List<TfsResultDataSet>> dataSets) {
            /* nothing to do here */
        }
    };

    public void setDataSetManager(DataSetManager dataSetManager) {
        if (dataSetManager != null) {
            dataSetManager.addListener(this.dataSetManagerListener);
        }
    }

    protected abstract void update(TfsResult tfsResult);

}