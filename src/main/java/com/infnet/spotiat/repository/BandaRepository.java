package com.infnet.spotiat.repository;

import com.infnet.spotiat.domain.Banda;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BandaRepository extends JpaRepository<Banda, UUID> {
    @EntityGraph(attributePaths = "musicas")
    Optional<Banda> findById(UUID id);
}
