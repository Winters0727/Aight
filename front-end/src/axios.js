import axios from 'axios'
import store from './store'


const instance = axios.create({
    // baseURL: 'http://localhost:8080/api/',
    baseURL: 'http://j3a308.p.ssafy.io:8080/api'
});

instance.interceptors.request.use(
    (config) =>{
        if(store.getters.nickname){
            config.headers['jwt-auth-token'] = store.getters.jwtAuthToken;
            config.headers['nickname'] = store.getters.encodedNickname;
            config.headers['user_pk'] = store.getters.user_pk;
        }

        return config;
    }
);

instance.interceptors.response.use(
    (response) =>{
        return response;
    },
    (error) =>{
        

        switch(error.response.status){
            case 401:
                store.dispatch('logout');
                break;
        }

        return Promise.reject(error);
    },
);

export default instance;