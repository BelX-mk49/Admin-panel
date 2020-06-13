<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">

<@c.page>
    <#if user??>
        <table class="table">
            <#list users as user>
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Username</th>
                    <th scope="col">Email</th>
                    <th scope="col">Registration date</th>
                    <th scope="col">Last login date</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th scope="row">${user.userId}</th>
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td>${user.registrationDate}</td>
                    <td>${user.lastLoginDate}</td>
                    <td><a class="btn btn-danger" href="/user/${user.userId}">Ban</a></td>
                </tr>
                </tbody>
            </#list>
        </table>
    <#else>
        Please log in
    </#if>

</@c.page>
