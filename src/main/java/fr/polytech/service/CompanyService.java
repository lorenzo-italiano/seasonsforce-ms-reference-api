package fr.polytech.service;

import fr.polytech.model.CompanyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

@Service
public class CompanyService {

    private final Logger logger = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    private ApiService apiService;

    /**
     * Get company by id.
     *
     * @param id    Company id.
     * @param token Token of the user.
     * @return Company with the specified id.
     * @throws HttpClientErrorException If the company is not found.
     */
    public CompanyDTO getCompanyById(UUID id, String token) throws HttpClientErrorException {
        String uri = System.getenv("COMPANY_API_URI") + "/" + id;
        return apiService.makeApiCall(uri, HttpMethod.GET, CompanyDTO.class, token);
    }
}
