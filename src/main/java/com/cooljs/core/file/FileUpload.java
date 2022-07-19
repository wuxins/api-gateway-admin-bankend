package com.cooljs.core.file;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.cooljs.core.exception.CoolException;
import org.apache.ibatis.io.Resources;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文件上传
 */
@Component
public class FileUpload {
    @Resource
    private LocalFileProperties localFileProperties;

    /**
     * 上传文件
     *
     * @param files 上传的文件
     * @return 文件路径
     */
    public Object upload(MultipartFile[] files, HttpServletRequest request) {
        if (StrUtil.isEmpty(localFileProperties.getFilePath()) || StrUtil.isEmpty(localFileProperties.getBaseUrl())) {
            throw new CoolException("filePath 或 baseUrl 未配置");
        }
        try {
            List<String> fileUrls = new ArrayList<>();
            String baseUrl = localFileProperties.getBaseUrl();
            String filePath = StrUtil.isNotEmpty(localFileProperties.getFilePath()) ? localFileProperties.getFilePath() : Resources.getResourceAsFile("static").getPath();
            String dir = filePath + "/upload/" + DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN) + "/";
            FileUtil.mkdir(dir);
            for (MultipartFile file : files) {
                // 保存文件
                String fileName = StrUtil.uuid().replaceAll("-", "") + getExtensionName(file.getOriginalFilename());
                file.transferTo(new File(dir + fileName));
                fileUrls.add(baseUrl + dir.replace(filePath, "") + fileName);
            }
            if (fileUrls.size() == 1) {
                return fileUrls.get(0);
            }
            return fileUrls;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CoolException("文件上传失败");
        }
    }

    private String getExtensionName(String fileName) {
        if (fileName.contains(".")) {
            String[] names = fileName.split("[.]");
            return "." + names[names.length - 1];
        }
        return "";
    }

    /**
     * 文件上传模式
     *
     * @return 上传模式
     */
    public UpLoadModeType getMode() {
        return new UpLoadModeType(FileModeEnum.LOCAL, "local");
    }

    /**
     * 获得原始我操作对象
     *
     * @return 文件操作对象
     */
    public Object getMetaFileObj() {
        return null;
    }
}
