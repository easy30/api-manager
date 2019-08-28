<template>

	<el-container>

		<el-main>
			<table class="c-table c-big">
				<tr>
					<th v-t="'id'"></th>
					<th v-t="'name'"></th>
					<th v-t="'description'"></th>
					<th v-t="'editor'"></th>
					<th v-t="'lastmodified'"></th>
					<th v-t="'operation'"></th>
				</tr>
				<template v-for="(row,index) in rows">
					<tr v-if="row.edit" :key="index">
						<td>{{row.id}}</td>
						<td>
							<el-input class="el-big" v-model="row.depName"></el-input>
						</td>
						<td>
							<el-input class="el-big" v-model="row.depDesc"></el-input>
						</td>
						<td>{{row.updateUserName}}</td>
						<td>{{row.updateTime}}</td>
						<td>
							<el-button @click="save(index)" size="small" v-t="'save'"></el-button>
							<el-button @click="cancel(index)" size="small" v-t="'cancel'"></el-button>
						</td>

					</tr>
					<tr v-else :key="index">

						<td @click="rowClick(index)" style="cursor: pointer;">{{row.id}}</td>
						<td @click="rowClick(index)" style="cursor: pointer;">{{row.depName}}</td>
						<td @click="rowClick(index)" style="cursor: pointer;">{{row.depDesc}}</td>
						<td @click="rowClick(index)" style="cursor: pointer;">{{row.updateUserName}}</td>
						<td @click="rowClick(index)" style="cursor: pointer;">{{row.updateTime}}</td>
						<td>
							<el-button @click="edit(index)" size="small" v-t="'edit'"></el-button>
							<el-button @click="del(index)" size="small" v-t="'delete'"></el-button>
						</td>
					</tr>
				</template>


			</table>
			<!-- <el-table :data="rows" @row-click="rowClick" style="width: 100%">
				<el-table-column prop="id" :label="$t('id')">
				</el-table-column>
				<el-table-column prop="depName" :label="$t('name')">
				</el-table-column>
				<el-table-column prop="depDesc" :label="$t('description')">
				</el-table-column>
				<el-table-column :label="$t('operation')">
					<template slot-scope="scope">
						<el-button @click="handleClick(scope.row)" type="text" size="small" v-t="'edit'"></el-button>
						<el-button @click="handleClick(scope.row)" type="text" size="small" v-t="'delete'"></el-button>
					</template>
				</el-table-column>
			</el-table> -->
			<el-row class="c-m-10">
				<el-col :span="24">
					<el-button @click="add()" v-t="'add'"></el-button>
				</el-col>
			</el-row>

		</el-main>
	</el-container>





</template>

<script>
	// @ is an alias to /src
	//import HelloWorld from '@/components/HelloWorld.vue'
	var baseUrl = "/apimanager/department";
	export default {
		name: 'home',
		data() {
			return {
				rows: [],
				info: {}
			}
		},
        async mounted() {
			/*  this.axios.post("/apimanager/user/login?account=13911610242&password=123456")
			      .then((response)=>{
			          console.log("res="+response.data);


			      }).catch((response)=>{
			      console.log(response)
			  })*/

			this.axios.get(baseUrl + "/list").then((response) => {
				var json = response.data;
				this.rows = json.data;
				this.info = response.data;

			}).catch((response) => {
				console.log(response)
			})

		},
		methods: {

			edit(index) {
				console.log(index);
				this.rows[index].edit = true
				this.$forceUpdate();
				//console.log(this.rows[index])
			},
			del(index) {
				if (confirm(this.$t("deleteConfirm"))) {
				    var thisRows=this.rows;
					var row = thisRows[index];
					this.ajax.postForm(baseUrl + "/delete", { id: row.id },
						function (response) {
                            thisRows.splice(index, 1);
					} ) ;

				}
			},
			save(index) {
				var row = this.rows[index];
				var url = baseUrl + (row.id ? "/update" : "/add");
				console.log(row);
				var self=this;
				this.ajax.postForm(url,row, function(response){
				    row.edit = false;
                    self.$forceUpdate();
				});


			},
			cancel(index) {
				var row = this.rows[index];
				if (row.id) {
					row.edit = false;
				} else {
					this.rows.pop();

				}

				this.$forceUpdate();
			},
			add() {
				this.rows.push({
					edit: true
				});
			},
			rowClick(index) {
				//console.log(row, event, column);
				//this.router.go()
				// this.$router.push({path: '/main/api', params: {depId: row.id}});
				var row = this.rows[index];
				this.$router.push({
					path: `/main/api/${row.id}`
				}); //, params: {depId: row.id}});

			}
		},
		components: {

		}
	}
</script>
