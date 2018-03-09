<#ftl encoding="utf-8">
<nav class="navbar navbar-expand-md  " style="margin-bottom: 0px;border-radius: 0px">
    <div class="container">
        <a class="navbar-brand" href="#">
            <img  style="width: 48px" src="${ctx}/res/img/logo.png">
            <span style="vertical-align: middle;color:#f0ad4e;margin-left:5px;font-size:18px;font-weight:bold">API Manager</span>
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div id="collapse navbar-collapse" class="navbar-collapse collapse" id="navbarsExampleDefault">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="#">项目</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">部门</a>
                </li>
                <li class="nav-item"><a  class="nav-link" href="javascript:alert('API Manager')">关于</a></li>

            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>
<p></p>
<script>
    if( document.getElementById("menu_${menu}")) {
        document.getElementById("menu_${menu}").style.color = "red";
    }


</script>