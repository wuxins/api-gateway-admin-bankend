package com.cooljs.core.annotation;


import com.baomidou.mybatisplus.annotation.TableName;
import com.tangzc.mpe.actable.annotation.*;
import com.tangzc.mpe.actable.annotation.constants.MySqlCharsetConstant;
import com.tangzc.mpe.actable.annotation.constants.MySqlEngineConstant;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TableName
@TablePrimary
@DsName("")
@TableComment
@TableCharset
@TableEngine
@Table
public @interface CoolTable {
    @AliasFor(
            annotation = TableName.class,
            attribute = "value"
    )
    String value() default "";

    @AliasFor(
            annotation = TablePrimary.class,
            attribute = "value"
    )
    boolean primary() default false;

    @AliasFor(
            annotation = DsName.class,
            attribute = "value"
    )
    String dsName() default "";

    @AliasFor(
            annotation = TableComment.class,
            attribute = "value"
    )
    String comment() default "";

    @AliasFor(
            annotation = TableCharset.class,
            attribute = "value"
    )
    MySqlCharsetConstant charset() default MySqlCharsetConstant.DEFAULT;

    @AliasFor(
            annotation = TableEngine.class,
            attribute = "value"
    )
    MySqlEngineConstant engine() default MySqlEngineConstant.DEFAULT;

    String[] excludeFields() default {"serialVersionUID", "entityClass"};
}
