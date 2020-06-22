function check_passwords(password_div_id, old_password_id,
                         new_password_id, new_password2_id,
                         empty_error_id, mismatched_pass_error_id,
                         submit_button_id,
                         enable_btn_class, disable_btn_class) {
    $('#' + password_div_id).on(
        'input', function () {
            let old_passwd = $('#' + old_password_id).val();
            let new_passwd = $('#' + new_password_id).val();
            let new_passwd2 = $('#' + new_password2_id).val();
            let submit_button = $('#' + submit_button_id);

            // если скрипт применяется в регистрации,
            // то старого пароля нет и его перменную надо сделать постоянно истинной
            if (old_passwd === undefined)
                old_passwd = true;

            if (
                // если блок с паролями виден (в личном кабинете он будет появляться и скрываться по клику)
                $('#' + password_div_id).is(":visible")
                // и все поля заполнены и новые пароли совпадают
                && old_passwd && check_new_passwds(new_passwd, new_passwd2)
            ) {
                // тогда кнопка для сохранения данных активируется
                submit_button.removeClass(disable_btn_class).addClass(enable_btn_class);
                submit_button.prop('disabled', false);
                $('#' + empty_error_id).text("");
                $('#' + mismatched_pass_error_id).text("");
            } else {
                // старый пароль не введен
                // или постоянный true в случае если скрипт используется в регистрации новго пользователя
                if (!old_passwd) {
                    $('#' + empty_error_id).text("Введите пароль");
                } else {
                    $('#' + empty_error_id).text("");

                }
                // новые пароли не совпадают
                if (new_passwd != new_passwd2) {
                    $('#' + mismatched_pass_error_id).text("Введенные пароли не совпадают");
                } else {
                    $('#' + mismatched_pass_error_id).text("");

                }

                // кнопка деактивируется
                submit_button.removeClass(enable_btn_class).addClass(disable_btn_class);
                submit_button.prop('disabled', true);
            }
        }
    )
}


function check_new_passwds(new_passwd, new_passwd2) {
    return new_passwd == new_passwd2 && new_passwd && new_passwd2;
}