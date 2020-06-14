<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">

<@c.page>
    <#if user??>
        <div class="btn-group" role="group" aria-label="Toolbar">
                <form action="/user/block" method="post">
                    <button type="submit" class="btn btn-secondary">Block</button>
                </form>
                <form action="/user/unblock" method="post">
                    <button type="submit" class="btn btn-primary">Unblock</button>
                </form>
                <form action="/user/delete" method="post">
                    <button type="submit" class="btn btn-danger">Delete</button>
                </form>
        </div>
        <form action="/user/block" method="post">
            <table class="table">
                <thead>
                <tr>
                    <th>
                        <input type="checkbox" onclick="toggle(this)"/>Select/unselect all<br/>
                    </th>
                    <th scope="col">Id</th>
                    <th scope="col">Username</th>
                    <th scope="col">Email</th>
                    <th scope="col">Registration date</th>
                    <th scope="col">Last login date</th>
                    <th scope="col">Block</th>
                </tr>
                </thead>
                <#list users as user>
                    <tbody>
                    <tr>
                        <th>
                            <label><input type="checkbox" name="foo" value="${user.userId}"></label>
                        </th>
                        <td>${user.userId}</td>
                        <td>${user.username}</td>
                        <td>${user.email}</td>
                        <td>${user.registrationDate}</td>
                        <td>${user.lastLoginDate}</td>
                        <td>${user.status}</td>
                    </tr>
                    </tbody>
                </#list>

            </table>
        </form>
        <script src="https://snipp.ru/cdn/jquery/2.1.1/jquery.min.js"></script>
        <script>
            function toggle(source) {
                checkboxes = document.getElementsByName('foo');
                for (var i = 0, n = checkboxes.length; i < n; i++) {
                    checkboxes[i].checked = source.checked;
                }
            }
        </script>
    <#else>
        Please log in
    </#if>

</@c.page>
