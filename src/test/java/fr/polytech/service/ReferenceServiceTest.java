package fr.polytech.service;

import fr.polytech.model.Reference;
import fr.polytech.model.ReferenceDTO;
import fr.polytech.repository.ReferenceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ReferenceServiceTest {

    @Autowired
    private ReferenceRepository referenceRepository;

    @Autowired
    private ReferenceService referenceService;

    @Test
    public void testGetAllReferences() {
        referenceRepository.save(new Reference()); // Save some dummy data
        referenceRepository.save(new Reference());

        List<Reference> result = referenceService.getAllReferences();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetReferenceById() {
        Reference savedReference = referenceRepository.save(new Reference());

        Reference result = referenceService.getReferenceById(savedReference.getId());
        assertNotNull(result);
        assertEquals(savedReference.getId(), result.getId());
    }

    @Test
    public void testGetReferenceByIdWithInvalidId() {
        // Check that an exception is thrown with status code 404
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> referenceService.getReferenceById(UUID.randomUUID()));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    public void testCreateReference() {
        ReferenceDTO referenceDTO = new ReferenceDTO();
        referenceDTO.setContact("contact");
        referenceDTO.setCompanyId(UUID.randomUUID());
        referenceDTO.setContactId(UUID.randomUUID());
        referenceDTO.setContactJobTitle("jobTitle");

        Reference result = referenceService.createReference(referenceDTO);
        assertNotNull(result);
        assertEquals(referenceDTO.getCompanyId(), result.getCompanyId());
    }

    @Test
    public void testCreateReferenceWithMissingAttributes() {
        ReferenceDTO referenceDTO = new ReferenceDTO();
        referenceDTO.setContact("contact");
        referenceDTO.setCompanyId(UUID.randomUUID());
        referenceDTO.setContactId(UUID.randomUUID());

        // Check that an exception is thrown with status code 400
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> referenceService.createReference(referenceDTO));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    public void testUpdateReference() {
        Reference savedReference = referenceRepository.save(new Reference());

        ReferenceDTO reference = new ReferenceDTO();
        reference.setId(savedReference.getId());
        reference.setContact("contact");
        reference.setCompanyId(UUID.randomUUID());
        reference.setContactId(UUID.randomUUID());
        reference.setContactJobTitle("jobTitle");

        Reference result = referenceService.updateReference(reference);
        assertNotNull(result);
        assertEquals(reference.getCompanyId(), result.getCompanyId());
    }

    @Test
    public void testUpdateReferenceWithMissingAttributes() {
        Reference savedReference = referenceRepository.save(new Reference());

        ReferenceDTO reference = new ReferenceDTO();
        reference.setId(savedReference.getId());
        reference.setContact("contact");
        reference.setCompanyId(UUID.randomUUID());
        reference.setContactId(UUID.randomUUID());

        // Check that an exception is thrown with status code 400
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> referenceService.updateReference(reference));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    public void testUpdateReferenceWithInvalidId() {
        ReferenceDTO reference = new ReferenceDTO();
        reference.setId(UUID.randomUUID());
        reference.setContact("contact");
        reference.setCompanyId(UUID.randomUUID());
        reference.setContactId(UUID.randomUUID());
        reference.setContactJobTitle("jobTitle");

        // Check that an exception is thrown with status code 404
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> referenceService.updateReference(reference));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    public void testDeleteReference() {
        Reference savedReference = referenceRepository.save(new Reference());

        referenceService.deleteReference(savedReference.getId());

        // Check that the reference has been deleted
        assertFalse(referenceRepository.findById(savedReference.getId()).isPresent());
    }

}
