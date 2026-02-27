package com.example.dsa_backend;

import com.example.dsa_backend.dto.SnippetRequest;
import com.example.dsa_backend.dto.SnippetResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/snippets")
public class CustomSnippetController {

    private final CustomSnippetRepository snippetRepository;

    public CustomSnippetController(CustomSnippetRepository snippetRepository) {
        this.snippetRepository = snippetRepository;
    }

    // GET /api/snippets
    // Returns all custom snippets belonging to the logged-in user
    @GetMapping
    public ResponseEntity<List<SnippetResponse>> getSnippets(
            @AuthenticationPrincipal String email) {

        List<SnippetResponse> snippets = snippetRepository
                .findByUserEmail(email)
                .stream()
                .map(SnippetResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(snippets);
    }

    // POST /api/snippets
    // Creates a new custom snippet, returns updated full list
    @PostMapping
    public ResponseEntity<List<SnippetResponse>> createSnippet(
            @AuthenticationPrincipal String email,
            @RequestBody SnippetRequest request) {

        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new RuntimeException("Title is required");
        }
        if (request.getCode() == null || request.getCode().isBlank()) {
            throw new RuntimeException("Code is required");
        }

        CustomSnippet snippet = new CustomSnippet(
                email,
                request.getTitle(),
                request.getTag() != null ? request.getTag() : "custom",
                request.getCode(),
                request.getDesc()
        );

        snippetRepository.save(snippet);

        // Return full updated list
        List<SnippetResponse> updated = snippetRepository
                .findByUserEmail(email)
                .stream()
                .map(SnippetResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(updated);
    }

    // DELETE /api/snippets/{id}
    // Deletes snippet by DB id (frontend sends "custom_5", we strip the prefix)
    @DeleteMapping("/{snippetId}")
    public ResponseEntity<List<SnippetResponse>> deleteSnippet(
            @AuthenticationPrincipal String email,
            @PathVariable String snippetId) {

        // Strip "custom_" prefix the frontend adds, get the numeric DB id
        Long dbId;
        try {
            String raw = snippetId.startsWith("custom_")
                    ? snippetId.substring(7)
                    : snippetId;
            dbId = Long.parseLong(raw);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid snippet id: " + snippetId);
        }

        // Only delete if it belongs to this user
        snippetRepository.findById(dbId).ifPresent(snippet -> {
            if (snippet.getUserEmail().equals(email)) {
                snippetRepository.deleteById(dbId);
            }
        });

        // Return updated list
        List<SnippetResponse> updated = snippetRepository
                .findByUserEmail(email)
                .stream()
                .map(SnippetResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(updated);
    }
}