package io.x22x22.docker.learn.orderserver.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Kdump
 */
@EqualsAndHashCode(callSuper = true)
@Table("order_master")
@Data
@ToString
public class Order extends Base {

    /**
     * 商品编码
     */
    private final String goodsCode;
    /**
     * 商品名称
     */
    private final String goodsName;
    /**
     * 订单金额
     */
    private final Double amount;
}
