package br.com.alura.spring.data.repository;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
//public interface FuncionarioRepository extends CrudRepository<Funcionario, Integer> { //repository utilizado anteriormente, quando não precisava de paginação

    public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Integer>, JpaSpecificationExecutor<Funcionario> { //este repository é utilizado para fazer paginação (PagingAndSorting) e o JpaSpecificationExecutor executa as specifications

    //Derived Query: queries criadas por comandos java
    List<Funcionario> findByNomeStartingWith(String nome);  //lista de outros métodos de busca que podem ser feitos: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation

    @Query(value = "SELECT f.id, f.nome, f.salario FROM funcionarios f", nativeQuery = true)
    List<FuncionarioProjecao> findFuncionarioSalario();

    //JPQL: queries criadas através de uma estrutura SQL porém com os nomes das entidades Java
    @Query("SELECT f FROM Funcionario f WHERE f.nome = :nome AND f.salario >= :salario AND f.dataContratacao = :dataContratacao") //o nome da entidade é conforme a classe java. O 'f' é um alias
    List<Funcionario> findNomeSalarioMaiorDataContratacao(String nome, BigDecimal salario, LocalDate dataContratacao);

    @Query(value = "SELECT * FROM funcionarios f WHERE f.data_contratacao >= :data", nativeQuery = true) //aqui estamos usando Native Query, ou seja, uma consulta nativa no banco de dados, não utilizando as entidades
    List<Funcionario> findDataContratacaoMaior(LocalDate data);

    //Paginação também funciona com métodos de busca, tipo o findByNome, mas para isso deve-se passar o Pageable como parâmetro, criar o objeto Pageable usando o PageRequest e a interface FuncionarioRepository deve estender o PagingAndSortingRepository

    // List<Funcionario> findByCargoDescricao(String descricao); //neste ele busca funcionarios baseado na descrição do cargo, ou seja, consulta por tabelas relacionadas. Descrição é um atributo dentro da entidade Cargo. Essa mesma pesquisa poderia ser feita abaixo:
    //@Query("SELECT f FROM Funcionario f JOIN f.cargo c WHERE c.descricao = :descricao")
    //List<Funcionario> findByCargoPelaDescricao(String descricao);
    //List<Funcionario> findByUnidadeTrabalhos_Descricao(String descricao); // esta pesquisa na entidade UnidadeTrabalhos. como essa entidade é formada por duas palavras, separamos o nome da entidade e o campo por um _

    // List<Funcionario> findByNomeStartingWithAndSalarioGreaterThanAndDataContratacao(String nome, BigDecimal salario, LocalDate dataContratacao); //essa query funciona, mas visualmente o nome fica muito grande e de dificil visualização
}
