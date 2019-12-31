package cn.tcc.foundation.core.lite;

import cn.tcc.foundation.core.lite.env.Env;
import cn.tcc.foundation.core.lite.io.BOMInputStream;
import cn.tcc.foundation.core.lite.util.OsUtil;
import cn.tcc.foundation.core.lite.util.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @author shawn.xiao
 * @date 2018/1/2
 */
public class ServerConfigProvider {

    static final String DEFAULT_CLUSTER_NAME = "default";
    private static final String SERVER_PROPERTIES_LINUX = "/opt/settings/server.properties";
    private static final String SERVER_PROPERTIES_WINDOWS = "C:/opt/settings/server.properties";
    private String[] PROD_IP_PREFIX = {"10.58.6"};

    private Env env;

    private String dataCenter;

    private Properties serverProperties = new Properties();

    public ServerConfigProvider() {
        initialize();
    }

    private void initialize() {
        try {
            boolean isWindow = OsUtil.isOSWindows();
            File file = null;
            if (isWindow) {
                file = new File(SERVER_PROPERTIES_WINDOWS);
            } else {
                file = new File(SERVER_PROPERTIES_LINUX);
            }
            if (file != null && (file.exists()) && (file.canRead())) {
                System.out.println("Loading " + file.getAbsolutePath());
                FileInputStream fis = new FileInputStream(file);
                initialize(fis);
                return;
            } else {
                System.out.println("/opt/settings/server.properties and C:/opt/settings/server.properties does not exist or is not readable.");
            }
            initialize(null);
        } catch (Exception ex) {
            System.out.println("onStartup env failed " + ex.getMessage());
        }
    }

    private void initialize(InputStream in) {
        try {
            if (in != null) {
                try {
                    this.serverProperties.load(new InputStreamReader(new BOMInputStream(in), StandardCharsets.UTF_8));
                } finally {
                    in.close();
                }
            }
            initDataCenter();
            initEnv();
            validate();
        } catch (Exception ex) {
            System.out.println("onStartup env failed " + ex.getMessage());
        }
    }

    private void validate() {
        if ((!this.env.isValid())) {
            throw new IllegalArgumentException("illegal env parameter. ");
        }
    }

    private void initEnv() {
        String envFromServerProperties = this.serverProperties.getProperty("env");
        if (!StringUtil.isNullOrEmpty(envFromServerProperties)) {
            this.env = Env.getByName(envFromServerProperties, Env.UNKNOWN);
        }
    }

    private void initDataCenter() {
        this.dataCenter = this.serverProperties.getProperty("idc");
        if (!StringUtil.isNullOrEmpty(this.dataCenter)) {
            this.dataCenter = this.dataCenter.trim();
        }
    }

    public Env getEnv() {
        return env;
    }

    public String getDataCenter() {
        return dataCenter;
    }
}
