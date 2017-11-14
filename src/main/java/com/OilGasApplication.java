package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: .</p>
 * <p>Company: </p>
 * <p>Date: 2017/11/14 16:48</p>
 * <p>Copyright: 2016-2017 happylifeplat.com All Rights Reserved</p>
 *
 * @author hyq
 */
@RestController
@SpringBootApplication
public class OilGasApplication {

    @RequestMapping("/")
    @ResponseBody
    public String word() {
        return "hello word !!";
    }

    public static void main(String[] args) {
        SpringApplication.run(OilGasApplication.class, args);
    }
}
