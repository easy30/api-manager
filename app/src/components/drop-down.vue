<template>
    <el-dropdown trigger="click"  @command="doClickItem">
        <el-button type="primary" class="el-dropdown-link" v-model="name" @click="doClickButton" style="margin-right: 10px">{{name}}
        </el-button>
        <el-dropdown-menu slot="dropdown" >
            <el-dropdown-item :key="item[0]" v-for="item in items" :command="item" v-show="show">{{item[1]}}</el-dropdown-item>

        </el-dropdown-menu>

    </el-dropdown>
</template>

<script>
export default {
  name: 'DropDown',
    data(){ return { show:true}
    },
    mounted(){

    },
  props: {
      clickButton: {
          type: Function,
          default: null
      },
      clickItem: {
          type: Function,
          default: null
      },
      data:Array,
      name:String,
      items:Array

  },
    methods:{

      doClickButton(){
         this.show=this.clickButton(this.data);

      },
      doClickItem(item){

          this.clickItem(this.data,item)  ;

      },



}
}
</script>


