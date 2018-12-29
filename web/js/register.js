function check_cpwd() {
    var pass = $('#password').val();
    var cpass = $('#password2').val();

    if (pass != cpass) {
        $('#password2').next().html('两次输入的密码不一致');
        $('#password2').next().css('color', '#e62e2e');
        $('#password2').next().show();
        error_check_password = true;
    } else {
        $('#password2').next().hide();
        error_check_password = false;
    }

}

$('#reg_form').submit(function () {
    check_cpwd();
    if ( error_check_password == false) {
        return true;
    } else {
        return false;
    }
});
