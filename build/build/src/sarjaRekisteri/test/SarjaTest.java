package sarjaRekisteri.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import sarjaRekisteri.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.04.24 22:32:20 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class SarjaTest {



  // Generated by ComTest BEGIN
  /** testGetNimi78 */
  @Test
  public void testGetNimi78() {    // Sarja: 78
    Sarja tw = new Sarja(); 
    tw.vastaaSarja(); 
    assertEquals("From: Sarja line: 81", "The Witcher", tw.getNimi()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testRekisteroi121 */
  @Test
  public void testRekisteroi121() {    // Sarja: 121
    Sarja s1 = new Sarja(); 
    assertEquals("From: Sarja line: 123", 0, s1.getId()); 
    s1.rekisteroi(); 
    Sarja s2 = new Sarja(); 
    s2.rekisteroi(); 
    int n1 = s1.getId(); 
    int n2 = s2.getId(); 
    assertEquals("From: Sarja line: 129", n2-1, n1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testToString154 */
  @Test
  public void testToString154() {    // Sarja: 154
    Sarja s = new Sarja(); 
    s.rekisteroi(); 
    int sid = s.getId(); 
    s.parse("|Witcher|1|8|2019|3.5|true"); 
    assertEquals("From: Sarja line: 159", true, s.toString().startsWith("" + sid + "|Witcher|1|8|2019|3.5|true|")); 
  } // Generated by ComTest END
}