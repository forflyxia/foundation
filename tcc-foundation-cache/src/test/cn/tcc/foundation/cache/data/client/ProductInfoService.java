package cn.tcc.foundation.cache.data.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductInfoService {

    public Map<Integer, ProductInfoVo> getProductInfoMap(List<Integer> ids) {
        Map<Integer, ProductInfoVo> map = new HashMap<>();
        for (Integer id : ids) {
            ProductInfoVo item = new ProductInfoVo();
            item.setId(id);
            item.setName(String.format("name %s", id));
            map.put(id, item);
        }
        return map;
    }

    public Map<Integer, List<ProductInfoVo>> getProductInfoListMap(List<Integer> ids) {
        Map<Integer, List<ProductInfoVo>> map = new HashMap<>();
        for (Integer id : ids) {

            List<ProductInfoVo> items = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                ProductInfoVo item = new ProductInfoVo();
                item.setId(id);
                item.setName(String.format("name %s-%s", id, i));
                items.add(item);
            }
            map.put(id, items);
        }
        return map;
    }

}
