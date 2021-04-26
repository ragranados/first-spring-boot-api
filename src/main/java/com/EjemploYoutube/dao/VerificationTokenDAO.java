package com.EjemploYoutube.dao;

import com.EjemploYoutube.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenDAO extends JpaRepository<VerificationToken, Integer> {

    @Query("FROM VerificationToken WHERE token =?1")
    VerificationToken findByUUID(@Param("token") String token);

}
