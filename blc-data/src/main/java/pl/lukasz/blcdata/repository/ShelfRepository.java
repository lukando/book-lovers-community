package pl.lukasz.blcdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lukasz.blcdata.entity.Shelf;

import java.util.List;
import java.util.Optional;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {
    List<Shelf> findAllByUser_Username(String username);

    Optional<Shelf> findByNameAndUser_Username(String name, String username);
}