package fr.polytech.model;

import fr.polytech.model.user.RecruiterDTO;

import java.util.UUID;

public class DetailedReferenceDTO {
    private UUID id;
    private String contactName;
    private CompanyDTO company;
    private RecruiterDTO contact;
    private String contactJobTitle;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    public RecruiterDTO getContact() {
        return contact;
    }

    public void setContact(RecruiterDTO contact) {
        this.contact = contact;
    }

    public String getContactJobTitle() {
        return contactJobTitle;
    }

    public void setContactJobTitle(String contactJobTitle) {
        this.contactJobTitle = contactJobTitle;
    }
}

