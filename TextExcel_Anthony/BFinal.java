
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * B final test
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class BFinal
{
    // Tests for Part B, Final submission

    private Spreadsheet grid;

    /**
     * B test
     */
    @Before
    public void initializeGrid()
    {
        grid = new Spreadsheet();
    }

    /**
     * B test
     */
    @Test
    public void testProcessCommand()
    {
        TestsALL.Helper helper = new TestsALL.Helper();
        helper.setItem(0, 5,  "1.0");
        helper.setItem(1, 5, "1.0");
        helper.setItem(2,  5,  "2.0");
        helper.setItem(3,  5, "3.0");
        helper.setItem(4, 5, "5.0");
        grid.processCommand("F1 = 1");
        grid.processCommand("F2 = ( 1 )");
        grid.processCommand("F3 = ( F2 + F1 )");
        grid.processCommand("F4 = ( F2 + F3 )");
        String actual = grid.processCommand("F5 = ( F3 + F4 )");
        assertEquals("grid", helper.getText(), actual);
        String inspected = grid.processCommand("F4");
        assertEquals("inspected", "( F2 + F3 )", inspected);
        String updated = grid.processCommand("F3 = 11.5");
        helper.setItem(2, 5, "11.5");
        helper.setItem(3, 5, "12.5");
        helper.setItem(4, 5, "24.0");
        assertEquals("updated grid", helper.getText(), updated);
        String updatedInspected = grid.processCommand("F4");
        assertEquals("updated inspected", "( F2 + F3 )", updatedInspected);
    }

    /**
     * B test
     */
    @Test
    public void testFormulaAssignment()
    {
        for (int row = 1; row < 11; row++)
        {
            for (int col = 1; col < 7; col++)
            {
                String cellName = Character.toString(
                        (char)('A' + col)) + (row + 1);
                grid.processCommand(cellName + " = 1");
            }
        }
        String formula1 = "( 4 * 5.5 / 2 + 1 - -11.5 )";
        String formula2 = "( sUm B6-g11 )";
        String formula3 = "( AvG f8-F9 )";
        grid.processCommand("K9 = " + formula1);
        grid.processCommand("J10 = " + formula2);
        grid.processCommand("I11 = " + formula3);
        Cell cell1 = grid.getCell(new TestsALL.TestLocation(8, 10));
        Cell cell2 = grid.getCell(new TestsALL.TestLocation(9, 9));
        Cell cell3 = grid.getCell(new TestsALL.TestLocation(10, 8));
        assertEquals("cell length 1", 10, cell1.abbreviatedCellText().length());
        assertEquals("inspection 1", formula1, cell1.fullCellText());
        assertEquals("cell length 2", 10, cell2.abbreviatedCellText().length());
        assertEquals("inspection 2", formula2, cell2.fullCellText());
        assertEquals("cell length 3", 10, cell3.abbreviatedCellText().length());
        assertEquals("inspection 3", formula3, cell3.fullCellText());
    }

    /**
     * B test
     */
    @Test
    public void testReferences()
    {
        String formula = "( A1 * A2 / A3 + A4 - A5 )";
        grid.processCommand("A1 = 5.4");
        grid.processCommand("A2 = 3.5");
        grid.processCommand("A3 = -1.4");
        grid.processCommand("A4 = 27.4");
        grid.processCommand("A5 = 11.182");
        grid.processCommand("L18 = " + formula);
        Cell cell = grid.getCell(new TestsALL.TestLocation(17, 11));
        assertEquals("reference formula value length", 
            10, cell.abbreviatedCellText().length());
        assertEquals("reference formula value",
            2.718, Double.parseDouble(cell.abbreviatedCellText()), 1e-6);
        assertEquals("reference formula inspection",
            formula, cell.fullCellText());
        grid.processCommand("A4 = 25.4");
        assertEquals("updated value length",
            10, cell.abbreviatedCellText().length());
        assertEquals("updated value",
            0.718, Double.parseDouble(cell.abbreviatedCellText()), 1e-6);
        assertEquals("updated inspection",
            formula, cell.fullCellText());
    }

    /**
     * B test
     */
    @Test
    public void testTransitiveReferences()
    {
        grid.processCommand("F1 = 1");
        grid.processCommand("F2 = ( 1 )");
        grid.processCommand("F3 = ( F2 + F1 )");
        grid.processCommand("F4 = ( F2 + F3 )");
        grid.processCommand("F5 = ( F3 + F4 )");
        Cell cell = grid.getCell(new TestsALL.TestLocation(4, 5));
        assertEquals("Fib(5)", TestsALL.Helper.format("5.0"),
            cell.abbreviatedCellText());
        assertEquals("inspection", "( F3 + F4 )", cell.fullCellText());
    }

    /**
     * B test
     */
    @Test
    public void testMixedReferencesAndConstantsWithOrWithoutPrecedence()
    { // Initially with precedence, 39.264.  without precedence, 9.564
        // Then with precedence 37.264, 13.564 without precedence
        String formula = "( 3.0 + A1 * A2 / -1.4 + A4 - A5 * -2.0 )";
        grid.processCommand("A1 = 5.4");
        grid.processCommand("A2 = 3.5");
        grid.processCommand("A4 = 27.4");
        grid.processCommand("A5 = 11.182");
        grid.processCommand("L18 = " + formula);
        Cell cell = grid.getCell(new TestsALL.TestLocation(17, 11));
        assertEquals("reference formula value length",
            10, cell.abbreviatedCellText().length());
        double resultInitial = Double.parseDouble(cell.abbreviatedCellText());
        if (resultInitial > 10)
        {
            assertEquals("initial value", 39.264, resultInitial, 1e-6);
        }
        else
        {
            assertEquals("initial value", 9.564, resultInitial, 1e-6);
        }
        assertEquals("initial formula inspection",
            formula, cell.fullCellText());
        grid.processCommand("A4 = 25.4");
        assertEquals("updated value length",
            10, cell.abbreviatedCellText().length());
        double resultUpdated = Double.parseDouble(cell.abbreviatedCellText());
        if (resultUpdated > 15)
        {
            assertEquals("updated value", 37.264, resultUpdated, 1e-6);
        }
        else
        {
            assertEquals("updated value", 13.564, resultUpdated, 1e-6);
        }
        assertEquals("updated inspection", formula, cell.fullCellText());
    }

    /**
     * B test
     */
    private String getReferenceFormulaString(int col)
    {
        String ret = "( 0.2";
        String[] operators = {" + ", " - ", " * ", " / "};
        int operator = 0;
        String colS = "" + (char) ('A' + col);

        for (int row = 1; row <= 18; row++)
        {
            ret += operators[operator] + colS + row;
            operator = (operator + 1) % 4;
        }

        ret += " )";
        return ret;
    }

    /**
     * B test
     */
    @Test
    public void testTransitiveNontrivialReferences()
    {
        grid.processCommand("F1 = 1");
        grid.processCommand("F2 = ( 1 )");
        grid.processCommand("F3 = ( 1 + 3 + F2 + F1 - 3 - 1 )");
        grid.processCommand("F4 = ( 1.0 * F2 + F3 - 0.0 )");
        String outerFormula = "( 1.0 - 1 + F3 + F4 * 1.0 )";
        grid.processCommand("F5 = " + outerFormula);
        Cell cell = grid.getCell(new TestsALL.TestLocation(4, 5));
        assertEquals("Fib(5)",
            TestsALL.Helper.format("5.0"), cell.abbreviatedCellText());
        assertEquals("inspection", outerFormula, cell.fullCellText());
    }

    /**
     * B test
     */
    @Test
    public void testSumSingle()
    {
        grid.processCommand("A15 = 37.05");
        grid.processCommand("A16 = ( SuM A15-A15 )");
        Cell cell = grid.getCell(new TestsALL.TestLocation(15, 0));
        assertEquals("sum single cell",
            TestsALL.Helper.format("37.05"), cell.abbreviatedCellText());
    }

    /**
     * B test
     */
    @Test
    public void testAvgSingle()
    {
        grid.processCommand("A1 = -9");
        grid.processCommand("A2 = ( 3 * A1 )");
        grid.processCommand("B1 = ( avg A2-A2 )");
        Cell cell = grid.getCell(new TestsALL.TestLocation(0, 1));
        assertEquals("avg single cell",
            TestsALL.Helper.format("-27.0"), cell.abbreviatedCellText());
    }

    /**
     * B test
     */
    @Test
    public void testProcessCommandWithFunctions()
    {
        TestsALL.Helper helper = new TestsALL.Helper();
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                String cellId = "" + (char)('A' + j) + (i + 1);
                grid.processCommand(cellId + " = " + (i * j));
                helper.setItem(i, j, (i * j) + ".0");
            }
        }
        String first = grid.processCommand("G8 = ( sum A1-E4 )");
        helper.setItem(7, 6, "60.0");
        assertEquals("grid with sum", helper.getText(), first);
        String second = grid.processCommand("G9 = ( avg A1-E4 )");
        helper.setItem(8, 6, "3.0");
        assertEquals("grid with sum and avg", helper.getText(), second);
        String updated = grid.processCommand("E4 = ( sum A4-D4 )");
        helper.setItem(3, 4, "18.0");
        helper.setItem(7, 6, "66.0");
        helper.setItem(8, 6, "3.3");
        assertEquals("updated grid", helper.getText(), updated);
    }

    /**
     * B test
     */
    @Test
    public void testSumSingleNegative()
    {
        grid.processCommand("A15 = -37.05");
        grid.processCommand("A16 = ( SuM A15-A15 )");
        Cell cell = grid.getCell(new TestsALL.TestLocation(15, 0));
        assertEquals("sum single cell",
            TestsALL.Helper.format("-37.05"), cell.abbreviatedCellText());
    }
}
