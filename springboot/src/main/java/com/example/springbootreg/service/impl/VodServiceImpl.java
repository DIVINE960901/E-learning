package com.example.springbootreg.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;

import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.vod.model.v20170321.*;
import com.example.springbootreg.custom.PutObjectProgressListener;
import com.example.springbootreg.service.VodService;
import com.example.springbootreg.utils.InitObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {


    private static String accessKeyId ="LTAI5tAaDo7BSv7jLmCmRquz" ;


    private static String accessKeySecret = "ecg6N5SlHwcd5LeJqKf1I6XG44jzuj";
    @Override
    public String uploadVideo(MultipartFile videoFile) {
        try {
            String fileName = videoFile.getOriginalFilename();
            String title = fileName.substring(0,fileName.lastIndexOf("."));
            InputStream inputStream = videoFile.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            request.setPrintProgress(true);
            request.setProgressListener(new PutObjectProgressListener());
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
                System.out.print("VideoId=" + response.getVideoId() + "\n");
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }
            return videoId;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getPlayAuth(String VideoID) throws Exception{
        DefaultAcsClient client = InitObject.initVodclient(accessKeyId,accessKeySecret);

        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        request.setVideoId(VideoID);

        response = client.getAcsResponse(request);
//        System.out.println("播放凭证："+response.getPlayAuth());
        return response.getPlayAuth();
    }

    @Override
    public String getPlayUrl(String videoId) throws Exception {
        String PlayURL = "";
        DefaultAcsClient client = InitObject.initVodclient("LTAI5tAaDo7BSv7jLmCmRquz","ecg6N5SlHwcd5LeJqKf1I6XG44jzuj");

        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        request.setVideoId(videoId);

        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            //System.out.println("PlayInfo.PlayURL:" + playInfo.getPlayURL()+"\n");
            PlayURL = playInfo.getPlayURL();
        }
        //System.out.println("视频封面："+response.getVideoBase().getCoverURL()+"\n");
        return PlayURL;
    }

    @Override
    public void DeleteVideo(String videoId) {
        DefaultAcsClient client = InitObject.initVodclient("LTAI5tAaDo7BSv7jLmCmRquz","ecg6N5SlHwcd5LeJqKf1I6XG44jzuj");

        DeleteVideoRequest request = new DeleteVideoRequest();
        DeleteVideoResponse response = new DeleteVideoResponse();

        request.setVideoIds(videoId);
        try {
            DeleteVideoResponse response1 = client.getAcsResponse(request);
            System.out.println("RequestId:" + response1.getRequestId());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
    }


}
