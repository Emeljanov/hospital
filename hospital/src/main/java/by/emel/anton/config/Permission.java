package by.emel.anton.config;

public enum Permission {

    DEVELOP_READ("delelop:read"),
    DEVELOP_WRITE("develop:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
