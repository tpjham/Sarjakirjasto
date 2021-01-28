/**
 * 
 */
package sarjaRekisteri;

import java.io.*;
import java.util.*;

import fi.jyu.mit.ohj2.WildChars;

/**
 * @author tomih
 * @version 1 Mar 2020
 *
 */
public class Sarjat implements Iterable<Sarja> {
    private static int MAX_MAARA = 5;
    private boolean muutettu = false;
    private int lkm = 0;
    private String tiedostonNimi = "sarjat";
    private Sarja alkiot[] = new Sarja[MAX_MAARA];
    
    /**
     * Sarjojen constructori
     */
    public Sarjat() {
        
    }
    
    /**
     * @param sarja Sarja joka lisataan
      * <pre name="test">
      * #import sarjaRekisteri.SailoException;
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
      * sarjat.lisaa(s1); sarjat.getLkm() === 4;
      * sarjat.lisaa(s1); sarjat.getLkm() === 5;
      * sarjat.getLkm() === 5;
      * </pre>
     */
    public void lisaa(Sarja sarja) {
        if (lkm >= alkiot.length) {
            teeUusiLista();
        };
        alkiot[lkm] = sarja;
        lkm++;
        muutettu = true;
    }
    
    /**
     * @param sarja Sarja joka lisätään tai korvataan
     * @throws SailoException Jos tietorakenne on täynnä
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #import java.util.Iterator;
     * Sarjat sarjat = new Sarjat();
     * Sarja s1 = new Sarja(); Sarja s2 = new Sarja();
     * s1.rekisteroi(); s2.rekisteroi();
     * sarjat.getLkm() === 0;
     * sarjat.korvaaTaiLisaa(s1); sarjat.getLkm() === 1;
     * sarjat.korvaaTaiLisaa(s2); sarjat.getLkm() === 2;
     * Sarja s3 = s1.clone();
     * s1.setNimi("kkk");
     * Iterator<Sarja> it = sarjat.iterator();
     * it.next() == s1 === true;
     * sarjat.korvaaTaiLisaa(s3); sarjat.getLkm() === 2;
     * it = sarjat.iterator();
     * Sarja s0 = it.next();
     * s0 === s3;
     * s0 == s3 === true;
     * s0 == s1 === false;
     */
    public void korvaaTaiLisaa(Sarja sarja) throws SailoException {
        int id = sarja.getId();
        for ( int i = 0; i < lkm; i++ ) {
            if ( alkiot[i].getId() == id ) {
                alkiot[i] = sarja;
                muutettu = true;
                return;
            }
        }
        lisaa(sarja);
    }
    
    /**
     * Uuden listan tekeminen jos vanha on täynnä.
     * Ottaa vanhan listan, tekee uuden joka on vanhan määrä + 5,
     * ja lisää vanhasta listasta uuteen jonka jälkeen korvaa listan uudella
     */
    private void teeUusiLista() {
        Sarja[] tmp = new Sarja[MAX_MAARA + 5];
        for ( int i = 0; i < alkiot.length; i++ ) {
            tmp[i] = alkiot[i];
        }
        MAX_MAARA = tmp.length;
        alkiot = tmp;
    }
    
    /**
     * @param i Indeksi
     * @return Sarja indeksissa
     * @throws IndexOutOfBoundsException Jos väärä indeksi
     */
    public Sarja anna(int i) throws IndexOutOfBoundsException {
        if ( i < 0 || lkm <= i ) {
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        }
        return alkiot[i];
    }
    
    /**
     * @param id Poistettavan sarjan id
     * @return 1 jos poistettiin, 0 jos ei löydy
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Sarjat sarjat = new Sarjat();
     * Sarja s1 = new Sarja(); Sarja s2 = new Sarja(); Sarja s3 = new Sarja();
     * s1.rekisteroi(); s2.rekisteroi(); s3.rekisteroi();
     * int id1 = s1.getId();
     * sarjat.lisaa(s1); sarjat.lisaa(s2); sarjat.lisaa(s3);
     * sarjat.poista(id1+1) === 1;
     * sarjat.annaId(id1+1) === null; sarjat.getLkm() === 2;
     * sarjat.poista(id1) === 1; sarjat.getLkm() === 1;
     * sarjat.poista(id1+3) === 0; sarjat.getLkm();
     * 
     */
    public int poista(int id ) {
        int ind = etsiId(id);
        if ( ind < 0 ) return 0;
        lkm--;
        for ( int i = ind; i < lkm; i++ ) {
            alkiot[i] = alkiot[i + 1];
        }
        alkiot[lkm] = null;
        muutettu = true;
        return 1;
    }
    
    /**
     * @param hakemisto Tiedosto
     * @throws SailoException Jos epäonnistuu
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.File;
     * 
     * Sarjat sarjat = new Sarjat();
     * Sarja s1 = new Sarja(); Sarja s2 = new Sarja();
     * s1.vastaaSarja(); s2.vastaaSarja();
     * String hakemisto = "testisarja";
     * String tiedNimi = hakemisto+"/nimet";
     * File ftied = new File(tiedNimi+".dat");
     * File dir = new File(hakemisto);
     * dir.mkdir();
     * ftied.delete();
     * sarjat.lueTiedostosta(tiedNimi); #THROWS SailoException
     * sarjat.lisaa(s1);
     * sarjat.lisaa(s2);
     * sarjat.tallenna();
     * ftied.delete() === true;
     * dir.delete() === true;
     * 
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        setTiedostonNimi(hakemisto);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonKokoNimi())) ) {
            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Sarja sarja = new Sarja();
                sarja.parse(rivi);
                lisaa(sarja);
                System.out.println("Lisätty sarja " + sarja.toString());
            }
            muutettu = false;
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto " + getTiedostonKokoNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
    
    /**
     * @throws SailoException jos ei onnistu
     */
    public void lueTiedosto() throws SailoException {
        lueTiedostosta(getTiedostonNimi());
    }
    
    /**
     * @throws SailoException Jos tallennus ei onnistu
     */
    public void tallenna() throws SailoException {
        if (!muutettu) return;
        
        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonKokoNimi());
        fbak.delete();
        ftied.renameTo(fbak);
        
        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for ( Sarja s : this ) {
                fo.println(s.toString());
            }
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }
        muutettu = false;
    }
    
    /**
     * @param tied asettaa tiedoston nimen
     */
    public void setTiedostonNimi(String tied) {
        tiedostonNimi = tied;
    }
    
    /**
     * @return Sarjojen lkm
     */
    public int getLkm() {
        return lkm;
    }
    
    /**
     * @return Palauttaa tiedoston nimen
     */
    public String getTiedostonNimi() {
        return tiedostonNimi;
    }
    
    /**
     * @return tiedoston nimi
     */
    public String getTiedostonKokoNimi() {
        return getTiedostonNimi() + ".dat";
    }
    
    /**
     * @return backup tiedoston nimi
     */
    public String getBakNimi() {
        return getTiedostonNimi() + ".bak";
    }
    
    /**
     * @author tomih
     * @version 3 Apr 2020
     *
     */
    public class SarjatIterator implements Iterator<Sarja> {
        private int kohdalla = 0;
        
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }

        @Override
        public Sarja next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Lista loppu");
            return anna(kohdalla++);
        }
        
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Ei voi positaa");
        }
    }
    
    @Override
    public Iterator<Sarja> iterator() {
        return new SarjatIterator();
    }
    
    /**
     * @param hakuehto hakusana
     * @param k hakuehto
     * @return hakua vastaavat
     */
    public Collection<Sarja> etsi(String hakuehto, int k ) {
        String ehto = "";
        if ( hakuehto != null && hakuehto.length() > 0 ) ehto = hakuehto.toLowerCase();
        int hk = k;
        List<Sarja> loytyneet = new ArrayList<Sarja>();
        for ( Sarja s : this ) {
            String sub = s.anna(hk).substring(0, (s.anna(hk).length())).toLowerCase();
            if ( sub.contains(ehto) ) {
                loytyneet.add(s);
                
            }
        }
        Collections.sort(loytyneet, new Sarja.Vertailija(hk));
        return loytyneet;
    }
    
    /**
     * @param id Etsii idn perusteella sarjan
     * @return Löytyneen sarjan indeksi tai -1 jos ei löydy
     */
    public int etsiId(int id) {
        for ( int i = 0; i < lkm; i++ ) {
            if ( id == alkiot[i].getId() ) return i;
            
        }
        return -1;
    }
    
    /**
     * @param id Sarja id jolla etsitään
     * @return Sarja jolla id tai null
     * <pre name="test">
     * #THROWS SailoException
     * Sarjat sarjat = new Sarjat();
     * Sarja s1 = new Sarja(); Sarja s2 = new Sarja(); Sarja s3 = new Sarja();
     * s1.rekisteroi(); s2.rekisteroi(); s3.rekisteroi();
     * int id1 = s1.getId();
     * sarjat.lisaa(s1); sarjat.lisaa(s2); sarjat.lisaa(s3);
     * sarjat.annaId(id1) == s1 === true;
     * sarjat.annaId(id1+1) == s2 === true;
     * sarjat.annaId(id1+2) == s3 === true;
     * 
     */
    public Sarja annaId(int id) {
        for ( Sarja s : this ) {
            if ( id == s.getId()) return s;
        }
        return null;
    }
    
    /**
     * @param args ei kaytossa
     */
    public static void main(String args[]) {
        Sarjat sarjat = new Sarjat();
        
        Sarja witcher = new Sarja(), rickMorty = new Sarja();
        witcher.rekisteroi();
        witcher.vastaaSarja();
        rickMorty.rekisteroi();
        rickMorty.vastaaSarja();
        
            sarjat.lisaa(witcher);
            sarjat.lisaa(rickMorty);
        
            for ( int i = 0; i < sarjat.getLkm(); i++) {
                Sarja sarja = sarjat.anna(i);
                System.out.println("Sarja nro: " + i);
                sarja.tulosta(System.out);
            }
    }

    /**
     * @param hakuehto sarjan id
     * @param k kenttä idlle
     * @return Palauttaa löydetyt sarjat
     */
    public Sarja etsiIDlla(String hakuehto, int k ) {
        String ehto = "*";
        if ( hakuehto != null && hakuehto.length() > 0 ) ehto = hakuehto;
        int hk = k;
        for ( Sarja s : this ) {
            if ( WildChars.onkoSamat(s.anna(hk), ehto)) {
                return s;
                
            }
        }
        return null;
    }
}
