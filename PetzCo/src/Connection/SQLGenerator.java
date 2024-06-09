package Connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import Principal.Log;

public class SQLGenerator {

    public static int insertSQL(String tabela, String colunas, String valores) {    
    	int lastId = 0;
	    Statement statement = null;
	    
	    try (Connection conn = Conexao.getConnection()) {
	        statement = conn.createStatement();
	        String query = String.format("INSERT INTO %s (%s) VALUES (%s)", tabela, colunas, valores);
	        
	        Log.geraLog("insertSQL: " + query);
	        statement.executeUpdate(query);	        
	        lastId = getLastId(tabela);
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
        
        return lastId;
    }

    public static int updateSQL(String tabela) {
    	Log.geraLog("updateSQL");
    	return 1;
    }

    public static int deleteSQL(String tabela) {
    	Log.geraLog("deleteSQL");
    	return 1;
    }

    public static int SelectSQL(String tabela) {
    	Log.geraLog("SelectSQL");
    	return 1;
    }
    
    public static int getLastId(String tabela) {
    	Statement statement = null;
    	ResultSet resultSet = null;
    	String idTabela = "id" + tabela.substring(0, 1).toUpperCase() + tabela.substring(1).toLowerCase();
    	int lastId = 0;
    	try (Connection conn = Conexao.getConnection()) {
    		String query = "SELECT MAX(" + idTabela + ") AS lastId FROM " + tabela;

    		statement = conn.createStatement();
    		Log.geraLog("getLastId: " + query);
	        resultSet = statement.executeQuery(query);
	
	        if (resultSet.next()) {
	            lastId = resultSet.getInt("lastId");
	        }
    	} catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    	
        return lastId;
    }
}
