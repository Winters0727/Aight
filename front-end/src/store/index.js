import Vue from 'vue';
import Vuex from 'vuex';

import * as auth from "@/store/auth";

import createPersistedState from "vuex-persistedstate"

Vue.use(Vuex);

export default new Vuex.Store({
    modules:{
        auth,
    },
    plugins: [createPersistedState({
        storage:window.sessionStorage,
        paths:[
            'auth',
        ]
    })],
})