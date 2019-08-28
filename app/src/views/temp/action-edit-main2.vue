<template>



	<el-tabs value="t-header">

		<!-- header   -->

		<el-tab-pane label="Header" name="t-header">
			<el-row>
				<el-col :span="2">&nbsp;</el-col>
				<el-col :span="8" v-t="'name'"></el-col>
				<el-col :span="8" v-t="'description'"></el-col>
				<el-col :span="5" v-t="'sample'"></el-col>
			</el-row>
			<JsonEditor :defValues="headers.defValues" :nodes="headers.nodes" :deep="0">
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
			<el-button type="primary" plain @click="insertField(headers)" v-t="'addParameter'"></el-button>
		</el-tab-pane>

		<!-- body -->
		<el-tab-pane label="Body" name="t-body">
			<JsonEditor :defValues="body.defValues" :nodes="body.nodes" :deep="deep" :addChild="addChild">
				<template v-slot="sd">
					<el-col :span="3">
						<el-select v-model="sd.node.type" @change="changeType(sd.nodes,sd.index)">
							<el-option v-for="item in fieldTypes" :key="item.value" :label="item.label" :value="item.value">
							</el-option>
						</el-select>
					</el-col>
					<el-col :span="1" style="text-align: center;">
						<el-checkbox v-model="sd.node.required" true-label="1"></el-checkbox>
					</el-col>
					<el-col :span="4">
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
			<el-button type="primary" plain @click="insertField(body)" v-t="'addParameter'"></el-button>
		</el-tab-pane>


		<!-- response success -->
		<el-tab-pane :label="$t('successResponse')" name="t-success">
			<JsonEditor :defValues="response.defValues" :nodes="response.nodes" :deep="deep" :addChild="addChild">
				<template v-slot="sd">
					<el-col :span="3">
						<el-select v-model="sd.node.type">
							<el-option v-for="item in fieldTypes" :key="item.value" :label="item.label" :value="item.value">
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
			<el-button type="primary" plain @click="insertField(response)" v-t="'addParameter'"></el-button>

		</el-tab-pane>
		
		<!--response fail -->
		<el-tab-pane :label="$t('failResponse')" name="t-fail">
			<JsonEditor :defValues="responseFail.defValues" :nodes="responseFail.nodes" :deep="0" :addChild="addChild">
				<template v-slot="sd">
					<el-col :span="3">
						<el-select v-model="sd.node.type">
							<el-option v-for="item in fieldTypes" :key="item.value" :label="item.label" :value="item.value">
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
			<el-button type="primary" plain @click="insertField(responseFail)" v-t="'addParameter'"></el-button>
		</el-tab-pane>

	</el-tabs>


</template>



<script>
	import JsonEditor from '@/components/JsonEditor.vue'
	export default {
		name: 'ActionBase',
		components: {
			JsonEditor
		},
		data() {
			return {
				form: {},
				value: 'aaa',
				fieldTypes: [{
						value: '0',
						label: 'date'
					},
					{
						value: '1',
						label: 'number'
					},
					{
						value: '2',
						label: 'string'
					},
					{
						value: '3',
						label: 'boolean'
					},
					{
						value: '4',
						label: 'object'
					},
					{
						value: '5',
						label: '[ ]'
					},
					{
						value: '6',
						label: 'number[ ]'
					},
					{
						value: '7',
						label: 'string[ ]'
					},
					{
						value: '8',
						label: 'boolean[ ]'
					},
					{
						value: '9',
						label: 'object[ ]'
					},

				],
				deep: 0,
				fields: [{
					"name": "name",
					"span": 3
				}, {
					"type": "name",
					"span": 3
				}],
				headers: {
					defValues: {
						"name": "",
						"type": "2",
						"desc": "",
						"defaultVal": ""
					},
					nodes: [{
						"name": "org",
						"type": "2",
						"required": "1",
						"desc": "所属机构",
						"rule": "1-6:ab",
						"defaultVal": ""
					}, {
						"name": "content",
						"type": "2",
						"required": "1",
						"desc": "工作内容",
						"rule": "1-6:ab",
						"defaultVal": "url"
					}, {
						"name": "workloadRemark",
						"type": "2",
						"required": "2",
						"desc": "工作量描述",
						"rule": "1-6:ab",
						"defaultVal": ""
					}, {
						"name": "shift",
						"type": "1",
						"required": "2",
						"desc": "台班：浮点数：最多保留三位小数",
						"rule": "1-100:10",
						"defaultVal": "0"
					}]
				},
				body: {
					defValues: {
						"name": "",
						"type": "2",
						"required": "1",
						"desc": "",
						"rule": "",
						"defaultVal": ""
					},
					nodes: [{
						"name": "org",
						"type": "2",
						"required": "1",
						"desc": "所属机构",
						"rule": "1-6:ab",
						"defaultVal": ""
					}, {
						"name": "content",
						"type": "2",
						"required": "1",
						"desc": "工作内容",
						"rule": "1-6:ab",
						"defaultVal": "url"
					}, {
						"name": "workloadRemark",
						"type": "2",
						"required": "2",
						"desc": "工作量描述",
						"rule": "1-6:ab",
						"defaultVal": ""
					}, {
						"name": "workhours",
						"type": "9",
						"required": "2",
						"desc": "工作时长",
						"rule": "",
						"defaultVal": "",
						"child": [{
							"name": "array[0]",
							"type": "4",
							"required": "2",
							"desc": "",
							"rule": "",
							"defaultVal": "",
							"child": [{
								"name": "remark",
								"type": "2",
								"required": "2",
								"desc": "描述",
								"rule": "1-6:ab",
								"defaultVal": ""
							}, {
								"name": "endTIme",
								"type": "1",
								"required": "2",
								"desc": "时间段，结束时间：eg：12：12",
								"rule": "1-100:10",
								"defaultVal": ""
							}, {
								"name": "startTime",
								"type": "2",
								"required": "2",
								"desc": "时间段，开始时间：eg：12：12",
								"rule": "1-6:ab",
								"defaultVal": ""
							}]
						}]
					}, {
						"name": "shift",
						"type": "1",
						"required": "2",
						"desc": "台班：浮点数：最多保留三位小数",
						"rule": "1-100:10",
						"defaultVal": "0"
					}]
				},
				
				response: {
					defValues: {
						"name": "",
						"type": "2",
						"desc": "",
						"rule": "",
						"defaultVal": ""
					},
					nodes: [{
						"name": "content",
						"type": "2",
						"required": "1",
						"desc": "工作内容",
						"rule": "1-6:ab",
						"defaultVal": "url"
					}, {
						"name": "workhours",
						"type": "9",
						"required": "2",
						"desc": "工作时长",
						"rule": "",
						"defaultVal": "",
						"child": [{
							"name": "array[0]",
							"type": "4",
							"required": "2",
							"desc": "",
							"rule": "",
							"defaultVal": "",
							"child": [{
								"name": "remark",
								"type": "2",
								"required": "2",
								"desc": "描述",
								"rule": "1-6:ab",
								"defaultVal": ""
							}, {
								"name": "startTime",
								"type": "2",
								"required": "2",
								"desc": "时间段，开始时间：eg：12：12",
								"rule": "1-6:ab",
								"defaultVal": ""
							}]
						}]
					}, {
						"name": "hello",
						"type": "1",
						"required": "2",
						"desc": "台班：浮点数：最多保留三位小数",
						"rule": "1-100:10",
						"defaultVal": "0"
					}]
				}



			}
		},

		mounted() {


		},
		props: {


		},

		methods: {
			insertField(sender) {
				sender.nodes.push(this.utils.clone(sender.defValues));
			},
			addChild(parent, defValues) {
				console.log("addchild:" + defValues);
				if (parent.child == null) parent.child = [];
				if (this.utils.isArrayField(parent.type)) {
					defValues.name = "array[" + parent.child.length + "]";
				}
				parent.child.push(defValues);

			},
			changeType(nodes, index) {

				var node = nodes[index];
				console.log(node);
				if (!this.utils.isObjectField(node.type) && node.child != null) {
					node.child = null;

				}
			}
		}
	};
</script>

<style>


</style>
