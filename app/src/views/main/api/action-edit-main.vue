<template>

    <div>


    <el-tabs value="t-query">

        <!-- query   -->
        <el-tab-pane class="c-el-table" label="Query" name="t-query">
            <el-row>
                <el-col :span="2">&nbsp;</el-col>
                <el-col :span="8" v-t="'name'"></el-col>
                <el-col :span="2" v-t="'require'" style="text-align: center"></el-col>
                <el-col :span="5" v-t="'description'"></el-col>
                <el-col :span="3" v-t="'mockRule'"></el-col>
                <el-col :span="3" v-t="'sample'"></el-col>
            </el-row>
            <JsonEditor :defValues="queryValue" :nodes="meta.query" :deep="0">
                <template v-slot="sd">

                    <el-col :span="2" style="text-align: center;">
                        <el-checkbox v-model="sd.node.required" true-label="1"></el-checkbox>
                    </el-col>
                    <el-col :span="5">
                        <el-input v-model="sd.node.desc" style="width: 98%"></el-input>
                    </el-col>
                    <el-col :span="3">
                        <el-input v-model="sd.node.rule" style="width: 98%"></el-input>
                    </el-col>
                    <el-col :span="3">
                        <el-input v-model="sd.node.defaultVal" style="width: 98%"></el-input>
                    </el-col>
                </template>
            </JsonEditor>
            <el-button type="primary" plain @click="addParameter(queryValue,meta.query)" v-t="'addParameter'"></el-button>
            <el-button type="danger" plain @click="meta.query=[]" v-t="'clearParameter'"></el-button>
        </el-tab-pane>


        <!-- body -->
        <el-tab-pane  class="c-el-table"  label="Body" name="t-body">

            <el-row>
                <el-col :span="2">&nbsp;</el-col>
                <el-col :span="8" v-t="'name'"></el-col>
                <el-col :span="3" v-t="'type'"></el-col>
                <el-col :span="2" v-t="'require'" style="text-align: center"></el-col>
                <el-col :span="4" v-t="'description'"></el-col>
                <el-col :span="2" v-t="'mockRule'"></el-col>
                <el-col :span="2" v-t="'sample'"></el-col>
            </el-row>

            <JsonEditor ref="editor" :defValues="bodyValue" :nodes="meta.requestBody" :deep="deep" :addChild="addChild">
                <template v-slot="sd">
                    <el-col :span="3">
                      <!--  <select v-model="sd.node.type"@change="changeType(sd.nodes,sd.index)">
                            <option v-for="item in fieldTypes"  :value="item[0]">{{item[1]}}</option>
                        </select>-->
                        <el-select v-model="sd.node.type" @change="changeType(sd.nodes,sd.index)">
                            <el-option v-for="item in fieldTypes" :key="item[0]" :label="item[1]"
                                       :value="item[0]">
                            </el-option>
                        </el-select>
                    </el-col>
                    <el-col :span="2" style="text-align: center;">
                        <el-checkbox v-model="sd.node.required" true-label="1"></el-checkbox>
                    </el-col>
                    <el-col :span="4">
                        <el-input v-model="sd.node.desc" style="width: 98%"></el-input>
                    </el-col>
                    <el-col :span="2">
                        <el-input v-model="sd.node.rule" style="width: 98%"></el-input>
                    </el-col>
                    <el-col :span="2">
                        <el-input v-model="sd.node.defaultVal" style="width: 98%"></el-input>
                    </el-col>
                </template>
            </JsonEditor>

           <!-- <el-button type="primary" plain @click="addParameter(bodyValue,meta.requestBody)" v-t="'addParameter'"></el-button>-->
            <drop-down :name="$t('addParameter')" :data="[bodyValue,meta.requestBody]" :items="items" :click-button="clickButton" :click-item="clickItem"></drop-down>
            <el-button type="danger" plain @click="meta.requestBody=[]" v-t="'clearParameter'"></el-button>
            <el-button plain @click="bodyImportShow(bodyValue,meta.requestBody)" v-t="'import'"></el-button>
            <el-button plain @click="exportParams(bodyValue,meta.requestBody)" v-t="'export'"></el-button>

            <el-dialog :title="$t('import')" :visible.sync="bodyImport.visible" width="500px" :close-on-click-modal="false" :close-on-press-escape="false">
                <el-form >
                    <el-form-item :label="$tt('object name')"  >
                        <el-select
                                v-model="bodyImport.objectName"
                                filterable
                                :loading="bodyImport.loading">
                            <el-option
                                v-for="item in bodyImport.options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>

                        </el-select>
                    </el-form-item>

                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="bodyImportCancel()" v-t="'cancel'"></el-button>
                    <el-button   @click="bodyImportOK()" v-t="'ok'"></el-button>
                </div>
            </el-dialog>

        </el-tab-pane>


        <!-- response success -->
        <el-tab-pane  class="c-el-table"  :label="$t('successResponse')" name="t-success">
            <el-row>
                <el-col :span="2">&nbsp;</el-col>
                <el-col :span="8" v-t="'name'"></el-col>
                <el-col :span="3" v-t="'type'"></el-col>
                <el-col :span="5" v-t="'description'"></el-col>
                <el-col :span="3" v-t="'mockRule'"></el-col>
                <el-col :span="2" v-t="'sample'"></el-col>
            </el-row>
            <JsonEditor :defValues="responseValue" :nodes="meta.response" :deep="deep" :addChild="addChild">
                <template v-slot="sd">
                    <el-col :span="3">
                    <el-select v-model="sd.node.type" @change="changeType(sd.nodes,sd.index)">
                        <el-option v-for="item in fieldTypes" :key="item[0]" :label="item[1]"
                                   :value="item[0]">
                        </el-option>
                    </el-select>
                    </el-col>
                    <el-col :span="5">
                        <el-input v-model="sd.node.desc" style="width: 98%"></el-input>
                    </el-col>
                    <el-col :span="3">
                        <el-input v-model="sd.node.rule" style="width: 98%"></el-input>
                    </el-col>
                    <el-col :span="2">
                        <el-input v-model="sd.node.defaultVal" style="width: 98%"></el-input>
                    </el-col>
                </template>
            </JsonEditor>
            <!--<el-button type="primary" plain @click="addParameter(responseValue,meata.response)" v-t="'addParameter'"></el-button>-->
            <drop-down :name="$t('addParameter')" :data="[responseValue,meta.response]" :items="items" :click-button="clickButton" :click-item="clickItem"></drop-down>
            <el-button type="danger" plain @click="meta.response=[]" v-t="'clearParameter'"></el-button>

        </el-tab-pane>

        <!--response fail -->
        <el-tab-pane  class="c-el-table" :label="$t('failResponse')" name="t-fail">
            <el-row>
                <el-col :span="2">&nbsp;</el-col>
                <el-col :span="8" v-t="'name'"></el-col>
                <el-col :span="3" v-t="'type'"></el-col>
                <el-col :span="5" v-t="'description'"></el-col>
                <el-col :span="3" v-t="'mockRule'"></el-col>
                <el-col :span="2" v-t="'sample'"></el-col>
            </el-row>
            <JsonEditor :defValues="responseFailValue" :nodes="meta.responseFail" :deep="0" :addChild="addChild">
                <template v-slot="sd">
                    <el-col :span="3">
                    <el-select v-model="sd.node.type" @change="changeType(sd.nodes,sd.index)">
                        <el-option v-for="item in fieldTypes" :key="item[0]" :label="item[1]"
                                   :value="item[0]">
                        </el-option>
                    </el-select>
                    </el-col>
                    <el-col :span="5">
                        <el-input v-model="sd.node.desc" style="width: 98%"></el-input>
                    </el-col>
                    <el-col :span="3">
                        <el-input v-model="sd.node.rule" style="width: 98%"></el-input>
                    </el-col>
                    <el-col :span="2">
                        <el-input v-model="sd.node.defaultVal" style="width: 98%"></el-input>
                    </el-col>
                </template>
            </JsonEditor>
            <!--<el-button type="primary" plain @click="addParameter(responseFailValue,meta,responseFail)" v-t="'addParameter'"></el-button>-->
            <drop-down :name="$t('addParameter')" :data="[responseFailValue,meta.responseFail]" :items="items" :click-button="clickButton" :click-item="clickItem"></drop-down>
            <el-button type="danger" plain @click="meta.responseFail=[]" v-t="'clearParameter'"></el-button>
        </el-tab-pane>

        <!-- header   -->
        <el-tab-pane class="c-el-table" label="Header" name="t-header">
            <el-row>
                <el-col :span="2">&nbsp;</el-col>
                <el-col :span="8" v-t="'name'"></el-col>
                <el-col :span="8" v-t="'description'"></el-col>
                <el-col :span="5" v-t="'sample'"></el-col>
            </el-row>
            <JsonEditor :defValues="headerValue" :nodes="meta.requestHeader" :deep="0">
                <template v-slot="sd">
                    <el-col :span="8">
                        <input type="hidden" v-model="sd.node.type">
                        <el-input v-model="sd.node.desc" style="width: 98%"></el-input>
                    </el-col>

                    <el-col :span="5">
                        <el-input v-model="sd.node.defaultVal" style="width: 98%"></el-input>
                    </el-col>
                </template>
            </JsonEditor>
            <el-button type="primary" plain @click="addParameter(headerValue,meta.requestHeader)" v-t="'addParameter'"></el-button>
            <el-button type="danger" plain @click="meta.requestHeader=[]" v-t="'clearParameter'"></el-button>
        </el-tab-pane>

    </el-tabs>
    </div>

</template>


<script>
    import JsonEditor from '@/components/JsonEditor.vue'

    export default {
        name: 'ActionBase',
        components: {
            JsonEditor
        },
        props: {
            action:Object,
            meta:Object,
        },
        data() {
            return {
               items:[ [0,this.$t("normalParam")],[1,this.$t("arrayItemParam")] ],
                form: {},
                value: 'aaa',
                //,['6','array[number]'],['7','array[string]'],['8','array[boolean]'],['9','array[object]']
                fieldTypes:[['0','date'],['1','number'],['2','string'],['3','boolean'],['4','object'],   ['5','array']],

                deep: 0,

                queryValue: {
                    "name": "",
                    "type": "2",
                    "required": "1",
                    "desc": "",
                    "rule": "",
                    "defaultVal": ""

                },

                headerValue: {
                        "name": "",
                        "type": "2",
                        "desc": "",
                        "defaultVal": ""

                     
                },
                bodyValue: {
                        "name": "",
                        "type": "2",
                        "required": "1",
                        "desc": "",
                        "rule": "",
                        "defaultVal": ""
                  
                },

                responseValue: {
                        "name": "",
                        "type": "2",
                        "desc": "",
                        "rule": "",
                        "defaultVal": ""
                    },
                    

                responseFailValue: {
                        "name": "",
                        "type": "2",
                        "desc": "",
                        "rule": "",
                        "defaultVal": ""
                    },

                bodyImport:{
                    visible:false,
                    loading:false,
                    objectName:"",
                    options:[]

                }


            }
        },

        mounted() {


        },
        updated(){
            //console.log( this.meta.requestHeader)
        },


        methods: {
            clickButton(args){

               var params=args[1];
               if(params==null || params.length==0){
                   return true;
               }else{
                   var cat=params[0].cat?params[0].cat:0;
                   this.clickItem(args,[cat]);

                   return false;
                }

            },
            clickItem(args,item){
                var def=args[0];
                var params=args[1];
                var v= this.utils.clone(def);
                v.cat=item[0];
                if(v.cat==1) v.name="array["+params.length+"]";
                params.push(v);

            },
            addParameter(def,sender) {
                sender.push(this.utils.clone(def));

            },
            bodyImportCancel(){ this.bodyImport.visible=false;},
            bodyImportOK(){
               var ops= this.bodyImport.options;
               var find=false;
               for(var i in ops) if(ops[i].value==this.bodyImport.objectName){
                   find=true;
                   break;
               }
               if(!find){
                   this.$alert(this.$t("pleaseSelect"));
                   return;
               }
                this.ajax.get("/apimanager/object/field/findObjectInfoByClassWholeNames",{classWholeNames:this.bodyImport.objectName},(response)=>{

                        var items=response.data.data;
                        items.map(item=>{ item.type=""+item.type, this.meta.requestBody.push(item)});

                })
                this.bodyImport.visible=false;


            },
            bodyImportShow(){
                this.bodyImport.visible=true;
                this.bodyImport.loading=true;
                this.bodyImport.objectName="";
                this.ajax.get("/apimanager/object/field/listObjectNames",(response)=>{
                    this.bodyImport.loading=false;
                    this.bodyImport.options=[];
                    response.data.data.map(item=>{ this.bodyImport.options.push({label:item,value:item})});


                })

            },
           /* bodyImportRemote(query){
                if(query=="") this.bodyImport.options=[];
                else {
                    this.ajax.get("/apimanager/object/field/findObjectDescByClassWholeNames",{ classWholeNames:query},(response)=>{

                    })
                }
            },*/
             exportParams(){
              this.$prompt(this.$t("inputSaveName")).then( ({ value }) =>  {
                   var obj={classWholeName:value};
                   obj.fieldInfoValue=JSON.stringify(  this.meta.requestBody);
                   this.ajax.postJson("/apimanager/object/field/createObj",obj,(response)=>{
                       this.$message(this.$t("SaveSuccess"));
                   });


                 }).catch(()=>{});


            },
            addChild(parent, defValues) {

                if (parent.child == null) parent.child = [];
                if (this.utils.isArrayField(parent.type)) {
                    defValues.name = "array[" + parent.child.length + "]";
                    defValues.cat=1;
                }

               // defValues.id = "nd_id_s_v";
                parent.child.push(defValues);
                //this.$set(defValues,"type", parseInt(defValues.type));

                //this.$forceUpdate();
              /*  this.$nextTick(function () {
                    document.getElementById("nd_id_s_v").scrollIntoView();
                    delete defValues.id;

                });*/


            },
            changeType(nodes, index) {


                var node = nodes[index];
                console.log(node);
                if (!this.utils.isObjectField(node.type) && node.child != null) {
                    node.child = null;

                }
                this.$refs.editor.$forceUpdate();
            }
        }
    };
</script>

<style>


</style>
