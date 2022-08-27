package com.nac.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nac.domain.Goods;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GoodsDaoTestCase {

    @Autowired
    private GoodsDao goodsDao;

    @Test
    void testGetById(){
        System.out.println(goodsDao.selectById(1));
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
        goodsDao.insert(goods);
    }

    @Test
    void testUpdate(){
        Goods goods=new Goods();
        goods.setId(147);
        goods.setName("ss");
        goods.setPrice("ss");
        goods.setNumber("s");
        goods.setDegree("s");
        goods.setDescription("s");
        goods.setType("s");
        goods.setStatus("s");
        goodsDao.updateById(goods);
    }

    @Test
    void testDelete(){
        goodsDao.deleteById(147);
    }

    @Test
    void testGetAll(){
        System.out.println(goodsDao.selectList(null));
    }
    @Test
    void testGetPage(){
        IPage page=new Page(2,5);
        goodsDao.selectPage(page,null);
        System.out.println(page.getCurrent());
        System.out.println(page.getSize());
        System.out.println(page.getTotal());
        System.out.println(page.getPages());
        System.out.println(page.getRecords());
    }

    @Test
    void testGetBy(){
        String name="ÇòÒÂ";
        LambdaQueryWrapper<Goods> lqw=new LambdaQueryWrapper<>();
        lqw.like(Strings.isNotEmpty(name),Goods::getName,name);
        goodsDao.selectList(lqw);
    }
}
