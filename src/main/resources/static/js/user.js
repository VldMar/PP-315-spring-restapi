$(document).ready(function() {
    showUserInfo();
})

function showUserInfo() {
    $.ajax({
        method: "get",
        url: "/principal",
        success: function (principal) {
            $("#span-username").text(principal.email);
            $("#span-roles").text(principal.roles.map(u => u.name).join(" "));
            if (principal.roles.map(u => u.name).includes("ADMIN")) {
                $("#admin-link").show();
            }
            fillUserInfoTable(principal);
        }
    });
}

function fillUserInfoTable(userInfo) {
    $("#user-info-table tbody").append(
        `
        <tr>
            <td>${userInfo.id}</td>
            <td>${userInfo.name}</td>
            <td>${userInfo.lastName}</td>
            <td>${userInfo.age}</td>
            <td>${userInfo.email}</td>
            <td>${userInfo.roles.map(u => u.name).join(" ")}</td>
        </tr>
        `
    )
}