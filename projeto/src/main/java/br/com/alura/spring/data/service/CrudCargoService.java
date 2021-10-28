package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Scanner;

@Service
public class CrudCargoService {

    private Boolean system = true;
    private final CargoRepository cargoRepository;

    public CrudCargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public void inicial(Scanner scanner) {

        while(system) {
            System.out.println("Qual ação de cargo deseja executar?");
            System.out.println("0 - sair");
            System.out.println("1 - salvar");
            System.out.println("2 - atualizar");
            System.out.println("3 - listar");
            System.out.println("4 - deletar");

            int action = scanner.nextInt();

            switch (action) {
                case 1:
                    salvar(scanner);
                    break;
                case 2:
                    atualizar(scanner);
                    break;
                case 3:
                    visualizar();
                    break;
                case 4:
                    deletar(scanner);
                    break;
                default:
                    system = false;
                    break;
            }

        }

    }

    private void salvar(Scanner scanner) {
        System.out.println("Descrição do cargo:");
        String descricao = scanner.nextLine().toUpperCase();
        descricao += scanner.nextLine().toUpperCase();
        Cargo cargo = new Cargo();
        cargo.setDescricao(descricao);
        cargoRepository.save(cargo);
        System.out.println("Cargo salvo");

    }

    private void atualizar(Scanner scanner) {
        System.out.println("Informe o id que quer alterar:");
        int id = scanner.nextInt();
        System.out.println("Informe a nova descrição:");
        String descricao = scanner.nextLine().toUpperCase();
        descricao += scanner.nextLine().toUpperCase();

        Cargo cargo = new Cargo();
        cargo.setId(id);
        cargo.setDescricao(descricao);

        cargoRepository.save(cargo);

        System.out.println("Atualização realizada com sucesso!");
    }

    private void visualizar() {
        Iterable<Cargo> cargos = cargoRepository.findAll();
        cargos.forEach(cargo -> System.out.println(cargo));
    }

    private void deletar(Scanner scanner) {
        System.out.println("Digite o id:");
        int id = scanner.nextInt();
        try {
            cargoRepository.deleteById(id);
            System.out.println("Cargo deletado com sucesso!");
        } catch (Exception e) {
            System.out.println("Id não encontrado");
        }
    }

}
