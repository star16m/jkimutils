$(document).ready(function() {
	var key = document.location.pathname.replace("/main/", "");
	console.log("call contents[" + key + "]");
    $.ajax({
        type: "GET",
        url: "/contents/" + key,
        data: {},
        dataType: "json",
        success: function(response) {
        console.log(response);
        $("div.main-title").html(response.title);
        $("div.main-description").html(response.description);
		    $('#datatable').dataTable({
				dom: "Bfrtip",
				aaData: response.data,
		        columnDefs: response.header,
				buttons: [
				{
				  extend: "copy",
				  className: "btn-sm"
				},
				{
				  extend: "csv",
				  className: "btn-sm"
				},
				{
				  extend: "excel",
				  className: "btn-sm"
				},
				{
				  extend: "pdfHtml5",
				  className: "btn-sm"
				},
				/*{
				  extend: "print",
				  className: "btn-sm"
				},*/
				]
		    });
        }
    });
});