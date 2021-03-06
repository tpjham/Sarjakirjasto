package sarjaRekisteri.test;
// Generated by ComTest BEGIN
import sarjaRekisteri.*;
import java.util.*;
import java.io.*;
import static org.junit.Assert.*;
import org.junit.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.04.24 15:32:11 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class SarjaRekisteriTest {


  // Generated by ComTest BEGIN  // SarjaRekisteri: 10
  private SarjaRekisteri rekisteri; 
  private Sarja s1; 
  private Sarja s2; 
  private int sid1; 
  private int sid2; 
  private Genre g1; 
  private Genre g2; 
  private int gid1; 
  private int gid2; 
  private SarjaGenre sg1; 
  private SarjaGenre sg2; 

  public void alustaRekisteri() {
  rekisteri = new SarjaRekisteri(); 
  s1 = new Sarja(); s1.vastaaSarja(); s1.rekisteroi(); 
  s2 = new Sarja(); s2.vastaaSarja(); s2.rekisteroi(); 
  sid1 = s1.getId(); 
  sid2 = s2.getId(); 
  g1 = new Genre(); g1.vastaaGenre(); g1.rekisteroi(); 
  g2 = new Genre(); g2.vastaaGenre(); g2.rekisteroi(); 
  g2.setNimi("Komedia"); 
  gid1 = g1.getGenreID(); 
  gid2 = g2.getGenreID(); 
  sg1 = new SarjaGenre(sid1, gid1); 
  sg2 = new SarjaGenre(sid2, gid2); 

  try {
  rekisteri.lisaa(s1); 
  rekisteri.lisaa(s2); 
  rekisteri.lisaa(g1); 
  rekisteri.lisaa(g2); 
  rekisteri.lisaa(sg1); 
  rekisteri.lisaa(sg2); 
  } catch ( Exception e ) {
  System.err.println(e.getMessage()); 
  }
  }
  // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testPoista67 
   * @throws Exception when error
   */
  @Test
  public void testPoista67() throws Exception {    // SarjaRekisteri: 67
    alustaRekisteri(); 
    assertEquals("From: SarjaRekisteri line: 70", 2, rekisteri.etsi("",0).size()); 
    assertEquals("From: SarjaRekisteri line: 71", 2, rekisteri.getSarjatLkm()); 
    assertEquals("From: SarjaRekisteri line: 72", 1, rekisteri.poista(s1));
    assertEquals("From: SarjaRekisteri line: 73", 1, rekisteri.getSarjatLkm()); 
    assertEquals("From: SarjaRekisteri line: 74", 1, rekisteri.etsi("",0).size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testLisaa88 */
  @Test
  public void testLisaa88() {    // SarjaRekisteri: 88
    Sarjat sarjat = new Sarjat(); 
    Sarja s1 = new Sarja(), s2 = new Sarja(); 
    assertEquals("From: SarjaRekisteri line: 92", 0, sarjat.getLkm()); 
    sarjat.lisaa(s1); assertEquals("From: SarjaRekisteri line: 93", 1, sarjat.getLkm()); 
    sarjat.lisaa(s2); assertEquals("From: SarjaRekisteri line: 94", 2, sarjat.getLkm()); 
    sarjat.lisaa(s1); assertEquals("From: SarjaRekisteri line: 95", 3, sarjat.getLkm()); 
    assertEquals("From: SarjaRekisteri line: 96", s1, sarjat.anna(0)); 
    assertEquals("From: SarjaRekisteri line: 97", s2, sarjat.anna(1)); 
    assertEquals("From: SarjaRekisteri line: 98", s1, sarjat.anna(2)); 
    assertEquals("From: SarjaRekisteri line: 99", false, sarjat.anna(1) == s1); 
    assertEquals("From: SarjaRekisteri line: 100", true, sarjat.anna(1) == s2); 
    try {
    assertEquals("From: SarjaRekisteri line: 101", s1, sarjat.anna(3)); 
    fail("SarjaRekisteri: 101 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    sarjat.lisaa(s1); assertEquals("From: SarjaRekisteri line: 102", 4, sarjat.getLkm()); 
    sarjat.lisaa(s1); assertEquals("From: SarjaRekisteri line: 103", 5, sarjat.getLkm()); 
    sarjat.lisaa(s1); 
    assertEquals("From: SarjaRekisteri line: 105", 6, sarjat.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testKorvaaTaiLisaa116 
   * @throws SailoException when error
   */
  @Test
  public void testKorvaaTaiLisaa116() throws SailoException {    // SarjaRekisteri: 116
    alustaRekisteri(); 
    assertEquals("From: SarjaRekisteri line: 119", 2, rekisteri.etsi("",0).size()); 
    rekisteri.korvaaTaiLisaa(s1); 
    assertEquals("From: SarjaRekisteri line: 121", 2, rekisteri.etsi("",0).size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testLisaa132 */
  @Test
  public void testLisaa132() {    // SarjaRekisteri: 132
    Genret gret = new Genret(); 
    Genre fant1 = new Genre(); fant1.rekisteroi(); fant1.vastaaGenre(); gret.lisaa(fant1); 
    Genre fant2 = new Genre(); fant2.rekisteroi(); fant2.setNimi("Komedia"); gret.lisaa(fant2); 
    Genre fant3 = new Genre(); fant3.rekisteroi(); fant3.setNimi("Kauhu"); gret.lisaa(fant3); 
    Genre fant4 = new Genre(); fant4.rekisteroi(); fant4.setNimi("Draama"); gret.lisaa(fant4); 
    Iterator<Genre> i2=gret.iterator(); 
    assertEquals("From: SarjaRekisteri line: 143", fant1, i2.next()); 
    assertEquals("From: SarjaRekisteri line: 144", fant2, i2.next()); 
    assertEquals("From: SarjaRekisteri line: 145", fant3, i2.next()); 
    assertEquals("From: SarjaRekisteri line: 146", fant4, i2.next()); 
    try {
    assertEquals("From: SarjaRekisteri line: 147", fant4, i2.next()); 
    fail("SarjaRekisteri: 147 Did not throw NoSuchElementException");
    } catch(NoSuchElementException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testEtsi168 
   * @throws CloneNotSupportedException when error
   * @throws SailoException when error
   */
  @Test
  public void testEtsi168() throws CloneNotSupportedException, SailoException {    // SarjaRekisteri: 168
    alustaRekisteri(); 
    Sarja s3 = new Sarja(); s3.vastaaSarja(); 
    s3.rekisteroi(); s3.setNimi("Simpsonit"); 
    rekisteri.lisaa(s3); 
    Genre g3 = new Genre(); g3.rekisteroi(); g3.setNimi("Romanssi"); 
    SarjaGenre sg3 = new SarjaGenre(s3.getId(), g3.getGenreID()); 
    rekisteri.lisaa(g3); 
    rekisteri.lisaa(sg3); 
    Collection<Sarja> loytyneet = rekisteri.etsi("roma",1); 
    assertEquals("From: SarjaRekisteri line: 179", 1, loytyneet.size()); 
    Iterator<Sarja> it = loytyneet.iterator(); 
    assertEquals("From: SarjaRekisteri line: 181", true, it.next() == s3); 
    loytyneet = rekisteri.etsi("Sim",0); 
    assertEquals("From: SarjaRekisteri line: 184", 1, loytyneet.size()); 
    it = loytyneet.iterator(); 
    assertEquals("From: SarjaRekisteri line: 186", true, it.next() == s3); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testAnnaSarja215 
   * @throws IndexOutOfBoundsException when error
   * @throws SailoException when error
   */
  @Test
  public void testAnnaSarja215() throws IndexOutOfBoundsException, SailoException {    // SarjaRekisteri: 215
    alustaRekisteri(); 
    Sarja s3 = new Sarja(); 
    s3.rekisteroi(); 
    rekisteri.lisaa(s3); 
    assertEquals("From: SarjaRekisteri line: 222", s1, rekisteri.annaSarja(0)); 
    assertEquals("From: SarjaRekisteri line: 223", s2, rekisteri.annaSarja(1)); 
    assertEquals("From: SarjaRekisteri line: 224", s3, rekisteri.annaSarja(2)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta278 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta278() throws SailoException {    // SarjaRekisteri: 278
    SarjaRekisteri rekisteri = new SarjaRekisteri(); 
    Sarja aku1 = new Sarja(); aku1.vastaaSarja(); aku1.rekisteroi(); 
    Sarja aku2 = new Sarja(); aku2.vastaaSarja(); aku2.rekisteroi(); 
    Genre fantasia1 = new Genre(); fantasia1.vastaaGenre(); fantasia1.rekisteroi(); 
    Genre fantasia11 = new Genre(); fantasia11.vastaaGenre(); fantasia11.rekisteroi(); 
    fantasia11.setNimi("Komedia"); 
    Genre fantasia22 = new Genre(); fantasia22.vastaaGenre(); fantasia22.rekisteroi(); 
    Genre fantasia12 = new Genre(); fantasia12.vastaaGenre(); fantasia12.rekisteroi(); 
    Genre fantasia23 = new Genre(); fantasia23.vastaaGenre(); fantasia23.rekisteroi(); 
    String hakemisto = "testisarja"; 
    File dir = new File(hakemisto); 
    File ftied  = new File(hakemisto+"/sarjat.dat"); 
    File fhtied = new File(hakemisto+"/genret.dat"); 
    File fhhtied = new File(hakemisto+"/sarjagenret.dat"); 
    dir.mkdir(); 
    ftied.delete(); 
    fhtied.delete(); 
    fhhtied.delete(); 
    try {
    rekisteri.lueTiedostosta(hakemisto); 
    fail("SarjaRekisteri: 303 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    rekisteri.lisaa(aku1); 
    rekisteri.lisaa(aku2); 
    rekisteri.lisaa(fantasia1); 
    rekisteri.lisaa(fantasia11); 
    rekisteri.lisaa(fantasia22); 
    rekisteri.lisaa(fantasia12); 
    rekisteri.lisaa(fantasia23); 
    SarjaGenre sg3 = new SarjaGenre(aku1.getId(),fantasia1.getGenreID()); 
    SarjaGenre sg4 = new SarjaGenre(aku2.getId(),fantasia11.getGenreID()); 
    rekisteri.lisaa(sg3); 
    rekisteri.lisaa(sg4); 
    rekisteri.talleta(); 
    rekisteri = new SarjaRekisteri(); 
    rekisteri.lueTiedostosta(hakemisto); 
    Collection<Sarja> kaikki = rekisteri.etsi("",0); 
    Iterator<Sarja> it = kaikki.iterator(); 
    assertEquals("From: SarjaRekisteri line: 320", aku1, it.next()); 
    assertEquals("From: SarjaRekisteri line: 321", aku2, it.next()); 
    assertEquals("From: SarjaRekisteri line: 322", false, it.hasNext()); 
    List<Genre> loytyneet = rekisteri.listaaGenret().haeGenret(""); 
    Iterator<Genre> ih = loytyneet.iterator(); 
    assertEquals("From: SarjaRekisteri line: 325", fantasia1, ih.next()); 
    assertEquals("From: SarjaRekisteri line: 326", true, ih.hasNext()); 
    rekisteri.lisaa(aku2); 
    rekisteri.lisaa(fantasia23); 
    rekisteri.talleta(); 
    assertEquals("From: SarjaRekisteri line: 330", true, ftied.delete()); 
    assertEquals("From: SarjaRekisteri line: 331", true, fhtied.delete()); 
    assertEquals("From: SarjaRekisteri line: 332", true, fhhtied.delete()); 
    File fbak = new File(hakemisto+"/sarjat.bak"); 
    assertEquals("From: SarjaRekisteri line: 334", true, fbak.delete()); 
    assertEquals("From: SarjaRekisteri line: 335", true, dir.delete()); 
  } // Generated by ComTest END
}