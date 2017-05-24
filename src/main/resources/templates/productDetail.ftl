<#-- RecoPick의 위젯을 테스트 할 때는 나누어진 페이지가 편리 -->
<#-- 상품 상세 보기를 별도 페이지로 렌더링 하기 위해 Freemarker를 사용한 서버쪽 렌더링으로 구현 -->
<#-- Todo: productDetail.ftl 로딩 후 다시 ajax로 필요한 정보를 요청하는 방식으로 js 분리 가능. main.js 참고 -->
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>RecoPick Demo Mall</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div id="app">
    <div class="header">
        <h1>RecoPick Demo Mall</h1>
    </div>
    <div class="main">
        <div class="products">
            <div class="search-results">
                <h2>Product Info</h2>
            </div>
            <div class="product">
                <div>
                    <div class="product-image">
                        <img :src="product.basicImage">
                    </div>
                </div>
                <div>
                    <h4 class="product-title">{{product.productName}}</h4>
                    <p>Price: <strong>{{product.productPrice}}</strong></p>
                    <p>Point: {{product.point}}</p>
                    <p>Chip: {{product.chip}}</p>
                    <p>Installment: {{product.installment}}</p>
                    <p>Ship Fee: {{product.shipFee}}</p>
                    <p>Sell Satisfaction: {{product.sellSatisfaction}}</p>
                    <p>Sell Grade: {{product.sellGrade}}</p>
                    <button class="btn add-to-cart" @click="addToCart()">Add to cart</button>
                </div>
            </div>
        </div>
        <div class="cart">
            <h2>Shopping Cart</h2>
            <transition-group name="fade" tag="ul">
                <li class="cart-item" v-for="(item, index) in cart" v-bind:key="index">
                    <div class="item-title">{{ item.productName }}</div>
                    <span class="item-qty">{{ item.productPrice }} * {{ item.quantity }}</span>
                    <button class="btn" v-on:click="inc(index)">+</button>
                    <button class="btn" v-on:click="dec(index)">-</button>
                </li>
            </transition-group>
            <transition name="fade">
                <div v-if="cart.length">
                    <p>Total: {{ total }}</p>
                    <div><button class="btn order-now" v-on:click="onOrder(cart)">Order Now</button></div>
                </div>
            </transition>
            <div v-if="cart.length === 0" class="empty-cart">
                No items in the cart
            </div>
        </div>
    </div>
</div>
<script src="https://unpkg.com/vue"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script src="/js/scrollMonitor.js"></script>
<script>
<#-- 서버쪽 렌더링으로 구현했기 때문에 js에 값을 심어주려면 js를 분리하지 말고 ftl 안으로 가져와야 함 -->
const RECO_SERVICE_ID = '543';
const RECO_REF = 'http://dev.recopick.com/index.html';
const RECO_URL = 'http://dev.recopick.com/index.html';
const RECO_UID = (function() {
    let url = document.location.href;
    let paramString = url.substring(url.indexOf('?') + 1);
    let params = paramString.split('&');
    for (let i = 0, len = params.length; i < len; i++) {
        let param = params[i];
        if (param.trim().indexOf('uid') === 0)
            return param.substring(param.indexOf('=') + 1);
    }
    return '';
}());
const RECO_MID = Math.floor(Math.random()*(1000000000-100000000)+100000000);
const RECO_BIRTHYEAR = Math.floor(Math.random()*(2000-1930)+1930);
const RECO_GENDER = Math.floor(Math.random()*(1-100)+100) % 2 ? 'F' : 'M';

new Vue({
    el: '#app',
    data: {
        userName: '',
        product: {},
        total: 0,
        items: [],
        cart: [],
        results: [],
        newSearch: 'anime',
        lastSearch: '',
        loading: false
    },
    created() {
        this.userName = '${Session.userName}';
        this.product.productCode = '${product.productCode}';
        this.product.productName = '${product.productName}';
        this.product.productPrice = parseInt('${productPrice}'.replace(/,/g, ''));
        this.product.basicImage = '${product.basicImage}';
        this.product.addImage1 = '${(product.addImage1)!}';
        this.product.addImage2 = '${(product.addImage2)!}';
        this.product.point = '${product.point}';
        this.product.chip = '${product.chip}';
        this.product.installment = '${product.installment}';
        this.product.shipFee = '${product.shipFee}';
        this.product.sellSatisfaction = '${product.sellSatisfaction}';
        this.product.sellGrade = '${product.sellGrade}';
    },
    methods: {
        appendItems() {
            if (this.items.length < this.results.length) {
                var append = this.results.slice(this.items.length, this.items.length + LOAD_NUM);
                this.items = this.items.concat(append);
            }
        },
        addToCart: function() {
            var item = this.product;
            this.total += item.productPrice;
            var found = false;
            for (var i = 0, len = this.cart.length; i < len; i++) {
                let currentItem = this.cart[i];
                if (currentItem.product.productCode === item.productCode) {
                    currentItem.quantity++;
                    currentItem.amounts = currentItem.productPrice * currentItem.quantity;
                    found = true;
                    break;
                }
            }
            if (!found) {
                this.cart.push({
                    product: {
                        productCode: item.productCode,
                        productName: item.productName,
                        productPrice: item.productPrice,
                        productImage: item.basicImage,
                        points: item.point,
                        chip: item.chip,
                        installment: item.installment,
                        shipFee: item.shipFee,
                        sellSatisfaction: item.sellSatisfaction,
                        sellGrade: item.sellGrade,
                    },
                    quantity: 1,
                    amounts: item.productPrice * 1
                });
            }
            this.sendLog('basket', {
                service_id: RECO_SERVICE_ID,
                uid: RECO_UID,
                ref: RECO_REF,
                url: RECO_URL,
                items: this.cart,
                user: {
                    mid: RECO_MID,
                    gender: RECO_GENDER,
                    birthyear: RECO_BIRTHYEAR
                }
            });
            this.saveCurrentCart();
        },
        inc: function(i) {
            var current = this.cart[i];
            current.quantity++;
            current.amounts = current.product.productPrice * current.quantity;
            this.total += current.product.productPrice;
            this.sendLog('basket', { msg: 'inc' });
            this.saveCurrentCart();
        },
        dec: function(i) {
            var current = this.cart[i];
            current.quantity--;
            current.amounts = current.product.productPrice * current.quantity;
            this.total -= current.product.productPrice;
            if (current.quantity <= 0) {
                this.cart.splice(i, 1);
                // Todo 개수가 0이 될 때 DB에서 해당 CartItem을 삭제해줘야 함
            }
            this.sendLog('basket', { msg: 'dec' });
            this.saveCurrentCart();
        },
        saveCurrentCart() {
            axios.post('/api/carts/' + this.userName,
                    {
                        member: {
                            userName: this.userName
                        },
                        cartItems: this.cart
                    })
                    .then(res => {
                        console.log(res);
                        this.onSignIn();
                    })
                    .catch(err => {
                        console.log(err);
                    });
        },
        sendLog: function(action, payload) {
            console.log(action, payload);
        },
        convertToOrderItems: function(cartItems) {
            for (var i = 0, len = cartItems.length; i < len; i++) {
                var item = cartItems[i];
                item.total_sales = item.price * item.count;
                // item.order_no = Math.floor(Math.random()*(10000000000-1000000000)+1000000000).toString();
            }
        }
    },
    computed: {
        noMoreItems: function() {
            return this.items.length == this.results.length && this.results.length > 0
        }
    },
    filters: {
        currency: function(price) {
            return price + ' 원';
        }
    },
});
</script>
</body>
</html>