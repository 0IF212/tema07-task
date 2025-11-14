package br.ifsp.task.controller;

import br.ifsp.task.dto.TagDTO;
import br.ifsp.task.model.Tag;
import br.ifsp.task.service.TagService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<TagDTO>> getAllTags() {
        List<TagDTO> dtos = tagService
            .getAllTags()
            .stream()
            .map(tag -> new TagDTO(tag.getId(), tag.getName()))
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> getTagById(@PathVariable Long id) {
        Tag tag = tagService.findById(id);
        TagDTO dto = new TagDTO(tag.getId(), tag.getName());
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<TagDTO> createTag(@Valid @RequestBody TagDTO tagDTO) {
        Tag tag = new Tag();
        tag.setName(tagDTO.getName());
        Tag saved = tagService.createTag(tag);
        TagDTO dto = new TagDTO(saved.getId(), saved.getName());
        return ResponseEntity.status(201).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDTO> updateTag(
        @PathVariable Long id,
        @Valid @RequestBody TagDTO tagDTO
    ) {
        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(tagDTO.getName());
        Tag updated = tagService.updateTag(tag);
        TagDTO dto = new TagDTO(updated.getId(), updated.getName());
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{tagId}/tasks/{taskId}")
    public ResponseEntity<Void> addTaskToTag(
        @PathVariable Long tagId,
        @PathVariable Long taskId
    ) {
        tagService.addTaskToTag(tagId, taskId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{tagId}/tasks/{taskId}")
    public ResponseEntity<Void> removeTaskFromTag(
        @PathVariable Long tagId,
        @PathVariable Long taskId
    ) {
        tagService.removeTaskFromTag(tagId, taskId);
        return ResponseEntity.noContent().build();
    }
}
