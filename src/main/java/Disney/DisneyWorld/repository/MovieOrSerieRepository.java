/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Disney.DisneyWorld.repository;

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
public interface MovieOrSerieRepository extends JpaRepository<MovieOrSerie, String> {

    @Query("SELECT a FROM moviesAndSeries a WHERE alta=1")
    public List<MovieOrSerie> listMovies();

    @Query("SELECT a FROM moviesAndSeries a WHERE a.titulo = :titulo")
    public MovieOrSerie findByName(@Param("titulo") String titulo);
    
    @Query("SELECT genero FROM moviesAndSeries a WHERE a.gender = :idGender")
    public List<MovieOrSerie> findByIdGender(@Param("idGender") String idGender);
    
    @Query("SELECT a FROM moviesAndSeries ORDER BY titulo ASC")
    public List<MovieOrSerie> OrderASC();
    
    @Query("SELECT a FROM moviesAndSeries ORDER BY titulo DESC")
    public List<MovieOrSerie> OrderDESC();
    
}
