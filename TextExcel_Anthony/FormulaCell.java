
/**
 * Write a description of class FormulaCell here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class FormulaCell extends RealCell
{
    private String input2;
    private Spreadsheet spread1;
    public FormulaCell(String input)
    {
        input2 = input;
    }

    public FormulaCell(String input, Spreadsheet spread){
        input2 = input;
        this.spread1 = spread;
    }

    /**
     * Text for spreadsheet cell display, must be exactly length 10
     * 
     * @return string
     */
    public String abbreviatedCellText() {
        if(spread1 != null){

        }
        String equation = input2.substring(input2.indexOf("(")+2, input2.indexOf(")"));
        if (equation.length() > 3)
        {
            if ((equation.substring(0,3).toUpperCase().equals("SUM")) || (equation.substring(0,3).toUpperCase().equals("AVG")))
            {
                String alphabet = "ABCDEFGHIJKL";
                String startCell = equation.substring(equation.indexOf(" ")+1,equation.indexOf("-"));
                int startNum = Integer.parseInt(startCell.substring(1,startCell.length()));
                String startLetter = startCell.substring(0,1).toUpperCase();
                int multiplier = alphabet.indexOf(startLetter);
                int realStart = (multiplier * 20) + startNum;
                double sum = 0.0;
                double averageTracker = 0;
                String endCell = equation.substring(equation.indexOf("-")+1, equation.length()-1);
                int endNum = Integer.parseInt(endCell.substring(1,endCell.length()));
                String endLetter = endCell.substring(0,1).toUpperCase();
                int multiplier2 = alphabet.indexOf(endLetter);
                int realEnd = (multiplier2 * 20) + endNum;

                for(int i = alphabet.indexOf(startLetter); i <= alphabet.indexOf(endLetter); i++){ //loops through the rows
                    for(int j = startNum; j <= endNum; j++){ //loops through the columns
                        String cellName = alphabet.substring(i, i + 1) + j;

                        SpreadsheetLocation cellAccess = new SpreadsheetLocation(cellName);
                        if (spread1.getCell(cellAccess).abbreviatedCellText().equals("          ")) {
                            sum = sum;
                        } else
                        {
                            sum = sum + Double.parseDouble(spread1.getCell(cellAccess).abbreviatedCellText().substring(0,spread1.getCell(cellAccess).abbreviatedCellText().indexOf(" "))) ;
                            averageTracker = averageTracker + 1;
                        }
                    }
                }
                if (equation.substring(0,3).toUpperCase().equals("SUM"))
                {
                    equation = sum + "";

                }
                if (equation.substring(0,3).toUpperCase().equals("AVG"))
                {
                    equation = sum/averageTracker + "";
                }
            }
        }   
        if ((equation.contains("+")||equation.contains("- ")||(equation.contains("*")||equation.contains("/")))) //checks if an equation was inputted
        {
            String alphabet = "ABCDEFGHILJKL";       
            for(int j = 0; j < equation.length()-1; j++){            //loops through all the characters in equation to find letters
                for(int i = 0; i < alphabet.length()-1; i++){
                    if(equation.contains(alphabet.substring(i, i+1))){  //when letters are found they are replaced with the value at their cell location
                        String cell1 = equation.substring(equation.indexOf(alphabet.substring(i, i+1)), equation.indexOf(" ", equation.indexOf(alphabet.substring(i, i+1))));
                        SpreadsheetLocation accessCell = new SpreadsheetLocation(cell1);
                        int first = equation.indexOf(alphabet.substring(i, i+1));
                        equation = equation.substring(0, first) + spread1.getCell(accessCell).abbreviatedCellText().substring(0,spread1.getCell(accessCell).abbreviatedCellText().indexOf(" "))  + equation.substring(first+cell1.length(), equation.length());
                    }
                }
            }
        }
        while(equation.contains("+")||equation.contains("- ")||(equation.contains("*")||equation.contains("/"))){ //while there is still an operatior in the equation it will continue evaluating
            {
                double num1 = Double.parseDouble(equation.substring(0, equation.indexOf(" ")));
                equation = equation.substring(equation.indexOf(" ")+1, equation.length());
                String operator = equation.substring(0, equation.indexOf(" "));
                equation = equation.substring(equation.indexOf(" ")+1, equation.length());
                double num2 = Double.parseDouble(equation.substring(0, equation.indexOf(" ")));
                equation = equation.substring(equation.indexOf(" ")+1, equation.length());
                if(operator.equals("+")){
                    equation = num1 + num2 + " " + equation;
                }
                if(operator.equals("-")){
                    equation = num1 - num2 + " " + equation;
                }
                if(operator.equals("*")){
                    equation = num1 * num2 + " " + equation;
                }
                if(operator.equals("/")){
                    equation = num1 / num2 + " " + equation;
                }
            }
        }

        String finalValue = equation.substring(0, equation.length());
        if (!(finalValue.contains("."))) 
        {
            finalValue = equation.substring(0, equation.length()-1);
        }
        String sspace = "";
        String input3 = finalValue; 
        if(input3.contains(".")){ //removes extra 0 that show up after the decimal point
            while(!input3.substring(input3.length()-2, input3.length()-1).equals(".")){
                if(input3.substring(input3.length()-1, input3.length()).equals("0")){
                    input3 = input2.substring(0, input3.length()-1);
                }else{
                    break;
                }
            }
        }
        if (!(input3.contains(".")) ){ //makes sure all numbers have a decimal point
            input3 = input3 + ".0";
        }
        if(input3.length() > 10) //makes the string longer or shorter depending on how long it is to ensure 10 characters
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
        return input2.substring(input2.indexOf("=")+2, input2.length()) + "";
    }

    public double getDoubleValue()
    {
        Double value = Double.parseDouble(input2); 
        return value;
    }
}
