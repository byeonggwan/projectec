$(document).on('click', '#alert', () => {

    axios({
        method: "post",
        url: "/alert",
        data: {
            "title": "title 테스트",
            "msg": "msg 테스트"
        },
        dataType: "Json",
        headers: {'Content-Type': 'application/json'}

    }).then(res => {
        console.log('alert');
        $("body").append(res.data);
    });
});

$(document).on('click', '#confirmBtn', () => {
    $('.txt04').remove();
})