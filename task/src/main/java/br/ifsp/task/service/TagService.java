package br.ifsp.task.service;

import br.ifsp.task.exception.ResourceNotFoundException;
import br.ifsp.task.model.Tag;
import br.ifsp.task.model.Task;
import br.ifsp.task.repository.TagRepository;
import br.ifsp.task.service.adapter.TaskServiceAdapter;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final TaskServiceAdapter taskAdapter;
    private final UserService userService;

    public TagService(
        TagRepository tagRepository,
        TaskServiceAdapter taskAdapter,
        UserService userService
    ) {
        this.tagRepository = tagRepository;
        this.taskAdapter = taskAdapter;
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
    public Tag addTaskToTag(Long tagId, Long taskId, Long userId, String token) {
        Tag tag = tagRepository
            .findByIdAndOwnerId(tagId, userId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Etiqueta não encontrada")
            );

        // Valida que a task existe (mas não podemos modificar a task no microserviço)
        // Por enquanto, apenas validamos
        taskAdapter.buscarPorIdParaValidacao(taskId, userId, token);

        // Nota: A associação tag-task precisa ser gerenciada de outra forma
        // quando Task está no microserviço. Por enquanto, apenas validamos.
        return tagRepository.save(tag);
    }

    @Transactional
    public void removeTaskFromTag(Long tagId, Long taskId, Long userId, String token) {
        // Valida que a task existe
        taskAdapter.buscarPorIdParaValidacao(taskId, userId, token);

        Tag tag = tagRepository
            .findByIdAndOwnerId(tagId, userId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Etiqueta não encontrada")
            );

        // Nota: A remoção da associação precisa ser gerenciada de outra forma
        // quando Task está no microserviço
    }
}
