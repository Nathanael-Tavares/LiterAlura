package com.example.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne
    private Autor autor;
    private String idioma;
    private Integer downloads;

    public Livro() {
    }

    public Livro(DadosLivro dadosLivro) {

        this.titulo = dadosLivro.titulo();

        if (dadosLivro.autor().size()>0){
            this.autor = dadosLivro.autor().get(0);
        }else {
            this.autor = null;
        }

        if (dadosLivro.idioma().size()>0){
            this.idioma=dadosLivro.idioma().get(0);
        }
        this.downloads = Integer.valueOf(dadosLivro.downloads());

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {

        return  "*********LIVRO*********** \n"+
                "Titulo: "+  titulo +  "\n"+
                "Autor: " + getAutor() +"\n"+
                "Idioma: " + idioma +"\n"+
                "Downloads: " + downloads +"\n"
                +"*****************************";
    }
}
