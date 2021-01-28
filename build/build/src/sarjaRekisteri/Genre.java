/**
 * 
 */
package sarjaRekisteri;

import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author tomih
 * @version 10 Mar 2020
 *
 */
public class Genre {
    private int genreID;
    private String genreNimi;
    private double rand;
    
    private static int seuraavaNro = 1;
    
    /**
     * Genren alustus
     */
    public Genre() {
        //
    }
    
    /**
     * Testi metodi
     */
    public void vastaaGenre() {
        genreNimi = "Fantasia";
        rand = Math.random();
    }
    
    /**
     * Jos lisätessä löytyi jo genre samalla nimellä niin peruutetaan IDn
     * suurennos
     */
    public void loytyi() {
        seuraavaNro--;
    }
    
    /**
     * @param out String genren tulostuksesta
     */
    public void tulosta(PrintStream out) {
        out.println(genreID + " " + genreNimi);
    }
    
    /**
     * @param os tietovirta johon tulosteteaan
     */
    public void tulosta(OutputStream os ) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * @return Palauttaa genren IDn
     */
    public int getGenreID() {
        return genreID;
    }
    
    /**
     * @return Palauttaa genren nimi
     */
    public String getNimi() {
        return genreNimi;
    }
    
    /**
     * @return Rekisteröi genren listaan
     * @example
     * <pre name="test">
     *  Genre g1 = new Genre();
     *  g1.getGenreID() === 0;
     *  g1.rekisteroi();
     *  Genre g2 = new Genre();
     *  g2.rekisteroi();
     *  int n1 = g1.getGenreID();
     *  int n2 = g2.getGenreID();
     *  n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        genreID = seuraavaNro;
        seuraavaNro++;
        return genreID;
    }
    
    /**
     * <pre name="test">
     * Genre g = new Genre();
     * g.parse("10|Draama");
     * g.toString().startsWith("10|Draama") === true;
     */
    @Override
    public String toString() {
        return "" + getGenreID() + "|" + getNimi();
    }
    
    /**
     * @param rivi rivi joka luetaan tiedostosta
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setGenreID(Mjonot.erota(sb, '|', getGenreID()));
        genreNimi = Mjonot.erota(sb, '|', genreNimi);
    }
    
    /**
     * Metodi jolla voidaan tarvittaessa asettaa genren id manuaalisesti
     * @param nr id jolla yritetään
     */
    private void setGenreID(int nr) {
        genreID = nr;
        if (genreID >= seuraavaNro ) seuraavaNro = genreID + 1;
    }
    
    /**
     * @param s Genren uusi nimi
     * @return ei kaytossa
     */
    public String setNimi(String s) {
        genreNimi = s;
        return null;
    }
    
    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) return false;
        return this.toString().equals(obj.toString());
    }
    
    @Override
    public int hashCode() {
        return genreID;
    }
    
    /**
     * Testipääohjlema
     * @param args Ei kaytossa
     */
    public static void main(String[] args) {
        Genre gen = new Genre();
        System.out.println(gen.getGenreID());
        gen.vastaaGenre();
        gen.tulosta(System.out);
    }

    /**
     * @return Palauttaa genren rand muuttujan
     */
    public double getRand() {
        return this.rand;
    }
}
