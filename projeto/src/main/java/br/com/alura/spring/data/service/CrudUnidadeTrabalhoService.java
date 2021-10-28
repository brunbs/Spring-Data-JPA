package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

@Service
public class CrudUnidadeTrabalhoService {

    private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;

    public CrudUnidadeTrabalhoService(UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
        this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
    }

    public void inicial(Scanner scanner) {

        Boolean system = true;

        while(system) {
            System.out.println("Qual ação de Unidade de Trabalho deseja executar?");
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
                    editar(scanner);
                    break;
                case 3:
                    listar();
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

        UnidadeTrabalho unidadeTrabalho = new UnidadeTrabalho();

        System.out.println("Insira uma descrição: ");
        String descricao = scanner.nextLine().toUpperCase();
        descricao += scanner.nextLine().toUpperCase();
        unidadeTrabalho.setDescricao(descricao);

        System.out.println("Insira um endereço: ");
        String endereco = scanner.nextLine().toUpperCase();
        unidadeTrabalho.setEndereco(endereco);

        unidadeTrabalhoRepository.save(unidadeTrabalho);
    }

    private void editar(Scanner scanner) {
        System.out.println("Insira o id da Unidade de Trabalho que deseja alterar: ");
        int id = scanner.nextInt();
        Optional<UnidadeTrabalho> retorno = unidadeTrabalhoRepository.findById(id);
        if (retorno.isPresent()) {
            UnidadeTrabalho unidadeTrabalho = retorno.get();
            unidadeTrabalho.setId(id);

            Boolean system = true;

            while(system) {
                System.out.println("O que deseja editar da Unidade de Trabalho?");
                System.out.println("0 - Confirmar e Sair");
                System.out.println("1 - Descrição");
                System.out.println("2 - Endereço");

                int action = scanner.nextInt();

                switch(action) {
                    case 1:
                        System.out.println("Digite uma nova descrição: ");
                        String descricao = scanner.nextLine().toUpperCase();
                        descricao += scanner.nextLine().toUpperCase();
                        unidadeTrabalho.setDescricao(descricao);
                        break;
                    case 2:
                        System.out.println("Digite um novo endereço: ");
                        String endereco = scanner.nextLine().toUpperCase();
                        endereco += scanner.nextLine().toUpperCase();
                        unidadeTrabalho.setEndereco(endereco);
                        break;
                    default:
                        system = false;
                        break;
                }
                unidadeTrabalhoRepository.save(unidadeTrabalho);
            }

        } else {
            System.out.println("Unidade de Trabalho não encontrada!");
        }
    }

    private void listar() {
        Iterable<UnidadeTrabalho> lista = unidadeTrabalhoRepository.findAll();
        lista.forEach(unidadeTrabalho -> System.out.println(unidadeTrabalho));
    }

    private void deletar(Scanner scanner) {
        System.out.println("Digite o id para deletar: ");
        int id = scanner.nextInt();
        unidadeTrabalhoRepository.deleteById(id);
    }

}
