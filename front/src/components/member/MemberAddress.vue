<template>
  <div>
    <h3>地址管理</h3>
    <hr/>
    <el-button type="text" @click="editing = true">新增地址</el-button>
    <el-row>
      <el-col v-for="(address, index) in addresses" :key="index" :span="8" :offset="1">
        <AddressCard :address="address" @delete="deleteAddress(index)"/>
      </el-col>
    </el-row>
    <!-- 新增地址 -->
    <el-dialog title="新增地址" :visible.sync="editing" width="30%">
      <AddressEditor @add="addAddress"/>
    </el-dialog>
  </div>
</template>

<script>
import AddressCard from './address/AddressCard';
import AddressEditor from './address/AddressEditor';
import Api from '../../assets/js/api';
import {Code} from '../../assets/js/attrib';

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
      'id': sessionStorage.getItem('id')
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
      Api('/add_address', {
        'address': address,
        'id': sessionStorage.getItem('id')
      }).then((data) => {
        if (data.code === Code.SUCCESS) {
          this.addresses.push(data.value);
        } else {
          this.$message.warning(data.msg);
        }
        this.closeEditor();
      }).catch(() => {
        this.closeEditor();
      });
    },
    deleteAddress (index) {
      this.$confirm('确认删除该地址？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        Api('delete_Address', {
          'aid': this.addresses[index].aid
        }).then((data) => {
          if (data.code === Code.SUCCESS) {
            this.addresses.splice(index, 1);
          } else {
            this.$message.warning(data.msg);
          }
        });
      }).catch(() => {});
    }
  }
};
</script>

<style scoped>

</style>
