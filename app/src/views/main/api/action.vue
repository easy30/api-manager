<template>


    <el-container>
        <el-main>

            <el-tabs value="first" :before-leave="beforeLeave">
                <el-tab-pane :label="$t('apiConfig')" name="first">
                    <action-edit-base ref="base" :action="action" :meta="meta"></action-edit-base>
                    <action-edit-main :action="action" :meta="meta"></action-edit-main>

                </el-tab-pane>
                <el-tab-pane :label="$t('testCase')" name="second">

                </el-tab-pane>

                <el-tab-pane>
                    <span slot="label">
                        <el-button type="primary" @click="save()">{{$t('save')}}</el-button>
                        <el-button type="primary" @click="back()">{{$t('back')}}</el-button>
                    </span>

                </el-tab-pane>


            </el-tabs>


        </el-main>


    </el-container>


</template>

<style>

</style>

<script>

    import ActionEditBase from './action-edit-base.vue';
    import ActionEditMain from './action-edit-main.vue';

    var baseUrl = "/apimanager/action";
    export default {
        components: {ActionEditBase, ActionEditMain},
        data() {
            return {
                id: this.$route.query.id,
                copy:this.$route.query.copy,
                depId: this.$route.params.depId,
                actionFlag:0,
                metaFlag:0,
                action: { projectId:null,moduleId:null,contentType:0,depId:this.$route.params.depId},
                meta: {
                    domains: [],
                    projects: [],
                    modules: [],
                    requestHeader: [],
                    requestBody: [],
                    query:[],
                    response: [],
                    responseFail: []
                },


            }
        },
        mounted() {

            this.edit();

        },
        watch:{
          /*  action(v1,v2) {
               console.log("change",v1,v2)
            }*/
            action:{
                handler:function(val,oldval){

                    console.log("change",val,oldval)

                    if(this.actionFlag==1) this.actionFlag=2;
                    console.log("actionFlag",this.actionFlag);
                },
                deep:true//对象内部的属性监听，也叫深度监听
            },
            meta:{
                handler:function(val,oldval){
                    console.log("change",val,oldval)

                    if(this.metaFlag==1) this.metaFlag=2;
                    console.log("metaFlag",this.metaFlag);
                },
                deep:true//对象内部的属性监听，也叫深度监听
            },
        },

        methods: {



            async edit() {

                var domainUrl = "/apimanager/domain/list?envId=1";
                var projectUrl = "/apimanager/project/list?depId=" + this.depId;
                var responses = await this.ajax.allSync([  this.axios.get(domainUrl),  this.axios.get(projectUrl),  ]);
                var resDomain = responses[0];
                var resProject = responses[1];
                this.meta.domains = resDomain.data.data;
                this.meta.projects = resProject.data.data;

                //-- get action
                var isEdit = this.id;
                if (isEdit) {
                    let url = baseUrl + "/findById?id=" + this.id;
                    let response = await this.ajax.getSync(url);
                    this.action = response.data.data;
                    //var t=parseInt(this.action.type);
                    //if(t>=6 && t<=9) t=5; //array[type]==> array


                    this.meta.requestHeader = JSON.parse(this.action.requestHeadDefinition);
                    //console.log( this.meta.requestHeader)
                    this.meta.requestBody = JSON.parse(this.action.requestDefinition);
                    if(this.action.queryDefinition) {
                        this.meta.query = JSON.parse(this.action.queryDefinition);
                    }
                    this.meta.response =JSON.parse(this.action.responseDefinition);
                    this.meta.responseFail =JSON.parse(this.action.responseFailDefinition);


                    this.fixFieldType(this.meta.requestBody);
                    this.fixFieldType(this.meta.response);
                    this.fixFieldType(this.meta.responseFail);

                    if(this.copy){
                        this.action.actionName+="_"+this.$t("copy");
                        this.action.id=null;
                    }


                } else {

                    if (this.$route.query.projectId) {
                        this.action.projectId = parseInt(this.$route.query.projectId);

                    }
                    if (this.$route.query.moduleId) {

                        this.action.moduleId = parseInt(this.$route.query.moduleId);
                        //-- get projectId
                        let url = "/apimanager/module/findById?id=" + this.action.moduleId;
                        let response = await this.ajax.getSync(url);
                        this.action.projectId = response.data.data.projectId;

                    }



                }


                if (this.$refs.base) this.$refs.base.projectChange(this.action.projectId);
               this.$forceUpdate();
               this.$nextTick(function () {
                   this.actionFlag=1;
                   this.metaFlag=1;
               })



            } ,
            save() {

                if(!this.$refs.base.validate()){
                    this.$message(this.$tt("field validate fail"));
                    return false;
                }

                this.action.requestHeadDefinition = JSON.stringify(this.meta.requestHeader);
                this.action.requestDefinition = JSON.stringify(this.meta.requestBody);
                this.action.queryDefinition = JSON.stringify(this.meta.query);
                this.action.responseDefinition = JSON.stringify(this.meta.response);
                this.action.responseFailDefinition = JSON.stringify(this.meta.responseFail);
                if (this.action.id) {
                    let url = baseUrl + "/update";
                    this.ajax.postJson(url, this.action, () => {
                        this.$message(this.$tt('save success'));
                        this.actionFlag=1;
                        this.metaFlag=1;
                    });


                } else {
                    let url = baseUrl + "/add";
                    this.ajax.postJson(url, this.action, response => {
                        //this.action.id = response.data.data;
                        this.$message(this.$tt('save success'));
                        var actionsPath = `/main/api/${this.depId}/action`;
                        this.$router.replace({
                            path: actionsPath,
                            query: { id : response.data.data,depId:this.depId}
                        });
                        this.actionFlag=1;
                        this.metaFlag=1;

                    });

                }


            },

            // pretend to click last tab
            beforeLeave(activeIndex /*, oldActiveIndex*/) {
                if (activeIndex == 2) {
                    return false
                }
            },
            back(){
                if(this.actionFlag==2 || this.metaFlag==2){
                    if(!confirm(this.$t("changeConfirm"))) {
                        return;
                    }
                }
                this.$router.go(-1);
            },
            fixFieldType(array){
                for(var i=0;i<array.length;i++){
                    var node=array[i];
                    if(node.type>=6 &&node.type<=9) node.type="5";
                    if(node.child) this.fixFieldType(node.child);
                }
            }
        }
    }
</script>

<style>


</style>