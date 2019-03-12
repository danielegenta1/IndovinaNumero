package it.polito.tdp.numero.model;

import java.security.InvalidParameterException;

//inserisco cosa riguarda il gioco, pulendo il controller
public class NumeroModel
{
	private final int NMAX = 100;
	private final int TMAX = 8;

	

	private int segreto;
	
	
	/**
	 * Avvia nuova partita
	 * */
	public void newGame()
	{
		inGioco = true;
		this.segreto = (int) (Math.random() * NMAX) + 1;
		this.tentativiFatti = 0;
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
		this.tentativiFatti++;
		if (this.tentativiFatti == this.TMAX)
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
		else
			return true;
	}
	
	public int getSegreto() {
		return segreto;
	}

	public int getTentativiFatti() {
		return tentativiFatti;
	}

	public boolean isInGioco() {
		return inGioco;
	}

	private int tentativiFatti;
	private boolean inGioco = false;
	
	public NumeroModel()
	{
		inGioco = false;
	}
	
	public int getTMAX() {
		return TMAX;
	}
	
	
}
