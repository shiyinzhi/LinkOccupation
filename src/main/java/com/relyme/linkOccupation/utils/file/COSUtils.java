package com.relyme.linkOccupation.utils.file;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.AnonymousCOSCredentials;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.Transfer;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferProgress;
import com.qcloud.cos.transfer.Upload;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class COSUtils {

    // 上传文件, 根据文件大小自动选择简单上传或者分块上传。
    public static UploadResult uploadFileMy(COSDomain cosDomain) {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(StringUtils.isEmpty(cosDomain.getSecretId())? "AKIDsZ2GFzVmnZ6PSGpAICrGRN5YnmW1HH4b" : cosDomain.getSecretId(),
                StringUtils.isEmpty(cosDomain.getSecretKey())? "LRHsEMFuJTP7ZlhOp0vb2D9pGRNjrkNk" : cosDomain.getSecretKey());
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(StringUtils.isEmpty(cosDomain.getRegionName())? "ap-chongqing" : cosDomain.getRegionName()));
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket名需包含appid  存储桶名称
        String bucketName = StringUtils.isEmpty(cosDomain.getBucketName())? "wdzxchn2022-1308375128" : cosDomain.getBucketName();

        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        // 传入一个threadpool, 若不传入线程池, 默认TransferManager中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(cosclient, threadPool);

        String key = cosDomain.getKeyStr()+cosDomain.getFileName();
        File localFile = new File(cosDomain.getFilePath());

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        UploadResult uploadResult = null;
        try {
            // 返回一个异步结果Upload, 可同步的调用waitForUploadResult等待upload结束, 成功返回UploadResult, 失败抛出异常.
            long startTime = System.currentTimeMillis();
            Upload upload = transferManager.upload(putObjectRequest);
            showTransferProgress(upload);
            uploadResult = upload.waitForUploadResult();
            long endTime = System.currentTimeMillis();
            System.out.println("used time: " + (endTime - startTime) / 1000);
            System.out.println(uploadResult.getETag());
            System.out.println(uploadResult.getCrc64Ecma());
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        transferManager.shutdownNow();
        cosclient.shutdown();

        return uploadResult;
    }

    private static void showTransferProgress(Transfer transfer) {
        System.out.println(transfer.getDescription());
        do {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return;
            }
            TransferProgress progress = transfer.getProgress();
            long so_far = progress.getBytesTransferred();
            long total = progress.getTotalBytesToTransfer();
            double pct = progress.getPercentTransferred();
            System.out.printf("[%d / %d] = %.02f%%\n", so_far, total, pct);
        } while (transfer.isDone() == false);
        System.out.println(transfer.getState());
    }

    /**
     * 获取文件路径
     * @param cosDomain
     * @return
     */
    public static URL getObjectUrl(COSDomain cosDomain){
        // 不需要验证身份信息
        COSCredentials cred = new AnonymousCOSCredentials();

        // ClientConfig 中包含了后续请求 COS 的客户端设置：
        ClientConfig clientConfig = new ClientConfig();

        // 设置 bucket 的地域
        // COS_REGION 请参照 https://cloud.tencent.com/document/product/436/6224
        clientConfig.setRegion(new Region(StringUtils.isEmpty(cosDomain.getRegionName())? "ap-chongqing" : cosDomain.getRegionName()));

        // 设置生成的 url 的请求协议, http 或者 https
        // 5.6.53 及更低的版本，建议设置使用 https 协议
        // 5.6.54 及更高版本，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);

        // 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);

        // 存储桶的命名格式为 BucketName-APPID，此处填写的存储桶名称必须为此格式
        String bucketName = StringUtils.isEmpty(cosDomain.getBucketName())? "wdzxchn2022-1308375128" : cosDomain.getBucketName();
        // 对象键(Key)是对象在存储桶中的唯一标识。详情请参见 [对象键](https://cloud.tencent.com/document/product/436/13324)
        String key = cosDomain.getKeyStr()+cosDomain.getFileName();
        URL objectUrl = cosClient.getObjectUrl(bucketName, key);
        System.out.println(objectUrl);

        return objectUrl;
    }
}
