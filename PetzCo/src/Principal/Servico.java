package Principal;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import java.sql.Time;
import java.util.ArrayList;

import Connection.SQLGenerator;
import Padrao.CRUD;
import Padrao.Item;
import Padrao.Log;
import Padrao.Relatorio;

public class Servico implements CRUD {
    private final String tabela = "servico";
    private int idServico;
    private boolean isDisponivel;
    private Time tempoEstimado;
    private String descricao;
    private float valor;

    public int getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    public boolean isDisponivel() {
        return isDisponivel;
    }

    public void setDisponivel(boolean disponivel) {
        isDisponivel = disponivel;
    }

    public Time getTempoEstimado() {
        return tempoEstimado;
    }

    public void setTempoEstimado(Time tempoEstimado) {
        this.tempoEstimado = tempoEstimado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    @Override
    public void cadastrar() {
	    CargoFuncionario cargoFuncionario = new CargoFuncionario();
	    String[][] options = cargoFuncionario.consultarOptions();
	    
        if (options == null || options.length == 0) {
        	JOptionPane.showMessageDialog(null, "Necessário cadastrar cargos primeiro!");
        	return;
        }
        
        String descricao = JOptionPane.showInputDialog("Digite a descrição:");
        if (descricao == null || descricao.equals("")) {
	    	JOptionPane.showMessageDialog(null, "Necessário inserir a descrição do serviço.");
	    	return;
	    }
        boolean isDisponivelInput = JOptionPane.showConfirmDialog(null, "Está disponível?") == JOptionPane.YES_OPTION;
        int isDisponivel = 1;
        if (!isDisponivelInput) {
        	isDisponivel = 0;
        }
        String tempoEstimadoInput = JOptionPane.showInputDialog("Digite o tempo estimado (HH:MM:SS):");
        Time tempoEstimado;
        try {
            tempoEstimado = Time.valueOf(tempoEstimadoInput);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Tempo estimado no formato incorreto. Use HH:MM:SS.");
            return;
        }
        float valor = Float.parseFloat(JOptionPane.showInputDialog("Digite o valor:"));
        
	    JComboBox<Item> comboBox = new JComboBox<>();
	    for (String[] option : options) {
	        int idOption = Integer.parseInt(option[0]);
	        String nomeOption = option[1];
	        comboBox.addItem(new Item(idOption, nomeOption));
	    }

	    int selecao = JOptionPane.showConfirmDialog(null, comboBox, "Selecione o cargo permitido: ", JOptionPane.OK_CANCEL_OPTION);

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
	    
        String colunas = "isDisponivel, tempoEstimado, descricao, valor, fkCargoFuncionario";
        String valores = "'" + isDisponivel + "','" + tempoEstimado + "','" + descricao + "','" + valor + "','" + fkCargo + "'";

        int idServico = SQLGenerator.insertSQL(tabela, colunas, valores);
        if (idServico > 0) {
            JOptionPane.showMessageDialog(null, "Serviço cadastrado com sucesso! Código do serviço: " + idServico);
            setIdServico(idServico);
            setDescricao(descricao);
            setDisponivel(isDisponivelInput);
            setTempoEstimado(tempoEstimado);
            setValor(valor);
        } else {
            JOptionPane.showMessageDialog(null, "Ocorreu algum erro no cadastro.");
        }
    }

    @Override
    public void alterar() {
	    CargoFuncionario cargoFuncionario = new CargoFuncionario();
	    String[][] options = cargoFuncionario.consultarOptions();
	    
        if (options == null || options.length == 0) {
        	JOptionPane.showMessageDialog(null, "Necessário cadastrar cargos primeiro!");
        	return;
        }
        
        ArrayList<String> colunasList = new ArrayList<>();
        ArrayList<String> valoresList = new ArrayList<>();

        String idServicoIput = JOptionPane.showInputDialog("Digite o código do serviço:");
	    if (idServicoIput == null || idServicoIput.equals("")) {
	    	JOptionPane.showMessageDialog(null, "O código do serviço não pode ser nulo.");
	    	return;
	    }
	    int idServico = Integer.parseInt(idServicoIput);

        String descricao = JOptionPane.showInputDialog("Digite a descrição:");
        if (descricao != null && !descricao.equals("")) {
            colunasList.add("descricao");
            valoresList.add(descricao);
        }

        boolean isDisponivelInput = JOptionPane.showConfirmDialog(null, "Está disponível?") == JOptionPane.YES_OPTION;
        int isDisponivel = 1;
        if (!isDisponivelInput) {
        	isDisponivel = 0;
        }
        colunasList.add("isDisponivel");
        valoresList.add(Integer.toString(isDisponivel));

        String tempoEstimadoInput = JOptionPane.showInputDialog("Digite o tempo estimado (HH:MM:SS):");
        if (tempoEstimadoInput != null && !tempoEstimadoInput.equals("")) {
            Time tempoEstimado;
            try {
                colunasList.add("tempoEstimado");
                tempoEstimado = Time.valueOf(tempoEstimadoInput);
                valoresList.add(tempoEstimado.toString());
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "Tempo estimado no formato incorreto. Use HH:MM:SS.");
                return;
            }
        }

        String valorInput = JOptionPane.showInputDialog("Digite o valor:");
        if (valorInput != null && !valorInput.equals("")) {
	        float valor = Float.parseFloat(valorInput);
	        if (valor > 0) {
	            colunasList.add("valor");
	            valoresList.add(Float.toString(valor));
	        }
        }

	    JComboBox<Item> comboBox = new JComboBox<>();
	    for (String[] option : options) {
	        int idOption = Integer.parseInt(option[0]);
	        String nomeOption = option[1];
	        comboBox.addItem(new Item(idOption, nomeOption));
	    }

	    int selecao = JOptionPane.showConfirmDialog(null, comboBox, "Selecione o cargo permitido: ", JOptionPane.OK_CANCEL_OPTION);

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

        if (!colunasList.isEmpty() && !valoresList.isEmpty()) {
            String[] colunas = colunasList.toArray(new String[0]);
            String[] valores = valoresList.toArray(new String[0]);

            if (SQLGenerator.updateSQL(tabela, idServico, colunas, valores)) {
                JOptionPane.showMessageDialog(null, "Serviço alterado com sucesso!");
                if (colunasList.contains("descricao")) {                	
                	setDescricao(descricao);
                }
                if (colunasList.contains("isDisponivel")) {                	
                	setDisponivel(isDisponivelInput);
                }
                if (colunasList.contains("tempoEstimado")) {                	
                	setTempoEstimado(Time.valueOf(tempoEstimadoInput));
                }
                if (colunasList.contains("valor")) {                	
                	setValor(valor);
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
    	String idServicoIput = JOptionPane.showInputDialog("Digite o código do serviço:");
	    if (idServicoIput == null || idServicoIput.equals("")) {
	    	JOptionPane.showMessageDialog(null, "O código do serviço não pode ser nulo.");
	    	return;
	    }
	    int idServico = Integer.parseInt(idServicoIput);

        if (SQLGenerator.deleteSQL(tabela, idServico)) {
            JOptionPane.showMessageDialog(null, "Serviço excluído com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Ocorreu algum erro na exclusão.");
        }
    }

    public void listar() {
        Relatorio.mostrarDados(SQLGenerator.SelectSQL(null, tabela, null, null));
    }

    public void consultar() {
    	String idServicoIput = JOptionPane.showInputDialog("Digite o código do serviço:");
	    if (idServicoIput == null || idServicoIput.equals("")) {
	    	JOptionPane.showMessageDialog(null, "O código do serviço não pode ser nulo.");
	    	return;
	    }
	    int idServico = Integer.parseInt(idServicoIput);
        String where = "WHERE idServico = " + idServico;

        Relatorio.mostrarDados(SQLGenerator.SelectSQL(null, tabela, null, where));
    }
    
	public String[][] consultarOptions() {
		String colunas = "idServico, descricao, valor";
		String where = "WHERE isDisponivel IS NOT NULL AND isDisponivel != '0'";
	    String[][] resultado = SQLGenerator.SelectSQL(colunas, tabela, null, where);
	
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
                    sb.append(" - R$ ");
                }
                sb.append(resultado[i+1][j]);
	        }
	        resultadoFormatado[i][1] = sb.toString();
	    }
	
	    return resultadoFormatado;
	}
}