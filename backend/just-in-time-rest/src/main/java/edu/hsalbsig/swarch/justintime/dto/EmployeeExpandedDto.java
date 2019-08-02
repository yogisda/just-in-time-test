package edu.hsalbsig.swarch.justintime.dto;

public class EmployeeExpandedDto extends EmployeeDto {
    private RoleDto role;

    public EmployeeExpandedDto() {
    }

    public EmployeeExpandedDto(Integer employeeId, String name, String email, float hoursPerDay, RoleDto role) {
        super(employeeId, name, email, hoursPerDay);
        this.role = role;
    }

    public RoleDto getRole() {
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = role;
    }
}
