package cn.tcc.foundation.appconfig.acm;

public class DemoConfig extends AbstractConfig<Integer> {

    private static final DemoConfig INSTANCE = new DemoConfig();

    private DemoConfig() { }

    public static DemoConfig getInstance() { return INSTANCE; }

    @Override
    protected String getConfigKey() {
        return "com.tc.internalapi.hotel.dynamicprice.addpricevalue";
    }

    @Override
    protected Integer reviseValue(Integer value) {
        return value;
    }
}
