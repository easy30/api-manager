<template>

        <!-- Form -->

        <el-dialog :title="$t('project')" :visible.sync=" visible" width="600px" :close-on-click-modal="false" :close-on-press-escape="false">
            <el-form  label-width="100px">
                <el-form-item :label="$tt('project name')"  >
                    <el-input class="el-input--big" v-model="row.projectName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item :label="$tt('project desc')"  >
                    <el-input class="el-input--big"  v-model="row.projectDesc" autocomplete="off"></el-input>
                </el-form-item>

                <el-form-item :label="$tt('server address')"  >
                    <el-form-item v-for="item in domains " :label="item.envName"  :key="item.envId">
                        <el-input class="el-input--big c-mb-5"   v-model="item.domainName" autocomplete="off"></el-input>
                    </el-form-item>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancel()" v-t="'cancel'"></el-button>
                <el-button   @click="save()" v-t="'save'"></el-button>
            </div>


        </el-dialog>


</template>

<style>
	.el-aside {
		color: #333;
	}
</style>

<script>



	var baseUrl="/apimanager/project";
	var labelName="projectName";
	var idPrefix="p";
	var leaf=false;

	export default {
		data() {

			return {

                row:{},

                visible:false,

                treeCurrentNodeData:null,

                envs:[],
                domains:[]

			}
		},
		computed: {

		},
		mounted() {
            this.ajax.get("/apimanager/env/list", response=>{
                this.envs=response.data.data;
            });


		},
        props: {

        },

		methods: {

		    resetDomains(){
                this.domains=[];
                this.envs.map(item=>{
                    this.domains.push({ envId:item.id, envName:item.envName, domainName:""});
                });

            },

            add() {

                this.row = {   depId: this.$parent.$parent.depId,  }
                this.resetDomains();
                this.visible = true;
            },

            edit(node, data) {

                this.treeCurrentNodeData = data;
                this.row = data.row;
                this.resetDomains();
                let url="/apimanager/domain/list?projectId="+this.row.id;
                this.ajax.get(url,r=>{
                    let domains2=r.data.data;
                    for(var i=0;i<this.domains.length;i++){
                        for(var j=0;j<domains2.length;j++){
                            if(this.domains[i].envId==domains2[j].envId){
                                let en=this.domains[i].envName;
                                this.$set(this.domains,i,domains2[j]);
                                //this.domains[i].domainName=domains2[j].domainName;
                                this.domains[i].envName=en;
                                break;
                            }
                        }
                    }
                    this.visible = true;

                });

            },
            delete(node, data) {
                var parent=this.$parent.$parent;
                if (confirm(this.$t("deleteConfirm"))) {
                    this.ajax.postForm(baseUrl + "/delete", {id: data.row.id},
                        function (response) {
                            parent.$refs.tree.remove(node);
                            parent.$forceUpdate();
                        });

                }
            },
            async save() {
                var parent = this.$parent.$parent;
                var current = this.row;
                var update = current.id;
                var url = baseUrl + (update ? "/update" : "/add");
                var self = this;

                var response = await this.ajax.postFormSync(url, current);
                if (!response.success) return;

                if (update) {
                    self.treeCurrentNodeData.label = current[labelName];
                    parent.$forceUpdate();

                } else {
                    current.id = response.data.data;

                    parent.$refs.tree.append({
                        "id": idPrefix + current.id,
                        "label": current[labelName],
                        "row": current,
                        "leaf": leaf
                    });//,this.$refs.tree.root);
                }


                var items=[];
                this.domains.map(domain=>{

                    domain.projectId=current.id;
                    let url="/apimanager/domain/"+(domain.id?"update":"add");
                    items.push(this.ajax.postFormItem(url,domain));

                });

                await  this.ajax.allSync(items);


                    self.visible = false;
                    //self.$forceUpdate();

            },
            cancel() {
                this.visible = false;

            }
        }


	};
</script>
