import axios from 'axios';
export default axios.create({
    baseURL: 'http://j3a308.p.ssafy.io:8080/api/',
    headers:{
        'Content-Type':'application/json',
    },
});