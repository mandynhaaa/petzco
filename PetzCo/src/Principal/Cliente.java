package Principal;

import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import Connection.SQLGenerator;
import Padrao.CRUD;
import Padrao.Formatos;
import Padrao.Relatorio;

public class Cliente extends Contato implements CRUD {
	private final String tabela = "cliente";
    private int idCliente;
    private LocalDate dataNascimento;
    
    public int getIdCliente() {
        return idCliente;
    }
    
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
    public String getDataNascimento() {
        return dataNascimento.format(Formatos.DATE_INPUT_FORMATTER);
    }
    
    public void setDataNascimento(String dataNascimento) {
        try {
            this.dataNascimento = LocalDate.parse(dataNascimento, Formatos.DATE_INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
        	JOptionPane.showMessageDialog(null, "Data de nascimento no formato incorreto. Use dd/MM/yyyy.");
            throw e;
        }
    }
    
    @Override
    public void cadastrar() {
    	String nome = JOptionPane.showInputDialog("Digite o nome:");
	    if (nome == null || nome.equals("")) {
	    	JOptionPane.showMessageDialog(null, "Necessário inserir o nome do funcionário.");
	    }
	    String cpf = JOptionPane.showInputDialog("Digite o CPF:");
	    if (cpf == null || cpf.equals("")) {
	    	JOptionPane.showMessageDialog(null, "O CPF não pode ser nulo.");
	    }
	    String telefone = JOptionPane.showInputDialog("Digite a telefone:");
	    String email = JOptionPane.showInputDialog("Digite o e-mail:");
	    String dataNascimentoInput = JOptionPane.showInputDialog("Digite a data de nascimento (dd/MM/yyyy):");

	    LocalDate dataNascimento;
	    try {
	        dataNascimento = LocalDate.parse(dataNascimentoInput, Formatos.DATE_INPUT_FORMATTER);
	    } catch (DateTimeParseException e) {
	        JOptionPane.showMessageDialog(null, "Data de nascimento no formato incorreto. Use dd/MM/yyyy.");
	        return;
	    }

	    String dataNascimentoFormatada = dataNascimento.format(Formatos.DATE_SQL_FORMATTER);

	    Endereco endereco = new Endereco();
	    endereco.cadastrar();
	    int fkEndereco = endereco.getIdEndereco();

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
        if (nome != null && !nome.equals("")) {
            colunasList.add("nome");
            valoresList.add(nome);
        }

        String cpf = JOptionPane.showInputDialog("Digite o CPF:");
        if (cpf != null && !cpf.equals("")) {
            colunasList.add("cpf");
            valoresList.add(cpf);
        }

        String telefone = JOptionPane.showInputDialog("Digite o telefone:");
        if (telefone != null && !telefone.equals("")) {
            colunasList.add("telefone");
            valoresList.add(telefone);
        }

        String email = JOptionPane.showInputDialog("Digite o e-mail:");
        if (email != null && !email.equals("")) {
            colunasList.add("email");
            valoresList.add(email);
        }

        String dataNascimentoInput = JOptionPane.showInputDialog("Digite a data de nascimento (dd/MM/yyyy):");
        if (dataNascimentoInput != null && !dataNascimentoInput.equals("")) {
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

        if (!colunasList.isEmpty() && !valoresList.isEmpty()) {
	        String[] colunas = colunasList.toArray(new String[0]);
	        String[] valores = valoresList.toArray(new String[0]);
	
	        if (SQLGenerator.updateSQL(tabela, idCliente, colunas, valores)) {
	            JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso!");
	            if (colunasList.contains("nome")) {	
	            	setNome(nome);
	            }
	            
	            if (colunasList.contains("cpf")) {
	            	setCpf(cpf);
	            }
	            
	            if (colunasList.contains("telefone")) {
	            	setTelefone(telefone);
	            }
	            
	            if (colunasList.contains("email")) {
	            	setEmail(email);
	            }
	            
	            if (colunasList.contains("dataNascimento")) {
	            	setDataNascimento(dataNascimentoInput);
	            }
	        } else {
	            JOptionPane.showMessageDialog(null, "Ocorreu algum erro na alteração.");
	        }
        } else {
        	JOptionPane.showMessageDialog(null, "Não é possível deixar os dados em branco. Nada alterado.");
        }
    }
    
    @Override
    public void excluir() {
    	int idCliente = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do cliente:"));
    	
    	if (SQLGenerator.deleteSQL(tabela, idCliente)) {
    		JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso!");
    	} else {
            JOptionPane.showMessageDialog(null, "Ocorreu algum erro na exclusão.");
        }
    }
    
    public void listar() {
        Relatorio.mostrarDados(SQLGenerator.SelectSQL(null, tabela, null, null));
    }
    
    public void consultar() {
    	int idCliente = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do cliente:"));

    	String colunas = "idCliente, nome, cpf, telefone, email, dataNascimento, idEndereco, "
    					+ "rua, numero, bairro, cidade, estado, complemento, cep";
    	String join = "INNER JOIN endereco ON (endereco.IdEndereco = cliente.fkEndereco)";
    	String where = "WHERE idCliente = " + idCliente;
    	
    	Relatorio.mostrarDados(SQLGenerator.SelectSQL(colunas, tabela, join, where));
    }
    
    public String[][] consultarOptions() {
    	String colunas = "idCliente, nome";
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

