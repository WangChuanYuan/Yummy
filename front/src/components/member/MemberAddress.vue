<template>
  <div>
    <h3>地址管理</h3>
    <hr/>
    <el-button type="text" @click="editing = true">新增地址</el-button>
    <el-row>
      <el-col v-for="(address, index) in addresses" :key="index" :span="8" :offset="1">
        <AddressCard :address="address" @deleteAddress="deleteAddress(index)"/>
      </el-col>
    </el-row>
    <!-- 新增地址 -->
    <el-dialog title="新增地址" :visible.sync="editing" width="30%">
      <AddressEditor @closeEditor="closeEditor" @addAddress="addAddress"/>
    </el-dialog>
  </div>
</template>

<script>
import AddressCard from './address/AddressCard';
import AddressEditor from './address/AddressEditor';
import Api from '../../assets/js/api';

export default {
  name: 'MemberAddress',
  components: {AddressEditor, AddressCard},
  data () {
    return {
      addresses: [],
      editing: false
    };
  },
  mounted () {
    Api('/get_addresses', {
      'email': sessionStorage.getItem('email')
    }).then((data) => {
      this.addresses = data;
    }).catch(() => {
      this.addresses = [];
    });
  },
  methods: {
    closeEditor () {
      this.editing = false;
    },
    addAddress (address) {
      this.addresses.push(address);
    },
    deleteAddress (index) {
      this.addresses.splice(index, 1);
    }
  }
};
</script>

<style scoped>

</style>
