$(document).on('click', '#dupCheck', () => {

    axios({
        method: "post",
        url: "/user/dupCheck",
        data: "temp@temp.com",
        dataType: "String",
        headers: {'Content-Type': 'application/json'}

    }).then(res => {
        console.log('alert');
        $("body").append(res.data);
    });
});

$(document).ready(() => {
    $("#logout").on("click", (event) => {
        event.preventDefault();

        $.ajax({
            url: "/user/logout",
            method: "post"
        }).then(res =>{
            location.href="/";
        })
        ;
    });
});

