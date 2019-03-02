package org.casual.yummy.service;

import org.casual.yummy.dto.GoodsDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticService {

    List<GoodsDTO> goodsSales(String rid, LocalDateTime from, LocalDateTime to);
}
