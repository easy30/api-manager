<template>


    <el-container>
        <el-main>

            <el-tabs value="first">
                <el-tab-pane :label="$t('apiConfig')" name="first">
                    <action-edit-base></action-edit-base>
                    <action-edit-main></action-edit-main>

                </el-tab-pane>
                <el-tab-pane :label="$t('testCase')" name="second">

                </el-tab-pane>

            </el-tabs>


        </el-main>
    </el-container>


</template>

<style>

</style>

<script>

    import ActionEditBase from './action-edit-base.vue';
    import ActionEditMain from './action-edit-main.vue';
    export default {
        components: { ActionEditBase,ActionEditMain},
        data() {
            return {
                form: {},
                value: 'aaa',
                options: []

            }
        },
        mounted() {


        },

        methods: {}
    };
</script>

<style>


</style>