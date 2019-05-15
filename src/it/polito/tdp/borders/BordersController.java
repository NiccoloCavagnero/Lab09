/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BordersController {

	Model model = new Model();
	
	public void setModel(Model model) {
		btnSearch.setDisable(true);
		boxNazioni.setDisable(true);
		this.model = model;
	}

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader
	
	 @FXML
	 private ComboBox<Country> boxNazioni;

	 @FXML
	 private TextArea txtResult;

	 @FXML
	 private Button btnSearch;
	 
	@FXML
	void doCalcolaConfini(ActionEvent event) {
		
		if ( Integer.parseInt(txtAnno.getText()) <= 2006 ) {
			try {
	    		model.generaGrafo(Integer.parseInt(txtAnno.getText()));
	    		
	    		txtResult.clear();
	    		
	    		txtResult.appendText("Numero componenti connesse: "+model.getNumComponentiConnesse()+"\n\n");
	    		
	    		for (Country c : model.getGrafo().vertexSet()) {	
	    			txtResult.appendText(c.toString()+" -> "+model.getGrafo().degreeOf(c)+"\n");
	    		}
	    		
	    		boxNazioni.getItems().addAll(model.getGrafo().vertexSet());
	    		
	    		btnSearch.setDisable(false);
	    		boxNazioni.setDisable(false);
	    		
	    		}catch(NumberFormatException nbe) {
	    			txtResult.appendText("Inserire un anno corretto( < 2006 )! \n");
	    		}
		}
		else {
			txtResult.setText("Inserire un anno corretto( < 2006 )! \n");
		}
	}
	
	 @FXML
	 void doSearch(ActionEvent event) {
		 
		txtResult.clear();
		
		List<Country> lista = new ArrayList<>(model.search(boxNazioni.getValue()));
	    	
	    txtResult.appendText("#STATI RAGGIUNGIBILI : "+lista.size()+"\n\n");
	    	for (Country stato : lista ) {
	    		txtResult.appendText(stato.toString()+"\n");
	    	}
    }

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
		assert boxNazioni != null : "fx:id=\"boxNazioni\" was not injected: check your FXML file 'Borders.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";
        assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'Borders.fxml'.";
	}
}
