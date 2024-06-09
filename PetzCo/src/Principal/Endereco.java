package Principal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import Connection.Conexao;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluir() {
		// TODO Auto-generated method stub
		
	}

	private void create(Endereco endereco) {
		
	}
}

