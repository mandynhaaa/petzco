package Principal;

import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import Connection.SQLGenerator;

public class Cliente extends Contato implements CRUD {
    private int idCliente;
    private LocalDate dataNascimento;
    
    private static final DateTimeFormatter DateInputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DateSQLFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private int getIdCliente() {
        return idCliente;
    }
    
    private void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
    private String getDataNascimento() {
        return dataNascimento.format(DateInputFormatter);
    }
    
    private void setDataNascimento(String dataNascimento) {
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

	    String tabela = "cliente";
	    String colunas = "nome, cpf, telefone, email, dataNascimento";
	    String valores = "'" + nome + "','" + cpf + "','" + telefone + "','" + email + "','" + dataNascimentoFormatada + "'";
	    
        Endereco endereco = new Endereco();
        endereco.cadastrar();
        
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
    	
    }
    
    @Override
    public void excluir() {
    }
}

