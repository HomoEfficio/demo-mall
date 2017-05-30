const LOAD_NUM = 10;
const RECO_SERVICE_ID = '543';
const RECO_REF = document.location.href;
const RECO_URL = document.location.href;

new Vue({
    el: '#app',
    data: {
        userName: '',
        product: {},
        total: 0,
        items: [],
        cart: {
            cartItems: []
        },
        results: [],
        newSearch: 'anime',
        lastSearch: '',
        loading: false,
        addedItem: {}  // cart 컴포넌트에서 사용되는 변수
    },
    created() {
        // this.userName = '${Session.userName}';
        // this.product.productCode = '${product.productCode}';
        // this.product.productName = '${product.productName}';
        // this.product.productPrice = parseInt('${productPrice}'.replace(/,/g, ''));
        // this.product.basicImage = '${product.basicImage}';
        // this.product.addImage1 = '${(product.addImage1)!}';
        // this.product.addImage2 = '${(product.addImage2)!}';
        // this.product.point = '${product.point}';
        // this.product.chip = '${product.chip}';
        // this.product.installment = '${product.installment}';
        // this.product.shipFee = '${product.shipFee}';
        // this.product.sellSatisfaction = '${product.sellSatisfaction}';
        // this.product.sellGrade = '${product.sellGrade}';

        let href = document.location.href;
        let productCode = href.substring(href.lastIndexOf('/') + 1);
        axios.get('/api/products/' + productCode)
            .then(res => {
                this.product = res.data;
                // this.product.productCode = '${product.productCode}';
                // this.product.productName = '${product.productName}';
                let priceString = this.product.productPrice.price;
                this.product.productPrice = priceString.substring(0, priceString.length - 1).replace(/,/g, '');
                // this.product.basicImage = '${product.basicImage}';
                // this.product.addImage1 = '${(product.addImage1)!}';
                // this.product.addImage2 = '${(product.addImage2)!}';
                // this.product.point = '${product.point}';
                // this.product.chip = '${product.chip}';
                // this.product.installment = '${product.installment}';
                // this.product.shipFee = '${product.shipFee}';
                // this.product.sellSatisfaction = '${product.sellSatisfaction}';
                // this.product.sellGrade = '${product.sellGrade}';
                document.getElementById('reco-meta-title').setAttribute('content', this.product.productName);
                document.getElementById('reco-meta-image').setAttribute('content', this.product.basicImage);
                document.getElementById('reco-meta-price').setAttribute('content', this.product.productPrice);
                document.getElementById('reco-meta-price-currency').setAttribute('content', 'KRW');
                document.getElementById('reco-meta-description').setAttribute('content', this.product.productName);
                // document.getElementById('reco-meta-author').setAttribute('content', this.product.sellerNick);

console.log('sendLog-view 호출', this.product.productCode, window.recoPick);
                window.recoPick('sendLog','view', false, {id:this.product.productCode, c1:'카테고리(대)', c2:'카테고리(중)', c3:'카테고리(소)'});
            })
            .catch(err => {
                console.log(err);
            });

        axios.get('/api/carts')
            .then(res => {
                this.cart = res.data;
                // this.cart.cartItems = res.data.cartItems;
            })
            .catch(err => {
                console.log(err);
            });
    },
    methods: {
        appendItems() {
            if (this.items.length < this.results.length) {
                var append = this.results.slice(this.items.length, this.items.length + LOAD_NUM);
                this.items = this.items.concat(append);
            }
        },
        addToCart: function() {
            this.addedItem = this.product;
            // Todo: 상품 정보
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
        },
        gotoMain(userName) {
            document.location.href = '/main/' + userName;
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