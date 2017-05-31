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
        let href = document.location.href;
        let productCode = href.substring(href.lastIndexOf('/') + 1);
        axios.get('/api/products/' + productCode)
            .then(res => {
                this.product = res.data;
                document.getElementById('reco-meta-title').setAttribute('content', this.product.productName);
                document.getElementById('reco-meta-image').setAttribute('content', this.product.productImage);
                document.getElementById('reco-meta-price').setAttribute('content', this.product.productPrice);
                document.getElementById('reco-meta-price-currency').setAttribute('content', 'KRW');
                document.getElementById('reco-meta-description').setAttribute('content', this.product.productName);
                document.getElementById('reco-meta-author').setAttribute('content', this.product.sellerNick);

                window.recoPick('sendLog','view', false, {id:this.product.productCode, c1: this.product.categoryName});
            })
            .catch(err => {
                console.log(err);
            });

        axios.get('/api/carts')
            .then(res => {
                this.cart = res.data;
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
        },
        convertToOrderItems: function(cartItems) {
            for (var i = 0, len = cartItems.length; i < len; i++) {
                var item = cartItems[i];
                item.total_sales = item.price * item.count;
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