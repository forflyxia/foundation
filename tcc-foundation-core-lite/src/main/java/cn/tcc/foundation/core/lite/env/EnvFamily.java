package cn.tcc.foundation.core.lite.env;

/**
 * @author shawn.xiao
 * @date 2018/1/2
 */
public enum EnvFamily {
    LOCAL("Local Development environment"),

    FAT("Feature Acceptance Test environment"),

    UAT("User Acceptance Test environment"),

    PROD("Production environment"),

    UNKNOWN("Unknown environment");

    private String description;

    EnvFamily(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
