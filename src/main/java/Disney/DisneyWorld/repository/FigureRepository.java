/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Disney.DisneyWorld.repository;

import Disney.DisneyWorld.entity.Figure;
import Disney.DisneyWorld.entity.MovieOrSerie;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author anico
 */
@Repository
public interface FigureRepository extends JpaRepository<Figure, String> {

    @Query("SELECT a FROM characters a WHERE alta=1")
    public List<Figure> listCharacters();

    @Query("SELECT a FROM characters a WHERE a.nombre = :nombre")
    public Figure findByName(@Param("nombre") String nombre);
    
    @Query("SELECT a FROM characters a WHERE a.edad = :edad")
    public Figure findByAge(@Param("edad") Integer edad);
    
    @Query("SELECT titulo FROM characters a WHERE a.MovieOrSerie = :idMovieOrSerie")
    public Figure findByIdMoviOrSerie(@Param("idMovieOrSerie") String idMovieOrSerie);

}
