package top.ninng.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.ninng.entity.UnifyResponse;

/**
 * HelloWorld
 *
 * @Author OhmLaw
 * @Date 2022/12/29 15:55
 * @Version 1.0
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public UnifyResponse<String> getHello(
            @RequestParam(value = "message", required = false, defaultValue = "get message null") String message) {
        return UnifyResponse.ok(message);
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public UnifyResponse<String> postHello(
            @RequestParam(value = "message", required = false, defaultValue = "post message null") String message) {
        return UnifyResponse.ok(message);
    }
}
