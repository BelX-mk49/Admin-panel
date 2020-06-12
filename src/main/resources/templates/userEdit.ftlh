<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">

<@c.page>
    <#if user??>
        User editor
        <form action="/user/delete" method="post">
            <label class="col-sm-2 col-form-label">
                <input type="text" readonly class="form-control-plaintext" name="username" value="${user.username}">
            </label>
            <input type="hidden" value="${user.userId}" name="userId">
            <input type="hidden" value="${_csrf.token}" name="_csrf">
            <button class="btn btn-danger" type="submit">Delete ${user.username}</button>
        </form>
        <#else>
        You are not login
    </#if>

</@c.page>
