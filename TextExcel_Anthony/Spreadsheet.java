import java.util.ArrayList;
/**
 * Spreadsheet
 * 
 * @author (your name)
 * @version (a version number or a date)
 */

public class Spreadsheet
{
    Cell[][] spreadsheet = new Cell[20][12];
    private int row;
    private int col;
    public Spreadsheet()
    {
        for(int i=0; i<getRows(); i++){
            for (int j = 0; j <getCols(); j++)
            {
                spreadsheet[i][j] = new EmptyCell(); //fills the initial spreadsheet with emptyCells
            }
        }
    }

    public String processCommand(String command)
    {
        if (command.length() > 1)
        {
            if(("123456789".contains(command.substring(1,2))) && ("ABCDEFGHIJKLM".contains(command.substring(0,1).toUpperCase()))){ //if the first character in input is a number
                if((command.length() > 3) && ("\"".contains(command.substring(command.indexOf(" ") + 3, command.indexOf(" ") + 4)))){//if inout contains "" must be a textCell
                    SpreadsheetLocation accessCell = new SpreadsheetLocation(command.substring(0, command.indexOf(" ")));//uses the first part, "A1", to create a spreadsheetLocation
                    spreadsheet[accessCell.getRow()][accessCell.getCol()] = new TextCell(command.substring(command.indexOf(" ") + 4,command.length()-1)); //creates a textCell at location and fills with string
                    return getGridText();
                }
                else if(((command.contains("(")) && (command.contains(")")))){ //if it contains (), it must be a formulaCell
                    SpreadsheetLocation accessCell = new SpreadsheetLocation(command.substring(0, command.indexOf(" ")));
                    spreadsheet[accessCell.getRow()][accessCell.getCol()] = new FormulaCell(command, this);
                    return getGridText();
                } else if((command.length() > 3) && (( "-".contains((command.substring(command.indexOf(" ") + 3, command.indexOf(" ") + 4)))) ||("01234567898".contains(command.substring(command.indexOf(" ") + 3, command.indexOf(" ") + 4)))) && (command.substring(command.length()-1, command.length()).equals("%"))){
                    //if input contains % must be a percent cell
                    SpreadsheetLocation accessCell = new SpreadsheetLocation(command.substring(0, command.indexOf(" ")));
                    spreadsheet[accessCell.getRow()][accessCell.getCol()] = new PercentCell(command.substring(command.indexOf(" ") + 3,command.length()-1));
                    return getGridText();
                } else if((command.length() > 3) && (( "-".contains((command.substring(command.indexOf(" ") + 3, command.indexOf(" ") + 4)))) ||("01234567898".contains(command.substring(command.indexOf(" ") + 3, command.indexOf(" ") + 4))))){
                    //if the input string starts with a number and doesnt contain a percent it must be a value cell
                    SpreadsheetLocation accessCell = new SpreadsheetLocation(command.substring(0, command.indexOf(" ")));
                    spreadsheet[accessCell.getRow()][accessCell.getCol()] = new ValueCell(command.substring(command.indexOf(" ") + 3,command.length()));
                    return getGridText();
                }else{ // if none of the other tests are true it must be a access command, "A2", so return the value at that position
                    SpreadsheetLocation accessCell = new SpreadsheetLocation(command);
                    return spreadsheet[accessCell.getRow()][accessCell.getCol()].fullCellText();
                }
            } else if (command.toUpperCase().equals("CLEAR")) //if clear is input reset the grid
            {
                for(int i=0; i<getRows(); i++){
                    for (int j = 0; j <getCols(); j++)
                    {
                        spreadsheet[i][j] = new EmptyCell();
                    }
                }
                return getGridText();
            } else if(command.length() > 7) {
                if ((command.substring(0,5).toUpperCase().equals("CLEAR")) && ("123456789".contains(command.substring(7,8))) && ("ABCDEFGHIJKLM".contains(command.substring(6,7).toUpperCase())))
                {// if there is a clear command with a position after it clear the position
                    SpreadsheetLocation accessCell2 = new SpreadsheetLocation(command.substring(command.indexOf(" ") + 1, command.length()));
                    spreadsheet[accessCell2.getRow()][accessCell2.getCol()] = new EmptyCell();
                    return getGridText();
                }
                else if ((command.substring(0,5).toUpperCase().equals("SORTA")) || ((command.substring(0,5).toUpperCase().equals("SORTD"))))
                {
                    String alphabet2 = "ABCDEFGHIJKL";
                    String startCell = command.substring(command.indexOf(" ")+1,command.indexOf("-"));
                    int startNum = Integer.parseInt(startCell.substring(1,startCell.length()));
                    String startLetter = startCell.substring(0,1).toUpperCase();
                    int multiplier = alphabet2.indexOf(startLetter);
                    int realStart = (multiplier * 20) + startNum;
                    String endCell = command.substring(command.indexOf("-")+1, command.length());
                    int endNum = Integer.parseInt(endCell.substring(1,endCell.length()));
                    String endLetter = endCell.substring(0,1).toUpperCase();
                    int multiplier2 = alphabet2.indexOf(endLetter);
                    int realEnd = (multiplier2 * 20) + endNum; 
                    ArrayList<Double> doubleArray = new ArrayList<Double>();
                    ArrayList<String> stringArray = new ArrayList<String>();
                    String TOF = "Y";
                    System.out.println("Mr.Wang is an AWESOME AP CS Teacher! :D"); //TRUE
                    for(int i = alphabet2.indexOf(startLetter); i <= alphabet2.indexOf(endLetter); i++){
                        for(int j = startNum; j <= endNum; j++) {
                            String cellName = alphabet2.substring(i, i + 1) + j;
                            SpreadsheetLocation cellAccess = new SpreadsheetLocation(cellName);

                            if (getCell(cellAccess) instanceof RealCell)//add all the realCells to an array list for sorting
                            {
                                if (getCell(cellAccess) instanceof PercentCell)
                                {
                                    doubleArray.add(Double.parseDouble(getCell(cellAccess).fullCellText()));//if it is a percent cell get the fullCellText rather than abbreviatedCellText
                                } else {
                                    doubleArray.add(Double.parseDouble(getCell(cellAccess).abbreviatedCellText().substring(0,getCell(cellAccess).abbreviatedCellText().indexOf(" "))));
                                    //if it is not a percentCell add the abbreviated cell text without the spaces (so that equations are solved)
                                }
                                TOF = "t"; 
                            }
                            if (getCell(cellAccess) instanceof TextCell)
                            {
                                stringArray.add(getCell(cellAccess).abbreviatedCellText().substring(0,getCell(cellAccess).abbreviatedCellText().indexOf(" ")));
                                //add all text cells to the array
                                TOF = "f";
                            }

                        }
                    }
                    if (TOF.equals("f"))
                    {
                        int counter = 0;
                        stringArray = StringInsertionSort(stringArray);
                        if (command.substring(0,5).toUpperCase().equals("SORTD"))
                        {
                            ArrayList<String> reversedStringArray = new ArrayList<String>();
                            for(int varReverse2 = stringArray.size() - 1; varReverse2 >= 0; varReverse2--)
                            {
                                reversedStringArray.add(stringArray.get(varReverse2));
                            }
                            stringArray = reversedStringArray;
                        }
                        for ( int j2 = startNum; j2 <= endNum; j2++) {
                            for (int i2 = alphabet2.indexOf(startLetter); i2 <= alphabet2.indexOf(endLetter); i2++){
                                String cellName2 = alphabet2.substring(i2, i2 + 1) + j2;
                                SpreadsheetLocation cellAccess2 = new SpreadsheetLocation(cellName2);
                                spreadsheet[cellAccess2.getRow()][cellAccess2.getCol()] = new TextCell(stringArray.get(counter));
                                counter++;
                            }
                        }
                        return getGridText();
                    }

                    if (TOF.equals("t"))
                    {
                        int counter = 0;
                        doubleArray = InsertionSort(doubleArray);
                        if (command.substring(0,5).toUpperCase().equals("SORTD"))
                        {
                            ArrayList<Double> reversedDoubleArray = new ArrayList<Double>();
                            for(int varReverse = doubleArray.size() - 1; varReverse >= 0; varReverse--)
                            {
                                reversedDoubleArray.add(doubleArray.get(varReverse));
                            }
                            doubleArray = reversedDoubleArray;
                        }
                        for ( int j2 = startNum; j2 <= endNum; j2++) {
                            for (int i2 = alphabet2.indexOf(startLetter); i2 <= alphabet2.indexOf(endLetter); i2++){
                                String cellName2 = alphabet2.substring(i2, i2 + 1) + j2;
                                SpreadsheetLocation cellAccess2 = new SpreadsheetLocation(cellName2);
                                spreadsheet[cellAccess2.getRow()][cellAccess2.getCol()] = new ValueCell(doubleArray.get(counter) + "");
                                counter++;
                            }
                        }
                        return getGridText();
                    }
                }
            }
        }
        return "";        
    }

    public int getRows()
    {
        // TODO Auto-generated method stub
        return spreadsheet.length;
    }

    public int getCols()
    {
        // TODO Auto-generated method stub
        return spreadsheet[0].length;
    }

    public Cell getCell(Location loc)
    {
        // TODO Auto-generated method stub
        return spreadsheet[loc.getRow()][loc.getCol()];
    }

    public String getCellText(Location loc)
    {
        // TODO Auto-generated method stub
        return spreadsheet[loc.getRow()][loc.getCol()].fullCellText();
    }

    public ArrayList InsertionSort(ArrayList<Double> list){ 
        double n;
        double temp;
        for (int i = 1; i < list.size(); i++){
            n = list.get(i);
            int j = i - 1;

            while (j >= 0 && n < list.get(j)) {    
                temp = list.get(j);

                list.set(j, list.get(j+1));

                list.set((j + 1), temp);
                j--;
            }
        }
        return list;
    }

    public ArrayList StringInsertionSort(ArrayList<String> list)
    {
        String n;
        String temp;
        for (int i = 1; i < list.size(); i ++)
        {
            n = list.get(i).toUpperCase();
            int j = i - 1;
            while ( j >=0 && (n.compareTo(list.get(j).toUpperCase()) < 0)) 
            {
                temp = list.get(j);
                list.set(j, list.get(j+1));
                list.set((j+1), temp);
                j--;
            }
        }
        return list;
    }

    public String getGridText()
    {
        String gridText = "   |A         |B         |C         |D         |E         |F         |G         |H         |I         |J         |K         |L         |\n";
        for(int i=0; i<getRows(); i++){
            String swoosh = (i+1 + "  ");
            gridText = gridText + swoosh.substring(0,3);
            for (int j = 0; j <getCols(); j++)
            {
                gridText = gridText + ("|" + spreadsheet[i][j].abbreviatedCellText());
            }
            gridText = gridText +("|\n");
        }
        return gridText;
    }

}
