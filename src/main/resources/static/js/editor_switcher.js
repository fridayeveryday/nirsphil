function enable_editor(editor_id, hidden_input_id, button_id,edit_button_class, preview_button_class) {

    let raw_data = $('#' + hidden_input_id).val();
    $('#' + editor_id).summernote('code', raw_data);
    // $('#' + editor_id).summernote( {disableDragAndDrop: true});
    $(".note-editing-area").bind(
        // "propertychange change keyup paste input",
      'DOMSubtreeModified', function () {
            let data = $('#' + editor_id).summernote('code');
            $('#' + hidden_input_id).val(data);
        })

    $('#' + button_id).removeClass(edit_button_class).addClass(preview_button_class).val("Предпросмотр");
    // $('#' + button_id).val("Предпросмотр");


}

function en_dis_able_editor(editor_id, hidden_input_id, button_id, edit_button_class, preview_button_class) {
    if ($('#' + button_id).hasClass(edit_button_class)) {
        enable_editor(editor_id, hidden_input_id, button_id,edit_button_class, preview_button_class)
    }else{
        disable_editor(editor_id, button_id, edit_button_class);
    }
}
function disable_editor(editor_id, button_id, edit_button_class, preview_button_class) {
    $('#' + editor_id).summernote('destroy');
    $('#' + button_id).removeClass(preview_button_class).addClass(edit_button_class).val("Редактировать");
    // $('#' + button_id).val("Редактировать");

}

