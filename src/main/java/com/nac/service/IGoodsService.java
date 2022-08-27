package com.nac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nac.domain.Goods;

public interface IGoodsService extends IService<Goods> {
    boolean saveGoods(Goods goods);

    boolean modify(Goods goods);

    boolean delete(Integer id);

    IPage<Goods> getPage(int currentPage,int pageSize);

    IPage<Goods> getPage(int currentPage,int pageSize,Goods goods);
}
