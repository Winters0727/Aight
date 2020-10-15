<template>
  <v-app-bar
    v-scroll="onScroll"
    :color="!isScrolling ? 'transparent' : 'white'"
    fixed
    flat
  >
    <v-slide-x-transition>
      <v-img
        v-if="showLogo"
        :src="require('@/assets/images/ProjectLogo.png')"
        class="shrink"
        contain
        height="50"
      />
    </v-slide-x-transition>

    <v-spacer />

   {{username}}님
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
</template>

<script>

  import { mapGetters } from 'vuex'

  export default {
    name: 'CoreAppBar',

    data: () => ({
      showLogo: false,
      isScrolling: false,
    }),

    computed:{
      ...mapGetters([
            'username',
        ])
    },

    methods: {
      onScroll () {
        const offset = window.pageYOffset
        this.isScrolling = offset > 50
        this.showLogo = offset > 200
      },
      logout(){
        this.$store.dispatch('logout');

        const options = {size: 'sm'}
        this.$dialogs.alert("로그아웃 됐습니다..", options)
        this.$router.push("/");
      },
    },
  }
</script>
