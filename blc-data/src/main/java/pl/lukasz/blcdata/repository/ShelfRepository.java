package pl.lukasz.blcdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.lukasz.blcdata.entity.Shelf;

import java.util.List;
import java.util.Optional;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {
    List<Shelf> findAllByUser_Username(String username);

    Optional<Shelf> findByNameAndUser_Username(String name, String username);

    @Query("SELECT s.name, COUNT(s) FROM Shelf s JOIN s.books b WHERE b.id = :bookId GROUP BY s.name")
    List<Object[]> countShelvesByBookId(@Param("bookId") Long bookId);

    List<Shelf> findAllByUserId(Long userId);
}