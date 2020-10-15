<template>
  <div class = "back">
        <v-app-bar
        color="white"
        fixed
        >   
        <v-slide-x-transition>
            <v-img
                src="@/assets/images/Temp_Logo2.jpg"
                class="shrink"
                contain
                height="50"
                style="left"
            />
        </v-slide-x-transition>

        <v-spacer />

        <v-btn
            dense
            dark
            color="secondary"
            small
            style="margin-left:5px;"
            @click="logout"
        >
            로그아웃
        </v-btn>
    </v-app-bar>

        <v-main>
           <section class="text-center" id="result">
                <!-- <v-app class="back" id="inspire"> -->
                <v-card
                class="mx-auto"
                max-width="640"
                >
                <v-card-text>
                    <p class="display-1 text-h3 text--primary">
                    {{username}}님의 면접결과
                    </p>
                    <div class="text--primary">
                        <canvas id="myChart" style="display:inline" :width=width :height=height></canvas>
                    </div>
                </v-card-text>
                <v-card-actions>
                </v-card-actions>
                </v-card>
            <!-- </v-app> -->
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

<script>
import Chart from 'chart.js'
import { mapGetters } from 'vuex'
import axios from 'axios'

export default {
    name : 'resultPage',
    data() {
        return {
            data : null,
            interviewResult : null,
            ctx : null,
            chart : null,
            fab : false,
            width : 480,
            height : 360,
        }
    },
    computed : {
         ...mapGetters([
            'username',
        ])
    },
    created() {
        axios({
            method : "get",
            url : `http://j3a308.p.ssafy.io:8080/api/interviewresult/select/username/${this.username}`,
        }).then(
            (res) => {
                this.data = res.data;
                var ctx = document.getElementById('myChart').getContext("2d");
                this.ctx = ctx;

                var gradientFill = ctx.createLinearGradient(500, 0, 100, 0);
                gradientFill.addColorStop(0, "rgba(128, 182, 244, 1)");
                gradientFill.addColorStop(1, "rgba(255, 255, 255, 0.6)");

                const data = this.data.map(val => val.image_score_list.slice(1,val.image_score_list.length-1).split(',').map(a => parseFloat(a)));

                let maxLength = 0;

                for (var i=0; i < data.length; i++) {
                    if (maxLength < data[i].length) {
                        maxLength = data[i].length
                    }
                }

                let labels = new Array();

                for (var j=0; j < maxLength; j++) {
                    labels.push(j);
                }

                this.chart = new Chart(ctx, {
                type: 'line',
                chartArea: {
                backgroundColor: 'rgba(0, 0, 0, 255)'
                },
                data: {
                    labels : labels,
                    datasets : data.map((val, index) => {
                        const color = `#${Math.floor(Math.random()*16777215).toString(16)}`;
                        return {
                            label : `${index+1}번째`,
                            data : val,
                            borderColor: color,
                            pointBorderColor: color,
                            pointBackgroundColor: color,
                            pointHoverBackgroundColor: color,
                            pointHoverBorderColor: color,
                            pointBorderWidth: 1,
                            pointHoverRadius: 1,
                            pointHoverBorderWidth: 1,
                            pointRadius: 1,
                            fill: false,
                            borderWidth: 4
                        }
                    })
                        },
                        options: {
                    responsive : false,
                    title: {
                    display: true,
                    text: '테스트 결과',
                    scales: {
                    yAxes: [{
                        ticks: {
                            maxTicksLimit: 10
                             },
                            }],
                    xAxes: [{
                        ticks: {
                            beginAtZero : true,
                            stepSize: 40
                              },
                            }],
                        }
                    },
                },
            });
                this.chart.update();
                }
        ).catch(err => console.log(err));

    },
    mounted() {

    },
    methods : {
        logout(){
            this.$session.destroy();
            this.$router.push("/");
        },
        goHome(){
        this.$router.push('/');
        },
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

  #result{
      margin-top : 10%;
  }

  #myChart{
      margin-left : auto;
      margin-right : auto;
      background-color: white;
  }
</style>