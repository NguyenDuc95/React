package com.kbfina.management.repository;

import com.kbfina.management.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    @Query(value = "SELECT * FROM USERS u WHERE u.username = :userName", nativeQuery = true)
    User findByUserName(@Param("userName") String userName);

}
