<template>


    <el-container>
        <el-main>

            <div id="action_-1"></div>

            <table style="text-align: left">
                <tbody>
                <template  v-for="(item,index) in actions">
                    <tr>
                        <td style="text-decoration: underline;cursor: pointer"  @click="urlClick(index)">{{item.id}}. {{item.actionName}}
                        </td>
                    </tr>
                </template>
                </tbody>
            </table>
            <el-row v-for="(item,index) in actions">
                <el-col :span="24" style="text-align: left;margin-bottom: 20px">
                    <div>
                        <h2 :id="'action_'+index" style="display: inline-block;font-size: 30px">{{item.id}}. {{item.actionName}}</h2>
                        <span style="border:1px solid;cursor: pointer;margin-left: 10px" @click="urlClick(-1)">回到顶部</span>
                        <div>{{item.actionDesc}}</div>
                        <p>
                            <span>{{convertHttpMethod(item.requestType)}}</span>
                            <span style="margin-left: 10px">{{item.requestUrl}}</span>
                        </p>

                        <h3>请求参数1</h3>
                        <table class="table table-bordered">
                            <tr><td>名称</td><td>类型</td><td>必填</td><td>说明</td><td>Mock规则</td><td>样例</td></tr>



                         <tr v-for="node in convertParams(item.requestDefinition)">

                                <td :style="{ paddingLeft: node.offset + 'px' }" >{{node.name}}</td>
                                <td>{{node.type}}</td>
                                <td>{{node.required}}</td>
                                <td>{{node.desc}}</td>
                                <td>{{node.rule}}</td>
                                <td>{{node.defaultVal}}</td>

                        </tr>
                        </table>


                        <h3>成功返回</h3>
                        <table class="table table-bordered">
                            <tr><td>名称</td><td>类型</td><td>说明</td><td>Mock规则</td><td>样例</td></tr>
                            <tr v-for="node in convertParams(item.responseDefinition)">

                                <td :style="{ paddingLeft: node.offset + 'px' }" >{{node.name}}</td>
                                <td>{{node.type}}</td>
                                <td>{{node.desc}}</td>
                                <td>{{node.rule}}</td>
                                <td>{{node.defaultVal}}</td>

                            </tr>
                        </table>




                        <h3>失败返回</h3>
                            <table class="table table-bordered">
                                <tr><td>名称</td><td>类型</td><td>说明</td><td>Mock规则</td><td>样例</td></tr>
                                <tr v-for="node in convertParams(item.responseFailDefinition)">

                                    <td :style="{ paddingLeft: node.offset + 'px' }" >{{node.name}}</td>
                                    <td>{{node.type}}</td>
                                    <td>{{node.desc}}</td>
                                    <td>{{node.rule}}</td>
                                    <td>{{node.defaultVal}}</td>

                                </tr>
                            </table>



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
        components: {},
        data() {
            return {
                deep:0,
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
                var result=[];
                var self=this;
                this.utils.treeNodeEach(obj,"child",function (node,pnode,deep) {
                    node.id=i++;
                    node.type=self.convertParamType(node.type);
                    node.required=self.convertParamYesNo(node.required);
                    node.offset=2+deep*20;
                    result.push(node)
                });
                //console.log("ok2")
                return result;
            },

            convertParamType(value){
                return ["date","number","string","boolean","object","array","array[number]","array[string]","array[boolean]","array[object]"][value];
            },
            convertParamYesNo ( value){
                return ["否","是"][value];
            },
            urlClick(index){
                //var action=this.actions[index];
                document.getElementById("action_"+index).scrollIntoView();
                return false;
            }
        }
    };
</script>

<style>
    .table-bordered {
        border: 1px solid #dee2e6;
    }

    .table {
        width: 100%;
        margin-bottom: 1rem;
        background-color: transparent;
    }
    table {
        border-collapse: collapse;
    }
    .table th,.table td {border:solid #dee2e6;border-width:0 1px 1px 0;padding:2px;}

</style>