package kd.cosmic.formplugin;

import kd.bos.context.RequestContext;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.form.plugin.AbstractFormPlugin;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.BusinessDataServiceHelper;
import kd.bos.servicehelper.org.OrgUnitServiceHelper;
import kd.bos.servicehelper.user.UserServiceHelper;
import kd.sdk.plugin.Plugin;

import java.util.EventObject;
import java.util.Map;

/**
 * 动态表单
 */
public class selfInfomation extends AbstractFormPlugin implements Plugin{

    @Override
    public void afterCreateNewData(EventObject e) {
        super.afterCreateNewData(e);

        String userId = String.valueOf(RequestContext.get().getCurrUserId());
        //this.getView().showMessage(userId+"\n");
        String userName = String.valueOf(RequestContext.get().getUserName());
        long userMainOrgId = UserServiceHelper.getUserMainOrgId(Long.parseLong(userId));
        DynamicObject info = BusinessDataServiceHelper.loadSingle("xhzh_person_information",
                "xhzh_idnumber,xhzh_dormitory1," +
                        "xhzh_yuanxi1,xhzh_major1,xhzh_inyear1," +
                        "xhzh_class1,xhzh_phonenumber1,xhzh_intime1," +
                        "xhzh_xuejizhuangtai1,xhzh_email1,xhzh_telephone1"
                        ,new QFilter[] {new QFilter("number", QCP.equals,userId)});

        Map<String, Object> companyfromOrg = OrgUnitServiceHelper.getCompanyfromOrg(userMainOrgId);

        this.getModel().setValue("xhzh_idorg",companyfromOrg.get("id"));
        this.getModel().setValue("useorg",companyfromOrg.get("id"));
        this.getModel().setValue("createorg",companyfromOrg.get("id"));
        this.getModel().setValue("name",userName);
        this.getModel().setValue("number",userId);
        if(info == null){
            return;
        }

        this.getModel().setValue("xhzh_idnumber",info.getString("xhzh_idnumber"));
        this.getModel().setValue("xhzh_dormitory1",info.getString("xhzh_dormitory1"));
        this.getModel().setValue("xhzh_yuanxi1",info.getString("xhzh_yuanxi1"));
        this.getModel().setValue("xhzh_major1",info.getString("xhzh_major1"));
        this.getModel().setValue("xhzh_inyear1",info.getString("xhzh_inyear1"));
        this.getModel().setValue("xhzh_class1",info.getString("xhzh_class1"));
        this.getModel().setValue("xhzh_phonenumber1",info.getString("xhzh_phonenumber1"));
        this.getModel().setValue("xhzh_intime1",info.getString("xhzh_intime1"));
        this.getModel().setValue("xhzh_xuejizhuangtai1",info.getString("xhzh_xuejizhuangtai1"));
        this.getModel().setValue("xhzh_email1",info.getString("xhzh_email1"));
        this.getModel().setValue("xhzh_telephone1",info.getString("xhzh_telephone1"));


    }
}