package fr.polytech.service;

import fr.polytech.model.CompanyDTO;
import fr.polytech.model.DetailedReferenceDTO;
import fr.polytech.model.Reference;
import fr.polytech.model.ReferenceDTO;
import fr.polytech.model.user.RecruiterDTO;
import fr.polytech.repository.ReferenceRepository;
import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

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

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

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
     * @throws HttpClientErrorException If the reference is not found.
     */
    public Reference getReferenceById(UUID id) throws HttpClientErrorException {
        Reference reference = referenceRepository.findById(id).orElse(null);
        logger.info("Getting reference with id " + id);

        if (reference == null) {
            logger.error("Error while getting a reference: reference not found");
            // If the reference is not found, throw an exception
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Reference not found");
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

        checkAttributes(reference);

        Reference referenceReturn = new Reference();
        referenceReturn.setContact(reference.getContact());
        referenceReturn.setCompanyId(reference.getCompanyId());
        referenceReturn.setContactId(reference.getContactId());
        referenceReturn.setContactJobTitle(reference.getContactJobTitle());

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
    public Reference updateReference(ReferenceDTO reference) throws HttpClientErrorException {
        logger.info("Starting the update of a reference");

        checkAttributes(reference);

        Reference storedReference = referenceRepository.findById(reference.getId()).orElse(null);

        if (storedReference == null) {
            logger.error("Error while updating a reference: reference not found");
            // If the reference is not found, throw an exception
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Reference not found");
        }

        storedReference.setContact(reference.getContact());
        storedReference.setCompanyId(reference.getCompanyId());
        storedReference.setContactId(reference.getContactId());
        storedReference.setContactJobTitle(reference.getContactJobTitle());

        // Save the reference in the database and return it
        return referenceRepository.save(storedReference);
    }

    /**
     * Delete a reference.
     *
     * @param id Reference id.
     * @throws NotFoundException If the reference is not found.
     */
    public void deleteReference(UUID id) throws HttpClientErrorException {
        logger.info("Starting the deletion of a reference");
        Reference reference = referenceRepository.findById(id).orElse(null);

        if (reference == null) {
            logger.error("Error while deleting a reference: reference not found");
            // If the reference is not found, throw an exception
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Reference not found");
        }

        referenceRepository.delete(reference);
    }

    /**
     * Get all references sent by a user.
     *
     * @param id User id (contact id).
     * @return List of references sent by the user.
     */
    public List<Reference> getReferenceByUserId(UUID id) {
        logger.info("Getting reference with contact id " + id);
        return referenceRepository.findByContactId(id);
    }

    /**
     * Check if the reference has all the required attributes.
     *
     * @param reference Reference to check.
     * @throws HttpClientErrorException If the reference does not have all the required attributes.
     */
    private void checkAttributes(ReferenceDTO reference) throws HttpClientErrorException {
        if (reference.getContact() == null || reference.getCompanyId() == null || reference.getContactId() == null || reference.getContactJobTitle() == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing attributes");
        }
    }

    /**
     * Get a detailed reference by id.
     * @param id Reference id.
     * @param token Token of the user.
     * @return Detailed reference with the specified id.
     */
    public DetailedReferenceDTO getDetailedReferenceById(UUID id, String token) {
        Reference referenceById = getReferenceById(id);

        if (referenceById == null) {
            logger.error("Error while getting a reference: reference not found");
            // If the reference is not found, throw an exception
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Reference not found");
        }

        DetailedReferenceDTO detailedReferenceDTO = new DetailedReferenceDTO();

        detailedReferenceDTO.setId(referenceById.getId());
        detailedReferenceDTO.setContactJobTitle(referenceById.getContactJobTitle());
        detailedReferenceDTO.setContactName(referenceById.getContact());

        CompanyDTO companyDTO = companyService.getCompanyById(referenceById.getCompanyId(), token);

        detailedReferenceDTO.setCompany(companyDTO);

        RecruiterDTO recruiterDTO = userService.getRecruiterById(referenceById.getContactId(), token);

        detailedReferenceDTO.setContact(recruiterDTO);

        return detailedReferenceDTO;
    }
}
