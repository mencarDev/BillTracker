package BillTrackPack;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class TextfieldFilter extends DocumentFilter
{
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException
    {
        if (isValid(string))
        {
            super.insertString(fb, offset, string, attr);
        }
    }

    //
    public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr) throws BadLocationException
    {
        if (isValid(string))
        {
            super.replace(fb, offset, length, string, attr);
        }
    }

    private boolean isValid(String text)
    {
        return text.matches("[0-9 ]*");
    }
}