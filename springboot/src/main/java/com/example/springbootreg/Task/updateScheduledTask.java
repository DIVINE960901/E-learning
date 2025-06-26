package com.example.springbootreg.Task;

import com.example.springbootreg.service.ElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class updateScheduledTask {

    @Autowired
    private ElasticsearchService elasticsearchService;

    //每隔10分钟更新一次
    @Scheduled(cron = "0 */10 * * * ?")
    public String update() throws IOException {
        elasticsearchService.bulkAddDocument();
        System.out.println("已经更新，时间为： " + new Date());
        return "update success";
    }
}
