package kd.cosmic.formplugin;


import kd.bos.context.RequestContext;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.form.control.events.BeforeClickEvent;
import kd.bos.form.plugin.AbstractFormPlugin;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;

import kd.bos.servicehelper.BusinessDataServiceHelper;
import kd.bos.servicehelper.workflow.MessageCenterServiceHelper;
import kd.bos.workflow.engine.msg.info.MessageInfo;


import java.util.EventObject;

/**
 * 动态表单
 */
public class Demo2 extends AbstractFormPlugin {
    @Override
    public void registerListener(EventObject e) {
        super.registerListener(e);
        this.addClickListeners("btnok");
    }

    @Override
    public void beforeClick(BeforeClickEvent evt) {
        super.beforeClick(evt);

        //查询物品是否存在
        Object xhzhObjectname = this.getModel().getValue("xhzh_objectname");

        DynamicObject[] supplies = BusinessDataServiceHelper.load("tk_xhzh_supplication",
                "fnumber,fname",new QFilter[] {new QFilter("fname", QCP.equals,xhzhObjectname)});

        if(supplies == null || supplies.length == 0){
            return;
        }

        //
        this.getView().showMessage(xhzhObjectname+"已存在");
        evt.setCancel(true);
    }

    @Override
    public void click(EventObject evt) {
        super.click(evt);

        Object suppliername = this.getModel().getValue("xhzh_objectname");
        Object xhzhReason = this.getModel().getValue("xhzh_reason");

        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setSenderId(RequestContext.get().getCurrUserId());
        messageInfo.setContent("[申请物品]----"+suppliername+"\n [申请理由]----"+xhzhReason);
        messageInfo.setId(RequestContext.get().getCurrUserId());

        MessageCenterServiceHelper.sendMessage(messageInfo);
        this.getView().showMessage("已发送给管理员");
    }
}