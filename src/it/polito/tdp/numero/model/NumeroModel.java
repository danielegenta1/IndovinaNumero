package it.polito.tdp.numero.model;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

//inserisco in MODEL cosa riguarda il gioco
public class NumeroModel
{
	private List<Integer>tentativi; //possibile sostituzione con hashmap, meno dispendioso
	private final int NMAX = 100;
	private final int TMAX = 8;
	
	private boolean inGioco = false;

	//private int tentativiFatti; convertita con property
	private IntegerProperty tentativiFatti;

	private int segreto;
	
	//COSTRUTTORE
	public NumeroModel()
	{
		inGioco = false;
		tentativiFatti = new SimpleIntegerProperty();
		tentativi = new LinkedList<Integer>();
	}
	
	// GETTERS AND SETTERS
	public int getSegreto() {
		return segreto;
	}

	public boolean isInGioco() {
		return inGioco;
	}
	
	public int getTMAX() {
		return TMAX;
	}

	//gestione property
	public final IntegerProperty tentativiFattiProperty() {
		return this.tentativiFatti;
	}
	

	public final int getTentativiFatti() {
		return this.tentativiFattiProperty().get();
	}
	

	public final void setTentativiFatti(final int tentativiFatti) {
		this.tentativiFattiProperty().set(tentativiFatti);
	}
	
	/**
	 * Avvia nuova partita
	 * */
	public void newGame()
	{
		this.tentativi.clear();
		inGioco = true;
		this.segreto = (int) (Math.random() * NMAX) + 1;
		this.tentativiFatti.set(0);;
	}
	
	/**
	 * Metodo per effettuare un tentativo
	 * 
	 * @param t il tentativo
	 * @return 1 se alto, -1 se basso, 0 se utente ha indovinato
	 */
	public int tentativo(int t)
	{
		//controllo se la partita è in corso
		if (!inGioco)
		{
			throw new IllegalStateException("La partita è terminata");
		}
		
		//controllo se input è in range corretto
		if (!tentativoValido(t))
		{
			throw new InvalidParameterException(String.format("Devi inserire un numero fra %d e %d", 1, NMAX));
		}
		
		//gestisci tentativo
		this.tentativiFatti.set(this.tentativiFatti.get() + 1);
		this.tentativi.add(t); //aggiungo tentativo alla lista
		if (this.tentativiFatti.get() == this.TMAX)
		{
			//partita finita, ho esaurito i tentativi
			this.inGioco = false;
			
		}
		
		if (t == this.segreto) {
			//ho indovinato
			this.inGioco = false;
			return 0;
		}
		else if (t > this.segreto)
			return 1;
		else 
			return -1;
	}
	
	public boolean tentativoValido(int t)
	{
		
		if (t < 1 || t > NMAX)
			return false;
		else if (tentativi.contains(t)) //tentativo già fatto
			return false;
		else
			return true;
	}
	
	
	
	
	
}
