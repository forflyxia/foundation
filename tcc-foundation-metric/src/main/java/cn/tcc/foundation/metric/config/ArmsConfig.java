package cn.tcc.foundation.metric.config;

/**
 * @author shawn.xiao
 * @date 2018/3/21
 */
public class ArmsConfig {

    private String endpoint;

    private String project;

    private String logstore;

    private String accessKeyId;

    private String accessKeySecret;

    private boolean enable;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getLogstore() {
        return logstore;
    }

    public void setLogstore(String logstore) {
        this.logstore = logstore;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "ArmsConfig{" +
                "endpoint='" + endpoint + '\'' +
                ", project='" + project + '\'' +
                ", logstore='" + logstore + '\'' +
                ", accessKeyId='" + accessKeyId + '\'' +
                ", accessKeySecret='" + accessKeySecret + '\'' +
                ", enable=" + enable +
                '}';
    }
}
