package ru.tadanoluka.task1.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tadanoluka.task1.model.enums.PostalMovementType;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "postal_movement")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PostalMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "postal_item_id", nullable = false)
    private PostalItem postalItem;

    @ManyToOne
    @JoinColumn(name = "post_office_id", nullable = false)
    private PostOffice postOffice;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PostalMovementType type;

    @Column(name = "timestamp", nullable = false)
    @Builder.Default
    private Instant timestamp = Instant.now();
}
