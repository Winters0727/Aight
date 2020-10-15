<template>
  <div id ="joinarea">
    <div id="inputarea">
      <h1 id="title">회원가입</h1>
        
        <table>
        <tr>
          <td class="td">이메일</td>
          <td class="td">
            <input
              v-model="email"
              id="email"
              placeholder="이메일을 입력해주세요"
              type="text"
              class="input"
            />
          </td>
          <td class="td">
            <v-btn text color="blue darken-3" @click="emailCheck" outlined
              >중복확인</v-btn
            >
          </td>
        </tr>
        <tr>
          <td class="td">비밀번호</td>
          <td class="td">
            <input
              v-model="password"
              id="name"
              placeholder="비밀번호를 입력해주세요"
              type="password"
              class="input"
            />
          </td>
        </tr>
        <tr>
          <td class="td">닉네임</td>
          <td class="td">
            <input
              v-model="nickname"
              id="nickname"
              placeholder="닉네임을 입력해주세요"
              type="text"
              class="input"
            />
          </td>
        </tr>
        <tr>
          <td class="td">이름</td>
          <td class="td">
            <input
              v-model="username"
              id="username"
              placeholder="유저이름을 입력해주세요"
              type="text"
              class="input"
            />
          </td>
        </tr>
        <tr>
          <td class="td">성별</td>
          <td class="td">
            <v-select v-model="el" :items="gender"></v-select>
          </td>
        </tr>
        <tr>
          <td class="td">나이</td>
          <td class="td">
            <input
              v-model="age"
              id="age"
              placeholder="나이를 입력해주세요"
              type="number"
              class="input"
            />
          </td>
        </tr>
      </table>

      
        <v-btn 
        id ="sign_btn"
        text color="blue darken-3" 
        @click="signIn"
        outlined
        >
        작성완료
        </v-btn>
        <v-btn 
        id ="complete_btn"
        text color="blue darken-3" 
        @click="goToPages"
        outlined
        >
        메인 페이지로 돌아가기
        </v-btn>
      
    </div>

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
import axios from "axios";
export default {
  data() {
    return {
      el: "성별",
      gender: ["남성", "여성"],
      email: "",
      password: "",
      nickname: "",
      username: "",
      age: "",
    };
  },
  methods: {
    signIn() {
      if (this.email == "") {
        const options = {size: 'sm'}
        this.$dialogs.alert("이메일 형식 아이디를 입력해주세요.", options)
        return;
      }
      if (this.password == "") {
        const options = {size: 'sm'}
        this.$dialogs.alert("비밀번호를 입력해주세요.", options)
        return;
      }
      if (this.nickname == "") {
        const options = {size: 'sm'}
        this.$dialogs.alert("별명을 입력해주세요.", options)
        return;
      }
      if (this.username == "") {
        const options = {size: 'sm'}
        this.$dialogs.alert("이름을 입력해주세요.", options)
        return;
      }
      if (this.gender == "성별") {
        const options = {size: 'sm'}
        this.$dialogs.alert("성별을 입력해주세요.", options)
        return;
      }
      if (this.age == "") {
        const options = {size: 'sm'}
        this.$dialogs.alert("나이를 입력해주세요.", options)
      } else {
        axios({
          method: "post",
          url: "http://j3a308.p.ssafy.io:8080/api/user/signup",
          data: {
            uid: this.email,
            password: this.password,
            nickname: this.nickname,
            username: this.username,
            is_admin: false,
            is_activated: true,
            auth_date: new Date,
            gender: this.el,
            age: this.age,
          },
        })
          .then((data) => {
            console.dir(data);
            let msg = "회원 가입 중 문제가 발생";
            if (data.data.status) {
              msg = "회원 가입 완료";
            }
            const options = {title: 'Sign Up', size: 'sm'}
            this.$dialogs.alert(msg, options)
          })
          .catch((error) => {
            console.log(error);
          });
        
      }
      this.$router.push("/");
    },

    emailCheck() {
      var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
      axios({
        method: "GET",
        url: "http://j3a308.p.ssafy.io:8080/api/user/emailCheck?email=" + this.email,
      })
        .then((data) => {
          let msg = "이미 가입된 이메일 입니다.";
          console.log(data);
          if (this.email == "") {
            const options = {size: 'sm'}
            this.$dialogs.alert("값을 입력 해주세요.", options)
            return;
          }
          if (this.email.match(regExp) == null) {
            const options = {size: 'sm'}
            this.$dialogs.alert("이메일 형식 아이디를 입력해주세요.", options)
            return;
          }
          if (data.data == false) {
            msg = "사용 가능한 이메일 입니다.";
          }
          const options = {size: 'sm'}
            this.$dialogs.alert(msg, options)
        })
        .catch((error) => {
          console.log(error);
        });
    },
    goToPages() {
      this.$router.push("/");
    },
  },
};
</script>

<style scoped>
.td {
  padding: 20px;
}
#title{
  margin-left: 35%;
}
#inputarea {
  position: absolute;
  margin-left: 35%;
  justify-content: center;
  background-color: white;
  
}
#joinarea {
  background-image: url("../../assets/images/background.png");
  background-size: cover;
  height: 100%;
}
#sign_btn{
  margin-top: 10%;
  margin-left: 20%;
  margin-bottom: 10%;
}
#complete_btn{
  margin-top: 10%;
  margin-left: 10%;
  margin-bottom: 10%;
}
</style>
