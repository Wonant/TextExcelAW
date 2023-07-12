
//Update this file with your own code.
/**
 * Represents a location in the spreadsheet
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SpreadsheetLocation extends Location
{
    String input;

    @Override
    public int getRow()
    {
        // TODO Auto-generated method stub
        int row = Integer.parseInt(input.substring(1, input.length())); //turns the number in the input "A12" to an integer "12"
        return row - 1;
    }

    @Override
    public int getCol()
    {
        // TODO Auto-generated method stub
        String alphabet = "ABCDEFGHIJKLM";
        String rowLet = input.substring(0,1).toUpperCase(); //takes the first value of the input 
        int col = alphabet.indexOf(rowLet); //uses the alphabet string to find the row index
        return col;
    }

    /**
     * Constructor
     * 
     * @param cellName Name of cell, like "A1"
     */
    public SpreadsheetLocation(String cellName)
    {
        input = cellName;
    }

}
