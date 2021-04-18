package com.EjemploYoutube.dao;

import com.EjemploYoutube.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDAO extends JpaRepository<Role, Integer> {
}
