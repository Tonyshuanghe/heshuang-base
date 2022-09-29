//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.store;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class KafkaLoggerPipeline extends AbstractLoggerPipeline {
    private static final Logger log = LoggerFactory.getLogger(KafkaLoggerPipeline.class);

    public KafkaLoggerPipeline() {
    }
    @Override
    protected void writeLogger(List<ActionLog> auditLogs) {
    }
    @Override
    public void synchronousResource(JSONObject props) {
    }
}
