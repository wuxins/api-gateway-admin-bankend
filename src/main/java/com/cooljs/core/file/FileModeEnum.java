package com.cooljs.core.file;

/**
 * 文件模式
 */
public enum FileModeEnum {
    LOCAL("local", "本地"),
    CLOUD("cloud", "云存储"),
    OTHER("other", "其他");

    private String value;

    private String des;

    FileModeEnum(String value, String des) {
        this.value = value;
        this.des = des;
    }

    public String value() {
        return this.value;
    }
}
