package Principal;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import Connection.SQLGenerator;
import Padrao.CRUD;
import Padrao.Relatorio;

public class Especie implements CRUD {
	private final String tabela = "especie";
	private int idEspecie;
	private String nome;
	private String nomeCientifico;
	private String vidaMedia;
	private String descricao;
	
	public int getIdEspecie() {
		return idEspecie;
	}
	public void setIdEspecie(int idEspecie) {
		this.idEspecie = idEspecie;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNomeCientifico() {
		return nomeCientifico;
	}
	public void setNomeCientifico(String nomeCientifico) {
		this.nomeCientifico = nomeCientifico;
	}
	public String getVidaMedia() {
		return vidaMedia;
	}
	public void setVidaMedia(String vidaMedia) {
		this.vidaMedia = vidaMedia;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	@Override
	public void cadastrar() {
	    String nome = JOptionPane.showInputDialog("Digite o nome da espécie:");
	    if (nome == null || nome.equals("")) {
	    	JOptionPane.showMessageDialog(null, "Necessário incluir o nome da espécie.");
	    }
	    
	    String nomeCientifico = JOptionPane.showInputDialog("Digite o nome científico da espécie:");
	    String vidaMedia = JOptionPane.showInputDialog("Digite a vida média da espécie:");
	    String descricao = JOptionPane.showInputDialog("Digite a descrição da espécie:");

	    String colunas = "nome, nomeCientifico, vidaMedia, descricao";
	    String valores = "'" + nome + "','" + nomeCientifico + "','" + vidaMedia + "','" + descricao + "'";      
        
        int idEspecie = SQLGenerator.insertSQL(tabela, colunas, valores);
        if (idEspecie > 0) {
        	JOptionPane.showMessageDialog(null, "Espécie cadastrada com sucesso! Código da espécie:  " + idEspecie);
        	setIdEspecie(idEspecie);
        	setNome(nome);                                                                                                       
        	setNomeCientifico(nomeCientifico);
        	setVidaMedia(vidaMedia);
        	setDescricao(descricao);
        } else {
        	JOptionPane.showMessageDialog(null, "Ocorreu algum erro no cadastro.");
        }		
	}
	@Override
	public void alterar() {
        ArrayList<String> colunasList = new ArrayList<>();
        ArrayList<String> valoresList = new ArrayList<>();

        String idEspecieInput = JOptionPane.showInputDialog("Digite o código da espécie:");
	    if (idEspecieInput == null || idEspecieInput.equals("")) {
	    	JOptionPane.showMessageDialog(null, "O código da espécie não pode ser nulo.");
	    }
	    int idEspecie = Integer.parseInt(idEspecieInput);
        String nome = JOptionPane.showInputDialog("Digite o nome:");
        if (nome != null && !nome.equals("")) {
            colunasList.add("nome");
            valoresList.add(nome);
        }

        String nomeCientifico = JOptionPane.showInputDialog("Digite o nome científico:");
        if (nomeCientifico != null && !nomeCientifico.equals("")) {
            colunasList.add("nomeCientifico");
            valoresList.add(nomeCientifico);
        }
        
        String vidaMedia = JOptionPane.showInputDialog("Digite a vida média:");
        if (vidaMedia != null && !vidaMedia.equals("")) {
            colunasList.add("vidaMedia");
            valoresList.add(vidaMedia);
        }
        
        String descricao = JOptionPane.showInputDialog("Digite a descrição:");
        if (descricao != null && !descricao.equals("")) {
            colunasList.add("descricao");
            valoresList.add(descricao);
        }

        if (!colunasList.isEmpty() && !valoresList.isEmpty()) {
        	String[] colunas = colunasList.toArray(new String[0]);
        	String[] valores = valoresList.toArray(new String[0]);
        	
        	if (SQLGenerator.updateSQL(tabela, idEspecie, colunas, valores)) {
        		JOptionPane.showMessageDialog(null, "Espécie alterada com sucesso!");
        		if (colunasList.contains("nome")) {        			
        			setNome(nome);                                                                                                       
        		}
        		if (colunasList.contains("nomeCientifico")) {
        			setNomeCientifico(nomeCientifico);
        		}
            	if (colunasList.contains("vidaMedia")) {
            		setVidaMedia(vidaMedia);
            	}
            	if (colunasList.contains("descricao")) {
            		setDescricao(descricao);
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
    	int idEspecie = Integer.parseInt(JOptionPane.showInputDialog("Digite o código da espécie:"));
    	
    	if (SQLGenerator.deleteSQL(tabela, idEspecie)) {
    		JOptionPane.showMessageDialog(null, "Espécie excluída com sucesso!");
    	} else {
            JOptionPane.showMessageDialog(null, "Ocorreu algum erro na exclusão.");
    	}
        	
	}
	@Override
	public void listar() {
		Relatorio.mostrarDados(SQLGenerator.SelectSQL(null, tabela, null, null));		
	}
	
	public String[][] consultarOptions() {
		String colunas = "idEspecie, nome, nomeCientifico";
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
            	if (j > 1) {
                    sb.append(" - ");
                }
                sb.append(resultado[i+1][j]);
            }
            resultadoFormatado[i][1] = sb.toString();
        }

        return resultadoFormatado;
    }
	
}
