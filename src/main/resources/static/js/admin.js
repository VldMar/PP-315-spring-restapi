$(document).ready(function() {
    setCurrentUser();
    fillUsersTable();
    subscribeUpdateClick();




    // $("#update-form").submit(function(e) {
    //     e.preventDefault();
    //     let form = $(this);
    //
    //     $.ajax({
    //         type: form.attr("method"),
    //         url:  form.attr("action"),
    //         data: form.serialize(),
    //         success: function (data) {
    //             console.log(data);
    //         }
    //     });
    // })
})

function setCurrentUser() {
    $.ajax({
        method: "get",
        url: "/principal",
        success: function (data) {
            $("#span-username").text(data.email);
            $("#span-roles").text(data.roles.join(" "));
        }
    });
}

function fillUsersTable() {
    $.ajax({
        method: "get",
        url: "/api/users",
        success: function (users) {
            users.forEach(user => {
                $("#all-users-table tbody").append(
                    `
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.first_name}</td>
                        <td>${user.last_name}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td>
                        <td>${user.roles.join(" ")}</td>
                        <td>
                            <button
                            type="button" 
                            class="btn btn-info editBtn" 
                            data-toggle="modal" 
                            data-userid="${user.id}">Edit</button>
                        </td>
                        <td>
                            <button
                            type="button" 
                            class="btn btn-danger deleteBtn" 
                            data-toggle="modal" 
                            data-userid="${user.id}">Delete</button>
                        </td>
                    </tr>
                `
                )
            })
        }
    });
}

function executeAjaxGet(url, successHandler) {
    $.ajax({
        method: "get",
        url: url,
        success: function (data) {
            successHandler(data)
        }
    });
}

function postData(form, successHandler) {
    $.ajax({
        method: form.attr("method"),
        url:  form.attr("action"),
        data: form.serialize(),
        success: function (data) {
            successHandler(data)
        }
    });
}

function subscribeUpdateClick() {
    $("body").on("click", ".editBtn", function() {
        let id = $(this).attr("data-userid");
        console.log("id=" + id)
        executeAjaxGet(`/api/users/${id}`, function(user) {
            console.log(user)
            $("#edit-id").val(user.id);
            $("#edit-first-name").val(user.first_name);
            $("#edit-last-name").val(user.last_name);
            $("#edit-age").val(user.age);
            $("#edit-email").val(user.email);
            $("#edit-password").val('');
            $("#edit-role").val(user.id);


            //TODO:: get all roles for select
            user.roles.forEach(role => {
                $("#edit-role-select").append(`
                    <option value="${role}" selected>${role}</option>
                `)
            })

            $("#editModal").modal("show");
        });
    });
}

function getUserById(id) {
    let user= {}
    $.ajax({
        method: "get",
        url: "/api/users/" + id,
        success: function (data) {
            user = data
        }
    });

    return user;
}

function getAllUsers() {
    let users= {}
    $.ajax({
        method: "get",
        url: "/api/users",
        success: function (data) {
            users = data
        }
    });

    return users;
}

function handleEditClick() {
    $(".editBtn").on("click", function() {
        let id = $(this).attr("data-userid");
        let user = getUserById(id);

    });
}

function handleDeleteClick() {
    $(".deleteBtn").on("click", function() {
        let id = $(this).attr("data-userid");
    });
}


/*
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
}*/