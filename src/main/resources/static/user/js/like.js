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