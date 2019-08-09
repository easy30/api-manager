<template>
    <el-container style="height: 500px; border: 1px solid #eee">
        <span>api.vue</span>
        <!-- {{$route.query.id}}-->
        <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
            <el-tree  :load="loadNode"  lazy   @node-click="handleNodeClick">   </el-tree>
        </el-aside>
        <router-view :key="key"></router-view>
    </el-container>
</template>

<style>
    .el-aside {
        color: #333;
    }
</style>

<script>

    var actionsPath;
    export default {
        data() {

            return {}
        },
        computed: {
            key() {
                return this.$route.name !== undefined ? this.$route.name + +new Date() : this.$route + +new Date()
            }
        },
        mounted() {
            var deptId=this.$route.params.deptId;
            //console.log(this.$route);
            actionsPath=`/main/api/${deptId}/actions`;

            this.$router.push({path: actionsPath, query: this.utils.clone(this.$route.query)});

        },

        methods: {
            handleNodeClick(data, node) {
                //http://apimanager.tiebaobei.com/apimanager/action/findPage?pageIndex=1&pageSize=8&depId=&projectId=&moduleId=170&createUser=
                console.log(node);
                if(node.level==1){
                    this.$router.push({path: actionsPath, query: {projectId: data.id}});
                }
                if (node.level == 2) {
                    this.$router.push({path: actionsPath, query: {moduleId: data.id}});
                }

            },
            loadNode(node, resolve) {
                if (node.level === 0) {
                    var depId = this.$route.params.deptId;

                    this.axios.get('/apimanager/project/list?depId=' + depId).then((response) => {
                        var json = response.data;
                        //console.log(json);
                        var data = json.data;
                        var result = [];
                        for (var i = 0; i < data.length; i++) {
                            var p = data[i];
                            // console.log("----"+p.projectName);
                            result.push({"id": p.id, "label": p.projectName, "isLeaf": false});
                        }
                        return resolve(result);


                        //console.log("res="+response.data.data.pageIndex);
                        //this.info=response.data;

                    }).catch((response) => {
                        console.log(response)
                    })

                }
                if (node.level == 1) {
                    this.axios.get('/apimanager/module/list?projectId=' + node.data.id).then((response) => {
                        var json = response.data;
                        //console.log("node.id=" + node.id);
                        //console.log(json);
                        var data = json.data;
                        var result = [];
                        for (var i = 0; i < data.length; i++) {
                            var p = data[i];
                            //console.log("moduleName:" + p.moduleName);
                            result.push({"id": p.id, "label": p.moduleName, "isLeaf": true});
                        }
                        return resolve(result);


                        //console.log("res="+response.data.data.pageIndex);
                        //this.info=response.data;

                    }).catch((response) => {
                        console.log(response)
                    })


                }

                if (node.level > 1) return resolve([]);


            }
        }
    };
</script>