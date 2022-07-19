package com.cooljs;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import com.cooljs.core.comm.CoolProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;


@Component
@Slf4j
public class AppReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    @Resource
    private ScriptRunner scriptRunner;

    @Resource
    private CoolProperties coolProperties;

    @Resource
    private Environment environment;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        initSql();
        log.info("COOL-ADMIN 服务启动成功！");
    }

    /**
     * 自动导入sql， 存在init.lock文件则不导入
     */
    void initSql() {
        try {
            if (coolProperties.getInitSql() != null && !coolProperties.getInitSql()) {
                return;
            }
            String sqlPath = Resources.getResourceAsFile("cool/sql").getPath();
            // 如果是本地开发不要读取target中的resources
            if (CollUtil.toList(environment.getActiveProfiles()).contains("local")) {
                sqlPath = System.getProperty("user.dir") + "/src/main/resources/cool/sql";
            }
            if (FileUtil.exist(sqlPath + "/init.lock")) {
                return;
            }
            File tempFile = new File(sqlPath);
            File[] files = tempFile.listFiles();
            assert files != null;
            for (File file : files) {
                String fileName = file.getName();
                String extend = fileName.substring(fileName.lastIndexOf(".") + 1);
                if (!extend.equals("sql")) {
                    continue;
                }
                if (file.isFile() && !fileName.equals("init.lock")) {
                    scriptRunner.runScript(Resources.getResourceAsReader("cool/sql/" + fileName));
                    log.info("自动导入 {} 文件成功", fileName);
                }
            }
            FileWriter fileWriter = new FileWriter(sqlPath + "/init.lock");
            fileWriter.write("coolSqlInit");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("自动导入sql文件失败，请手动导入");
        }
    }
}
