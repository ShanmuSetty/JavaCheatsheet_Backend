package com.example.dsa_backend;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteRepository favoriteRepository;

    public FavoriteController(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    // GET /api/favorites
    // Returns list of snippet ID strings the user has favorited
    // e.g. ["s1", "a3", "custom_7"]
    @GetMapping
    public ResponseEntity<List<String>> getFavorites(
            @AuthenticationPrincipal String email) {

        List<String> ids = favoriteRepository
                .findByUserEmail(email)
                .stream()
                .map(Favorite::getSnippetId)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ids);
    }

    // POST /api/favorites/{snippetId}
    // Toggles the favorite — adds if not present, removes if already there
    // Returns the updated full list of favorited IDs
    @PostMapping("/{snippetId}")
    public ResponseEntity<List<String>> toggleFavorite(
            @AuthenticationPrincipal String email,
            @PathVariable String snippetId) {

        var existing = favoriteRepository
                .findByUserEmailAndSnippetId(email, snippetId);

        if (existing.isPresent()) {
            // Already favorited → remove it
            favoriteRepository.deleteByUserEmailAndSnippetId(email, snippetId);
        } else {
            // Not favorited → add it
            favoriteRepository.save(new Favorite(email, snippetId));
        }

        // Return updated full list
        List<String> updated = favoriteRepository
                .findByUserEmail(email)
                .stream()
                .map(Favorite::getSnippetId)
                .collect(Collectors.toList());

        return ResponseEntity.ok(updated);
    }
}