<script>
export default {
  data(){
    return {
      num:1000,
      uname:"非哥",
      msg:"<h2>标题</h2>",
      vbid:"d1",
      url:"https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png",
      attributeName:"id",
      mouseEvent:"click",
      message:"helloworld"
    }
  },
  computed:{
        reverseMsg:function (){
        return this.message.split('').reverse().join('')
        }
  },
  methods:{
    changeUname:function (){
      //this指向vue实例
      this.uname = "大飞哥"
    },
    changeColor:function (){
      this.vbid='d2'
    },

  },
};
</script>

<template>
  <div>
    <a href="https://vitejs.dev" target="_blank">
      <img src="/vite.svg" class="logo" alt="Vite logo" />
    </a>
    <a href="https://vuejs.org/" target="_blank">
      <img src="./assets/vue.svg" class="logo vue" alt="Vue logo" />
    </a>
  </div>
  <HelloWorld msg="Vite + Vue" />


    <div>
      <p>{{num}}</p>
      <p>{{uname}}</p>
      <!-- v-once :当数据改变世纪，插值处的内容不会更新 -->
      <p v-once>{{uname}}</p>
      <button @click = "changeUname">改变名字</button>
      <p v-html="msg"></p>
      <!-- v-bind:动态的绑定属性内容 -->
      <p  v-bind:id="vbid">v-bind绑定</p>
      <img v-bind:src="url"/>
      <button @click="changeColor">改变颜色</button>
      <button @click="vbid='d1'">改变颜色</button>
      <!-- 使用javascript表达式 -->
      <p>{{num+20}}</p>
      <p>{{uname.split('').reverse().join('')}}</p>
      <p  v-bind:id="vbid+1">v-bind绑定</p>
      <!-- v-bind语法糖: ： -->
      <p  :id="vbid">v-bind绑定</p>
      <!-- v-on：用于监听DOM事件 -->
      <button v-on:click="changeColor">改变颜色</button>
      <!-- v-on语法糖：@ -->
      <button @click="vbid='d1'">改变颜色</button>
      <!-- 动态参数 -->
      <!--  动态属性    -->
      <p  v-bind:[attributeName]="vbid">动态v-bind绑定</p>
      <button @click="attributeName='class'">改变属性</button>
      <!-- 动态事件 -->
      <button @[mouseEvent]="attributeName='class'">改变属性</button>
      <button @click="mouseEvent='mouseover'">改变事件</button>

      <p>{{reverseMsg}}</p>




    </div>



</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
#d1{
  color:red;
}
#d2{
  color:blue;
}

.d1{
  font-size: 50px;
}
</style>
