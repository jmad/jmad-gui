package cern.accsoft.steering.jmad.domain.result.tfs;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.JMadConstants;
import cern.accsoft.steering.jmad.domain.var.GlobalVariable;
import cern.accsoft.steering.jmad.util.MadxVarType;

public class TfsSummaryImpl implements TfsSummary {

    /** the logger for this class */
    private static final Logger LOGGER = Logger.getLogger(TfsSummaryImpl.class);

    /** All the values in their String representation */
    private final Map<String, String> stringValues = new HashMap<String, String>();

    /** The valuetypes for each key */
    private final Map<String, MadxVarType> valueTypes = new HashMap<String, MadxVarType>();

    /** The calculated double values for those variables who are of type double. */
    private final Map<String, Double> doubleValues = new HashMap<String, Double>();

    /**
     * adds a value to the summary
     * 
     * @param madxName the key of the value
     * @param value the value as string
     * @param varType the type of the value
     */
    public void addValue(String madxName, String value, MadxVarType varType) {
        String key = unifyKey(madxName);
        this.stringValues.put(key, value);
        this.valueTypes.put(key, varType);
    }

    /**
     * converts all the values that have VarType Double to double values, so that they can be retrieved quickly
     * afterwards.
     * 
     * @throws TfsResultException if the conversion fails
     */
    public void convert() throws TfsResultException {
        this.doubleValues.clear();
        for (String key : getKeys()) {
            if (MadxVarType.DOUBLE.equals(getVarType(key))) {
                String strValue = getStringValue(key);

                if (strValue == null) {
                    throw new TfsResultException("Data contains no value for key '" + key + "'.");
                }

                try {
                    this.doubleValues.put(unifyKey(key), Double.parseDouble(strValue));
                } catch (NumberFormatException e) {
                    throw new TfsResultException("Error while converting value '" + strValue + "' to Double", e);
                }
            }
        }
    }

    /**
     * ensures that all keys are the same case.
     * 
     * @param key the key to unify
     * @return the converted key.
     */
    private static final String unifyKey(String key) {
        return key.toUpperCase(JMadConstants.DEFAULT_LOCALE);
    }

    @Override
    public Double getDoubleValue(String key) {
        Double value = this.doubleValues.get(unifyKey(key));
        if (value == null) {
            LOGGER.warn("TwissSummary seems not to contain a double value for key '" + key + "'");
        }
        return value;
    }

    @Override
    public Double getDoubleValue(GlobalVariable variable) {
        return getDoubleValue(variable.getMadxName());
    }

    @Override
    public Collection<String> getKeys() {
        return this.stringValues.keySet();
    }

    @Override
    public String getStringValue(String key) {
        String value = this.stringValues.get(unifyKey(key));
        if (value == null) {
            LOGGER.warn("TwissSummary seems not to contain a string value for key '" + key + "'");
        }
        return value;
    }

    @Override
    public String getStringValue(GlobalVariable variable) {
        return getStringValue(variable.getMadxName());
    }

    @Override
    public MadxVarType getVarType(String key) {
        MadxVarType value = this.valueTypes.get(unifyKey(key));
        if (value == null) {
            LOGGER.warn("TwissSummary seems not to contain a value type for key '" + key + "'");
        }
        return value;
    }

    @Override
    public MadxVarType getVarType(GlobalVariable variable) {
        return getVarType(variable.getMadxName());
    }
}
