<template>
    <div class = "back">
        <v-app-bar
        color="white"
        fixed

        >   
        <v-layout row>
                <v-flex xs6 md1 lg1>
                <v-img
                    src="@/assets/images/ProjectLogo.png"
                    class="shrink"
                    contain
                    height="50"
                />
                </v-flex>
                <v-flex  md7 lg9>
                </v-flex>
                <v-flex xs3 md2 lg1>
                    
                </v-flex>
                <v-flex xs3 md2 lg1>
                    <v-btn
                        dense
                        dark
                        color="secondary"
                        small
                        style="margin-left:5%; margin-top:6%;"
                        @click="logout"
                    >
                        로그아웃
                    </v-btn>
                </v-flex>
            
            </v-layout>
    </v-app-bar>

        <v-main>
            <section id = "interview">

                <div class ="text-center" style ="margin-top:200px">
                    <video id="inputVideo" style="display:inline" :width=width :height=height poster="../../assets/images/webcam_2.jpg"></video>
                    <canvas id="myChart" style="display:inline" :width=width :height=height></canvas>
                </div>
                <div class ="text-center" style ="margin-top:40px">
                    <v-btn fab dark large color ="red darken-4" id ="actionBtn">Record</v-btn>
                    <a :href=downloadChart download='다운로드.png' id="resultBtn"><v-btn fab dark large color ="black darken-4">Result</v-btn></a>
                    <v-btn fab dark large color ="orange darken-4" id ="initializeBtn" @click="() => initializeChart()">초기화</v-btn>
                    <v-btn fab dark large color ="blue darken-4" id ="uploadBtn" @click="() => uploadData(realTimeArray)">업로드</v-btn>
                </div>
            </section>
        </v-main>

        <v-speed-dial fixed bottom right open-on-hover v-model="fab" style="margin-bottom: 100px;">
            <v-btn
                slot="activator"
                fab
                v-model="fab"
            >
                <v-icon v-if="fab">mdi-close</v-icon>
                <v-icon v-else>mdi-plus</v-icon>
            </v-btn>
            <router-link to="/board">
                <v-btn fab small>
                <v-icon>fas fa-book</v-icon>
                </v-btn>
            </router-link>
            <v-btn fab small>
                <v-icon>mdi-account-circle</v-icon>
            </v-btn>
            <v-btn fab small>
                <v-icon @click="goHome">mdi-home</v-icon>
            </v-btn>
        </v-speed-dial>


        <v-bottom-navigation
                dark
                fixed
                color="#292929"
                height="100"
        >
        <div class="title font-weight-light grey--text text--lighten-1 text-center" style="line-height: 100px;">
            &copy; {{ (new Date()).getFullYear() }} — AI Interview — Made with 💜 by Team.Aight
        </div>
        </v-bottom-navigation>
        
    </div>

    
</template>


<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>

<script>

import io from 'socket.io-client'
import cv from 'opencv.js'
import Chart from 'chart.js'
import { mapGetters } from 'vuex'
import axios from 'axios'

export default {
    name: 'socketTest',
    data(){
        return{
            message: [],
            socket: null,
            src : null,
            dst : null,
            videodata : null,
            cap : null,
            actionBtn: null,
            resultBtn: null,
            initializeBtn : null,
            streaming : false,
            stream : null,
            width : 0,
            height : 0,
            FPS : 30,
            fab: false,
            realTimeResult : {'emotion':[], 'value':[]},
            ctx : null,
            realTimeIndex : [0],
            realTimeArray : [50],
            chart : null,
            downloadChart : null,
        }
    },
    created(){
        
        this.socket = io('http://j3a308.p.ssafy.io:8000', {transports : ['websocket']})
        // this.socket = io('ws://127.0.0.1:8000', {transports : ['websocket']})
        
        this.socket.on('MESSAGE', (socket) =>{
            
            this.message = socket;
            console.log(this.message);
        });
        
        this.width = 320
        this.height = 240
        
    },
    mounted(){
        this.videodata = document.getElementById('inputVideo');
        this.actionBtn = document.getElementById('actionBtn');
        this.resultBtn = document.getElementById('resultBtn');
        this.initializeBtn = document.getElementById('initializeBtn');

        this.onCvLoaded();
        this.cap = new cv.VideoCapture(this.videodata);

        var ctx = document.getElementById('myChart').getContext("2d");
        this.ctx = ctx;

        this.downloadChart = document.getElementById('myChart').toDataURL('image/png');

        var gradientFill = ctx.createLinearGradient(500, 0, 100, 0);
        gradientFill.addColorStop(0, "rgba(128, 182, 244, 1)");
        gradientFill.addColorStop(1, "rgba(255, 255, 255, 0.6)");

        this.chart = new Chart(ctx, {
        type: 'line',
        chartArea: {
        backgroundColor: 'rgba(0, 0, 0, 255)'
        },
        data: {
            labels : this.realTimeIndex,
            datasets : [
                {
                label : '실시간 결과',
                data : this.realTimeArray,
                borderColor: "#80b6f4",
                pointBorderColor: "#80b6f4",
                pointBackgroundColor: "#80b6f4",
                pointHoverBackgroundColor: "#80b6f4",
                pointHoverBorderColor: "#80b6f4",
                pointBorderWidth: 1,
                pointHoverRadius: 1,
                pointHoverBorderWidth: 1,
                pointRadius: 1,
                fill: true,
                backgroundColor : gradientFill,
                borderWidth: 4,}
            ]
        },
        options: {
            responsive : false,
            title: {
            display: true,
            text: '면접 테스트'
        },
        },
        legend : {
            display : false,
        },
        scales: {
            xAxes: [{
                ticks: {
                    display : false,
                },
                gridLines: {
                    display: false,
                }
            }],
            yAxes: [{
                ticks: {
                    display : false,
                    },
                gridLines: {
                    display: false,
                }
            }],
            }
            });
    },
    computed : {
        ...mapGetters([
            'username',
            'user_pk',
        ])},
    methods :{
        onCvLoaded(){
            cv.onRuntimeInitialized = this.onReady();
        },
        onReady(){

            let resultBtn = this.resultBtn;
            let initializeBtn = this.initializeBtn;

            this.actionBtn.addEventListener('click', ()=>{
                if(this.streaming){
                    this.stop();
                    this.actionBtn.textContent ='Record';
                    resultBtn.style.display = 'inline-block';
                    initializeBtn.style.display = 'inline-block';
                    
                }else{
                    this.Record();
                    this.actionBtn.textContent ='Stop'
                    resultBtn.style.display = 'none';
                    initializeBtn.style.display = 'none';
                }
            });
        },
        initializeChart() {
            let ctx = this.ctx;

            var gradientFill = ctx.createLinearGradient(500, 0, 100, 0);
            gradientFill.addColorStop(0, "rgba(128, 182, 244, 1)");
            gradientFill.addColorStop(1, "rgba(255, 255, 255, 0.6)");

            this.realTimeIndex = [0];
            this.realTimeArray = [50];

            this.chart = new Chart(ctx, {
                type: 'line',
                chartArea: {
                backgroundColor: 'rgba(0, 0, 0, 255)'
                },
                data: {
                    labels : this.realTimeIndex,
                    datasets : [
                        {
                        label : '실시간 결과',
                        data : this.realTimeArray,
                        borderColor: "#80b6f4",
                        pointBorderColor: "#80b6f4",
                        pointBackgroundColor: "#80b6f4",
                        pointHoverBackgroundColor: "#80b6f4",
                        pointHoverBorderColor: "#80b6f4",
                        pointBorderWidth: 1,
                        pointHoverRadius: 1,
                        pointHoverBorderWidth: 1,
                        pointRadius: 1,
                        fill: true,
                        backgroundColor : gradientFill,
                        borderWidth: 4,}
                    ]
                },
                options: {
                    responsive : false,
                    title: {
                    display: true,
                    text: '면접 테스트'
                },
                },
                legend : {
                    display : false,
                },
                scales: {
                    xAxes: [{
                        ticks: {
                            display : false,
                        },
                        gridLines: {
                            display: false,
                        }
                    }],
                    yAxes: [{
                        ticks: {
                            display : false,
                            },
                        gridLines: {
                            display: false,
                        }
                    }],
                    }
                    });
            this.chart.update();
        },
        Record(){
            navigator.mediaDevices.getUserMedia({ video: true, audio: false })
            .then(_stream => {
                this.stream = _stream;
                
                this.videodata.srcObject = this.stream;
                this.videodata.play();
                this.streaming = true;
                this.src = new cv.Mat(this.height, this.width, cv.CV_8UC4);
                this.dst = new cv.Mat(this.height, this.width, cv.CV_8UC1);
                setTimeout(this.processVideo, 0)
            })
            .catch(err => console.log(`An error occurred: ${err}`));
        },
        stop(){
            if (this.videodata) {
                this.videodata.pause();
                this.videodata.srcObject = null;
            }
            if (this.stream) {
                this.stream.getVideoTracks()[0].stop();
            }
            this.streaming = false;
        },
        async processVideo(){
            let uploadToServer = function(socket, data) {
                socket.emit('cvdata', {'data' : data});
            };

            if (!this.streaming) {
            this.src.delete();
            this.dst.delete();
            return;
            }

            const begin = Date.now();
            this.cap.read(this.src);
            cv.cvtColor(this.src, this.dst, cv.COLOR_RGBA2GRAY);
            
            await uploadToServer(this.socket, this.src.data);

            const realTimeResultArray = this.realTimeResult;

            this.socket.on('res', await function(data) {
                let result = JSON.parse(data['data'])['emotion'];
                let mostEmotion = '';
                let mostValue = 0;
                for (var emotion in result) {
                    if (result[emotion] > mostValue) {
                        mostEmotion = emotion;
                        mostValue = result[emotion];
                    }
                }
                realTimeResultArray.emotion.push(mostEmotion);
                realTimeResultArray.value.push(mostValue);
            });

            let lastPoint = this.realTimeArray[this.realTimeArray.length-1];
            this.calculatePoint(this.realTimeResult, lastPoint);
            this.realTimeIndex.push(this.realTimeIndex.length);

            this.chart.update();

            this.downloadChart = document.getElementById('myChart').toDataURL('image/png');

            const delay = 1000/this.FPS - (Date.now() - begin);
            setTimeout(this.processVideo, delay+1000);
        },
        logout(){
            this.$session.destroy();
            this.$router.push("/");
        },
        goHome(){
        this.$router.push('/');
        },
        calculatePoint(result, point) {
            let emotion = result.emotion[result.emotion.length-1];
            switch (emotion) {
                case 'Happy':
                    point += 1.5;
                    break;
                case 'Surpirse':
                    point += 0.5;
                    break;
                case 'Neutral':
                    break;
                case 'Fear':
                    point -= 0.5;
                    break;
                case 'Sad':
                    point -= 1.5;
                    break;
                case 'Angry':
                    point -= 2.5;
                    break;
                default:
                    break;
            };

            if (point > 100) {
                point = 100;
            } else if (point < 0) {
                point = 0;
            };

            this.realTimeArray.push(point);
            return;
        },
        uploadData(result) {
            axios({
            method: "post",
            url : "http://j3a308.p.ssafy.io:8080/api/interviewresult",
            // url: "http://j3a308.p.ssafy.io:8000/api/interviewresult",
            data: {
                user_pk : parseInt(this.user_pk),
                username : this.username,
                image_score : parseFloat(result[result.length-1]),
                image_score_list : `[${String(result)}]`,
                voice_score : 0,
                silent_interval : 'None',
                graph_image_url : 'None',
                feedback : 'None',
                video_length : 0,
                is_live : true,
                filename : 'None',
                test_date : Date.now(),
            },
            }).then(res => {
                console.log(res);
            }).catch(err => console.log(err));
        }
    }
}
</script>

<style scoped>
  a{ color: black; }
  a:link { text-decoration: none; }
  a:visited { text-decoration: none; }
  a:hover { color: blue; }
  a:active { text-decoration: none; }

  .back{
    background-image: url("../../assets/images/background.png");
    background-size: cover;
    height: 100%;
  }

  #myChart{
      margin-left : 50px;
      background-color: white;
  }

  #resultBtn {
      margin-left : 50px;
      display : none;
  }

  #initializeBtn {
      margin-left : 50px;
      display : inline-block;
  }

  #uploadBtn {
      margin-left : 50px;
      display : inline-block;
  }
</style>