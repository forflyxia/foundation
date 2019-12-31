package cn.tcc.foundation.core.lite.env;

/**
 * @author shawn.xiao
 * @date 2018/1/2
 */
public enum Env {
    LOCAL("Local Development environment", EnvFamily.LOCAL),

    DEV("Development environment", EnvFamily.FAT),

    FWS("Feature Web Service Test environment", EnvFamily.FAT),

    FAT("Feature Acceptance Test environment", EnvFamily.FAT),

    FAT1("Feature Acceptance Test environment", EnvFamily.FAT),

    FAT2("Feature Acceptance Test environment", EnvFamily.FAT),

    LPT("Load and Performance Test environment", EnvFamily.FAT),

    UAT("User Acceptance Test environment", EnvFamily.UAT),

    UAT1("User Acceptance Test environment", EnvFamily.UAT),

    PROD("Production environment", EnvFamily.PROD),

    PROD1("Production environment", EnvFamily.PROD),

    UNKNOWN("Unknown environment", EnvFamily.UNKNOWN);

    private final String description;
    private final EnvFamily envFamily;

    Env(String description, EnvFamily envFamily) {
        this.description = description;
        this.envFamily = envFamily;
    }

    public static Env getByName(String name, Env defaultValue) {
        if (name != null) {
            name = name.trim();
            for (Env value : values()) {
                if (value.name().equalsIgnoreCase(name)) {
                    return value;
                }
            }
        }
        return defaultValue;
    }

    public String getDescription() {
        return description;
    }

    public EnvFamily getEnvFamily() {
        return envFamily;
    }

    public boolean isValid() {
        return this != UNKNOWN;
    }
}
