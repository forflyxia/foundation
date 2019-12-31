package cn.tcc.foundation.core;

import cn.tcc.foundation.core.lite.ProfilesConfig;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.util.Set;

@HandlesTypes({XApplicationInitializer.class})
public class XServletContainerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> tcInitializers, ServletContext servletContext) throws ServletException {
        if (tcInitializers == null) {
            return;
        }

        new ProfilesConfig().getProfiles();

        String profile = System.getProperty("spring.profiles.active");
        System.out.println(profile);
        System.out.println("XServletContainerInitializer onStartup done.");

        for (Class<?> item : tcInitializers) {
            if (XApplicationInitializer.class.isAssignableFrom(item)) {
                try {
                    XApplicationInitializer tcInitializer = (XApplicationInitializer)item.newInstance();
                    tcInitializer.onStartup();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
