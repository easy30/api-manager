<template>


    <el-container>
        <el-main>


            <!-- <el-table :data="actions"
                     stripe
                     style="width: 99.9%">
                 <el-table-column prop="id" :label="$t('id')"></el-table-column>
                 <el-table-column prop="actionName" :label="$t('apiName')"></el-table-column>
                 <el-table-column prop="moduleId" :label="$t('module')"></el-table-column>
                 <el-table-column prop="createUserName" :label="$t('creator')"></el-table-column>
                 <el-table-column prop="updateUserName" :label="$t('editor')"></el-table-column>
                 <el-table-column prop="updateTime" :label="$t('lastmodified')"></el-table-column>

             </el-table>-->
            <el-row>
                <el-col :span="24" class="c-margin">
                    <Page :data="data" ></Page>
                </el-col>
            </el-row>
            <table class="c-table">
                <tbody>
                <tr>
                    <th v-t="'id'"></th>
                    <th v-t="'apiName'"></th>
                    <th v-t="'module'"></th>
                    <th v-t="'creator'"></th>
                    <th v-t="'editor'"></th>
                    <th v-t="'lastmodified'"></th>
                    <th v-t="'operation'"></th>

                </tr>
                <template v-for="(item) in actions">
                    <tr>
                        <td>{{item.id}}</td>
                        <td>{{item.actionName}}</td>
                        <td>{{item.moduleId}}</td>
                        <td>{{item.createUserName}}</td>
                        <td>{{item.updateUserName}}</td>
                        <td>{{new Date(item.updateTime).toLocaleString()}}</td>
                        <td>
                            <el-button v-t="'edit'" type="primary" @click="edit(item)"></el-button>
                            <el-button v-t="'copy'" type="primary" @click="copy(item)"></el-button>
                            <el-button v-t="'delete'" type="danger" @click="actionDelete(item)"></el-button>
                        </td>

                    </tr>
                </template>
                </tbody>
            </table>
            <el-row class="c-margin">
                <el-col :span="20" >
                    <Page :data="data" ></Page>
                </el-col>
                <el-col :span="4">
                    <el-button v-t="'add'" @click="add()"></el-button>

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
                depId : this.utils.notNull(this.$route.params.depId),
                data: {},
                actions: []
            }
        },
        mounted() {

            console.log(this.$route.path);

            var projectId = this.utils.notNull(this.$route.query.projectId);
            var moduleId = this.utils.notNull(this.$route.query.moduleId);
            var pageIndex = this.utils.notNull(this.$route.query.pageIndex, 1);
            var pageSize = this.utils.notNull(this.$route.query.pageSize, 20);
            //pageIndex=1&pageSize=8&depId=&projectId=&moduleId=170&createUser=
            var url = `/apimanager/action/findPage?pageIndex=${pageIndex}&pageSize=${pageSize}&depId=${this.depId}&projectId=${projectId}&moduleId=${moduleId}`;


            this.ajax.get(url,(response) => {

                var json = response.data;
                this.data = json.data;
                this.actions = this.data.datas;
                this.$forceUpdate();


            })

        },

        methods: {

            add(){

                var actionsPath = `/main/api/${this.depId}/action`;
                var q={};
                if(this.$route.query.projectId) q.projectId=this.$route.query.projectId;
                if(this.$route.query.moduleId) q.moduleId=this.$route.query.moduleId;

                this.$router.push({
                    path: actionsPath,
                    query: q
                });

            },

            edit(action) {
                var actionsPath = `/main/api/${this.depId}/action`;

                this.$router.push({
                    path: actionsPath,
                    query: { id :action.id,depId:this.depId}
                });

            },
            copy(action){
                var actionsPath = `/main/api/${this.depId}/action`;

                this.$router.push({
                    path: actionsPath,
                    query: { copy:true,id:action.id,depId:this.depId}
                });
            },
            actionDelete(action){
                if(!confirm(this.$t("deleteConfirm"))) return;
                var url = "/apimanager/action/delete";
                this.ajax.postForm(url,{id:action.id},response=>{
                    this.$router.go(0);
                });
            },
          /*  convertHttpMethod(type) {
                if (type == 1) return "GET";
                if (type == 2) return "POST";
            },
            convertParams(params) {

                var obj = JSON.parse(params);
                var i = 1;
                var result = [];
                var self = this;
                this.utils.treeNodeEach(obj, "child", function (node, pnode, deep) {
                    node.id = i++;
                    node.type = self.convertParamType(node.type);
                    node.required = self.convertParamYesNo(node.required);
                    node.offset = 2 + deep * 20;
                    result.push(node)
                });
                //console.log("ok2")
                return result;
            },

            convertParamType(value) {
                return ["date", "number", "string", "boolean", "object", "array", "array[number]", "array[string]", "array[boolean]", "array[object]"][value];
            },
            convertParamYesNo(value) {
                return ["否", "是"][value];
            },
            urlClick(index) {
                //var action=this.actions[index];
                document.getElementById("action_" + index).scrollIntoView();
                return false;
            }*/
        }
    };
</script>

<style>
    /* .table-bordered {
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

     .table th, .table td {
         border: solid #dee2e6;
         border-width: 0 1px 1px 0;
         padding: 2px;
     }*/

</style>