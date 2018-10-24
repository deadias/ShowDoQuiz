package br.com.andrea.dias;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import br.com.andrea.dias.modelo.Alternativa;
import br.com.andrea.dias.modelo.Questao;
import br.com.andrea.dias.modelo.Topico;
import br.com.andrea.dias.servico.QuizServico;

/**
 * Programa responsável pela manutenção do jogo Show do Quiz
 * Atravéz deste programa é possível cadastrar novos tópicos e perguntas
 */
public class QuizManutencao {
	
	//Utilizaremos o leitor para receber os dados informados pelo usuário
	private static Scanner leitor = new Scanner(System.in);
	
	//Este é o serviço responsável pela manipulação do banco de dados
	private static QuizServico quizServico;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		//Criando uma nova instância do serviço
		quizServico = new QuizServico();
		
		exibirMensagem("Escolha uma opção:");
		
		//O programa exibirá um menu até que o usuário digite zero para sair
		while(true) {
			exibirMensagem("1 - Manter Tópicos");
			exibirMensagem("2 - Manter Perguntas");
			exibirMensagem("0 - Sair");
			
			//Lendo a opção escolhida pelo usuário
			int opcao = leitor.nextInt();
			
			//Decidindo qual ação executar
			if (opcao == 1) {
				mostrarMenuTopicos();
			} else if (opcao == 2) {
				mostrarMenuPerguntas();
			} else if (opcao == 0) {
				exibirMensagem("Tchau!");
				System.exit(0);
			} else {
				exibirMensagem("Opção inválida.");
			}
		}		
	}
	
	//Exibe as opções disponíveis para a manutenção de tópicos
	private static void mostrarMenuTopicos() throws SQLException {
		
		//O programa exibirá um menu até que o usuário digite zero para voltar ao menu anterior
		while(true) {
			exibirMensagem("1 - Listar Tópicos");
			exibirMensagem("2 - Cadastrar Tópicos");
			exibirMensagem("0 - Voltar");

			int opcao = leitor.nextInt();
			leitor.nextLine();
			
			if (opcao == 1) {
				listarTopicos();
			} else if (opcao == 2) {
				cadastrarTopico();
			} else if (opcao == 0) {
				break;
			} else {
				exibirMensagem("Opção inválida.");
			}
		}
	}
	
	//Exibe todos os tópicos cadastrados no sistema
	private static void listarTopicos() throws SQLException {
		exibirMensagem("Tópicos cadastrados: ");
		
		//Chamando o método 'consultarTopicos' do serviço
		ArrayList<Topico> topicos = quizServico.consultarTopicos();
		
		//Iterando todos os tópicos retornados para exibir na tela
		for (Topico topico : topicos) {
			exibirMensagem(topico.getId() + " - " + topico.getDescricao());
		}
		
        exibirMensagem("");
	}

	//Exibe as opções disponíveis para a manutenção de perguntas
	private static void mostrarMenuPerguntas() throws SQLException {
		
		//O programa exibirá um menu até que o usuário digite zero para voltar ao menu anterior
		while(true) {
			exibirMensagem("1 - Cadastrar Pergunta");
			exibirMensagem("0 - Voltar");

			int opcao = leitor.nextInt();
			leitor.nextLine();
			
			if (opcao == 1) {
				cadastrarPerguntas();
			} else if (opcao == 0) {
				break;
			} else {
				exibirMensagem("Opção inválida.");
			}
		}
	}
	
	//Cadastra um novo tópico
	private static void cadastrarTopico() throws SQLException {
		exibirMensagem("Informe o Tópico:");
		String topico = leitor.nextLine();
		quizServico.cadastrarTopico(topico);
	}
	
	//Cadastra uma nova pergunta e suas alternativas
	private static void cadastrarPerguntas() throws SQLException {

        exibirMensagem("Escolha um Tópico pelo ID:");
        
        //Listando os tópicos para que o usário selecione um
        listarTopicos();
        
        //Lendo o tópico escolhido pelo usuário
    	int topicoId = leitor.nextInt();
    	leitor.nextLine();
    	
    	//Criando uma nova instância de Topico
    	Topico topico = new Topico();
    	topico.setId(topicoId);
    	
    	exibirMensagem("Qual é a pergunta?");
    	String pergunta = leitor.nextLine();
    	
    	//Criando uma nova instância de Questao
    	Questao questao = new Questao();
    	questao.setPergunta(pergunta);
    	questao.setTopico(topico);
    	
    	//Laço para obter as 5 alternativas
    	for (int c = 1; c <= 5; c++) {
    		exibirMensagem("Informe a alternativa " + c);
    		String descricao = leitor.nextLine();
    		
    		exibirMensagem("Esta é a resposta correta? 0 - Não, 1 - Sim");
    		int correta = leitor.nextInt();
    		leitor.nextLine();
    		
    		Alternativa alternativa = new Alternativa();
    		alternativa.setDescricao(descricao);
    		alternativa.setCorreta(correta == 1);
    		
    		//Adicionando a alternativa ao array de alternativas da questão
    		questao.addAlternativa(alternativa);
    	}
    
    	//Chamando o serviço para cadastrar a nova pergunta e suas alternativas 
    	quizServico.cadastrarQuestao(questao);
    	
    	exibirMensagem("Questão cadastrada com sucesso");
    	exibirMensagem("");
	}

	//Exibie a mensagem na tela
	private static void exibirMensagem(String mensagem) {
		System.out.println(mensagem);
	}
}
