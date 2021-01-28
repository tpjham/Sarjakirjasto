/**
 * 
 */
package sarjaRekisteri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author tomih
 * @version 10 Mar 2020
 *
 */
public class SarjaGenret implements Iterable<SarjaGenre> {
    
    private boolean muutettu = false;
    private String tiedostonNimi = "sarjagenre";
    
    private final Collection<SarjaGenre> alkiot = new ArrayList<SarjaGenre>();
    
    /**
     * SarjaGenre relaation konstruktori
     */
    public SarjaGenret() {
        //
    }
    
    /**
     * @param sg SarjaGenre relaatio joka lisätään
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * SarjaGenret sgt = new SarjaGenret();
     * SarjaGenre sg1 = new SarjaGenre(1,1); sgt.lisaa(sg1);
     * SarjaGenre sg2 = new SarjaGenre(2,3); sgt.lisaa(sg2);
     * SarjaGenre sg3 = new SarjaGenre(3,4); sgt.lisaa(sg3);
     * SarjaGenre sg4 = new SarjaGenre(5,4); sgt.lisaa(sg4);
     * 
     * List<SarjaGenre> loytyneet;
     * loytyneet = sgt.annaGenret(1);
     * loytyneet.size() === 1;
     * loytyneet.get(0).getGenreID() === 1;
     * 
     * 
     * 
     * </pre>
     */
    public void lisaa(SarjaGenre sg) {
        alkiot.add(sg);
        muutettu = true;
    }
    
    /**
     * @param hakemisto Tiedoston nimi
     * @throws SailoException Jos lukeminen ei onnistu
     */
    public void lueTiedosto(String hakemisto) throws SailoException {
        setTiedostonNimi(hakemisto);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonKokoNimi())) ) {
            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                SarjaGenre sg = new SarjaGenre();
                sg.parse(rivi);
                lisaa(sg);
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
        lueTiedosto(getTiedostonNimi());
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
            for ( SarjaGenre sg : this ) {
                fo.println(sg.toString());
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
     * @return Palauttaa collectionin määrän
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    /**
     * @return Palauttaa tiedoston nimen
     */
    public String getTiedostonNimi() {
        return tiedostonNimi;
    }
    
    /**
     * @return Palauttaa tiedoston nimen
     */
    public String getTiedostonKokoNimi() {
        return tiedostonNimi + ".dat";
    }
    
    /**
     * @return backup tiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonNimi + ".bak";
    }
    
    @Override
    public Iterator<SarjaGenre> iterator() {
        return alkiot.iterator();
    }
    
    /**
     * Hakee genret relaatiosta joilla on sama sarja id 
     * @param sarjaID Sarja id 
     * @return Palauttaa genren id(t)
     */
    public String haeGenret(int sarjaID) {
        String out = "";
        List<SarjaGenre> genret = annaGenret(sarjaID);
        for (SarjaGenre sg : genret ) {
            out += sg.getGenreID();
        }
        return out;
    }
    
    /**
     * Hakee listan genreistä joilla sama sarja id
     * @param sarjaID Sarja id
     * @return Lista genreistä
     */
    public List<SarjaGenre> annaGenret(int sarjaID) {
        List<SarjaGenre> loydetyt = new ArrayList<SarjaGenre>();
        for (SarjaGenre sg : alkiot ) {
            if (sg.getSarjaID() == sarjaID) loydetyt.add(sg);
        }
        return loydetyt;
    }
    
    /**
     * Testi pääohjelma
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        SarjaGenret sgt = new SarjaGenret();
        SarjaGenre sg1 = new SarjaGenre();
        SarjaGenre sg2 = new SarjaGenre();
        SarjaGenre sg3 = new SarjaGenre();
        
        sgt.lisaa(sg1);
        sgt.lisaa(sg2);
        sgt.lisaa(sg3);
        
        
        for ( SarjaGenre sg : sgt) {
            sg.tulosta(System.out);
        }
    }

    /**
     * @param id Sarjan id
     * @return Palauttaa sarjagenre objektin
     */
    public SarjaGenre annaSG(int id) {
        for ( SarjaGenre sg : alkiot ) {
            if ( sg.getSarjaID() == id ) {
                return sg;
            }
        }
        return null;
    }

    /**
     * @param apusg SarjaGenre objekti jonka tiedoilla muutetaan rekisteristä löytyvää objektia
     * @return Onnistuiko muokkaus, eli löytyikö sarjagenreä
     * <pre name="test">
     * SarjaGenret sgt = new SarjaGenret();
     * SarjaGenre sg1 = new SarjaGenre(1,1);
     * sgt.lisaa(sg1);
     * sg1.getGenreID() === 1;
     * 
     * SarjaGenre sg2 = new SarjaGenre(1,2);
     * sgt.muutaSG(sg2);
     * 
     * sg1.getGenreID() === 2;
     */
    public boolean muutaSG(SarjaGenre apusg) {
        for ( SarjaGenre sg : alkiot ) {
            if ( sg.getSarjaID() == apusg.getSarjaID() ) {
                sg.setGenreID(apusg.getGenreID());
                muutettu = true;
                return true;
            }
        }
        return false;
    }
    


    /**
     * @param genreID2 Genre ID jota haetaan
     * @return Sarjan ID jolla genre
     */
    public int getSarjat(int genreID2) {
        for (SarjaGenre sg : alkiot ) {
            if ( sg.getGenreID() == genreID2 ) {
                return sg.getSarjaID();
            }
        }
        return -1;
    }

    /**
     * @param id Sarjan id joka poistetaan
     * @return Kuinka monta poistettiin
     */
    public int poistaSG(int id) {
        int n = 0;
        for ( Iterator<SarjaGenre> it = alkiot.iterator(); it.hasNext(); ) {
            SarjaGenre sg = it.next();
            if ( sg.getSarjaID() == id ) {
                it.remove();
                n++;
            }
        }
        if ( n > 0 ) muutettu = true;
        return n;
    }

}
