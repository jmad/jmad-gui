package cern.accsoft.steering.jmad.gui.manage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResult;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResultRequestImpl;
import cern.accsoft.steering.jmad.domain.var.MadxVariable;
import cern.accsoft.steering.jmad.domain.var.TwissVariable;
import cern.accsoft.steering.jmad.domain.var.Variable;
import cern.accsoft.steering.jmad.gui.data.TfsResultDataSet;
import cern.accsoft.steering.jmad.gui.data.TfsResultDataSet.TfsResultDataSetType;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.model.manage.JMadModelManagerAdapter;
import cern.accsoft.steering.jmad.model.manage.JMadModelManagerListener;

/**
 * creates DataSets for JMad-Results and stores the therefore required
 * Variables. So all the DataSets can then be refreshed very easily.
 * 
 * @author kfuchsbe
 */
public class TfsDataSetManager implements DataSetManager {
	private final static Logger logger = Logger
			.getLogger(TfsDataSetManager.class);

	/** the model - manager from which to get the model */
	private JMadModelManager modelManager = null;

	/** the listeners */
	private ArrayList<DataSetManagerListener> listeners = new ArrayList<DataSetManagerListener>();

	/** store all variables we require to query the model */
	private Map<String, TwissVariable> requiredVariables = new HashMap<String, TwissVariable>();

	/** store all created DataSets to be able to refresh them. */
	private ArrayList<TfsResultDataSet> dataSets = new ArrayList<TfsResultDataSet>();

	/** The manager which knows about (almost) all selectable variables */
	private AllVarManager allVarManager;

	/** the actual results */
	private TfsResult tfsResult = null;

	/** the reference result (if stored) */
	private TfsResult referenceTfsResult = null;

	private final JMadModelManagerListener modelManagerListener = new JMadModelManagerAdapter() {

		@Override
		public void changedActiveModel(JMadModel newModel) {
			resetReference();
		}
	};

	/**
	 * sets the modelManager from which to retrieve the actual model
	 * 
	 * @param modelManager
	 */
	public void setModelManager(JMadModelManager modelManager) {
		this.modelManager = modelManager;
		modelManager.addListener(this.modelManagerListener);
	}

	/**
	 * get the actual model
	 * 
	 * @return the model.
	 */
	private JMadModel getModel() {
		if (this.modelManager != null) {
			return this.modelManager.getActiveModel();
		} else {
			return null;
		}
	}

	/**
	 * creates new DataSets for jdataviewer and stores the required variables to
	 * be able to refresh the data efficiently.
	 * 
	 * @param xVar
	 * @param yVars
	 */
	public Map<Integer, List<TfsResultDataSet>> createDataSets(
			TwissVariable xVar, Map<Integer, Collection<TwissVariable>> yVars,
			TfsResultDataSetType type) {
		requiredVariables.put(xVar.getMadxName(), xVar);
		for (Integer yAxis : yVars.keySet()) {
			for (TwissVariable var : yVars.get(yAxis)) {
				requiredVariables.put(var.getMadxName(), var);
			}
		}

		/* make sure, that all variables are available. */
		refresh();

		Map<Integer, List<TfsResultDataSet>> newDataSets = new HashMap<Integer, List<TfsResultDataSet>>();
		for (Integer yAxis : yVars.keySet()) {
			Collection<TwissVariable> variables = yVars.get(yAxis);
			if (variables.size() <= 0) {
				continue;
			}
			List<TfsResultDataSet> dsList = new ArrayList<TfsResultDataSet>();
			newDataSets.put(yAxis, dsList);
			for (TwissVariable var : variables) {
				TfsResultDataSet dataSet = createDataSet(xVar, var, type, yAxis);
				dsList.add(dataSet);
				/* if it is an aperture, then add another one with factor -1 */
				if (var.isApertureVariable()) {
					dataSet = createDataSet(xVar, var, type, yAxis + 1);
					dataSet.setFactor(-1);
					dsList.add(dataSet);
				}
			}
		}
		return newDataSets;
	}

	/**
	 * creates one dataSet
	 * 
	 * @param xVar
	 * @param yVar
	 * @param type
	 * @param axis
	 * @return
	 */
	private TfsResultDataSet createDataSet(MadxVariable xVar,
			TwissVariable yVar, TfsResultDataSetType type, Integer axis) {
		String dsName = yVar.toString();
		dsName += " (" + "Y" + (axis + 1) + ")";
		TfsResultDataSet dataSet = new TfsResultDataSet(dsName, xVar, yVar,
				type, this.tfsResult, this.referenceTfsResult);
		dataSets.add(dataSet);
		return dataSet;
	}

	/**
	 * creates the given datasets and fires an event that they were created.
	 * 
	 * @param name
	 *            the name to be displayed for these datasets
	 * @param xVar
	 * @param yVars
	 * @param relative
	 */
	public void createDataSets(String name, TwissVariable xVar,
			Map<Integer, Collection<TwissVariable>> yVars,
			TfsResultDataSetType type) {
		Map<Integer, List<TfsResultDataSet>> dataSets = createDataSets(xVar,
				yVars, type);

		/* if no name is given, we construct one */
		if ((name == null) || (name.length() == 0)) {
			name = "";
			for (Integer yAxis : yVars.keySet()) {
				Collection<TwissVariable> vars = yVars.get(yAxis);
				if ((vars == null) || (vars.size() <= 0)) {
					continue;
				}
				for (Variable var : vars) {
					if (name.length() > 0) {
						name += ", ";
					}
					name += var.getName() + "(" + xVar.getName() + ")";
				}
			}
			name += " [" + type.toString() + "]";
		}

		String xLabel = xVar.toString();

		fireCreatedDataSets(name, xLabel, dataSets);
	}

	/**
	 * reads new values from model by twissing and sets the new result to all
	 * dataSets.
	 */
	public void refresh() {
		tfsResult = runTwiss(requiredVariables.values());

		if (tfsResult != null) {
			for (TfsResultDataSet dataSet : dataSets) {
				dataSet.setTfsResult(tfsResult);
			}
		}

		publishTfsResult();
	}

	/**
	 * notifies all the listeners that a new tfsResult is available
	 */
	private void publishTfsResult() {
		if (tfsResult != null) {
			for (DataSetManagerListener listener : this.listeners) {
				listener.twissCalculated(this.tfsResult);
			}
		}
	}

	/**
	 * runs a twiss and stores the result as reference for all datasets.
	 */
	public void setAsReference() {
		referenceTfsResult = runTwiss(getAllVarManager()
				.getAllSelectableVariables());

		if (referenceTfsResult != null) {
			for (TfsResultDataSet dataSet : dataSets) {
				dataSet.setReferenceTfsResult(referenceTfsResult);
			}
		}
	}

	/**
	 * reset the reference dataset
	 */
	public void resetReference() {
		this.referenceTfsResult = null;
		for (TfsResultDataSet dataSet : dataSets) {
			dataSet.setReferenceTfsResult(null);
		}
	}

	/**
	 * runs a twiss on the model and returns a Tfs-Result with all required
	 * Variables.
	 * 
	 * @return the Result with all variables
	 */
	private TfsResult runTwiss(Collection<TwissVariable> variables) {
		if (getModel() == null) {
			logger.warn("Model not set. Cannot refresh datasets!");
			return null;
		}

		TfsResultRequestImpl request = new TfsResultRequestImpl();
		request.addElementFilter(".*");
		request.addVariable(TfsResultDataSet.LABEL_VAR);
		for (TwissVariable var : variables) {
			request.addVariable(var);
		}

		TfsResult result = null;
		try {
			result = getModel().twiss(request);
		} catch (JMadModelException e) {
			logger.error("Error while running Twiss on model.", e);
		}

		return result;
	}

	/**
	 * notifies all listeners, that a new DataSet was created.
	 * 
	 * @param name
	 *            the name of the list of DataSets
	 * @param dataSets
	 *            the DataSets
	 */
	private void fireCreatedDataSets(String name, String xLabel,
			Map<Integer, List<TfsResultDataSet>> dataSets) {
		for (DataSetManagerListener listener : listeners) {
			listener.createdDataSets(name, xLabel, dataSets);
		}
	}

	@Override
	public void addListener(DataSetManagerListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public void removeListener(DataSetManagerListener listener) {
		this.listeners.remove(listener);
	}

	public void setAllVarManager(AllVarManager allVarManager) {
		this.allVarManager = allVarManager;
	}

	private AllVarManager getAllVarManager() {
		return allVarManager;
	}

}
