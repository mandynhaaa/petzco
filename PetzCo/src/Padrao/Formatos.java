package Padrao;

import java.time.format.DateTimeFormatter;

public class Formatos {
    public static final DateTimeFormatter DATE_INPUT_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter DATE_SQL_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}
