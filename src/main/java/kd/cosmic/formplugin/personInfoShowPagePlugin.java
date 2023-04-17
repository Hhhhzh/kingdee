package kd.cosmic.formplugin;

import com.kingdee.util.StringUtils;
import dm.jdbc.util.StringUtil;
import kd.bos.context.RequestContext;
import kd.bos.dataentity.OperateOption;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.entity.operate.result.OperationResult;
import kd.bos.form.control.Button;
import kd.bos.form.control.Control;
import kd.bos.form.control.events.BeforeClickEvent;
import kd.bos.form.plugin.AbstractFormPlugin;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.BusinessDataServiceHelper;
import kd.bos.servicehelper.QueryListParameter;
import kd.bos.servicehelper.QueryServiceHelper;
import kd.bos.servicehelper.operation.OperationServiceHelper;
import kd.bos.servicehelper.operation.SaveServiceHelper;
import kd.bos.servicehelper.org.OrgUnitServiceHelper;
import kd.bos.servicehelper.user.UserServiceHelper;
import kd.sdk.plugin.Plugin;

import java.util.EventObject;
import java.util.Map;

/**
 * 动态表单
 */
public class personInfoShowPagePlugin extends AbstractFormPlugin {

    @Override
    public void afterCreateNewData(EventObject e) {
        super.afterCreateNewData(e);

        //getUserID
        String userId = String.valueOf(RequestContext.get().getCurrUserId());
        String userName = String.valueOf(RequestContext.get().getUserName());
        long userMainOrgId = UserServiceHelper.getUserMainOrgId(Long.parseLong(userId));
        Map<String, Object> companyfromOrg = OrgUnitServiceHelper.getCompanyfromOrg(userMainOrgId);
        this.getModel().setValue("xhzh_idorg",companyfromOrg.get("id"));
        this.getModel().setValue("xhzh_name",userName);
        this.getModel().setValue("xhzh_number",userId);

        //初始化
        DynamicObject[] tempInfo = BusinessDataServiceHelper.load("xhzh_person_information", "xhzh_idnumber,xhzh_dormitory1" +
                        "xhzh_idnumber,"+
                        "xhzh_yuanxi1,xhzh_major1,xhzh_inyear1," +
                        "xhzh_class1,xhzh_phonenumber1,xhzh_intime1," +
                        "xhzh_xuejizhuangtai1,xhzh_email1,xhzh_telephone1"
                , new QFilter[]{new QFilter("number", QCP.equals, userId)});
        this.getModel().setValue("xhzh_idnumber",tempInfo[0].getString("xhzh_idnumber"));
        this.getModel().setValue("xhzh_yuanxi1",tempInfo[0].getString("xhzh_yuanxi1"));
        this.getModel().setValue("xhzh_major1",tempInfo[0].getString("xhzh_major1"));
        this.getModel().setValue("xhzh_inyear1",tempInfo[0].getString("xhzh_inyear1"));
        this.getModel().setValue("xhzh_class1",tempInfo[0].getString("xhzh_class1"));
        this.getModel().setValue("xhzh_phonenumber1",tempInfo[0].getString("xhzh_phonenumber1"));
        this.getModel().setValue("xhzh_intime1",tempInfo[0].getString("xhzh_intime1"));
        this.getModel().setValue("xhzh_xuejizhuangtai1",tempInfo[0].getString("xhzh_xuejizhuangtai1"));
        this.getModel().setValue("xhzh_email1",tempInfo[0].getString("xhzh_email1"));
        this.getModel().setValue("xhzh_telephone1",tempInfo[0].getString("xhzh_telephone1"));

        //baseInfo
        this.getView().setEnable(false,"xhzh_name");
        this.getView().setEnable(false,"xhzh_number");
        this.getView().setEnable(false,"xhzh_idorg");
        this.getView().setEnable(false,"xhzh_idnumber");

        //detailInfo
        this.getView().setEnable(false,"xhzh_yuanxi1");
        this.getView().setEnable(false,"xhzh_major1");
        this.getView().setEnable(false,"xhzh_inyear1");
        this.getView().setEnable(false,"xhzh_class1");
        this.getView().setEnable(false,"xhzh_phonenumber1");
        this.getView().setEnable(false,"xhzh_intime1");
        this.getView().setEnable(false,"xhzh_xuejizhuangtai1");
        this.getView().setEnable(false,"xhzh_email1");
        this.getView().setEnable(false,"xhzh_telephone1");

        //dormitoryInfo
        this.getView().setEnable(false,"xhzh_dormitory_number");
        this.getView().setEnable(false,"xhzh_dormitory_rom_number");
        this.getView().setEnable(false,"xhzh_bed_number");
        this.getView().setEnable(false,"xhzh_if_living");
        //奖惩


        //button
        this.getView().setEnable(false,"xhzh_save");



    }
    public void registerListener(EventObject e) {
        super.registerListener(e);
        Button xhzhSave = this.getView().getControl("xhzh_save");
        Button xhzhChange = this.getView().getControl("xhzh_change");
        xhzhSave.addClickListener(this);
        xhzhChange.addClickListener(this);
    }

    @Override
    public void beforeClick(BeforeClickEvent evt) {
        super.beforeClick(evt);
        Control source = (Control) evt.getSource();
        if(StringUtils.equals("xhzh_change",source.getKey())){
            String userId = String.valueOf(RequestContext.get().getCurrUserId());
            DynamicObject info = BusinessDataServiceHelper.loadSingle("xhzh_person_information",
                    "xhzh_idnumber"
                    ,new QFilter[] {new QFilter("number", QCP.equals,userId)});
            if (info != null){
                return;
            }
            this.getView().showMessage("请先添加您的信息");
            evt.setCancel(true);
        }
    }

    @Override
    public void click(EventObject evt) {
        super.click(evt);
        Control source = (Control) evt.getSource();
        String userId = String.valueOf(RequestContext.get().getCurrUserId());

        if(StringUtils.equals("xhzh_change",source.getKey())){
            //button lock
            this.getView().setEnable(false,"xhzh_change");
            //button unlock
            this.getView().setEnable(true,"xhzh_save");
            //textfield unlock
            this.getView().setEnable(true,"xhzh_yuanxi1");
            this.getView().setEnable(true,"xhzh_idnumber");
            this.getView().setEnable(true,"xhzh_major1");
            this.getView().setEnable(true,"xhzh_inyear1");
            this.getView().setEnable(true,"xhzh_class1");
            this.getView().setEnable(true,"xhzh_phonenumber1");
            this.getView().setEnable(true,"xhzh_intime1");
            this.getView().setEnable(true,"xhzh_xuejizhuangtai1");
            this.getView().setEnable(true,"xhzh_email1");
            this.getView().setEnable(true,"xhzh_telephone1");
        }else if(StringUtils.equals("xhzh_save",source.getKey())){
            //base and detail info get ,change and save
            DynamicObject[] tempInfo = BusinessDataServiceHelper.load("xhzh_person_information", "xhzh_idnumber,xhzh_dormitory1" +
                            "xhzh_idnumber,"+
                            "xhzh_yuanxi1,xhzh_major1,xhzh_inyear1," +
                            "xhzh_class1,xhzh_phonenumber1,xhzh_intime1," +
                            "xhzh_xuejizhuangtai1,xhzh_email1,xhzh_telephone1"
                    , new QFilter[]{new QFilter("number", QCP.equals, userId)});

            tempInfo[0].set("xhzh_idnumber",this.getModel().getValue("xhzh_idnumber"));
            tempInfo[0].set("xhzh_yuanxi1",this.getModel().getValue("xhzh_yuanxi1"));
            tempInfo[0].set("xhzh_major1",this.getModel().getValue("xhzh_major1"));
            tempInfo[0].set("xhzh_inyear1",this.getModel().getValue("xhzh_inyear1"));
            tempInfo[0].set("xhzh_class1",this.getModel().getValue("xhzh_class1"));
            tempInfo[0].set("xhzh_phonenumber1",this.getModel().getValue("xhzh_phonenumber1"));
            tempInfo[0].set("xhzh_intime1",this.getModel().getValue("xhzh_intime1"));
            tempInfo[0].set("xhzh_xuejizhuangtai1",this.getModel().getValue("xhzh_xuejizhuangtai1"));
            tempInfo[0].set("xhzh_email1",this.getModel().getValue("xhzh_email1"));
            tempInfo[0].set("xhzh_telephone1",this.getModel().getValue("xhzh_telephone1"));
            //TODO
            //其他表，如上操作



            //更新提交
            Object[] result = SaveServiceHelper.save(tempInfo);
            this.getView().setEnable(true,"xhzh_change");
            this.getView().setEnable(false,"xhzh_save");

        }


    }
}