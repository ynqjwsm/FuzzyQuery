package so.wsm.fq.domian;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_information", uniqueConstraints = {@UniqueConstraint(name = "unique_enbid", columnNames = {"enbId", "nameInChinese"})})
public class Information {

    @JSONField(serialize = false)
    @Id
    @GeneratedValue
    private Long id;

    @JSONField(name = "e")
    private Integer enbId;

    @JSONField(name = "b")
    private String branch;

    @JSONField(name = "nc")
    private String nameInChinese;

    @JSONField(name = "ne")
    private String nameInEnglish;

    public ImportInformationItem toPlanObject(){

        ImportInformationItem information = new ImportInformationItem();

        information.setEnbId(this.enbId);
        information.setBranch(this.branch);
        information.setNameInChinese(this.nameInChinese);
        information.setNameInEnglish(this.nameInEnglish);

        return information;

    }

    public static List<ImportInformationItem> toPalnObjects(Collection<Information> items){

        List<ImportInformationItem> informationList = new ArrayList<ImportInformationItem>();

        for(Information item : items){
            informationList.add(item.toPlanObject());
        }

        return informationList;

    }

}
