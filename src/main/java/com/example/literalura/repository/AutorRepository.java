package com.example.literalura.repository;

import com.example.literalura.model.Autor;
import com.example.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository  extends JpaRepository<Autor,Long> {
    Autor findByNameContainingIgnoreCase(String name);

    Boolean existsByName(String name);

    Autor findByName(String name);
    @Query("SELECT a FROM Autor a WHERE a.birth_year <= :year AND (a.death_year IS NULL OR a.death_year >= :year)")
    List<Autor> findAuthorsByYear(Integer year);



}
