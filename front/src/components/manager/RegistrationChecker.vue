<template>
  <div>
    <h3>门店信息审核</h3>
    <hr/>
    <el-container>
      <el-table :data="registrations">
        <el-table-column prop="time" label="申请日期"/>
        <el-table-column label="申请类型">
          <template slot-scope="scope">
            <span>{{scope.row.restaurant.accountState ===  'UNACTIVATED' ? '注册' : '修改'}}</span>
          </template>
        </el-table-column>
        <el-table-column label="申请人">
          <template slot-scope="scope">
            <span>{{scope.row.restaurant.id}}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态">
          <template slot-scope="scope">
            <div>{{status[scope.row.status].label}}</div>
          </template>
        </el-table-column>
        <el-table-column label="详情">
          <template slot-scope="scope">
            <el-popover
              placement="left"
              trigger="hover">
              <div v-if="scope.row.restaurant.accountState !== 'UNACTIVATED'">
                <h3>门店当前信息</h3>
                <el-form>
                  <el-form-item label="门店名称">
                    {{scope.row.restaurant.registerInfo.name}}
                  </el-form-item>
                  <el-form-item label="门店邮箱">
                    {{scope.row.restaurant.registerInfo.email}}
                  </el-form-item>
                  <el-form-item label="门店分类">
                    {{types[scope.row.restaurant.registerInfo.type].label}}
                  </el-form-item>
                  <el-form-item label="位置">
                    {{scope.row.restaurant.registerInfo.location}}
                  </el-form-item>
                  <el-form-item label="详细地址">
                    {{scope.row.restaurant.registerInfo.detailLocation}}
                  </el-form-item>
                </el-form>
                <hr/>
              </div>
              <div>
                <h3>新信息</h3>
                <el-form style="color: red">
                  <el-form-item label="门店名称">
                    {{scope.row.registerInfo.name}}
                  </el-form-item>
                  <el-form-item label="门店邮箱">
                    {{scope.row.registerInfo.email}}
                  </el-form-item>
                  <el-form-item label="门店分类">
                    {{types[scope.row.registerInfo.type].label}}
                  </el-form-item>
                  <el-form-item label="位置">
                    {{scope.row.registerInfo.location}}
                  </el-form-item>
                  <el-form-item label="详细地址">
                    {{scope.row.registerInfo.detailLocation}}
                  </el-form-item>
                </el-form>
              </div>
              <el-button type="text" slot="reference">详情</el-button>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-radio-group
              v-model="scope.row.status"
              :disabled="scope.row.status !== status.PENDING.value"
              size="mini" @change="check(scope.row)">
              <el-radio-button :label="status.ACCESS.value">通过</el-radio-button>
              <el-radio-button :label="status.DENIED.value">拒绝</el-radio-button>
            </el-radio-group>
          </template>
        </el-table-column>
      </el-table>
    </el-container>
  </div>
</template>

<script>
import {Code, RegStatus, RestaurantType} from '../../assets/js/attrib';
import Api from '../../assets/js/api';

export default {
  name: 'RegistrationChecker',
  data () {
    return {
      status: RegStatus,
      types: RestaurantType,
      registrations: []
    };
  },
  mounted () {
    Api.get('/get_pending_registrations').then((data) => {
      if (data) this.registrations = data;
    }).catch(() => {});
  },
  methods: {
    check (registration) {
      Api.post('/check_registration', {
        rgid: registration.rgid,
        status: registration.status
      }).then((data) => {
        if (data.code === Code.SUCCESS) {
          this.$message.success(data.msg);
        } else this.$message.warning(data.msg);
      }).catch(() => {});
    }
  }
};
</script>

<style scoped>

</style>
