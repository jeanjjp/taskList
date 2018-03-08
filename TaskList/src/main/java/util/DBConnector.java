package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
	//classe responsavel pela conexao com BD
	//faz a conexao sempre que é instanciada
	public Connection getConnection() {
		String nomeBD = "db_task";
		String servidor = "jdbc:mysql://localhost/" + nomeBD;
		String usuario = "root";
		String senha = "";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conexao = DriverManager.getConnection(servidor, usuario, senha);
			System.out.println("Conexao aberta");
			return conexao;
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Erro ao obter conexao com o banco "+e.getMessage());
			throw new RuntimeException(e);
		}
	}
	//metodo para fechar a conexao com bd
	public static void fecharConexao(Connection con) {
		try {
			con.close();
			System.out.println("Conexao fechada");
		} catch (SQLException e) {
			System.out.println(e);
			throw new RuntimeException(e);
		}
	}
}