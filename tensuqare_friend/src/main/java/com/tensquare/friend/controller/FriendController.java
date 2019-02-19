package com.tensquare.friend.controller;

import com.tensquare.friend.service.FriendService;
import config.JwtUtil;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;
    @Autowired
    private HttpServletRequest request;

    @PutMapping(value = "/like/{friendid}/{type}")
    public Result addFriend(@PathVariable String friendid,@PathVariable int type){
        Claims user_claims = (Claims) request.getAttribute("user_claims");
        String id = user_claims.getId();
        if (user_claims==null){
            return new Result(false, StatusCode.LOGINERROR, "权限有误");
        }
        if (type==0){
            friendService.addFriend(id,friendid);
            return new Result(true, StatusCode.OK, "添加成功");
        }else if (type==1){
            //添加非好友
            return new Result(true, StatusCode.OK, "添加成功");
        }
        return new Result(false, StatusCode.ERROR, "type输入有误，只有0和1");
    }
}
