package com.example.dsa_backend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CustomSnippetRepository extends JpaRepository<CustomSnippet, Long> {

    List<CustomSnippet> findByUserEmail(String userEmail);
}