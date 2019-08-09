<template>

    <el-container style=" border: 1px solid #eee">
        <el-header>

            <router-link to="/">Home</router-link> &nbsp;
            <router-link :to="{path: '/main/api', query: {id: this.$route.query.id}}">API</router-link> &nbsp;
            <router-link :to="{path: '/main/doc', query: {id: this.$route.query.id}}">Doc</router-link> &nbsp;

        </el-header>
        <el-main>
            <router-view></router-view>
        </el-main>
    </el-container>

    <!--<div>
        <router-view></router-view>
    </div>-->

</template>

<style>
    .el-header {
        background-color: #B3C0D1;
        color: #333;
        line-height: 60px;
    }

</style>

<script>
    export default {
        data() {

            return {

            }
        }
    };
</script>