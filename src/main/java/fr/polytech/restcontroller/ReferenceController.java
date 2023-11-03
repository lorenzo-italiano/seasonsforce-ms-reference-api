package fr.polytech.restcontroller;

import fr.polytech.model.Reference;
import fr.polytech.model.ReferenceDTO;
import fr.polytech.service.ReferenceService;
import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reference")
public class ReferenceController {

    /**
     * Initialize the logger.
     */
    private final Logger logger = LoggerFactory.getLogger(ReferenceController.class);

    @Autowired
    private ReferenceService referenceService;

    /**
     * Get all references.
     *
     * @return List of all references.
     */
    @GetMapping("/")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<List<Reference>> getAllReferences() {
        return ResponseEntity.ok(referenceService.getAllReferences());
    }

    /**
     * Get reference by id.
     *
     * @param id Reference id.
     * @return Reference with the specified id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Reference> getReferenceById(@PathVariable("id") UUID id) {
        try {
            return ResponseEntity.ok(referenceService.getReferenceById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create a reference.
     *
     * @param reference Reference to create.
     * @return Created reference.
     */
    @PostMapping("/")
    public ResponseEntity<Reference> createReference(@RequestBody ReferenceDTO reference) {
        try {
            Reference createdReference = referenceService.createReference(reference);
            logger.info("Completed creation of a reference");
            logger.debug("Created new reference: " + createdReference.toString());
            return ResponseEntity.ok(createdReference);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Update a reference.
     *
     * @param reference Reference to update.
     * @return Updated reference.
     */
    @PutMapping("/")
    public ResponseEntity<Reference> updateReference(@RequestBody ReferenceDTO reference) {
        try {
            Reference updatedReference = referenceService.updateReference(reference);
            logger.info("Completed update of a reference");
            logger.debug("Updated reference: " + updatedReference.toString());
            return ResponseEntity.ok(updatedReference);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (HttpClientErrorException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Delete a reference.
     *
     * @param id Reference id.
     * @return True if the reference was deleted, false otherwise.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteReference(@PathVariable("id") UUID id) {
        try {
            referenceService.deleteReference(id);
            logger.info("Completed deletion of a reference");
            logger.debug("Deleted reference with id " + id);
            return ResponseEntity.ok(true);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
