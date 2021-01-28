package sarjaRekisteri.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import sarjaRekisteri.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.04.24 15:41:13 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class SarjaGenreTest {



  // Generated by ComTest BEGIN
  /** testToString51 */
  @Test
  public void testToString51() {    // SarjaGenre: 51
    SarjaGenre sg = new SarjaGenre(); 
    sg.parse("10|10");
    assertEquals("From: SarjaGenre line: 54", true, sg.toString().startsWith("10|10")); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testGetGenreID83 */
  @Test
  public void testGetGenreID83() {    // SarjaGenre: 83
    SarjaGenre sg = new SarjaGenre(1, 2); 
    assertEquals("From: SarjaGenre line: 85", 2, sg.getGenreID()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testGetSarjaID94 */
  @Test
  public void testGetSarjaID94() {    // SarjaGenre: 94
    SarjaGenre sg = new SarjaGenre(1, 2); 
    assertEquals("From: SarjaGenre line: 96", 1, sg.getSarjaID()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testSetGenreID115 */
  @Test
  public void testSetGenreID115() {    // SarjaGenre: 115
    SarjaGenre sg = new SarjaGenre(1, 0); 
    assertEquals("From: SarjaGenre line: 117", 0, sg.getGenreID()); 
    sg.setGenreID(2); 
    assertEquals("From: SarjaGenre line: 119", 2, sg.getGenreID()); 
  } // Generated by ComTest END
}