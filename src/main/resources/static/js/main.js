function showModal(data) {
    $("#modal-page").html(data);
    $("#modal-page .modal").modal("show");
}

function closeModal() {
    $("#modal-page").html("");
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
}

function onUpdateClick(id) {
   $.ajax({
        type: "get",
        url: "/update/" + id,
        success: function (data) {
            showModal(data);
        }
    });
}

function onDeleteClick(id) {
    $.ajax({
        type: "get",
        url: "/delete/" + id,
        success: function (data) {
            showModal(data);
        }
    });
}