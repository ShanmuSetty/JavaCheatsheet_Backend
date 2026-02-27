package com.example.dsa_backend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUserEmail(String userEmail);

    Optional<Favorite> findByUserEmailAndSnippetId(String userEmail, String snippetId);

    void deleteByUserEmailAndSnippetId(String userEmail, String snippetId);
}