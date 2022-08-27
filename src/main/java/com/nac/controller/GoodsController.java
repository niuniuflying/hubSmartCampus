package com.nac.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nac.controller.utils.R;
import com.nac.domain.Goods;
import com.nac.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;

    @GetMapping
    public R getAll(){
        return new R( true,goodsService.list());
    }

    @PostMapping
    public R save(@RequestBody Goods goods){
        boolean flag=goodsService.save(goods);
        return new R(flag,flag ? "Success !": "Lose !");
    }

    @PutMapping
    public R update(@RequestBody Goods goods){
        return new R(goodsService.modify(goods));
    }

    @DeleteMapping("{id}")
    public R delete(@PathVariable Integer id){
        //热部署
//        System.out.println("111111111111");
        return new R(goodsService.delete(id));
    }

    @GetMapping("{id}")
    public R getById(@PathVariable Integer id){
        return new R(true,goodsService.getById(id));
    }


    @GetMapping("{currentPage}/{pageSize}")
    public R getPage(@PathVariable int currentPage,@PathVariable int pageSize,Goods goods){
        IPage<Goods> page = goodsService.getPage(currentPage, pageSize,goods);
        //如果当前页码值大于总页码值。那么重新执行查询操作，使用最大页码值作为当前页码值
        if (currentPage>page.getPages()){
            page=goodsService.getPage((int)page.getPages(),pageSize,goods);
        }
        return new R(true,page);
    }
}
