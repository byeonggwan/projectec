$(document).on("click", "#adminNewProductButton", () => {
    const CATEGORY_NAME = $("#sort").val();
    const PRODUCT_NAME = $("#adminNewProductName").val();
    const PRODUCT_PRICE = $("#adminNewProductPrice").val();
    const PRODUCT_STOCK = $("#adminNewProductStock").val();
    const PRODUCT_DESC = $("#adminNewProductDesc").val();
    const PRODUCT_DISCOUNT = $("#adminNewProductDiscount").val();

    axios({
        method: "post",
        url: "/admin/add-product",
        data: {CATEGORY_NAME, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_STOCK, PRODUCT_DESC, PRODUCT_DISCOUNT},
        dataType: "String",
        headers: {"Content-Type": "application/json"}
    }).then(res => {
        window.location.replace(res.data);
    });

});