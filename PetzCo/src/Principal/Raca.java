package Principal;

import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import Connection.SQLGenerator;
import Padrao.CRUD;
import Padrao.Item;
import Padrao.Log;
import Padrao.Relatorio;

public class Raca implements CRUD {
	private final String tabela = "raca";
	private int idRaca;
	private String nome;
	private String porte;
	private String descricao;
	
	public int getIdRaca() {
		return idRaca;
	}

	public void setIdRaca(int idRaca) {
		this.idRaca = idRaca;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPorte() {
		return porte;
	}

	public void setPorte(String porte) {
		this.porte = porte;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public void cadastrar() {
		Especie especie = new Especie();
		String[][] optionsEspecie = especie.consultarOptions();
		
		if (optionsEspecie == null || optionsEspecie.length == 0) {
			JOptionPane.showMessageDialog(null, "Necessário cadastrar espécies primeiro!");
			return;
		}
		
	    JComboBox<Item> comboBox = new JComboBox<>();
	    for (String[] option : optionsEspecie) {
	        int idOption = Integer.parseInt(option[0]);
	        String nomeOption = option[1];
	        comboBox.addItem(new Item(idOption, nomeOption));
	    }

	    int selecao = JOptionPane.showConfirmDialog(null, comboBox, "Selecione a espécie: ", JOptionPane.OK_CANCEL_OPTION);

	    int fkEspecie = 0;
	    if (selecao == JOptionPane.OK_OPTION) {
	    	Item opcao = (Item) comboBox.getSelectedItem();
	        if (opcao != null) {
	        	fkEspecie = opcao.getId();
	            Log.geraLog("Espécie selecionada: " + opcao.getNome() + " (ID: " + fkEspecie + ")");
	        } else {
	        	Log.geraLog("Nenhuma opção selecionada.");
	        }
	    } else {
	    	Log.geraLog("Nenhuma opção selecionada.");
	    }
	    
	    String nome = JOptionPane.showInputDialog("Digite o nome da raça:");
	    String porte = JOptionPane.showInputDialog("Digite o porte da raça:");
	    String descricao = JOptionPane.showInputDialog("Digite a descrição da raça:");

	    String colunas = "nome, porte, descricao, fkEspecie";
	    String valores = "'" + nome + "','" + porte + "','" + descricao + "','" + fkEspecie + "'";      
        
        int idRaca = SQLGenerator.insertSQL(tabela, colunas, valores);
        if (idRaca > 0) {
        	JOptionPane.showMessageDialog(null, "Raça cadastrada com sucesso! Código da raça:  " + idRaca);
        	setNome(nome);                                                                                                       
        	setPorte(porte);
        	setDescricao(descricao);
        } else {
        	JOptionPane.showMessageDialog(null, "Ocorreu algum erro no cadastro.");
        }
	}

	@Override
	public void alterar() {
        ArrayList<String> colunasList = new ArrayList<>();
        ArrayList<String> valoresList = new ArrayList<>();

        int idCliente = Integer.parseInt(JOptionPane.showInputDialog("Digite o código da raça:"));
        String nome = JOptionPane.showInputDialog("Digite o nome da raça:");
        if (nome != null && !nome.equals("")) {
            colunasList.add("nome");
            valoresList.add(nome);
        }
        
        String porte = JOptionPane.showInputDialog("Digite o porte da raça:");
        if (porte != null && !porte.equals("")) {
            colunasList.add("porte");
            valoresList.add(porte);
        }

        String descricao = JOptionPane.showInputDialog("Digite a descrição da raça:");
        if (descricao != null && !descricao.equals("")) {
            colunasList.add("descricao");
            valoresList.add(descricao);
        }

        if (!colunasList.isEmpty() && !valoresList.isEmpty()) {
        	String[] colunas = colunasList.toArray(new String[0]);
        	String[] valores = valoresList.toArray(new String[0]);
        	
        	if (SQLGenerator.updateSQL(tabela, idCliente, colunas, valores)) {
        		JOptionPane.showMessageDialog(null, "Raça alterada com sucesso!");
            	setNome(nome);                                                                                                       
            	setPorte(porte);
            	setDescricao(descricao);
        	} else {
        		JOptionPane.showMessageDialog(null, "Ocorreu algum erro na alteração.");
        	}
        } else {
        	JOptionPane.showMessageDialog(null, "Não é possível deixar os dados em branco. Nada alterado.");
        }		
	}

	@Override
	public void excluir() {
    	int idRaca = Integer.parseInt(JOptionPane.showInputDialog("Digite o código da raça:"));
    	
    	if (SQLGenerator.deleteSQL(tabela, idRaca)) {
    		JOptionPane.showMessageDialog(null, "Raça excluído com sucesso!");
    	} else {
            JOptionPane.showMessageDialog(null, "Ocorreu algum erro na exclusão.");
        }
	}

	@Override
	public void listar() {
		Relatorio.mostrarDados(SQLGenerator.SelectSQL(null, tabela, null, null));
	}
	
	public String[][] consultarOptions() {
		String colunas = "idRaca, nome";
        String[][] resultado = SQLGenerator.SelectSQL(colunas, tabela, null, null);

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
                sb.append(resultado[i+1][j]);
            }
            resultadoFormatado[i][1] = sb.toString();
        }

        return resultadoFormatado;
    }
}
