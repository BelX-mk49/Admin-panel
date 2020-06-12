<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">

<@c.page>
    <#if user??>
        List of users
        <table>
            <thead>
            <tr>
                <th>Name</th>
                <th>Role</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list users as user>
                <tr>
                    <td>${user.username}</td>
                    <td><a href="/user/${user.userId}">edit</a></td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        Please log in
    </#if>

</@c.page>
