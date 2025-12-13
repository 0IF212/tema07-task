package br.ifsp.task.controller;

import br.ifsp.task.dto.TagDTO;
import br.ifsp.task.model.Tag;
import br.ifsp.task.service.TagService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<TagDTO>> getAllTags(
        @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");

        List<TagDTO> dtos = tagService
            .getAllTags(userId)
            .stream()
            .map(tag -> new TagDTO(tag.getId(), tag.getName()))
            .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> getTagById(
        @PathVariable Long id,
        @AuthenticationPrincipal Jwt jwt
    ) {
        Tag tag = tagService.findById(id, jwt.getClaim("userId"));
        return ResponseEntity.ok(new TagDTO(tag.getId(), tag.getName()));
    }

    @PostMapping
    public ResponseEntity<TagDTO> createTag(
        @Valid @RequestBody TagDTO tagDTO,
        @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");

        Tag tag = new Tag();
        tag.setName(tagDTO.getName());

        Tag saved = tagService.createTag(tag, userId);
        return ResponseEntity.status(201).body(
            new TagDTO(saved.getId(), saved.getName())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDTO> updateTag(
        @PathVariable Long id,
        @Valid @RequestBody TagDTO tagDTO,
        @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");

        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(tagDTO.getName());

        Tag updated = tagService.updateTag(tag, userId);
        return ResponseEntity.ok(
            new TagDTO(updated.getId(), updated.getName())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(
        @PathVariable Long id,
        @AuthenticationPrincipal Jwt jwt
    ) {
        tagService.deleteTag(id, jwt.getClaim("userId"));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{tagId}/tasks/{taskId}")
    public ResponseEntity<Void> addTaskToTag(
        @PathVariable Long tagId,
        @PathVariable Long taskId,
        @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");
        String token = jwt.getTokenValue();
        tagService.addTaskToTag(tagId, taskId, userId, token);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{tagId}/tasks/{taskId}")
    public ResponseEntity<Void> removeTaskFromTag(
        @PathVariable Long tagId,
        @PathVariable Long taskId,
        @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");
        String token = jwt.getTokenValue();
        tagService.removeTaskFromTag(tagId, taskId, userId, token);
        return ResponseEntity.noContent().build();
    }
}
