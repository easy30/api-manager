<template>
    <div id="app">
        <!--
            <div>
              <p>
                If Element is successfully added to this project, you'll see an
                <code v-text="'<el-button>'"></code>
                below
              </p>
              <el-button>el-button</el-button>
            </div>
        -->


        <!-- <router-link to="/">Go to Home</router-link> &nbsp;
         <router-link to="/json">Go to Json</router-link> &nbsp;
         <router-link to="/about">Go to About</router-link>-->
        <!-- <span >{{$t('hello')}}</span>

         <span v-t="'id'"></span>vt <span v-t="'add'"></span>
         -------- -->
        <router-view v-if="isShow"></router-view>

    </div>
</template>

<script>
    //  import Lang from '@/components/Lang.vue'

    export default {
        name: 'app',
       /* provide() {
            return {
                reload: this.reload
            }
        },*/
        data() {
            return {
                isShow: true
            }
        },
        methods: {
            reload: function() {
                console.log("reload")
                this.isShow = false
                this.$nextTick(function () {
                    this.isShow = true
                })
            }
        },
        mounted() {
            /*var array= document.querySelectorAll("[lang-key]");
           // alert(array.l)
            for(var i=0;i<array.length;i++){

                var e=array[i];

                e.innerHTML= this.$t(  e.getAttribute("lang-key"));
            }*/

        }

    }
</script>

<style>
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        /*text-align: center;*/
        color: #2c3e50;
        margin-top: 0px;
    }
</style>
