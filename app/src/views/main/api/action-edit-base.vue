<template>


                    <el-form :model="action" :rules="rules" ref="baseForm"  :inline="false"   label-width="60px" size="mini">
                        <table border="0" width="100%">
                            <tr>
                                <td>
                                    <el-form-item :label="$t('name')" prop="actionName">
                                        <el-input v-model="action.actionName" ></el-input>
                                    </el-form-item>
                                </td>
                                <td>
                                    <el-form-item :label="$t('project')" prop="projectId">
                                        <el-select v-model="action.projectId" filterable @change="projectChange(action.projectId)">
                                            <el-option :label="$t('pleaseSelect')" value=""></el-option>
                                            <el-option
                                                    v-for="item in meta.projects"
                                                    :key="item.id"
                                                    :label="item.projectName"
                                                    :value="item.id">
                                            </el-option>
                                        </el-select>
                                    </el-form-item>

                                </td>
                                <td>
                                    <el-form-item :label="$t('module')" prop="moduleId">
                                        <el-select v-model="action.moduleId" filterable>
                                            <el-option :label="$t('pleaseSelect')" value=""></el-option>
                                            <el-option
                                                    v-for="item in modules"
                                                    :key="item.id"
                                                    :label="item.moduleName"
                                                    :value="item.id">
                                            </el-option>
                                        </el-select>
                                    </el-form-item>

                                </td>

                            </tr>

                            <tr>
                                <td colspan="4">


                                    <el-form-item :label="$t('url')" required>
                                        <el-select v-model="action.requestType" style="width: 15%">

                                            <el-option label="GET" :value="1"></el-option>
                                            <el-option label="POST" :value="2"></el-option>

                                        </el-select>

                                        <el-select v-model="action.domainId" style="display:none;width: 80%" filterable>
                                            <el-option :label="$t('pleaseSelect')"  value=""></el-option>
                                            <el-option
                                                    v-for="item in meta.domains"
                                                    :key="item.id"
                                                    :label="item.domainName"
                                                    :value="item.id">
                                            </el-option>
                                        </el-select>

                                        <el-form-item  prop="requestUrl" style="display:inline-block;width: 85%">
                                            <el-input v-model="action.requestUrl" ></el-input>
                                        </el-form-item>

                                    </el-form-item>
                                </td>
                            </tr>


                            <tr>
                                <td>
                                    <el-form-item :label="$t('type')"  prop="contentType">
                                    <el-select  v-model="action.contentType"   filterable>
                                        <el-option
                                                v-for="item in contentTypes"
                                                :key="item.value"
                                                :label="item.label"
                                                :value="item.value">
                                        </el-option>
                                    </el-select>
                                    </el-form-item>
                                </td>
                                <td>
                                    <el-form-item :label="$t('status')">
                                        <el-select v-model="action.status" >
                                            <el-option :label="$t('develop')" :value="1"></el-option>
                                            <el-option :label="$t('complete')" :value="2"></el-option>
                                        </el-select>

                                    </el-form-item>
                                </td>
                                <td>
                                    <el-form-item :label="$t('level')">
                                        <el-select v-model="action.actionLevel">
                                            <el-option :label="$t('pleaseSelect')" value=""></el-option>
                                            <el-option label="A" :value="1"></el-option>
                                            <el-option label="B" :value="2"></el-option>
                                            <el-option label="C" :value="3"></el-option>
                                            <el-option label="D" :value="4"></el-option>

                                        </el-select>




                                    </el-form-item>
                                </td>
                            </tr>


                            <tr>
                                <td colspan="4">

                                    <el-form-item :label="$t('description')">
                                        <el-input
                                                type="textarea"
                                                :rows="2"
                                                v-model="action.actionDesc">
                                        </el-input>
                                    </el-form-item>

                                </td>
                            </tr>
                        </table>

                    </el-form>


</template>



<script>
    var rule1= [  {required: true, message: ' ', trigger:"blur"}];
    var  rule2= [  {required: true, message: ' ', trigger:"blur"}];//change
    export default {
        name: 'ActionEditBase',
        components: {},
        props: {
            action:Object,
            meta:Object,

        },
        data() {
            return {
                contentTypes: [
                    {
                        value: 0,
                        label: 'x-www-form-urlencoded'
                    },
                    {
                        value: 1,
                        label: 'application/json'
                    },
                    {
                        value: 2,
                        label: 'form-data'
                    },
                    {
                        value: 3,
                        label: 'text/plain'
                    }

                ],


                value: 'aaa',
                modules:[],

                rules: {
                    actionName:rule1,
                    requestUrl:rule1,
                    projectId:rule2,
                    moduleId:rule2,
                    contentType:rule2
                },
                options: []


            }
        },

        mounted() {

        },
        updated: function () {
           /* if(!this.up && this.action.projectId){
                this.up=true;
                this.projectChange(this.action.projectId);

            }*/

        },


        methods: {
            validate(){
                let result;
                this.$refs['baseForm'].validate((valid) => {
                    result= valid;

                });
                return result
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        alert('submit!');
                    } else {
                        //alert('error submit!!');
                        return false;
                    }
                });
            },
            projectChange(projectId){
                console.log(projectId)
                if(!projectId) return;

                var moduleUrl="/apimanager/module/list?projectId="+projectId;
                this.ajax.get(moduleUrl,response=> {
                    this.modules=response.data.data;
                    var find=false;

                    for(var i in this.modules){

                        if(this.modules[i].id==this.action.moduleId){
                            find=true;
                        }
                    }
                    if(!find) this.action.moduleId=null;
                    //this.$forceUpdate();
                });

            }
        }
    };
</script>

<style>


</style>