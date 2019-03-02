package org.casual.yummy.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDTO {

    private String rid;

    private List<GoodsDTO> goods;

    private List<ComboDTO> combos;
}
