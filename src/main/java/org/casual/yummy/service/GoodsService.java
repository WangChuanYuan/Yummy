package org.casual.yummy.service;

import org.casual.yummy.model.goods.Goods;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GoodsService {

    ResultMsg<Goods> addGoods(String rid, Long cgid, Goods goods);

    ResultMsg<Goods> modifyGoods(Goods goods);

    ResultMsg<Goods> deleteGoods(Long gid);

    Goods getGoodsById(Long gid);

    List<Goods> getSellingGoods(String rid, Long cgid, Pageable page);
}
