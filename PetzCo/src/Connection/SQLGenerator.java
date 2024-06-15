package Connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

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

    public static boolean updateSQL(String tabela, int id, String[] colunas, String[] valores) {
        Statement statement = null;
        String idTabela = "id" + tabela.substring(0, 1).toUpperCase() + tabela.substring(1).toLowerCase();
        try (Connection conn = Conexao.getConnection()) {
            statement = conn.createStatement();

            if (colunas.length != valores.length) {
                throw new IllegalArgumentException("Número de colunas e valores não corresponde.");
            }

            String set = "";
            for (int i = 0; i < colunas.length; i++) {
                if (i > 0) {
                	set += ", ";
                }
                set += colunas[i].trim() + " = '" + valores[i].trim() + "'";
            }

            String query = String.format("UPDATE %s SET %s WHERE %s = %d", tabela, set, idTabela, id);

            Log.geraLog("updateSQL: " + query);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }


    public static boolean deleteSQL(String tabela, int id) {
    	Statement statement = null;
    	String idTabela = "id" + tabela.substring(0, 1).toUpperCase() + tabela.substring(1).toLowerCase();
    	try (Connection conn = Conexao.getConnection()) {
            statement = conn.createStatement();
            
	    	String query = String.format("DELETE FROM %s WHERE %s = %d", tabela, idTabela, id);
	    	
	    	Log.geraLog("deleteSQL: " + query);
	    	statement.executeUpdate(query);
    	} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    	return true;
    }

    public static String[][] SelectSQL(String colunas, String tabela, String join, String where) {  	
    	Statement statement = null;
        ResultSet resultSet = null;
        List<String[]> resultList = new ArrayList<>();
        String[] columnNames = null;
        
        if (colunas == null) {
        	colunas = "*";
        }
        
        if (join == null) {
            join = "";
        }
        if (where == null) {
            where = "";
        }

        try (Connection conn = Conexao.getConnection()) {
            statement = conn.createStatement();

            String query = String.format("SELECT %s FROM %s %s %s", colunas, tabela, join, where);

            Log.geraLog("SelectAllSQL: " + query);
            resultSet = statement.executeQuery(query);

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }

            while (resultSet.next()) {
                String[] row = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getString(i);
                }
                resultList.add(row);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String[][] result = new String[resultList.size() + 1][columnNames.length];
        result[0] = columnNames;
        for (int i = 0; i < resultList.size(); i++) {
            result[i + 1] = resultList.get(i);
        }

        return result;
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
        }
    	
        return lastId;
    }
}
