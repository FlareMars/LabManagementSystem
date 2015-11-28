package com.libmanagement.admin.web;

import com.libmanagement.common.exception.LMSServerException;
import com.libmanagement.admin.common.AdminWebBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping
public class IndexWeb extends AdminWebBean {
    private static Log logger = LogFactory.getLog(IndexWeb.class);



    @RequestMapping("/")
    public String defaultPage(){
        return "redirect:/index";
    }


    @RequestMapping("index")
    public String index(){

        return "/pages/index";
    }

     @RequestMapping("/login")
    public String login(@RequestParam(value = "name",required = false)String name,
                        @RequestParam(value = "password",required = false)String password,
                        @RequestParam(value = "authCode",required = false)String authCode,
                        @RequestParam(value = "rememberMe",required = false)Boolean rememberMe,
                        Model model){
        try{
            logger.debug("login action~");
            if(name==null){
                return "/pages/login";
            }

            UsernamePasswordToken token = new UsernamePasswordToken(name, password);

            if(rememberMe!=null) {
                token.setRememberMe(rememberMe.booleanValue());
            }

            Subject currentUser = SecurityUtils.getSubject();

            currentUser.login(token);


            return "redirect:/";

        }catch(LMSServerException e){
            model.addAttribute("name",name);
            model.addAttribute("password", "");
            logger.debug(e,e);
//            logger.debug(name + " login fail!, error msg:" + e.getMessage());
        }catch(Exception e){
            model.addAttribute("name",name);
            model.addAttribute("password", "");
            logger.debug(e,e);
//            logger.debug(name + " login fail!, error msg:" + e.getMessage());
        }
        return "/pages/login";
    }

    @RequestMapping("/logout")
    public String logout(){
        try{
            Subject currentUser = SecurityUtils.getSubject();

            currentUser.logout();

            return "redirect:/login";

        }catch(LMSServerException e){

        }catch(Exception e){

        }
        return "redirect:/login";
    }



}
