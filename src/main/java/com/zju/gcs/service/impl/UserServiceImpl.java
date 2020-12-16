package com.zju.gcs.service.impl;

import com.zju.gcs.common.exception.NirException;
import com.zju.gcs.common.exception.NirExceptionEnum;
import com.zju.gcs.common.util.MD5Util;
import com.zju.gcs.mapper.DoctorDOMapper;
import com.zju.gcs.mapper.UserDOMapper;
import com.zju.gcs.model.DoctorDO;
import com.zju.gcs.model.DoctorDOExample;
import com.zju.gcs.model.UserDO;
import com.zju.gcs.model.UserDOExample;
import com.zju.gcs.service.UserService;
import com.zju.gcs.vo.AccessVO;
import com.zju.gcs.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDOMapper userMapper;

    @Autowired
    private DoctorDOMapper doctorDOMapper;

    @Override
    public UserDO getUser(LoginVO loginVO, HttpServletRequest request) {
        String name = loginVO.getUsername();
        String pwd = MD5Util.md5Encode(loginVO.getPassword());
        HttpSession session = request.getSession();
        loginVO.setPassword(pwd);
        UserDOExample example = new UserDOExample();
        example.createCriteria().andUsernameEqualTo(name).andPasswordEqualTo(pwd);

        List<UserDO> users = userMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(users)) {
            throw new NirException(NirExceptionEnum.NO_USER_FOUND);
        }

        UserDO user = users.get(0);

        session.setAttribute("userId", user.getId());

        return user;
    }

    @Override
    public void addUser(UserDO userDO) {
        String username = userDO.getUsername();
        String password = MD5Util.md5Encode(userDO.getPassword());
        UserDOExample example = new UserDOExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UserDO> users = userMapper.selectByExample(example);

        if (!CollectionUtils.isEmpty(users)) {
            throw new NirException(NirExceptionEnum.USER_EXIST);
        }
        else{
            userDO.setPassword(password);
            userMapper.insert(userDO);
        }
    }

    @Override
    public void updateAccess(AccessVO accessVO) {
        DoctorDO doctorDO = doctorDOMapper.selectByPrimaryKey(accessVO.getDoctorId());
        int user_id = doctorDO.getUserId();
        UserDO userDO = new UserDO();
        userDO.setId(user_id);
        int[] accessArray = accessVO.getAccessArray();
        for(int choice : accessArray){
            if(choice == 0){
                userDO.setCreatePatientinfo(1);
            }
            else if(choice == 1){
                userDO.setQueryPatientinfo(1);
            }
            else if(choice == 2){
                userDO.setCreateTreatrecord(1);
            }
            else if(choice == 3){
                userDO.setQueryCase(1);
            }
            else if(choice == 4){
                userDO.setAnalysisAi(1);
            }
//            else if(choice == 5){
//                userDO.setCreateTreatplan(1);
//            }
            else if(choice == 5){
                userDO.setControlAccess(1);
            }
        }
        userMapper.updateAccessByPrimaryKey(userDO);
    }

    @Override
    public List<AccessVO> getAccess() {
        DoctorDOExample doExample = new DoctorDOExample();
        List<DoctorDO> list = doctorDOMapper.selectByExample(doExample);
        List<AccessVO> result = new ArrayList<AccessVO>();
        for(DoctorDO doctor : list){
            AccessVO accessVO = new AccessVO();
            accessVO.setDoctorId(doctor.getId());
            accessVO.setDoctorName(doctor.getName());
            accessVO.setDepartment(doctor.getDepartment());
            int user_id = doctor.getUserId();
            UserDO userDO = userMapper.selectByPrimaryKey(user_id);
            List<Integer> accessArray = new ArrayList<Integer>();
            if(userDO.getCreatePatientinfo().equals(1)){
                accessArray.add(0);
            }
            if(userDO.getQueryPatientinfo().equals(1)){
                accessArray.add(1);
            }
            if(userDO.getCreateTreatrecord().equals(1)){
                accessArray.add(2);
            }
            if(userDO.getQueryCase().equals(1)){
                accessArray.add(3);
            }
            if(userDO.getAnalysisAi().equals(1)){
                accessArray.add(4);
            }
//            if(userDO.getCreateTreatplan().equals(1)){
//                accessArray.add(5);
//            }
            if(userDO.getControlAccess().equals(1)){
                accessArray.add(5);
            }
            int[] accessList = new int[accessArray.size()];
            for(int i =0; i<accessArray.size(); i++){
                accessList[i] = accessArray.get(i);
            }
            accessVO.setAccessArray(accessList);
            result.add(accessVO);
        }

        return result;
    }

    @Override
    public UserDO getUserInfo(Integer user_id) {
        UserDO userInfo = userMapper.selectByPrimaryKey(user_id);
        return userInfo;
    }
}
