package cern.accsoft.steering.jmad.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents all possible Types of Values in the file. It also takes care of the corresponding tags.
 * 
 * @author kfuchsbe
 */
public enum MadxVarType {
    STRING(String.class) {
        private final Matcher matcher = Pattern.compile("%[0-9]*s").matcher("");

        @Override
        public boolean match(String tag) {
            matcher.reset(tag);
            if (matcher.matches()) {
                return true;
            }
            return false;
        }
    },
    DOUBLE(Double.class) {
        @Override
        public boolean match(String tag) {
            /*
             * XXX currently integers (%hd) and integers (%d) are also mapped to doubles. If this will be changed in the
             * future than also TfsResult and TfsSummary need some refacturing.
             */
            return "%le".equals(tag) || "%hd".equals(tag) || "%d".equals(tag);
        }
    },
    UNKNOWN(null) {
        @Override
        public boolean match(String tag) {
            return false;
        }
    };

    private Class<?> valueClass;

    private MadxVarType(Class<?> valueClass) {
        this.valueClass = valueClass;
    }

    public abstract boolean match(String tag);

    public Class<?> getValueClass() {
        return this.valueClass;
    }

    /**
     * Determine the correct Value of VarType for a given tag (%s, %le)
     * 
     * @param tag the tag for which to get the VarType - Value.
     * @return The VarType corresponding to the given tag.
     */
    public static MadxVarType getVarType(String tag) {
        if (tag != null) {
            for (MadxVarType type : MadxVarType.values()) {
                if (type.match(tag)) {
                    return type;
                }
            }
        }
        return MadxVarType.UNKNOWN;
    }
}