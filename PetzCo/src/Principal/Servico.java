package Principal;

import javax.swing.JOptionPane;
import java.sql.Time;
import java.util.ArrayList;

import Connection.SQLGenerator;
import Padrao.CRUD;
import Padrao.Relatorio;

public class Servico implements CRUD {
    private final String tabela = "servico";
    private int idServico;
    private boolean isDisponivel;
    private Time tempoEstimado;
    private String descricao;
    private float valor;
    private int fkCargoFuncionario;

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

    public int getFkCargoFuncionario() {
        return fkCargoFuncionario;
    }

    public void setFkCargoFuncionario(int fkCargoFuncionario) {
        this.fkCargoFuncionario = fkCargoFuncionario;
    }

    @Override
    public void cadastrar() {
        String descricao = JOptionPane.showInputDialog("Digite a descrição:");
        boolean isDisponivel = JOptionPane.showConfirmDialog(null, "Está disponível?") == JOptionPane.YES_OPTION;
        String tempoEstimadoInput = JOptionPane.showInputDialog("Digite o tempo estimado (HH:MM:SS):");
        Time tempoEstimado;
        try {
            tempoEstimado = Time.valueOf(tempoEstimadoInput);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Tempo estimado no formato incorreto. Use HH:MM:SS.");
            return;
        }
        float valor = Float.parseFloat(JOptionPane.showInputDialog("Digite o valor:"));
        int fkCargoFuncionario = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do cargo do funcionário:"));

        String colunas = "isDisponivel, tempoEstimado, descricao, valor, fkCargoFuncionario";
        String valores = "'" + isDisponivel + "','" + tempoEstimado + "','" + descricao + "','" + valor + "','" + fkCargoFuncionario + "'";

        int idServico = SQLGenerator.insertSQL(tabela, colunas, valores);
        if (idServico > 0) {
            JOptionPane.showMessageDialog(null, "Serviço cadastrado com sucesso! Código do serviço: " + idServico);
            setIdServico(idServico);
            setDescricao(descricao);
            setDisponivel(isDisponivel);
            setTempoEstimado(tempoEstimado);
            setValor(valor);
            setFkCargoFuncionario(fkCargoFuncionario);
        } else {
            JOptionPane.showMessageDialog(null, "Ocorreu algum erro no cadastro.");
        }
    }

    @Override
    public void alterar() {
        ArrayList<String> colunasList = new ArrayList<>();
        ArrayList<String> valoresList = new ArrayList<>();

        int idServico = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do serviço:"));

        String descricao = JOptionPane.showInputDialog("Digite a descrição:");
        if (descricao != null && !descricao.equals("")) {
            colunasList.add("descricao");
            valoresList.add(descricao);
        }

        boolean isDisponivel = JOptionPane.showConfirmDialog(null, "Está disponível?") == JOptionPane.YES_OPTION;
        colunasList.add("isDisponivel");
        valoresList.add(Boolean.toString(isDisponivel));

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

        float valor = Float.parseFloat(JOptionPane.showInputDialog("Digite o valor:"));
        if (valor > 0) {
            colunasList.add("valor");
            valoresList.add(Float.toString(valor));
        }

        int fkCargoFuncionario = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do cargo do funcionário:"));
        if (fkCargoFuncionario > 0) {
            colunasList.add("fkCargoFuncionario");
            valoresList.add(Integer.toString(fkCargoFuncionario));
        }

        if (!colunasList.isEmpty() && !valoresList.isEmpty()) {
            String[] colunas = colunasList.toArray(new String[0]);
            String[] valores = valoresList.toArray(new String[0]);

            if (SQLGenerator.updateSQL(tabela, idServico, colunas, valores)) {
                JOptionPane.showMessageDialog(null, "Serviço alterado com sucesso!");
                setDescricao(descricao);
                setDisponivel(isDisponivel);
                setTempoEstimado(Time.valueOf(tempoEstimadoInput));
                setValor(valor);
                setFkCargoFuncionario(fkCargoFuncionario);
            } else {
                JOptionPane.showMessageDialog(null, "Ocorreu algum erro na alteração.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Não é possível deixar os dados em branco. Nada alterado.");
        }
    }

    @Override
    public void excluir() {
        int idServico = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do serviço:"));

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
        int idServico = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do serviço:"));

        String colunas = "idServico, isDisponivel, tempoEstimado, descricao, valor, fkCargoFuncionario";
        String where = "WHERE idServico = " + idServico;

        Relatorio.mostrarDados(SQLGenerator.SelectSQL(colunas, tabela, null, where));
    }
}