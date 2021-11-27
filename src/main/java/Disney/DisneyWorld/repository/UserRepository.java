/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Disney.DisneyWorld.repository;

import Disney.DisneyWorld.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author anico
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>{
    
    @Query("SELECT c FROM Users c WHERE c.email = :email")
    public User findByEmail(@Param("email") String email);
    
}
