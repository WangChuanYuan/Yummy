package org.casual.yummy.service;

import org.casual.yummy.model.goods.Goods;
import org.casual.yummy.utils.ResultMsg;

public interface GoodsService {

    ResultMsg<Goods> addGoods(String rid, Goods goods);
}
