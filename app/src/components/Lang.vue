<template>
  <span>{{$t(k)}}</span>
</template>

<script>
export default {
  name: 'Lang',
    mounted(){
      //alert(this.key);
    },
  props: {
    k:String
  }
}
</script>

