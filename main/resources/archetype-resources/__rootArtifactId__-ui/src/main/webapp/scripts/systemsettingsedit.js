$('#encryptToggle').click(function() {
    var $id = $(this).find('.active').data("id");
    var $encrypted = $(this).find('.active').data("encrypted");
    window.location = "systemsettings/edit?id=" + $id + "&encrypted=" + !$encrypted;
});

$('#editSetting').click(function() {
    var $encrypted = $(this).data("encrypted");
    var $id = $(this).data("id");
    window.location = "systemsettings/edit?id=" + $id + "&encrypted=" + $encrypted + "&edit=true";
});