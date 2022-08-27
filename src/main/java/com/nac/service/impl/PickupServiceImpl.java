package com.nac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nac.dao.PickupDao;
import com.nac.domain.Pickup;
import com.nac.service.IPickupService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PickupServiceImpl extends ServiceImpl<PickupDao, Pickup> implements IPickupService {

    @Autowired
    private PickupDao pickupDao;

    @Override
    public boolean saveGoods(Pickup pickup) {
        return pickupDao.insert(pickup)>0;
    }

    @Override
    public boolean modify(Pickup pickup) {
        return pickupDao.updateById(pickup)>0;
    }

    @Override
    public boolean delete(Integer id) {
        return pickupDao.deleteById(id)>0;
    }

    @Override
    public IPage<Pickup> getPage(int currentPage, int pageSize) {
        IPage page=new Page(currentPage,pageSize);
        pickupDao.selectPage(page,null);
        return page;
    }

    @Override
    public IPage<Pickup> getPage(int currentPage, int pageSize, Pickup pickup) {
        LambdaQueryWrapper<Pickup> lqw=new LambdaQueryWrapper<>();
        lqw.like(Strings.isNotEmpty(pickup.getRes()),Pickup::getRes,pickup.getRes());
        lqw.like(Strings.isNotEmpty(pickup.getTime()),Pickup::getTime,pickup.getTime());
        lqw.like(Strings.isNotEmpty(pickup.getDescription()),Pickup::getDescription,pickup.getDescription());
        lqw.like(Strings.isNotEmpty(pickup.getState()),Pickup::getState,pickup.getState());
        IPage page=new Page(currentPage,pageSize);
        pickupDao.selectPage(page,lqw);
        return page;
    }
}
