package com.cooljs.core.file;

import lombok.Data;

/**
 * 上传模式类型
 */
@Data
public class UpLoadModeType {

    /**
     * 模式
     */
    private FileModeEnum mode;

    /**
     * 类型
     */
    private String type;

    UpLoadModeType(FileModeEnum mode, String type) {
        this.mode = mode;
        this.type = type;
    }
}
