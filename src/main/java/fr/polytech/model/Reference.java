package fr.polytech.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "reference", schema = "public")
public class Reference {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String message;

    private UUID companyId;

    private UUID senderId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public void setCompanyId(UUID companyId) {
        this.companyId = companyId;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }

    @Override
    public String toString() {
        return "Reference{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", companyId=" + companyId +
                ", senderId=" + senderId +
                '}';
    }
}
