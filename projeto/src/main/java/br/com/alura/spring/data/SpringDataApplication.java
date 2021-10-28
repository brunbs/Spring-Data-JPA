package br.com.alura.spring.data;

import java.util.Scanner;

import br.com.alura.spring.data.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@EnableJpaRepositories
@SpringBootApplication //varre toda a aplicação e lê todas as anotações Spring
public class SpringDataApplication implements CommandLineRunner{ //obriga a implementar o método "run" que será executado após o início do spring

	private final CrudCargoService cargoService;  //precisamos passar o repository, porém é uma interface e não podemos dar new nela, então vamos fazer a injeção de dependência
	private final CrudFuncionarioService funcionarioService;
	private final CrudUnidadeTrabalhoService unidadeTrabalhoService;
	private final RelatoriosService relatoriosService;
	private final RelatorioFuncionarioDinamico relatorioFuncionarioDinamico;

	private Boolean system = true;


	public SpringDataApplication(CrudCargoService cargoService,
								 CrudFuncionarioService funcionarioService,
								 CrudUnidadeTrabalhoService unidadeTrabalhoService,
								 RelatoriosService relatoriosService,
								 RelatorioFuncionarioDinamico relatorioFuncionarioDinamico) { //construtor da classe spring data, dizendo que é para criar uma instância de cargorepository
		this.cargoService = cargoService;
		this.funcionarioService = funcionarioService;
		this.unidadeTrabalhoService = unidadeTrabalhoService;
		this.relatoriosService = relatoriosService;
		this.relatorioFuncionarioDinamico = relatorioFuncionarioDinamico;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDataApplication.class, args); //inicia o framework spring

	}


	@Override
	public void run(String... args) throws Exception {

		Scanner scanner = new Scanner(System.in);

		while(system) {
			System.out.println("Qual ação você quer executar?");
			System.out.println("0 - Sair");
			System.out.println("1 - Cargo");
			System.out.println("2 - Funcionario");
			System.out.println("3 - Unidade de Trabalho");
			System.out.println("4 - Relatórios");
			System.out.println("5 - Relatório funcionário dinâmico");

			int action = scanner.nextInt();

			switch(action) {
				case 1:
					cargoService.inicial(scanner);
					break;
				case 2:
					funcionarioService.inicial(scanner);
					break;
				case 3:
					unidadeTrabalhoService.inicial(scanner);
					break;
				case 4:
					relatoriosService.inicial(scanner);
					break;
				case 5:
					relatorioFuncionarioDinamico.inicial(scanner);
					break;
				default:
					system = false;
					break;
			}


		}


	}
}