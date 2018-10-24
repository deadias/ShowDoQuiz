package br.com.andrea.dias;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import br.com.andrea.dias.modelo.Alternativa;
import br.com.andrea.dias.modelo.Questao;
import br.com.andrea.dias.servico.QuizServico;

/**
 * Programa que exibe as perguntas e alternativas
 */
public class ShowDoQuiz {

	//Utilizaremos o leitor para receber os dados informados pelo usuário
	private static Scanner leitor = new Scanner(System.in);

	//Este é o serviço responsável pela manipulação do banco de dados
	private static QuizServico quizServico;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		exibirMensagem("Bem vindo ao Show do Quiz!");
		exibirMensagem("");
		
		//Instanciando o serviço
		quizServico = new QuizServico();
		
		//Consultando todas as questões
		ArrayList<Questao> questoes = quizServico.consultarQuestoes();
		
		//pontuação do usuário
		int pontos = 0;
		
		//Iterando e exibindo as questões
		for (Questao questao : questoes) {
			
			//Exibindo o tópico
			exibirMensagem("Tópico: " + questao.getTopico().getDescricao());
			exibirMensagem("");
			
			//Exibindo a pergunta
			exibirMensagem(questao.getPergunta());
			exibirMensagem("");
			
			//Índice da alternativa para ser escolhido pelo usuário
			int i = 1;
			
			//Exibindo todas as alternativas
			for (Alternativa alternativa : questao.getAlternativas()) {
				exibirMensagem(i + " - " + alternativa.getDescricao());
				i++;
			}
			
			//Lendo a resposta do usuário
			int resposta = leitor.nextInt();
			
			//Recuperando a alternativa do array de alternativas pelo índice escolhido pelo usuário
			Alternativa alternativa = questao.getAlternativas().get(resposta - 1);
			
			//Checando se o usuário acertou questão
			if (alternativa.isCorreta()) {
				exibirMensagem("Resposta CORRETA! Parabéns!");
				pontos++;
			} else {
				exibirMensagem("Resposta ERRADA!");
			}
			
			exibirMensagem("");
		}
		
		//Exibindo a pontuação
		exibirMensagem("Total de Pontos: " + pontos + " de " + questoes.size());
	}
	
	//Exibie a mensagem na tela
	private static void exibirMensagem(String mensagem) {
		System.out.println(mensagem);
	}
}
