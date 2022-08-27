package com.nac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nac.dao.GoodsDao;
import com.nac.domain.Goods;
import com.nac.service.IGoodsService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsDao, Goods> implements IGoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Override
    public boolean saveGoods(Goods goods) {
        return goodsDao.insert(goods) > 0;
    }

    @Override
    public boolean modify(Goods goods) {
        return goodsDao.updateById(goods) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return goodsDao.deleteById(id) > 0;
    }

    @Override
    public IPage<Goods> getPage(int currentPage, int pageSize) {
        IPage page = new Page(currentPage, pageSize);
        goodsDao.selectPage(page, null);
        return page;
    }

    @Override
    public IPage<Goods> getPage(int currentPage, int pageSize, Goods goods) {
        LambdaQueryWrapper<Goods> lqw = new LambdaQueryWrapper<>();
        lqw.like(Strings.isNotEmpty(goods.getName()), Goods::getName, goods.getName());
        lqw.like(Strings.isNotEmpty(goods.getDegree()), Goods::getDegree, goods.getDegree());
        lqw.like(Strings.isNotEmpty(goods.getDescription()), Goods::getDescription, goods.getDescription());
        lqw.like(Strings.isNotEmpty(goods.getType()), Goods::getType, goods.getType());
        lqw.like(Strings.isNotEmpty(goods.getStatus()), Goods::getStatus, goods.getStatus());
        IPage page = new Page(currentPage, pageSize);
        goodsDao.selectPage(page, lqw);
        return page;
    }
}
