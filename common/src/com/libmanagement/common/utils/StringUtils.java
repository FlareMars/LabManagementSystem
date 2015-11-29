package com.libmanagement.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum  StringUtils {
    INSTANCE;

    public boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public boolean notEmpty(String str) {
        return !isEmpty(str);
    }

    public boolean isEmpty(Object obj) {
        if(obj==null) return true;
        if(obj.toString().length()==0) return true;
        return false;
    }

    public boolean notEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public boolean isAnyEmpty(String... strs) {
        for (String str : strs) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    public String chineseToUtf8(String source) {
        try {
            return new String(source.getBytes("ISO8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean equals(String s1,String s2){
        if(s1==null && s2==null){

            return true;
        }else{
            try{
                return s1.equals(s2);
            }catch (Exception e){
                return false;
            }
        }
    }


    /**
     * 验证邮箱
     *
     * @param email
     * @return boolean
     */
    public boolean isEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证手机号码
     *
     * @param mobileNumber
     * @return boolean
     */
    public boolean isMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public  String generateUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public boolean isCharacterNumber(String str) {

        try{
            if(isEmpty(str)){
                return true;
            }

            return str.matches("[0-9A-Za-z_]*");
        }catch(Exception e){
            return  false;
        }

    }

    public boolean isChineseCharacterNumber(String str){

        if(isEmpty(str)){
            return true;
        }

        String regex = "^[a-z0-9A-Z_\u4e00-\u9fa5]+$";
        return str.matches(regex);

    }

    public int getMinValue(String limits){
        if(limits==null) return 0;
        if(limits.indexOf(",")!=-1){
            String[] ss = limits.split(",");
            if(StringUtils.INSTANCE.isEmpty(ss[0])){
                return 0;
            }else if(ss[0].equals("n")){
                return 0;
            }else {
                return Integer.parseInt(ss[0]);
            }
        }else {
            if(limits.equals("n")){
                return 0;
            }else {
                return Integer.parseInt(limits);
            }
        }
    }

    public int getMaxValue(String limits){
        if(limits==null) return 0;
        if(limits.indexOf(",")!=-1){
            String[] ss = limits.split(",");
            if(StringUtils.INSTANCE.isEmpty(ss[1])){
                return 0;
            }else if(ss[1].equals("n")){
                return 999;
            }else {
                return Integer.parseInt(ss[1]);
            }
        }else {
            if(limits.equals("n")){
                return 999;
            }else {
                return Integer.parseInt(limits);
            }
        }
    }

    public String base64Encoder(String str){
        return new sun.misc.BASE64Encoder().encode(str.getBytes());
    }


}
