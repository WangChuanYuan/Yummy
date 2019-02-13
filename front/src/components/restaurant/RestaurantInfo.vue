<template>
  <el-form :model="restaurantInfo" :rules="infoRules" ref="restaurantInfo" class="center" style="width: 30%">
    <el-form-item prop="name" label="门店名称">
      <el-input v-model="restaurantInfo.name" placeholder="门店名称"></el-input>
    </el-form-item>
    <el-form-item prop="type" label="门店分类">
      <el-select v-model="restaurantInfo.type" placeholder="请选择" style="width: 100%" :value="types.DELICACY.value">
        <el-option v-for="type in types" :key="type.value" :value="type.value" :label="type.label"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item prop="location" label="位置">
      <el-input v-model="restaurantInfo.location" placeholder="位置" readonly="true">
        <v-region slot="append" :ui="true" @values="locationChange"></v-region>
      </el-input>
    </el-form-item>
    <el-form-item prop="detailLocation" label="详细地址">
      <el-input v-model="restaurantInfo.detailLocation" placeholder="详细地址"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit('restaurantInfo')" style="width: 100%">提交</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import {RestaurantType} from '../../assets/js/attrib';
import Api from '../../assets/js/api';

export default {
  name: 'RestaurantInfo',
  props: {
    'aim': {
      type: String,
      default: 'add'
    }
  },
  data () {
    return {
      types: RestaurantType,
      restaurantInfo: {
        name: '',
        type: RestaurantType.DELICACY.value,
        location: '',
        detailLocation: ''
      },
      infoRules: {
        name: [
          {
            required: true,
            message: '请输入门店名称',
            trigger: 'blur'
          }
        ],
        type: [
          {
            required: true,
            message: '请选择门店分类',
            trigger: 'blur'
          }
        ],
        location: [
          {
            required: true,
            message: '请选择位置',
            trigger: 'blur'
          }
        ],
        detailLocation: [
          {
            required: true,
            message: '请输入详细地址',
            trigger: 'blur'
          }
        ]
      }
    };
  },
  mounted () {
    if (this.aim !== 'add') {
      Api('/get_restaurant', {
        'id': sessionStorage.getItem('id')
      }).then((data) => {
        if (data) this.restaurantInfo = data;
      }).catch(() => {
      });
    }
  },
  methods: {
    locationChange (val) {
      let location = '';
      if (val.province) {
        location += val.province.value;
      }
      if (val.city) {
        location += val.city.value;
      }
      if (val.area) {
        location += val.area.value;
      }
      if (val.town) {
        location += val.town.value;
      }
      this.infoForm.location = location;
    },
    submit (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.aim === 'add') {
            // todo register restaurant
          }
        }
      });
    }
  }
};
</script>

<style scoped>
</style>
