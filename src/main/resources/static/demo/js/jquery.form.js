;(function ($) {
    $.fn.extend({
        serializeObject: function () {
            var jsonObject = {};
            var inputs = this.serializeArray();
            $.each(inputs, function () {
                if (jsonObject[this.name]) {
                    if (!jsonObject[this.name].push) {
                        jsonObject[this.name] = [jsonObject[this.name]];
                    }
                    jsonObject[this.name].push(this.value || '');
                } else {
                    jsonObject[this.name] = this.value || '';
                }
            });
            return jsonObject;
        }
    });
})(jQuery);