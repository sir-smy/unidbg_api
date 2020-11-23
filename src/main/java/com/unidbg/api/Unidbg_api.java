package com.unidbg.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import run.RSA_Phone;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.*;
import run.NativeUtils;

import java.io.IOException;

@RestController
public class Unidbg_api {
    private static final Logger logger = LoggerFactory.getLogger(Unidbg_api.class);
    public static NativeUtils instance;

    static {
        try {
            String class_name = "com/xx/xx"; //类路径smali写法
            String so_name = "libprotect.so"; //类路径smali写法
            instance = new NativeUtils(class_name, so_name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String test(@RequestParam String param) throws IOException {

        System.out.println(param);
        JSONObject js = JSONUtil.parseObj(param);
        // 方法名
        String tag = js.getStr("tag", "");
        // 参数
        String a1 = js.getStr("a1", "");
        String res;
        if (tag.equals("getQdscJNI")) {
            res = instance.getQdscJNI(a1);
        } else if (tag.equals("getQdsfJNI")) {
            res = instance.getQdsfJNI(a1);
        } else if (tag.equals("getQdsfJNI2")) {
            res = instance.getQdsfJNI2(a1);
        } else if (tag.equals("getContentJNI")) {
            res = instance.getContentJNI();
        } else {
            res = RSA_Phone.rsa_encrpt(a1);
        }
        return res;
    }


}
