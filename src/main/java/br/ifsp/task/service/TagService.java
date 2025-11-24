package br.ifsp.task.service;

import br.ifsp.task.exception.ResourceNotFoundException;
import br.ifsp.task.model.Tag;
import br.ifsp.task.model.Task;
import br.ifsp.task.repository.TagRepository;
import br.ifsp.task.repository.TaskRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final TaskRepository taskRepository;
    private final UserService userService;

    public TagService(
        TagRepository tagRepository,
        TaskRepository taskRepository,
        UserService userService
    ) {
        this.tagRepository = tagRepository;
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public List<Tag> getAllTags(Long userId) {
        return tagRepository.findByOwnerId(userId);
    }

    public Tag findById(Long id, Long userId) {
        return tagRepository
            .findByIdAndOwnerId(id, userId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Etiqueta não encontrada")
            );
    }

    public Tag createTag(Tag tag, Long userId) {
        tag.setOwner(userService.findById(userId));
        return tagRepository.save(tag);
    }

    public Tag updateTag(Tag tag, Long userId) {
        Tag existente = tagRepository
            .findByIdAndOwnerId(tag.getId(), userId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Etiqueta não encontrada")
            );

        existente.setName(tag.getName());
        return tagRepository.save(existente);
    }

    public void deleteTag(Long id, Long userId) {
        Tag tag = findById(id, userId);
        tagRepository.delete(tag);
    }

    @Transactional
    public Tag addTaskToTag(Long tagId, Long taskId, Long userId) {
        Tag tag = tagRepository
            .findByIdAndOwnerId(tagId, userId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Etiqueta não encontrada")
            );

        Task task = taskRepository
            .findByIdAndOwnerId(taskId, userId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Tarefa não encontrada")
            );

        tag.getTasks().add(task);
        task.getTags().add(tag);

        return tagRepository.save(tag);
    }

    @Transactional
    public void removeTaskFromTag(Long tagId, Long taskId, Long userId) {
        Task task = taskRepository
            .findByIdAndOwnerId(taskId, userId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Tarefa não encontrada")
            );

        Tag tag = tagRepository
            .findByIdAndOwnerId(tagId, userId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Etiqueta não encontrada")
            );

        if (!task.getTags().contains(tag)) {
            throw new IllegalArgumentException(
                "Essa etiqueta não está associada a esta tarefa."
            );
        }

        task.getTags().remove(tag);
        taskRepository.save(task);
    }
}
