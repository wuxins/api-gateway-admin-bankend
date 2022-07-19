package com.cooljs.modules.base.service.sys;

import cn.hutool.json.JSONObject;
import com.cooljs.core.base.BaseService;
import com.cooljs.modules.base.entity.sys.BaseSysLogEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统日志
 */
public interface BaseSysLogService extends BaseService<BaseSysLogEntity> {
    /**
     * 清理日志
     *
     * @param isAll 是否全部清除
     */
    void clear(boolean isAll);

    /**
     * 清理日志
     */
    void autoClear();

    /**
     * 日志记录
     *
     * @param requestParams 请求参数
     * @param request       请求
     */
    void record(HttpServletRequest request, JSONObject requestParams);
}
