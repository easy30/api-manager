<template>


                    <el-form ref="form" :inline="false" :model="form" label-width="50px" size="mini">
                        <table border="0" width="100%">
                            <tr>
                                <td>
                                    <el-form-item :label="$t('name')">
                                        <el-input v-model="form.name" ></el-input>
                                    </el-form-item>
                                </td>
                                <td>
                                    <el-form-item :label="$t('module')">
                                        <el-select v-model="value" filterable>
                                            <el-option
                                                    v-for="item in options"
                                                    :key="item.value"
                                                    :label="item.label"
                                                    :value="item.value">
                                            </el-option>
                                        </el-select>
                                    </el-form-item>

                                </td>
                                <td>
                                    <el-form-item :label="$t('status')">
                                        <el-input v-model="form.name" style="width: 80px"></el-input>
                                    </el-form-item>
                                </td>
                                <td>
                                    <el-form-item :label="$t('level')">
                                        <el-input v-model="form.name" style="width: 80px"></el-input>
                                    </el-form-item>
                                </td>
                            </tr>

                            <tr>
                                <td colspan="4">

                                    <el-form-item :label="$t('url')">
                                        <el-select v-model="value" style="width: 70px">
                                            <el-option
                                                    v-for="item in options"
                                                    :key="item.value"
                                                    :label="item.label"
                                                    :value="item.value">
                                            </el-option>
                                        </el-select>


                                        <el-select v-model="value" filterable>
                                            <el-option
                                                    v-for="item in options"
                                                    :key="item.value"
                                                    :label="item.label"
                                                    :value="item.value">
                                            </el-option>
                                        </el-select>
                                        <el-input style="width: 300px"></el-input>
                                    </el-form-item>
                                </td>
                            </tr>


                            <tr>
                                <td colspan="4">

                                    <el-form-item :label="$t('description')">
                                        <el-input
                                                type="textarea"
                                                :rows="2"
                                                v-model="value">
                                        </el-input>
                                    </el-form-item>

                                </td>
                            </tr>
                        </table>

                    </el-form>


</template>



<script>
    export default {
        name: 'ActionEditBase',
        components: {},
        data() {
            return {
                form: {},
                value: 'aaa',
                options: []

            }
        },

        mounted() {


        },
        props: {


        },

        methods: {}
    };
</script>

<style>


</style>