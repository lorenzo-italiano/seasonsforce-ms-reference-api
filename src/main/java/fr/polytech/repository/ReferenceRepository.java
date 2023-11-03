package fr.polytech.repository;

import fr.polytech.model.Reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference, UUID> { }
