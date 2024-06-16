drop database if exists dbpetzco;
create database dbpetzco;
use dbpetzco;

CREATE TABLE endereco (
    idEndereco INT PRIMARY KEY AUTO_INCREMENT,
    rua VARCHAR (100),
    numero int,
    bairro VARCHAR (50),
    cidade VARCHAR (50),
    estado VARCHAR (50),
    complemento VARCHAR (100),
    cep VARCHAR (15)
);

CREATE TABLE cliente (
    idCliente INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR (100),
    cpf VARCHAR (20),
    telefone VARCHAR (20),
    email VARCHAR (100),
    dataNascimento DATE,
    fkEndereco INT,
    FOREIGN KEY (fkEndereco) REFERENCES endereco(idEndereco)
);

CREATE TABLE cargoFuncionario (
	idCargoFuncionario INT PRIMARY KEY AUTO_INCREMENT,
    nomeCargo VARCHAR (20),
    salarioBase FLOAT
);

CREATE TABLE funcionario (
	idFuncionario INT PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR (100),
	cpf VARCHAR (20),
    telefone VARCHAR (20),
    email VARCHAR (100),
    dataContratacao DATE,
    fkEndereco INT,
    fkCargoFuncionario INT,
    FOREIGN KEY (fkEndereco) REFERENCES endereco(idEndereco),
    FOREIGN KEY (fkCargoFuncionario) REFERENCES cargoFuncionario(idCargoFuncionario)
);

CREATE TABLE especie (
 	idEspecie INT PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR (30),
    nomeCientifico VARCHAR (50),
    vidaMedia VARCHAR (20),
    descricao VARCHAR (100)
);

CREATE TABLE raca (
 	idRaca INT PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR (30),
    porte VARCHAR (20),
    descricao VARCHAR (100),
    fkEspecie INT,
    FOREIGN KEY (fkEspecie) REFERENCES especie(idEspecie)
);

CREATE TABLE pet (
 	idPet INT PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR (30),
    idade VARCHAR (10),
    fkRaca INT,
    fkCliente INT,
    FOREIGN KEY (fkRaca) REFERENCES raca(idRaca),
    FOREIGN KEY (fkCliente) REFERENCES cliente(idCliente)
);

CREATE TABLE servico (
 	idServico INT PRIMARY KEY AUTO_INCREMENT,
	isDisponivel BOOLEAN,
    tempoEstimado TIME,
	descricao VARCHAR (30),
    valor FLOAT,
    fkCargoFuncionario INT,
    FOREIGN KEY (fkCargoFuncionario) REFERENCES cargoFuncionario(idCargoFuncionario)
);

CREATE TABLE agendamento (
 	idAgendamento INT PRIMARY KEY AUTO_INCREMENT,
	dataAgendamento DATE,
    observacao VARCHAR (50),
    fkFuncionario INT,
    fkServico INT,
    fkPet INT,
    FOREIGN KEY (fkFuncionario) REFERENCES funcionario(idFuncionario),
    FOREIGN KEY (fkServico) REFERENCES servico(idServico),
    FOREIGN KEY (fkPet) REFERENCES pet(idPet)
);