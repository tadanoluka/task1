package ru.tadanoluka.task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tadanoluka.task1.model.PostalItem;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostalItemRepository extends JpaRepository<PostalItem, UUID> {

    @Query("""
        FROM PostalItem AS item
        JOIN FETCH item.movements AS move
        WHERE item.id = :id
        ORDER BY move.timestamp ASC
    """)
    Optional<PostalItem> findPostalItemByIdOrderByMovementsTimestamp(UUID id);
}
