package com.zju.gcs.Controller;

import com.zju.gcs.common.exception.NirException;
import com.zju.gcs.common.exception.NirExceptionEnum;
import com.zju.gcs.common.result.Result;
import com.zju.gcs.common.result.ResultEnum;
import com.zju.gcs.model.PatientDO;
import com.zju.gcs.service.PatientService;
import com.zju.gcs.vo.AddPatientVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @PostMapping("addPatient")
    public Result<Void> register(@RequestBody AddPatientVO addPatientVO) {
        Result<Void> result = new Result<>();
        PatientDO patientDO = new PatientDO();
        try {
            // 多余的参数没有补充 可以在构造函数里面加入
            BeanUtils.copyProperties(addPatientVO, patientDO);
            patientService.addPatient(patientDO);
        } catch (NirException e) {
            result.setCode(e.getCode());
            result.setMsg(e.getMessage());
            return result;
        } catch (Exception e) {
            result.setCode(NirExceptionEnum.GENERAL_EXCEPTION.getCode());
            result.setMsg(e.getMessage());
            return result;
        }
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        return result;
    }
}
