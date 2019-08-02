package edu.hsalbsig.swarch.justintime.dto;

public class EmployeeRegistrationDto extends EmployeeDto implements IValidateable {
    private String password;
    
    public EmployeeRegistrationDto() {
        
    }

    public EmployeeRegistrationDto(String name, String email, float hoursPerDay, String password) {
        super(null, name, email, hoursPerDay);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    } 
    
    public void validate() throws DataValidationException {
        if (password == null || password.isBlank()) {
            throw new DataValidationException();
        }
        
        super.validate();
    }
}
