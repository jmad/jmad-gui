package cern.accsoft.steering.jmad.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MadStatementParser {

    public static List<String> parse(File file) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            String[] parts;
            int comment = 0;
            StringBuffer text = new StringBuffer();
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("(!|//).*", "");
                if (line.contains("/*")) {
                    parts = line.split("\\/*");
                    if (parts.length > 0)
                        text.append(parts[0]);
                    comment += 1;
                }
                if (line.contains("*/")) {
                    parts = line.split("\\*/");
                    if (parts.length > 1)
                        text.append(parts[1]);
                    comment -= 1;
                } else if (comment == 0) {
                    text.append(line);
                }
            }
            line = text.toString().replaceAll("\\s+", "").toLowerCase();
            StringTokenizer st = new StringTokenizer(line, ";", false);
            while (st.hasMoreTokens()) {
                String tk = st.nextToken() + ';';
                lines.add(tk);
            }
        } catch (IOException e) {
            throw new IOException("Error while parsing MadX-Strength file '" + file.getAbsolutePath() + "'.", e);
        }
        return lines;
    }
}
