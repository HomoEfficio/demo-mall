Vue.component('demo-cart', {
    template: `
        <div>
            <h2>Shopping Cart</h2>
            <transition-group name="fade" tag="ul">
                <li class="cart-item" v-for="(item, index) in cart.cartItems" v-bind:key="index">
                    <div class="item-title">{{ item.product.productName }}</div>
                    <span class="item-qty">{{ item.product.productPrice }} * {{ item.quantity }}</span>
                    <button class="btn" v-on:click="inc(index)">+</button>
                    <button class="btn" v-on:click="dec(index)">-</button>
                </li>
            </transition-group>
            <transition name="fade">
                <div v-if="cart.length">
                    <p>Total: {{ total | currency }}</p>
                    <div><button class="btn order-now" v-on:click="onOrder(cart)">Order Now</button></div>
                </div>
            </transition>
            <div v-if="cart.length === 0" class="empty-cart">
                No items in the cart
            </div>
        </div>        
    `,
    data() {
        return {
            userName: '',
            total: 0,
            cart: {
                cartItems: []
            },
        };
    },
    props: ['added-item'],
    watch: {
        addedItem(addedItem) {
            this.addToCart(addedItem);
        }
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
        addToCart: function(addedItem) {
            let item = addedItem;
            this.total += item.productPrice;
            var found = false;
            for (let i = 0, len = this.cart.cartItems.length; i < len; i++) {
                let currentItem = this.cart.cartItems[i];
                if (currentItem.product.productCode === item.productCode) {
                    currentItem.quantity++;
                    currentItem.amounts = currentItem.product.productPrice * currentItem.quantity;
                    found = true;
                    break;
                }
            }
            if (!found) {
                this.cart.cartItems.push({
                    product: {
                        productCode: item.productCode,
                        productName: item.productName,
                        productPrice: item.productPrice,
                        productImage: item.productImage,
                        // image100: item.productImage,
                        // image110: item.productImage,
                        // image120: item.productImage,
                        // image130: item.productImage,
                        // image140: item.productImage,
                        // image150: item.productImage,
                        // image170: item.productImage,
                        // image200: item.productImage,
                        // image250: item.productImage,
                        // image270: item.productImage,
                        // image300: item.productImage,
                        // text1: item.text1,
                        // text2: item.text2,
                        // sellerNick: item.sellerNick,
                        // seller: item.seller,
                        // sellerGrd: item.sellerGrd,
                        // rating: item.rating,
                        // detailPageUrl: item.detailPageUrl,
                        // salePrice: item.salePrice,
                        // delivery: item.delivery,
                        // reviewCount: item.reviewCount,
                        // buySatisfy: item.buySatisfy,
                        // minorYn: item.minorYn,
                        // benefit: item.benefit,
                    },
                    quantity: 1,
                    amounts: item.productPrice * 1
                });
                // Todo: cart 아이템을 DB에 저장(cart 비우고 현재 카트 아이템으로 insert)
            }
            this.sendLog('basket', {
                service_id: RECO_SERVICE_ID,
                uid: RECO_UID,
                ref: RECO_REF,
                url: RECO_URL,
                items: this.cart.cartItems,
                user: {
                    mid: RECO_MID,
                    gender: RECO_GENDER,
                    birthyear: RECO_BIRTHYEAR
                }
            });
            this.saveCurrentCart();
        },
        inc: function(i) {
            var current = this.cart.cartItems[i];
            current.quantity++;
            current.amounts = current.product.productPrice * current.quantity;
            this.total += current.product.productPrice;
            this.sendLog('basket', {
                service_id: RECO_SERVICE_ID,
                uid: RECO_UID,
                ref: RECO_REF,
                url: RECO_URL,
                items: this.cart.cartItems,
                user: {
                    mid: RECO_MID,
                    gender: RECO_GENDER,
                    birthyear: RECO_BIRTHYEAR
                }
            });
            this.saveCurrentCart();
        },
        dec: function(i) {
            var current = this.cart.cartItems[i];
            current.quantity--;
            current.amounts = current.product.productPrice * current.quantity;
            this.total -= current.product.productPrice;
            if (current.quantity <= 0) {
                this.cart.cartItems.splice(i, 1);
            }
            this.sendLog('basket', {
                service_id: RECO_SERVICE_ID,
                uid: RECO_UID,
                ref: RECO_REF,
                url: RECO_URL,
                items: this.cart.cartItems,
                user: {
                    mid: RECO_MID,
                    gender: RECO_GENDER,
                    birthyear: RECO_BIRTHYEAR
                }
            });
            this.saveCurrentCart();
        },
        sendLog: function(action, payload) {
            console.log(action, payload);
        },
        saveCurrentCart() {
            axios.post('/api/carts', this.cart)
                .then(res => {
                    console.log(res);
                })
                .catch(err => {
                    console.log(err);
                });
        },
    }
});