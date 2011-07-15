// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

package cern.accsoft.steering.util.gui.script;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class JConsole extends JTextPane {
    private static final long serialVersionUID = 1L;

    private boolean output_able;

    private SimpleAttributeSet out_att;
    private SimpleAttributeSet err_att;

    private ConsoleOutputStream out;
    private ConsoleOutputStream err;

    private StyledDocument text;

    public JConsole() {
        super();
        setEditable(false);
        setBackground(Color.BLACK);

        out_att = new SimpleAttributeSet();
        StyleConstants.setForeground(out_att, Color.GREEN);
        err_att = new SimpleAttributeSet();
        StyleConstants.setForeground(err_att, Color.RED);

        text = new DefaultStyledDocument();
        setDocument(text);

        out = new ConsoleOutputStream();
        err = new ConsoleOutputStream();

        output_able = true;
    }

    private synchronized void print(byte[] b, ConsoleOutputStream os) {
        try {
            text.insertString(text.getLength(), new String(b, 0, b.length), os == out ? out_att : err_att);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void clear() throws BadLocationException {
        text.remove(0, text.getLength());
    }

    public void redirectSystem() {
        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(err, true));
    }

    public void setOutColor(Color outc) {
        StyleConstants.setForeground(out_att, outc);
    }

    public void setErrColor(Color errc) {
        StyleConstants.setForeground(err_att, errc);
    }

    public OutputStream getOutStream() {
        return out;
    }

    public OutputStream getErrStream() {
        return err;
    }

    /**
     * OutputStream der Text in diesem JTextPane ausgibt
     */
    private class ConsoleOutputStream extends OutputStream {
        private Queue<Byte> buffer = new LinkedList<Byte>();
        private boolean closed;

        public ConsoleOutputStream() {
            closed = false;
        }

        @Override
        public void write(int b) throws IOException {
            if (closed)
                throw new IOException("Stream closed");
            buffer.offer((byte) b);
        }

        @Override
        public void flush() {
            if (output_able)
                synchronized (buffer) {
                    byte[] b = new byte[buffer.size()];
                    int cnt = 0;
                    while (!buffer.isEmpty())
                        b[cnt++] = buffer.poll();
                    print(b, this);
                }
        }

        @Override
        public void close() {
            closed = true;
        }
    }
}