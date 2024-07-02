package com.example.news_v2.repository;

import com.example.news_v2.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"roles"})
    public Optional<User> findByUsername(String username);
}
