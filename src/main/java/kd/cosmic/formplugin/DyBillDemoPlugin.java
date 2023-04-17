package kd.cosmic.formplugin;

import kd.bos.context.RequestContext;
import kd.bos.form.ClientProperties;
import kd.bos.form.plugin.AbstractFormPlugin;
import kd.bos.openapi.common.util.KHashMap;
import kd.bos.servicehelper.org.OrgUnitServiceHelper;
import kd.bos.servicehelper.user.UserServiceHelper;
import kd.sdk.plugin.Plugin;

import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态表单
 */
public class DyBillDemoPlugin extends AbstractFormPlugin {

    @Override
    public void afterCreateNewData(EventObject e) {
        super.afterCreateNewData(e);

        //获取部门&&公司的id
        String userId = String.valueOf(RequestContext.get().getCurrUserId());

        //查询公司&&部门
        long userMainOrgId = UserServiceHelper.getUserMainOrgId(Long.parseLong(userId));

        Map<String, Object> companyfromOrg = OrgUnitServiceHelper.getCompanyfromOrg(userMainOrgId);


        this.getModel().setValue("xhzh_applyorg",companyfromOrg.get("id"));
    }

    //修改单据状态颜色
    @Override
    public void afterBindData(EventObject e) {
        super.afterBindData(e);
        String billstatus = (String) this.getModel().getValue("billstatus");
        Map<String, Object> objectObjectHashMap = new HashMap<>();
        if("A".equals(billstatus)){
            objectObjectHashMap.put(ClientProperties.ForeColor,"red");
        }else if("B".equals(billstatus)){
            objectObjectHashMap.put(ClientProperties.ForeColor,"green");
        }else{
            objectObjectHashMap.put(ClientProperties.ForeColor,"blue");
        }

        this.getView().updateControlMetadata("billstatus",objectObjectHashMap);
    }
}