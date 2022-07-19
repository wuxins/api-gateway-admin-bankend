package com.cooljs.core.request;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 查询构建器
 *
 * @param <T>
 */
@Data
public class CrudOption<T> {
    public QueryWrapper<T> queryWrapper;
    String[] fieldEq;
    String[] keyWordLikeFields;
    String[] select;
    JSONObject requestParams;

    Environment evn;

    public CrudOption(JSONObject requestParams) {
        this.requestParams = requestParams;
        this.queryWrapper = new QueryWrapper<T>();
        this.evn = SpringUtil.getBean(Environment.class);
    }


    public CrudOption<T> fieldEq(String... fields) {
        this.fieldEq = fields;
        return this;
    }

    public QueryWrapper<T> getQueryWrapper() {
        return build(this.queryWrapper);
    }

    public CrudOption<T> queryWrapper(QueryWrapper<T> queryWrapper) {
        this.queryWrapper = queryWrapper;
        return this;
    }

    public CrudOption<T> keyWordLikeFields(String... fields) {
        this.keyWordLikeFields = fields;
        return this;
    }

    public CrudOption<T> select(String... selects) {
        this.select = selects;
        return this;
    }

    /**
     * 获得字段名称， 是下划线 还是驼峰
     *
     * @param field 字段
     * @return 新的字段
     */
    public String getField(String field) {
        try {
            // 是否转下划线
            Boolean underscore = evn.getProperty("mybatis-plus.configuration.map-underscore-to-camel-case", Boolean.class);
            return underscore == null || underscore ? StrUtil.toUnderlineCase(field) : field;
        } catch (Exception e) {
            return field;
        }
    }

    /**
     * 构建查询条件
     *
     * @return QueryWrapper
     */
    private QueryWrapper<T> build(QueryWrapper<T> queryWrapper) {
        Convert.toList(String.class, this.fieldEq).forEach(field -> {
            queryWrapper.eq(ObjectUtil.isNotNull(requestParams.get(field)), getField(field), requestParams.get(field));
        });
        if (this.keyWordLikeFields != null) {
            String sql = Convert.toList(String.class, this.keyWordLikeFields).stream().map(field -> getField(field) + " LIKE {0}").collect(Collectors.joining(" or "));
            queryWrapper.apply(StrUtil.isNotEmpty(requestParams.getStr("keyWord")) && StrUtil.isNotEmpty(sql), "(" + sql + ")", "%" + requestParams.get("keyWord") + "%");
        }
        if (select != null) {
            queryWrapper.select(Arrays.stream(this.select).map(field -> getField(field)).toArray(String[]::new));
        }
        // 排序
        order(queryWrapper);
        return queryWrapper;
    }

    private void order(QueryWrapper<T> queryWrapper) {
        if (StrUtil.isEmpty(queryWrapper.getSqlSegment())) {
            queryWrapper.apply("1 = 1");
        }
        String order = requestParams.getStr("order", "createTime");
        String sort = requestParams.getStr("sort", "desc");
        queryWrapper.orderBy(StrUtil.isNotEmpty(order) && StrUtil.isNotEmpty(sort), sort.equals("asc"), order);
    }

}
