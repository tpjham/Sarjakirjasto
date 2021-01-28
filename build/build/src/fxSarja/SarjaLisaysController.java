/**
 * 
 */
package fxSarja;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sarjaRekisteri.*;


/**
 * @author tomih
 * @version 23 Apr 2020
 *
 */
public class SarjaLisaysController implements ModalControllerInterface<Sarja>,Initializable {

    @FXML private TextField lisaaNimi;
    @FXML private TextField lisaaKausi;
    @FXML private TextField lisaaJakso;
    @FXML private TextField lisaaGenre;
    @FXML private TextField lisaaVuosi;
    @FXML private Slider lisaaArvio;
    @FXML private CheckBox lisaaNahty;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
    }

    /**
     * Sarjan muokkaus ja lisäysikkunan OK painikkeen metodi
     */
    @FXML private void handleOK() {
        for ( int i = 0; i < edits.length; i++) {
            if ( edits[i].getText().equalsIgnoreCase("")) {
                naytaVirhe("Kaikkien kenttien täytyy olla täytetty");
                return;
            }
            String out = "";
            out += lisaaArvio.getValue();
            sarjaValittu.setArvio(out);
            sarjaValittu.setNahty(lisaaNahty.isSelected());
            
            
            ModalController.closeStage(lisaaNimi);
        }
    }

    /**
     * Peruutus painikkeen metodi
     */
    @FXML private void handleCancel() {
        sarjaValittu = null;
        ModalController.closeStage(lisaaNimi);;
    }
    
    //============================================================
    
    private Sarja sarjaValittu;
    private TextField edits[];
    
    /**
     * @param edits Parametrina annettu taulukko kenttiä jotka tyhjennetään
     */
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit : edits) {
            edit.setText("");
        }
    }
    
    /**
     * Alustetaan lisays gui
     */
    protected void alusta() {
        edits = new TextField[] {lisaaNimi, lisaaKausi, lisaaJakso, lisaaVuosi };
        int i = 0;
        for ( TextField edit : edits ) {
            final int k = ++i;
            edit.setOnKeyReleased(e -> kasitteleMuutos(k, (TextField)(e.getSource())));
            
        }
    }
    
    /**
     * Sarjan muutoksien käsittely, ottaa ikkunalta merkkijonon ja asettaa 
     * kentän mukaan tietylle osalle sarjaa
     * @param k Tekstikentän id jota on muokattu
     * @param edit Tekstikentän vastaus merkkijonona
     */
    private void kasitteleMuutos(int k, TextField edit) {
        if ( sarjaValittu == null ) return;
        String s = edit.getText();
        if ( s.isEmpty() ) return;
        String virhe = null;
        switch ( k ) {
        case 1 : virhe = sarjaValittu.setNimi(s); break;
        case 2 : virhe = sarjaValittu.setKausi(s); break;
        case 3 : virhe = sarjaValittu.setJakso(s); break;
        case 4 : virhe = sarjaValittu.setVuosi(s); break;
        default:
        }
        if ( virhe == null ) {
            Dialogs.setToolTipText(edit, "");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit, virhe);
            naytaVirhe(virhe);
        }
    }

    @Override
    public Sarja getResult() {
        return sarjaValittu;
    }

    @Override
    public void handleShown() {
        lisaaNimi.requestFocus();
    }

    @Override
    public void setDefault(Sarja arg0) {
        sarjaValittu = arg0;
        alusta();
        naytaJasen(edits, sarjaValittu);
    }
    
    /**
     * Virheen näyttämisen metodi sarjan lisäys/muokkaus ikkunalle
     * @param virhe Virhe joka näytetään merkkijonona
     */
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            return;
        }
        Dialogs.showMessageDialog(virhe);
    }

    /**
     * @param editsT laatikot joihin tiedot näytetään
     * @param sarja Sarja jonka tiedot haetaan
     */
    public void naytaJasen(TextField[] editsT, Sarja sarja) {
        if ( sarja == null ) return;
        editsT[0].setText(sarja.getNimi());
        editsT[1].setText(sarja.getKausi());
        editsT[2].setText(sarja.getJakso());
        editsT[3].setText(sarja.getVuosi());
        lisaaArvio.setValue(sarja.getArvio());
        lisaaNahty.setSelected(sarja.getNahty());
        
    }

    /**
     * @param modalityStage mille ollaan modaalisia
     * @param oletus Sarja joka on oletus arvo
     * @return Palautetaan null jos peruutetaan
     */
    public static Sarja kysySarja(Stage modalityStage, Sarja oletus) {
        return ModalController.<Sarja, SarjaLisaysController>showModal(
                SarjaLisaysController.class.getResource("SarjaLisaysGUIView.fxml"),
                "Sarjan lisäys/muuttaminen",
                modalityStage, oletus, ctrl -> ctrl.alusta()
                );
    }
    

}
