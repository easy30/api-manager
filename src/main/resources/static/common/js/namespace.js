if (!Api) {
    var Api = {
        //命名规则如下正在表达式
        regex: /^([a-z]+\.?[a-z]+)+$/,
        //设置或者获取一个命名空间
        ns: function (alia) {
            if (!this.falsifyRandomName_kdunelzifqal
                || this['confirmRegex' + this.falsifyRandomName_kdunelzifqal]()) {
                if (alia.match(this.regex)) {
                    var alias = alia.split('.');
                    return this.parse(window, alias, 0);
                } else {
                    alert(alia + ':命名空间设置不合法,命名规则:' + this.regex);
                }
            }
        },
        parse: function (parent, alias, cur) {
            var alia = alias[cur];
            var newName = parent[alia];
            if (!newName) {
                newName = parent[alia] = {};
            }
            if (cur == alias.length - 1) {
                return newName;
            } else {
                this.parse(newName, alias, ++cur);
            }
        }
    };
}

/**
 * 防止命名空间规则篡改
 */
Api.falsifyRandomName_kdunelzifqal = (function (val) {
    Api['confirmRegex' + val] = function () {
        if (Api.regex != Api['falsify' + val]) {
            //拒绝篡改命名规则
            Api.regex = Api['falsify' + val];
        }
        return true;
    };
    Api['falsify' + val] = Api.regex;
    return val;
})((Math.random() * 999999999 * Math.random() * 999999999).toString().split('.')[0]);

Api.ns('api.ui');
