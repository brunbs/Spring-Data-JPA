package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Service
public class RelatoriosService {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final FuncionarioRepository funcionarioRepository;

    public RelatoriosService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }


    public void inicial(Scanner scanner) {
        Boolean system = true;

        while(system) {
            System.out.println("Qual ação de Relatório deseja executar?");
            System.out.println("0 - Sair");
            System.out.println("1 - Buscar funcionário pelo nome");
            System.out.println("2 - Buscar funcionário pelo nome, salário maior que e data de contratação");
            System.out.println("3 - Buscar funcionário por data de contratação");
            System.out.println("4 - Pesquisa funcionário salário");

            int action = scanner.nextInt();

            switch(action) {
                case 1:
                    buscaFuncionarioNome(scanner);
                    break;
                case 2:
                    buscaFuncionarioNomeSalarioMaiorData(scanner);
                    break;
                case 3:
                    buscaFuncionarioDataContratacao(scanner);
                    break;
                case 4:
                    pesquisaFuncionarioSalario();
                    break;
                default:
                    system = false;
                    break;
            }

        }
    }

    private void buscaFuncionarioNome(Scanner scanner) {
        System.out.println("Digite o nome do funcionário");
        String nome = scanner.next().toUpperCase();
        List<Funcionario> lista = funcionarioRepository.findByNomeStartingWith(nome);
        lista.forEach(System.out::println);
    }

    private void buscaFuncionarioNomeSalarioMaiorData(Scanner scanner) {
        System.out.println("Qual o nome");
        String nome = scanner.next();

        System.out.println("Qual data de contratação?");
        String data = scanner.next();
        LocalDate localDate = LocalDate.parse(data, formatter);

        System.out.println("Qual salário?");
        BigDecimal salario = scanner.nextBigDecimal();

        List<Funcionario> lista = funcionarioRepository.findNomeSalarioMaiorDataContratacao(nome, salario, localDate);
        lista.forEach(System.out::println);
    }

    private void buscaFuncionarioDataContratacao(Scanner scanner) {
        System.out.println("Digite a data de contratação");
        String data = scanner.next();
        LocalDate localDate = LocalDate.parse(data, formatter);

        List<Funcionario> lista = funcionarioRepository.findDataContratacaoMaior(localDate);
        lista.forEach(System.out::println);
    }

    private void pesquisaFuncionarioSalario() {
        List<FuncionarioProjecao> lista = funcionarioRepository.findFuncionarioSalario();
        lista.forEach(funcionario -> System.out.println("Funcionário: id " + funcionario.getId() + " | nome: " + funcionario.getNome() + " | salário: " + funcionario.getSalario()));
    }

}
