package skplanet.recopick.demo.mall.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-17
 */
@Getter
@Setter
public class ProductSearchResponseDto {

    private RequestDto request;

    private ProductsDto products;
}
