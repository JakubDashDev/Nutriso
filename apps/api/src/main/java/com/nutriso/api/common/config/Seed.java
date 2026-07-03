package com.nutriso.api.common.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.nutriso.api.user.enums.Role;
import com.nutriso.api.user.model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class Seed implements CommandLineRunner {

  @PersistenceContext
  private EntityManager entityManager;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    boolean exists = entityManager
      .createQuery("select count(u) from User u where u.email = :email", Long.class)
      .setParameter("email", "user@user.com")
      .getSingleResult() > 0;

    if(exists) return;

    User user = new User("user@user.com", "Test User", passwordEncoder.encode("password"), Role.USER);
    entityManager.persist(user);
  }
}
