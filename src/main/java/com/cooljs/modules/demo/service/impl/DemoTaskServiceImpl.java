package com.cooljs.modules.demo.service.impl;

import com.cooljs.modules.demo.service.DemoTaskService;
import org.springframework.stereotype.Service;

@Service()
public class DemoTaskServiceImpl implements DemoTaskService {
    @Override
    public void test(String args) {
        System.out.println("任务被调用了" + args);
    }
}
