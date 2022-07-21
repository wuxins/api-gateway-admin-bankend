package com.cooljs.modules.gateway.base;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tangzc.mpe.actable.annotation.Column;
import com.tangzc.mpe.actable.annotation.ColumnComment;
import com.tangzc.mpe.actable.annotation.IsAutoIncrement;
import com.tangzc.mpe.actable.annotation.constants.MySqlTypeConstant;
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
    @Column(comment = "创建时间", notNull = true, value = "created_time")
    private Date createdTime;

    @InsertUpdateOptionDate
    @Column(comment = "更新时间", notNull = true, value = "updated_time")
    private Date updatedTime;

    @Column(comment = "更新时间", notNull = true, value = "created_by", defaultValue = "system", length = 64)
    private String createdBy;

    @Column(comment = "更新时间", notNull = true, value = "updated_by", defaultValue = "system", length = 64)
    private String updatedBy;

    @Column(comment = "是否逻辑删除", notNull = true, value = "is_deleted", defaultValue = "N", type = MySqlTypeConstant.CHAR, length = 1)
    private String isDeleted;
}