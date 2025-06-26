package com.example.springbootreg.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.example.springbootreg.pojo.EsCourse;
import com.example.springbootreg.pojo.courseAdmin;
import com.example.springbootreg.service.CourseService;
import com.example.springbootreg.service.ElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ElasticsearchImpl implements ElasticsearchService {

    @Autowired
    private CourseService courseService;
    @Autowired
    ElasticsearchClient client;


    @Override
    public List<EsCourse> search(String TargetName) throws IOException {
        List<EsCourse> list = new ArrayList<>();
        Query queryByTitle = Query.of(q -> q.match(m -> m.field("title").query(TargetName)));
        SearchRequest searchRequest = new SearchRequest.Builder().index("es_course").query(queryByTitle).build();
        SearchResponse<EsCourse> search = client.search(searchRequest, EsCourse.class);
        for (Hit<EsCourse> hit : search.hits().hits()){
            System.out.println(hit.source());
            list.add(hit.source());
        }
        return list;
    }

    @Override
    public void bulkAddDocument() throws IOException {
        List<EsCourse> esList = new ArrayList<>();
        List<courseAdmin> course = courseService.findByState();
        for ( courseAdmin courseAdmin :course){
//            System.out.println(courseAdmin.getId());
//            System.out.println(courseAdmin.getTitle());
//            System.out.println(courseAdmin.getNickname());
//            System.out.println(courseAdmin.getCoverImg());
//            System.out.println(courseAdmin.getCreateTime());
            esList.add(new EsCourse(courseAdmin.getId(),courseAdmin.getTitle(),courseAdmin.getNickname(),courseAdmin.getCoverImg()));
        }
//        System.out.println(esList);
//        esList.add(new EsCourse());
        List<BulkOperation> bulkOperationArrayList = new ArrayList<>();
        for (EsCourse esCourse : esList){
            bulkOperationArrayList.add(
                    BulkOperation.of(o -> o.index(i -> i.document(esCourse).id(esCourse.getId()))));
        }
        BulkResponse bulkResponse = client.bulk(b -> b.index("es_course").operations(bulkOperationArrayList));
//        System.out.println(bulkResponse);
    }

    @Override
    public void createIndex() throws IOException {
        CreateIndexResponse indexResponse = client.indices().create(c ->c.index("es_course"));
        System.out.println(indexResponse);
        System.out.println(indexResponse.acknowledged());
    }

    @Override
    public void existsIndex() throws IOException {
        BooleanResponse booleanResponse = client.indices().exists(e -> e.index("es_course"));
        System.out.println(booleanResponse.value());
    }
}
