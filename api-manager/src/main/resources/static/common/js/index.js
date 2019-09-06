api.util.loadScripts([
    'plugins/mock/mock-min.js',
    'plugins/jsonview/js/jquery.jsonview.js',
    'common/js/jquery.md5.js',
    'common/ui/jquery.param.js',
    'common/ui/jquery.chosen.js',
    'common/ui/jquery.dialog.js',
    'common/ui/jquery.pager.js',
    'common/ui/jquery.form.js',
    'common/ui/jquery.tab.js',
    'common/ui/jquery.load.js',
    'common/ui/jquery.editTable.js',
    'common/ui/jquery.editSelect.js',
    'common/ui/jquery.progress.js',
    'common/ui/jquery.downMenu.js',
    'common/ui/jquery.autocomplete.js'
    ], function () {
    var headConf = {
        container: '#head',
        url: 'head.html',
        loaded: function () {
            api.util.loadScript(api.util.getUrl('common/js/head.js'), function () {
                $.ajax({
                    url: api.util.getUrl('/apimanager/user/findBySession'),
                    type: 'GET',
                    dataType: 'json',
                    success: function (result) {
                        $('#userName').text(result.data.userName);
                    }
                })
            });
        }
    }
    api.ui.load(headConf);

    var menuNavConf = {
        container: '#menu-nav',
        url: 'menu-nav.html',
        loaded: function () {
            $('.menu-list .inactive').click(function () {
                if ($(this).siblings('ul').css('display') != 'none') {
                    $(this).addClass('inactives');
                    $(this).siblings('ul').slideUp(100);
                } else {
                    $(this).removeClass('inactives');
                    $(this).siblings('ul').slideDown(100).children('li');
                }
            })
        }
    }
    api.ui.load(menuNavConf);

    // 基于准备好的dom，初始化echarts实例
    var containerConf = {
        container: '#container',
        url: 'html/action/actionCount.html',
        loaded: function () {
            $('#depart').parent('.container-fluid').css('display','none');
            $.ajax({
                url: api.util.getUrl('apimanager/action/countGroupByProject'),
                type: 'get',
                dataType: 'json',
                success: function (result) {
                    var datas = result.data;
                    var xData = [],series = [];
                    if(datas){
                        $.each(datas, function (index, data) {
                            xData.push(data.projectName);
                            series.push(data.actionCount);
                        })
                    }
                    console.log(xData);
                    console.log(series);
                    var myChart = echarts.init(document.getElementById('actionNum'));
                    // 指定图表的配置项和数据
                    var option = {
                        title: {
                            text: '项目接口数量统计',
                            x: '38%',
                            textStyle:{
                                fontSize: 25
                            }
                        },
                        legend: {
                            y: '6%',
                            x: '880px',
                            data: ['数量'],
                            color: 'rgb(42,170,227)',
                            textStyle:{
                                fontSize: 18
                            }
                        },
                        grid: {
                            x: '8%',
                            width:'80%',
                            top: '20%',
                            containLabel: true
                        },
                        xAxis: {
                            data: xData,
                            axisLabel:{
                                interval: 0,
                                rotate: -30
                            }
                        },
                        yAxis: {},
                        series: [{
                            name: '数量',
                            type: 'bar',
                            data: series,
                            label: {
                                show: true,
                                position: 'top',
                                color: 'black'
                            },
                            itemStyle: {
                                color: 'rgb(42,170,227)'
                            }
                        }]
                    };
                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }
            })
        }
    }
    api.ui.load(containerConf);
})
