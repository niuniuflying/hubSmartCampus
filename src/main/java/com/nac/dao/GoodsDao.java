package com.nac.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nac.domain.Goods;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsDao extends BaseMapper<Goods> {

}
