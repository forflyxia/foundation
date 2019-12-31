package cn.tcc.foundation.metric.metric;

import cn.tcc.foundation.core.serialization.GsonSerializer;
import cn.tcc.foundation.metric.config.ArmsClient;
import cn.tcc.foundation.metric.config.ArmsClientProvider;
import cn.tcc.foundation.metric.config.ArmsConfig;
import com.aliyun.openservices.log.Client;
import com.aliyun.openservices.log.common.LogItem;
import com.aliyun.openservices.log.exception.LogException;
import com.aliyun.openservices.log.request.PutLogsRequest;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author shawn.xiao 2019/1/2
 **/
public class MetricClient<T> {
    ArmsConfig armsConfig = null;
    Client armsClient = null;

    public MetricClient(String metricName) {
        try {
            armsConfig = ArmsClientProvider.get(metricName);
            armsClient = ArmsClient.getClient(metricName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> void push(T t) {
        if (armsConfig == null) {
            System.out.println("armsConfig not init.");
        }
        if (armsClient == null) {
            System.out.println("armsClient not init.");
        }

        if (!armsConfig.isEnable()) {
            System.out.println(String.format("logstore=%s is not enabled!", armsConfig.getLogstore()));
            return;
        }
        DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long currentTime = System.currentTimeMillis();
        String formatedTime = dateFormat.format(new Date(currentTime));
        List<LogItem> logGroup = new ArrayList<>();
        LogItem logItem = new LogItem();
        String json;
        if(t instanceof String){
            json = (String) t;
        }else {
            json = GsonSerializer.toJson(t);
        }
        logItem.PushBack("content", String.format("%s|%s|%s", formatedTime, json, UUID.randomUUID()));
        logGroup.add(logItem);
        PutLogsRequest req = new PutLogsRequest(armsConfig.getProject(), armsConfig.getLogstore(), "", "", logGroup);

        try {
            armsClient.PutLogs(req);
        } catch (LogException e) {
            e.printStackTrace();
        }
    }
}
