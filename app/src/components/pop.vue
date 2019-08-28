<template>
    <div v-show="show" style="position: absolute;left:10px;top:20px;z-index: 100">
        <div class="item" :key="item[0]"  v-for="item in items" @click="doClick(item)">{{item[1]}}</div>
    </div>

</template>

<script>
export default {
  name: 'Pop',
    data(){ return { show:true,target:null}
    },
    mounted(){
      //alert(this.key);
    },
  props: {
      clickItem: {
          type: Function,
          default: null
      },
    items:Array

  },
    methods:{
      doClick(item){
          this.clickItem(this.target,item)  ;
          this.show=false;

      },
      getElementLeft(el) {
        if(el.offsetParent) {
        return this.getElementLeft(el.offsetParent) + el.offsetLeft
     }
        return el.offsetLeft
    },
        getElementTop(el) {
            if(el.offsetParent) {
                return this.getElementTop(el.offsetParent) + el.offsetTop
            }
            return el.offsetTop
        },
      pop(target){
          this.target=target;

          console.log("top:",this.target.offsetTop)
          console.log("left:",this.getElementLeft(target));
          this.$el.style.top=(this.getElementTop(target)-this.$el.offsetHeight-150)+"px";
          this.$el.style.left=this.target.offsetLeft+"px";
          this.show=true;

      }


}
}
</script>
<style scoped>
    .item{
        padding: 5px;
        background-color: white;
        border: 1px solid lightgray;
        cursor: pointer;
    }

</style>

