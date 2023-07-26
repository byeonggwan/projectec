$(document).on('click', '#select', () => {
    axios({
        method: "post",
        url: "/user/select",
        headers: {'Content-Type': 'application/json'}

    }).then(res => {
        console.log(res.data);

        const data = res.data;

        for (let i=0; i<data.length; i++) {
            $("body").append(`
                <div>
                    <br>
                    <p> EMAIL ${i} is : ${data[i]["EMAIL"]} </p>
                    <p> PASSWORD ${i} is : ${data[i]["PWD"]} </p>
                    <br>
                </div>
            `);
        }
    });
});

$(document).on('click', '#insert', () => {
    axios({
        method: "post",
        url: "/user/insert",
        headers: {'Content-Type': 'application/json'}

    }).then(res => {
        console.log('successful insert');

    });
})

$(document).on('click', '#user', () => {
    location.href = "user";
})