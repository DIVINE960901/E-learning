package com.example.springbootreg.service;

import com.example.springbootreg.pojo.EsCourse;

import java.io.IOException;
import java.util.List;

public interface ElasticsearchService {

    List<EsCourse> search(String title) throws IOException;

    void bulkAddDocument() throws IOException;

    void createIndex() throws IOException;

    void existsIndex() throws IOException;
}
