import Vue from "vue";
import Vuex from "vuex";
Vue.use(Vuex)

const state = {
  loading: false,
}

export default new Vuex.Store({
  state
})
