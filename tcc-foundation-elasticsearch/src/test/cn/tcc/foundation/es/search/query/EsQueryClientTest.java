package cn.tcc.foundation.es.search.query;

import cn.tcc.foundation.core.ProfilesConfig;
import cn.tcc.foundation.es.EsInitializer;
import cn.tcc.foundation.es.search.TestRequestDemo;
import cn.tcc.foundation.es.search.reponse.EsQueryResponse;
import org.junit.Assert;

public class EsQueryClientTest {

    public static void main(String[] args) throws Exception {

        new ProfilesConfig().getProfiles();

        EsInitializer initializer = new EsInitializer();
        initializer.onStartup();

        EsQueryClient<TestRequestDemo> client = new EsQueryClient<TestRequestDemo>("clustername1");
//        EsQueryResponse<TestRequestDemo> response = (EsQueryResponse)client.from("product_index", "product")
//                .shouldMatch(p->p.type_ch, "跟团游")
//                .shouldMatch(p->p.type_ch, "自由行")
//                .boxToBool()
//                .mustMatch(p->p.is_seckill, true).filterBool()
//                .filterRangeGTE(p->p.seckill_starttime, "Jan 26, 2019 10:00:00 AM")
//                .filterRangeLTE(p->p.seckill_starttime, "Jan 26, 2019 10:00:00 AM")
//                .mustMatch(p->p.isvalid, 1).filterBool()
//                .paging(1,20)
//                .execute(TestRequestDemo.class);
//
//        Assert.assertTrue(response.getTookInMillis() > 0);
//        Assert.assertNotNull(response);
    }

}