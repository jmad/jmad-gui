// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, Kajetan Fuchsberger. All rights reserved.
 * 
 * JMad is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JMad is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JMad.  If not, see <http://www.gnu.org/licenses/>.
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


public class JConsole extends JTextPane
{
	private static final long serialVersionUID = 1L;

	private boolean output_able;
    
    private SimpleAttributeSet out_att;
    private SimpleAttributeSet err_att;
    
    private ConsoleOutputStream out;
    private ConsoleOutputStream err;
    
    private StyledDocument text;
    
    
    
    public JConsole()
    {
        super();
        setEditable( false );
        setBackground( Color.BLACK );
        
        out_att = new SimpleAttributeSet();
        StyleConstants.setForeground( out_att, Color.GREEN );
        err_att = new SimpleAttributeSet();
        StyleConstants.setForeground( err_att, Color.RED );
        
        text = new DefaultStyledDocument();
        setDocument( text );
        
        out = new ConsoleOutputStream();
        err = new ConsoleOutputStream();
        
        output_able = true;
    }
    
    
    
    private synchronized void print( byte[] b, ConsoleOutputStream os )
    {
        try {
            text.insertString( text.getLength(), new String( b, 0, b.length ),
                               os == out ? out_att : err_att );
        } catch( BadLocationException e ) {
            e.printStackTrace();
        }
    }
    
    public void clear() throws BadLocationException
    {
        text.remove( 0, text.getLength() );
    }
    
    
    public void redirectSystem()
    {
        System.setOut( new PrintStream( out, true ) );
        System.setErr( new PrintStream( err, true ) );
    }
    
    public void setOutColor( Color outc ) { StyleConstants.setForeground( out_att, outc ); }
    public void setErrColor( Color errc ) { StyleConstants.setForeground( err_att, errc ); }
    
    public OutputStream getOutStream() { return out; }
    public OutputStream getErrStream() { return err; }
    
    
    /**
     * OutputStream der Text in diesem JTextPane ausgibt
     */
    private class ConsoleOutputStream extends OutputStream
    {
        private Queue<Byte> buffer = new LinkedList<Byte>();
        private boolean closed;
        
        public ConsoleOutputStream()
        {
            closed = false;
        }
        
        @Override
        public void write( int b ) throws IOException
        {
            if( closed )
                throw new IOException( "Stream closed" );
            buffer.offer( (byte)b );
        }
        
        @Override
        public void flush()
        {
            if( output_able )
            synchronized( buffer )
            {
                byte[] b = new byte[buffer.size()];
                int cnt = 0;
                while( !buffer.isEmpty() )
                    b[cnt++] = buffer.poll();
                print( b, this );
            }
        }
        
        @Override
        public void close()
        {
            closed = true;
        }
    }
}