<template>
  <div>
    <el-card class="box-card">
      <div>
        <span>{{inAddress.name}}</span>
        <span>{{inAddress.sex === 'MAN' ? '先生' : '女士'}}</span>
        <el-button type="text" class="address-op" @click="deleteAddress">删除</el-button>
        <el-button type="text" class="address-op" @click="editing = true">修改</el-button>
        <br/>
        <br/>
        <span>{{inAddress.location}}</span>
        <span>{{inAddress.detailLocation}}</span>
        <br/>
        <span>{{inAddress.phone}}</span>
      </div>
    </el-card>
    <!-- 修改地址 -->
    <el-dialog title="修改地址" :visible.sync="editing" width="30%">
      <AddressEditor :address="inAddress" aim="modify" @modify="modifyAddress"/>
    </el-dialog>
  </div>
</template>

<script>
import AddressEditor from './AddressEditor';
import Api from '../../../assets/js/api';
import {Code, Sex} from '../../../assets/js/attrib';

export default {
  name: 'AddressCard',
  components: {AddressEditor},
  props: {
    'address': {
      type: Object,
      default: function () {
        return {
          aid: 0,
          name: '',
          sex: Sex.MAN,
          location: '',
          detailLocation: '',
          phone: ''
        };
      }
    }
  },
  data () {
    return {
      editing: false,
      inAddress: JSON.parse(JSON.stringify(this.address))
    };
  },
  watch: {
    address (val) {
      this.inAddress = JSON.parse(JSON.stringify(val));
    }
  },
  methods: {
    closeEditor () {
      this.editing = false;
    },
    deleteAddress () {
      this.$emit('delete');
    },
    modifyAddress (address) {
      Api('/modify_address', {
        'address': address,
        'id': sessionStorage.getItem('id')
      }).then((data) => {
        if (data.code === Code.SUCCESS) {
          this.inAddress = data.value;
        } else {
          this.$message.warning(data.msg);
        }
        this.closeEditor();
      }).catch(() => {
        this.closeEditor();
      });
    }
  }
};
</script>

<style scoped>
  .box-card {
    width: 320px;
    height: 120px;
    margin: 8px 0;
    text-align: left;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .address-op {
    float: right;
    padding: 3px 0;
  }
</style>
