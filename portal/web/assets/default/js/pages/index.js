(function($){

    app_inits.push(function() {
        setup_login_form()
        setup_register_form();
    });

    function setup_login_form() {
        var form = $("#login_form");
        form.validate({
            focusInvalid: true,
            errorPlacement: function () { },
            rules: {
                userName: { required: true },
                password: { required: true }
            },

            messages: {
                userName: { required: '请输入用户名', maxlength: $app.resources.msg_maxLength },
                password: {required: '请输入密码', maxlength: $app.resources.msg_maxLength}
            },

            submitHandler: function () {
                try {
                    $app.ajaxSubmit({
                        form: 'login_form',
                        url: $app.absolute("/rest/user/login"),
                        hint: '登录中...'
                    }).done(function(r) {
                        window.location.href = $app.absolute("/home");
                    });
                } catch(e) {
                    console.log(e);
                }
                return false;
            }
        });
    }

    function setup_register_form() {
        var form = $("#register_form");
        form.validate({
            focusInvalid: true,
            errorPlacement: function () { },
            rules: {
                userName: { required: true },
                password: { required: true },
                passwordConfirm: { equalTo: '#register_password' }
            },

            messages: {
                userName: { required: '请输入用户名', maxlength: $app.resources.msg_maxLength },
                password: {required: '请输入密码', maxlength: $app.resources.msg_maxLength},
                passwordConfirm: {equalTo: '两次输入密码不一致'}
            },

            submitHandler: function () {
                try {
                    $app.ajaxSubmit({
                        form: 'register_form',
                        url: $app.absolute("/rest/user/register"),
                        hint: '注册中...'
                    }).done(function(r) {
                        console.log(r);
                    });
                } catch(e) {
                    console.log(e);
                }
                return false;
            }
        });
    }
})(jQuery);