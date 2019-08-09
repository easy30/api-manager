<template>
    <div class="main">
        <div v-for="(node,index) in nodes">

                <el-row>
                    <el-col :span="2"  >
                        <span style="min-width: 50px">
                            <el-button  type="danger" @click="deleteField(index)">x</el-button>
                            <el-button  type="primary"  v-if="isObject(node.type)" @click="insertChild(index)">+</el-button>


                        </span>

                    </el-col>
                    <el-col :span="8">



                        <input :id="node.id" :style="{  paddingLeft: deep + 'px', width:'100%',  boxSizing: 'border-box' }"
                                v-model="node.name">

                    </el-col>

                    <slot :node="node" :deep="deep" :index="index" :nodes="nodes"></slot>


                    <!-- <span :style="{ marginLeft: deep + 'px' }"></span>-->

                    <!--<span>deep:{{deep}}</span>-->
                    <!--  <span>name:{{node.name}}</span>-->

                </el-row>

            <JsonEditor :nodes="node.child" :deep="deep+20">
                <template v-slot="sd">
                    <slot :node="sd.node" :deep="sd.deep"></slot>
                </template>
            </JsonEditor>

        </div>
    </div>
</template>

<script>
    export default {
        name: 'JsonEditor',
        methods: {
            deleteField: function (index) {
                // alert(index);
                this.nodes.splice(index, 1);
            },
            insertChild: function (index) {
                //console.log("insertChild+"+this.utils.clone);
                var node = this.nodes[index];
                var values=this.utils.clone(this.defValues);
                if(this.addChild){
                    this.addChild(node,values);
                }else {
                    if (node.child == null) node.child = [];
                    node.child.push(values);
                }
                // console.log(this.nodes.child[0]);
            },
            isObject: function (type) {
                return type >= 4;
            }
        },
        props: {
            addChild: {
                type: Function,
                default: null
            },
            defValues: Object,
            nodes: Array,
            deep: Number,
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    .el-row {
        padding-bottom: 2px;
        margin-bottom: 10px;
        border-bottom: 1px lightgray solid;

    }

    .el-col {
        border-radius: 0px;
        margin-bottom: 2px;
        margin-right: 2px;
    }
</style>
