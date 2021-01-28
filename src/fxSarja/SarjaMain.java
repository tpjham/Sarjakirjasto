package fxSarja;
	
import javafx.application.Application;
import javafx.stage.Stage;
import sarjaRekisteri.SarjaRekisteri;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 * @author tomih
 * @version 3 Jan 2020
 *
 */
public class SarjaMain extends Application {
	@Override
	public void start(Stage primaryStage) {
	    try {
	        final FXMLLoader ldr = new FXMLLoader(getClass().getResource("sarjaGUIView.fxml")); 
	        final Pane root = (Pane)ldr.load();
	        final SarjaGUIController sarjaCtrl = (SarjaGUIController)ldr.getController(); 
	        
	        final Scene scene = new Scene(root);
	        scene.getStylesheets().add(getClass().getResource("sarja.css").toExternalForm()); 
	        primaryStage.setScene(scene);
	        primaryStage.setTitle("Sarja-kirjasto");
	        	        
	        primaryStage.setOnCloseRequest((event) -> {
	            // Kutsutaan voikoSulkea-metodia
	            if ( !sarjaCtrl.voikoSulkea() ) event.consume();
	        });
	        
	        SarjaRekisteri rekisteri = new SarjaRekisteri();
	        sarjaCtrl.setRekisteri(rekisteri);
	        
	        primaryStage.show();
	        sarjaCtrl.lueTiedostosta();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
