package edu.hsalbsig.swarch.justintime.dto;

public class EmployeeSimpleDto extends EmployeeDto {
    private int roleId;
    
    public EmployeeSimpleDto() {
        
    }

    public EmployeeSimpleDto(Integer employeeId, String name, String email, float hoursPerDay, int roleId) {
        super(employeeId, name, email, hoursPerDay);
        this.roleId = roleId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
