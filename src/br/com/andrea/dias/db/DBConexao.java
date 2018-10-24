package br.com.andrea.dias.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Classe responsável pela conexão com o banco de dados
public class DBConexao {

	private static Connection conexao;
	
	public static Connection getDBConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		if (conexao == null) {
			conexao = DriverManager
	                .getConnection("jdbc:mysql://127.0.0.1:33306/quiz?user=root&password=");	
		}
		
		return conexao;
	}
}
