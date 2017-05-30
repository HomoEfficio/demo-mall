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
            <div><button class="pure-button pure-button-primary" @click.prevent="onOrder">주문하기</button></div>
        </div>        
    `,
    data() {
        return {
            userName: '',
            total: 0,
            cart: {
                cartItems: []
            },
            ref: document.location.href,
            url: document.location.href
        };
    },
    props: ['added-item', 'uid', 'userInfo'],
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
        cartItemsToRecoItems(cartItems) {
            let recoItems = [];
            for (let cartItem of cartItems) {
                let recoItem = {
                    id: cartItem.product.productCode,
                    count: cartItem.quantity
                };
                recoItems.push(recoItem);
            }
            return recoItems;
        },
        orderItemsToRecoItems(orderItems) {
            let recoItems = [];
            for (let orderItem of orderItems) {
                let recoItem = {
                    id: orderItem.product.productCode,
                    count: orderItem.quantity,
                    total_sales: orderItem.product.productPrice * orderItem.quantity
                };
                recoItems.push(recoItem);
            }
            return recoItems;
        },
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
            }
            window.recoPick('sendLog', 'basket', false, this.cartItemsToRecoItems(this.cart.cartItems));
            this.saveCurrentCart();
        },
        inc: function(i) {
            var current = this.cart.cartItems[i];
            current.quantity++;
            current.amounts = current.product.productPrice * current.quantity;
            this.total += current.product.productPrice;
            window.recoPick('sendLog', 'basket', false, this.cartItemsToRecoItems(this.cart.cartItems));
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
            window.recoPick('sendLog', 'basket', false, this.cartItemsToRecoItems(this.cart.cartItems));
            this.saveCurrentCart();
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
        onOrder() {
            axios.post('/api/orders', this.cart)
                .then(res => {
                    window.recoPick('sendLog', 'order', false, this.orderItemsToRecoItems(this.cart.cartItems));
                    console.log(res);
                    let orderId = res.data;
                    document.location.href = '/orders/' + orderId;
                }).catch(err => {
                    console.log(err);
                });
        }
    }
});