package skplanet.recopick.demo.mall.dto;

import lombok.Getter;
import lombok.Setter;
import skplanet.recopick.demo.mall.domain.Product;

import java.util.List;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-17
 */
@Getter
@Setter
public class ProductsDto {

    private String totalCount;

    private List<Product> product;
}
