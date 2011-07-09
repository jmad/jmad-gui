package cern.accsoft.steering.jmad.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestFile {
    private File file = null;

    public TestFile(String fileName) {
        this.file = new File(fileName);
    }

    public void write(String text) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(text);
            bufferedWriter.flush();
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getFile() {
        return file;
    }

    public void delete() {
        if ((file != null) && (!file.delete())) {
            ; /* ignore, may not exist */
        }
    }
}
