package br.com.alura.spring.data.orm;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="funcionarios")
public class Funcionario {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String cpf;
    private BigDecimal salario;
    private LocalDate dataContratacao;
    @ManyToOne(fetch = FetchType.EAGER) //pois vários funcionários podem ter o mesmo cargo e um funcionário só pode ter um cargo
    @JoinColumn(name="cargo_id", nullable = false)
    private Cargo cargo;


    @Fetch(FetchMode.SELECT)
    @ManyToMany(fetch = FetchType.EAGER) //varios funcionarios podem trablhar em varias unidades de trabalho assim como cada unidade de trabalho tem varios funcionarios
    @JoinTable(name = "funcionarios_unidades", joinColumns =  {
            @JoinColumn(name="fk_funcionario") },
    inverseJoinColumns = { @JoinColumn(name="fk_unidade") })
    private List<UnidadeTrabalho> unidadeTrabalhos;

    public Funcionario() {
    }

    public Funcionario(String nome, String cpf, BigDecimal salario, LocalDate dataContratacao) {
        this.nome = nome;
        this.cpf = cpf;
        this.salario = salario;
        this.dataContratacao = dataContratacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public void setUnidadeTrabalhos(List<UnidadeTrabalho> unidadeTrabalhos) {
        this.unidadeTrabalhos = unidadeTrabalhos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public List<UnidadeTrabalho> getUnidadeTrabalhos() {
        return unidadeTrabalhos;
    }

    @Override
    public String toString() {
        return "Funcionario: " + "id:" + id + "| nome:'" + nome + "| cpf:" + cpf + "| salario:" + salario
                + "| dataContratacao:" + dataContratacao + "| cargo:" + cargo.getDescricao();
    }
}
