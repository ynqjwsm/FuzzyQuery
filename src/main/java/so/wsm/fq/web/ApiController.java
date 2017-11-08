package so.wsm.fq.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import so.wsm.fq.domian.Information;
import so.wsm.fq.domian.InformationRepository;

import java.util.ArrayList;


@Controller
@RequestMapping(value = {"/api"})
public class ApiController {

    @Autowired
    private InformationRepository informationRepository;

    @ResponseBody
    @PostMapping(value = {"/fuzzy"})
    public Object fuzzyQuery(@RequestParam(defaultValue = "") String param){
        if(null == param || "".equals(param)){
            return new ArrayList<Information>();
        }
        return informationRepository
                .findTop20ByNameInChineseContainingOrNameInEnglishContaining(param, param);
    }

    @ResponseBody
    @PostMapping(value = {"/exact"})
    public Object exactQuery(@RequestParam(defaultValue = "0") Integer param){
        if(null == param || param <= 0){
            return new ArrayList<Information>();
        }
        return informationRepository.findTop20ByEnbIdIs(param);
    }

}
