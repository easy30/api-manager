<template>

  <template  v-for="node in nodes">
  <tr>
    <td :style="{ paddingLeft: deep + 'px' }" >{{node.name}}</td>
    <td>{{node.type}}</td>
    <td>{{node.required}}</td>
    <td>{{node.desc}}</td>
    <td>{{node.rule}}</td>
    <td>{{node.defaultVal}}</td>
  </tr>
  <TreeTable :nodes="node.child" :deep="deep+50"/>
  </template>

</template>

<script>
export default {
  name: 'TreeTable',
    methods: {

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
