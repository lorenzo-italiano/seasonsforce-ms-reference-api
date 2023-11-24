package fr.polytech.restcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.polytech.model.Reference;
import fr.polytech.model.ReferenceDTO;
import fr.polytech.service.ReferenceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ReferenceController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class ReferenceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReferenceService referenceService;

    @Test
    @WithMockUser
    public void testGetAllReferences() throws Exception {
        given(referenceService.getAllReferences()).willReturn(Arrays.asList(new Reference(), new Reference()));
        mockMvc.perform(get("/api/v1/reference/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testGetReferenceById() throws Exception {
        UUID id = UUID.randomUUID();
        given(referenceService.getReferenceById(id)).willReturn(new Reference());
        mockMvc.perform(get("/api/v1/reference/" + id))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testCreateReference() throws Exception {
        ReferenceDTO referenceDTO = new ReferenceDTO();
        // Set properties for referenceDTO as needed
        referenceDTO.setContact("contact");
        referenceDTO.setCompanyId(UUID.randomUUID());
        referenceDTO.setContactId(UUID.randomUUID());
        referenceDTO.setContactJobTitle("jobTitle");

        Reference reference = new Reference();
        // Set properties for reference as needed
        reference.setContact(referenceDTO.getContact());
        reference.setCompanyId(referenceDTO.getCompanyId());
        reference.setContactId(referenceDTO.getContactId());
        reference.setContactJobTitle(referenceDTO.getContactJobTitle());

        given(referenceService.createReference(any(ReferenceDTO.class))).willReturn(reference);

        mockMvc.perform(post("/api/v1/reference/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(referenceDTO))
                        .with(csrf()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    public void testUpdateReference() throws Exception {
        ReferenceDTO referenceDTO = new ReferenceDTO();
        // Set properties for referenceDTO as needed
        referenceDTO.setContact("contact");
        referenceDTO.setCompanyId(UUID.randomUUID());
        referenceDTO.setContactId(UUID.randomUUID());
        referenceDTO.setContactJobTitle("jobTitle");

        Reference reference = new Reference();
        // Set properties for reference as needed
        reference.setContact(referenceDTO.getContact());
        reference.setCompanyId(referenceDTO.getCompanyId());
        reference.setContactId(referenceDTO.getContactId());
        reference.setContactJobTitle(referenceDTO.getContactJobTitle());

        given(referenceService.updateReference(any(ReferenceDTO.class))).willReturn(reference);

        mockMvc.perform(put("/api/v1/reference/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(referenceDTO))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testDeleteReference() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(delete("/api/v1/reference/" + id).with(csrf()))
                .andExpect(status().isOk());
    }
}