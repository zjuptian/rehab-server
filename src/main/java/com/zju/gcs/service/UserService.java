package com.zju.gcs.service;

import com.zju.gcs.model.UserDO;
import com.zju.gcs.vo.AccessVO;
import com.zju.gcs.vo.LoginVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public interface UserService {
    UserDO getUser(LoginVO loginVO, HttpServletRequest request);

    void addUser(UserDO userDO);

    void updateAccess(AccessVO accessVO);

    List<AccessVO> getAccess();

    UserDO getUserInfo(Integer user_id);


}
