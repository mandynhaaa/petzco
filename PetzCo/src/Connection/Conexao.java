package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Padrao.Log;
 
public class Conexao {
	public Conexao () {
	}
	
	public static Connection con = null;
	 
    public static Connection getConnection() {
    	Log.geraLog("Conectando ao banco...");
    	try {
    		return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbpetzco", "root", "admin");
    	} catch (SQLException e) {
    		System.out.println("Erro ao Conectar");
    		throw new RuntimeException(e);
    	}
    }
}
