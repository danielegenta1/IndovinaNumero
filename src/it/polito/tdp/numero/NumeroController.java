package it.polito.tdp.numero;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.numero.model.NumeroModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

//Inserisco in CONTROLLER cosa riguarda l'interfaccia
public class NumeroController {

	//
	private NumeroModel model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private HBox boxControllopartita;

	@FXML
	private TextField txtTentativiFatti;
	// numero di tentativi rimasti ancora da provare

	@FXML
	private HBox boxControlloTentativi;

	@FXML
	private TextField txtTentativo;
	// tentativo inserito dall'utente

	@FXML
	private TextArea txtMessaggi;
	
	@FXML
	private ProgressBar progressBar;

	@FXML
	void handleNuovaPartita(ActionEvent event) {
		// Gestisce l'inizio di una nuova partita


		// Gestione dell'interfaccia
		boxControllopartita.setDisable(true);
		boxControlloTentativi.setDisable(false);
		
		txtMessaggi.clear();
		txtTentativo.clear();
		//txtRimasti.setText(Integer.toString(0)); convertito con property
		
		// comunico al modello di iniziare una nuova partita
		model.newGame();
	}

	@FXML
	void handleProvaTentativo(ActionEvent event) {

		// Leggi il valore del tentativo
		String ts = txtTentativo.getText();

		// Controlla se è valido

		int tentativo ;
		try 
		{
			tentativo = Integer.parseInt(ts);
		} 
		catch (NumberFormatException e)
		{
			// la stringa inserita non è un numero valido
			txtMessaggi.appendText("Non è un numero valido\n");
			return ;
		}
		
		if (!model.tentativoValido(tentativo))
			txtMessaggi.appendText("Range non valido o mossa già effettuata\n");
		
		int risultato = model.tentativo(tentativo);
		if (risultato == 0)
		{
			txtMessaggi.appendText("Complimenti, hai indovinato in "+ model.getTentativiFatti() +" tentativi\n");
			boxControllopartita.setDisable(false);
			boxControlloTentativi.setDisable(true);
		}
		else if (risultato < 0)
			txtMessaggi.appendText("Tentativo troppo BASSO\n");
		else
			txtMessaggi.appendText("Tentativo troppo ALTO\n");

		// Aggiornare interfaccia con n. tentativi rimasti
		//txtRimasti.setText(Integer.toString(model.getTentativiFatti())); convertito con property
		
		if (!model.isInGioco())
		{	
			//la partita è finita!
			if (risultato != 0)
			{
				txtMessaggi.appendText("Hai perso!");
				txtMessaggi.appendText(String.format("\nIl numero segreto era: %d", model.getSegreto()));
				
				// Gestione dell'interfaccia
				boxControllopartita.setDisable(false);
				boxControlloTentativi.setDisable(true);
			}
		}

	}
	
	@FXML
	void handleAbbandona(ActionEvent event) 
	{
		
		boxControllopartita.setDisable(false);
		boxControlloTentativi.setDisable(true);	
	}

	@FXML
	void initialize() {
		assert boxControllopartita != null : "fx:id=\"boxControllopartita\" was not injected: check your FXML file 'Numero.fxml'.";
		assert txtTentativiFatti != null : "fx:id=\"txtRimasti\" was not injected: check your FXML file 'Numero.fxml'.";
		assert boxControlloTentativi != null : "fx:id=\"boxControlloTentativi\" was not injected: check your FXML file 'Numero.fxml'.";
		assert txtTentativo != null : "fx:id=\"txtTentativo\" was not injected: check your FXML file 'Numero.fxml'.";
		assert txtMessaggi != null : "fx:id=\"txtMessaggi\" was not injected: check your FXML file 'Numero.fxml'.";

	}
	
	public void setModel(NumeroModel model) 
	{
		this.model = model;
		
		txtTentativiFatti.textProperty().bind(Bindings.convert(model.tentativiFattiProperty()));
		progressBar.progressProperty().bind(model.tentativiFattiProperty());
		
	}
	

}
