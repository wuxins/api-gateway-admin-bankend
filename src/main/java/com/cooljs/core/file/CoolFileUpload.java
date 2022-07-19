package com.cooljs.core.file;

import cn.hutool.core.lang.Dict;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 文件上传
 */
@Component
public class CoolFileUpload {
    @Resource
    private FileUpload fileUpload;
    @Resource
    private FileProperties fileProperties;
    @Resource
    private OssFileUpload ossFileUpload;

    @PostConstruct
    private void init() {
        if (fileProperties.getMode() == FileModeEnum.CLOUD) {
            this.fileUpload = ossFileUpload;
        }
    }

    /**
     * 文件上传
     *
     * @param files   文件
     * @param request 请求
     * @return 上传模式
     */
    public Object upload(MultipartFile[] files, HttpServletRequest request) {
        return this.fileUpload.upload(files, request);
    }

    /**
     * 获得上传模式
     *
     * @return 上传
     */
    public Object getMode() {
        UpLoadModeType upLoadModeType = this.fileUpload.getMode();
        return Dict.create()
                .set("mode", upLoadModeType.getMode().value())
                .set("type", upLoadModeType.getType());
    }


    /**
     * 获得上传
     *
     * @return
     */
    public Object getMetaFileObj() {
        return this.fileUpload.getMetaFileObj();
    }
}
