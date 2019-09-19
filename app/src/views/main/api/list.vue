<template>


    <el-container>
        <el-main style="padding: 0px 0px 0px 10px;">


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
                    <el-form :inline="true" >
                        <el-form-item label="" style="margin-bottom:0px">
                            <el-input v-model="keywords" style="width:400px" @keyup.enter.native="search()"></el-input>
                        </el-form-item>

                        <el-form-item style="margin-bottom:0px ">
                            <el-button type="primary" @click="search()" v-t="'search'"></el-button>
                        </el-form-item>
                    </el-form>

                </el-col>
            </el-row>
            <el-row class="c-margin">
                <el-col :span="20" >
                    <Page :data="data" ></Page>
                </el-col>
                <el-col :span="4">
                    <el-button type="primary" v-t="'addApi'" @click="add()"></el-button>

                </el-col>
            </el-row>
            <table class="c-table">
                <tbody>
                <tr>
                    <th v-t="'id'"></th>
                    <th v-t="'apiName'"></th>
                    <th v-t="'module'"></th>
                  <!--  <th v-t="'creator'"></th>-->
                    <th v-t="'editor'"></th>
                    <th v-t="'lastmodified'"></th>
                    <th v-t="'operation'"></th>

                </tr>
                <template v-for="(item) in actions">
                    <tr :key="item.id">
                        <td>{{item.id}}</td>
                        <td><el-link  @click="edit(item)">{{item.actionName}}</el-link></td>
                        <td>{{item.moduleName}}</td>
                      <!--  <td>{{item.createUserName}}</td>-->
                        <td>{{item.updateUserName}}</td>
                        <td :title="new Date(item.updateTime).toLocaleString()">{{new Date(item.updateTime).toLocaleDateString()}}</td>
                        <td>
                            <el-button style="margin-right: 10px" v-t="'edit'" type="primary" @click="edit(item)"></el-button>
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
                    <el-button type="primary" v-t="'addApi'" @click="add()"></el-button>

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
                keywords:this.utils.notNull(this.$route.query.keywords),
                r:this.utils.notNull(this.$route.query.r,0),
                data: {},
                actions: [],

            }
        },
        mounted() {

            console.log(this.$route.path);

            var projectId = this.utils.notNull(this.$route.query.projectId);
            var moduleId = this.utils.notNull(this.$route.query.moduleId);
            var pageIndex = this.utils.notNull(this.$route.query.pageIndex, 1);
            var pageSize = this.utils.notNull(this.$route.query.pageSize, 20);
            //pageIndex=1&pageSize=8&depId=&projectId=&moduleId=170&createUser=
            var url = `/apimanager/action/search?pageIndex=${pageIndex}&pageSize=${pageSize}&depId=${this.depId}&projectId=${projectId}&moduleId=${moduleId}&keywords=${this.keywords}`;


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
                this.ajax.postForm(url,{id:action.id},()=>{
                    this.$router.go(0);
                });
            },
            search(){
                var q = this.utils.clone(this.$route.query);
                if (q == null) q = {};
                q.pageIndex = 1;
                q.pageSize = this.data.pageSize;
                q.keywords=this.keywords;
                this.$routerPush({path: this.$route.path, query: q});

            }
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

<style scoped>
    .el-button+.el-button {
        margin: 2px 10px 0px 0px;
    }

</style>