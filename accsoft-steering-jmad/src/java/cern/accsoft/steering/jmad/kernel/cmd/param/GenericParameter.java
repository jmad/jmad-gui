package cern.accsoft.steering.jmad.kernel.cmd.param;

import cern.accsoft.steering.jmad.domain.types.MadxValue;

public class GenericParameter<T> extends AbstractParameter {
    private String name;
    private T value;

    private boolean useValueQuotes = false;

    public GenericParameter(String name) {
        this(name, null, false);
    }

    public GenericParameter(String name, T value) {
        this(name, value, false);
    }

    public GenericParameter(String name, T value, boolean useValueQuotes) {
        this.name = name;
        this.value = value;
        this.useValueQuotes = useValueQuotes;
    }

    public boolean isSet() {
        if ((value != null) && (value.getClass().equals(Boolean.class))) {
            return (Boolean) value;
        } else {
            return (value != null);
        }
    }

    @Override
    public String compose() {
        String valueString;

        if (value.getClass().equals(Boolean.class)) {
            if ((Boolean) value) {
                return name;
            } else {
                return "";
            }
        } else if (value instanceof MadxValue) {
            return name + "=" + ((MadxValue) value).getMadxString();
        } else {
            if (useValueQuotes) {
                valueString = "\"" + value.toString() + "\"";
            } else {
                valueString = value.toString();
            }
            return name + "=" + valueString;
        }
    }

}
