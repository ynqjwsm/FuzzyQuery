package so.wsm.fq.domian;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportInformationItem {

    @Excel(name = "基站编号", width = 12.0D)
    private Integer enbId;

    @Excel(name = "分公司", width = 24.0D)
    private String branch;

    @Excel(name = "中文站名", width = 72.0D)
    private String nameInChinese;

    @Excel(name = "英文站名", width = 72.0D)
    private String nameInEnglish;

    public boolean valid(){
        return null != enbId && enbId > 0
                && null != branch && !"".equals(branch)
                && null != nameInChinese && !"".equals(nameInChinese)
                && null != nameInEnglish && !"".equals(nameInEnglish);
    }

    public Information toEntity(){

        Information information = new Information();

        information.setEnbId(this.enbId);
        information.setBranch(this.branch);
        information.setNameInChinese(this.nameInChinese);
        information.setNameInEnglish(this.nameInEnglish);

        return information;

    }

    public static List<Information> toEntities(Collection<ImportInformationItem> items){

        List<Information> informationList = new ArrayList<Information>();

        for(ImportInformationItem item : items){

            if(!item.valid()) continue;

            Information information = new Information();

            information.setEnbId(item.enbId);
            information.setBranch(item.branch);
            information.setNameInChinese(item.nameInChinese);
            information.setNameInEnglish(item.nameInEnglish);

            informationList.add(information);
        }

        return informationList;

    }

}
