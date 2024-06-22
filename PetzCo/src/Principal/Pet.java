package Principal;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import Connection.SQLGenerator;
import Padrao.CRUD;
import Padrao.Item;
import Padrao.Log;
import Padrao.Relatorio;

public class Pet implements CRUD{
	private final String tabela = "pet";
	private int idPet;
	private String nome;
	private String idade;
	
	public int getIdPet() {
		return idPet;
	}
	public void setIdPet(int idPet) {
		this.idPet = idPet;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getIdade() {
		return idade;
	}
	public void setIdade(String idade) {
		this.idade = idade;
	}
	
	@Override
	public void cadastrar() {
		Raca raca = new Raca();
		String[][] optionsRaca = raca.consultarOptions();
		
		if (optionsRaca == null || optionsRaca.length == 0) {
			JOptionPane.showMessageDialog(null, "Necessário cadastrar raças primeiro!");
			return;
		}
		
		Cliente cliente = new Cliente();
		String[][] optionsCliente = cliente.consultarOptions();
		
		if (optionsCliente == null || optionsCliente.length == 0) {
			JOptionPane.showMessageDialog(null, "Necessário cadastrar clientes primeiro!");
			return;
		}
		
	    String nome = JOptionPane.showInputDialog("Digite o nome:");
	    if (nome == null || nome.equals("")) {
	    	JOptionPane.showMessageDialog(null, "Necessário inserir o nome do pet.");
	    }
	    String idade = JOptionPane.showInputDialog("Digite a idade:");
	    
	    JComboBox<Item> comboBoxRaca = new JComboBox<>();
	    for (String[] option : optionsRaca) {
	        int idOption = Integer.parseInt(option[0]);
	        String nomeOption = option[1];
	        comboBoxRaca.addItem(new Item(idOption, nomeOption));
	    }

	    int selecao = JOptionPane.showConfirmDialog(null, comboBoxRaca, "Selecione a raça: ", JOptionPane.OK_CANCEL_OPTION);

	    int fkRaca = 0;
	    if (selecao == JOptionPane.OK_OPTION) {
	    	Item opcao = (Item) comboBoxRaca.getSelectedItem();
	        if (opcao != null) {
	        	fkRaca = opcao.getId();
	            Log.geraLog("Raça selecionada: " + opcao.getNome() + " (ID: " + fkRaca + ")");
	        } else {
	        	Log.geraLog("Nenhuma opção selecionada.");
	        }
	    } else {
	    	Log.geraLog("Nenhuma opção selecionada.");
	    }
	    
	    JComboBox<Item> comboBoxCliente = new JComboBox<>();
	    for (String[] option : optionsCliente) {
	        int idOption = Integer.parseInt(option[0]);
	        String nomeOption = option[1];
	        comboBoxCliente.addItem(new Item(idOption, nomeOption));
	    }

	    selecao = JOptionPane.showConfirmDialog(null, comboBoxCliente, "Selecione o dono: ", JOptionPane.OK_CANCEL_OPTION);

	    int fkCliente = 0;
	    if (selecao == JOptionPane.OK_OPTION) {
	    	Item opcao = (Item) comboBoxCliente.getSelectedItem();
	        if (opcao != null) {
	        	fkCliente = opcao.getId();
	            Log.geraLog("Cliente selecionado: " + opcao.getNome() + " (ID: " + fkCliente + ")");
	        } else {
	        	Log.geraLog("Nenhuma opção selecionada.");
	        }
	    } else {
	    	Log.geraLog("Nenhuma opção selecionada.");
	    }
	    
	    
	    String colunas = "nome, idade, fkRaca, fkCliente";
	    String valores = "'" + nome + "','" + idade + "','" + fkRaca + "','" + fkCliente + "'";        
        
        int idPet = SQLGenerator.insertSQL(tabela, colunas, valores);
        if (idPet > 0) {
        	JOptionPane.showMessageDialog(null, "Pet cadastrado com sucesso! Código do pet:  " + idPet);
        	setIdPet(idPet);
        	setNome(nome);
        	setIdade(idade);
        } else {
        	JOptionPane.showMessageDialog(null, "Ocorreu algum erro no cadastro.");
        }
	}
	@Override
	public void alterar() {
		Raca raca = new Raca();
		String[][] optionsRaca = raca.consultarOptions();
		
		if (optionsRaca == null || optionsRaca.length == 0) {
			JOptionPane.showMessageDialog(null, "Necessário cadastrar raças primeiro!");
			return;
		}
		
		Cliente cliente = new Cliente();
		String[][] optionsCliente = cliente.consultarOptions();
		
		if (optionsCliente == null || optionsCliente.length == 0) {
			JOptionPane.showMessageDialog(null, "Necessário cadastrar clientes primeiro!");
			return;
		}
		
        ArrayList<String> colunasList = new ArrayList<>();
        ArrayList<String> valoresList = new ArrayList<>();
        
		String idPetInput = JOptionPane.showInputDialog("Digite o código do pet:");
	    if (idPetInput == null || idPetInput.equals("")) {
	    	JOptionPane.showMessageDialog(null, "O código do pet não pode ser nulo.");
	    	return;
	    }
		
		int idPet = Integer.parseInt(idPetInput);
		String nome = JOptionPane.showInputDialog("Digite o nome:");
        if (nome != null && !nome.equals("")) {
            colunasList.add("nome");
            valoresList.add(nome);
        }

        String idade = JOptionPane.showInputDialog("Digite a idade:");
        if (idade != null && !idade.equals("")) {
            colunasList.add("idade");
            valoresList.add(idade);
        }
	    
	    JComboBox<Item> comboBoxRaca = new JComboBox<>();
	    for (String[] option : optionsRaca) {
	        int idOption = Integer.parseInt(option[0]);
	        String nomeOption = option[1];
	        comboBoxRaca.addItem(new Item(idOption, nomeOption));
	    }

	    int selecao = JOptionPane.showConfirmDialog(null, comboBoxRaca, "Selecione a raça: ", JOptionPane.OK_CANCEL_OPTION);

	    int fkRaca = 0;
	    if (selecao == JOptionPane.OK_OPTION) {
	    	Item opcao = (Item) comboBoxRaca.getSelectedItem();
	        if (opcao != null) {
	        	fkRaca = opcao.getId();
	            colunasList.add("fkRaca");
	            valoresList.add(Integer.toString(fkRaca));
	            Log.geraLog("Raça selecionada: " + opcao.getNome() + " (ID: " + fkRaca + ")");
	        } else {
	        	Log.geraLog("Nenhuma opção selecionada.");
	        }
	    } else {
	    	Log.geraLog("Nenhuma opção selecionada.");
	    }
	    
	    JComboBox<Item> comboBoxCliente = new JComboBox<>();
	    for (String[] option : optionsCliente) {
	        int idOption = Integer.parseInt(option[0]);
	        String nomeOption = option[1];
	        comboBoxCliente.addItem(new Item(idOption, nomeOption));
	    }

	    selecao = JOptionPane.showConfirmDialog(null, comboBoxCliente, "Selecione o dono: ", JOptionPane.OK_CANCEL_OPTION);

	    int fkCliente = 0;
	    if (selecao == JOptionPane.OK_OPTION) {
	    	Item opcao = (Item) comboBoxCliente.getSelectedItem();
	        if (opcao != null) {
	        	fkCliente = opcao.getId();
	            colunasList.add("fkCliente");
	            valoresList.add(Integer.toString(fkCliente));
	            Log.geraLog("Cliente selecionado: " + opcao.getNome() + " (ID: " + fkCliente + ")");
	        } else {
	        	Log.geraLog("Nenhuma opção selecionada.");
	        }
	    } else {
	    	Log.geraLog("Nenhuma opção selecionada.");
	    }    
        
        if (!colunasList.isEmpty() && !valoresList.isEmpty()) {
        	String[] colunas = colunasList.toArray(new String[0]);
        	String[] valores = valoresList.toArray(new String[0]);
        	
        	if (SQLGenerator.updateSQL(tabela, idPet, colunas, valores)) {
        		JOptionPane.showMessageDialog(null, "Pet alterado com sucesso!");
            	setNome(nome);
            	setIdade(idade);
        	} else {
        		JOptionPane.showMessageDialog(null, "Ocorreu algum erro na alteração.");
        	}
        } else {
        	JOptionPane.showMessageDialog(null, "Não é possível deixar os dados em branco. Nada alterado.");
        }
	}
	@Override
	public void excluir() {
		String idPetInput = JOptionPane.showInputDialog("Digite o código do pet:");
	    if (idPetInput == null || idPetInput.equals("")) {
	    	JOptionPane.showMessageDialog(null, "O código do pet não pode ser nulo.");
	    	return;
	    }
		
		int idPet = Integer.parseInt(idPetInput);
		
		Agendamento agendamento = new Agendamento();
		if (agendamento.consultarAgendamentoPet(idPet) > 0) {				
			if (SQLGenerator.deleteSQL(tabela, idPet)) {
				JOptionPane.showMessageDialog(null, "Pet excluído com sucesso!");
			} else {
				JOptionPane.showMessageDialog(null, "Ocorreu algum erro na exclusão.");
			}
		}
	}
	@Override
	public void listar() {
        Relatorio.mostrarDados(SQLGenerator.SelectSQL(null, tabela, null, null));
	}
	
	public String[][] consultarOptions() {
		String colunas = "idPet, pet.nome, cliente.nome dono";
		String join = "INNER JOIN cliente on (cliente.idCliente = pet.fkCliente)";
	    String[][] resultado = SQLGenerator.SelectSQL(colunas, tabela, join, null);
	
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
                    sb.append(" - Tutor: ");
                }
                sb.append(resultado[i+1][j]);
	        }
	        resultadoFormatado[i][1] = sb.toString();
	    }
	
	    return resultadoFormatado;
	}
}
