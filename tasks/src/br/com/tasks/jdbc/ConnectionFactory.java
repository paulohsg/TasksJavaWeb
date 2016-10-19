package br.com.tasks.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	public Connection getConnection(){
		System.out.println("Conectando ao Banco de Dados...");
		
		String stringDeConexao="jdbc:mysql://localhost/banco";
		String user="root";
		String pass="";
		
		try{
			return DriverManager.getConnection
			(stringDeConexao,user,pass);
		}catch(SQLException e){
			throw new RuntimeException(e+"\nNÃO FOI POSSÍVEL CONECTAR NO BANCO DE DADOS");
		}
	}
}