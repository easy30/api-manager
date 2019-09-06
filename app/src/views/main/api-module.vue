<template>


    <el-dialog :title="$t('module')" :visible.sync="visible" width="400px" :close-on-click-modal="false" :close-on-press-escape="false">
        <el-form >
            <el-form-item :label="$tt('module name')"  >
                <el-input class="el-input--big" v-model="row.moduleName" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item :label="$tt('module desc')"  >
                <el-input class="el-input--big"  v-model="row.moduleDesc" autocomplete="off"></el-input>
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



	var baseUrl="/apimanager/module";
	var labelName="moduleName";
	var idPrefix="m";
	var pIdPrefix="p";
	var leaf=true;

	export default {
		data() {

			return {


                row :{},
                visible:false,

                treeCurrentNodeData:null,

			}
		},
		computed: {

		},
		mounted() {

		},
        props: {


        },

		methods: {

            add(node,data) {

                this.row ={
                    projectId:data.row.id,
                    moduleName:"",
                    moduleDesc:""
                }
                this.visible = true;
            },

            edit(node, data) {
                console.log("edit:" + data)
                this.treeCurrentNodeData = data;
                this.row = data.row;
                this.visible = true;
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
            save() {
                var parent=this.$parent.$parent;
                var current = this.row;
                var update = current.id;
                var url = baseUrl + (update ? "/update" : "/add");
                var self = this;

                this.ajax.postForm(url, current,  (response) => {

                    if (update) {
                        self.treeCurrentNodeData.label = current[labelName];
                        parent.$forceUpdate();

                    } else {
                        current.id = response.data.data;
                        console.log(current);
                        parent.$refs.tree.append({
                            "id": idPrefix + current.id,
                            "label": current[labelName],
                            "row": current,
                            "leaf": leaf
                        },pIdPrefix+current.projectId);
                    }

                    self.visible = false;
                    //self.$forceUpdate();
                });
            },
            cancel() {
                this.visible = false;

            }
        }


	};
</script>
