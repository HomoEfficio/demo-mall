package skplanet.recopick.demo.mall.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-17
 */
@Entity
@Getter
@Setter
@Table(name = "PRODUCT")
public class Product extends BaseEntity implements Serializable {

    @Id
    @NotNull
    @Column(name = "product_code")
    private String productCode;

    @NotNull
    private String productName;

    @NotNull
    private Integer productPrice;

    @NotNull
    private String productImage;

    private String productImage100;

    private String productImage110;

    private String productImage120;

    private String productImage130;

    private String productImage140;

    private String productImage150;

    private String productImage170;

    private String productImage200;

    private String productImage250;

    private String productImage270;

    private String productImage300;

    private String text1;

    private String text2;

    private String sellerNick;

    private String seller;

    private String sellerGrd;

    private String rating;

    private String detailPageUrl;

    private String salePrice;

    private String delivery;

    private String reviewCount;

    private String buySatisfy;

    private String minorYn;

    // 원래 상품 검색의 상품 정보에는 없는 항목이지만
    // 상품 검색의 결과에 포함되어 있는 카테고리 목록 중
    // 첫번째 항목 이름을 여기에 저장해서 view 로그 전달 시 파라미터로 사용
    private String categoryName;

    @Embedded
    private Benefit benefit;


    // 이하 Product Detail 에 나오는 컬럼

    // 원래 point지만 Benefit의 point와 충돌되어 s 붙임
    private String points;

    private String chip;

    private String installment;

    private String shipFee;

    private String sellSatisfaction;

    private String sellGrade;

//    @ManyToOne
//    @JoinColumn(name = "order_id")
//    private Order order;
}
