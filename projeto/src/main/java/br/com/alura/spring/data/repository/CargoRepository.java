package br.com.alura.spring.data.repository;

import br.com.alura.spring.data.orm.Cargo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends CrudRepository<Cargo, Integer> { //precisamos passar para o Repository dois parâmetros: o tipo de objeto que ele irá trabalhar e o tipo de "id", no caso do cargo, a id é um Integer

}
