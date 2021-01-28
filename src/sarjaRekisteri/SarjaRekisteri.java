package sarjaRekisteri;

import java.io.File;
import java.util.*;

/**
 * @author tomih
 * @version 2 Mar 2020
 * @example
 * <pre name="testJAVA">
 * #import sarjaRekisteri.*;
 * private SarjaRekisteri rekisteri;
 * private Sarja s1;
 * private Sarja s2;
 * private int sid1;
 * private int sid2;
 * private Genre g1;
 * private Genre g2;
 * private int gid1;
 * private int gid2;
 * private SarjaGenre sg1;
 * private SarjaGenre sg2;
 * 
 * public void alustaRekisteri() {
 * rekisteri = new SarjaRekisteri();
 * s1 = new Sarja(); s1.vastaaSarja(); s1.rekisteroi();
 * s2 = new Sarja(); s2.vastaaSarja(); s2.rekisteroi();
 * sid1 = s1.getId();
 * sid2 = s2.getId();
 * g1 = new Genre(); g1.vastaaGenre(); g1.rekisteroi();
 * g2 = new Genre(); g2.vastaaGenre(); g2.rekisteroi();
 * g2.setNimi("Komedia");
 * gid1 = g1.getGenreID();
 * gid2 = g2.getGenreID();
 * sg1 = new SarjaGenre(sid1, gid1);
 * sg2 = new SarjaGenre(sid2, gid2);
 * 
 * try {
 * rekisteri.lisaa(s1);
 * rekisteri.lisaa(s2);
 * rekisteri.lisaa(g1);
 * rekisteri.lisaa(g2);
 * rekisteri.lisaa(sg1);
 * rekisteri.lisaa(sg2);
 * } catch ( Exception e ) {
 * System.err.println(e.getMessage());
 * }
 * }
 * </pre>
 *
 */
public class SarjaRekisteri {
    private Sarjat sarjat = new Sarjat();
    private Genret genret = new Genret();
    private SarjaGenret sgt = new SarjaGenret();
    
    /**
     * @return sarjojen määrä
     */
    public int getSarjatLkm() {
        return sarjat.getLkm();
    }
    
    /**
     * @param sarja Poistettava sarja
     * @return poistettu määrä
     * <pre name="test"> 
     * #THROWS Exception
     * alustaRekisteri();
     * rekisteri.etsi("",0).size() === 2;
     * rekisteri.getSarjatLkm() === 2;
     * rekisteri.poista(s1) === 1
     * rekisteri.getSarjatLkm() === 1;
     * rekisteri.etsi("",0).size() === 1;
     * </pre>
     * 
     */
    public int poista(Sarja sarja) {
        if ( sarja == null ) return 0;
        int ret = sarjat.poista(sarja.getId());
        sgt.poistaSG(sarja.getId());
        return ret;
    }
    
    /**
     * @param sarja Sarja joka lisataan
     * @throws SailoException Jos ei onnistu
      * <pre name="test">
      * #PACKAGEIMPORT
      * Sarjat sarjat = new Sarjat();
      * Sarja s1 = new Sarja(), s2 = new Sarja();
      * sarjat.getLkm() === 0;
      * sarjat.lisaa(s1); sarjat.getLkm() === 1;
      * sarjat.lisaa(s2); sarjat.getLkm() === 2;
      * sarjat.lisaa(s1); sarjat.getLkm() === 3;
      * sarjat.anna(0) === s1;
      * sarjat.anna(1) === s2;
      * sarjat.anna(2) === s1;
      * sarjat.anna(1) == s1 === false;
      * sarjat.anna(1) == s2 === true;
      * sarjat.anna(3) === s1; #THROWS IndexOutOfBoundsException 
      * sarjat.lisaa(s1); sarjat.getLkm() === 4;
      * sarjat.lisaa(s1); sarjat.getLkm() === 5;
      * sarjat.lisaa(s1);
      * sarjat.getLkm() === 6;
      * </pre>
     */
    public void lisaa(Sarja sarja) throws SailoException {
        if ( sarja == null ) return;
        sarjat.lisaa(sarja);
    }
    
    /**
     * @param sarja Sarja joka lisätään tai jolla korvataan
     * @throws SailoException Jos tietorakenne täynnä
     * <pre name="test">
     * #THROWS SailoException
     * alustaRekisteri();
     * rekisteri.etsi("",0).size() === 2;
     * rekisteri.korvaaTaiLisaa(s1);
     * rekisteri.etsi("",0).size() === 2;
     * </pre>
     */
    public void korvaaTaiLisaa(Sarja sarja) throws SailoException {
        sarjat.korvaaTaiLisaa(sarja);
    }
    
    //public void 
    
    /**
     * @param ger Lisää genren rekisteriin
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Genret gret = new Genret();
     * Genre fant1 = new Genre(); fant1.rekisteroi(); fant1.vastaaGenre(); gret.lisaa(fant1);
     * Genre fant2 = new Genre(); fant2.rekisteroi(); fant2.setNimi("Komedia"); gret.lisaa(fant2);
     * Genre fant3 = new Genre(); fant3.rekisteroi(); fant3.setNimi("Kauhu"); gret.lisaa(fant3);
     * Genre fant4 = new Genre(); fant4.rekisteroi(); fant4.setNimi("Draama"); gret.lisaa(fant4);
     * 
     * Iterator<Genre> i2=gret.iterator();
     * i2.next() === fant1;
     * i2.next() === fant2;
     * i2.next() === fant3;
     * i2.next() === fant4;
     * i2.next() === fant4; #THROWS NoSuchElementException
     * </pre>
     */
    public void lisaa(Genre ger) {
        if ( ger == null ) return;
        genret.lisaa(ger);
    }
    
    /**
     * @param sg Lisää sarjagenre relaation
     */
    public void lisaa(SarjaGenre sg) {
        if ( sg == null ) return;
        sgt.lisaa(sg);
    }
    
    /**
     * @param hakuehto Hakusana jolla etsitään
     * @param k Haku
     * @return Lista haetuista
     * @throws SailoException Jos ei löydy
     * <pre name="test">
     * #THROWS CloneNotSupportedException, SailoException
     * alustaRekisteri();
     * Sarja s3 = new Sarja(); s3.vastaaSarja();
     * s3.rekisteroi(); s3.setNimi("Simpsonit");
     * rekisteri.lisaa(s3);
     * Genre g3 = new Genre(); g3.rekisteroi(); g3.setNimi("Romanssi");
     * SarjaGenre sg3 = new SarjaGenre(s3.getId(), g3.getGenreID());
     * rekisteri.lisaa(g3);
     * rekisteri.lisaa(sg3);
     * Collection<Sarja> loytyneet = rekisteri.etsi("roma",1);
     * loytyneet.size() === 1;
     * Iterator<Sarja> it = loytyneet.iterator();
     * it.next() == s3 === true;
     * 
     * loytyneet = rekisteri.etsi("Sim",0);
     * loytyneet.size() === 1;
     * it = loytyneet.iterator();
     * it.next() == s3 === true;
     */
    public Collection<Sarja> etsi(String hakuehto, int k) throws SailoException {
        System.out.println("Etsitään ehdolla: " + hakuehto  + " id: " + k);
        if ( k == 1 ) {
            String ehto = "";
            if ( hakuehto != null && hakuehto.length() > 0 ) ehto = hakuehto;
            List<Genre> gt = genret.haeGenret(ehto);
            List<Sarja> loydetyt = new ArrayList<Sarja>();
                    
            for ( SarjaGenre sg : sgt ) {
                for ( Genre g : gt ) {
                    if ( sg.getGenreID() == g.getGenreID() ) {
                        String out = "" + sg.getSarjaID();
                        loydetyt.add(sarjat.etsiIDlla(out, 3));
                    }
                }
                
            }
            Collections.sort(loydetyt, new Sarja.Vertailija(3));
            return loydetyt;
        }
        return sarjat.etsi(hakuehto, k);
    }
    
    /**
     * @param i Sarjan indeksi
     * @return Indeksissä olevan sarjan tiedot
     * @throws IndexOutOfBoundsException Jos väärä indeksi
     * <pre name="test">
     * #THROWS IndexOutOfBoundsException, SailoException
     * alustaRekisteri();
     * Sarja s3 = new Sarja();
     * s3.rekisteroi();
     * rekisteri.lisaa(s3);
     * 
     * rekisteri.annaSarja(0) === s1;
     * rekisteri.annaSarja(1) === s2;
     * rekisteri.annaSarja(2) === s3;
     * 
     * </pre>
     */
    public Sarja annaSarja(int i) throws IndexOutOfBoundsException {
        return sarjat.anna(i);
    }
    
    /**
     * @param id Genren id
     * @return Genren nimi
     */
    public String annaGenre(int id ) {
        return genret.annaNimi(id);
    }
    
    /**
     * @param id genren id
     * @return genren objekti
     */
    public Genre haeGenre(int id) {
        return genret.annaGenre(id);
    }
    
    /**
     * @param nimi Genre jonka nimellä haetaan
     * @return genren id
     */
    public String annaGenreId(String nimi) {
        return genret.annaGenreId(nimi);
    }
    
     /**
      * Asettaa tiedostojen perusnimet
     * @param nimi testi
      */
     public void setTiedosto(String nimi) {
         File dir = new File(nimi);
         dir.mkdirs();
         String hakemistonNimi = "";
         if ( !nimi.isEmpty() ) hakemistonNimi = dir.getName() + "/";
         sarjat.setTiedostonNimi(hakemistonNimi + "sarjat");
         genret.setTiedostonNimi(hakemistonNimi + "genret");
         sgt.setTiedostonNimi(hakemistonNimi + "sarjagenret");
     }
     
     
     
     /**
      * Lukee rekisterin tiedot tiedostosta
     * @param hakemisto hakemiston nimi
     * @throws SailoException jos lukeminen epäonnistuu
      * 
      * @example
      * <pre name="test">
      * #THROWS SailoException 
      * #import java.io.*;
      * #import java.util.*;
      * 
      *  SarjaRekisteri rekisteri = new SarjaRekisteri();
      *  
      *  Sarja aku1 = new Sarja(); aku1.vastaaSarja(); aku1.rekisteroi();
      *  Sarja aku2 = new Sarja(); aku2.vastaaSarja(); aku2.rekisteroi();
      *  Genre fantasia1 = new Genre(); fantasia1.vastaaGenre(); fantasia1.rekisteroi();
      *  Genre fantasia11 = new Genre(); fantasia11.vastaaGenre(); fantasia11.rekisteroi();
      *  fantasia11.setNimi("Komedia");
      *  Genre fantasia22 = new Genre(); fantasia22.vastaaGenre(); fantasia22.rekisteroi();
      *  Genre fantasia12 = new Genre(); fantasia12.vastaaGenre(); fantasia12.rekisteroi();
      *  Genre fantasia23 = new Genre(); fantasia23.vastaaGenre(); fantasia23.rekisteroi();
      *   
      *  String hakemisto = "testisarja";
      *  File dir = new File(hakemisto);
      *  File ftied  = new File(hakemisto+"/sarjat.dat");
      *  File fhtied = new File(hakemisto+"/genret.dat");
      *  File fhhtied = new File(hakemisto+"/sarjagenret.dat");
      *  dir.mkdir();  
      *  ftied.delete();
      *  fhtied.delete();
      *  fhhtied.delete();
      *  rekisteri.lueTiedostosta(hakemisto); #THROWS SailoException
      *  rekisteri.lisaa(aku1);
      *  rekisteri.lisaa(aku2);
      *  rekisteri.lisaa(fantasia1);
      *  rekisteri.lisaa(fantasia11);
      *  rekisteri.lisaa(fantasia22);
      *  rekisteri.lisaa(fantasia12);
      *  rekisteri.lisaa(fantasia23);
      *  SarjaGenre sg3 = new SarjaGenre(aku1.getId(),fantasia1.getGenreID());
      *  SarjaGenre sg4 = new SarjaGenre(aku2.getId(),fantasia11.getGenreID());
      *  rekisteri.lisaa(sg3);
      *  rekisteri.lisaa(sg4);
      *  rekisteri.talleta();
      *  rekisteri = new SarjaRekisteri();
      *  rekisteri.lueTiedostosta(hakemisto);
      *  Collection<Sarja> kaikki = rekisteri.etsi("",0); 
      *  Iterator<Sarja> it = kaikki.iterator();
      *  it.next() === aku1;
      *  it.next() === aku2;
      *  it.hasNext() === false;
      *  List<Genre> loytyneet = rekisteri.listaaGenret().haeGenret("");
      *  Iterator<Genre> ih = loytyneet.iterator();
      *  ih.next() === fantasia1;
      *  ih.hasNext() === true;
      *  rekisteri.lisaa(aku2);
      *  rekisteri.lisaa(fantasia23);
      *  rekisteri.talleta();
      *  ftied.delete()  === true;
      *  fhtied.delete() === true;
      *  fhhtied.delete() === true;
      *  File fbak = new File(hakemisto+"/sarjat.bak");
      *  fbak.delete() === true;
      *  dir.delete() === true;
      * </pre>
      */
     public void lueTiedostosta(String hakemisto) throws SailoException {
         sarjat = new Sarjat(); // jos luetaan olemassa olevaan niin helpoin tyhjentää näin
         genret = new Genret();
         sgt = new SarjaGenret();
         
         setTiedosto(hakemisto);
 
         sarjat.lueTiedosto();
         genret.lueTiedosto();
         sgt.lueTiedosto();
     }
    
    /**
     * @param sarja Sarja jonka genrea haetaan
     * @return Palauttaa genret jotka löydettiin
     */
    public List<SarjaGenre> annaGenre(Sarja sarja) {
        return sgt.annaGenret(sarja.getId());
    }
    
    /**
     * @throws SailoException Jos epäonnistuu
     */
    public void talleta() throws SailoException {
        String virhe ="";
        try {
            sarjat.tallenna();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }
        
        try {
            genret.tallenna();
        } catch ( SailoException ex ) {
            virhe += ex.getMessage();
        }
        
        try {
            sgt.tallenna();
        } catch ( SailoException ex ) {
            virhe += ex.getMessage();
        }
        if ( !"".equals(virhe)) throw new SailoException(virhe);
    }
    

    /**
     * @param gid Genre ID
     * @return Palauttaa genren nimen
     */
    public String annaNimi(int gid) {
        return genret.annaNimi(gid);
    }
    
    /**
     * Testi pääohjelma rekisterille
     * @param args EI kaytossa
     */
    public static void main(String[] args) {
        SarjaRekisteri rekisteri = new SarjaRekisteri();
        
        try {
            Sarja s1 = new Sarja();
            Sarja s2 = new Sarja();
            
            s1.rekisteroi();
            s1.vastaaSarja();
            s2.rekisteroi();
            s2.vastaaSarja();
            
            rekisteri.lisaa(s1);
            rekisteri.lisaa(s2);
            
            int id1 = s1.getId();
            int id2 = s2.getId();
            
            Genre g1 = new Genre();
            Genre g2 = new Genre();
            g1.vastaaGenre();
            g1.rekisteroi();
            g2.vastaaGenre();
            g2.rekisteroi();
            rekisteri.lisaa(g1);
            rekisteri.lisaa(g2);
            
            int id11 = g1.getGenreID();
            int id22 = g2.getGenreID();
            
            SarjaGenre sg1 = new SarjaGenre(id1, id11);
            SarjaGenre sg2 = new SarjaGenre(id2, id22);
            rekisteri.lisaa(sg1);
            rekisteri.lisaa(sg2);
            
            for ( int i = 0; i < rekisteri.getSarjatLkm(); i++ ) {
                Sarja sarja = rekisteri.annaSarja(i);
                System.out.println("Sarja paikassa: " + i);
                sarja.tulosta(System.out);
                List<SarjaGenre> loytyneet = rekisteri.annaGenre(sarja);
                for (SarjaGenre sg : loytyneet ) {
                    int gid = sg.getGenreID();
                    System.out.println("Genre: " + rekisteri.annaNimi(gid));
                }
            }
            
        } catch ( SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * @param id Sarjan id jolla haetaan
     * @return SarjaGenre objekti joka löytyi sarjalle
     */
    public SarjaGenre haeSarjaGenre(int id) {
        return sgt.annaSG(id);
    }

    /**
     * @param apusg Sarjagenre jota lähdetään muokkaamaan
     * @return Palautetaan onnistuiko
     */
    public boolean muutaSG(SarjaGenre apusg) {
        
        return sgt.muutaSG(apusg);
    }

    /**
     * @return Palauttaa listan kaikista genreista
     */
    public Genret listaaGenret() {
        return genret;
    }
}
