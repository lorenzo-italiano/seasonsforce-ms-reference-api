package fr.polytech.restcontroller;

import fr.polytech.annotation.IsAdmin;
import fr.polytech.annotation.IsCandidate;
import fr.polytech.model.Reference;
import fr.polytech.model.ReferenceDTO;
import fr.polytech.service.ReferenceService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Produces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @IsAdmin
    @Produces(MediaType.APPLICATION_JSON_VALUE)
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
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reference> getReferenceById(@PathVariable("id") UUID id) {
        try {
            return ResponseEntity.ok(referenceService.getReferenceById(id));
        } catch (HttpClientErrorException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get reference by user id.
     *
     * @param id User id (contact id).
     * @return List of references with the specified user id.
     */
    @GetMapping("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Reference>> getReferenceByUserId(@PathVariable("id") UUID id) {
        try {
            return ResponseEntity.ok(referenceService.getReferenceByUserId(id));
        } catch (HttpClientErrorException e) {
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
    @IsCandidate
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reference> createReference(@RequestBody Reference reference) {
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
    @IsCandidate
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
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
    @IsCandidate
    @Produces(MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Boolean> deleteReference(@PathVariable("id") UUID id) {
        try {

            referenceService.deleteReference(id);
            logger.info("Completed deletion of a reference");
            logger.debug("Deleted reference with id " + id);
            return ResponseEntity.ok(true);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(false, e.getStatusCode());
        }
    }
}
