$(document).ready(function() {
    showUserInfo();
})


function showUserInfo() {
    $.ajax({
        method: "get",
        url: "/principal",
        success: function (principal) {
            $("#span-username").text(principal.email);
            $("#span-roles").text(principal.roles.join(" "));
            if (principal.roles.includes("ADMIN")) {
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
            <td>${userInfo.first_name}</td>
            <td>${userInfo.last_name}</td>
            <td>${userInfo.age}</td>
            <td>${userInfo.email}</td>
            <td>${userInfo.roles.join(" ")}</td>
        </tr>
        `
    )
}