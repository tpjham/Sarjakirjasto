/**
 * 
 */
package sarjaRekisteri;

import java.io.*;
import java.util.Calendar;
import java.util.Comparator;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author tomih
 * @version 1 Mar 2020
 *
 */
public class Sarja implements Cloneable {
    private int id;
    private String nimi = "";
    private int tKausi = 0;
    private int jakso = 0;
    private int julkVuosi = 0;
    private double arvio = 0.0;
    private boolean nahty = false;
    private double rand;
    
    private static int seuraavaNro = 1;
    
    /**
     * @author tomih
     * @version 24 Apr 2020
     *  Sarjan vertailija
     */
    public static class Vertailija implements Comparator<Sarja> {
        private int k;
        
        /**
         * @param k Kentta jota vertaillaan
         */
        public Vertailija(int k) {
            this.k = k;
        }
        
        @Override
        public int compare(Sarja s1, Sarja s2) {
            return s1.getAvain(k).compareToIgnoreCase(s2.getAvain(k));
        }
    }
    
    /**
     * @param k monennenko kentän sisältö palautetaan
     * @return Kentän sisältö merkkijonona
     */
    public String getAvain(int k) {
        switch (k) {
        case 0: return "" + nimi;
        case 2: return "" + julkVuosi;
        case 3: return "" + id;
        default: return "Test";
        }
    }
    
    /**
     * @param k Monennenko kentän sisältö haetaan
     * @return kentän sisältö
     */
    public String anna(int k) {
        switch (k) {
        case 0: return "" + nimi;
        case 2: return "" + julkVuosi;
        case 3: return "" + id;
        default: return "Test";
        }
    }
    
    /**
     * @return Palauttaa sarjan nimen
     * <pre name="test">
     *  Sarja tw = new Sarja();
     *  tw.vastaaSarja();
     *  tw.getNimi() === "The Witcher";
     * </pre>
     */
    public String getNimi() {
        return nimi;
    }
    
    /**
     * testiarvot sarjoille
     */
    public void vastaaSarja() {
        nimi = "The Witcher";
        tKausi = 1;
        jakso = 8;
        julkVuosi = 2019;
        arvio = 4.5;
        nahty = true;
        rand = Math.random();
    }
    
    /**
     * @param out Tulostus
     */
    public void tulosta(PrintStream out) {
        out.println(nimi + ", " + julkVuosi);
        out.println("S" + tKausi + "E" + jakso);
        out.println(arvio);
        out.println(rand);
    }
    
    /**
     * @param os Tulostus
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * @return Seuraava id 
     * @example
     * <pre name="test">
     *  Sarja s1 = new Sarja();
     *  s1.getId() === 0;
     *  s1.rekisteroi();
     *  Sarja s2 = new Sarja();
     *  s2.rekisteroi();
     *  int n1 = s1.getId();
     *  int n2 = s2.getId();
     *  n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        id = seuraavaNro;
        seuraavaNro++;
        return id;
    }
    
    /**
     * @return palauttaa sarjan id:n
     */
    public int getId() {
        return id;
    }
    
    /**
     * @param nr Uusi numero
     */
    public void setId(int nr) {
        id = nr;
        if ( id >= seuraavaNro ) seuraavaNro = id + 1;
    }
    
    /**
     * <pre name="test">
     * Sarja s = new Sarja();
     * s.rekisteroi();
     * int sid = s.getId();
     * s.parse("|Witcher|1|8|2019|3.5|true");
     * s.toString().startsWith("" + sid + "|Witcher|1|8|2019|3.5|true|") === true;
     */
    @Override
    public String toString() {
        return "" + id + "|"
                + nimi + "|"
                + tKausi + "|"
                + jakso + "|"
                + julkVuosi + "|"
                + arvio + "|"
                + nahty + "|";
    }
    
    /**
     * @param rivi Tiedoston rivi
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setId(Mjonot.erota(sb, '|', getId()));
        nimi = Mjonot.erota(sb, '|', nimi);
        tKausi = Mjonot.erota(sb, '|', tKausi);
        jakso = Mjonot.erota(sb, '|', jakso);
        julkVuosi = Mjonot.erota(sb, '|', julkVuosi);
        arvio = Mjonot.erota(sb, '|', arvio);
        nahty = Boolean.parseBoolean(Mjonot.erota(sb, '|', nahty));
    }
    
    @Override
    public Sarja clone() throws CloneNotSupportedException {
        Sarja uusi;
        uusi = (Sarja) super.clone();
        return uusi;
    }
    
    @Override
    public boolean equals(Object sarja) {
        if ( sarja == null ) return false;
        return this.toString().equals(sarja.toString());
    }
    
    @Override
    public int hashCode() {
        return id;
    }

    /**
     * @return sarjan tuotantokausi
     */
    public String getKausi() {
        String out = "";
        out += tKausi;
        return out;
    }

    /**
     * @return Palauttaa sarjan jakson
     */
    public String getJakso() {
        String out = "";
        out += jakso;
        return out;
    }

    /**
     * @return palauttaa sarjan julkaisuvuoden
     */
    public String getVuosi() {
        String out = "";
        out += julkVuosi;
        return out;
    }

    /**
     * @return palauttaa käyttäjän antaman arvion sarjasta
     */
    public double getArvio() {
        return arvio;
    }

    /**
     * @return palauttaa boolean arvon sarjan "nahty" tilasta
     */
    public boolean getNahty() {
        return nahty;
    }
    
    /**
     * @param s sarjan uusi nimi
     * @return ei kayteta
     */
    public String setNimi(String s) {
        nimi = s;
        return null;
    }
    
    /**
     * @param s Asetetaan jakso jossa ollaan
     * @return Jos virhe niin palautetaan
     */
    public String setJakso(String s) {
        if ( s == "") return null;
        if ( !s.matches("[0-9]*") ) return "Jakson oltava numeerinen";
        jakso = Integer.parseInt(s);
        return null;
    }
    
    /**
     * @param s Asetetaan kausi jossa ollaan
     * @return Jos virhe niin palautetaan
     */
    public String setKausi(String s) {
        if ( s == "") return null;
        if ( !s.matches("[0-9]*") ) return "Kauden oltava numeerinen";
        tKausi = Integer.parseInt(s);
        return null;
    }
    
    /**
     * @param s Asetetaan sarjan julkaisuvuosi
     * @return Jos virhe niin palautetaan
     */
    public String setVuosi(String s) {
        if ( s == "") return null;
        if ( !s.matches("[0-9]*") ) return "Vuoden oltava numeerinen";
        if ( tarkistaVuosi(s) == false ) return "Vuosi ei voi olla suurempi kuin nykyinen";
        julkVuosi = Integer.parseInt(s);
        return null;
    }

    /**
     * Metodi jolla tarkistetaan että vuosi jota sarjalle yritetään antaa ei ole suurempi kuin nykyinen vuosi
     * @param s vuosiluku merkkijonona
     * @return True jos vuosi luku pienempi kuin nykyinen ja onnistuu, false jos väärä
     */
    private boolean tarkistaVuosi(String s) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        if ( year < Integer.parseInt(s) ) return false;
        return true;
    }
    
    /**
     * @param s Asetetaan sarjan arvio
     * @return ei kayteta
     */
    public String setArvio(String s) {
        arvio = Double.parseDouble(s);
        return null;
    }
    
    /**
     * @param arg Boolean onko nahty
     * @return ei kaytossa
     */
    public Boolean setNahty(Boolean arg) {
        nahty = arg;
        return null;
    }
}
