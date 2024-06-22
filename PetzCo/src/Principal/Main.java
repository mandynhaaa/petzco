package Principal;

public class Main {
	public static void main(String[] args) {
        Cliente cliente = new Cliente();
        Pet pet = new Pet();
        Agendamento agendamento = new Agendamento();
  
        cliente.listar();
        pet.cadastrar();
        pet.listar();
        agendamento.cadastrar();
        agendamento.alterar();
        agendamento.listar();
        agendamento.excluir();
	}
}