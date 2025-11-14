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

    public TagService(
        TagRepository tagRepository,
        TaskRepository taskRepository
    ) {
        this.tagRepository = tagRepository;
        this.taskRepository = taskRepository;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag findById(Long id) {
        return tagRepository
            .findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("Etiqueta não encontrada.")
            );
    }

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public Tag updateTag(Tag tag) {
        Tag existente = tagRepository
            .findById(tag.getId())
            .orElseThrow(() ->
                new ResourceNotFoundException("Tag não encontrada")
            );
        existente.setName(tag.getName());
        return tagRepository.save(existente);
    }

    public void deleteTag(Long id) {
        Tag tag = findById(id);
        tagRepository.delete(tag);
    }

    @Transactional
    public Tag addTaskToTag(Long tagId, Long taskId) {
        Tag tag = tagRepository
            .findById(tagId)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Tag não encontrada com id " + tagId
                )
            );
        Task task = taskRepository
            .findById(taskId)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Tarefa não encontrada com id " + taskId
                )
            );

        tag.getTasks().add(task);
        task.getTags().add(tag);

        return tagRepository.save(tag);
    }

    @Transactional
    public void removeTaskFromTag(Long tagId, Long taskId) {
        Task task = taskRepository
            .findById(taskId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Tarefa não encontrada.")
            );
        Tag tag = findById(tagId);

        if (!task.getTags().contains(tag)) {
            throw new IllegalArgumentException(
                "Essa etiqueta não está associada a esta tarefa."
            );
        }

        task.getTags().remove(tag);
        taskRepository.save(task);
    }
}
