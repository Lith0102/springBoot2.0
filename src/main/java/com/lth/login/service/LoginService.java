package com.lth.login.service;

import com.util.DataConvert;
import com.util.Tools;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService{

	@Autowired
    SqlSessionFactory sqlSessionFactory;
	@Autowired
    SqlSession sqlSession;

	/**
	 * 修改密码
	 * 
	 * @param map
	 * @return
	 */
	public Map<String, Object> updatePwd(Map<String, Object> map) {
		Map<String,Object> reqMap = new HashMap<String, Object>();
		try {
			//判断两次密码输入是否一致
			if(!DataConvert.ToString(map.get("newPwd")).equals(DataConvert.ToString(map.get("confirmPwd")))){
				reqMap.put("success",false);
				reqMap.put("msg","两次密码输入不一致");
				return reqMap;
			}
			map.put("userPwd", Tools.md5(DataConvert.ToString(map.get("newPwd"))));
			int row = sqlSession.update("loginDao.updatePwd", map);
			if (row > 0) {
				reqMap.put("success", true);
				reqMap.put("msg", "修改成功,请重新登录!");
			} else {
				reqMap.put("success", false);
				reqMap.put("msg", "修改失败!");
			}
		}catch (Exception e){
			e.getMessage();
		}
		return reqMap;
	}



}
