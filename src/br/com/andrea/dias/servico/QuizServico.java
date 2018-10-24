package br.com.andrea.dias.servico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.com.andrea.dias.db.DBConexao;
import br.com.andrea.dias.modelo.Alternativa;
import br.com.andrea.dias.modelo.Questao;
import br.com.andrea.dias.modelo.Topico;

/**
 * Serviço responsável pela manutenção do sistema
 */
public class QuizServico {

	//Objeto responsável pela conexão com o banco de dados
	private Connection conexao;
	

	//Construtor do serviço
	public QuizServico() throws ClassNotFoundException, SQLException {
		
		//Abrindo a conexão com o banco
		this.conexao = DBConexao.getDBConnection();
	}
	
	//Consulta todos os tópicos existentes no sistema
	private ArrayList<Topico> consultarTopicos() throws SQLException {
		Statement statement = this.conexao.createStatement();
		
		//SQL que será executado no banco de dados
		ResultSet resultado = statement.executeQuery("SELECT * FROM topico ORDER BY descricao");
		
		//Criando um array vazio para preencher com os tópicos retornados do banco
		ArrayList<Topico> topicos = new ArrayList<Topico>();
		
		//Iterando o resultado da consulta
        while (resultado.next()) {
        	
        	//Instanciando um novo tópico
        	Topico topico = new Topico();
        	topico.setId(resultado.getInt("id"));
        	topico.setDescricao(resultado.getString("descricao"));
        	
        	//Adicionando ao array de tópicos
        	topicos.add(topico);
        }
        
        //retornando o array de tópicos
        return topicos;
	}
	
	//Cadastra um novo tópico
	public void cadastrarTopico(String descricao) throws SQLException {
    	String insertTopico = "INSERT INTO topico (descricao) VALUES (?)";
    	PreparedStatement ps = this.conexao.prepareStatement(insertTopico);
    	ps.setString(1, descricao);
    	ps.executeUpdate();
	}
	
	//Consulta todas as questões cadastradas no sistema
	public ArrayList<Questao> consultarQuestoes() throws SQLException {
		
		//Array de questões que será retornado
		ArrayList<Questao> questoes = new ArrayList<Questao>();
		
		Statement stQuestoes = conexao.createStatement();
		ResultSet resQuestoes = stQuestoes.executeQuery("SELECT * FROM questao"); 
		
		//Iterando o resultado da consulta de questões
        while (resQuestoes.next()) {
        	
        	//instanciando uma nova questão
        	Questao questao = new Questao();
        	questao.setId(resQuestoes.getInt("id"));
        	questao.setPergunta(resQuestoes.getString("pergunta"));
        	
        	//Consultando o tópico relacionado à questão atual
        	Statement stTopicos = conexao.createStatement();
        	ResultSet resTopico = stTopicos.executeQuery("SELECT * FROM topico WHERE id = " + resQuestoes.getInt("topico_id"));
        	resTopico.next();
        	
        	//Instanciando o tópico e adicionando na questão
        	Topico topico = new Topico();
        	topico.setId(resTopico.getInt("id"));
        	topico.setDescricao(resTopico.getString("descricao"));
        	
        	questao.setTopico(topico);
        	
        	//Consultando todas alternativas da questão
        	Statement stAlternativas = conexao.createStatement();
        	ResultSet resAlternativas = stAlternativas.executeQuery("SELECT * FROM alternativa WHERE questao_id = " + questao.getId());
        	
        	//Iterando o resultado e adicionando as alternativas no array dentro da questão
        	while (resAlternativas.next()) {
        		Alternativa alternativa = new Alternativa();
        		alternativa.setId(resAlternativas.getInt("id"));
        		alternativa.setDescricao(resAlternativas.getString("descricao"));
        		alternativa.setCorreta(resAlternativas.getBoolean("correta"));
        		questao.addAlternativa(alternativa);
        	}
        	
        	questoes.add(questao);
        }
        
        return questoes;
	}
	
	//Cadastra uma nova questão e suas alternativas
	public void cadastrarQuestao(Questao questao) throws SQLException {
		
		//Inserindo a nova questão
    	String insertQuestao = "INSERT INTO questao (topico_id, pergunta) VALUES (?, ?)";
    	PreparedStatement ps = conexao.prepareStatement(insertQuestao, Statement.RETURN_GENERATED_KEYS);
    	ps.setInt(1, questao.getTopico().getId());
    	ps.setString(2, questao.getPergunta());
    	ps.executeUpdate();
    	
    	ResultSet rsChave = ps.getGeneratedKeys();
    	rsChave.next();
    	
    	//Recuperando o id da questão recém criada para relacionar com as alternativas
    	int questaoId = rsChave.getInt(1);
    	
    	String insertAlternativa = "INSERT INTO alternativa (questao_id, descricao, correta) VALUES (?, ?, ?)";
    	
    	//iterando as alternativas e salvando no banco
    	for (Alternativa alternativa : questao.getAlternativas()) {
    		ps = conexao.prepareStatement(insertAlternativa);
    		ps.setInt(1,  questaoId);
    		ps.setString(2,  alternativa.getDescricao());
    		ps.setBoolean(3, alternativa.isCorreta());
    		ps.executeUpdate();
    	}
	}
}
