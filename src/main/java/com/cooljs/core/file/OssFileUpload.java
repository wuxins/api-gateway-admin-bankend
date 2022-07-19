package com.cooljs.core.file;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.cooljs.core.exception.CoolException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 文件上传
 */
@Component
public class OssFileUpload extends FileUpload {

    @Resource
    private OssFileProperties ossFileProperties;

    private OSSClient ossClient;

    @PostConstruct
    private void init() {
        if (ossFileProperties != null && StrUtil.isNotEmpty(ossFileProperties.getEndpoint())
                && StrUtil.isNotEmpty(ossFileProperties.getAccessKeyId())
                && StrUtil.isNotEmpty(ossFileProperties.getAccessKeySecret())) {
            ossClient = new OSSClient(ossFileProperties.getEndpoint(), ossFileProperties.getAccessKeyId(), ossFileProperties.getAccessKeySecret());
        }
    }

    @Override
    public Object upload(MultipartFile[] files, HttpServletRequest request) {
        try {
            Date expiration = new Date(System.currentTimeMillis() + ossFileProperties.getTimeout() * 1000);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, "");
            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);
            return Dict.create()
                    .set("OSSAccessKeyId", ossFileProperties.getAccessKeyId())
                    .set("success_action_status", 200)
                    .set("policy", encodedPolicy)
                    .set("signature", postSignature)
                    .set("host", "http://" + ossFileProperties.getBucket() + "." + ossFileProperties.getEndpoint());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CoolException("获得签名失败");
        }
    }

    @Override
    public UpLoadModeType getMode() {
        return new UpLoadModeType(FileModeEnum.CLOUD, "oss");
    }

    @Override
    public Object getMetaFileObj() {
        return this.ossClient;
    }
}
