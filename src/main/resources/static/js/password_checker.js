function checker(password_div_id, old_password_id,
                 new_password_id, new_password2_id,
                 empty_error_id, mismatched_pass_error_id,
                 submit_button_id,
                 enable_btn_class, disable_btn_class) {
    $('#' + password_div_id).on(
        'input', function () {
            // если все поля пустые
            if ((!$('#' + old_password_id).val() && !$('#' + new_password_id).val() && !$('#' + new_password2_id).val())
                // или все заполнены
                || $('#' + old_password_id).val() && $('#' + new_password_id).val() && $('#' + new_password2_id).val()
                // и новый пароль, введенный дважды совпадают
                && $('#' + new_password_id).val() == $('#' + new_password2_id).val()) {
                // тогда кнопка для сохранения данных активируется
                $('#' + submit_button_id).removeClass(disable_btn_class).addClass(enable_btn_class);
                $('#' + submit_button_id).prop('disabled', false);
                $('#' + empty_error_id).text("");
                $('#' + mismatched_pass_error_id).text("");
            } else {
                // старый пароль не введен
                if (!$('#' + old_password_id).val()) {
                    $('#' + empty_error_id).text("Введите старый пароль");
                }else{
                    $('#' + empty_error_id).text("");

                }
                // новые пароли не совпадают
                if ($('#' + new_password_id).val() != $('#' + new_password2_id).val()) {
                    $('#' + mismatched_pass_error_id).text("Введенные пароли не совпадают");
                }else{
                    $('#' + mismatched_pass_error_id).text("");

                }

                // кнопка деактивируется
                $('#' + submit_button_id).removeClass(enable_btn_class).addClass(disable_btn_class);
                $('#' + submit_button_id).prop('disabled', true);
            }
        }
    )
}