package kd.cosmic.opreate;

import kd.bos.entity.ExtendedDataEntity;
import kd.bos.entity.formula.RowDataModel;
import kd.bos.entity.validate.AbstractValidator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Myvalidator extends AbstractValidator {

    public void initializeConfiguration(){
        super.initializeConfiguration();

        //获得标识  （表单的名称）
        this.entityKey = "xhzh_purapply";
    }
    public void initialize(){
        super.initialize();
    }

    /**
     * 执行自定义校验
     */
    @Override
    public void validate() {
        //定义一个行数据存取模型，用于方便的读取本实体，父实体，单据头的字段

        RowDataModel rowDataModel = new RowDataModel(this.entityKey, this.getValidateContext().getSubEntityType());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //逐行检验
        for (ExtendedDataEntity rowDataEntity : this.getDataEntities()){
            rowDataModel.setRowContext(rowDataEntity.getDataEntity());
            Date xhzhApplydate = (Date) rowDataModel.getValue("xhzh_applydate");
            Date xhzhEndate = (Date) rowDataModel.getValue("xhzh_endate");
            if (xhzhApplydate.compareTo(xhzhEndate)>0){
                //校验不通过，出错误提示
                this.addErrorMessage(rowDataEntity,String.format("申请日期(%)不能晚于结束日期(%)!",simpleDateFormat.format(xhzhApplydate),
                        simpleDateFormat.format(xhzhEndate)));
            }
        }
    }
}
