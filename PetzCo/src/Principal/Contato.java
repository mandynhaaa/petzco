package Principal;

public class Contato {
		protected String nome;
		protected String cpf;
		protected String telefone;
		protected String email;
		protected int fkEndereco;
		
		public String getCpf() {
			return cpf;
		}
		public void setCpf(String cpf) {
			this.cpf = cpf;
		}
		protected int getFkEndereco() {
			return fkEndereco;
		}
		protected void setFkEndereco(int fkEndereco) {
			this.fkEndereco = fkEndereco;
		}
		protected String getNome() {
			return nome;
		}
		protected void setNome(String nome) {
			this.nome = nome;
		}
		protected String getTelefone() {
			return telefone;
		}
		protected void setTelefone(String telefone) {
			this.telefone = telefone;
		}
		protected String getEmail() {
			return email;
		}
		protected void setEmail(String email) {
			this.email = email;
		}		
}
