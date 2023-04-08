package kd.cosmic.formplugin;

import kd.bos.bill.AbstractBillPlugIn;
import kd.bos.servicehelper.user.UserServiceHelper;

import java.util.EventObject;

public class PresaleApplyPlugin extends AbstractBillPlugIn {

    public void afterCreateNewData(EventObject e){
        super.afterCreateNewData(e);

        long userId = UserServiceHelper.getCurrentUserId();
        this.getModel().setValue("xhzh_applier",userId);

    }
}
