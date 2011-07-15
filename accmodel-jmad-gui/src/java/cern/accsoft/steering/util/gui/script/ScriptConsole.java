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
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * Script console for BSF-compliant languages. The console is independent of the scripting language, which can be
 * changed dynamically. This class uses a BSFManager, and not an Interpreter, as most other examples do. NB: This class
 * is inspired from bsh/util/JConsole.java by Patrick Niemeyer (pat@pat.net). JConsole is subject to the Sun Public
 * License version 1.0 and to the GNU Lesser General Public License (the "LGPL"). http://cvs.sourceforge.
 * net/viewcvs.py/beanshell/BeanShell/src/bsh/util/JConsole.java This is a small refactoring, which does not use the
 * apache-bsf library anymore, but the java-internal classes
 * 
 * @author Olivier Dameron (dameron@smi.stanford.edu), Patrick Niemeyer (pat@pat.net)
 */
public class ScriptConsole extends JScrollPane implements KeyListener, MouseListener, ActionListener {
    private static final long serialVersionUID = 1L;

    private final static String CUT = "Cut";
    private final static String COPY = "Copy";
    private final static String PASTE = "Paste";

    private InputStream in;
    private ConsoleOutputStream out = new ConsoleOutputStream();
    private ConsoleOutputStream err = new ConsoleOutputStream();
    private PrintStream outPrintStream = new PrintStream(out, true);
    private PrintStream outErrStream = new PrintStream(err, true);

    private PrintStream defaultSystemOutput = System.out;
    private PrintStream defaultSystemError = System.err;

    private int cmdStart = 0;
    private Vector<String> history = new Vector<String>();
    private String startedLine;
    private int histLine = 0;
    private String executionBuffer = "";
    private boolean multilineCommand = false;

    private JPopupMenu menu;
    private JTextPane text;
    private StyledDocument doc = new DefaultStyledDocument();

    private ScriptEngine scriptEngine;
    private String indentOffset;

    private boolean output_able = true;

    private MutableAttributeSet out_att = new SimpleAttributeSet();
    private MutableAttributeSet err_att = new SimpleAttributeSet();
    {
        StyleConstants.setForeground(out_att, Color.GREEN);
        StyleConstants.setForeground(err_att, Color.RED);
    }

    public ScriptConsole() {
        this(null, null);
    }

    public ScriptConsole(InputStream cin, OutputStream cout) {
        super();
        indentOffset = "";

        /*
         * Special TextPane which catches for cut and paste, both L&F keys and programmatic behaviour
         */
        text = new JTextPane(doc) {
            private static final long serialVersionUID = 1L;

            public void cut() {
                if (text.getCaretPosition() < cmdStart) {
                    super.copy();
                } else {
                    super.cut();
                }
            }

            public void paste() {
                forceCaretMoveToEnd();
                super.paste();
            }
        };

        Font font = new Font("Monospaced", Font.PLAIN, 12);
        text.setText("");
        text.setFont(font);
        text.setMargin(new Insets(7, 5, 7, 5));
        text.addKeyListener(this);
        setViewportView(text);

        // create popup menu
        menu = new JPopupMenu("JConsole	Menu");
        menu.add(new JMenuItem(CUT)).addActionListener(this);
        menu.add(new JMenuItem(COPY)).addActionListener(this);
        menu.add(new JMenuItem(PASTE)).addActionListener(this);

        text.addMouseListener(this);

        requestFocus();

        setStyle(Color.GREEN.darker());
        println("Welcome the scripting console!");
        setStyle(Color.black);

        addPrompt();
    }

    public void captureSystemOut(boolean mode) {
        if (mode) {
            System.setOut(outPrintStream);
            System.setErr(outErrStream);
        } else {
            System.setOut(defaultSystemOutput);
            System.setErr(defaultSystemError);
        }
    }

    public InputStream getInputStream() {
        return in;
    }

    public Reader getIn() {
        return new InputStreamReader(in);
    }

    public void requestFocus() {
        super.requestFocus();
        text.requestFocus();
    }

    public void keyPressed(KeyEvent e) {
        type(e);
        // gotUp=false;
    }

    public void keyTyped(KeyEvent e) {
        type(e);
    }

    public void keyReleased(KeyEvent e) {
        // gotUp=true;
        type(e);
    }

    private void addPrompt() {
        setStyle(Color.magenta);
        if (!multilineCommand) {
            append(">>> ");
        } else {
            append("... ");
        }
        // append(indentOffset);
        setStyle(Color.black);
        resetCommandStart();
    }

    private synchronized void type(KeyEvent e) {
        /* Necessary for overcoming color bug */
        setStyle(Color.black);

        switch (e.getKeyCode()) {
        case (KeyEvent.VK_ENTER):
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                enter();
                if (indentOffset.length() == 0) {
                    executeBuffer();
                }
                blankLine();
                addPrompt();
                resetCommandStart();
                append(indentOffset);
                text.setCaretPosition(cmdStart + indentOffset.length());
            }
            e.consume();
            text.repaint();
            break;

        case (KeyEvent.VK_UP):
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                historyUp();
            }
            e.consume();
            break;

        case (KeyEvent.VK_DOWN):
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                historyDown();
            }
            e.consume();
            break;

        case (KeyEvent.VK_LEFT):
            break;

        case (KeyEvent.VK_BACK_SPACE):
            // if (e.getID() == KeyEvent.KEY_PRESSED) {
            // backspace();
            // }
            // e.consume();
            // break;
        case (KeyEvent.VK_DELETE):
            if (text.getCaretPosition() <= cmdStart) {
                // This doesn't work for backspace.
                // See default case for workaround
                e.consume();
            }
            break;

        case (KeyEvent.VK_RIGHT):
            forceCaretMoveToStart();
            break;

        case (KeyEvent.VK_HOME):
            text.setCaretPosition(cmdStart);
            e.consume();
            break;

        case (KeyEvent.VK_U): // clear line
            if ((e.getModifiers() & InputEvent.CTRL_MASK) > 0) {
                replaceRange("", cmdStart, textLength());
                histLine = 0;
                e.consume();
            }
            break;

        case (KeyEvent.VK_ALT):
        case (KeyEvent.VK_CAPS_LOCK):
        case (KeyEvent.VK_CONTROL):
        case (KeyEvent.VK_META):
        case (KeyEvent.VK_SHIFT):
        case (KeyEvent.VK_PRINTSCREEN):
        case (KeyEvent.VK_SCROLL_LOCK):
        case (KeyEvent.VK_PAUSE):
        case (KeyEvent.VK_INSERT):
        case (KeyEvent.VK_F1):
        case (KeyEvent.VK_F2):
        case (KeyEvent.VK_F3):
        case (KeyEvent.VK_F4):
        case (KeyEvent.VK_F5):
        case (KeyEvent.VK_F6):
        case (KeyEvent.VK_F7):
        case (KeyEvent.VK_F8):
        case (KeyEvent.VK_F9):
        case (KeyEvent.VK_F10):
        case (KeyEvent.VK_F11):
        case (KeyEvent.VK_F12):
        case (KeyEvent.VK_ESCAPE):

            // only modifier pressed
            break;

        // Control-C
        case (KeyEvent.VK_C):
            if (text.getSelectedText() == null) {
                if (((e.getModifiers() & InputEvent.CTRL_MASK) > 0) && (e.getID() == KeyEvent.KEY_PRESSED)) {
                    append("^C");
                }
                e.consume();
            }
            break;

        // case ( KeyEvent.VK_TAB ):
        // if (e.getID() == KeyEvent.KEY_RELEASED) {
        // String part = text.getText().substring( cmdStart );
        // doCommandCompletion( part );
        // }
        // e.consume();
        // break;

        default:
            if ((e.getModifiers() & (InputEvent.CTRL_MASK | InputEvent.ALT_MASK | InputEvent.META_MASK)) == 0) {
                // plain character
                forceCaretMoveToEnd();
            }

            /*
             * The getKeyCode function always returns VK_UNDEFINED for keyTyped events, so backspace is not fully
             * consumed.
             */
            if (e.paramString().indexOf("Backspace") != -1) {
                if (text.getCaretPosition() <= cmdStart) {
                    e.consume();
                    break;
                }
            }

            break;
        }
    }

    private void resetCommandStart() {
        cmdStart = textLength();
    }

    private void append(String string) {
        int slen = textLength();
        text.select(slen, slen);
        text.replaceSelection(string);
    }

    private String replaceRange(Object s, int start, int end) {
        String st = s.toString();
        text.select(start, end);
        text.replaceSelection(st);
        // text.repaint();
        return st;
    }

    private void forceCaretMoveToEnd() {
        if (text.getCaretPosition() < cmdStart) {
            // move caret first!
            text.setCaretPosition(textLength());
        }
        text.repaint();
    }

    private void forceCaretMoveToStart() {
        if (text.getCaretPosition() < cmdStart) {
            // move caret first!
        }
        text.repaint();
    }

    private String getIndentation(String target) {
        if (target.length() == 0) {
            return "";
        }
        if (target.startsWith(" ")) {
            return " " + getIndentation(target.substring(1));
        }
        return "";
    }

    private void blankLine() {
        String s = getCmd();
        if (s.equals(indentOffset + "\n")) {
            indentOffset = "";
        }
    }

    private void enter() {
        String s = getCmd();

        if (s.length() == 0) {
            // special hack for empty return!
            // s = ";\n";
        } else {
            history.addElement(s);
            s = s + "\n";
        }

        append("\n");
        histLine = 0;
        acceptLine(s);
        text.repaint();

        indentOffset = getIndentation(s);
        if (isJython()) {
            if (s.endsWith(":\n")) {
                indentOffset += "    ";
            }
        }

        multilineCommand = true; // will be reset to false in executeBuffer()
    }

    private boolean isJython() {
        if (this.scriptEngine == null) {
            return false;
        }
        return scriptEngine.toString().equals("jython");
    }

    private String getCmd() {
        String s = "";
        try {
            s = text.getText(cmdStart, textLength() - cmdStart);
        } catch (BadLocationException e) {
            // should not happen
            System.out.println("Internal JConsole Error: " + e);
        }
        return s;
    }

    private void historyUp() {
        if (history.size() == 0)
            return;
        if (histLine == 0) // save current line
            startedLine = getCmd();
        if (histLine < history.size()) {
            histLine++;
            showHistoryLine();
        }
    }

    private void historyDown() {
        if (histLine == 0)
            return;

        histLine--;
        showHistoryLine();
    }

    private void showHistoryLine() {
        String showline;
        if (histLine == 0)
            showline = startedLine;
        else
            showline = (String) history.elementAt(history.size() - histLine);

        replaceRange(showline, cmdStart, textLength());
        text.setCaretPosition(textLength());
        text.repaint();
    }

    String ZEROS = "000";

    private void acceptLine(String line) {
        // backup original line
        String lineOrig = line;
        // Switch color to blue
        setStyle(Color.blue);
        // Patch to handle Unicode characters
        // Submitted by Daniel Leuck
        StringBuffer buf = new StringBuffer();
        int lineLength = line.length();
        for (int i = 0; i < lineLength; i++) {
            String val = Integer.toString(line.charAt(i), 16);
            val = ZEROS.substring(0, 4 - val.length()) + val;
            buf.append("\\u" + val);
        }
        line = buf.toString();
        // End unicode patch

        try {
            addLineToExecutionBuffer(lineOrig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* Restoring default black color */
        setStyle(Color.black);
    }

    private void addLineToExecutionBuffer(String s) {
        // executionBuffer.concat(s);
        executionBuffer = executionBuffer + s;
    }

    private void executeBuffer() {
        executeCommand(executionBuffer);
        executionBuffer = "";
        multilineCommand = false;
        indentOffset = "";
    }

    public void executeCommand(String command) {
        if (this.scriptEngine == null) {
            print("No script engine set. Cannot execute command.\n", Color.red);
            return;
        }
        captureSystemOut(true);
        setStyle(Color.blue);
        try {
            this.scriptEngine.eval(command);
        } catch (ScriptException e) {
            setStyle(Color.red);
            e.printStackTrace();
        }
        setStyle(Color.black);
        captureSystemOut(false);
    }

    public void println(Object o) {
        print(String.valueOf(o) + "\n");
        text.repaint();
    }

    public void setScriptEngine(ScriptEngine scriptEngine) {
        this.scriptEngine = scriptEngine;
        AttributeSet style = setStyle(Color.green);
        println("New scriptEngine: '" + scriptEngine.getFactory().getEngineName());
        setStyle(style);
    }

    public void print(final Object o) {
        print(o, null, null);
    }

    /**
     * Prints "\\n" (i.e. newline)
     */
    public void println() {
        print("\n");
        text.repaint();
    }

    public void error(Object o) {
        print(o, Color.red);
    }

    public void println(Icon icon) {
        print(icon);
        println();
        text.repaint();
    }

    public void print(final Icon icon) {
        if (icon == null)
            return;

        invokeAndWait(new Runnable() {
            public void run() {
                text.insertIcon(icon);
                resetCommandStart();
                text.setCaretPosition(cmdStart);
            }
        });
    }

    public void print(Object s, Font font) {
        print(s, font, null);
    }

    public void print(Object s, Color color) {
        print(s, null, color);
    }

    public void print(final Object o, final Font font, final Color color) {
        invokeAndWait(new Runnable() {
            public void run() {
                AttributeSet old = getStyle();
                setStyle(font, color);
                append(String.valueOf(o));
                resetCommandStart();
                text.setCaretPosition(cmdStart);
                setStyle(old, true);
            }
        });
    }

    public void print(Object s, String fontFamilyName, int size, Color color) {
        print(s, fontFamilyName, size, color, false, false, false);
    }

    public void print(final Object o, final String fontFamilyName, final int size, final Color color,
            final boolean bold, final boolean italic, final boolean underline) {
        invokeAndWait(new Runnable() {
            public void run() {
                AttributeSet old = getStyle();
                setStyle(fontFamilyName, size, color, bold, italic, underline);
                append(String.valueOf(o));
                resetCommandStart();
                text.setCaretPosition(cmdStart);
                setStyle(old, true);
            }
        });
    }

    private AttributeSet setStyle(Color color) {
        return setStyle(null, color);
    }

    private AttributeSet setStyle(Font font, Color color) {
        if (font != null)
            return setStyle(font.getFamily(), font.getSize(), color, font.isBold(), font.isItalic(),
                    StyleConstants.isUnderline(getStyle()));
        else
            return setStyle(null, -1, color);
    }

    private AttributeSet setStyle(String fontFamilyName, int size, Color color) {
        MutableAttributeSet attr = new SimpleAttributeSet();
        if (color != null)
            StyleConstants.setForeground(attr, color);
        if (fontFamilyName != null)
            StyleConstants.setFontFamily(attr, fontFamilyName);
        if (size != -1)
            StyleConstants.setFontSize(attr, size);

        setStyle(attr);

        return getStyle();
    }

    private AttributeSet setStyle(String fontFamilyName, int size, Color color, boolean bold, boolean italic,
            boolean underline) {
        MutableAttributeSet attr = new SimpleAttributeSet();
        if (color != null)
            StyleConstants.setForeground(attr, color);
        if (fontFamilyName != null)
            StyleConstants.setFontFamily(attr, fontFamilyName);
        if (size != -1)
            StyleConstants.setFontSize(attr, size);
        StyleConstants.setBold(attr, bold);
        StyleConstants.setItalic(attr, italic);
        StyleConstants.setUnderline(attr, underline);

        setStyle(attr);

        return getStyle();
    }

    private void setStyle(AttributeSet attributes) {
        setStyle(attributes, false);
    }

    private void setStyle(AttributeSet attributes, boolean overWrite) {
        text.setCharacterAttributes(attributes, overWrite);
    }

    private AttributeSet getStyle() {
        return text.getCharacterAttributes();
    }

    public void setFont(Font font) {
        super.setFont(font);

        if (text != null)
            text.setFont(font);
    }

    public String toString() {
        return "BeanShell console";
    }

    // MouseListener Interface
    public void mouseClicked(MouseEvent event) {
    }

    public void mousePressed(MouseEvent event) {
        if (event.isPopupTrigger()) {
            menu.show((Component) event.getSource(), event.getX(), event.getY());
        }
    }

    public void mouseReleased(MouseEvent event) {
        if (event.isPopupTrigger()) {
            menu.show((Component) event.getSource(), event.getX(), event.getY());
        }
        text.repaint();
    }

    public void mouseEntered(MouseEvent event) {
        giveFocusToConsole();
    }

    public void giveFocusToConsole() {
        requestFocus();
        text.setCaretPosition(textLength());
    }

    // handle cut, copy and paste
    public void actionPerformed(ActionEvent event) {
        String cmd = event.getActionCommand();
        if (cmd.equals(CUT)) {
            text.cut();
        } else if (cmd.equals(COPY)) {
            text.copy();
        } else if (cmd.equals(PASTE)) {
            text.paste();
        }
    }

    /**
     * If not in the event thread run via SwingUtilities.invokeAndWait()
     */
    private void invokeAndWait(Runnable run) {
        if (!SwingUtilities.isEventDispatchThread()) {
            try {
                SwingUtilities.invokeAndWait(run);
            } catch (Exception e) {
                // shouldn't happen
                e.printStackTrace();
            }
        } else {
            run.run();
        }
    }

    /**
     * The overridden read method in this class will not throw "Broken pipe" IOExceptions; It will simply wait for new
     * writers and data. This is used by the JConsole internal read thread to allow writers in different (and in
     * particular ephemeral) threads to write to the pipe. It also checks a little more frequently than the original
     * read(). Warning: read() will not even error on a read to an explicitly closed pipe (override closed to for that).
     */
    public static class BlockingPipedInputStream extends PipedInputStream {
        boolean closed;

        public BlockingPipedInputStream(PipedOutputStream pout) throws IOException {
            super(pout);
        }

        public synchronized int read() throws IOException {
            if (closed)
                throw new IOException("stream closed");

            while (super.in < 0) { // While no data */
                notifyAll(); // Notify any writers to wake up
                try {
                    wait(750);
                } catch (InterruptedException e) {
                    throw new InterruptedIOException();
                }
            }
            // This is what the superclass does.
            int ret = buffer[super.out++] & 0xFF;
            if (super.out >= buffer.length)
                super.out = 0;
            if (super.in == super.out)
                super.in = -1; /* now empty */
            return ret;
        }

        public void close() throws IOException {
            closed = true;
            super.close();
        }
    }

    // public void setNameCompletion( NameCompletion nc ) {
    // this.nameCompletion = nc;
    // }

    public void setWaitFeedback(boolean on) {
        if (on)
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        else
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    private int textLength() {
        return text.getDocument().getLength();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        /* nothing to do */
    }

    private synchronized void print(byte[] b, ConsoleOutputStream os) {
        try {
            doc.insertString(doc.getLength(), new String(b, 0, b.length), os == out ? out_att : err_att);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
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
