package ru.tadanoluka.task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tadanoluka.task1.model.PostOffice;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostOfficeRepository extends JpaRepository<PostOffice, UUID> {

    boolean existsByPostcode(String postcode);

    Optional<PostOffice> findByPostcode(String postcode);
}
