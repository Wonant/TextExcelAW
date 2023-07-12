
/**
 * Write a description of class ValueCell here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ValueCell extends RealCell
{
    private String input2;

    /**
     * Constructor for objects of class RealCell
     */
    public ValueCell(String input)
    {
        input2 = input;// initialise instance variables

    }

    /**
     * Text for spreadsheet cell display, must be exactly length 10
     * 
     * @return string
     */
    public String abbreviatedCellText() {
        String sspace = "";
        String input3 = input2; 
        if(input3.contains(".")){ //removes extra 0 after the decimal point
            while(!input3.substring(input3.length()-2, input3.length()-1).equals(".")){
                if(input3.substring(input3.length()-1, input3.length()).equals("0")){
                    input3 = input2.substring(0, input3.length()-1);
                }else{
                    break;
                }
            }
        }
        if (!(input3.contains(".")) ){ //makes sure there is a decimal point
            input3 = input3 + ".0";
        }
        if(input3.length() > 10) //makes the ouput longer or shorter so that it is 10 characters long
        {
            return input3.substring(0,10);
        } else {
            int amountOfSpace = 10 - input3.length();
            for(int i = 0; i < amountOfSpace; i++)
            {
                sspace = sspace + " ";
            }
            return input3 + sspace;
        }

    }

    /**
     * Text for individual cell inspection, not truncated or padded
     * 
     * @return string
     */
    public String fullCellText() {
        return input2 + "";
    }

    public double getDoubleValue()
    {
        Double value = Double.parseDouble(input2);  //returns numerical value
        return value;
    }

    public static void main(String[] args)
    {
        ValueCell Lit = new ValueCell("6.785323");
        System.out.println(Lit.abbreviatedCellText() + "|");
        System.out.println(Lit.fullCellText() + "|");
    }
}
