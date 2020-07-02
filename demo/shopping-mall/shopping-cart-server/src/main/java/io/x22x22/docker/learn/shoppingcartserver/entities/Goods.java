package io.x22x22.docker.learn.shoppingcartserver.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kdump
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goods {
    private String id;
    private String name;
}
