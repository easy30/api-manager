<template>
    <div> <span>

                        <span style="color:red">{{pageIndex}}</span>/<span>{{totalPage}}</span>
                         <span v-t="'pages'"></span>
                        <span> [{{pageSize}},{{totalRecord}}]</span>
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
            //alert(this.key);
        },
        props: {
            //jump:Number,
            pageIndex: Number,
            totalPage: Number,
            pageSize: Number,
            totalRecord: Number

        },
        methods: {
            go(page) {
                if (page==null || isNaN(page)) return;
                if (page == 0) page = this.pageIndex - 1;
                else if (page == -1) page = this.pageIndex + 1;
                else if (page == -2) page = this.totalPage;
                if (page < 1) page == 1;
                if (page > this.totalPage) page = this.totalPage;

                var q = this.utils.clone(this.$route.query);
                if (q == null) q = {};

                q.pageIndex = page;
                q.pageSize = this.pageSize;
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