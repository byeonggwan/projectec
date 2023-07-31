$(document).on("change", "#allCheck", () => {
    let checked = $("#allCheck").is(":checked");

    if (checked)
        $(".essential-chk").prop("checked",true);
    else
        $(".essential-chk").prop("checked",false);
});

$(document).on("click", "#logout", (event) => {
    event.preventDefault();

    axios({
        url: "/user/logout",
        method: "post"
    }).then(res =>{
        location.href="/";
    })
    ;
});

$(document).on("propertychange change keyup paste input", "#USER_EMAIL", () => {
    const USER_EMAIL = $("#USER_EMAIL").val();

    axios({
        method: "post",
        url: "/user/validate-email",
        data: {USER_EMAIL},
        dataType: "String",
        headers: {"Content-Type": "application/json"}

    }).then(res => {
        if (res.data == false) {
            $("#invalidEmail").show();
            $("#validEmail").hide();
        }
        else {
            $("#invalidEmail").hide();
            $("#validEmail").show();
        }
    });
});

$(document).on("propertychange change keyup paste input", "#USER_NAME", () => {
    const USER_NAME = $("#USER_NAME").val();

    axios({
        method: "post",
        url: "/user/validate-name",
        data: {USER_NAME},
        dataType: "String",
        headers: {"Content-Type": "application/json"}

    }).then(res => {
        if (res.data == false) {
            $("#invalidName").show();
            $("#validName").hide();
        }
        else {
            $("#invalidName").hide();
            $("#validName").show();
        }
    });
});

$(document).on("propertychange change keyup paste input", "#USER_PWD", () => {
    // 숫자, 영문자, 특수문자를 각각 1개 이상 조합한 8~20 길이의 문자열
    const regExp = /^.*(?=^.{8,20}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
    const USER_PWD = $("#USER_PWD").val();

    if (regExp.test(USER_PWD)) {
        $("#validPwd").show();
        $("#invalidPwd").hide();
    } else {
        $("#validPwd").hide();
        $("#invalidPwd").show();
    }
});

$(document).on("propertychange change keyup paste input", "#USER_PWD2", () => {
    const regExp = /^.*(?=^.{8,20}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
    const USER_PWD = $("#USER_PWD").val();
    const USER_PWD2 = $("#USER_PWD2").val();

    if (regExp.test(USER_PWD) && USER_PWD == USER_PWD2) {
        $("#validPwd2").show();
        $("#invalidPwd2").hide();
    } else {
        $("#validPwd2").hide();
        $("#invalidPwd2").show();
    }
});

// 향후 리팩토링 필요
$(document).on("click", "#signup-button", () => {
    const USER_EMAIL = $("#USER_EMAIL").val();
    const USER_NAME = $("#USER_NAME").val();
    const USER_PWD = $("#USER_PWD").val();
    const USER_PWD2 = $("#USER_PWD2").val();
    const MARKET_CHK = $("#MARKET_CHK").prop("checked");
    const PRIVAC_CHK = $("#PRIVAC_CHK").prop("checked");

    // 패스워드 동일한지 검사
    if (USER_PWD != USER_PWD2) {
        alert("비밀 번호를 체크해주세요.");
        return;
    }

    // 필수항목 전부 체크됐는지 검사
    let allChecked = true;
    $(".essential-chk").each(function () {
        if (!this.checked) {
            allChecked = false;
            return false; // 반복문 탈출
        }
    });
    if (!allChecked) {
        alert("필수 약관을 체크해주세요.");
        return;
    }

    axios({
        method: "post",
        url: "/user/validate-signup",
        data: {USER_EMAIL, USER_NAME, USER_PWD, MARKET_CHK, PRIVAC_CHK},
        dataType: "String",
        headers: {"Content-Type": "application/json"}

    }).then(res => {
        console.log(res.data);
        if (res.data == true) {
            window.location.replace("joinConfirm?USER_EMAIL=" + USER_EMAIL);
        }
        else {
            alert("필수 항목을 체크해주세요.");
        }
    });

});