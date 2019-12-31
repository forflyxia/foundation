package cn.tcc.foundation.es.search.bulk;

import cn.tcc.foundation.core.ProfilesConfig;
import cn.tcc.foundation.es.EsInitializer;
import cn.tcc.foundation.es.search.TestRequestDemo;
import cn.tcc.foundation.es.search.reponse.EsBulkResponse;
import org.junit.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class EsBulkClientTest {
    public static void main(String[] args) throws Exception {

        new ProfilesConfig().getProfiles();

        EsInitializer initializer = new EsInitializer();
        initializer.onStartup();

        List<TestRequestDemo> items = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            TestRequestDemo t1 = new TestRequestDemo();
            t1.product_id = i+1;
            if (i % 2 == 0) {
                t1.name = String.format("c# %s", t1.product_id);
                t1.type_ch = "1";
            } else {
                t1.name = String.format("java %s", t1.product_id);
                t1.type_ch = "2";
            }
            items.add(t1);
        }

        String key = "1";
        String value = "{\"fk_employee_owner\":72,\"saleenddate\":\"一月 1, 2021\",\"createtime\":\"Aug 18, 2017 10:23:44 PM\",\"fk_currency_sale\":1,\"modifytime\":\"Aug 18, 2017 10:23:44 PM\",\"fk_user_modify\":1,\"basesaleprice\":1298.00,\"isvalid\":1,\"type\":2,\"fk_department\":6,\"itemKey\":23287,\"salestartdate\":\"一月 1, 2017\",\"fk_company\":593,\"id\":23287,\"fk_user_create\":1,\"dataChange_LastTime\":\"Aug 18, 2017 10:23:44 PM\"}";
        Map<String, String> map = new HashMap<>();
        map.put(key, value);

        EsBulkClient<String> client = new EsBulkClient("clustername1");
        EsBulkResponse<String> response = (EsBulkResponse)client.from("test", "user").source(map).execute(String.class);
        Assert.assertNotNull(response.getSuccessItemsMap());
    }
}