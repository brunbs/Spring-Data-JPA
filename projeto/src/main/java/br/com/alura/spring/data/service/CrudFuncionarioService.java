package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CrudFuncionarioService {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final FuncionarioRepository funcionarioRepository;
    private final CargoRepository cargoRepository;
    private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;

    public CrudFuncionarioService(FuncionarioRepository funcionarioRepository, CargoRepository cargoRepository, UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.cargoRepository = cargoRepository;
        this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
    }

    public void inicial(Scanner scanner) {


        Boolean system = true;

        while(system) {

            System.out.println("Qual ação deseja realizar?");
            System.out.println("0 - Sair");
            System.out.println("1 - Salvar");
            System.out.println("2 - Editar");
            System.out.println("3 - Deletar");
            System.out.println("4 - Listar");

            int action = scanner.nextInt();

            switch(action) {
                case 1:
                    salvar(scanner);
                    break;
                case 2:
                    editar(scanner);
                    break;
                case 3:
                    deletar(scanner);
                    break;
                case 4:
                    listar(scanner);
                    break;
                default:
                    system = false;
            }
        }

    }


    private void salvar(Scanner scanner) {

        System.out.println("Digite o id do cargo:");
        int cargoId = scanner.nextInt();
        Optional<Cargo> cargo = cargoRepository.findById(cargoId);

        if(cargo.isPresent()) {
            System.out.println("Digite o nome do Funcionário: ");
            String nome = scanner.nextLine().toUpperCase();
            nome += scanner.nextLine().toUpperCase();
            System.out.println("Digite o CPF do Funcionário: ");
            String cpf = scanner.nextLine();
            System.out.println("Digite o salário do Funcionário: ");
            BigDecimal salario = scanner.nextBigDecimal();

            LocalDate dataContratacao = LocalDate.now();

            List<UnidadeTrabalho> unidades = unidade(scanner);

            Funcionario func = new Funcionario();

            func.setCargo(cargo.get());
            func.setNome(nome);
            func.setCpf(cpf);
            func.setSalario(salario);
            func.setDataContratacao(dataContratacao);
            func.setUnidadeTrabalhos(unidades);

            funcionarioRepository.save(func);

            System.out.println("Funcionário salvo");
        } else {
            System.out.println("Cargo não encontrado!");
        }

    }

    private List<UnidadeTrabalho> unidade(Scanner scanner) {
        Boolean system = true;

        List<UnidadeTrabalho> unidades = new ArrayList<>();

        while(system) {
            System.out.println("Digite a unidade de trabalho, ou digite 0 para sair");
            int action = scanner.nextInt();
            switch(action) {
                case 0:
                    system = false;
                    break;
                default:
                    Optional<UnidadeTrabalho> unidade = unidadeTrabalhoRepository.findById(action);
                    if(unidade.isPresent()) {
                        unidades.add(unidade.get());
                    } else {
                        System.out.println("Unidade não encontrada!");
                    }
            }
        }

        return unidades;

    }

    private void editar(Scanner scanner) {

            System.out.println("Digite o id do Funcionário: ");
            int id = scanner.nextInt();

            Optional<Funcionario> retorno = funcionarioRepository.findById(id);
            if (retorno.isPresent()) {
                Funcionario func = retorno.get();
                func.setId(id);
                Boolean system = true;

                while(system) {
                    System.out.println("O que deseja alterar?");
                    System.out.println("0 - Confirmar e Sair");
                    System.out.println("1 - Nome");
                    System.out.println("2 - CPF");
                    System.out.println("3 - Salário");
                    System.out.println("4 - Cargo");

                    int action = scanner.nextInt();

                    switch(action) {
                        case 1:
                            String nome = scanner.nextLine().toUpperCase();
                            nome += scanner.nextLine().toUpperCase();
                            func.setNome(nome);
                            break;
                        case 2:
                            scanner.nextLine();
                            String cpf = scanner.nextLine();
                            func.setCpf(cpf);
                            break;
                        case 3:
                            BigDecimal salario = scanner.nextBigDecimal();
                            func.setSalario(salario);
                            break;
                        case 4:
                            Integer cargoId = scanner.nextInt();
                            Optional<Cargo> cargo = cargoRepository.findById(cargoId);
                            if (cargo.isPresent()) {
                                func.setCargo(cargo.get());
                            } else {
                                System.out.println("cargo não encontrado");
                            }
                        default:
                            system = false;
                            funcionarioRepository.save(func);
                            System.out.println("Alterações realizadas com sucesso!");
                            break;
                    }
                }

                } else {
                System.out.println("Funcionário não encontrado!");

            }

        }

    private void deletar(Scanner scanner) {
        System.out.println("Digite o id do Funcionário: ");
        int id = scanner.nextInt();
        try {
            funcionarioRepository.deleteById(id);
            System.out.println("Funcionário deletado com sucesso!");
        } catch (Exception e) {
            System.out.println("Funcionário não encontrado!");
        }
    }

    private void listar(Scanner scanner) {

        System.out.println("Qual página deseja visualizar?");
        Integer page = scanner.nextInt();

        Pageable pageable = PageRequest.of(page, 5, Sort.Direction.ASC, "nome"); //para ordenação padrão "por id" utilizar o Sort.unsorted()

        Page<Funcionario> funcionarios = funcionarioRepository.findAll(pageable);

        System.out.println(funcionarios); //mostra o total de páginas
        System.out.println("Pagina atual: " + funcionarios.getNumber());
        System.out.println("Total de elementos " + funcionarios.getTotalElements());

        funcionarios.forEach(funcionario -> System.out.println(funcionario));
    }

}
