package br.com.tasks.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;

import br.com.tasks.jdbc.ConnectionFactory;
import br.com.tasks.jdbc.model.Task;

public class TaskDAO {
	Connection connection;
	
	public TaskDAO(){
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public void adiciona(Task task){
		String sql = "insert into Tasks (descricao) " +
					"values (?)";
		
		try{
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, task.getDescricao());
			
			stmt.execute();
			stmt.close();
		}catch (SQLException e){
			throw new RuntimeException (e);
		}
	}
	
	public List<Task> lista(){
		try{
			List<Task> Tasks = new ArrayList<Task>();
			PreparedStatement stmt = this.connection.prepareStatement
			("select * from Tasks");
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				//criando objeto Task
				Task task = new Task();
				task.setId(rs.getLong("id"));
				task.setDescricao(rs.getString("descricao"));
				task.setFinalizado(rs.getBoolean("finalizado"));
				
				if(rs.getDate("dataFinalizacao") != null)
				{
				//montando data atraves do calendar
				Calendar dataFinalizacao = Calendar.getInstance();
				dataFinalizacao.setTime(rs.getDate("dataFinalizacao"));
				
				task.setDataFinalizacao(dataFinalizacao);
				}
				//adicionar objeto a lista
				Tasks.add(task);
			}
			rs.close();
			stmt.close();
			System.out.println("Lista gerada coim Sucesso!");
			return Tasks;
	
			}catch(SQLException e){
				throw new RuntimeException(e);
		}
	}
	
	public void remove(Task task){
		try{
			PreparedStatement stmt = this.connection.prepareStatement
			("delete from Tasks where id = ?");
			
			stmt.setLong(1, task.getId());
			stmt.execute();
			stmt.close();
			
			System.out.println("Task Excluida com SUCESSO!");
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	public Task buscaPorId(Long id){
		
		try{
			PreparedStatement stmt = this.connection.prepareStatement("select * from Tasks");
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				//System.out.println(rs.getLong("id")+ " // " + id);
				if(id == rs.getLong("id"))
				{
					//criando objeto Task
					Task task = new Task();
					task.setId(rs.getLong("id"));
					task.setDescricao(rs.getString("descricao"));
					task.setFinalizado(rs.getBoolean("finalizado"));
					
					if(rs.getDate("dataFinalizacao") != null)
					{
						//montando data atraves do calendar
						Calendar dataFinalizacao = Calendar.getInstance();
						dataFinalizacao.setTime(rs.getDate("dataFinalizacao"));
					
						task.setDataFinalizacao(dataFinalizacao);
					}
					System.out.println("retornada Task:"+task.getId());
					return task;
				}
			}
				return null;
			}catch(SQLException e){
				throw new RuntimeException(e);
			}	
	}
	
	public void altera(Task task){
		String sql = "update Tasks set descricao=?, finalizado=?, dataFinalizacao=? where id=?";
		
		try{
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			
			stmt.setString(1, task.getDescricao());
			stmt.setBoolean(2, task.isFinalizado());
			if(task.getDataFinalizacao() != null)
			{
				stmt.setDate(3, new Date(task.getDataFinalizacao().getTimeInMillis()));
			}
			else
			{
				stmt.setDate(3, null);
			}
			
			stmt.setLong(4, task.getId());
				
			stmt.execute();
			stmt.close();
			
			System.out.println("DADOS ALTERADOS COM SUCESSO!");
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	public void finaliza(Long id){
		
		Task task = new TaskDAO().buscaPorId(id);
		
		String sql = "update Tasks set finalizado=?, dataFinalizacao=? where id=?";
		
		try{
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			
			stmt.setBoolean(1, true);
			
			stmt.setDate(2, new Date(Calendar.getInstance().getTimeInMillis()));
			
			stmt.setLong(3, task.getId());
				
			stmt.execute();
			stmt.close();
			
			System.out.println("DADOS ALTERADOS COM SUCESSO!");
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		
		
	}
}