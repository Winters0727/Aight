<template>
  <div class="back">
    <v-app-bar
        color="white"
        fixed

    >
      <v-slide-x-transition>
        <v-img
            src="@/assets/logo.png"
            class="shrink"
            contain
            height="50"
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

    <v-container style="margin-top:80px; margin-bottom: 110px;">
      <v-card>
        <v-card-title>
          <b>공지사항</b>
          <v-spacer></v-spacer>
          <v-text-field
              v-model="search"
              append-icon="mdi-magnify"
              label="Search"
              single-line
              hide-details
          ></v-text-field>
        </v-card-title>
        <v-data-table
            :headers="headers"
            :search="search"
            :items="notices"
        >
          <template #item.title="{ item }">
            <a target="_blank" :href="item.title">
              {{ item.title }}
            </a>
          </template>
        </v-data-table>
      </v-card>
      <div v-if="uid === 'admin@test.com'" align="right">
        <v-btn dense color="primary" style="margin-top: 10px;">공지작성</v-btn>
      </div>
    </v-container>


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
export default {

  data: function(){
    return{
      uid: this.$session.get("user").uid,

      search: '',
      headers: [
        { text: '번호', align: 'start', sortable: true, value: 'num' },
        { text: '제목', align: 'start', sortable: false, value: 'title' },
        { text: '작성일', align: 'center', sortable: false, value: 'date' },
        { text: '작성자', align: 'center', sortable: false, value: 'writer' },
      ],
      notices: [
        {
          num: 1,
          title: '첫 공지',
          date: '2020-09-23',
          writer: 'admin',
        },
      ],
      fab: false,
    }
  },

  methods: {
    logout(){
      this.$session.destroy();
      this.$router.push("/");
    },
    goHome(){
      this.$router.push('/');
    },
  },
}
</script>

<style scoped>
  a{ color: black; }
  a:link { text-decoration: none; }
  a:visited { text-decoration: none; }
  a:hover { color: blue; }
  a:active { text-decoration: none; }

  .back{
    background-image: url("../../assets/images/bbback.jpg");
    background-size: cover;
    height: 100%;
  }
</style>