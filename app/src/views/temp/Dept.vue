<template>

    <el-container style="height: 500px; border: 1px solid #eee">
        <!-- {{$route.query.id}}-->
        <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
            <el-tree
                    :load="loadNode1"

                    lazy
                    @node-click="handleNodeClick">
            </el-tree>

        </el-aside>

        <router-view :key="key"></router-view>



    </el-container>


</template>

<style>
    .el-header {
        background-color: #B3C0D1;
        color: #333;
        line-height: 60px;
    }

    .el-aside {
        color: #333;
    }
</style>

<script>
    export default {
        data() {
            const item = {
                date: '2016-05-02',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1518 弄'
            };
            return {
                tableData: Array(20).fill(item),


                /* props1: {
                     label: 'label',
                     children: 'children',
                     isLeaf: 'isLeaf'
                 },*/

            }
        },
        computed: {
            key() {
                return this.$route.name !== undefined? this.$route.name + +new Date(): this.$route + +new Date()
            }
        },
        mounted() {

            /* var depId = this.$route.query.id;

             this.axios.get('/apimanager/project/list?depId=' + depId).then((response) => {
                 var json = response.data;
                 //console.log(json);
                 var data = json.data;
                 for (var i = 0; i < data.length; i++) {
                     var p = data[i];
                     console.log(p.projectName);
                     this.treeData.push({"id": p.id, "label": p.projectName, "isLeaf": false});
                 }


                 //console.log("res="+response.data.data.pageIndex);
                 //this.info=response.data;

             }).catch((response) => {
                 console.log(response)
             })*/

        },

        methods: {
            handleNodeClick(data, node) {
                console.log(node);
                if (node.level == 2){
                    this.$router.push({path: '/main/dept/doc', query: {id: data.id}});
                }
                /* data.children=[{"id":1111,"label":"11111","isLeaf":true}];
                 var id=data.id;
                 for(var i=0;i<this.treeData.length;i++){
                     var p=this.treeData[i];
                     //console.log(p);
                     if(p.id==id){
                         p.children=[{"id":1111,"label":"11111","isLeaf":true}];
                         tree.load(node,resolve)
                         console.log(this.treeData);
                         break;
                     }
                 }*/
            },
            loadNode1(node, resolve) {
                if (node.level === 0) {
                    var depId = this.$route.query.id;

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

                /* setTimeout(() => {
                     const data = [{
                         label: 'leaf',
                         isLeaf: true
                     }, {
                         label: 'zone'
                     }];

                     resolve(data);
                 }, 500);*/
            }
        }
    };
</script>