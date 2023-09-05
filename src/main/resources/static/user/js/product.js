$(document).on("click", ".like-button", function() {
    // session 값 없을시 axios 안넘어가게 설계 가능
    let productId = $(this).data('product-id');

    let iconSpan = $(this).find('span');
    let isLiked = iconSpan.hasClass('red__heart');

    axios({
        method: 'POST',
        url: `/product/${productId}/like`,
        data: {isLiked},
        headers: {"Content-Type": "application/json"}
    }).then(res => {
        if (res.data) {
            if (isLiked) {
                iconSpan
                    .removeClass('material-icons red__heart')
                    .addClass('material-symbols-outlined');
            } else {
                iconSpan
                    .removeClass('material-symbols-outlined')
                    .addClass('material-icons red__heart');
            }
        } else {
            window.location.replace("/user/signin");
        }
    });

});

$(document).on("click", "#detailAddToCartButton", function () {
    document.querySelector('.txt04.right__modal').style.display = "block";
    const productId = parseInt($(this).data("product-id"));
    const productSize = parseInt($("#detailProductSize").val());
    addToCart(productId, productSize);
    console.log(localStorage);
});

$(document).on("click", "#detailSizeMinusButton", function () {
    let sizeInput = document.getElementById("detailProductSize");
    let currentSize = parseInt(sizeInput.value);
    if (currentSize > 1) {
        sizeInput.value = currentSize - 1;
    }
});

$(document).on("click", "#detailSizePlusButton", function () {
    let sizeInput = document.getElementById("detailProductSize");
    let currentSize = parseInt(sizeInput.value);
    let stockSize = parseInt($(this).data("product-stock"));
    if (currentSize < stockSize) {
        sizeInput.value = currentSize + 1;
    }
});

$(document).on("click", "#detailReturnLink", function () {
    document.querySelector('.txt04.right__modal').style.display = "none";
});

// 로컬 스토리지에 cart 데이터를 저장하는 함수
function addToCart(productId, productSize) {
    let cart = JSON.parse(localStorage.getItem("cart")) || {};
    cart[productId] = productSize;
    localStorage.setItem("cart", JSON.stringify(cart));
}

function deleteFromCart(productId) {
    let cart = JSON.parse(localStorage.getItem("cart")) || {};
    delete cart[productId];
    localStorage.setItem("cart", JSON.stringify(cart))
}


