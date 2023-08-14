$(document).on("click", "#adminNewProductButton", () => {
    const category_name = $("#sort").val();
    const product_name = $("#adminNewProductName").val();
    const product_price = $("#adminNewProductPrice").val();
    const product_stock = $("#adminNewProductStock").val();
    const product_desc = $("#adminNewProductDesc").val();
    const product_discount = $("#adminNewProductDiscount").val();
    const thumbnail= $("#adminNewProductPicture")[0].files[0];
    const formData = new FormData();
    formData.append("category_name", category_name);
    formData.append("product_name", product_name);
    formData.append("product_price", product_price);
    formData.append("product_stock", product_stock);
    formData.append("product_desc", tinymce.get('adminNewProductDesc').getContent());
    formData.append("product_discount", product_discount);
    formData.append("thumbnail", thumbnail);
    console.log("here" + thumbnail);

    axios.post("/admin/add-product", formData, {
        headers: {
            "Content-Type": "multipart/form-data" // 이미지 파일을 전송할 때 필요한 헤더
        }
    }).then(res => {
        window.location.replace(res.data);
    }).catch(error => {
        console.error(error);
    });
});

window.onload = function() {
    tinymce.init({
        selector: '#adminNewProductDesc',
        plugins: 'ai tinycomments mentions anchor autolink charmap codesample emoticons image link lists media searchreplace table visualblocks wordcount checklist mediaembed casechange export formatpainter pageembed permanentpen footnotes advtemplate advtable advcode editimage tableofcontents mergetags powerpaste tinymcespellchecker autocorrect a11ychecker typography inlinecss',
        toolbar: 'undo redo | blocks fontfamily fontsize | bold italic underline strikethrough | link image media table mergetags | align lineheight | tinycomments | checklist numlist bullist indent outdent | emoticons charmap | removeformat',
        file_picker_types: 'image',
        tinycomments_mode: 'embedded',
        tinycomments_author: 'Author name',
        mergetags_list: [
            { value: 'First.Name', title: 'First Name' },
            { value: 'Email', title: 'Email' },
        ],
        ai_request: (request, respondWith) => respondWith.string(() => Promise.reject("See docs to implement AI Assistant"))
    });
};

$(document).on("click", "#testBtn", () => {
    console.log(tinymce.get('adminNewProductDesc').getContent());


});

$(document).on("click", ".product-status-button", function() {
    const status = $(this).val();

    const url = new URL(window.location.href);
    const params = new URLSearchParams(url.search);

    params.set("status", status);

    url.search = params.toString();

    window.location.href = url;
});

$(document).on("click", ".product-category-button", function() {
    const categoryId = $(this).val();

    const url = new URL(window.location.href);
    const params = new URLSearchParams(url.search);

    params.set("categoryId", categoryId);

    url.search = params.toString();

    window.location.href = url;
});

$(document).on("click", ".product-cancel-button", function (){
    const productId = $(this).val();
    console.log(productId);
    axios({
        method: "post",
        url: "/admin/cancel-product",
        data: {productId},
        dataType: "String",
        headers: {"Content-Type": "application/json"}
    }).then(res => {
        window.location.replace(res.data);
    }).catch(error => {
        console.error(error);
    });
});