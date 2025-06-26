package com.example.springbootreg;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.cat.IndicesResponse;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.example.springbootreg.pojo.EsCourse;
import com.example.springbootreg.pojo.courseAdmin;
import com.example.springbootreg.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ElasticsearchTest {

    @Autowired
    private CourseService courseService;
    @Autowired
    ElasticsearchClient client;

    //创建索引
    @Test
    public void createIndex() throws IOException {
        CreateIndexResponse indexResponse = client.indices().create(c ->c.index("es_course"));
        System.out.println(indexResponse);
        System.out.println(indexResponse.acknowledged());
    }

    //判断索引是否存在
    @Test
    public void existsIndex() throws IOException {
        BooleanResponse booleanResponse = client.indices().exists(e -> e.index("es_course"));
        System.out.println(booleanResponse.value());
    }

    //查看某个索引信息
    public void findIndex() throws IOException {
        GetIndexResponse getIndexResponse = client.indices().get(getIndex -> getIndex.index("es_course"));
        System.out.println(getIndexResponse.result());
    }

    //查看所有索引信息
    public void findIndexes() throws IOException {
        IndicesResponse indicesResponse = client.cat().indices();
        System.out.println(indicesResponse.valueBody());
    }


    //删除某个索引
    public void deleteIndex() throws IOException {
        DeleteIndexResponse deleteIndexResponse = client.indices().delete(d -> d.index("es_course"));
    }


    //添加文档
    public void addDocument() throws IOException {

        EsCourse esCourse = new EsCourse("a06a3747d63c71ee80126633b79f0102",
                "JAVA基础语法","TT1","http://localhost:8080/img/7.jpg");
        IndexResponse indexResponse = client.index(
                i ->i.index("es_course").id(esCourse.getId()).document(esCourse));

        System.out.println(indexResponse);

    }


    @Test
    //批量添加文档
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
        System.out.println(bulkResponse);
    }


    //查询文档
    public void getDocument(String id) throws IOException {
        GetResponse<EsCourse> getResponse = client.get(g ->g.index("es_course").id(id), EsCourse.class);
        System.out.println(getResponse.source());
    }


    //判断文档是否存在
    public void existDocument(String id) throws IOException {
        BooleanResponse indexResponse = client.exists(e -> e.index("es_course").id(id));
        System.out.println(indexResponse.value());
    }

    //更新文档

    public void updateDoument(String id) throws IOException {
        EsCourse esCourse = new EsCourse("a06a3747d63c71ee80126633b79f0102",
                "JAVA基础语法","TT1","http://localhost:8080/img/7.jpg");
        UpdateResponse<EsCourse> updateResponse = client.update(u ->u.index("es_course")
                .id(id).doc(esCourse),EsCourse.class
        );
    }

    //删除文档
    public void deleteDocument(String id) throws IOException {
        DeleteResponse deleteResponse = client.delete(
                d ->d.index("es_course").id(id)
        );
        System.out.println(deleteResponse.id());
    }

    //查询某个索引的全部数据
    @Test
    public void searchAll() throws IOException {
        List<EsCourse> list = new ArrayList<>();
        Query queryAll = Query.of(q -> q.matchAll(m ->m));
        SearchRequest searchRequest = new SearchRequest.Builder().index("es_course").query(queryAll).build();
        SearchResponse<EsCourse> search = client.search(searchRequest, EsCourse.class);
        for (Hit<EsCourse> hit : search.hits().hits()){
            System.out.println(hit.source());
            list.add(hit.source());
        }
    }

    //条件查询，有分词效果
    @Test
    public void searchMatch() throws IOException {
        List<EsCourse> list = new ArrayList<>();
        Query queryByTitle = Query.of(q -> q.match(m -> m.field("title").query("JAVA")));
        SearchRequest searchRequest = new SearchRequest.Builder().index("es_course").query(queryByTitle).build();
        SearchResponse<EsCourse> search = client.search(searchRequest, EsCourse.class);
        for (Hit<EsCourse> hit : search.hits().hits()){
            System.out.println(hit.source());
            list.add(hit.source());
        }
        System.out.println(list);
    }

    //完全匹配查询，无分词效果
    public void searchMatchPhrase(String TargetName) throws IOException {
        List<EsCourse> list = new ArrayList<>();
        Query queryByTitle = Query.of(q -> q.matchPhrase(m -> m.field("title").query(TargetName)));
        SearchRequest searchRequest = new SearchRequest.Builder().index("es_course").query(queryByTitle).build();
        SearchResponse<EsCourse> search = client.search(searchRequest, EsCourse.class);
        for (Hit<EsCourse> hit : search.hits().hits()){
            System.out.println(hit.source());
            list.add(hit.source());
        }
    }

    //多条件查询，多个条件都要同时满足（逻辑关系为and）
    public void searchBoolMust(String TargetName,String TargetName2) throws IOException {
        List<EsCourse> list = new ArrayList<>();
        Query queryByTitle = Query.of(q -> q.match(m -> m.field("title").query(TargetName)));
        Query queryByNickname = Query.of(q -> q.match(m -> m.field("nickname").query(TargetName2)));
        Query bool = Query.of(q -> q.bool(b -> b.must(queryByTitle).must(queryByNickname)));
        SearchRequest searchRequest = new SearchRequest.Builder().index("es_course").query(bool).build();
        SearchResponse<EsCourse> search = client.search(searchRequest, EsCourse.class);
        for (Hit<EsCourse> hit : search.hits().hits()){
            System.out.println(hit.source());
            list.add(hit.source());
        }
    }


    //多条件查询，多个条件都满足任意一个均可（逻辑关系为or）
    public void searchBoolShould(String TargetName,String TargetName2) throws IOException {
        List<EsCourse> list = new ArrayList<>();
        Query queryByTitle = Query.of(q -> q.match(m -> m.field("title").query(TargetName)));
        Query queryByNickname = Query.of(q -> q.match(m -> m.field("nickname").query(TargetName2)));
        Query bool = Query.of(q -> q.bool(b -> b.should(queryByTitle).should(queryByNickname)));
        SearchRequest searchRequest = new SearchRequest.Builder().index("es_course").query(bool).build();
        SearchResponse<EsCourse> search = client.search(searchRequest, EsCourse.class);
        for (Hit<EsCourse> hit : search.hits().hits()){
            System.out.println(hit.source());
            list.add(hit.source());
        }
    }


    //模糊查询
    public void searchFuzzy() throws IOException {
        List<EsCourse> list = new ArrayList<>();
        Query queryByTitle = Query.of(q -> q.wildcard(m -> m.field("title").value("*J*")));//包含J字的
        //Query queryByTitle = Query.of(q -> q.prefix(m -> m.field("title").value("J")));//以李字为前缀（开头）
        //Query queryByTitle = Query.of(q -> q.fuzzy(m -> m.field("title").value("J")));//包含J字
        //Query queryByTitle = Query.of(q -> q.fuzzy(m -> m.field("title").value("J").fuzziness("2")));//这样如果相差不超过两个字符也算
        SearchRequest searchRequest = new SearchRequest.Builder().index("es_course").query(queryByTitle).build();
        SearchResponse<EsCourse> search = client.search(searchRequest, EsCourse.class);
        for (Hit<EsCourse> hit : search.hits().hits()){
            System.out.println(hit.source());
            list.add(hit.source());
        }
    }


}
