/**
 * 
 */
package cern.accsoft.steering.jmad.util.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;

/**
 * This is a utility class that uses the XStream mechanism to clone objects. Custom converters can be provided in order
 * to control behaviour of the cloning.
 * 
 * @author kfuchsbe
 */
public final class CloneUtil {

    private CloneUtil() {
        /* Only static methods */
    }

    @SuppressWarnings("unchecked")
    public static <T> T clone(Class<T> clazz, T object) {
        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);
        xStream.processAnnotations(clazz);
        return (T) xStream.fromXML(xStream.toXML(object));
    }

    @SuppressWarnings("unchecked")
    public static <T> T clone(Class<T> clazz, T object, Converter converter) {
        XStream xStream = new XStream();
        xStream.registerConverter(converter);
        xStream.autodetectAnnotations(true);
        xStream.processAnnotations(clazz);
        return (T) xStream.fromXML(xStream.toXML(object));

    }

}
