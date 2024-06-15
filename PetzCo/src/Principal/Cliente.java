package Principal;

import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import Connection.SQLGenerator;

public class Cliente extends Contato implements CRUD {
    private int idCliente;
    private LocalDate dataNascimento;
    
    private static final DateTimeFormatter DateInputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DateSQLFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public int getIdCliente() {
        return idCliente;
    }
    
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
    public String getDataNascimento() {
        return dataNascimento.format(DateInputFormatter);
    }
    
    public void setDataNascimento(String dataNascimento) {
        try {
            this.dataNascimento = LocalDate.parse(dataNascimento, DateInputFormatter);
        } catch (DateTimeParseException e) {
        	JOptionPane.showMessageDialog(null, "Data de nascimento no formato incorreto. Use dd/MM/yyyy.");
            throw e;
        }
    }
    
    @Override
    public void cadastrar() {
	    String nome = JOptionPane.showInputDialog("Digite o nome:");
	    String cpf = JOptionPane.showInputDialog("Digite o CPF:");
	    String telefone = JOptionPane.showInputDialog("Digite a telefone:");
	    String email = JOptionPane.showInputDialog("Digite o e-mail:");
	    String dataNascimentoInput = JOptionPane.showInputDialog("Digite a data de nascimento (dd/MM/yyyy):");

	    LocalDate dataNascimento;
	    try {
	        dataNascimento = LocalDate.parse(dataNascimentoInput, DateInputFormatter);
	    } catch (DateTimeParseException e) {
	        JOptionPane.showMessageDialog(null, "Data de nascimento no formato incorreto. Use dd/MM/yyyy.");
	        return;
	    }

	    String dataNascimentoFormatada = dataNascimento.format(DateSQLFormatter);

	    Endereco endereco = new Endereco();
	    endereco.cadastrar();
	    fkEndereco = endereco.getIdEndereco();
	    
	    String tabela = "cliente";
	    String colunas = "nome, cpf, telefone, email, dataNascimento, fkEndereco";
	    String valores = "'" + nome + "','" + cpf + "','" + telefone + "','" + email + "','" 
	    					+ dataNascimentoFormatada + "', '" + fkEndereco + "'";        
        
        int idCliente = SQLGenerator.insertSQL(tabela, colunas, valores);
        if (idCliente > 0) {
        	JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso! Código do cliente:  " + idCliente);
        	setIdCliente(idCliente);
        	setNome(nome);
        	setCpf(cpf);
        	setTelefone(telefone);
        	setEmail(email);
        	setDataNascimento(dataNascimentoInput);
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
                dataNascimento = LocalDate.parse(dataNascimentoInput, DateInputFormatter);
                valoresList.add(dataNascimento.format(DateSQLFormatter));
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(null, "Data de nascimento no formato incorreto. Use dd/MM/yyyy.");
                return;
            }
        }

        String tabela = "cliente";
        
        Endereco endereco = new Endereco();
        endereco.alterar();

        String[] colunas = colunasList.toArray(new String[0]);
        String[] valores = valoresList.toArray(new String[0]);

        if (SQLGenerator.updateSQL(tabela, idCliente, colunas, valores)) {
            JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso!");
        	setNome(nome);
        	setCpf(cpf);
        	setTelefone(telefone);
        	setEmail(email);
        	setDataNascimento(dataNascimentoInput);
        } else {
            JOptionPane.showMessageDialog(null, "Ocorreu algum erro na alteração.");
        }
    }
    
    @Override
    public void excluir() {
    	int idCliente = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do cliente:"));
    	
    	String tabela = "cliente";
    	
    	if (SQLGenerator.deleteSQL(tabela, idCliente)) {
    		JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso!");
    	} else {
            JOptionPane.showMessageDialog(null, "Ocorreu algum erro na exclusão.");
        }
    }
    
    public void listar() {
        String tabela = "cliente";

        Relatorio.mostrarDados(SQLGenerator.SelectSQL(null, tabela, null, null));
        
    }
    
    public void consultar() {
    	int idCliente = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do cliente:"));
    	
    	String tabela = "cliente";
    	String colunas = "idCliente, nome, cpf, telefone, email, dataNascimento, idEndereco, "
    					+ "rua, numero, bairro, cidade, estado, complemento, cep";
    	String join = "INNER JOIN endereco ON (endereco.IdEndereco = cliente.fkEndereco)";
    	String where = "WHERE idCliente = " + idCliente;
    	
    	Relatorio.mostrarDados(SQLGenerator.SelectSQL(colunas, tabela, join, where));
    }
}

