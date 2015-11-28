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
                email: { required: true },
                company: { required: true },
                name: { required: true },
                title: { required: true },
                contactPhone: { required: true }
            },

            messages: {
                email: { required: '请输入Email地址', maxlength: $app.resources.msg_maxLength },
                company: {required: '请输入公司名称', maxlength: $app.resources.msg_maxLength},
                name: {required: '请输入联系人姓名', maxlength: $app.resources.msg_maxLength},
                title: {required: '请输入联系人职位', maxlength: $app.resources.msg_maxLength},
                contactPhone: {required: '请输入联系人电话', maxlength: $app.resources.msg_maxLength}
            },

            submitHandler: function () {
                try {
                    $app.ajaxSubmit({
                        form: 'register_form',
                        url: $app.absolute("/rest/user/register"),
                        hint: '注册中...'
                    }).done(function(r) {
                        $app.showMessage("您的注册请求已经记录，稍后我们的销售人员会与您联系，谢谢您选择我们的服务", function() {
                            window.location.reload();
                        });
                    });
                } catch(e) {
                    console.log(e);
                }
                return false;
            }
        });
    }

    /*function setup_register_form() {
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
    }*/
})(jQuery);