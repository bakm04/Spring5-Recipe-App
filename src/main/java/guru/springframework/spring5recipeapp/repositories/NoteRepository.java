package guru.springframework.spring5recipeapp.repositories;

import guru.springframework.spring5recipeapp.domain.Notes;
import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends CrudRepository<Notes, Long> {
}
