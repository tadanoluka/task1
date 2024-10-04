package ru.tadanoluka.task1.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tadanoluka.task1.model.enums.PostalItemStatus;
import ru.tadanoluka.task1.model.enums.PostalItemType;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "postal_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PostalItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PostalItemType type;

    @Column(name = "recipient_name", nullable = false)
    private String recipientName;

    @ManyToOne
    @JoinColumn(name = "recipient_post_office_id", nullable = false)
    private PostOffice recipientPostOffice;

    @Column(name = "recipient_address", nullable = false)
    private String recipientAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PostalItemStatus status;

    @OneToMany(mappedBy = "postalItem", cascade = CascadeType.ALL)
    private List<PostalMovement> movements;
}
