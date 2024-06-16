package Padrao;

import java.time.LocalDate;
import javax.swing.JOptionPane;

public class Relatorio {
    public static void mostrarDados(String[][] result) {
    	if (result.length <= 0) {
            JOptionPane.showMessageDialog(null, "Não há registros.");
            return;
        }
        StringBuilder resultText = new StringBuilder();

        for (int i = 1; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                String coluna = result[0][j];
                String valor = result[i][j];

                String colunaFormatada = formatarNomeColuna(coluna);
                String valorFormatado = formatarValor(coluna, valor);

                if (!coluna.startsWith("fk")) {
                	resultText.append(colunaFormatada).append(": ").append(valorFormatado).append("\n");
                }
            }
            resultText.append("\n");
        }
        JOptionPane.showMessageDialog(null, resultText.toString());
    }

    private static String formatarNomeColuna(String nomeColuna) {
    	if (nomeColuna.startsWith("id")) {    		
    		return "Código " + nomeColuna.substring(2);
    	}
    	switch (nomeColuna) {
	        case "email":
	            return "E-mail";
	        case "cep":
	        case "cpf":
	            return nomeColuna.toUpperCase();
    	}
        
        String[] palavras = nomeColuna.split("(?=[A-Z])");
        StringBuilder nomeFormatado = new StringBuilder();
        for (String palavra : palavras) {
            if (nomeFormatado.length() > 0) {
                nomeFormatado.append(" ");
            }
            nomeFormatado.append(palavra.substring(0, 1).toUpperCase());
            if (palavra.length() > 1) {
                nomeFormatado.append(palavra.substring(1).toLowerCase());
            }
        }
        return nomeFormatado.toString();
    }
    
    private static String formatarValor(String nomeColuna, String valor) {
        switch (nomeColuna) {
            case "telefone":
                if (valor.length() > 9) {
                    return String.format("(%s) %s-%s",
                            valor.substring(0, 2),
                            valor.substring(2, 6),
                            valor.substring(6));
                }                
            case "cep":
                return String.format("%s-%s",
                        valor.substring(0, 5),
                        valor.substring(5));
            case "cpf":
                return String.format("%s.%s.%s-%s",
                        valor.substring(0, 3),
                        valor.substring(3, 6),
                        valor.substring(6, 9),
                        valor.substring(9));
        }
        
        if (nomeColuna.startsWith("data")) {
            LocalDate date = LocalDate.parse(valor, Formatos.DATE_SQL_FORMATTER);
            valor = (date.format(Formatos.DATE_INPUT_FORMATTER));
        }
        return valor;
    }

}

