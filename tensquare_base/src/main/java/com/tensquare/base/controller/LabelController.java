package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/label")
public class LabelController {
    @Autowired
    private LabelService labelService;

    @PostMapping
    public Result add(@RequestBody Label label){
        labelService.add(label);
        return new Result(true, StatusCode.OK, "添加成功");
    }
    @GetMapping
    public Result findAll(){
        return new Result(true, StatusCode.OK, "查询列表成功", labelService.findAll() );
    }

    @GetMapping(value = "/{labelId}")
    public Result findById(@PathVariable String labelId){
        return new Result(true, StatusCode.OK, "查询成功", labelService.findById(labelId) );
    }
    @PutMapping("/{labelId}")
    public Result update(@RequestBody Label label, @PathVariable String labelId){
        label.setId(labelId);
        labelService.update(label);
        return new Result(true, StatusCode.OK, "更新成功");
    }

    @DeleteMapping("/{labelId}")
    public Result deleteById(@PathVariable String labelId){
        labelService.deleteById(labelId);
        return new Result(true, StatusCode.OK, "删除成功");
    }
    @PostMapping("/search")
    public Result findSearch(@RequestBody Label label){
        return new Result(true, StatusCode.OK, " 查询成功", labelService.findSearch(label));
    }
    @PostMapping("/search/{page}/{size}")
    public Result pageQuary(@RequestBody Label label,@PathVariable int page,@PathVariable int size){
        Page<Label> pageData = labelService.pageQuary(label, page, size);
        return new Result(true, StatusCode.OK, " 查询成功", new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }

}
