/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Disney.DisneyWorld.repository;

import Disney.DisneyWorld.entity.Figure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author anico
 */
@Repository
public interface FigureRepository extends JpaRepository<Figure, String>{
    
}
