<template>
    <div style="">

        <JsonEditor :nodes="nodes" :deep="deep">
            <template v-slot="sd">

                <el-col :span="2"><el-select v-model="sd.node.type"></el-select></el-col>
                <el-col :span="1" style="text-align: center;"><el-checkbox v-model="sd.node.required" true-label="1"></el-checkbox></el-col>
                <el-col :span="5"><el-input v-model="sd.node.desc" style="width: 98%"></el-input></el-col>
                <el-col :span="4"><el-input v-model="sd.node.rule" style="width: 98%"></el-input></el-col>
                <el-col :span="4"><el-input v-model="sd.node.defaultVal" style="width: 98%"></el-input></el-col>

            </template>
        </JsonEditor>
        <button class="btn btn-primary btn-xs " @click="insertField">addField</button>
    </div>
</template>

<script>
    // @ is an alias to /src
    import JsonEditor from '@/components/JsonEditor.vue'

    export default {
        name: 'home',
        methods: {
            insertField: function () {
                this.nodes.push({
                    "name": "coolma",
                    "type": "2",
                    "required": "1",
                    "desc": "所属机构",
                    "rule": "1-6:ab",
                    "defaultVal": ""
                });
            }
        },
        data: function () {

            return {
                deep: 0,
                fields: [{"name": "name", "span": 3}, {"type": "name", "span": 3}],
                nodes: [{
                    "name": "org",
                    "type": "2",
                    "required": "1",
                    "desc": "所属机构",
                    "rule": "1-6:ab",
                    "defaultVal": ""
                }, {
                    "name": "carId",
                    "type": "1",
                    "required": "1",
                    "desc": "设备ID",
                    "rule": "1-100:10",
                    "defaultVal": "0"
                }, {
                    "name": "workDate",
                    "type": "1",
                    "required": "1",
                    "desc": "工作日期：时间戳，只精确到年月日，时分秒都为0",
                    "rule": "1-100:10",
                    "defaultVal": "0"
                }, {
                    "name": "constructionArea",
                    "type": "2",
                    "required": "1",
                    "desc": "施工区域",
                    "rule": "1-6:ab",
                    "defaultVal": ""
                }, {
                    "name": "operatorName",
                    "type": "2",
                    "required": "1",
                    "desc": "设备操作人",
                    "rule": "1-6:ab",
                    "defaultVal": ""
                }, {
                    "name": "engineer",
                    "type": "2",
                    "required": "2",
                    "desc": "现场工程师",
                    "rule": "1-6:ab",
                    "defaultVal": ""
                }, {
                    "name": "manager",
                    "type": "2",
                    "required": "2",
                    "desc": "区域经理",
                    "rule": "1-6:ab",
                    "defaultVal": ""
                }, {
                    "name": "content",
                    "type": "2",
                    "required": "1",
                    "desc": "工作内容",
                    "rule": "1-6:ab",
                    "defaultVal": "url"
                }, {
                    "name": "recordType",
                    "type": "1",
                    "required": "1",
                    "desc": "记录类型：1：工作时长；2：工作量",
                    "rule": "1-100:10",
                    "defaultVal": "0"
                }, {
                    "name": "workload",
                    "type": "1",
                    "required": "2",
                    "desc": "工作量：浮点数；保留一位小数",
                    "rule": "1-100:10",
                    "defaultVal": "0"
                }, {
                    "name": "workloadUnit",
                    "type": "1",
                    "required": "2",
                    "desc": "工作量单位：1：方；2：趟",
                    "rule": "1-100:10",
                    "defaultVal": ""
                }, {
                    "name": "workloadRemark",
                    "type": "2",
                    "required": "2",
                    "desc": "工作量描述",
                    "rule": "1-6:ab",
                    "defaultVal": ""
                }, {
                    "name": "workhours",
                    "type": "9",
                    "required": "2",
                    "desc": "工作时长",
                    "rule": "",
                    "defaultVal": "",
                    "child": [{
                        "name": "array[0]",
                        "type": "4",
                        "required": "2",
                        "desc": "",
                        "rule": "",
                        "defaultVal": "",
                        "child": [{
                            "name": "remark",
                            "type": "2",
                            "required": "2",
                            "desc": "描述",
                            "rule": "1-6:ab",
                            "defaultVal": ""
                        }, {
                            "name": "endTIme",
                            "type": "1",
                            "required": "2",
                            "desc": "时间段，结束时间：eg：12：12",
                            "rule": "1-100:10",
                            "defaultVal": ""
                        }, {
                            "name": "startTime",
                            "type": "2",
                            "required": "2",
                            "desc": "时间段，开始时间：eg：12：12",
                            "rule": "1-6:ab",
                            "defaultVal": ""
                        }]
                    }]
                }, {
                    "name": "shift",
                    "type": "1",
                    "required": "2",
                    "desc": "台班：浮点数：最多保留三位小数",
                    "rule": "1-100:10",
                    "defaultVal": "0"
                }]
            }
        },
        components: {
            JsonEditor
        }
    }

</script>
