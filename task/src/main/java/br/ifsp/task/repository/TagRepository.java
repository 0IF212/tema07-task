package br.ifsp.task.repository;

import br.ifsp.task.model.Tag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByOwnerId(Long userId);

    Optional<Tag> findByIdAndOwnerId(Long tagId, Long userId);

    Optional<Tag> findByNameIgnoreCaseAndOwnerId(String name, Long userId);
}
