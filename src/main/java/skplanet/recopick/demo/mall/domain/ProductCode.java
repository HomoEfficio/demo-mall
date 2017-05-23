package skplanet.recopick.demo.mall.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-23
 */
@Embeddable
@Getter
@Setter
public class ProductCode {

    private String productCode;

    public ProductCode() {
    }

    public ProductCode(String productCode) {
        this.productCode = productCode;
    }
}
