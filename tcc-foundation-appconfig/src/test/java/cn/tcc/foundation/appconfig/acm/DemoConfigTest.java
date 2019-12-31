package cn.tcc.foundation.appconfig.acm;

import cn.tcc.foundation.appconfig.ConfigInitializer;

public class DemoConfigTest {

    public static void main(String[] args) {
        ConfigInitializer initializer = new ConfigInitializer();
        initializer.onStartup();

        Integer value = DemoConfig.getInstance().getValue(Integer.class);
        Integer value1 = DemoConfig.getInstance().getValue(Integer.class);
    }
}