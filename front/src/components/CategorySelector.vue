<template>
  <div class="selector" :style="{width: width + 'px'}">
    <el-row>
      <el-col :span="3" style="color: #888; font-weight: bold">{{title}}</el-col>
      <el-col :span="21">
        <el-row :gutter="5">
          <el-col :span="3" v-for="item in categories" :key="item[value]">
            <div class="item" :class="{selected: item[value] === selected}" @click="select(item[value])">{{item[label]}}</div>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'CategorySelector',
  props: {
    'title': {
      type: String,
      default: '分类'
    },
    'categories': {
      type: [Array, Object],
      required: true
    },
    'label': {
      type: String,
      required: true
    },
    'value': {
      type: String,
      required: true
    },
    'width': {
      type: Number,
      default: 800
    }
  },
  data () {
    return {
      selected: ''
    };
  },
  methods: {
    select (value) {
      this.selected = value;
      this.$emit('select', value);
    }
  }
};
</script>

<style scoped>
  .selector {
    padding: 5px 10px 5px 10px;
    background-color: white;
    border: 1px solid var(--theme-medium-grey);
    box-shadow: 10px 10px 5px var(--theme-medium-grey);
  }

  .selected {
    background-color: var(--theme-grey);
  }

  .item:hover {
    background-color: var(--theme-grey);
    cursor: pointer;
  }
</style>
