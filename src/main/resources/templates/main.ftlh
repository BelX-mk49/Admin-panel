<#import "parts/common.ftlh" as c>

<@c.page>
    <h5>Hello,
        <#if user??>${user.username}<#else>guest</#if></h5>
    <div>This is a simple user management system</div>

    <#if user??>
        User editor
        <form action="/user" method="post">
            <label>
                <input type="text" name="username" value="${user.username}">
            </label>
            <input type="hidden" value="${user.userId}" name="userId">
            <input type="hidden" value="${_csrf.token}" name="_csrf">
            <button type="submit">Save</button>
        </form>
        <form action="/user/delete" method="post">
            <label>
                <input type="text" name="username" value="${user.username}">
            </label>
            <input type="hidden" value="${user.userId}" name="userId">
            <input type="hidden" value="${_csrf.token}" name="_csrf">
            <button type="submit">Delete ${user.username}</button>
        </form>
    <#else>
        Please authorise
    </#if>

</@c.page>

