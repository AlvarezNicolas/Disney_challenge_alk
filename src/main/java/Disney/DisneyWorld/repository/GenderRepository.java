/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Disney.DisneyWorld.repository;

import Disney.DisneyWorld.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author anico
 */
public interface GenderRepository extends JpaRepository<Gender, String>{
    
}
