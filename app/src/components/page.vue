<template>
    <div> <span>

                        <span style="color:red">{{data.pageIndex}}</span>/<span>{{data.totalPage}}</span>
                         <span v-t="'pages'"></span>
                        <span>  {{data.totalRecord}}</span>  <span v-t="'record'"></span>
             </span>
        <span class="c-ml-5">
            <el-input v-model="jump"  size="mini" style="width:30px;padding: 0 3px}"></el-input>
             <el-button class="c-p-go" @click="go(jump)">GO</el-button>
        </span>

        <span class="c-ml-20">
                    <el-button v-t="'firstPage'" @click="go(1)"></el-button>
                    <el-button v-t="'prevPage'" @click="go(0)"></el-button>
                    <el-button v-t="'nextPage'" @click="go(-1)"></el-button>
                    <el-button v-t="'lastPage'" @click="go(-2)"></el-button>
        </span>


    </div>
</template>

<script>
    export default {
        name: 'Page',
        data() {
            return {
                jump: null
            }
        },
        mounted() {
             
        },
        props: {
            //jump:Number,
            data:Object

        },
        methods: {
            go(page) {
                if (page==null || isNaN(page)) return;
                if (page == 0) page = this.data.pageIndex - 1;
                else if (page == -1) page = this.data.pageIndex + 1;
                else if (page == -2) page = this.data.totalPage;
                if (page < 1) page == 1;
                if (page > this.data.totalPage) page = this.data.totalPage;

                var q = this.utils.clone(this.$route.query);
                if (q == null) q = {};

                q.pageIndex = page;
                q.pageSize = this.data.pageSize;
                this.$router.replace({path: this.$route.path, query: q});


            },
        }
    }
</script>

<style>
  .c-p-go  .el-input__inner{
         width: 30px;
         padding: 0 3px;

    }
</style>