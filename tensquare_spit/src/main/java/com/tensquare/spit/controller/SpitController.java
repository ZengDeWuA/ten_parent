package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping
    public Result save(@RequestBody Spit spit){
        spitService.save(spit);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    @GetMapping
    public Result findAll(){
        return new Result(true, StatusCode.OK, "查询列表成功", spitService.findAll());
    }

    @GetMapping(value = "/{spitId}")
    public Result findById(@PathVariable String spitId){
        return new Result(true, StatusCode.OK, "查询成功", spitService.findById(spitId));
    }

    @PutMapping(value = "/{spitId}")
    public Result update(@PathVariable String spitId,@RequestBody Spit spit){
        spit.set_id(spitId);
        return new Result(true, StatusCode.OK,"修改成功" ,spitService.update(spit) );
    }

    @DeleteMapping(value = "/{spitId}")
    public Result delete(@PathVariable String spitId){
        spitService.deleteBtId(spitId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @GetMapping(value = "/comment/{parentid}/{page}/{size}")
    public Result comment(@PathVariable String parentid,int page,int size){
        Page<Spit> pageList = spitService.findByParentId(parentid, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Spit>(pageList.getTotalElements(),pageList.getContent()));
    }

    @PutMapping("/thumbup/{spitId}")
    public Result thumbupAdd(@PathVariable String spitId){
        String userId = "1024";
        if (redisTemplate.opsForValue().get("thumbup"+userId)!=null){
            return new Result(false, StatusCode.ERROR, "重复操作");
        }
        redisTemplate.opsForValue().set("thumbup"+userId, "1");
        spitService.thumpubAdd(spitId);
        return new Result(true, StatusCode.OK, "点赞成功");
    }

}
