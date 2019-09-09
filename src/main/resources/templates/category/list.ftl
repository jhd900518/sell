<html>
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
    <#include "../common/nav.ftl">
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>
                                类目id
                            </th>
                            <th>
                                名字
                            </th>
                            <th>
                                分类编号
                            </th>
                            <th>
                                创建时间
                            </th>
                            <th>
                                修改时间
                            </th>
                            <th>
                                操作
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list categoryList as categoryproduct>
                            <tr>
                                <td>
                                    ${categoryproduct.getCategoryId()}
                                </td>
                                <td>
                                    ${categoryproduct.getCategoryName()}
                                </td>
                                <td>
                                    ${categoryproduct.getCategoryType()}
                                </td>
                                <td>
                                    ${categoryproduct.getCreateTime()}
                                </td>
                                <td>
                                    ${categoryproduct.getUpdateTime()}
                                </td>
                                <td><a href="/sell/seller/category/index?categoryId=${categoryproduct.categoryId}">修改</a>
                                </td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>