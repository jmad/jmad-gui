package cern.accsoft.steering.jmad.util.xml;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.thoughtworks.xstream.XStream;

public abstract class GenericXStreamService<T> implements PersistenceService<T> {

    /** the original xstream xml-converter (singleton) */
    private final XStream xStream = new XStream();

	@Override
    public synchronized File save(T object, File xmlFile) throws PersistenceServiceException {
        File file = ensureXmlFileExtension(xmlFile);

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(xStream.toXML(object));
            writer.close();
        } catch (Exception ex) {
            throw new PersistenceServiceException("Error writing Object [" + object.toString() + "] of Class ["
                    + object.getClass().getCanonicalName() + "] to XmlFile [" + file.getAbsolutePath() + "]", ex);
        }

        return file;
    }

    /**
     * adds the correct extension for jmad-model xm files to the file name if not already there.
     * 
     * @param file the file which shall be a correct jmad xml file
     * @return the file
     */
    private File ensureXmlFileExtension(File file) {
        if (isXmlFileName(file.getName())) {
            return file;
        } else {
            return new File(file.getAbsolutePath() + getFileExtension());
        }
    }

    /**
     * determines if the given name is a correct xml file name
     * 
     * @param fileName the file name to check
     * @return true if it is an xml file name, false if not
     */
    private boolean isXmlFileName(String fileName) {
        return fileName.toLowerCase().endsWith(getFileExtension());
    }

    @Override
    public void save(T object, OutputStream outStream) throws PersistenceServiceException {
        try {
            OutputStreamWriter writer = new OutputStreamWriter(outStream);
            writer.write(xStream.toXML(object));
            writer.flush();
        } catch (IOException e) {
            throw new PersistenceServiceException("Error writing Object [" + object.toString() + "] of Class ["
                    + object.getClass().getCanonicalName() + "] to output stream.", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public synchronized T load(File xmlFile) throws PersistenceServiceException {

        T retVal = null;
        try {
            retVal = (T) xStream.fromXML(new FileReader(xmlFile));
        } catch (Exception ex) {
            throw new PersistenceServiceException("Error loading Object from XmlFile [" + xmlFile.getAbsolutePath()
                    + "]", ex);
        }

        return retVal;
    }

    @Override
    @SuppressWarnings("unchecked")
    public synchronized T load(InputStream inputStream) throws PersistenceServiceException {
        T retVal = null;
        try {
            retVal = (T) xStream.fromXML(new InputStreamReader(inputStream));
        } catch (Exception ex) {
            throw new PersistenceServiceException("Error loading Object from Xml stream", ex);
        }
        return retVal;
    }

    @Override
    @SuppressWarnings("unchecked")
    public synchronized T clone(T object) throws PersistenceServiceException {

        T retVal = null;
        try {
            retVal = (T) xStream.fromXML(xStream.toXML(object));
        } catch (Exception ex) {

            String className = object.getClass().getCanonicalName();

            throw new PersistenceServiceException("Error cloning Object of Class [" + className + "]", ex);
        }

        return retVal;
    }

    protected XStream getXStream() {
        return xStream;
    }

}
