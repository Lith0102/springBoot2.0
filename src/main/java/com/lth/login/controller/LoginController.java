package com.lth.login.controller;

import com.config.AdminRewriteFilter;
import com.lth.login.service.InitService;
import com.lth.login.service.LoginService;
import com.lth.system.service.RoleService;
import com.util.DataConvert;
import com.util.MapCache;
import com.util.StringHelper;
import com.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: @Litonghui
 * Date: 2018/12/26 12:53
 * Content: 用户登录
 */
@Controller
@RequestMapping(value = "/admin")
public class LoginController {

    @Autowired
    private HttpSession session;
    @Autowired
    private InitService initService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private RoleService roleService;


    /**
     * 跳转登录页面
     * @param map
     * @return
     */
    @RequestMapping(value = {"","/login"})
    public ModelAndView toLogin(@RequestParam Map<String,Object> map){
        //SiteInfo siteInfo = setting.getSetting(SiteInfo.class);
        //session.setAttribute("siteInfo",siteInfo);
        if(session.getAttribute("loginUser")!=null&&!StringHelper.IsNullOrEmpty(DataConvert.ToString(session.getAttribute("loginUser")))){
            return new ModelAndView("redirect:/"+ AdminRewriteFilter.adminPrefix +"/index");
        }
        return new ModelAndView("/admin/login/newLogin",map);
    }

    @RequestMapping(value = "/login/userLogin")
    @ResponseBody
    public Map<String,Object> getUserInfo(@RequestParam Map login){
        Map<String,Object> map = new HashMap<String, Object>();
        String name = DataConvert.ToString(login.get("userName"));
        String pwd = DataConvert.ToString(login.get("userPwd"));
        String code = DataConvert.ToString(session.getAttribute("code"));
        String valiCode = DataConvert.ToString(login.get("code"));
        if(StringUtils.isEmpty(name)||StringUtils.isEmpty(pwd)){
            map.put("isLogin",false);
            map.put("message","用户名或密码不得为空!");
        }else if(StringUtils.isEmpty(valiCode)){
            map.put("isLogin",false);
            map.put("message","验证码不能为空!");
        }else if(!valiCode.equalsIgnoreCase(code)){
            map.put("isLogin",false);
            map.put("message","验证码不正确!");
        }else{
            String md5pwd = Tools.md5(pwd);
            login.put("userPwd",md5pwd);
            Map<String,Object> userMap = initService.login(login);
            if(userMap!=null&&!userMap.isEmpty()){
                session.setAttribute("userId",DataConvert.ToString(userMap.get("userId")));
                session.setAttribute("userType",DataConvert.ToString(userMap.get("userType")));
                session.setAttribute("loginUser",name);
                session.setAttribute("adminRealName",DataConvert.ToString(userMap.get("realname")));

                //TODO 加入缓存值,真正开发时此处的值通过用户表取出
                MapCache.getInstance();
                Map<String,Object> codeMap = new HashMap<String, Object>();
                codeMap.put("DBIp",userMap.get("ip"));
                codeMap.put("DBName",userMap.get("DBName"));
//                codeMap.put("DBIp","192.168.2.188");
//                codeMap.put("DBName","master");
                MapCache.putCache("user_"+DataConvert.ToString(userMap.get("userId")),codeMap);

                //查询用户权限放入session
                List<String> Alist = roleService.selAuthorityByUserId(userMap.get("userId") + "");
                //查询用户的角色Id
                List<String> roleId = roleService.selectRoleIdByUserId(userMap.get("userId") + "");
                session.setAttribute("AuthorityID", Alist);
                session.setAttribute("roleId", roleId);
                map.put("isLogin",true);
            }else{
                map.put("isLogin",false);
                map.put("message","用户名或密码错误!");
            }
        }
        return map;
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/login/loginOut")
    public ModelAndView loginOut(){
        String userId = DataConvert.ToString(session.getAttribute("userId"));
        if(StringUtils.isEmpty(userId)){
            userId = DataConvert.ToString(session.getAttribute("indexUserId"));
        }
        session.invalidate();
        return new ModelAndView("/admin/login/timeout");
    }

    /**
     * 跳转修改密码页面
     */
    @RequestMapping(value = "/login/toUpPwd")
    public ModelAndView updatePwd(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("userId",DataConvert.ToString(session.getAttribute("userId")));
        return new ModelAndView("/admin/login/modifyPwd",map);
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "/login/updatePwd")
    @ResponseBody
    public Map updatePwd(@RequestParam Map map){
        Map reqMap = loginService.updatePwd(map);
        return reqMap;
    }

}
