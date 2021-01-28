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
public class SarjaGenre {
    private int genreID = 0;
    private int sarjaID = 0;
    
    /**
     * Konstruktori SarjaGenrelle
     */
    public SarjaGenre() {
        //
    }
    
    /**
     * Konstruktori jolle syötetään sarjan id ja genreid
     * @param sarjaID Sarja id
     * @param genreID Genre id
     */
    public SarjaGenre(int sarjaID, int genreID) {
        this.sarjaID = sarjaID;
        this.genreID = genreID;
    }
    
    /**
     * @param out SarjaGenren tulostus
     */
    public void tulosta(PrintStream out) {
        out.println(sarjaID + "|" + genreID);
    }
    
    /**
     * @param os tietovirta johon tulosteteaan
     */
    public void tulosta(OutputStream os ) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * <pre name="test">
     * SarjaGenre sg = new SarjaGenre();
     * sg.parse("10|10");
     * sg.toString().startsWith("10|10") === true;
     */
    @Override
    public String toString() {
        return "" + getSarjaID() + "|" + getGenreID();
    }
    
    /**
     * @param rivi rivi joka luetaan tiedostosta
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        sarjaID = Mjonot.erota(sb, '|', getSarjaID());
        genreID = Mjonot.erota(sb, '|', getGenreID());
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
     * @return Palauttaa genreid
     * <pre name="test">
     *  SarjaGenre sg = new SarjaGenre(1, 2);
     *  sg.getGenreID() === 2;
     * </pre>
     */
    public int getGenreID() {
        return genreID;
    }
    
    /**
     * @return Palauttaa sarja idn
     * <pre name="test">
     *  SarjaGenre sg = new SarjaGenre(1, 2);
     *  sg.getSarjaID() === 1;
     * </pre>
     */
    public int getSarjaID() {
        return sarjaID;
    }
    
    /**
     * Testipääohjelma
     * @param args Ei kaytossa
     */
    public static void main(String[] args) {
        SarjaGenre sg = new SarjaGenre();
        sg.tulosta(System.out);
    }

    /**
     * Asettaan relaation genre idn
     * @param genreID2 Genren ID joka asetetaan
     * <pre name="test">
     *  SarjaGenre sg = new SarjaGenre(1, 0);
     *  sg.getGenreID() === 0;
     *  sg.setGenreID(2);
     *  sg.getGenreID() === 2;
     * </pre>
     */
    public void setGenreID(int genreID2) {
        this.genreID = genreID2;
    }


}
