<template>
  <div>
    <v-content>
      <section id="title">
        <v-row no-gutters>
          <v-img
              :min-height="'calc(100vh - ' + $vuetify.application.top + 'px)'"
              src="../../assets/images/background.png"
          >
            <v-theme-provider dark>
              <v-container fill-height>
                <v-row
                    align="center"
                    class="white--text mx-auto"
                    justify="center"
                >
                  <v-col
                      class="white--text text-center"
                      cols="12"
                      tag="h1"
                  >
                  <span
                      :class="[$vuetify.breakpoint.smAndDown ? 'display-1' : 'display-2']"
                      class="font-weight-light"
                  >
                    WELCOME TO
                  </span>

                    <br>

                    <span
                        :class="[$vuetify.breakpoint.smAndDown ? 'display-3': 'display-4']"
                        class="font-weight-black"
                    >
                    Aight
                  </span>

                  </v-col>

                  <v-btn
                      class="align-self-end"
                      fab
                      outlined
                      @click="$vuetify.goTo('#login')"
                  >
                    <v-icon>mdi-chevron-double-down</v-icon>
                  </v-btn>
                </v-row>
              </v-container>
            </v-theme-provider>
          </v-img>
        </v-row>
      </section>

      <section id="login">
        <div class="py-12"></div>

        <v-container class="text-center">
          <h2 class="display-2 font-weight-bold mb-3">WITH US</h2>

          <v-responsive
              class="mx-auto mb-8"
              width="56"
          >
            <v-divider class="mb-1"></v-divider>

            <v-divider></v-divider>
          </v-responsive>

          <v-responsive
              class="mx-auto font-weight-light mb-8"
              max-width="400"
          >
            <v-row style="padding: 0 10px 0 10px;">
              <v-col cols="8" style="padding-bottom: 0px;">
                <v-text-field
                    outlined
                    dense
                    label="ID"
                    append-icon="fas fa-user"
                    v-model="uid"
                    :rules="[rules.uid]"
                ></v-text-field>
                <v-text-field
                    outlined
                    type="password"
                    dense
                    label="PW"
                    append-icon="fas fa-lock"
                    v-model="password"
                    :rules="[rules.password]"
                ></v-text-field>
              </v-col>
              <v-col cols="4" align="center" style="line-height: 100px;">
                <v-btn outlined large @click="onLogin(uid, password)">LogIn</v-btn>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="7" style="line-height: 40px; text-align: left;">
                <span>비밀번호를 잊으셨나요?</span>
              </v-col>

              <v-col cols="5" >
                <v-btn text color="blue darken-3">비밀번호 찾기</v-btn>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="7" style="line-height: 40px; text-align: left;">
                <span>아직 회원이 아닌가요?</span>
              </v-col>
              <v-col cols="5" >
                <v-btn text color="blue darken-3" @click="goJoin()">회원가입</v-btn>
              </v-col>
            </v-row>
          </v-responsive>

          <v-avatar
              size="128"
          >
            <v-img src="../../assets/images/ai.png"></v-img>
          </v-avatar>

        </v-container>

        <div class="py-12"></div>
      </section>

      <section
          id="features"
          class="grey lighten-3"
      >
        <div class="py-12"></div>

        <v-container class="text-center">
          <h2 class="display-2 font-weight-bold mb-3">TEAM. Aight</h2>

          <v-responsive
              class="mx-auto mb-12"
              width="56"
          >

            <v-divider class="mb-1"></v-divider>

            <v-divider></v-divider>
          </v-responsive>

          <v-row>
            <v-col
                v-for="({ profile, title, text }, i) in features"
                :key="i"
                cols="12/5"
            >
              <figure class="snip1384"><img :src="profile"/>
                <figcaption>
                  <h3>{{title}}</h3>
                  <p>{{text}}</p>
                </figcaption>
              </figure>

            </v-col>
          </v-row>
        </v-container>

        <div class="py-12"></div>
      </section>
    </v-content>

    <v-footer
        class="justify-center"
        color="#292929"
        height="100"
    >
      <div class="title font-weight-light grey--text text--lighten-1 text-center">
        &copy; {{ (new Date()).getFullYear() }} — AI Interview — Made with 💜 by Team.Aight
      </div>

    </v-footer>
  </div>
</template>

<script>
// import PV from "password-validator";
// import * as EmailValidator from "email-validator";

export default {
  data: () => {
    return {
      features: [
        {
          profile: 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/331810/sample92.jpg',
          title: '팀장 이원오',
          text: 'Back-end & AWS',
        },
        {
          profile: 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/331810/sample92.jpg',
          title: '팀원 강명훈',
          text: 'Front-End & Back-End',
        },
        {
          profile: 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/331810/sample92.jpg',
          title: '팀원 고민주',
          text: 'Front-end',
        },
        {
          profile: 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/331810/sample92.jpg',
          title: '팀원 박춘화',
          text: 'Back-end & Python',
        },
        {
          profile: 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/331810/sample92.jpg',
          title: '팀원 정희훈',
          text: 'Back-end & UCC',
        },
      ],
      rules: {
        uid: value => !!value || '아이디를 입력해주세요.',
        password: value => !!value || '비밀번호를 입력해주세요.',
      },
      uid: '',
      password: '',
    }
  },

  mounted(){
    
  },

  methods: {
    onLogin(uid, password) {

      let exptext = /^[A-Za-z0-9]+@[A-Za-z0-9]+\.[A-Za-z0-9]+/;
      if(exptext.test(uid)==false){
        const options = {title: 'ID Validation', size: 'sm'}
        this.$dialogs.alert("아이디 형식이 올바르지 않습니다", options)
        
      }
      else if(password=='')
      {
         const options = {title: 'Empty Password', size: 'sm'}
        this.$dialogs.alert("비밀번호를 입력해주세요.", options)
      }
      else {
        console.log(uid, password);

        
        this.$store.dispatch("login", {
          uid : this.uid,
          password : this.password
        })
          .then((res) =>{
            console.log(res);
            const options = {title: 'Login', size: 'sm'}
            this.$dialogs.alert("로그인 성공", options)
            this.$router.push("/main");
          })
          .catch((err) =>{
            console.log(err);
            const options = {title: 'Login', size: 'sm'}
            this.$dialogs.alert("로그인 실패", options)
            
          })
      }
    },
    goJoin() {
      this.$router.push("/join");
    },
  },
}
</script>

<style>
@import url(https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css);
figure.snip1384 {

  font-family: 'Raleway', Arial, sans-serif;
  position: relative;
  overflow: hidden;
  margin: 10px;
  min-width: 180px;
  max-width: 200px;
  width: 100%;
  color: #ffffff;
  text-align: left;
  font-size: 16px;
  background-color: #000000;
}
figure.snip1384 * {
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  -webkit-transition: all 0.35s ease;
  transition: all 0.35s ease;
}
figure.snip1384 img {
  max-width: 100%;
  backface-visibility: hidden;
  vertical-align: top;
}
figure.snip1384:after,
figure.snip1384 figcaption {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
}
figure.snip1384:after {

  content: '';
  background-color: rgba(0, 0, 0, 0.65);
  -webkit-transition: all 0.35s ease;
  transition: all 0.35s ease;
  opacity: 0;
}
figure.snip1384 figcaption {
  z-index: 1;
  padding: 40px;
}
figure.snip1384 h3,
figure.snip1384 .links {
  width: 100%;
  margin: 5px 0;
  padding: 0;
}
figure.snip1384 h3 {
  line-height: 1.1em;
  font-weight: 700;
  font-size: 1.4em;
  text-transform: uppercase;
  opacity: 0;
}
figure.snip1384 p {
  font-size: 0.8em;
  font-weight: 300;
  letter-spacing: 1px;
  opacity: 0;
  top: 50%;
  -webkit-transform: translateY(40px);
  transform: translateY(40px);
}
figure.snip1384 i {
  position: absolute;
  bottom: 10px;
  right: 10px;
  padding: 20px 25px;
  font-size: 34px;
  opacity: 0;
  -webkit-transform: translateX(-10px);
  transform: translateX(-10px);
}
figure.snip1384 a {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 1;
}
figure.snip1384:hover img,
figure.snip1384.hover img {
  zoom: 1;
  filter: alpha(opacity=50);
  -webkit-opacity: 0.5;
  opacity: 0.5;
}
figure.snip1384:hover:after,
figure.snip1384.hover:after {
  opacity: 1;
  position: absolute;
  top: 10px;
  bottom: 10px;
  left: 10px;
  right: 10px;
}
figure.snip1384:hover h3,
figure.snip1384.hover h3,
figure.snip1384:hover p,
figure.snip1384.hover p,
figure.snip1384:hover i,
figure.snip1384.hover i {
  -webkit-transform: translate(0px, 0px);
  transform: translate(0px, 0px);
  opacity: 1;
}

</style>