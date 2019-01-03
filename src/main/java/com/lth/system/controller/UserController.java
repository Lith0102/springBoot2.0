package com.lth.system.controller;

import com.core.Authorize;
import com.lth.system.service.UserService;
import com.util.Page;
import com.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/admin/system/user")
public class UserController {
	@Autowired
	UserService userService;

	/**
	 * 跳转用户管理列表页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/userList")
	@Authorize(setting = "用户-用户列表,管理员-管理员列表",type= Authorize.AuthorizeType.ONE)
	public ModelAndView adminList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String userType = request.getParameter("userType");
		map.put("role", userService.selRoleByIdentify2(userType));
		map.put("userType", userType);
		return new ModelAndView("/admin/system/user/userList", map);
	}
	
	/**
	 * 获取用户列表数据
	 * 
	 * @param request
	 * @param page		当前页
	 * @param limit		页大小
	 */
	@RequestMapping(value = "/userListData")
	@ResponseBody
	public Map<String, Object> adminlistData(HttpServletRequest request, int page, int limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> maps = new HashMap<String, Object>();
		String userType = request.getParameter("userType");
		maps.put("userType", userType);
		String userName = request.getParameter("userName");
		String roleName = request.getParameter("roleName");
		maps.put("userName", userName);
		maps.put("roleName", roleName);
		//计算起始值
		long totalCount = userService.getTotalCount2(maps);
		maps.put("start", (page-1)*limit);
		maps.put("pageSize",limit);
		List<Map<String, Object>> list = userService.selectAdminUser(maps);
		map.put("msg", "");
		map.put("code", 0);
		map.put("data", list);
		map.put("count", totalCount);
		return map;
	}

	/**
	 * 弹出用户添加页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/adds")
	@Authorize(setting = "管理员-管理员添加,用户-用户添加",type= Authorize.AuthorizeType.ONE)
	public ModelAndView addUrls(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String userType = request.getParameter("userType");
		map.put("role", userService.selRoleByIdentify2(userType));
		map.put("_op", "add");
		map.put("userType", userType);
		return new ModelAndView("/admin/system/user/userAdd", map);
	}

	/**
	 * 添加用户
	 */
	@RequestMapping("/addAdmin")
	@ResponseBody
	public Map<String, Object> addAdmin(@RequestParam Map<String, Object> param, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		int count = userService.selByName(param);
		if (count > 0) {
			map.put("success", false);
			map.put("msg", "该用户名已存在！");
			return map;
		}
		count = userService.selByPhone(param);
		if (count > 0) {
			map.put("success", false);
			map.put("msg", "该手机号已存在！");
			return map;
		}
		String userPwd1 = param.get("userPwd") + "";
		if (!StringUtils.isEmpty(userPwd1)) {
			String userPwd = Tools.md5(userPwd1);
			param.put("userPwd", userPwd);
		}
		/*String[] roleid = request.getParameterValues("roleId");
		for (String str : roleid) {
			list.add(str);
		}*/
		String roleid = request.getParameter("roleId");
		list = Arrays.asList(roleid.split(","));
		String birthDate = param.get("birthDate") + "";
		if (StringUtils.isEmpty(birthDate)) {
			param.put("birthDate", null);
		}
		param.put("roleId", list);
		int row = userService.addAdminUserInfo(param);
		//int ro = userService.addUserRole(param);
		if (row > 0) {
			map.put("success", true);
			map.put("msg", "添加成功！");
		} else {
			map.put("success", false);
			map.put("msg", "添加失败！");
		}
		return map;
	}

	/**
	 * 弹出用户修改页面
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/adminUpdate")
	@Authorize(setting = "管理员-管理员修改,用户-用户修改",type= Authorize.AuthorizeType.ONE)
	public ModelAndView adminUpdate(HttpServletRequest request) {
		// 通过ID查询单个会员
		String userId = request.getParameter("userId");
		String userType = request.getParameter("userType");
		Map<String, Object> map = new HashMap<String, Object>();
		//根据用户类型查找对应角色
		map.put("role", userService.selRoleByIdentify2(userType));
		//根据用户查询其所有觉角色
		map.put("userRole", net.sf.json.JSONArray.fromObject(userService.selUserRole(userId)));
		Map<String, Object> userMap = userService.selectUserInfoById(userId);
		map.put("userMap", userMap);
		map.put("_op", "update");
		return new ModelAndView("/admin/system/user/userAdd", map);
	}

	/**
	 * 修改用户信息
	 * 
	 * @param param
	 * @param request
	 * @return
	 */
	@RequestMapping("/editAdminUser")
	@ResponseBody
	public Map<String, Object> editAdminUser(@RequestParam Map<String, Object> param, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		String userPwd1 = param.get("userPwd") + "";
		if (!StringUtils.isEmpty(userPwd1)) {
			String userPwd = Tools.md5(userPwd1);
			param.put("userPwd", userPwd);
		}
		String roleid = request.getParameter("roleId");
		list = Arrays.asList(roleid.split(","));
		String birthDate = param.get("birthDate") + "";
		if (StringUtils.isEmpty(birthDate)) {
			param.put("birthDate", null);
		}
		param.put("roleId", list);
		//int row = userService.updateUser(param);
		int ro = userService.addUserRoles(param);
		if (ro > 0) {
			map.put("success", true);
			map.put("msg", "修改成功！");
		} else {
			map.put("success", false);
			map.put("msg", "修改失败！");
		}
		return map;
	}

	/**
	 * 单个或批量删除后台用户
	 * 
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delAdminUser")
	@Authorize(setting = "管理员-管理员删除,用户-用户删除",type= Authorize.AuthorizeType.ONE)
	public Map<String, Object> delAdminUser(@RequestParam("userId") String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> parMap = new HashMap<String, Object>();
		String[] userIds = userId.split(",");
		parMap.put("userId", userIds);
		int row = userService.deleteUser(parMap);
		if (row > 0) {
			map.put("result", true);
			map.put("msg", "删除成功！");
		} else {
			map.put("result", false);
			map.put("msg", "删除失败！");
		}
		return map;
	}

	/**
	 * 单个或批量冻结用户
	 * 
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/lockAdminUser")
	//@Authorize(setting = "用户-管理员冻结")
	public Map<String, Object> lockAdminUser(@RequestParam("userId") String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> parMap = new HashMap<String, Object>();
		String[] userIds = userId.split(",");
		parMap.put("userId", userIds);
		int row = userService.lockUser(parMap);
		if (row > 0) {
			map.put("success", true);
			map.put("msg", "冻结成功！");
		} else {
			map.put("success", false);
			map.put("msg", "冻结失败！");
		}
		return map;
	}

	/**
	 * 单个或批量解冻用户
	 * 
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/unlockAdminUser")
	//@Authorize(setting = "用户-管理员解冻")
	public Map<String, Object> unlockAdminUser(@RequestParam("userId") String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> parMap = new HashMap<String, Object>();
		String[] userIds = userId.split(",");
		parMap.put("userId", userIds);
		int row = userService.unlockUser(parMap);
		if (row > 0) {
			map.put("success", true);
			map.put("msg", "解冻成功！");
		} else {
			map.put("success", false);
			map.put("msg", "解冻失败！");
		}
		return map;
	}

}
