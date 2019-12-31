package cn.tcc.foundation.es.search;


import java.math.BigDecimal;

public class TestRequestDemo implements EsEntity<Integer> {

    public int product_id;

    public String name;

    public String type_ch;

    public String destination_ch;

    public String destination_en;

    public boolean is_book_together;

    public boolean is_seckill;

    public String seckill_starttime;

    public String seckill_endtime;

    public String subtitle;

    public int weight;

    public String tag_ch;

    public String features_ch;

    public String channel_code;

    public int isvalid;


    @Override
    public Integer getKey() {
        return product_id;
    }
}
