package com.example.backend.DTO;

import java.util.UUID;

public class ProjectDTO {
    private UUID id;
    private String title;
    private String details;
    private String iamge;

    private UUID getId (){
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIamge() {
        return iamge;
    }

    public void setIamge(String iamge) {
        this.iamge = iamge;
    }

}
