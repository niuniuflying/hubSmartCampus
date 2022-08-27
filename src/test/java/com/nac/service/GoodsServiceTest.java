package com.nac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nac.domain.Goods;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GoodsServiceTest {

    @Autowired
    private IGoodsService goodsService;

    @Test
    void testGetById(){
        System.out.println(goodsService.getById(146));
    }

    @Test
    void testSave(){
        Goods goods=new Goods();
        goods.setName("s");
        goods.setPrice("s");
        goods.setNumber("s");
        goods.setDegree("s");
        goods.setDescription("s");
        goods.setType("s");
        goods.setStatus("s");
        goodsService.save(goods);
    }

    @Test
    void testUpdate(){
        Goods goods=new Goods();
        goods.setId(149);
        goods.setName("ss");
        goods.setPrice("ss");
        goods.setNumber("s");
        goods.setDegree("s");
        goods.setDescription("s");
        goods.setType("s");
        goods.setStatus("s");
        goodsService.updateById(goods);
    }

    @Test
    void testDelete(){
        goodsService.removeById(149);
    }

    @Test
    void testGetAll(){
        System.out.println(goodsService.list());
    }

    @Test
    void testGetPage(){
        IPage<Goods> page= new Page<Goods>(2,5);
        goodsService.page(page);
        System.out.println(page.getCurrent());
        System.out.println(page.getSize());
        System.out.println(page.getTotal());
        System.out.println(page.getPages());
        System.out.println(page.getRecords());
    }
}
