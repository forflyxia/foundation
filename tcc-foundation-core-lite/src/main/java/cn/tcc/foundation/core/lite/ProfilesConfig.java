package cn.tcc.foundation.core.lite;


import cn.tcc.foundation.core.lite.env.Env;
import cn.tcc.foundation.core.lite.util.StringUtil;

/**
 * @author shawn.xiao
 * @date 2018/1/2
 */
public class ProfilesConfig {

    private static String SPRING_PROFILES_ACTIVE = "spring.profiles.active";

    /**
     * 根据server.properties文件获取环境配置文件
     */
    private static void getProfilesByServerProperties() {
        ServerConfigProvider serverConfigProvider = new ServerConfigProvider();
        Env env = serverConfigProvider.getEnv();
        System.out.println(String.format("getProfiles:%s", env.name()));
        if (StringUtil.isNullOrEmpty(env.name())) {
            System.out.println("pls check server.properties!");
            return;
        }
        String profile = System.getProperty(SPRING_PROFILES_ACTIVE);
        if (StringUtil.isNullOrEmpty(profile)) {
            System.out.println("System.getProperty(SPRING_PROFILES_ACTIVE) IS NULL");
            profile = env.name();
            System.setProperty(SPRING_PROFILES_ACTIVE, profile.toLowerCase());
            System.out.println(String.format("System.setProperty(SPRING_PROFILES_ACTIVE, %s)",profile));
        }
        System.out.println(String.format("正在使用%s环境配置文件", profile));
    }

    public void getProfiles() {
        getProfilesByServerProperties();
    }
}
