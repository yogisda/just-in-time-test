package edu.hsalbsig.swarch.justintime.dto;

public class RoleDto {
    private Integer id;
    private String description;
    
    public RoleDto() {
        
    }
    
    public RoleDto(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
