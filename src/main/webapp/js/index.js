// 包装类型销量分析
(function () {
    // 1.实例化对象
    var myChart = echarts.init(document.querySelector(".bar .chart"));
    // 2.指定配置项和数据
    $.get('http://localhost:8088/getPackAgeSales', function (res) {
        var option = {
            color: ['#2f89cf'],
            // 提示框组件
            tooltip: {
                trigger: 'axis',
                axisPointer: { // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            // 修改图表位置大小
            grid: {
                left: '0%',
                top: '10px',
                right: '0%',
                bottom: '4%',
                containLabel: true
            },
            // x轴相关配置
            xAxis: [{
                type: 'category',
                data: res.key,
                axisTick: {
                    alignWithLabel: true
                },
                // 修改刻度标签，相关样式
                axisLabel: {
                    color: "rgba(255,255,255,0.8)",
                    fontSize: 10
                },
                // x轴样式不显示
                axisLine: {
                    show: false
                }
            }],
            // y轴相关配置
            yAxis: [{
                type: 'value',
                // 修改刻度标签，相关样式
                axisLabel: {
                    color: "rgba(255,255,255,0.6)",
                    fontSize: 12
                },
                // y轴样式修改
                axisLine: {
                    lineStyle: {
                        color: "rgba(255,255,255,0.6)",
                        width: 2
                    }
                },
                // y轴分割线的颜色
                splitLine: {
                    lineStyle: {
                        color: "rgba(255,255,255,0.1)"
                    }
                }
            }],
            // 系列列表配置
            series: [{
                name: '总销量',
                type: 'bar',
                barWidth: '35%',
                // ajax传动态数据
                data: res.val,
                itemStyle: {
                    // 修改柱子圆角
                    barBorderRadius: 5
                }
            }]
        };
        // 3.把配置项给实例对象
        myChart.setOption(option);
    });

    // 4.让图表随屏幕自适应
    window.addEventListener('resize', function () {
        myChart.resize();
    })
})();

// 词云
(function () {
    // 1.实例化对象
    var myChart = echarts.init(document.querySelector(".bar2 .chart"));

    // 声明颜色数组
    var myColor = ["#1089E7", "#F57474", "#56D0E3", "#F8B448", "#8B78F6"];
    $.get('http://localhost:8088/getWordCount', function (res) {
        // 2.指定配置项和数据
        var option = {
            tooltip: {
                show: true
            },
            series: [{
                type: "wordCloud",
                gridSize: 6,
                shape: 'diamond',
                sizeRange: [6, 35],
                width: 800,
                height: 500,
                textStyle: {
                    normal: {
                        color: function () {
                            return 'rgb(' + [
                                Math.round(Math.random() * 160),
                                Math.round(Math.random() * 160),
                                Math.round(Math.random() * 160)
                            ].join(',') + ')';
                        }
                    },
                    emphasis: {
                        shadowBlur: 10,
                        shadowColor: '#333'
                    }
                },
                data: res
            }]
        };
        // 3.把配置项给实例对象
        myChart.setOption(option);
    });

    // 4.让图表随屏幕自适应
    window.addEventListener('resize', function () {
        myChart.resize();
    })
})();

// 种类销量
(function () {
    var myChart = echarts.init(document.querySelector(".line .chart"));
    $.get('http://localhost:8088/getGenresSales', function (res) {
        var option = {
            // 修改两条线的颜色
            color: ['#00f2f1', '#ed3f35'],
            tooltip: {
                trigger: 'axis'
            },
            // 图例组件
            legend: {
                // 当serise 有name值时， legend 不需要写data
                // 修改图例组件文字颜色
                textStyle: {
                    color: '#4c9bfd'
                },
                right: '10%',
            },
            grid: {
                top: "20%",
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true,
                show: true, // 显示边框
                borderColor: '#012f4a' // 边框颜色
            },
            xAxis: {
                type: 'category',
                boundaryGap: false, // 去除轴间距
                data: res.key,
                // 去除刻度线
                axisTick: {
                    show: false
                },
                axisLabel: {
                    color: "#4c9bfb" // x轴文本颜色
                },
                axisLine: {
                    show: false // 去除轴线
                }
            },
            yAxis: {
                type: 'value',
                // 去除刻度线
                axisTick: {
                    show: false
                },
                axisLabel: {
                    color: "#4c9bfb" // x轴文本颜色
                },
                axisLine: {
                    show: false // 去除轴线
                },
                splitLine: {
                    lineStyle: {
                        color: "#012f4a"
                    }
                }
            },
            series: [{
                type: 'line',
                smooth: true, // 圆滑的线
                name: '总销量',
                data: res.val
            }
            ]
        };

        myChart.setOption(option);
    });
    // 4.让图表随屏幕自适应
    window.addEventListener('resize', function () {
        myChart.resize();
    })

    // 5.点击切换2020 和 2021 的数据
    $('.line h2 a').on('click', function () {
        // console.log($(this).index());
        // 点击a 之后 根据当前a的索引号 找到对应的 yearData 相关对象
        // console.log(yearData[$(this).index()]);
        var obj = yearData[$(this).index()];
        option.series[0].data = obj.data[0];
        option.series[1].data = obj.data[1];
        // 选中年份高亮
        $('.line h2 a').removeClass('a-active');
        $(this).addClass('a-active');

        // 需要重新渲染
        myChart.setOption(option);
    })
})();

// 价格销量
(function () {
    var myChart = echarts.init(document.querySelector('.line2 .chart'));
    $.get('http://localhost:8088/getPriceSales', function (res) {
        var option = {
            tooltip: {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left'
            },
            series: [
                {
                    name: '销量',
                    type: 'pie',
                    radius: ['40%', '70%'],
                    avoidLabelOverlap: false,
                    itemStyle: {
                        borderRadius: 10,
                        borderColor: '#fff',
                        borderWidth: 2
                    },
                    label: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        label: {
                            show: true,
                            fontSize: 10,
                            fontWeight: 'bold'
                        }
                    },
                    labelLine: {
                        show: false
                    },
                    data: res
                }
            ]
        };
        myChart.setOption(option);
    });

    window.addEventListener('resize', function () {
        myChart.resize();
    })
})();

// 饼形图1
(function () {
    var myChart = echarts.init(document.querySelector(".pie .chart"));
    $.get('http://localhost:8088//getPriceSalesTotal', function (res) {
        var option = {
            tooltip: {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left'
            },
            series: [
                {
                    name: '销售额',
                    type: 'pie',
                    radius: '50%',
                    data: res,
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };

        myChart.setOption(option);
    });
    window.addEventListener('resize', function () {
        myChart.resize();
    })
})();

// 饼形图2
(function () {
    var myChart = echarts.init(document.querySelector('.pie2 .chart'));
    $.get('http://localhost:8088/getShopSalesTop10', function (res) {
        var option = {
            color: ['#2f89cf'],
            // 提示框组件
            tooltip: {
                trigger: 'axis',
                axisPointer: { // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            // 修改图表位置大小
            grid: {
                left: '0%',
                top: '10px',
                right: '0%',
                bottom: '4%',
                containLabel: true
            },
            // x轴相关配置
            xAxis: [{
                type: 'category',
                data: res.key,
                axisTick: {
                    alignWithLabel: true
                },
                // 修改刻度标签，相关样式
                axisLabel: {
                    color: "rgba(255,255,255,0.8)",
                    fontSize: 10
                },
                // x轴样式不显示
                axisLine: {
                    show: false
                }
            }],
            // y轴相关配置
            yAxis: [{
                type: 'value',
                // 修改刻度标签，相关样式
                axisLabel: {
                    color: "rgba(255,255,255,0.6)",
                    fontSize: 12
                },
                // y轴样式修改
                axisLine: {
                    lineStyle: {
                        color: "rgba(255,255,255,0.6)",
                        width: 2
                    }
                },
                // y轴分割线的颜色
                splitLine: {
                    lineStyle: {
                        color: "rgba(255,255,255,0.1)"
                    }
                }
            }],
            // 系列列表配置
            series: [{
                name: '购买人数',
                type: 'bar',
                barWidth: '35%',
                // ajax传动态数据
                data: res.val,
                itemStyle: {
                    // 修改柱子圆角
                    barBorderRadius: 5
                }
            }]
        };
        myChart.setOption(option);
    });
    window.addEventListener('resize', function () {
        myChart.resize();
    })
})();


// 中国地图
(function () {
    var myChart = echarts.init(document.querySelector(".map .chart"));
    $.get('http://localhost:8088/getProviceSales', function (res) {
        var option = {
            title: {
                x: 'center'
            },
            tooltip: {
                trigger: 'item'
            },

            //左侧小导航图标
            visualMap: {
                show: false,
                x: 'left',
                y: 'center',
                splitList: [
                    {start: 0, end: 10000},
                    {start: 10000, end: 50000},
                    {start: 50000, end: 100000},
                    {start: 100000, end: 250000},
                    {start: 250000, end: 300000},
                    {start: 300000, end: 10000000},
                ],
                color: ['#5475f5', '#9feaa5', '#85daef', '#74e2ca', '#e6ac53', '#9fb5ea']
            },

            //配置属性
            series: [{
                name: '销量',
                type: 'map',
                mapType: 'china',
                roam: true,
                label: {
                    normal: {
                        show: true  //省份名称
                    },
                    emphasis: {
                        show: false
                    }
                },
                data: res //数据
            }]
        };
        myChart.setOption(option);
    });
    window.addEventListener('resize', function () {
        myChart.resize();
    })
})();