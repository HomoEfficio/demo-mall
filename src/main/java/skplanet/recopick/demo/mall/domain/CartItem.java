package skplanet.recopick.demo.mall.domain;

import lombok.Getter;
import lombok.Setter;
import skplanet.recopick.demo.mall.dto.ProductDto;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-23
 */
@Embeddable
@Getter
@Setter
public class CartItem extends BaseEntity implements Serializable {

    @Embedded
    private ProductCode productCode;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private int productPrice;

    @Column(name = "product_image")
    private String productImage;

    @Column(name = "point")
    private String point;

    @Column(name = "chip")
    private String chip;

    @Column(name = "installment")
    private String installment;

    @Column(name = "shipFee")
    private String shipFee;

    @Column(name = "sellSatisfaction")
    private String sellSatisfaction;

    @Column(name = "sellGrade")
    private String sellGrade;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "amounts")
    private int amounts;
}
