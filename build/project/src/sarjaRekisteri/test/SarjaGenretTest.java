package sarjaRekisteri.test;
// Generated by ComTest BEGIN
import sarjaRekisteri.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.04.24 15:36:12 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class SarjaGenretTest {



  // Generated by ComTest BEGIN
  /** testLisaa36 */
  @Test
  public void testLisaa36() {    // SarjaGenret: 36
    SarjaGenret sgt = new SarjaGenret(); 
    SarjaGenre sg1 = new SarjaGenre(1,1); sgt.lisaa(sg1); 
    SarjaGenre sg2 = new SarjaGenre(2,3); sgt.lisaa(sg2); 
    SarjaGenre sg3 = new SarjaGenre(3,4); sgt.lisaa(sg3); 
    SarjaGenre sg4 = new SarjaGenre(5,4); sgt.lisaa(sg4); 
    List<SarjaGenre> loytyneet; 
    loytyneet = sgt.annaGenret(1); 
    assertEquals("From: SarjaGenret line: 48", 1, loytyneet.size()); 
    assertEquals("From: SarjaGenret line: 49", 1, loytyneet.get(0).getGenreID()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testMuutaSG216 */
  @Test
  public void testMuutaSG216() {    // SarjaGenret: 216
    SarjaGenret sgt = new SarjaGenret(); 
    SarjaGenre sg1 = new SarjaGenre(1,1); 
    sgt.lisaa(sg1); 
    assertEquals("From: SarjaGenret line: 220", 1, sg1.getGenreID()); 
    SarjaGenre sg2 = new SarjaGenre(1,2); 
    sgt.muutaSG(sg2); 
    assertEquals("From: SarjaGenret line: 225", 2, sg1.getGenreID()); 
  } // Generated by ComTest END
}