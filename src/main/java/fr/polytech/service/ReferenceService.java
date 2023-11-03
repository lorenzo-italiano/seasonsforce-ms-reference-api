package fr.polytech.service;

import fr.polytech.model.Reference;
import fr.polytech.model.ReferenceDTO;
import fr.polytech.repository.ReferenceRepository;
import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReferenceService {

    /**
     * Initialize the logger.
     */
    private final Logger logger = LoggerFactory.getLogger(ReferenceService.class);

    @Autowired
    private ReferenceRepository referenceRepository;

    /**
     * Get all references.
     *
     * @return List of all references.
     */
    public List<Reference> getAllReferences() {
        logger.info("Getting all references");
        return referenceRepository.findAll();
    }

    /**
     * Get reference by id.
     *
     * @param id Reference id.
     * @return Reference with the specified id.
     * @throws NotFoundException If the reference is not found.
     */
    public Reference getReferenceById(UUID id) throws NotFoundException {
        Reference reference = referenceRepository.findById(id).orElse(null);
        logger.info("Getting reference with id " + id);

        if (reference == null) {
            logger.error("Error while getting a reference: reference not found");
            // If the reference is not found, throw an exception
            throw new NotFoundException("Reference not found");
        }

        logger.debug("Returning reference with id " + id);
        return reference;
    }

    /**
     * Create a reference.
     *
     * @param reference Reference to create.
     * @return Created reference.
     */
    public Reference createReference(ReferenceDTO reference) {
        logger.info("Starting the creation of a reference");

        Reference referenceReturn = new Reference();
        referenceReturn.setMessage(reference.getMessage());
        referenceReturn.setCompanyId(reference.getCompanyId());
        referenceReturn.setSenderId(reference.getSenderId());

        // Save the reference in the database and return it
        return referenceRepository.save(referenceReturn);
    }

    /**
     * Update a reference.
     *
     * @param reference Reference to update.
     * @return Updated reference.
     * @throws NotFoundException If the reference is not found.
     */
    public Reference updateReference(ReferenceDTO reference) throws NotFoundException {
        logger.info("Starting the update of a reference");
        Reference storedReference = referenceRepository.findById(reference.getId()).orElse(null);

        if (storedReference == null) {
            logger.error("Error while updating a reference: reference not found");
            // If the reference is not found, throw an exception
            throw new NotFoundException("Reference not found");
        }

        storedReference.setMessage(reference.getMessage());
        storedReference.setCompanyId(reference.getCompanyId());
        storedReference.setSenderId(reference.getSenderId());

        // Save the reference in the database and return it
        return referenceRepository.save(storedReference);
    }

    /**
     * Delete a reference.
     *
     * @param id Reference id.
     * @throws NotFoundException If the reference is not found.
     */
    public void deleteReference(UUID id) throws NotFoundException {
        logger.info("Starting the deletion of a reference");
        Reference reference = referenceRepository.findById(id).orElse(null);

        if (reference == null) {
            logger.error("Error while deleting a reference: reference not found");
            // If the reference is not found, throw an exception
            throw new NotFoundException("Reference not found");
        }

        referenceRepository.delete(reference);
    }
}
