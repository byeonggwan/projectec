document.addEventListener('DOMContentLoaded', function () {
    updateTotalPrice();
});

$(document).on("click", ".cartSizeMinusButton", function () {
    let productId = $(this).data('product-id');
    let cartSizeButton = $(`.cartSizeButton[data-product-id="${productId}"]`);
    let currentSize = parseInt(cartSizeButton.text());

    if (currentSize > 1) {
        currentSize -= 1;
        cartSizeButton.text(currentSize);
        addToCart(productId, currentSize);
        updatePrice(productId, currentSize);
    }
});

$(document).on("click", ".cartSizePlusButton", function () {
    let productId = $(this).data('product-id');
    let cartSizeButton = $(`.cartSizeButton[data-product-id="${productId}"]`);
    let currentSize = parseInt(cartSizeButton.text());
    let stockSize = parseInt($(this).data('product-stock'));

    if (currentSize < stockSize) {
        currentSize += 1;
        cartSizeButton.text(currentSize);
        addToCart(productId, currentSize);
        updatePrice(productId, currentSize);
    }
});

$(document).on("click", ".cartDeleteProductButton", function () {
   let productId = $(this).data('product-id');
   deleteFromCart(productId);
   $(this).closest('tr').remove();
   updateTotalPrice();
});

function updatePrice(productId, currentSize) {
    let totalProductPriceText = $(`.productPrice[data-product-id="${productId}"]`);
    let productPrice = parseInt(totalProductPriceText.data('product-price'));
    let totalProductPrice = productPrice * currentSize;
    totalProductPriceText.text(`${totalProductPrice.toLocaleString()}원`);

    updateTotalPrice();
}

function updateTotalPrice() {
    let totalPrice = 0;
    let totalDiscount = 0;
    let lastTotalDiscount = 0;
    $('.productPrice').each(function () {
        let priceText = $(this).text();
        let price = parseInt(priceText.replace(/[^\d]+/g, ''));
        let discount = parseInt($(this).data('product-discount'));
        totalPrice += price;
        totalDiscount += Math.floor(price * discount / 100);
        lastTotalDiscount = totalPrice - totalDiscount;
    });
    $('#totalPrice').text(totalPrice.toLocaleString() + '원');
    $('#totalDiscount').text(`-${totalDiscount.toLocaleString()}원`);

    if (lastTotalDiscount >= 50000) {
        $('#deliveryFee').text("무료");
    }
    else {
        lastTotalDiscount += 3000;
        $('#deliveryFee').text("3,000원");
    }
    $('#lastTotalPrice').text(lastTotalDiscount.toLocaleString() + '원');
}