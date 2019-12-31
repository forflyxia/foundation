package cn.tcc.foundation.metric;

import cn.tcc.foundation.core.lite.ProfilesConfig;
import cn.tcc.foundation.metric.metric.MetricClient;

import javax.servlet.ServletException;

/**
 * @author shawn.xiao 2019/1/2
 **/
public class MetricTest {

    public static void main(String[] args) throws ServletException {
        new ProfilesConfig().getProfiles();

        MetricInitializer initializer = new MetricInitializer();
        initializer.onStartup();

        MetricClient<QueryEntity> queryEntityMetricClient = new MetricClient<>("query");
        QueryEntity queryEntity = new QueryEntity();
        queryEntity.setId(1);
        queryEntity.setName("test method");
        queryEntityMetricClient.push(queryEntity);


    }
}
