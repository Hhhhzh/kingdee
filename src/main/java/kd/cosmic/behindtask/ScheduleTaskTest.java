package kd.cosmic.behindtask;

import kd.bos.context.RequestContext;
import kd.bos.exception.KDException;
import kd.bos.schedule.api.MessageHandler;
import kd.bos.schedule.executor.AbstractTask;
import kd.sdk.plugin.Plugin;

import java.util.Map;

/**
 * 后台任务插件
 */
public class ScheduleTaskTest extends AbstractTask implements Plugin {

    @Override
    public MessageHandler getMessageHandle() {
        return super.getMessageHandle();
    }

    @Override
    public void execute(RequestContext requestContext, Map<String, Object> map) throws KDException {
        System.out.println("Hello WORLD!");
    }
}