package edu.hsalbsig.swarch.justintime.dto;

public class CredentialsDto implements IValidateable {
    private String email;
    private String password;
    
    public CredentialsDto() {
        
    }
    
    public CredentialsDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void validate() throws DataValidationException {
        if (email == null || email.isBlank()) {
            throw new DataValidationException();
        }
        
        if (password == null || password.isBlank()) {
            throw new DataValidationException();
        }
    }
}
