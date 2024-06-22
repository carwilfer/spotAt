package com.infnet.spotiat.repository;

import com.infnet.spotiat.domain.Plano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlanoRepository extends JpaRepository<Plano, UUID> {
    List<Plano> findByAtivoTrue();
}
