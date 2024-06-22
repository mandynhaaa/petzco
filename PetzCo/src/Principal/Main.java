package Principal;

public class Main {
	public static void main(String[] args) {
        Cliente cliente = new Cliente();
        Pet pet = new Pet();
        Agendamento agendamento = new Agendamento();

        cliente.listar();
        pet.cadastrar();
        agendamento.cadastrar();
        agendamento.alterar();
        pet.listar();
        agendamento.excluir();
	}
}