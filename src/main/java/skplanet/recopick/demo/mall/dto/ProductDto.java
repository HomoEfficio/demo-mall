package skplanet.recopick.demo.mall.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-22
 */
@Getter
@Setter
public class ProductDto {

    private String productCode;

    private String productName;

    private ProductPriceDto productPrice;

    private String basicImage;

    private String addImage1;

    private String addImage2;

    private String point;

    private String chip;

    private String installment;

    private String shipFee;

    private String sellSatisfaction;

    private String sellGrade;
}
