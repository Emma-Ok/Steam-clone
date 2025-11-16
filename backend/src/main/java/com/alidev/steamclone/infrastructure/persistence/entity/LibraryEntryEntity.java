package com.alidev.steamclone.infrastructure.persistence.entity;

import com.alidev.steamclone.domain.model.LibraryEntry;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "library_entries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibraryEntryEntity {

    @Id
    private UUID id;

    @Column(nullable = false, name = "user_id")
    private UUID userId;

    @Column(nullable = false, name = "game_id")
    private UUID gameId;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "library_entry_statuses", joinColumns = @JoinColumn(name = "entry_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private Set<LibraryEntry.Status> statuses = new HashSet<>();

    private int progressMinutes;

    @Column(nullable = false)
    private Instant addedAt;
}
