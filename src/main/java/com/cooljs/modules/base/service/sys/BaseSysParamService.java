package com.cooljs.modules.base.service.sys;

import com.cooljs.core.base.BaseService;
import com.cooljs.modules.base.entity.sys.BaseSysParamEntity;

/**
 * 系统参数配置
 */
public interface BaseSysParamService extends BaseService<BaseSysParamEntity> {
    /**
     * 根据key获得网页内容
     *
     * @param key 键
     * @return 网页内容
     */
    String htmlByKey(String key);

    /**
     * 根据key获得数据
     *
     * @param key 键
     * @return 数据
     */
    String dataByKey(String key);
}
