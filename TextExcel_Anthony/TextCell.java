
/**
 * Write a description of class TextCell here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TextCell extends Cell
{
    // instance variables - replace the example below with your own
    private String storage;
    /**
     * Constructor for objects of class TextCell
     */
    public TextCell(String string)
    {
        storage = string; //initialises the input
    }

    /**
     * Text for spreadsheet cell display, must be exactly length 10
     * 
     * @return string
     */
    public String abbreviatedCellText() {
        String sspace = "";
        if(storage.length() > 10) //makes the string 10 characters long
        {
            return storage.substring(0,10);
        } else {
            int amountOfSpace = 10 - storage.length();
            for(int i = 0; i < amountOfSpace; i++)
            {
                sspace = sspace + " ";
            }
            return storage + sspace;
        }
    }

    /**
     * Text for individual cell inspection, not truncated or padded
     * 
     * @return string
     */
    public String fullCellText() {
        return "\"" + storage + "\""; //returns the string with quotations around it
    }
    //public static void main(String[] args)
    //{
    //    TextCell LOL = new TextCell("WWOOOOOOOOHOOOOO");
    //    System.out.println(LOL.abbreviatedCellText() + "l");
    //}
}
