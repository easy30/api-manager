<template>
	<el-container style="height: 500px; border: 1px solid #eee">


		<!-- {{$route.query.id}}-->
		<el-aside width="220px" style="background-color: rgb(238, 241, 246)">

            <!-- add project-->
            <el-row class="c-m-5"><el-col :span="24" >
                <el-button icon="el-icon-plus" :title="$tt('add project')"
                           @click="projectAdd()" style="float: right">{{$tt('add project')}}</el-button>

            </el-col> </el-row>

            <!-- tree view -->
			<el-tree ref="tree"  :load="treeNodeLoad" :props="treePops" lazy @node-click="treeNodeClick" node-key="id">
				<span class="custom-tree-node" slot-scope="{ node, data }"  >
					<div v-if="node.level === 1">
						<span style="width:140px;display: inline-block; white-space: nowrap; overflow: hidden;text-overflow:ellipsis"
                             :title="node.label">{{ node.label }}</span>
						<span class="c-ml-5">
							<el-dropdown trigger="click"  @command="projectCommand">
								<span class="el-dropdown-link"  @click.stop=""> <i class="el-icon-edit-outline"></i> </span>

								<el-dropdown-menu slot="dropdown">
									<el-dropdown-item icon="el-icon-edit-outline" :command="[1,node,data]">{{$tt("edit project")}}</el-dropdown-item>
									<el-dropdown-item icon="el-icon-delete" :command="[2,node,data]">{{$tt("delete project")}}</el-dropdown-item>
                                    <el-dropdown-item icon="el-icon-plus" divided :command="[3,node,data]">{{$tt("add module")}}</el-dropdown-item>

								</el-dropdown-menu>
							</el-dropdown>
						</span>
					</div>
					
					<div v-if="node.level === 2">
						<span style="width:120px;display: inline-block; white-space: nowrap; overflow: hidden;text-overflow:ellipsis"
                              :title="node.label">{{ node.label }}</span>
						<span class="c-ml-5">
							<el-dropdown trigger="click"  @command="moduleCommand">
								<span class="el-dropdown-link"  @click.stop=""> <i class="el-icon-edit"></i> </span>

								<el-dropdown-menu slot="dropdown">
									<el-dropdown-item icon="el-icon-edit" :command="[1,node,data]">{{$tt("edit module")}}</el-dropdown-item>
									<el-dropdown-item icon="el-icon-delete" :command="[2,node,data]">{{$tt("delete module")}}</el-dropdown-item>


								</el-dropdown-menu>
							</el-dropdown>
						</span>
					</div>
						
				</span>
			</el-tree>
		</el-aside>


        <!-- Form -->


        <el-dialog :title="$t('project')" :visible.sync=" project.visible" width="400px" :close-on-click-modal="false" :close-on-press-escape="false">
            <el-form >
                <el-form-item :label="$tt('project name')"  >
                    <el-input class="el-big" v-model="project.current.projectName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item :label="$tt('project desc')"  >
                    <el-input class="el-big"  v-model="project.current.projectDesc" autocomplete="off"></el-input>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="projectCancel()" v-t="'cancel'"></el-button>
                <el-button   @click="projectSave()" v-t="'save'"></el-button>
            </div>
        </el-dialog>

        <el-dialog :title="$t('module')" :visible.sync="module.visible" width="400px" :close-on-click-modal="false" :close-on-press-escape="false">
            <el-form >
                <el-form-item :label="$tt('module name')"  >
                    <el-input class="el-big" v-model="module.current.moduleName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item :label="$tt('module desc')"  >
                    <el-input class="el-big"  v-model="module.current.moduleDesc" autocomplete="off"></el-input>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="moduleCancel()" v-t="'cancel'"></el-button>
                <el-button   @click="moduleSave()" v-t="'save'"></el-button>
            </div>
        </el-dialog>


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
	var projectUrl="/apimanager/project";
	var moduleUrl="/apimanager/module";
	export default {
		data() {


			return {
                depId:this.$route.params.depId,

                project: {
                    visible:false,
                    current:{

                        projectName:"",
                        projectDesc:""

                    }

                },

                module: {
                    visible:false,
                    current:{
                        moduleName:"",
                        moduleDesc:""

                    }

                },
                



                treeCurrentNodeData:null,
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

			actionsPath = `/main/api/${this.depId}/actions`;

			this.$router.push({
				path: actionsPath,
				query: this.utils.clone(this.$route.query)
			});

		},

		methods: {

           projectAdd(){

               this.project.current={
                   depId:this.depId,
                   projectName:"",
                   projectDesc:""

               }
               this.project.visible=true;
           },
            projectCommand(args){
               if(args[0]==1){
                   this.projectEdit(args[1],args[2]);
               }else  if(args[0]==2){
                   this.projectDelete(args[1],args[2]);
               }else  if(args[0]==3){
                   this.moduleAdd(args[1],args[2]);
               }
            },
            projectEdit(node,data){
               console.log("edit:"+data)
                this.treeCurrentNodeData=data;
                this.project.current=data.row ;
                this.project.visible=true;
            },
            projectDelete(node,data){
                var self=this;
                if (confirm(this.$t("deleteConfirm"))) {
                    this.axios.$post(projectUrl + "/delete", { id: data.row.id },
                        function (response) {
                            self.$refs.tree.remove(node);
                            self.$forceUpdate();
                        } ) ;

                }
            },
            projectSave(){
                var current=this.project.current;
                var  update=current.id;
                var url = projectUrl + (update ? "/update" : "/add");
                var self=this;

                this.axios.$post(url,current, function(response){

                    if(update){
                        self.treeCurrentNodeData.label=current.projectName;
                        self.$forceUpdate();

                    }else{
                        current.id=response.data.data;
                        console.log(current);
                        self.$refs.tree.append({
                            "id": "p"+current.id,
                            "label": current.projectName,
                            "row":current,
                            "leaf": false
                        });//,this.$refs.tree.root);
                    }

                    self.project.visible=false;
                    //self.$forceUpdate();
                });
            },
            projectCancel(){
                this.project.visible=false;

            },

			//-- do with module
            moduleAdd(node,data){

                this.module.current={
                    projectId:data.row.id,
                    moduleName:"",
                    moduleDesc:""

                }
                this.module.visible=true;
            },
            moduleCommand(args){
                if(args[0]==1){
                    this.moduleEdit(args[1],args[2]);
                }else  if(args[0]==2){
                    this.moduleDelete(args[1],args[2]);
                }
            },
            moduleEdit(node,data){
                console.log(node)
                this.treeCurrentNodeData=data;
                this.module.current=data.row ;
                this.module.visible=true;
            },
            moduleDelete(node,data){
                var self=this;
                if (confirm(this.$t("deleteConfirm"))) {
                    this.axios.$post(moduleUrl + "/delete", { id: data.row.id },
                        function (response) {
                            self.$refs.tree.remove(node);
                            self.$forceUpdate();
                        } ) ;

                }
            },
            moduleSave(){
                var current=this.module.current;
                var  update=current.id;
                var url = moduleUrl + (update ? "/update" : "/add");
                var self=this;

                this.axios.$post(url,current, function(response){

                    if(update){
                        self.treeCurrentNodeData.label=current.moduleName;
                        self.$forceUpdate();

                    }else{
                        current.id=response.data.data;
                        console.log(current);
                        self.$refs.tree.append({
                            "id": "m"+current.id,
                            "label": current.moduleName,
                            "row":current,
                            "leaf": true
                        },"p"+current.projectId);
                    }

                    self.module.visible=false;
                });
            },
            moduleCancel(){
                this.module.visible=false;

            },


           treeNodeEnter($event){
                $event.currentTarget.querySelector('.el-dropdown-link').style.display='block'

            },
            treeNodeLeave($event){
                $event.currentTarget.querySelector('.el-dropdown-link').style.display='none'
            },
			treeNodeClick(data, node) {
				//http://apimanager.tiebaobei.com/apimanager/action/findPage?pageIndex=1&pageSize=8&depId=&projectId=&moduleId=170&createUser=


				if (node.level == 1) {
					this.$router.push({
						path: actionsPath,
						query: {
							projectId: data.row.id
						}
					});
				}
				if (node.level == 2) {
					this.$router.push({
						path: actionsPath,
						query: {
							moduleId: data.row.id
						}
					});
				}

			},
			treeNodeLoad(node, resolve) {
               console.log(node);
				if (node.level === 0) {

					this.axios.get('/apimanager/project/list?depId=' + this.depId).then((response) => {
						var json = response.data;
						//console.log(json);
						var data = json.data;
						var result = [];
						for (var i = 0; i < data.length; i++) {
							var p = data[i];
							// console.log("----"+p.projectName);
							result.push({
								"id": "p"+p.id,
								"label": p.projectName,
                                 "row":p,
								"leaf": false
							});
						}

						//-- set root data for lazy mode. if not set ,  add/edit/delete cause error
						node.data=result;



						/*this.$nextTick(function(){
						    setTimeout(function() {
                                var nodes = document.querySelectorAll(".custom-tree-node");
                                console.log("next:" + nodes.length)
                                for (var i = 0; i < nodes.length; i++) {
                                    var p = nodes[i].parentNode;
                                    if (!p.onmouseenter) {
                                        p.onmouseenter = function () {
                                            console.log("hehe")
                                            event.target .querySelector('.el-dropdown-link').style.display = 'block';
                                        }
                                    }
                                    if (!p.onmouseleave) {
                                        p.onmouseleave = function () {
                                            event.target.querySelector('.el-dropdown-link').style.display = 'none';
                                        }
                                    }
                                }
                            },3000);


                        });


*/

						return resolve(result);



					}).catch((response) => {
						console.log(response)
					})

				}
				if (node.level == 1) {
					this.axios.get('/apimanager/module/list?projectId=' + node.data.row.id).then((response) => {
						var json = response.data;
						//console.log("node.id=" + node.id);
						//console.log(json);
						var data = json.data;
						var result = [];
						for (var i = 0; i < data.length; i++) {
							var p = data[i];
							//console.log("moduleName:" + p.moduleName);
							result.push({
								"id": "m"+p.id,
								"label": p.moduleName,
                                "row":p,
								"leaf": true
							});
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
