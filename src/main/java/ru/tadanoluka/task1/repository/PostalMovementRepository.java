package ru.tadanoluka.task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tadanoluka.task1.model.PostalItem;
import ru.tadanoluka.task1.model.PostalMovement;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostalMovementRepository extends JpaRepository<PostalMovement, UUID> {

    Optional<PostalMovement> findFirstByPostalItemOrderByTimestamp(PostalItem postalItem);
}
