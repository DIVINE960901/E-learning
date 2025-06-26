package com.example.springbootreg;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.*;

import java.util.ArrayList;
import java.util.List;


public class TestVod {
    public static void main(String[] args) throws Exception{
        //getPlayUrl();
        //getPlayAuth();

        //CreateVideo();

    }

    public static String buildAuditContent() throws Exception {
        List<JSONObject> auditContents = new ArrayList<>();

        JSONObject auditContent = new JSONObject();
        auditContent.put("VideoId", "c08be920ce3171eea1846733a78e0102"); // 视频ID
        auditContent.put("Status", "Normal"); // 审核状态 Normal
        //auditContent.put("Reason", "含有淫秽色情内容"); // 若审核状态为屏蔽时，需填写，最长支持128字节
        auditContents.add(auditContent);

        return auditContents.toString();
    }
    public static void CreateVideo() throws Exception{
        DefaultAcsClient client = InitObject.initVodclient("LTAI5tAaDo7BSv7jLmCmRquz","ecg6N5SlHwcd5LeJqKf1I6XG44jzuj");

        CreateAuditRequest request = new CreateAuditRequest();
        CreateAuditResponse response = new CreateAuditResponse();

        request.setAuditContent(buildAuditContent());

        response = client.getAcsResponse(request);

        System.out.println("审核结果："+response.getRequestId());
    }

    public static void getPlayAuth() throws Exception{
        DefaultAcsClient client = InitObject.initVodclient("LTAI5tAaDo7BSv7jLmCmRquz","ecg6N5SlHwcd5LeJqKf1I6XG44jzuj");

        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        request.setVideoId("502a211cb04d71eea97f5017f0e80102");

        response = client.getAcsResponse(request);
        System.out.println("播放凭证："+response.getPlayAuth());
    }
    public static void getPlayUrl() throws Exception{
        DefaultAcsClient client = InitObject.initVodclient("LTAI5tAaDo7BSv7jLmCmRquz","ecg6N5SlHwcd5LeJqKf1I6XG44jzuj");

        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        request.setVideoId("502a211cb04d71eea97f5017f0e80102");

        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.println("PlayInfo.PlayURL:" + playInfo.getPlayURL()+"\n");

        }
        //System.out.println("视频封面："+response.getVideoBase().getCoverURL()+"\n");

    }
}
