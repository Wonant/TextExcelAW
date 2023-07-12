 
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * C final test
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class CFinal
{
    private Spreadsheet grid;
    
    /**
     * C test
     */
    @Before
    public void initializeGrid()
    {
        grid = new Spreadsheet();
    }
    
    /**
     * C test
     */
    private static String getCellName(int row, int col)
    {
        return "" + (Character.toString((char) ('A' + col))) + (row + 1);
    }
    
    /**
     * C test
     */
    @Test
    public void testSortaCol() 
    {
        grid.processCommand("A1 = 19.1");
        grid.processCommand("A2 = 2.1");
        grid.processCommand("A3 = 607.1");
        grid.processCommand("A4 = 0.01");

        grid.processCommand("soRTa A1-A4");

        assertEquals("0.01",
            grid.getCell(new TestsALL.TestLocation(0, 0)).fullCellText());
        assertEquals("2.1",
            grid.getCell(new TestsALL.TestLocation(1, 0)).fullCellText());
        assertEquals("19.1",
            grid.getCell(new TestsALL.TestLocation(2, 0)).fullCellText());
        assertEquals("607.1",
            grid.getCell(new TestsALL.TestLocation(3, 0)).fullCellText());
    }
    
    /**
     * C test
     */
    @Test
    public void testSortaRow()
    {
        grid.processCommand("A1 = 19.2");
        grid.processCommand("B1 = 2.2");
        grid.processCommand("C1 = 607.2");
        grid.processCommand("D1 = 0.01");

        grid.processCommand("soRTa A1-D1");

        assertEquals("0.01",
            grid.getCell(new TestsALL.TestLocation(0, 0)).fullCellText());
        assertEquals("2.2",
            grid.getCell(new TestsALL.TestLocation(0, 1)).fullCellText());
        assertEquals("19.2",
            grid.getCell(new TestsALL.TestLocation(0, 2)).fullCellText());
        assertEquals("607.2",
            grid.getCell(new TestsALL.TestLocation(0, 3)).fullCellText());
    }
    
    /**
     * C test
     */
    @Test
    public void testSortaRowExtraValues() 
    {

        grid.processCommand("A1 = 19.4");
        grid.processCommand("B1 = 2.4");
        grid.processCommand("C1 = 607.4");
        grid.processCommand("D1 = 0.01");
        grid.processCommand("E1 = 17.4");
        grid.processCommand("A2 = 3.14159");
        grid.processCommand("B2 = \"extras!\"");

        grid.processCommand("soRTa A1-D1");

        assertEquals("0.01",
            grid.getCell(new TestsALL.TestLocation(0, 0)).fullCellText());
        assertEquals( "2.4",
            grid.getCell(new TestsALL.TestLocation(0, 1)).fullCellText());
        assertEquals("19.4",
            grid.getCell(new TestsALL.TestLocation(0, 2)).fullCellText());
        assertEquals("607.4",
            grid.getCell(new TestsALL.TestLocation(0, 3)).fullCellText());

        assertEquals("17.4",
            grid.getCell(new TestsALL.TestLocation(0, 4)).fullCellText());
        assertEquals("3.14159",
            grid.getCell(new TestsALL.TestLocation(1, 0)).fullCellText());
        assertEquals("\"extras!\"",
            grid.getCell(new TestsALL.TestLocation(1, 1)).fullCellText());
    }
    
    /**
     * C test
     */
    @Test
    public void testSortaTwoDimensionalOffset() 
    {

        grid.processCommand("C3 = 19.5");
        grid.processCommand("C4 = 2.5");
        grid.processCommand("D3 = 607.5");
        grid.processCommand("D4 = 0.01");
        grid.processCommand("E1 = 17.5");
        grid.processCommand("A2 = 3.14159");

        grid.processCommand("soRTa C3-D4");

        assertEquals("0.01",
            grid.getCell(new TestsALL.TestLocation(2, 2)).fullCellText());
        assertEquals("2.5",
            grid.getCell(new TestsALL.TestLocation(2, 3)).fullCellText());
        assertEquals("19.5",
            grid.getCell(new TestsALL.TestLocation(3, 2)).fullCellText());
        assertEquals("607.5",
            grid.getCell(new TestsALL.TestLocation(3, 3)).fullCellText());

        assertEquals("17.5",
            grid.getCell(new TestsALL.TestLocation(0, 4)).fullCellText());
        assertEquals("3.14159",
            grid.getCell(new TestsALL.TestLocation(1, 0)).fullCellText());
    }
    
    /**
     * C test
     */
    @Test
    public void testSortaTwoDimensional() 
    {

        grid.processCommand("A1 = 19.3");
        grid.processCommand("A2 = 2.3");
        grid.processCommand("B1 = 607.3");
        grid.processCommand("B2 = 0.01");

        grid.processCommand("SoRTa A1-B2");

        assertEquals("0.01",
            grid.getCell(new TestsALL.TestLocation(0, 0)).fullCellText());
        assertEquals("2.3",
            grid.getCell(new TestsALL.TestLocation(0, 1)).fullCellText());
        assertEquals("19.3",
            grid.getCell(new TestsALL.TestLocation(1, 0)).fullCellText());
        assertEquals("607.3",
            grid.getCell(new TestsALL.TestLocation(1, 1)).fullCellText());
    }
    
    /**
     * C test
     */
    @Test
    public void testComparableText()
    {
        grid.processCommand("A1 = \"chocolate\"");
        grid.processCommand("B1 = \"chocolate\"");
        grid.processCommand("C1 = \"sauce\"");

        Cell a1 = grid.getCell(new TestsALL.TestLocation(0, 0));
        Cell b1 = grid.getCell(new TestsALL.TestLocation(0, 1));
        Cell c1 = grid.getCell(new TestsALL.TestLocation(0, 2));

        // If any of these CRASH (RED), then you did not properly implement the
        // Comparable interface on your TextCell class
        Comparable<Object> comparableA1 = (Comparable<Object>) a1;
        Comparable<Object> comparableC1 = (Comparable<Object>) c1;

        assertEquals("Comparing string with itself should give 0",
            0, comparableA1.compareTo(b1));
        assertTrue("Comparing string with later string should return negative",
            comparableA1.compareTo(c1) < 0);
        assertTrue(
            "Comparing string with earlier string should return positive",
            comparableC1.compareTo(a1) > 0);
    }
    
    /**
     * C test
     */
    @Test
    public void testComparableValue()
    {
        grid.processCommand("A1 = 5.5");
        grid.processCommand("B1 = 5.5");
        grid.processCommand("C1 = 5.6");
        grid.processCommand("D1 = -5.7");

        Cell a1 = grid.getCell(new TestsALL.TestLocation(0, 0));
        Cell b1 = grid.getCell(new TestsALL.TestLocation(0, 1));
        Cell c1 = grid.getCell(new TestsALL.TestLocation(0, 2));
        Cell d1 = grid.getCell(new TestsALL.TestLocation(0, 3));

        // If any of these CRASH (RED), then you did not properly implement the
        // Comparable interface on your ValueCell class
        Comparable<Object> comparableA1 = (Comparable<Object>) a1;
        Comparable<Object> comparableC1 = (Comparable<Object>) c1;

        assertEquals("Comparing value with itself should give 0",
            0, comparableA1.compareTo(b1));
        assertTrue("Comparing value with larger value should return negative",
            comparableA1.compareTo(c1) < 0);
        assertTrue("Comparing value with smaller value should return positive",
            comparableC1.compareTo(a1) > 0);
        assertTrue("Comparing value with smaller value should return positive",
            comparableA1.compareTo(d1) > 0);
    }        
    
    /**
     * C test
     */
    @Test
    public void testComparablePercent()
    {
        grid.processCommand("A1 = 55%");
        grid.processCommand("B1 = 55%");
        grid.processCommand("C1 = 56%");
        grid.processCommand("D1 = 157%");

        Cell a1 = grid.getCell(new TestsALL.TestLocation(0, 0));
        Cell b1 = grid.getCell(new TestsALL.TestLocation(0, 1));
        Cell c1 = grid.getCell(new TestsALL.TestLocation(0, 2));
        Cell d1 = grid.getCell(new TestsALL.TestLocation(0, 3));

        // If any of these CRASH (RED), then you did not properly implement the
        // Comparable interface on your PercentCell class
        Comparable<Object> comparableA1 = (Comparable<Object>) a1;
        Comparable<Object> comparableC1 = (Comparable<Object>) c1;
        Comparable<Object> comparableD1 = (Comparable<Object>) d1;

        assertEquals("Comparing percent with itself should give 0",
            0, comparableA1.compareTo(b1));
        assertTrue(
            "Comparing percent with larger percent should return negative",
            comparableA1.compareTo(c1) < 0);
        assertTrue(
            "Comparing percent with smaller percent should return positive",
            comparableD1.compareTo(a1) > 0);
    }              
}
