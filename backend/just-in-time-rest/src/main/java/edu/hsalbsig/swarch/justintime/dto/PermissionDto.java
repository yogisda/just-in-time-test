package edu.hsalbsig.swarch.justintime.dto;

public class PermissionDto {
    private String objectType;
    private int objectId;
    private String permissionType;
    private boolean hasPermission;
    
    public PermissionDto() {
    }

    public PermissionDto(String objectType, int objectId, String permissionType, boolean hasPermission) {
        this.objectType = objectType;
        this.objectId = objectId;
        this.permissionType = permissionType;
        this.hasPermission = hasPermission;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public String getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }

    public boolean isHasPermission() {
        return hasPermission;
    }

    public void setHasPermission(boolean hasPermission) {
        this.hasPermission = hasPermission;
    }
}
