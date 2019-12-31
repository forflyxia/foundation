package cn.tcc.foundation.es.search.bulk.builder.executor;

import cn.tcc.foundation.core.serialization.GsonSerializer;
import cn.tcc.foundation.es.cluster.EsHighLevelClient;
import cn.tcc.foundation.es.search.EsExecutor;
import cn.tcc.foundation.es.search.bulk.builder.EsBulkBuilder;
import cn.tcc.foundation.es.search.reponse.EsBulkResponse;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.util.HashMap;
import java.util.Map;

public class EsBulkExecutorImpl<T> implements EsExecutor<T> {

    private EsBulkBuilder bulkBuilder;

    public EsBulkExecutorImpl(EsBulkBuilder builder) {
        bulkBuilder = builder;
    }

    @Override
    public EsBulkResponse<T> execute(Class<?> clazz) throws Exception {

        BulkRequest request = new BulkRequest();
        for (Object o : bulkBuilder.getItemsMap().entrySet()) {
            Map.Entry<String, T> entry = (Map.Entry<String, T>) o;
            String key = entry.getKey();
            T value = entry.getValue();

            String json = "";
            if (value instanceof String) {
                json = value.toString();
            } else {
                json = GsonSerializer.toJson(value);
            }
            IndexRequest indexRequest = new IndexRequest(bulkBuilder.getIndexName(), bulkBuilder.getTypeName(), key);
            indexRequest.source(json, XContentType.JSON);
            request.add(indexRequest);
        }
        RestHighLevelClient client = EsHighLevelClient.getClient(bulkBuilder.getClusterName());
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        return getSuccessResponse(response);
    }

    private EsBulkResponse<T> getSuccessResponse(BulkResponse response) {

        EsBulkResponse<T> successResponse = new EsBulkResponse<>();
        successResponse.setTookInMillis(response.getTook().millis());
        successResponse.setSuccessItemsMap(new HashMap<>());

        for (BulkItemResponse bulkItemResponse : response) {
            DocWriteResponse itemResponse = bulkItemResponse.getResponse();
            if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.INDEX) {
                IndexResponse indexResponse = (IndexResponse) itemResponse;
                T item = (T) bulkBuilder.getItemsMap().get(indexResponse.getId());
                if (item != null) {
                    successResponse.getSuccessItemsMap().put(indexResponse.getId(), item);
                }
            }
        }
        return successResponse;
    }
}
