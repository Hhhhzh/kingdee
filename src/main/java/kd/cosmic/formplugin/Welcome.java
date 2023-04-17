package kd.cosmic.formplugin;

import kd.bos.context.RequestContext;
import kd.bos.form.control.events.BeforeClickEvent;
import kd.bos.form.plugin.AbstractFormPlugin;
import kd.sdk.plugin.Plugin;

import java.util.EventObject;

/**
 * 动态表单
 */
public class Welcome extends AbstractFormPlugin  {
    @Override
    public void registerListener(EventObject e) {
        super.registerListener(e);
        this.addClickListeners("xhzh_savebutton1");
    }

    @Override
    public void beforeClick(BeforeClickEvent evt) {
        super.beforeClick(evt);
        Object xhzhName = this.getModel().getValue("xhzh_name");
        Object xhzhNumber = this.getModel().getValue("xhzh_textfield");
        //都不为空，校验通过
        if(xhzhNumber != "" && xhzhName != ""){
            return;
        }
        //有一个或都为空，不走click事件
        this.getView().showMessage("请输入您的学号与姓名");
        evt.setCancel(true);
    }

    @Override
    public void click(EventObject evt) {
        super.click(evt);
        String userName = RequestContext.get().getUserName();
        long currUserId = RequestContext.get().getCurrUserId();
        Object xhzhName = this.getModel().getValue("xhzh_name");
        Object xhzhNumber = this.getModel().getValue("xhzh_textfield");
        if(Long.parseLong((String) xhzhNumber) == currUserId && userName == xhzhName){
            this.getView().showMessage("校验成功");
        }else{
            this.getView().showMessage("请重新输入名字学号");
        }
    }
}