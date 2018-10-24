package br.com.andrea.dias.modelo;

import java.util.ArrayList;

public class Questao extends ModeloBase {

	private String pergunta;
	
	private Topico topico;
	
	private ArrayList<Alternativa> alternativas;

	public Questao() {
		this.alternativas = new ArrayList<Alternativa>();
	}
	
	public String getPergunta() {
		return pergunta;
	}

	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}

	public Topico getTopico() {
		return topico;
	}

	public void setTopico(Topico topico) {
		this.topico = topico;
	}

	public ArrayList<Alternativa> getAlternativas() {
		return alternativas;
	}

	public void setAlternativas(ArrayList<Alternativa> alternativas) {
		this.alternativas = alternativas;
	}
	
	public void addAlternativa(Alternativa alternativa) {
		this.alternativas.add(alternativa);
	}
	
}
