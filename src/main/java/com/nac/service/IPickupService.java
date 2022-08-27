package com.nac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nac.domain.Pickup;

public interface IPickupService extends IService<Pickup> {
    boolean saveGoods(Pickup pickup);

    boolean modify(Pickup pickup);

    boolean delete(Integer id);

    IPage<Pickup> getPage(int currentPage,int pageSize);

    IPage<Pickup> getPage(int currentPage,int pageSize,Pickup pickup);
}
