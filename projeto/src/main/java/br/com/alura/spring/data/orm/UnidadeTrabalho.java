package br.com.alura.spring.data.orm;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="unidade_trabalho")
public class UnidadeTrabalho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String descricao;
    private String endereco;
    @ManyToMany(mappedBy = "unidadeTrabalhos", fetch = FetchType.EAGER) //pois um funcionário tem várias unidades de trabalho e cada unidade de trabalho tem vários funcionários
    private List<Funcionario> funcionarios;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UnidadeTrabalho{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", endereco='" + endereco + '\'' +
                '}';
    }

    public UnidadeTrabalho() {
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
