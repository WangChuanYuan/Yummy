<template>
  <div>
    <el-card class="box-card clear-fix" :body-style="{ padding: '0px' }">
      <div class="clear-fix">
        <span>{{inAddress.name}}</span>
        <span>{{inAddress.sex === 'MAN' ? '先生' : '女士'}}</span>
        <div class="address-op">
          <el-button type="text" @click="editing = true">修改</el-button>
          <el-button type="text" @click="deleteAddress">删除</el-button>
        </div>
        <br/>
        <br/>
        <div>
          <span>{{inAddress.location}}</span>
          <br/>
          <span>{{inAddress.detailLocation}}</span>
          <br/>
          <span>{{inAddress.phone}}</span>
        </div>
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
          lng: 0,
          lat: 0,
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
      Api.post('/modify_address', {
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
    height: 110px;
    margin-bottom: 10px;
    text-align: left;
  }

  .address-op {
    padding-right: 20px;
    float: right;
  }
</style>
