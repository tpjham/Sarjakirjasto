package fxSarja;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import sarjaRekisteri.*;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.*;;


/**
 * Luokka kerhon käyttöliittymän tapahtumien hoitamiseksi.
 * @author vesal
 * @version 3.1.2018
 */
public class SarjaGUIController implements Initializable {
    @FXML private TextField hakuehto;
    @FXML private ComboBoxChooser<String> kentat;
    @FXML private ScrollPane panelSarja;
    @FXML private ListChooser<Sarja> chooserSarjat;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }

    /**
     * Käsitellään uuden jäsenen lisääminen
     */
    @FXML private void handleUusiSarja() {
        uusiSarja();
    }
    
    
    /**
     * Käsitellään tallennuskäsky
     */
    @FXML private void handleTallenna() {
        tallenna();
    }
    
    
    /**
     * Käsitellään lopetuskäsky
     */
    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    }
    
    /**
     * Käsitellään tietojen haku
     */
    @FXML private void handleHakuEhto() {
        hae(0);
    }
    
    
    
    /**
     * Käsitellään tietojen poisto
     */
    @FXML private void handleDelete() {
        poistaSarja();
    }

    /**
     * About ikkunan käsittely
     */
    @FXML private void handleAbout() {
        Dialogs.showMessageDialog("About ei näytä vielä mitään");
    }
    
    @FXML private void handleMuokkaa() {
        muokkaa();
    }
    
    // ==================================================================
    
    /**
     * Rekisteri jota ohjelmassa käytetään
     */
    protected SarjaRekisteri rekisteri;
    private Sarja sarjaValittu;
    private SarjaGenre apuSG;
    @FXML private TextField fxSarjaNimi;
    @FXML private TextField fxSarjaKausi;
    @FXML private TextField fxSarjaJakso;
    @FXML private TextField fxSarjaGenre;
    @FXML private TextField fxSarjaVuosi;
    @FXML private Slider fxSarjaArvio;
    @FXML private CheckBox fxSarjaNahty;
    @FXML private TextField fields[];
    
    
    /**
     * Alusta
     */
    protected void alusta() {
        chooserSarjat.clear();
        chooserSarjat.addSelectionListener(e -> naytaSarja());
        
        fields = new TextField[] {fxSarjaNimi, fxSarjaKausi, fxSarjaJakso, fxSarjaGenre, fxSarjaVuosi};

    }

    /**
     * Metodi virheiden näyttämiselle
     * @param virhe Merkkijono virheestä joka näytetään
     */
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            //
        }
        Dialogs.showMessageDialog(virhe);
    }
    
    /**
     * Tietojen tallennus
     */
    private String tallenna() {
        try {
            rekisteri.talleta();
            return null;
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa ongelma " + ex.getMessage());
            return ex.getMessage();
        }
    }
    
    
    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkaa sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
    
    /**
     *  Nayttaa valitus sarjhan tiedot
     */
    protected void naytaSarja() {
        sarjaValittu = chooserSarjat.getSelectedObject();
        
        //vaihe5kentta.clear();
        
        if (sarjaValittu == null) {
            return;
        }
        
        
        fxSarjaNimi.setText(sarjaValittu.getNimi());
        fxSarjaKausi.setText(sarjaValittu.getKausi());
        fxSarjaJakso.setText(sarjaValittu.getJakso());
        List<SarjaGenre> loytyneet = rekisteri.annaGenre(sarjaValittu);
        int gid = 0;
        for (SarjaGenre sg : loytyneet ) {
            gid = sg.getGenreID();
        }
        fxSarjaGenre.setText(rekisteri.annaNimi(gid));
        fxSarjaVuosi.setText(sarjaValittu.getVuosi());
        fxSarjaArvio.setValue(sarjaValittu.getArvio());
        fxSarjaNahty.setSelected(sarjaValittu.getNahty());
    }
    
    /**
     * @param sarjaNro Sarjojen haku
     */
    protected void hae(int sarjaNro) {
        int sNro = sarjaNro;
        if ( sNro <= 0 ) {
            Sarja kohdalla = sarjaValittu;
            if ( kohdalla != null ) sNro = kohdalla.getId();
        }
        
        int k = kentat.getSelectionModel().getSelectedIndex();
        String ehto = hakuehto.getText();
        
        
        chooserSarjat.clear();
        
        int index = 0;
        Collection<Sarja> sarjat;
        
        try {
            sarjat = rekisteri.etsi(ehto, k);
            int i = 0;
            for ( Sarja s : sarjat ) {
                if ( s.getId() == sNro ) index = i;
                chooserSarjat.add(s.getNimi(), s);
                i++;
            }
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Sarjan hakemisessa ongelmia: " + ex.getMessage());
        }
        chooserSarjat.setSelectedIndex(index);
    }
    
    /**
     * Uuden sarjan lisääminen rekisteriin nappia painaessa
     */
    protected void uusiSarja() {
        try {
            boolean uusiGenre = true;
            Sarja uusi = new Sarja();
            uusi = SarjaLisaysController.kysySarja(null, uusi);
            if ( uusi == null ) return;
            String genreNimi;
            genreNimi = SarjaGenreController.kysyGenre(null, "Fantasia");
            
            uusi.rekisteroi();
            rekisteri.lisaa(uusi);
            
            Genret genret = rekisteri.listaaGenret();
            
            SarjaGenre sg = new SarjaGenre(uusi.getId(),0);
            for (Genre g : genret ) {
                if ( g.getNimi().equalsIgnoreCase(genreNimi) ) {
                    sg.setGenreID(g.getGenreID());
                    uusiGenre = false;
                }
            }
            if ( uusiGenre ) {
                Genre g = new Genre();
                g.rekisteroi();
                g.setNimi(genreNimi);
                sg.setGenreID(g.getGenreID());
                rekisteri.lisaa(g);
            }

            rekisteri.lisaa(sg);
            
            hae ( uusi.getId() );
            
            
        } catch ( SailoException e ) {
            Dialogs.showMessageDialog("Ongelma uuden luomisesa " + e.getMessage());
            return;
        }
    }
    
    /**
     * @param rekisteri Asetetaan rekisteri käyttöön
     */
    public void setRekisteri(SarjaRekisteri rekisteri) {
        this.rekisteri = rekisteri;
        naytaSarja();
    }

    /**
     * @return test
     */
    public String lueTiedostosta() {
        try {
            rekisteri.lueTiedostosta("sarja");
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage();
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
    }
    
    /**
     * Olemassa olevan sarjan ja sen relaatioiden muokkaus,
     * avaa kaksi uutta ikkunaan joissa ensin annetaan sarja muokattavaksi,
     * toisessa annetaan genre
     */
    private void muokkaa() {
        if ( sarjaValittu == null ) return;
        
        try {
            boolean uusiGenre = true;
            Sarja sarja;
            sarja = SarjaLisaysController.kysySarja(null, sarjaValittu.clone());
            if ( sarja == null ) return;
            String genreNimi;
            genreNimi = SarjaGenreController.kysyGenre(null, "Fantasia");
            
            rekisteri.korvaaTaiLisaa(sarja);
            
            Genret genret = rekisteri.listaaGenret();
            
            apuSG = rekisteri.haeSarjaGenre(sarja.getId());
            
            for (Genre g : genret ) {
                if ( g.getNimi().equalsIgnoreCase(genreNimi) ) {
                    apuSG.setGenreID(g.getGenreID());
                    uusiGenre = false;
                    
                    rekisteri.muutaSG(apuSG);
                    return;
                }
            }
            
            if ( uusiGenre ) {
                Genre g = new Genre();
                g.rekisteroi();
                g.setNimi(genreNimi);
                
                apuSG.setGenreID(g.getGenreID());
                
                rekisteri.muutaSG(apuSG);
            }
            
            rekisteri.korvaaTaiLisaa(sarja);
            hae ( sarja.getId() );
            chooserSarjat.refresh();
        } catch (CloneNotSupportedException e ) {
            naytaVirhe("Ei voi kloonata");

        } catch (SailoException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
    }
    
    /**
     * Sarjan ja sen relaation poistamisen aloittava metodi
     */
    private void poistaSarja() {
        Sarja sarja = sarjaValittu;
        if ( sarja == null ) return;
        if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko sarja: " + sarja.getNimi(), "Kyllä", "Ei"))
            return;
        rekisteri.poista(sarja);
        int index = chooserSarjat.getSelectedIndex();
        hae(0);
        chooserSarjat.setSelectedIndex(index);
    }

    /**
     * @return Palauttaa rekisterin
     */
    public SarjaRekisteri annaRekisteri() {
        return rekisteri;
    }

}