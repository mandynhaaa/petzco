package Principal;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import Connection.SQLGenerator;
import Padrao.CRUD;
import Padrao.Relatorio;
import Padrao.Formatos;
import Padrao.Item;
import Padrao.Log;

public class Agendamento implements CRUD {
    private final String tabela = "agendamento";
    private int idAgendamento;
    private LocalDate dataAgendamento;
    private String observacao;

    public int getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(int idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public String getDataAgendamento() {
        return dataAgendamento.format(Formatos.DATE_INPUT_FORMATTER);
    }

    public void setDataAgendamento(String dataAgendamento) {
        try {
            this.dataAgendamento = LocalDate.parse(dataAgendamento, Formatos.DATE_INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Data de agendamento no formato incorreto. Use dd/MM/yyyy.");
            throw e;
        }
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public void cadastrar() {
		Funcionario funcionario = new Funcionario();
		String[][] optionsFuncionario = funcionario.consultarOptions();
		
		if (optionsFuncionario == null || optionsFuncionario.length == 0) {
			JOptionPane.showMessageDialog(null, "Necessário cadastrar funcionários primeiro!");
			return;
		}
		
		Servico servico = new Servico();
		String[][] optionsServico = servico.consultarOptions();
		
		if (optionsServico == null || optionsServico.length == 0) {
			JOptionPane.showMessageDialog(null, "Necessário cadastrar serviços primeiro!");
			return;
		}
		
		Pet pet = new Pet();
		String[][] optionsPet = pet.consultarOptions();
		
		if (optionsPet == null || optionsPet.length == 0) {
			JOptionPane.showMessageDialog(null, "Necessário cadastrar pets primeiro!");
			return;
		}
		
        String dataAgendamentoInput = JOptionPane.showInputDialog("Digite a data do agendamento (dd/MM/yyyy):");
        if (dataAgendamentoInput == null || dataAgendamentoInput.equals("")) {
	    	JOptionPane.showMessageDialog(null, "Necessário inserir a data do agendamento.");
	    	return;
	    }
        
        LocalDate dataAgendamento;
        try {
            dataAgendamento = LocalDate.parse(dataAgendamentoInput, Formatos.DATE_INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Data de agendamento no formato incorreto. Use dd/MM/yyyy.");
            return;
        }

        String dataAgendamentoFormatada = dataAgendamento.format(Formatos.DATE_SQL_FORMATTER);

        String observacao = JOptionPane.showInputDialog("Digite a observação:");
        
	    JComboBox<Item> comboBoxFuncionario = new JComboBox<>();
	    for (String[] option : optionsFuncionario) {
	        int idOption = Integer.parseInt(option[0]);
	        String nomeOption = option[1];
	        comboBoxFuncionario.addItem(new Item(idOption, nomeOption));
	    }

	    int selecao = JOptionPane.showConfirmDialog(null, comboBoxFuncionario, "Selecione o funcionário: ", JOptionPane.OK_CANCEL_OPTION);

	    int fkFuncionario = 0;
	    if (selecao == JOptionPane.OK_OPTION) {
	    	Item opcao = (Item) comboBoxFuncionario.getSelectedItem();
	        if (opcao != null) {
	        	fkFuncionario = opcao.getId();
	            Log.geraLog("Funcionário selecionado: " + opcao.getNome() + " (ID: " + fkFuncionario + ")");
	        } else {
	        	Log.geraLog("Nenhuma opção selecionada.");
	        }
	    } else {
	    	Log.geraLog("Nenhuma opção selecionada.");
	    }
	    
	    JComboBox<Item> comboBoxPet = new JComboBox<>();
	    for (String[] option : optionsPet) {
	        int idOption = Integer.parseInt(option[0]);
	        String nomeOption = option[1];
	        comboBoxPet.addItem(new Item(idOption, nomeOption));
	    }

	    selecao = JOptionPane.showConfirmDialog(null, comboBoxPet, "Selecione o pet: ", JOptionPane.OK_CANCEL_OPTION);

	    int fkPet = 0;
	    if (selecao == JOptionPane.OK_OPTION) {
	    	Item opcao = (Item) comboBoxPet.getSelectedItem();
	        if (opcao != null) {
	        	fkPet = opcao.getId();
	            Log.geraLog("Pet selecionado: " + opcao.getNome() + " (ID: " + fkPet + ")");
	        } else {
	        	Log.geraLog("Nenhuma opção selecionada.");
	        }
	    } else {
	    	Log.geraLog("Nenhuma opção selecionada.");
	    }
	    
	    JComboBox<Item> comboBoxServico = new JComboBox<>();
	    for (String[] option : optionsServico) {
	        int idOption = Integer.parseInt(option[0]);
	        String nomeOption = option[1];
	        comboBoxServico.addItem(new Item(idOption, nomeOption));
	    }

	    selecao = JOptionPane.showConfirmDialog(null, comboBoxServico, "Selecione o serviço: ", JOptionPane.OK_CANCEL_OPTION);

	    int fkServico = 0;
	    if (selecao == JOptionPane.OK_OPTION) {
	    	Item opcao = (Item) comboBoxServico.getSelectedItem();
	        if (opcao != null) {
	        	fkServico = opcao.getId();
	            Log.geraLog("Serviço selecionado: " + opcao.getNome() + " (ID: " + fkServico + ")");
	        } else {
	        	Log.geraLog("Nenhuma opção selecionada.");
	        }
	    } else {
	    	Log.geraLog("Nenhuma opção selecionada.");
	    }
	    
        String colunas = "dataAgendamento, observacao, fkFuncionario, fkServico, fkPet";
        String valores = "'" + dataAgendamentoFormatada + "','" + observacao + "','" + fkFuncionario + "','" + fkServico + "','" + fkPet + "'";

        int idAgendamento = SQLGenerator.insertSQL(tabela, colunas, valores);
        if (idAgendamento > 0) {
            JOptionPane.showMessageDialog(null, "Agendamento cadastrado com sucesso! Código do agendamento: " + idAgendamento);
            setIdAgendamento(idAgendamento);
            setDataAgendamento(dataAgendamentoInput);
            setObservacao(observacao);
        } else {
            JOptionPane.showMessageDialog(null, "Ocorreu algum erro no cadastro.");
        }
    }

    @Override
    public void alterar() {
    	Funcionario funcionario = new Funcionario();
		String[][] optionsFuncionario = funcionario.consultarOptions();
		
		if (optionsFuncionario == null || optionsFuncionario.length == 0) {
			JOptionPane.showMessageDialog(null, "Necessário cadastrar funcionários primeiro!");
			return;
		}
		
		Servico servico = new Servico();
		String[][] optionsServico = servico.consultarOptions();
		
		if (optionsServico == null || optionsServico.length == 0) {
			JOptionPane.showMessageDialog(null, "Necessário cadastrar serviços primeiro!");
			return;
		}
		
		Pet pet = new Pet();
		String[][] optionsPet = pet.consultarOptions();
		
		if (optionsPet == null || optionsPet.length == 0) {
			JOptionPane.showMessageDialog(null, "Necessário cadastrar pets primeiro!");
			return;
		}
		
        ArrayList<String> colunasList = new ArrayList<>();
        ArrayList<String> valoresList = new ArrayList<>();
       
        String idAgendamentoInput = JOptionPane.showInputDialog("Digite o código do agendamento:");
        if (idAgendamentoInput == null || idAgendamentoInput.equals("")) {
            JOptionPane.showMessageDialog(null, "O código do agendamento não pode ser nulo.");
            return;
        }
        int idAgendamento = Integer.parseInt(idAgendamentoInput);

        String dataAgendamentoInput = JOptionPane.showInputDialog("Digite a data do agendamento (dd/MM/yyyy):");
        if (dataAgendamentoInput != null && !dataAgendamentoInput.equals("")) {
            LocalDate dataAgendamento;
            try {
                colunasList.add("dataAgendamento");
                dataAgendamento = LocalDate.parse(dataAgendamentoInput, Formatos.DATE_INPUT_FORMATTER);
                valoresList.add(dataAgendamento.format(Formatos.DATE_SQL_FORMATTER));
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(null, "Data de agendamento no formato incorreto. Use dd/MM/yyyy.");
                return;
            }
        }

        String observacao = JOptionPane.showInputDialog("Digite a observação:");
        if (observacao != null && !observacao.equals("")) {
            colunasList.add("observacao");
            valoresList.add(observacao);
        }

	    JComboBox<Item> comboBoxFuncionario = new JComboBox<>();
	    for (String[] option : optionsFuncionario) {
	        int idOption = Integer.parseInt(option[0]);
	        String nomeOption = option[1];
	        comboBoxFuncionario.addItem(new Item(idOption, nomeOption));
	    }

	    int selecao = JOptionPane.showConfirmDialog(null, comboBoxFuncionario, "Selecione o funcionário: ", JOptionPane.OK_CANCEL_OPTION);

	    int fkFuncionario = 0;
	    if (selecao == JOptionPane.OK_OPTION) {
	    	Item opcao = (Item) comboBoxFuncionario.getSelectedItem();
	        if (opcao != null) {
	        	fkFuncionario = opcao.getId();
	            colunasList.add("fkFuncionario");
	            valoresList.add(Integer.toString(fkFuncionario));
	            Log.geraLog("Funcionário selecionado: " + opcao.getNome() + " (ID: " + fkFuncionario + ")");
	        } else {
	        	Log.geraLog("Nenhuma opção selecionada.");
	        }
	    } else {
	    	Log.geraLog("Nenhuma opção selecionada.");
	    }
	    
	    JComboBox<Item> comboBoxPet = new JComboBox<>();
	    for (String[] option : optionsPet) {
	        int idOption = Integer.parseInt(option[0]);
	        String nomeOption = option[1];
	        comboBoxPet.addItem(new Item(idOption, nomeOption));
	    }

	    selecao = JOptionPane.showConfirmDialog(null, comboBoxPet, "Selecione o pet: ", JOptionPane.OK_CANCEL_OPTION);

	    int fkPet = 0;
	    if (selecao == JOptionPane.OK_OPTION) {
	    	Item opcao = (Item) comboBoxPet.getSelectedItem();
	        if (opcao != null) {
	        	fkPet = opcao.getId();
	            colunasList.add("fkPet");
	            valoresList.add(Integer.toString(fkPet));
	            Log.geraLog("Pet selecionado: " + opcao.getNome() + " (ID: " + fkPet + ")");
	        } else {
	        	Log.geraLog("Nenhuma opção selecionada.");
	        }
	    } else {
	    	Log.geraLog("Nenhuma opção selecionada.");
	    }
	    
	    JComboBox<Item> comboBoxServico = new JComboBox<>();
	    for (String[] option : optionsServico) {
	        int idOption = Integer.parseInt(option[0]);
	        String nomeOption = option[1];
	        comboBoxServico.addItem(new Item(idOption, nomeOption));
	    }

	    selecao = JOptionPane.showConfirmDialog(null, comboBoxServico, "Selecione o serviço: ", JOptionPane.OK_CANCEL_OPTION);

	    int fkServico = 0;
	    if (selecao == JOptionPane.OK_OPTION) {
	    	Item opcao = (Item) comboBoxServico.getSelectedItem();
	        if (opcao != null) {
	        	fkServico = opcao.getId();
	            colunasList.add("fkServico");
	            valoresList.add(Integer.toString(fkServico));
	            Log.geraLog("Serviço selecionado: " + opcao.getNome() + " (ID: " + fkServico + ")");
	        } else {
	        	Log.geraLog("Nenhuma opção selecionada.");
	        }
	    } else {
	    	Log.geraLog("Nenhuma opção selecionada.");
	    }
	    
        if (!colunasList.isEmpty() && !valoresList.isEmpty()) {
            String[] colunas = colunasList.toArray(new String[0]);
            String[] valores = valoresList.toArray(new String[0]);

            if (SQLGenerator.updateSQL(tabela, idAgendamento, colunas, valores)) {
                JOptionPane.showMessageDialog(null, "Agendamento alterado com sucesso!");
                if (colunasList.contains("dataAgendamento")) {                	
                	setDataAgendamento(dataAgendamentoInput);
                }
                if (colunasList.contains("observacao")) {                	
                	setObservacao(observacao);
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
        String idAgendamentoInput = JOptionPane.showInputDialog("Digite o código do agendamento:");
        if (idAgendamentoInput == null || idAgendamentoInput.equals("")) {
            JOptionPane.showMessageDialog(null, "O código do agendamento não pode ser nulo.");
            return;
        }
        int idAgendamento = Integer.parseInt(idAgendamentoInput);

        if (SQLGenerator.deleteSQL(tabela, idAgendamento)) {
            JOptionPane.showMessageDialog(null, "Agendamento excluído com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Ocorreu algum erro na exclusão.");
        }
    }

    public void listar() {
        Relatorio.mostrarDados(SQLGenerator.SelectSQL(null, tabela, null, null));
    }

    public void consultar() {
        String idAgendamentoInput = JOptionPane.showInputDialog("Digite o código do agendamento:");
        if (idAgendamentoInput == null || idAgendamentoInput.equals("")) {
            JOptionPane.showMessageDialog(null, "O código do agendamento não pode ser nulo.");
            return;
        }
        int idAgendamento = Integer.parseInt(idAgendamentoInput);

        String where = "WHERE idAgendamento = " + idAgendamento;

        Relatorio.mostrarDados(SQLGenerator.SelectSQL(null, tabela, null, where));
    }
}