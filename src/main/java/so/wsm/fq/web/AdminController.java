package so.wsm.fq.web;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import so.wsm.fq.config.ManagerConfig;
import so.wsm.fq.domian.ImportInformationItem;
import so.wsm.fq.domian.Information;
import so.wsm.fq.domian.InformationRepository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = {"/manage"})
public class AdminController {

    @Autowired
    private InformationRepository informationRepository;

    @GetMapping(value = {"/page"})
    public String page(@RequestParam(required = false) @ModelAttribute String message){
        return "manage/manage";
    }

    @GetMapping(value = {"/empty"})
    public String empty(Model model){
        informationRepository.deleteAllInBatch();
        model.addAttribute("message", "清空数据成功!");
        return "manage/result";
    }

    @GetMapping(value = {"/display"})
    public String display(Model model){
        model.addAttribute("data", informationRepository.findAll());
        return "manage/display";
    }

    @GetMapping(value = {"/delete"})
    public String delete(@RequestParam Long id){
        informationRepository.delete(id);
        return "redirect:/manage/display";
    }

    /* 0:全量覆盖 1:增量导入  */
    @PostMapping(value = {"/update"})
    public String update(@RequestParam(name = "method", defaultValue = "0") Integer method,
                         @RequestParam(name = "file") MultipartFile file,
                         Model model) throws Exception {

        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(1);
        importParams.setHeadRows(1);

        List<ImportInformationItem> data = ExcelImportUtil.importExcel(file.getInputStream(), ImportInformationItem.class, importParams);

        if(method == 0){
            informationRepository.deleteAllInBatch();
            informationRepository.save(ImportInformationItem.toEntities(data));
            model.addAttribute("message", "全量数据替换成功!");
        }else if(method == 1){
            List<Information> informationList = ImportInformationItem.toEntities(data);
            for(Information information : informationList){
                Information inStore = informationRepository.findByEnbIdAndNameInChineseIs(information.getEnbId(), information.getNameInChinese());
                if(null != inStore){
                    informationRepository.delete(inStore.getId());
                }
                informationRepository.save(information);
            }
            model.addAttribute("message", "增量导入数据成功!");
        }
        return "manage/result";
    }

    @GetMapping(value = {"/template"})
    public ResponseEntity<byte[]> template(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "template.xls");
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("站点信息数据","数据"), ImportInformationItem.class, new ArrayList<ImportInformationItem>());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(outputStream.toByteArray(),
                headers, HttpStatus.CREATED);
    }


    @GetMapping(value = {"/download"})
    public ResponseEntity<byte[]> download(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "data.xls");
        List<ImportInformationItem> items  = Information.toPalnObjects(informationRepository.findAll());
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("站点信息数据","数据"), ImportInformationItem.class, items);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(outputStream.toByteArray(),
                headers, HttpStatus.CREATED);
    }

}
