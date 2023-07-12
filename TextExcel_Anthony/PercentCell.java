
/**
 * Write a description of class PercentCell here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PercentCell extends RealCell
{
    private String input2;

    /**
     * Constructor for objects of class RealCell
     */
    public PercentCell(String input)
    {
        input2 = input;// initialise instance variables

    }

    /**
     * Text for spreadsheet cell display, must be exactly length 10
     * 
     * @return string
     */
    //ASK TWANG ABOUT IF DECIMAL NEEDED IN INPUT/ HOW VALUE CELL WORKS(LOOK AT TEST A3)
    public String abbreviatedCellText() {
        String sspace = "";
        String truncateInput = input2.substring(0, input2.indexOf("."));
        if(truncateInput.length() > 10) //makes the string 10 characters long and adds a %
        {
            return truncateInput.substring(0,9) + "%";
        } else {
            int amountOfSpace = 9 - truncateInput.length();
            for(int i = 0; i < amountOfSpace; i++)
            {
                sspace = sspace + " ";
            }
            return truncateInput + "%" + sspace;
        }
    }

    /**
     * Text for individual cell inspection, not truncated or padded
     * 
     * @return string
     */
    public String fullCellText() {
        double decimal = getDoubleValue()/100; 
        return decimal + "";
    }

    public double getDoubleValue()
    {
        Double value = Double.parseDouble(input2); 
        return value;
    }

}
