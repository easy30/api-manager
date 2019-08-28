<template>

    <el-container style=" border: 1px solid #eee">
        <el-header>

            <router-link to="/" style="text-decoration: none;">
                <img src="img/logo.png" style="width: 48px;vertical-align: middle;">
                <span style="vertical-align: middle;  margin-left: 5px; font-size: 18px; font-weight: bold;margin-right: 20px">API Manager</span>
            </router-link>
           <!-- <router-link to="/" v-t="'homePage'"></router-link> &nbsp;-->
            <router-link :to="{path: `/main/api/${this.$route.params.depId}`}">API</router-link> &nbsp;
            <router-link :to="{path: `/main/doc/${this.$route.params.depId}`}">Doc</router-link> &nbsp;

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