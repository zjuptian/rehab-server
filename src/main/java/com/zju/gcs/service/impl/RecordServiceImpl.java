package com.zju.gcs.service.impl;

import com.zju.gcs.mapper.PatientDOMapper;
import com.zju.gcs.mapper.RecordDOMapper;
import com.zju.gcs.model.HistoryRecordDO;
import com.zju.gcs.model.RecordDO;
import com.zju.gcs.model.RecordDOExample;
import com.zju.gcs.service.RecordService;
import com.zju.gcs.vo.HistoryRecordsVO;
import com.zju.gcs.vo.UploadRecordVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    @Value("${rehab.file.infraBeforeFile}")
    private String INFRA_BEFORE_FILE;

    @Value("${rehab.file.infraAfterFile}")
    private String INFRA_AFTER_FILE;

    @Value("${rehab.file.infraBeforeTxt}")
    private String INFRA_BEFORE_TXT;

    @Value("${rehab.file.infraAfterTxt}")
    private String INFRA_AFTER_TXT;
//    @Value("${rehab.file.location}")
//    private String BASE_PATH;

    @Autowired
    private RecordDOMapper recordMapper;

    @Autowired
    private PatientDOMapper patientMapper;



    @Override
    public void uploadRecord(UploadRecordVO uploadRecordVO) throws IOException {
        RecordDO recordDO = new RecordDO();
        BeanUtils.copyProperties(uploadRecordVO,recordDO);
        String infraBeforePath = addFile(uploadRecordVO.getInfraBeforeFile(),0);
        recordDO.setInfraBeforePath(infraBeforePath);
        String infraAfterPath = addFile(uploadRecordVO.getInfraAfterFile(),1);
        recordDO.setInfraAfterPath(infraAfterPath);
        String infraBeforeTxtPath = addFile(uploadRecordVO.getInfraBeforeTxt(),2);
        recordDO.setInfraBeforeTxtPath(infraBeforeTxtPath);
        String infraAfterTxtPath = addFile(uploadRecordVO.getInfraAfterTxt(),3);
        recordDO.setInfraAfterTxtPath(infraAfterTxtPath);
        RecordDOExample recordDOExample = new RecordDOExample();
        recordDOExample.createCriteria().andPatientIdEqualTo(uploadRecordVO.getPatientId());
        Integer count = recordMapper.countByExample(recordDOExample);
        recordDO.setTreatCount(count+1);
        recordMapper.insertSelective(recordDO);
    }

    @Override
    public String addFile(MultipartFile file,Integer param) throws IOException {
        if (file == null) {
            return "";
        }
        String fileName = file.getOriginalFilename();
        String fullPath = (param == 0 ? INFRA_BEFORE_FILE: param == 1 ? INFRA_AFTER_FILE : param == 2 ? INFRA_BEFORE_TXT :
        INFRA_AFTER_TXT) + File.separator + fileName;
        File newFile = new File(fullPath);
        if(!newFile.getParentFile().exists()){
            newFile.getParentFile().mkdirs();
        }
        file.transferTo(newFile);
        return fileName;
    }

    @Override
    public List<HistoryRecordDO> getHistoryRecords(HistoryRecordsVO historyRecordsVO) {
        return recordMapper.selectHistoryRecords(historyRecordsVO);
    }


}
