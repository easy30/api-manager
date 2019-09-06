<template>
	<el-container style="height: 500px; border: 1px solid #eee">


		<!-- {{$route.query.id}}-->
		<el-aside width="220px" style="background-color: rgb(238, 241, 246)">

            <!-- add project-->
            <el-row class="c-m-5"><el-col :span="24" >
                <el-button icon="el-icon-plus" :title="$tt('add project')"
                           @click="$refs.apiProject.add()" style="float: right">{{$tt('add project')}}</el-button>

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
		<api-project ref="apiProject"></api-project>
		<api-module ref="apiModule"></api-module>

		<router-view :key="key"></router-view>

	</el-container>

</template>

<style>
	.el-aside {
		color: #333;
	}
</style>

<script>

    import ApiProject from './api-project.vue';
    import ApiModule from './api-module.vue';
	var actionsPath;

	export default {
        components:{ ApiProject,ApiModule},
		data() {


			return {
                depId:this.$route.params.depId,

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

            console.log("this.$route",this.$route);
			actionsPath = `/main/api/${this.depId}/list`;

		/*	this.$router.push({
				path: actionsPath,
				query: this.utils.clone(this.$route.query)
			});*/

		},

		methods: {

            projectCommand(args) {
                var apiProject=this.$refs.apiProject;
                if (args[0] == 1) {
                    apiProject.edit(args[1], args[2]);
                } else if (args[0] == 2) {
                    apiProject.delete(args[1], args[2]);
                } else if (args[0] == 3) {
                    this.$refs.apiModule.add(args[1], args[2]);
                }
            },


            moduleCommand(args){
                var apiModule=this.$refs.apiModule;
                if(args[0]==1){
                     apiModule.edit(args[1],args[2]);
                }else  if(args[0]==2){
                    console.log("api:");
                    console.log(this);
                    apiModule.delete(args[1],args[2]);
                }
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
                 //console.log(node);
				if (node.level === 0) {

					this.axios.get('/apimanager/project/list?depId=' + this.depId).then((response) => {
						var json = response.data;
						//console.log(json);
						var data = json.data;
						data.sort(this.utils.compare("projectName"));
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
