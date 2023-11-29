package fr.polytech.repository;

import fr.polytech.model.Reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference, UUID> {

    /**
     * Find all references by contact id.
     *
     * @param id Contact id.
     * @return List of references.
     */
    @Query("SELECT r FROM Reference r WHERE r.contactId = :id")
    List<Reference> findByContactId(@Param("id") UUID id);
}
