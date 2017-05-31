const LOAD_NUM = 10;
const RECO_SERVICE_ID = '543';
const RECO_REF = document.location.href;
const RECO_URL = document.location.href;

new Vue({
    el: '#app',
    data: {
        userName: '',
        uid: window.demoUserUid,
        userInfo: window.demoUserInfo,
        total: 0,
        items: [],
        cart: {
            cartItems: []
        },
        results: [],
        newSearch: '',  // 뒤로 가기 시 검색어 유지에 사용
        lastSearch: '',
        loading: false,
        addedItem: {} // cart-component에서 사용되는 변수
    },
    created() {
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
        onSearch(mid, birthYear, gender) {
            if (this.newSearch.length) {
                this.items = [];
                this.loading = true;
                window.recoPick('sendLog', 'search', false, this.newSearch);
                axios.get('/api/search/'.concat(this.newSearch))
                    .then(res => {
                        this.lastSearch = this.newSearch;
                        this.results = res.data;
                        this.appendItems();
                        this.loading = false;
                    })
                    .catch(err => {
                        console.log(err);
                    });
            }
        },
        onDetail(index) {
            document.location.href = '/products/' + this.items[index].productCode;
        },
        addToCart: function(index) {
            // addedItem이 변경되면 component-cart.js 내의 메서드를 호출하게 되므로
            // 장바구니 로그는 component-basket.js에서 전송하게 구현
            this.addedItem = this.items[index];
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