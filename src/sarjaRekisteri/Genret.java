/**
 * 
 */
package sarjaRekisteri;

import java.io.*;
import java.util.*;

/**
 * @author tomih
 * @version 1 Mar 2020
 *
 */
public class Genret implements Iterable<Genre> {
    
    private boolean muutettu = false;
    private String tiedostonNimi = "genret";
    
    private final Collection<Genre> alkiot = new ArrayList<Genre>();
    
    /**
     * Genrejen constructori
     */
    public Genret() {
        
    }
    
    /**
     * @param genre Genre joka lisataan
     * 
     * @example
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
     * fant4.getNimi() === "Draama";
     * gret.annaNimi(fant4.getGenreID()) === "Draama";
     * gret.annaGenreId("Draama") === "" + fant4.getGenreID();
     * 
     * Iterator<Genre> i2=gret.iterator();
     * i2.next() === fant1;
     * i2.next() === fant2;
     * i2.next() === fant3;
     * i2.next() === fant4;
     * i2.next() === fant4; #THROWS NoSuchElementException
     * </pre>
     */
    public void lisaa(Genre genre) {
        for ( Genre g : alkiot ) {
            if ( genre.getNimi().equalsIgnoreCase(g.getNimi()) ) {
                g.loytyi();
                return;
            }
        }
        alkiot.add(genre);
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
                Genre ger = new Genre();
                ger.parse(rivi);
                lisaa(ger);
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
            for ( Genre g : this ) {
                fo.println(g.toString());
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
     * @return Palauttaa genrejen lukumäärän
     */
    public int getGenreLkm() {
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
    
    /**
     * @param i Genren id
     * @return Genren nimi
     */
    public String annaNimi(int i) {
        String out = "";
        for ( Genre g : alkiot ) {
            if ( g.getGenreID() == i ) {
                out += g.getNimi();
                return out;
            }
        }
        return "";
    }
    
    /**
     * @param id genren id
     * @return Genre objekti
     */
    public Genre annaGenre(int id) {
        for ( Genre g : alkiot ) {
            if (g.getGenreID() == id ) {
                return g;
            }
        }
        return null;
    }
    
    @Override
    public Iterator<Genre> iterator() {
        return alkiot.iterator();
    }
    
    /**
     * Testi ohjelma genreille
     * @param args Ei kaytossa
     */
    public static void main(String[] args) {
        Genret genreja = new Genret();
        Genre fantasia1 = new Genre();
        fantasia1.vastaaGenre();
        fantasia1.rekisteroi();
        Genre fantasia2 = new Genre();
        fantasia2.vastaaGenre();
        fantasia2.rekisteroi();
        
        genreja.lisaa(fantasia1);
        genreja.lisaa(fantasia2);
        
        for ( Genre gen : genreja ) {
            gen.tulosta(System.out);
        }
    }

    /**
     * @param nimi Nimi jolla haetaan
     * @return Genren id
     */
    public String annaGenreId(String nimi) {
        String out = "";
        for ( Genre g : alkiot ) {
            if ( (g.getNimi()).equalsIgnoreCase(nimi) ) {
                out += g.getGenreID();
                return out;
            }
        }
        return null;
    }

    /**
     * @param hakuehto Genren nimi
     * @return Palauttaa listan genreista
     */
    public List<Genre> haeGenret(String hakuehto) {
        String ehto = "";
        if ( hakuehto != null && hakuehto.length() > 0 ) ehto = hakuehto.toLowerCase();
        List<Genre> loydetyt = new ArrayList<Genre>();
        for ( Genre g : this ) {
            String sub = g.getNimi().substring(0, (g.getNimi().length())).toLowerCase();
            if ( sub.contains(ehto) ) {
                loydetyt.add(g);
            }
        }
        
        return loydetyt;
    }
}
