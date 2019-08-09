<template>


    <el-container>
        <el-main>
            <el-row v-for="item in actions">
                <el-col :span="24" style="text-align: left">
                    <div>
                        <h2>{{item.actionName}}</h2>
                        <p>{{item.actionDesc}}</p>
                        <p>
                            <span>{{convertHttpMethod(item.requestType)}}</span>
                            <span style="margin-left: 10px">{{item.requestUrl}}</span>
                        </p>

                        <h3>请求参数</h3>
                        <el-table
                                :data="convertParams(item.requestDefinition)"
                                style="width: 100%;margin-bottom: 20px;"
                                row-key="id"
                                border
                                default-expand-all
                                :tree-props="{children: 'child', hasChildren: 'hasChildren'}">
                            <el-table-column
                                    min-width="300px"
                                    prop="name"
                                    label="名称" >
                            </el-table-column>
                            <el-table-column
                                    prop="type"
                                    label="类型"
                                    :formatter="convertParamType">
                            </el-table-column>
                            <el-table-column
                                    prop="required"
                                    label="必填" :formatter="convertParamYesNo">
                            </el-table-column>
                            <el-table-column
                                    prop="desc"
                                    label="说明">
                            </el-table-column>
                            <el-table-column
                                    prop="rule"
                                    label="Mock规则">
                            </el-table-column>
                            <el-table-column
                                    prop="defaultVal"
                                    label="样例">
                            </el-table-column>
                        </el-table>

                        <h3>成功返回</h3>
                        <el-table
                                :data="convertParams(item.responseDefinition)"
                                style="width: 100%;margin-bottom: 20px;"
                                row-key="id"

                                border
                                default-expand-all
                                :tree-props="{children: 'child', hasChildren: 'hasChildren'}">
                            <el-table-column
                                    prop="name"
                                    label="名称" >
                            </el-table-column>
                            <el-table-column
                                    prop="type"
                                    label="类型"
                                    :formatter="convertParamType">
                            </el-table-column>

                            <el-table-column
                                    prop="desc"
                                    label="说明">
                            </el-table-column>
                            <el-table-column
                                    prop="rule"
                                    label="Mock规则">
                            </el-table-column>
                            <el-table-column
                                    prop="defaultVal"
                                    label="样例">
                            </el-table-column>
                        </el-table>

                        <h3>失败返回</h3>
                        <el-table
                                :data="convertParams(item.responseFailDefinition)"
                                style="width: 100%;margin-bottom: 20px;"
                                row-key="id"
                                border
                                default-expand-all
                                :tree-props="{children: 'child', hasChildren: 'hasChildren'}">
                            <el-table-column
                                    prop="name"
                                    label="名称" >
                            </el-table-column>
                            <el-table-column
                                    prop="type"
                                    label="类型"
                                    :formatter="convertParamType">
                            </el-table-column>

                            <el-table-column
                                    prop="desc"
                                    label="说明">
                            </el-table-column>
                            <el-table-column
                                    prop="rule"
                                    label="Mock规则">
                            </el-table-column>
                            <el-table-column
                                    prop="defaultVal"
                                    label="样例">
                            </el-table-column>
                        </el-table>
                        <!--<el-row>
                            <el-col :span="2">
                                <div>{{convertHttpMethod(item.requestType)}}</div>
                            </el-col>
                            <el-col :span="22">
                                <div>{{item.requestUrl}}</div>
                            </el-col>
                        </el-row>-->
                    </div>
                </el-col>

            </el-row>
        </el-main>
    </el-container>


</template>

<style>

</style>

<script>
    export default {
        data() {
            return {
                actions: []
            }
        },
        mounted() {

            var moduleId = this.$route.query.id;

            this.axios.get('/apimanager/action/list?moduleId=' + moduleId).then((response) => {
                var json = response.data;

                this.actions = json.data;
                console.log("ok")



            }).catch((response) => {
                console.log(response)
            })

        },

        methods: {
            convertHttpMethod(type) {
                if (type == 1) return "GET";
                if (type == 2) return "POST";
            },
            convertParams(params){

                var obj= JSON.parse(params);
                var i=1;
                this.utils.treeNodeEach(obj,"child",function (node) {
                    node.id=i++;
                });
                console.log("ok2")
                return obj;
            },

            convertParamType(row, column, cellValue, index){
                return ["date","number","string","boolean","object","array","array[number]","array[string]","array[boolean]","array[object]"][cellValue];
            },
            convertParamYesNo (row, column, cellValue, index){
                return ["否","是"][cellValue];
            }
        }
    };
</script>