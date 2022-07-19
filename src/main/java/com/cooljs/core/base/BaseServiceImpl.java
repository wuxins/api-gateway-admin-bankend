package com.cooljs.core.base;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础service实现类
 *
 * @param <M> 实体操作类
 * @param <T> 实体
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity<T>> extends ServiceImpl<M, T> implements BaseService<T> {

    @Override
    public Long add(T entity) {
        baseMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public Object add(JSONObject requestParams, T entity) {
        this.add(entity);
        this.modifyAfter(requestParams, entity, ModifyEnum.ADD);
        return entity.getId();
    }

    @Override
    public Object addBatch(JSONObject requestParams, List<T> entitys) {
        List<Long> ids = new ArrayList<>();
        entitys.forEach(e -> {
            ids.add(this.add(e));
        });
        requestParams.set("ids", ids);
        this.modifyAfter(requestParams, null, ModifyEnum.ADD);
        return ids;
    }

    @Override
    public void delete(Long... ids) {
        baseMapper.deleteBatchIds(Convert.toList(ids));
    }

    @Override
    public void delete(JSONObject requestParams, Long... ids) {
        this.delete(ids);
        this.modifyAfter(requestParams, null, ModifyEnum.DELETE);
    }

    @Override
    public void update(T entity) {
        baseMapper.updateById(entity);

    }

    @Override
    public void update(JSONObject requestParams, T entity) {
        this.update(entity);
        this.modifyAfter(requestParams, entity, ModifyEnum.UPDATE);
    }

    @Override
    public Object list(JSONObject requestParams, QueryWrapper<T> queryWrapper) {
        return this.list(queryWrapper);
    }

    @Override
    public Object list(QueryWrapper<T> queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Object page(JSONObject requestParams, Page<T> page, QueryWrapper<T> queryWrapper) {
        return this.page(page, queryWrapper);
    }

    @Override
    public Object page(Page<T> page, QueryWrapper<T> queryWrapper) {
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Object info(JSONObject requestParams, Long id) {
        return info(id);
    }

    @Override
    public Object info(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public void modifyAfter(JSONObject requestParams, T t) {

    }

    @Override
    public void modifyAfter(JSONObject requestParams, T t, ModifyEnum type) {
        modifyAfter(requestParams, t);
    }
}
