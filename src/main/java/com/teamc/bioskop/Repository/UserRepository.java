package com.teamc.bioskop.Repository;

import com.teamc.bioskop.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{



    @Query("SELECT u FROM User u WHERE CONCAT(u.userId, ' ', u.username, ' ', u.emailId, ' ') LIKE %?1%")
    public List<User> search(String keyword);






}

