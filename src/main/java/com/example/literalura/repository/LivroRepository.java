package com.example.literalura.repository;

import com.example.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository  extends JpaRepository<Livro,Long> {

    Livro findByTituloContainingIgnoreCase(String titulo);
    @Query("SELECT l FROM Livro l WHERE l.idioma = :idioma")
    List<Livro> findByIdioma(String idioma);
}
