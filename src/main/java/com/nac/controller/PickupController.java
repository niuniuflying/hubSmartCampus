package com.nac.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nac.controller.utils.R;
import com.nac.domain.Pickup;
import com.nac.service.IPickupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pickup")
public class PickupController {

    @Autowired
    private IPickupService pickupService;

    @GetMapping
    public R getAll(){
        return new R( true,pickupService.list());
    }

    @PostMapping
    public R save(@RequestBody Pickup pickup){
        boolean flag=pickupService.save(pickup);
        return new R(flag,flag ? "Success !": "Lose !");
    }

    @PutMapping
    public R update(@RequestBody Pickup pickup){
        return new R(pickupService.modify(pickup));
    }

    @DeleteMapping("{id}")
    public R delete(@PathVariable Integer id){
//        System.out.println("111111111111");
        return new R(pickupService.delete(id));
    }

    @GetMapping("{id}")
    public R getById(@PathVariable Integer id){
        return new R(true,pickupService.getById(id));
    }


    @GetMapping("{currentPage}/{pageSize}")
    public R getPage(@PathVariable int currentPage,@PathVariable int pageSize,Pickup pickup){
        IPage<Pickup> page = pickupService.getPage(currentPage, pageSize,pickup);
        //如果当前页码值大于总页码值。那么重新执行查询操作，使用最大页码值作为当前页码值
        if (currentPage>page.getPages()){
            page=pickupService.getPage((int)page.getPages(),pageSize,pickup);
        }
        return new R(true,page);
    }
}
