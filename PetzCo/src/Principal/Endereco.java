package Principal;

import java.util.ArrayList;
import javax.swing.JOptionPane;

import Connection.SQLGenerator;

public class Endereco implements CRUD {
	private int idEndereco;
	private String pais;
	private String estado;
	private String cidade;
	private String bairro;
	private String rua;
	private int numero;
	private String complemento;
	private String cep;	
	
	public int getIdEndereco() {
		return idEndereco;
	}
	public void setIdEndereco(int idEndereco) {
		this.idEndereco = idEndereco;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}

	@Override
	public void cadastrar() {
	    String pais = JOptionPane.showInputDialog("Digite o pais:");
	    String estado = JOptionPane.showInputDialog("Digite o estado:");
	    String cidade = JOptionPane.showInputDialog("Digite a cidade:");
	    String bairro = JOptionPane.showInputDialog("Digite o bairro:");
	    String rua = JOptionPane.showInputDialog("Digite a rua:");
	    int numero = Integer.parseInt(JOptionPane.showInputDialog("Digite o numero:"));
	    String complemento = JOptionPane.showInputDialog("Digite o complemento:");
	    String cep = JOptionPane.showInputDialog("Digite o CEP:");

	    String tabela = "endereco";
	    String colunas = "rua, numero, bairro, cidade, estado, complemento, cep";
	    String valores = "'" + rua + "'," + numero + ",'" + bairro + "','" + cidade
	                    + "','" + estado + "','" + complemento + "','" + cep  + "'";
	    
	    int idEndereco = SQLGenerator.insertSQL(tabela, colunas, valores);
        if (idEndereco > 0) {
        	JOptionPane.showMessageDialog(null, "Endereço cadastrado com sucesso!");
        	setIdEndereco(idEndereco);
        	setPais(pais);
        	setEstado(estado);
        	setCidade(cidade);
        	setBairro(bairro);
        	setRua(rua);
        	setNumero(numero);
        	setComplemento(complemento);
        	setCep(cep);
        } else {
        	JOptionPane.showMessageDialog(null, "Ocorreu algum erro no cadastro.");
        }
	}

	@Override
	public void alterar() {
		ArrayList<String> colunasList = new ArrayList<>();
        ArrayList<String> valoresList = new ArrayList<>();

        int idEndereco = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do endereço:"));
        String pais = JOptionPane.showInputDialog("Digite o pais:");
        if (pais != null) {
            colunasList.add("pais");
            valoresList.add(pais);
        }

        String estado = JOptionPane.showInputDialog("Digite o estado:");
        if (estado != null) {
            colunasList.add("estado");
            valoresList.add(estado);
        }

        String cidade = JOptionPane.showInputDialog("Digite a cidade:");
        if (cidade != null) {
            colunasList.add("cidade");
            valoresList.add(cidade);
        }
        
        String bairro = JOptionPane.showInputDialog("Digite o bairro:");
        if (bairro != null) {
            colunasList.add("bairro");
            valoresList.add(bairro);
        }

        String rua = JOptionPane.showInputDialog("Digite a rua:");
        if (rua != null) {
            colunasList.add("rua");
            valoresList.add(rua);
        }
        
        String numeroInput = JOptionPane.showInputDialog("Digite o numero:");
        int numero = Integer.parseInt(numeroInput);
        if (numero != 0) {
            colunasList.add("numero");
            valoresList.add(numeroInput);
        }
        
        String complemento = JOptionPane.showInputDialog("Digite o complemento:");
        if (complemento != null) {
            colunasList.add("complemento");
            valoresList.add(complemento);
        }

        String cep = JOptionPane.showInputDialog("Digite o CEP:");
        if (cep != null) {
            colunasList.add("cep");
            valoresList.add(cep);
        }

        String tabela = "endereco";
        String[] colunas = colunasList.toArray(new String[0]);
        String[] valores = valoresList.toArray(new String[0]);

        if (SQLGenerator.updateSQL(tabela, idEndereco, colunas, valores)) {
            JOptionPane.showMessageDialog(null, "Endereço alterado com sucesso!");
        	setPais(pais);
        	setEstado(estado);
        	setCidade(cidade);
        	setBairro(bairro);
        	setRua(rua);
        	setNumero(numero);
        	setComplemento(complemento);
        	setCep(cep);
        } else {
            JOptionPane.showMessageDialog(null, "Ocorreu algum erro na alteração.");
        }
		
	}

	@Override
	public void excluir() {
    	int idEndereco = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do endereço:"));
    	
    	String tabela = "endereco";
    	
    	if (SQLGenerator.deleteSQL(tabela, idEndereco)) {
    		JOptionPane.showMessageDialog(null, "Endereco excluído com sucesso!");
    	} else {
            JOptionPane.showMessageDialog(null, "Ocorreu algum erro na exclusão.");
        }
	}

	public void listar() {
	    String tabela = "endereco";

	    Relatorio.mostrarDados(SQLGenerator.SelectSQL(null, tabela, null, null));
	}
	
    public void consultar() {
    	int idEndereco = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do endereço:"));
    	
    	String tabela = "endereco";
    	String colunas = "idEndereco, pais, estado, cidade, rua, numero, complemento, cep";
    	String where = "WHERE idEndereco = " + idEndereco;
    	
    	Relatorio.mostrarDados(SQLGenerator.SelectSQL(colunas, tabela, null, where));
    }

}

