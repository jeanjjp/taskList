package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import model.Task;
import util.Constants;
import util.DBConnector;

public class TaskDAO {

	// classe responsavel pela transferencia de dados entre BD e ssistema.
	
	private Connection connection;

	public int incluirOuAlterarTask(Task task) {

		int idInserido = 0;
		String sql;
		if (task.getIdTask() <= 0) {
			sql = "INSERT INTO task (titulo_task, descricao_task, status_task, data_criacao) VALUES (?, ?, ?, ?)";
		} else {
			sql = "UPDATE task SET titulo_task = ?, descricao_task = ?, status_task = ?  WHERE id_task = ?";
		}

		try {
			// abre conexao com bd
			this.connection = new DBConnector().getConnection();
			PreparedStatement stmt;
			if (task.getIdTask() <= 0) {
				stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				// seta os valores
				Date dataAtual = new Date(System.currentTimeMillis());
				task.setDataCriacao(dataAtual);
				java.sql.Date dataAtualSQL = new java.sql.Date(task.getDataCriacao().getTime());
				task.setStatusTask(Constants.EM_ANDAMENTO);
				// seta na string
				stmt.setString(1, task.getTituloTask());
				stmt.setString(2, task.getDescricaoTask());
				stmt.setInt(3, task.getStatusTask());
				stmt.setDate(4, dataAtualSQL);
				// excuta
				stmt.executeUpdate();
				// retorna o ID inserido no BD
				ResultSet rs = stmt.getGeneratedKeys();
				// verifica
				if (rs.next()) {
					idInserido = rs.getInt(1);
				}

				if (idInserido > 0) {
					System.out.println("Task inserida");
				} else {
					System.out.println("Erro ao inserir Task \n" + stmt);
				}
			} else {
				stmt = connection.prepareStatement(sql);

				stmt.setString(1, task.getTituloTask());
				stmt.setString(2, task.getDescricaoTask());
				stmt.setInt(3, task.getStatusTask());
				stmt.setInt(4, task.getIdTask());

				int ok = stmt.executeUpdate();

				if (ok == 1) {
					System.out.println("Task atualizada com sucesso no BD!");
					idInserido = task.getIdTask();
				} else {
					System.out.println("Erro ao atualizar Task no BD! \n" + stmt);
				}
			}

			stmt.close();

			return idInserido;

		} catch (SQLException e) {
			System.out.println("Erro ao inserir Task " + e);
			throw new RuntimeException(e);
		} finally {
			DBConnector.fecharConexao(this.connection);
		}
	}

	//
	//
	public ArrayList<Task> listarTasks(String pesquisa) {

		Task task;
		ArrayList<Task> taskList = new ArrayList<Task>();
		String sql = "";

		if (pesquisa.equalsIgnoreCase("") || pesquisa == null) {
			sql = "SELECT * from task";
		}else{
			sql = "SELECT * from task WHERE titulo_task LIKE '%"+pesquisa+"%' OR descricao_task LIKE '%"+pesquisa+"%'";
		}

		try {
			this.connection = new DBConnector().getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			
			// executa
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				//cria uma nova task
				task = new Task();
				//seta os valores
				task.setIdTask(rs.getInt("id_task"));
				task.setTituloTask(rs.getString("titulo_task"));
				task.setDescricaoTask(rs.getString("descricao_task"));
				task.setStatusTask(rs.getInt("status_task"));
				task.setDataCriacao(rs.getDate("data_criacao"));
				
				//add a task na lista
				taskList.add(task);

			}
			System.out.println("Buscando Array de tasks");
			stmt.close();
			return taskList;
		} catch (SQLException e) {
			System.out.println("Erro ao buscar tasks no BD! " + e);
			return taskList;
		} finally {
			DBConnector.fecharConexao(this.connection);
		}
	}

	public boolean deletarTask(int idTask) {

		boolean removidoSucesso = false;
		String sql = "DELETE FROM task WHERE id_task = ?";

		try {
			this.connection = new DBConnector().getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, idTask);
			int ok = stmt.executeUpdate();

			if (ok == 1) {
				System.out.println("Task removida com sucesso");
				removidoSucesso = true;
			} else {
				System.out.println("Erro ao remover task");
			}

			stmt.close();
			return removidoSucesso;
		} catch (SQLException e) {
			System.out.println("Erro ao remover Task no BD! " + e);
			throw new RuntimeException(e);
		} finally {
			DBConnector.fecharConexao(this.connection);
		}
	}

}
