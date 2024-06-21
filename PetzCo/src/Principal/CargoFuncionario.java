package Principal;

import java.util.ArrayList;
import javax.swing.JOptionPane;

import Connection.SQLGenerator;
import Padrao.CRUD;
import Padrao.Relatorio;

public class CargoFuncionario implements CRUD {
	private final String tabela = "cargoFuncionario";
	private int idCargoFuncionario;
	private String nomeCargo;
	private String salarioBase;
	
	public int getIdCargoFuncionario() {
		return idCargoFuncionario;
	}
	public void setIdCargoFuncionario(int idCargoFuncionario) {
		this.idCargoFuncionario = idCargoFuncionario;
	}
	public String getNomeCargo() {
		return nomeCargo;
	}
	public void setNomeCargo(String nomeCargo) {
		this.nomeCargo = nomeCargo;
	}
	public String getSalarioBase() {
		return salarioBase;
	}
	public void setSalarioBase(String salarioBase) {
		this.salarioBase = salarioBase;
	}
	
	@Override
    public void cadastrar() {
	    String nomeCargo = JOptionPane.showInputDialog("Digite o cargo:");
	    String salarioBase = JOptionPane.showInputDialog("Digite o salário base:");

	    String colunas = "nomeCargo, salarioBase";
	    String valores = "'" + nomeCargo + "','" + salarioBase + "'";      
        
        int idCargo = SQLGenerator.insertSQL(tabela, colunas, valores);
        if (idCargo > 0) {
        	JOptionPane.showMessageDialog(null, "Cargo cadastrado com sucesso! Código do cargo:  " + idCargo);
        	setIdCargoFuncionario(idCargo);
        	setNomeCargo(nomeCargo);
        	setSalarioBase(salarioBase);
        } else {
        	JOptionPane.showMessageDialog(null, "Ocorreu algum erro no cadastro.");
        }
	}
    
    @Override
    public void alterar() {
        ArrayList<String> colunasList = new ArrayList<>();
        ArrayList<String> valoresList = new ArrayList<>();

        int idCargo = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do cargo:"));
        String nomeCargo = JOptionPane.showInputDialog("Digite o cargo:");
        if (nomeCargo != null && !nomeCargo.equals("")) {
            colunasList.add("nomeCargo");
            valoresList.add(nomeCargo);
        }

        String salarioBase = JOptionPane.showInputDialog("Digite o salário base:");
        if (salarioBase != null && !salarioBase.equals("")) {
            colunasList.add("salarioBase");
            valoresList.add(salarioBase);
        }

        if (!colunasList.isEmpty() && !valoresList.isEmpty()) {
        	String[] colunas = colunasList.toArray(new String[0]);
        	String[] valores = valoresList.toArray(new String[0]);
        	
        	if (SQLGenerator.updateSQL(tabela, idCargo, colunas, valores)) {
        		JOptionPane.showMessageDialog(null, "Cargo alterado com sucesso!");
        		setNomeCargo(nomeCargo);
        		setSalarioBase(salarioBase);
        	} else {
        		JOptionPane.showMessageDialog(null, "Ocorreu algum erro na alteração.");
        	}
        } else {
        	JOptionPane.showMessageDialog(null, "Não é possível deixar os dados em branco. Nada alterado.");
        }
    }
    
    @Override
    public void excluir() {
    	int idCargo = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do cargo:"));
    	
    	if (SQLGenerator.deleteSQL(tabela, idCargo)) {
    		JOptionPane.showMessageDialog(null, "Cargo excluído com sucesso!");
    	} else {
            JOptionPane.showMessageDialog(null, "Ocorreu algum erro na exclusão.");
        }
    }
    
    public void listar() {
        Relatorio.mostrarDados(SQLGenerator.SelectSQL(null, tabela, null, null));
    }
    
    public String[][] consultarOptions() {
        String[][] resultado = SQLGenerator.SelectSQL(null, tabela, null, null);

        if (resultado == null || resultado.length == 0) {
            return new String[0][0];
        }

        int numeroLinhas = resultado.length -1;
        int numeroColunas = resultado[0].length;

        String[][] resultadoFormatado = new String[numeroLinhas][2];

        for (int i = 0; i < numeroLinhas; i++) {
        	resultadoFormatado[i][0] = resultado[i+1][0];
            StringBuilder sb = new StringBuilder();
            for (int j = 1; j < numeroColunas; j++) {
                if (j > 1) {
                    sb.append(" - Salário Base: R$");
                }
                sb.append(resultado[i+1][j]);
            }
            resultadoFormatado[i][1] = sb.toString();
        }

        return resultadoFormatado;
    }

}
