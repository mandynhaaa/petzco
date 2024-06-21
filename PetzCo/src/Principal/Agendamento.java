package Principal;

import javax.swing.JOptionPane;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import Connection.SQLGenerator;
import Padrao.CRUD;
import Padrao.Relatorio;
import Padrao.Formatos;

public class Agendamento implements CRUD {
    private final String tabela = "agendamento";
    private int idAgendamento;
    private LocalDate dataAgendamento;
    private String observacao;
    private int fkFuncionario;
    private int fkServico;
    private int fkPet;

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

    public int getFkFuncionario() {
        return fkFuncionario;
    }

    public void setFkFuncionario(int fkFuncionario) {
        this.fkFuncionario = fkFuncionario;
    }

    public int getFkServico() {
        return fkServico;
    }

    public void setFkServico(int fkServico) {
        this.fkServico = fkServico;
    }

    public int getFkPet() {
        return fkPet;
    }

    public void setFkPet(int fkPet) {
        this.fkPet = fkPet;
    }

    @Override
    public void cadastrar() {
        String dataAgendamentoInput = JOptionPane.showInputDialog("Digite a data do agendamento (dd/MM/yyyy):");
        LocalDate dataAgendamento;
        try {
            dataAgendamento = LocalDate.parse(dataAgendamentoInput, Formatos.DATE_INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Data de agendamento no formato incorreto. Use dd/MM/yyyy.");
            return;
        }

        String dataAgendamentoFormatada = dataAgendamento.format(Formatos.DATE_SQL_FORMATTER);

        String observacao = JOptionPane.showInputDialog("Digite a observação:");
        int fkFuncionario = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do funcionário:"));
        int fkServico = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do serviço:"));
        int fkPet = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do pet:"));

        String colunas = "dataAgendamento, observacao, fkFuncionario, fkServico, fkPet";
        String valores = "'" + dataAgendamentoFormatada + "','" + observacao + "','" + fkFuncionario + "','" + fkServico + "','" + fkPet + "'";

        int idAgendamento = SQLGenerator.insertSQL(tabela, colunas, valores);
        if (idAgendamento > 0) {
            JOptionPane.showMessageDialog(null, "Agendamento cadastrado com sucesso! Código do agendamento: " + idAgendamento);
            setIdAgendamento(idAgendamento);
            setDataAgendamento(dataAgendamentoInput);
            setObservacao(observacao);
            setFkFuncionario(fkFuncionario);
            setFkServico(fkServico);
            setFkPet(fkPet);
        } else {
            JOptionPane.showMessageDialog(null, "Ocorreu algum erro no cadastro.");
        }
    }

    @Override
    public void alterar() {
        ArrayList<String> colunasList = new ArrayList<>();
        ArrayList<String> valoresList = new ArrayList<>();

        int idAgendamento = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do agendamento:"));

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

        int fkFuncionario = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do funcionário:"));
        if (fkFuncionario > 0) {
            colunasList.add("fkFuncionario");
            valoresList.add(Integer.toString(fkFuncionario));
        }

        int fkServico = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do serviço:"));
        if (fkServico > 0) {
            colunasList.add("fkServico");
            valoresList.add(Integer.toString(fkServico));
        }

        int fkPet = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do pet:"));
        if (fkPet > 0) {
            colunasList.add("fkPet");
            valoresList.add(Integer.toString(fkPet));
        }

        if (!colunasList.isEmpty() && !valoresList.isEmpty()) {
            String[] colunas = colunasList.toArray(new String[0]);
            String[] valores = valoresList.toArray(new String[0]);

            if (SQLGenerator.updateSQL(tabela, idAgendamento, colunas, valores)) {
                JOptionPane.showMessageDialog(null, "Agendamento alterado com sucesso!");
                setDataAgendamento(dataAgendamentoInput);
                setObservacao(observacao);
                setFkFuncionario(fkFuncionario);
                setFkServico(fkServico);
                setFkPet(fkPet);
            } else {
                JOptionPane.showMessageDialog(null, "Ocorreu algum erro na alteração.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Não é possível deixar os dados em branco. Nada alterado.");
        }
    }

    @Override
    public void excluir() {
        int idAgendamento = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do agendamento:"));

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
        int idAgendamento = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do agendamento:"));

        String colunas = "idAgendamento, dataAgendamento, observacao, fkFuncionario, fkServico, fkPet";
        String where = "WHERE idAgendamento = " + idAgendamento;

        Relatorio.mostrarDados(SQLGenerator.SelectSQL(colunas, tabela, null, where));
    }
}