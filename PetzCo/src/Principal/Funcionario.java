package Principal;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import Connection.SQLGenerator;
import Padrao.CRUD;
import Padrao.Formatos;
import Padrao.Item;
import Padrao.Log;
import Padrao.Relatorio;

public class Funcionario extends Contato implements CRUD {
	private final String tabela = "funcionario";
    private int idFuncionario;
    private LocalDate dataContratacao;
    
    public int getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public String getDataContratacao() {
        return dataContratacao.format(Formatos.DATE_INPUT_FORMATTER);
    }
    
    public void setDataContratacao(String dataContratacao) {
        try {
            this.dataContratacao = LocalDate.parse(dataContratacao, Formatos.DATE_INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
        	JOptionPane.showMessageDialog(null, "Data de contratação no formato incorreto. Use dd/MM/yyyy.");
            throw e;
        }
    }
    
    @Override
    public void cadastrar() {
	    CargoFuncionario cargoFuncionario = new CargoFuncionario();
	    String[][] options = cargoFuncionario.consultar();
	    
        if (options == null || options.length == 0) {
        	JOptionPane.showMessageDialog(null, "Necessário cadastrar cargos primeiro!");
        	return;
        }
        
	    String nome = JOptionPane.showInputDialog("Digite o nome:");
	    String cpf = JOptionPane.showInputDialog("Digite o CPF:");
	    String telefone = JOptionPane.showInputDialog("Digite a telefone:");
	    String email = JOptionPane.showInputDialog("Digite o e-mail:");
	    String dataContratacaoInput = JOptionPane.showInputDialog("Digite a data de contratação (dd/MM/yyyy):");
        
	    LocalDate dataContratacao;
	    try {
	    	dataContratacao = LocalDate.parse(dataContratacaoInput, Formatos.DATE_INPUT_FORMATTER);
	    } catch (DateTimeParseException e) {
	        JOptionPane.showMessageDialog(null, "Data de contratação no formato incorreto. Use dd/MM/yyyy.");
	        return;
	    }

	    String dataContratacaoFormatada = dataContratacao.format(Formatos.DATE_SQL_FORMATTER);
	    
	    JComboBox<Item> comboBox = new JComboBox<>();
	    for (String[] option : options) {
	        int idOption = Integer.parseInt(option[0]);
	        String nomeOption = option[1];
	        comboBox.addItem(new Item(idOption, nomeOption));
	    }

	    int selecao = JOptionPane.showConfirmDialog(null, comboBox, "Selecione o cargo do profissional: ", JOptionPane.OK_CANCEL_OPTION);

	    int fkCargo = 0;
	    if (selecao == JOptionPane.OK_OPTION) {
	    	Item opcao = (Item) comboBox.getSelectedItem();
	        if (opcao != null) {
	        	fkCargo = opcao.getId();
	            Log.geraLog("Cargo selecionado: " + opcao.getNome() + " (ID: " + fkCargo + ")");
	        } else {
	        	Log.geraLog("Nenhuma opção selecionada.");
	        }
	    } else {
	    	Log.geraLog("Nenhuma opção selecionada.");
	    }

	    Endereco endereco = new Endereco();
	    endereco.cadastrar();
	    int fkEndereco = endereco.getIdEndereco();
	    
	    String colunas = "nome, cpf, telefone, email, dataContratacao, fkEndereco, fkCargo";
	    String valores = "'" + nome + "','" + cpf + "','" + telefone + "','" + email + "','" 
	    					+ dataContratacaoFormatada + "', '" + fkEndereco + "', '" + fkCargo + "'";        
        
        int idFuncionario = SQLGenerator.insertSQL(tabela, colunas, valores);
        if (idFuncionario > 0) {
        	JOptionPane.showMessageDialog(null, "Funcionário cadastrado com sucesso! Código do funcionário:  " + idFuncionario);
        	setIdFuncionario(idFuncionario);
        	setNome(nome);
        	setCpf(cpf);
        	setTelefone(telefone);
        	setEmail(email);
        	setDataContratacao(dataContratacaoInput);
        } else {
        	JOptionPane.showMessageDialog(null, "Ocorreu algum erro no cadastro.");
        }
	}
    
    @Override
    public void alterar() {
        ArrayList<String> colunasList = new ArrayList<>();
        ArrayList<String> valoresList = new ArrayList<>();

        int idCliente = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do cliente:"));
        String nome = JOptionPane.showInputDialog("Digite o nome:");
        if (nome != null) {
            colunasList.add("nome");
            valoresList.add(nome);
        }

        String cpf = JOptionPane.showInputDialog("Digite o CPF:");
        if (cpf != null) {
            colunasList.add("cpf");
            valoresList.add(cpf);
        }

        String telefone = JOptionPane.showInputDialog("Digite o telefone:");
        if (telefone != null) {
            colunasList.add("telefone");
            valoresList.add(telefone);
        }

        String email = JOptionPane.showInputDialog("Digite o e-mail:");
        if (email != null) {
            colunasList.add("email");
            valoresList.add(email);
        }

        String dataNascimentoInput = JOptionPane.showInputDialog("Digite a data de nascimento (dd/MM/yyyy):");
        if (dataNascimentoInput != null) {
            LocalDate dataNascimento;

            try {
                colunasList.add("dataNascimento");
                dataNascimento = LocalDate.parse(dataNascimentoInput, Formatos.DATE_INPUT_FORMATTER);
                valoresList.add(dataNascimento.format(Formatos.DATE_SQL_FORMATTER));
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(null, "Data de nascimento no formato incorreto. Use dd/MM/yyyy.");
                return;
            }
        }
        
        Endereco endereco = new Endereco();
        endereco.alterar();

        String[] colunas = colunasList.toArray(new String[0]);
        String[] valores = valoresList.toArray(new String[0]);

        if (SQLGenerator.updateSQL(tabela, idCliente, colunas, valores)) {
            JOptionPane.showMessageDialog(null, "Funcionario alterado com sucesso!");
        	setNome(nome);
        	setCpf(cpf);
        	setTelefone(telefone);
        	setEmail(email);
        	setDataContratacao(dataNascimentoInput);
        } else {
            JOptionPane.showMessageDialog(null, "Ocorreu algum erro na alteração.");
        }
    }
    
    @Override
    public void excluir() {
    	int idCliente = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do funcionário:"));
    	
    	if (SQLGenerator.deleteSQL(tabela, idCliente)) {
    		JOptionPane.showMessageDialog(null, "Funcionário excluído com sucesso!");
    	} else {
            JOptionPane.showMessageDialog(null, "Ocorreu algum erro na exclusão.");
        }
    }
    
    public void listar() {
        Relatorio.mostrarDados(SQLGenerator.SelectSQL(null, tabela, null, null));
        
    }
    
    public void consultar() {
    	int idCliente = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do cliente:"));
    	
    	String colunas = "idFuncionario, nome, cpf, telefone, email, dataNascimento, idEndereco, "
    					+ "rua, numero, bairro, cidade, estado, complemento, cep";
    	String join = "INNER JOIN endereco ON (endereco.IdEndereco = cliente.fkEndereco)";
    	String where = "WHERE idCliente = " + idCliente;
    	
    	Relatorio.mostrarDados(SQLGenerator.SelectSQL(colunas, tabela, join, where));
    }
    
}

