$(document).ready(async function() {
    await setCurrentUser();
    await fillSelectsRoles();
    await fillUsersTable();
    onUserCreate();
    onModalSubmitClick();
})

const fetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    },
    getPrincipal: async () => await fetch("/principal"),
    getAllUsers: async () => await fetch("/admin/users"),
    getUserById: async (id) => await fetch(`/admin/users/${id}`),
    createUser: async (user) => await fetch("/admin/users", {
        method: 'POST',
        headers: fetchService.head,
        body: JSON.stringify(user)
    }),
    updateUser: async (userId, user) => await fetch(`/admin/users/${userId}`, {
        method: "PUT",
        headers: fetchService.head,
        body: JSON.stringify(user),
    }),
    deleteUser: async (id) => await fetch(`/admin/users/${id}`, {
        method: "DELETE",
        headers: fetchService.head
    }),
    getAllRoles: async() => await fetch("/admin/roles")
}

async function setCurrentUser() {
    await fetchService.getPrincipal()
        .then(res => res.json())
        .then(principal => {
            $("#span-username").text(principal.email);
            $("#span-roles").text(principal.roles.map(u => u.name).join(" "));
        });
}

async function fillSelectsRoles() {
    await fetchService.getAllRoles()
        .then(res => res.json())
        .then(roles => roles.forEach(role => {
            $("#modal-form select").append(`<option value="${role}">${role}</option>`);
            $("#new-user-form select").append(`<option value="${role}">${role}</option>`);
            if (role === "USER") {
                $("#new-user-form #new-user__roles option:last").attr('selected', true);
            }
        }));
}

function onUserCreate() {
    $("#new-user-form").on("click", "button[type=submit]", async function(e) {
        e.preventDefault();
        let user = {
            name: $("#new-user__first-name").val(),
            lastName: $("#new-user__last-name").val(),
            age: $("#new-user__age").val(),
            email: $("#new-user__email").val(),
            password: $("#new-user__password").val(),
            roles: $("#new-user__roles").val()
        };

        await fetchService.createUser(user)
            .then(res => res.json())
            .then(user => {
               addUserInTable(user);
                $("#main-tab li:first-child a").tab("show");
                $("#new-user-form input").val('')
            });
    });
}

function onModalSubmitClick() {
    $("#modal button[type='submit']").on("click", async function(e) {
        e.preventDefault();
        let action = $(this).attr("data-action");
        switch (action) {
            case "edit":
                let newUser = {
                    id: $("#modal-form #id").val(),
                    name: $("#modal-form #first-name").val(),
                    lastName: $("#modal-form #last-name").val(),
                    age: $("#modal-form #age").val(),
                    email: $("#modal-form #email").val(),
                    password: $("#modal-form #password").val(),
                    roles: $("#modal-form #roles").val()
                }
                await fetchService.updateUser(newUser.id, newUser)
                    .then(res => res.json())
                    .then(resUser => {
                        showAlert("Данные о пользователе успешно изменены", "success")
                        updateUserRow(resUser);
                        $("#modal").modal("hide");
                    });
                break;
            case "delete":
                let userId = $("#modal-form #id").val()
                await fetchService.deleteUser(userId)
                    .then(res => res.text())
                    .then(successMessage => {
                        showAlert(successMessage, "success");
                        deleteUserRow(userId);
                        $("#modal").modal("hide");
                    });
                break;
        }
    })
}

async function fillUsersTable() {
    await fetchService.getAllUsers()
        .then(res => res.json())
        .then(users => users.forEach(user => addUserInTable(user)));

    // подписываем все кнопки на событие
    $("#all-users-table").on("click", ".btn-action", function() {
        let id = $(this).attr("data-userid");
        let action = $(this).attr("data-action");

        fetchService.getUserById(id)
            .then(res => res.json())
            .then(user => {
                fillModalAndShow(user, action);
            });
    })
}

function fillModalAndShow(user, action) {
    // заполняем модальное окно
    $("#modal-form #id").val(user.id);
    $("#modal-form #first-name").val(user.name);
    $("#modal-form #last-name").val(user.lastName);
    $("#modal-form #age").val(user.age);
    $("#modal-form #email").val(user.email);

    // логика в зависимости от действия
    switch (action) {
        case "edit":
            fillEditModal(user);
            break;
        case "delete":
            fillDeleteModal(user);
            break;
    }

    $("#modal").modal("show");
}

function fillEditModal(user) {
    // настриваем метод
    $("#modal-form").attr("method", "put")
    // настраиваем пароль
    $("#modal-form #password-group").show();
    // настраиваем заголовок
    $("#modal .modal-title").text("Edit User");
    // настраиваем роли
    $(`#modal-form #roles option`).show();
    user.roles.forEach(role => {
        $(`#modal-form #roles option:contains(${role.name})`).prop("selected","selected")
    });
    // все инпуты доступны для изменений
    $("#modal input:not(:first)").prop("readonly", false);

    // настраиваем кнопку
    $("#modal button[type='submit']")
        .text("Edit")
        .attr( "class", "btn btn-primary")
        .attr("data-action", "edit")
        .attr("data-userid", user.id);

}

function fillDeleteModal(user) {
    // настриваем метод
    $("#modal-form").attr("method", "delete")
    // настраиваем пароль
    $("#modal-form #password-group").hide();
    // настраиваем заголовок
    $("#modal .modal-title").text("Delete User");
    // настраиваем роли
    $(`#modal-form #roles option`).hide();
    user.roles.forEach(role => {
        $(`#modal-form #roles option:contains(${role.name})`).prop("selected","selected").show()
    });
    // все инпуты доступны для чтения
    $("#modal input").prop("readonly", true);

    // настраиваем кнопку
    $("#modal button[type='submit']")
        .text("Delete")
        .attr("class", "btn btn-danger")
        .attr("data-action", "delete")
        .attr("data-userid", user.id);
}


function addUserInTable(user) {
    console.log(user);
    $("#all-users-table tbody").append(
        `
        <tr data-userid="${user.id}">
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${user.roles.map(u => u.name).join(" ")}</td>
            <td>
                <button
                type="button" 
                class="btn btn-info btn-action" 
                data-toggle="modal" 
                data-userid="${user.id}"
                data-action="edit">Edit</button>
            </td>
            <td>
                <button
                type="button" 
                class="btn btn-danger btn-action" 
                data-toggle="modal" 
                data-userid="${user.id}"
                data-action="delete">Delete</button>
            </td>
        </tr>
        `
    )
}

function updateUserRow(user) {
    $("#all-users-table tbody").find(`tr[data-userid=${user.id}]`).html(
        `
       <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${user.roles.map(u => u.name).join(" ")}</td>
            <td>
                <button
                type="button" 
                class="btn btn-info btn-action" 
                data-toggle="modal" 
                data-userid="${user.id}"
                data-action="edit">Edit</button>
            </td>
            <td>
                <button
                type="button" 
                class="btn btn-danger btn-action" 
                data-toggle="modal" 
                data-userid="${user.id}"
                data-action="delete">Delete</button>
            </td>
        `
    )
}

function deleteUserRow(userId) {
    $("#all-users-table tbody").find(`tr[data-userid=${userId}]`).empty();
}

function showAlert(mess, alertType) {
    let alertClass = alertType === "success" ? "alert-success" : "alert-danger"
    $(".alert").text(mess).attr("class", "alert " + alertClass).fadeTo(2000, 500).slideDown(500, function(){
        $(".alert").show().slideUp(500);
    });
}