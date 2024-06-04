package com.example.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(
                         @JsonAlias("title") String titulo,
                         @JsonAlias("authors")  List<Autor> autor,
                         @JsonAlias("languages") List<String> idioma,
                        @JsonAlias("download_count")  String downloads
                         ) {
}
