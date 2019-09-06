<template>
    <el-container style="height: 500px; border: 1px solid #eee">
        <!-- {{$route.query.id}}-->
        <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
            <el-tree  :load="loadNode"   :props="treePops" lazy   @node-click="handleNodeClick">   </el-tree>
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
    export default {
        data() {

            return {
                depId : this.$route.params.depId,
                treePops: {

                    label: 'label',
                    children: 'children',
                    isLeaf: 'leaf'
                }
            }
        },
        computed: {
            key() {
                return this.$route.name !== undefined ? this.$route.name + +new Date() : this.$route + +new Date()
            }
        },
        mounted() {


        },

        methods: {
            handleNodeClick(data, node) {

                if (node.level == 2) {
                    this.$router.push({path: `/main/doc/${this.depId}/module`, query: {id: data.id}});
                }

            },
            loadNode(node, resolve) {
                if (node.level === 0) {

                    this.axios.get('/apimanager/project/list?depId=' + this.depId).then((response) => {
                        var json = response.data;
                        console.log(json);
                        var data = json.data;
                        data.sort(this.utils.compare("projectName"));
                        var result = [];
                        for (var i = 0; i < data.length; i++) {
                            var p = data[i];
                            // console.log("----"+p.projectName);
                            result.push({"id": p.id, "label": p.projectName, "leaf": false});
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
                            result.push({"id": p.id, "label": p.moduleName, "leaf": true});
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