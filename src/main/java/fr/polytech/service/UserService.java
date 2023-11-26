package fr.polytech.service;

import fr.polytech.model.CompanyDTO;
import fr.polytech.model.user.RecruiterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private ApiService apiService;

    /**
     * Get a Job Category by id.
     */
    public RecruiterDTO getRecruiterById(UUID id, String token) throws HttpClientErrorException {
        String uri = System.getenv("USER_API_URI") + "/" + id;
        return apiService.makeApiCall(uri, HttpMethod.GET, RecruiterDTO.class, token);
    }

}
