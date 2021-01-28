/**
 * 
 */
package fxSarja;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author tomih
 * @version 24 Apr 2020
 *
 */
public class SarjaGenreController implements ModalControllerInterface<String> {
    @FXML private TextField textVastaus;
    private String vastaus = null;
    
    /**
     * Genren kysely ikkunan OK napin handlaus
     */
    @FXML private void handleOK() {
        vastaus = textVastaus.getText();
        ModalController.closeStage(textVastaus);
    }

    @Override
    public String getResult() {
        return vastaus;
    }

    @Override
    public void handleShown() {
        textVastaus.requestFocus();
    }

    @Override
    public void setDefault(String oletus) {
        textVastaus.setText(oletus);
    }
    
    /**
     * @param modalityStage Null
     * @param oletus Oletus genre
     * @return Palauttaa genren stringinä
     */
    public static String kysyGenre(Stage modalityStage, String oletus) {
        return ModalController.showModal(SarjaGenreController.class.getResource("SarjaGenreGUI.fxml"), "Genre", modalityStage, oletus);
    }
}
