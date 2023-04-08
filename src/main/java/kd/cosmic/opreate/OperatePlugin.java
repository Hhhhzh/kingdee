package kd.cosmic.opreate;

import kd.bos.entity.plugin.AbstractOperationServicePlugIn;
import kd.bos.entity.plugin.AddValidatorsEventArgs;
import kd.bos.entity.plugin.PreparePropertysEventArgs;
import kd.bos.newdevportal.domaindefine.sample.validator.MyValidator;

public class OperatePlugin extends AbstractOperationServicePlugIn {
    /**
     * 执行操作前，准备加载单据数据钱，出发时间
     * 插件在此事件作用为加载指定的字段
     * @param e
     */
    @Override
    public void onPreparePropertys(PreparePropertysEventArgs e) {
        e.getFieldKeys().add("xhzh_applydate");
        e.getFieldKeys().add("xhzh_endate");
//        e.getFieldKeys().add("");
//        e.getFieldKeys().add("");
//        e.getFieldKeys().add("");
        super.onPreparePropertys(e);

    }
    @Override
    public void onAddValidators(AddValidatorsEventArgs e) {
        //添加自定义校验器
        e.addValidator(new Myvalidator());
        super.onAddValidators(e);
    }


}
