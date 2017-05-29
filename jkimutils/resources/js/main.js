$(document).ready(function() {
    $("ul.main-menu").empty();
    $.ajax({
        type: "GET",
        url: "/menu/list",
        data: {},
        dataType: "json",
        success: function(response) {
            $.each(response, function(i) {
                model = this;
                row = "<li><a href='" + model.link + "'>";
                row += model.name;
                row += "</a></li>";
                $(row).appendTo("ul.main-menu");
            });
        }
    });
});