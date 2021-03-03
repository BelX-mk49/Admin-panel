<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">

<@c.page>
    <#if user??>
        <table class="table">
            <thead>
            <tr>
                <th>
                    <label for="checkbox"></label><input id="checkbox" type="checkbox" onclick="toggle(this)">Select/unselect all<br/>
                </th>
                <th scope="col">Id</th>
                <th scope="col">Username</th>
                <th scope="col">Email</th>
                <th scope="col">Registration date</th>
                <th scope="col">Last login date</th>
                <th scope="col">Active</th>
            </tr>
            </thead>
            <div class="btn-group" role="group" aria-label="Toolbar">
                <form action="/user/edit" method="post">
                    <button id="btn1" disabled="disabled" name="action"
                            value="block" type="submit" class="btn btn-secondary">Block</button>
                    <button id="btn2" disabled="disabled" name="action"
                            value="unblock" type="submit" class="btn btn-primary">Unblock</button>
                    <button id="btn3" disabled="disabled" name="action"
                            value="delete" type="submit" class="btn btn-danger">Delete</button>
                    <#list users as user>
                        <tbody>
                        <tr>
                            <th>
                                <input type="hidden" value="${_csrf.token}" name="_csrf">
                                <label><input id="checkbox" name="userId" value="${user.userId}" type="checkbox"></label>
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
                </form>
            </div>
        </table>
        <script src="https://snipp.ru/cdn/jquery/2.1.1/jquery.min.js"></script>
        <script>
            function toggle(source) {
                checkboxes = document.getElementsByName('userId');
                for (var i = 0, n = checkboxes.length; i < n; i++) {
                    checkboxes[i].checked = source.checked;
                }
            }
        </script>
        <script>
            $('input:checkbox').click(function(){
                if ($(this).is(':checked')){
                    $('#btn1').removeAttr('disabled');
                    $('#btn2').removeAttr('disabled');
                    $('#btn3').removeAttr('disabled');
                } else {
                    $('#btn1').attr('disabled', 'disabled');
                    $('#btn2').attr('disabled', 'disabled');
                    $('#btn3').attr('disabled', 'disabled');
                }
            });
        </script>
    <#else>
        Please log in
    </#if>
</@c.page>
