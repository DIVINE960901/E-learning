package com.example.springbootreg;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.example.springbootreg.controller.FavoriteController;
import com.example.springbootreg.pojo.EsCourse;
import com.example.springbootreg.pojo.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@SpringBootTest
class SpringbootRegApplicationTests {



    @Test
    void contextLoads() {
    }

    @Test
    public void testFdetail(){
        FavoriteController favoriteController = new FavoriteController();
        Result result = favoriteController.Fdetail();
        System.out.println(result);
    }

    @Test
    public void testURL(){
        String staticResourcePath = "E:/IdeaCode/springbootReg/src/main/resources/static/coverImg/";
        String imageName = "56b31c58-df5c-409b-bf36-77cb1c03a51e.jpg";
        File imageFile = new File(staticResourcePath + imageName);

        if (imageFile.exists()) {
            boolean isDeleted = imageFile.delete();
            if (isDeleted) {
                System.out.println("The file '" + imageName + "' has been successfully deleted.");
            } else {
                System.err.println("Failed to delete the file '" + imageName + "'.");
            }
        } else {
            System.err.println("The file '" + imageName + "' does not exist.");
        }
    }



}
