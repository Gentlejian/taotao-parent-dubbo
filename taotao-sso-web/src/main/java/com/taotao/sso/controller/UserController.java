package com.taotao.sso.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 张剑锋
 * @date 2020/3/15 - 15:23
 *
 * 用户处理Controller
 */
@Controller
public class UserController {

    @Resource
    private UserService userService;
    @Value("${TT_TOKEN}")
    private String TT_TOKEN;

    /**  检查数据是否可用  */
    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public TaotaoResult checkUserData(@PathVariable String param,
                                      @PathVariable Integer type) {
        TaotaoResult result = userService.checkData(param, type);
        return result;
    }

    /**  注册 post请求 */
    @RequestMapping(value="/user/register",method=RequestMethod.POST)
    @ResponseBody
    public TaotaoResult register(TbUser user) {
        TaotaoResult result = userService.register(user);
        return result;
    }

    /**  登录  */
    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(String username,
                              String password,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        TaotaoResult result = userService.login(username, password);
        //登录成功后写cookie
        if (result.getStatus() == 200) {
            //把 token 写入 cookie
            CookieUtils.setCookie(request,response,TT_TOKEN,result.getData().toString());
        }
        return result;
    }

    /**  根据token进行查询数据库 */
    /*@RequestMapping(value = "/user/token/{token}",method = RequestMethod.GET,
            //指定返回响应数据的content-type
            produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getUserByToken(@PathVariable String token,String callback) {
        TaotaoResult result = userService.getUserByToken(token);
        //判断是否为jsonp请求
        if (StringUtils.isNotBlank(callback)) {
            return callback +"(" + JsonUtils.objectToJson(result) + ")";
        }
        return JsonUtils.objectToJson(result);
    }*/

    //jsonp的第二种方法，spring4.1以上版本使用
    @RequestMapping(value="/user/token/{token}", method=RequestMethod.GET)
    @ResponseBody
    public Object getUserByToken(@PathVariable String token, String callback) {
        TaotaoResult result = userService.getUserByToken(token);
        //判断是否为jsonp请求
        if (StringUtils.isNotBlank(callback)) {
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            //设置回调方法
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return result;
    }
}

