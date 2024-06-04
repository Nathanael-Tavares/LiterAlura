package com.example.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private Integer birth_year;
    private Integer death_year;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    List<Livro> livros  = new ArrayList();

    public Autor(String name) {
        this.name=name;
    }
    public Autor(){}



    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(Integer birth_year) {
        this.birth_year = birth_year;
    }

    public Integer getDeath_year() {
        return death_year;
    }

    public void setDeath_year(Integer death_year) {
        this.death_year = death_year;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLivros(List<Livro> livros) {
        if (livros != null) {
            this.livros.clear(); // Limpa a lista de livros existente
            this.livros.addAll(livros); // Adiciona os novos livros à lista
            for (Livro livro : livros) {
                livro.setAutor(this); // Associa este autor aos livros
            }
        }
    }
    @Override
    public String toString() {
        return
                "Nome:" + name  +
                " Ano de nascimento: " + birth_year +
                " Ano de morte: " + death_year
                ;
    }
    public void imprimirLivros() {
        System.out.println("**********************");
        System.out.println(toString());
        System.out.println("Livros do Autor: " + this.name );
        for (Livro livro : livros) {
            System.out.println("*"+livro.getTitulo());
        }
        System.out.println("**********************");
}
}
