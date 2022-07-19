package com.cooljs.core.base;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tangzc.mpe.actable.annotation.ColumnComment;
import com.tangzc.mpe.actable.annotation.IsAutoIncrement;
import com.tangzc.mpe.annotation.InsertOptionDate;
import com.tangzc.mpe.annotation.InsertUpdateOptionDate;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseEntity<T extends Model<T>> extends Model<T> {
    @IsAutoIncrement(value = true)
    @ColumnComment("ID")
    private Long id;

    @InsertOptionDate
    @ColumnComment("创建时间")
    private Date createTime;

    @InsertUpdateOptionDate
    @ColumnComment("更新时间")
    private Date updateTime;
}