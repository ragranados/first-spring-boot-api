package com.EjemploYoutube.dao;

import com.EjemploYoutube.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenDAO extends JpaRepository<VerificationToken, Integer> {

}
