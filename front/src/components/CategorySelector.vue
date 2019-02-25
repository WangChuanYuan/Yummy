<template>
  <div class="selector" :style="{width: width + 'px', backgroundColor: backgroundColor}">
    <el-row>
      <el-col :span="3" style="font-weight: bold" :style="{color: titleColor}">{{title}}</el-col>
      <el-col :span="21">
        <el-row :gutter="5">
          <el-col :span="3">
            <div class="item" :class="{selected: '' === selected}" @click="select('')">全部</div>
          </el-col>
          <el-col :span="3" v-for="item in categories" :key="item[value]">
            <div class="item" :class="{selected: selected === item[value]}" @click="select(item[value])">{{item[label]}}</div>
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
    'titleColor': {
      type: String,
      default: '#888'
    },
    'backgroundColor': {
      type: String,
      default: 'white'
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
    border: 1px solid var(--theme-medium-grey);
    box-shadow: 10px 10px 5px var(--theme-medium-grey);
  }

  .selected {
    background-color: var(--theme-blue);
    color: white;
  }

  .item:hover {
    background-color: var(--theme-blue);
    color: white;
    cursor: pointer;
  }
</style>
