/*
 * HighligtedTextPane.java
 *
 * Created on 4. kveten 2006, 12:16
 *
 */

package cz.muni.stanse.gui;

import com.Ostermiller.Syntax.HighlightedDocument;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

/**
 * The <CODE>HighligtedTextPane</CODE> component is for hilighting, finding and working with text
 * @author Ondrej Zivotsky ( ondrej@zivotsky.cz )
 * @version 1.0
 */
public class HighligtedTextPane extends JTextPane {
    
    /**
     * Document style for C or C++ source files
     */
    public static final Object DOCUMENT_STYLE_C = HighlightedDocument.C_STYLE;
    /**
     * Document style for java source files
     */
    public static final Object DOCUMENT_STYLE_JAVA = HighlightedDocument.JAVA_STYLE;
    /**
     * Document style for java script files
     */
    public static final Object DOCUMENT_STYLE_JAVASCRIPT = HighlightedDocument.JAVASCRIPT_STYLE;
    /**
     * Document style for SQL files
     */
    public static final Object DOCUMENT_STYLE_SQL = HighlightedDocument.SQL_STYLE;
    /**
     * Document style for HTML source files
     */
    public static final Object DOCUMENT_STYLE_HTML_SYMPLE = HighlightedDocument.HTML_STYLE;
    /**
     * Document style for HTML source files
     */
    public static final Object DOCUMENT_STYLE_HTML_COMPLEX = HighlightedDocument.HTML_KEY_STYLE;
    /**
     * Document style for LaTeX source files
     */
    public static final Object DOCUMENT_STYLE_LATEX = HighlightedDocument.LATEX_STYLE;
    /**
     * Document style for java properties files
     */
    public static final Object DOCUMENT_STYLE_PROPERTIES = HighlightedDocument.PROPERTIES_STYLE;
    /**
     * Document style for grayed files
     */
    public static final Object DOCUMENT_STYLE_GRAYED_OUT = HighlightedDocument.GRAYED_OUT_STYLE;
    /**
     * Document style for text files
     */
    public static final Object DOCUMENT_STYLE_PLAIN_TEXT = HighlightedDocument.PLAIN_STYLE;
    
    private boolean edit;
    private KeyAdapter keyAdapterEditing;
    
    
    private String cachedText = null;
    
    
    /**
     * Creates a new instance of HighligtedTextPane
     */
    public HighligtedTextPane() {
        this(true);
    }
    
    /**
     * Creates a new instance of HighligtedTextPane
     * @param enableChanges value <CODE>true</CODE> means, that component is enabled and editable
     * value <CODE>false</CODE> means, that component is enabled but editing is restricted (see <CODE>setEditableSpecial()</CODE>)
     */
    public HighligtedTextPane(boolean enableChanges) {
        setEditableSpecial(enableChanges);
        setFont(new Font("Monospace", Font.PLAIN, getFont().getSize()));
        setSelectionColor(new Color(255,255,175));
        
        // adapter, ktery nedovoli editovat text
        // pozmeneno -- editace je i tak zakazana.
        keyAdapterEditing = new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (    (evt.getKeyCode()!=evt.VK_UP)&&(evt.getKeyCode()!=evt.VK_DOWN)
                &&(evt.getKeyCode()!=evt.VK_LEFT)&&(evt.getKeyCode()!=evt.VK_RIGHT)
                &&(evt.getKeyCode()!=evt.VK_HOME)&&(evt.getKeyCode()!=evt.VK_END)
                &&(evt.getKeyCode()!=evt.VK_PAGE_UP)&&(evt.getKeyCode()!=evt.VK_PAGE_DOWN)
                &&(! ((evt.getKeyCode()==evt.VK_C) && (evt.isControlDown())) )
                ) {
               //     evt.setKeyCode(evt.KEY_RELEASED);
                }
            }
//            public void keyTyped(java.awt.event.KeyEvent evt) {
//                //evt.setKeyChar(evt.CHAR_UNDEFINED);
//            }
        };
    }
    
    /**
     * Adds the specified key listener to receive key events from this component. If l is null, no exception is thrown and no action is performed.
     * <BR><b>Varning:</b> If <CODE>HighlightedTextPane</CODE> is set to special editing mode (see isEditableSpecial(), setEditableSpecial()), new added <CODE>KeyListener</CODE> will have invalid a <CODE>KeyEvent</CODE>. If you want add your own <CODE>KeyListener</CODE>, add it before calling method <CODE>setEditableSpecial(false)</CODE>! (Or switch special editing off, add your own listener and than switch special editing on).
     * @param l the key listener.
     * @since JDK1.1
     * @see KeyEvent
     * @see KeyListener
     * @see removeKeyListener(java.awt.event.KeyListener)
     * @see getKeyListeners()
     */
    public void addKeyListener(KeyListener l) { // toto je tu jen kvuli javadoc ! :-)
        super.addKeyListener(l);
    }
    
    /**
     * Sets the special editing mode of this Component.
     * <BR>Value <CODE>true</CODE> means full text editing (<CODE>isEditable()</CODE> must be true)
     * <BR>Value <CODE>false</CODE> means editing restrictions. User can copy from text, select text, scroll by keyboard,.. but user can't edit the text.
     * <b>Varning:</b> In case of false this method add a special KeyListener which invalid a KeyEvent for other KeyListener. If you want add your own KeyListener, add it before calling this method! (Or switch special editing off, add your own listener and than switch special editing on)
     * @param enableChanges boolean value to be set
     * @see isEditableSpecial()
     */
    public void setEditableSpecial(boolean enableChanges) {
        edit = enableChanges;
        if (enableChanges) {
            removeKeyListener(keyAdapterEditing);
        } else {
            addKeyListener(keyAdapterEditing);
        }
    }
    
    /**
     * Returns the boolean indicating whether this component is editable in normal(<CODE>true</CODE>) or special editable(<CODE>false</CODE>) mode.
     * @return the boolean value
     * @see setEditableSpecial()
     */
    public boolean isEditableSpecial() {
        return edit;
    }
    
    /**
     * Replaces the currently selected content with new content represented by the given string. If there is no selection this amounts to an insert of the given text. If there is no replacement text this amounts to a removal of the current selection.
     * <BR>If <CODE>isEditableSpecial()</CODE> is false, <B>this will do nothing</B>.
     * <BR>This is the method that is used by the default implementation of the action for inserting content that gets bound to the keymap actions.
     * <BR>This method is thread safe, although most Swing methods are not. Please see Threads and Swing for more information.
     * @param content the content to replace the selection with
     */
    public void replaceSelection(String content) {
        if (edit) {
            super.replaceSelection(content);
        }
        // else do nothing
    }
    
    /**
     * Transfers the contents of the system clipboard into the associated text model. If there is a selection in the associated view, it is replaced with the contents of the clipboard. If there is no selection, the clipboard contents are inserted in front of the current insert position in the associated view. If the clipboard is empty, does nothing.
     * <BR>If <CODE>isEditableSpecial()</CODE> is false, <B>this will do nothing</B>.
     */
    public void paste() {
        if (edit) {
            super.paste();
        }
        // else do nothing
    }
    
    /**
     * Transfers the currently selected range in the associated text model to the system clipboard, removing the contents from the model. The current selection is reset. Does nothing for <CODE>null</CODE> selections.
     * <BR>If <CODE>isEditableSpecial()</CODE> is false, <B>this will do same as <CODE>copy()</CODE></B>.
     */
    public void cut() {
        if (edit) {
            super.cut();
        } else {
            copy(); // copy only
        }
    }
    
    // vrati index prvniho znaku radku "line"
    private int getIndexOfLine(int line) {
        if (line <= 0) { throw new IndexOutOfBoundsException("Line number is less then 1"); }
        int j = 0;
        if (line == 1) { // prvni radek
            j = 0;
        } else {     // najdi radek
            j = getText().indexOf(System.getProperty("line.separator"), 0); // najdi prvni
            int count = 2; // !!! uz jsme na 2. radku !!!
            while ((j > -1)&&(count < line)) { // dokud tam jsou radky a neni to hledany
                count++;
                j = getText().indexOf(System.getProperty("line.separator"), j+1);
            }
            if (j < 0) { throw new IndexOutOfBoundsException("Line number is out of range"); }
            j++; // preskoc znak konce radku
        }
        return j;
    }

    /**
     * Clear cache and call super
     * @param t String to be set
     */
    public void setText(String t) {
        cachedText = t;
        super.setText(t);
    }

    
    /**
     * Get text from cache, if available, call super otherwise.
     * @return Text in the pane
     */
    public String getText() {
        if(cachedText == null) {
            cachedText = super.getText();
        }
        return cachedText;
    }
    

    
    /**
     * Returns a line of text in component. (Line is text between 2 line separators). Lines are numbered from 1.
     * <BR>If <CODE>line</CODE> is incorrect line, this will throw a IndexOutOfBoundsException exception.
     * @param line number of line (numbered from 1)
     * @throws java.lang.IndexOutOfBoundsException if the line number is less than 1 or there is not so amny lines
     * @return text of elected line
     */
    public String getLine(int line) throws IndexOutOfBoundsException {
        String textLine;                       // najdi dalsi konec radky
        int index = getIndexOfLine(line);
        int k = getText().indexOf(System.getProperty("line.separator"), index);
        if (k < 0) { k = getText().length(); } // posledni radek
        try {
            textLine = getText(index, k-index); // zjisti si text radku
        } catch (BadLocationException ex) {
            textLine = ""; // nikdy nenastane :-)
        }
        return textLine;
    }
    
    /**
     * Select text from beginning position (row and column in text) to end position (row and column). Then move the cursor on the end of selection.
     * <BR>If some index is out of range, the IndexOutOfBoundsException exception is thrown.
     * @return index of first character in selection. Index is index in whole text! (ending index is getCaretPosition())
     * @param rowStart row of starting position (numbered from 1)
     * @param columnStart column of starting position (numbered from 1)
     * @param rowEnd row of ending position (numbered from 1)
     * @param columnEnd column of ending position (numbered from 1)
     * @throws java.lang.IndexOutOfBoundsException if some index is invalid
     */
    public int selectText(int rowStart, int columnStart, int rowEnd, int columnEnd) throws IndexOutOfBoundsException {
        int start = getIndexOfLine(rowStart);
        int end = getIndexOfLine(rowEnd);
        if ((start > end)||((start == end)&&(columnStart > columnEnd))) {
            throw new IndexOutOfBoundsException("Selection indexes are invalid. End is before start!");
        }
        columnStart = columnStart-1; // chceme cislovat od nuly
        columnEnd = columnEnd-1;
        int k;
        // najdi absolutni index zacatku
        k = getText().substring(start, start+columnStart).indexOf(System.getProperty("line.separator"));
        if (k == -1) { // sloupec v tomto radku existuje
            start = start + columnStart;
        } else { // sloupec je za koncem radku - start je na novem radku
            start = start + k + 1;
        }
        // najdi absolutni index konce
        k = getText().substring(end, end+columnEnd).indexOf(System.getProperty("line.separator"));
        if (k == -1) { // sloupec v tomto radku existuje
            end = end + columnEnd;
        } else { // sloupec je za koncem radku - konec je konci
            end = end + k;
        }
        select(start, end); // vyber text
        scrollRectToVisible(new Rectangle()); // zobrazi co nejvic z vyberu (konec a dopredu)
        requestFocusInWindow(); // bez toho to proste nezvyrazni (no comment)
        return start;
    }
    
    /**
     * Select elected lines and puts cursor on the end of selection.
     * <BR>If some index is out of range, the IndexOutOfBoundsException exception is thrown.
     * @return index of first character in selection. Index is index in whole text! (ending index is getCaretPosition())
     * @param rowStart row of starting position (numbered from 1)
     * @param rowEnd row of ending position (numbered from 1)
     * @throws java.lang.IndexOutOfBoundsException if some index is invalid
     */
    public int selectLines(int rowStart, int rowEnd) throws IndexOutOfBoundsException {
        int start = getIndexOfLine(rowStart);
        int end = getIndexOfLine(rowEnd);
        if (start > end) {
            throw new IndexOutOfBoundsException("Selection indexes are invalid. End is before start!");
        }
        int k;     // najdi absolutni index konce
        k = getText().substring(end).indexOf(System.getProperty("line.separator"));
        if (k == -1) { // je to posledni radek
            end = getText().length();
        } else { // neni to posledni radek - konec je na konci
            end = end + k;
        }
        select(start, end); // vyber text
        scrollRectToVisible(new Rectangle()); // zobrazi co nejvic z vyberu (konec a dopredu)
        requestFocusInWindow(); // bez toho to proste nezvyrazni (no comment)
        return start;
    }
    
    /**
     * Returns a column count in elected row
     * <BR>If row is out of range, the IndexOutOfBoundsException exception is thrown.
     * @return number of columns in elected row
     * @param row row numbered from 1
     * @throws java.lang.IndexOutOfBoundsException if line number is invalid
     */
    public int getColumnsCount(int row) throws IndexOutOfBoundsException {
        int line = getIndexOfLine(row);
        int column;     // najdi relativni index konce radku
        column = getText().substring(line).indexOf(System.getProperty("line.separator"));
        if (column == -1) { // je to posledni radek
            column = getText().length() - line;
        }
        return column;
    }
    
    /**
     * Returns the line count of text in component.
     * @return line count
     */
    public int getLinesCount() {
        String text = getText(); // text
        int i = text.indexOf(System.getProperty("line.separator"), 0); // prvni radek
        int count = 1;
        while (i >= 0) {
            count++;                // najdi dalsi radky
            i = text.indexOf(System.getProperty("line.separator"), i+1);
        }
        return count;
    }
    
    /**
     * Finds specific string in the text in component. Founded string is selected, cursor is moved to the end of the string and index of beginning of the string is returned.
     * <BR>If finding is unsuccessfull <CODE>-1</CODE> is returned.
     * <BR><b>Searching is case insensitive!</b>
     * @param start starting position of searching
     * @param expr finding text
     * @return index of first character of selected string  (ending index is getCaretPosition()) or <CODE>-1</CODE> if finding was unsuccessfull 
     */
    public int findText(String expr, int start) {
        if ((start < 0)||(start > getText().length()-1)) { start = 0; } // kontrola parametru
        int i;
        i = getText().toLowerCase().indexOf(expr.toLowerCase(), start); // najdi text
        if (i < 0) { // pokud tam neni, hledej znova od zacatku
            i = getText().toLowerCase().indexOf(expr.toLowerCase(), 0); // najdi text
        }
        if (i >= 0) { // pokud neco nasel
            select(i, i+expr.length()); // vyber text
            scrollRectToVisible(new Rectangle()); // zobrazi co nejvic z vyberu (konec a dopredu)
            requestFocusInWindow(); // bez toho to proste nezvyrazni (no comment)
        }
        return i; // vrat pocatecni index
    }
    
    /**
     * Finds specific regular expression in the text in component. Founded text matching regular expression is selected, cursor is moved to the end of the string and index of beginning of the string is returned.
     * <BR>If finding is unsuccessfull <CODE>-1</CODE> is returned.
     * <BR><b>Searching is case insensitive!</b>
     * @param expr finding regular expression
     * @param start starting position of searching
     * @return index of first character of selected string  (ending index is getCaretPosition()) or <CODE>-1</CODE> if finding was unsuccessfull or <CODE>-2</CODE> if regular expression was incorrect
     */
    public int findRegExpr(String expr, int start) {
        if ((start < 0)||(start > getText().length()-1)) { start = 0; } // kontrola parametru
        int i, j;
        try {                       // najdi text
            Matcher m = Pattern.compile(expr, Pattern.CASE_INSENSITIVE).matcher(getText());
            if (! m.find(start)) { // pokud nenajde
                m.find(0); // hledej znovu od zacatku
            }
            i = m.start(); // zacatek nalezeneho retezce
            j = m.end(); // konec nalezeneho retezce
            select(i, j);  // vyber text
            scrollRectToVisible(new Rectangle()); // zobrazi co nejvic z vyberu (konec a dopredu)
            requestFocusInWindow();  // bez toho to proste nezvyrazni (no comment)
        } catch (PatternSyntaxException ex) {
            i = -2; // regex is not correct
        } catch (IllegalStateException ex) {
            i = -1; // not found
        }
        return i;
    }
    
    
    public void openFile(final File file) {
        openFile(file, null);
    }
    /**
     * Opens a file and set the highlighting style.
     * <BR>File is reading in a new thread and then displayed by event-dispatcher thread.
     * @param style document highlighting style object (use constants DOCUMENT_STYLE_...)
     * @param file file to open
     */
    public void openFile(final File file, Object style) {
        Cursor originalCursor = getCursor();
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(style != null) {
            // nastav novy prazdny dokument (se zvyraznovanim syntaxe)
            com.Ostermiller.Syntax.HighlightedDocument sourceStyledDocument = new HighlightedDocument();
            sourceStyledDocument.setHighlightStyle(style);
            setStyledDocument(sourceStyledDocument);
        }
        setText("Loading file '"+file.getName()+"' ...");
        
        // nahrani textu do pomocneho JTextPane ve vlakne!
        final JTextPane pane = new JTextPane();
        Runnable myThread = new Runnable() {
            public void run() {
                try {
                    BufferedReader fr = new BufferedReader(new FileReader(file));
                    pane.read(fr, null);
                    fr.close();
                } catch (java.io.FileNotFoundException e) {
                    System.err.println(e.getLocalizedMessage());
                } catch (java.io.IOException e) {
                    System.err.println(e.getLocalizedMessage());
                }
                // zobraz text do komponenty. MUSI to byt ve vlakne event-dispatcheru
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        // zkopiruj text BEZ formatovani a stylu
                        setText(pane.getText());
                    }
                });
            }
        };
        new Thread(myThread, "Reading Source FILE Thread").start();
        
        this.setCursor(originalCursor);
    }
    
    /**
     * Sets a new document highlighting style. New highlighting used immediately.
     * @param newDocumentStyle document highlighting style object (use constants DOCUMENT_STYLE_...)
     */
    public void setDocumentHighlightingStyle(Object newDocumentStyle) {
        ((com.Ostermiller.Syntax.HighlightedDocument) getStyledDocument()).setHighlightStyle(newDocumentStyle);
    }
    
}
