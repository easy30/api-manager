<template>
  <div class="main">
  <div v-for="(node,index) in nodes">
    <div>
      <span>
          <button v-if="isObject(node.type)" @click="insertChild(index)">+</button>
        <button  @click="deleteField(index)">X</button>

      </span>
     <!-- <span :style="{ marginLeft: deep + 'px' }"></span>-->
      <span><input :style="{ paddingLeft: deep + 'px' }" v-model="node.name" ></span>
      <!--<span>deep:{{deep}}</span>-->
    <!--  <span>name:{{node.name}}</span>-->
    <span>type:{{node.type}}</span>
    </div>
    <JsonEditor :nodes="node.child" :deep="deep+50"/>

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
            var node=this.nodes[index];
            if(node.child==null) node.child=[];
            node.child.push({
                "name": "childName",
                "type": "2",
                "required": "1",
                "desc": "所属机构",
                "rule": "1-6:ab",
                "defaultVal": ""
            });
           // console.log(this.nodes.child[0]);
        },
        isObject:function (type) {
          return type>=4;
        }
    },
  props: {
    nodes: Array,
      deep:Number,
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .main {
    border: 0px solid;
    text-align: left;
  }
</style>
