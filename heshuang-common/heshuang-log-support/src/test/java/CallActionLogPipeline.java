import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson.JSONObject;
import com.heshuang.logger.store.AbstractLoggerPipeline;
import com.heshuang.logger.store.ActionLog;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022/4/7 16:04
 * Description: 调用日志 ActionLog实现类
 */
@Component
public class CallActionLogPipeline extends AbstractLoggerPipeline {


    private ThreadLocal<String> threadSysUser = ThreadUtil.createThreadLocal(false);


    public void setUserId(String userId) {
        threadSysUser.set(userId);
    }

    @Override
    protected void writeLogger(List<ActionLog> list) {
      // 写入数据
    }

    @Override
    public void synchronousResource(JSONObject properties) {
       //同步数据
    }


}
