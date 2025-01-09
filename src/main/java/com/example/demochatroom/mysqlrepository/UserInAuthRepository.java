package com.example.demochatroom.mysqlrepository;

import com.example.demochatroom.mysqlEntities.UserInAuth;
//import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
//

@Repository
public interface UserInAuthRepository extends JpaRepository<UserInAuth,Long> {


    @Query(value = "SELECT u from UserInAuth u where u.username= :username")
    public UserInAuth findByUsername(@Param("username") String username);
}
