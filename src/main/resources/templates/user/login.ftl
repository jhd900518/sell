<html>
<#include "../common/header.ftl">
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <form role="form" method="post" action="/sell/seller/login">
                <div class="form-group">
                    <label>账号</label>
                    <input name="username" type="text" class="form-control" id="exampleInputEmail1" />
                </div>
                <div class="form-group">
                    <label>密码</label>
                    <input name="password" type="password" class="form-control" id="exampleInputPassword1" />
                </div>
                <button type="submit" class="btn btn-default">登录</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>