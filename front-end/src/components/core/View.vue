<template>
  <div>
    <section class="overflow-hidden">
      <v-row no-gutters>
        <v-col
            md="6"
        >
          <v-img
              :src="require('@/assets/images/interview.png')"
              height="100vh"
          />
        </v-col>

        <v-col
            cols="12"
            md="6"
            :pa-5="$vuetify.breakpoint.smAndDown"
        >
          <base-bubble-1
              style="transform: rotate(180deg) translateY(25%)"
          />

          <v-row
              align="center"
              justify="center"
              style="text-align: center;"
          >
            <v-col
                cols="10"
                md="6"
            >
              <base-heading>Interview</base-heading>
              <base-text>
                AI 또는 화상면접을 연습해보고 싶나요?
              </base-text>
              <base-btn class="mt-4" @click="goInterview">
                Let's Live Interview!
              </base-btn>
              <v-spacer/>
              <base-btn class="mt-4" @click="goUpload">
                Let's Video Upload!
              </base-btn>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </section>

    <section class="overflow-hidden">
      <v-row no-gutters>
        <v-col
            cols="12"
            md="6"
            :pa-5="$vuetify.breakpoint.smAndDown"
        >
          <base-bubble-1 style="transform: translate(5%, -5%)" />

          <v-row
              align="center"
              justify="center"
              style="text-align: center;"
          >
            <v-col
                cols="10"
                md="6"
            >
              <base-heading>Report</base-heading>
              <base-text>
                모의면접 결과가 궁금하세요?
              </base-text>
              <base-btn class="mt-4" @click="goResult">
                View Report!
              </base-btn>
            </v-col>
          </v-row>
        </v-col>
        <v-col
            class="hidden-sm-and-down"
            md="6"
        >
          <v-img
              :src="require('@/assets/images/report.jpg')"
              height="100vh"
          />
        </v-col>

      </v-row>
    </section>

    <v-speed-dial fixed bottom right open-on-hover v-model="fab">
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

  </div>
</template>

<script>
  export default {
    name: 'CoreView',

    data: () => ({
      fab: false,
    }),

    methods:{
      goHome(){
        this.$router.push('/');
      },
      goInterview(){
        this.$router.push('/interview');
      },
      goUpload(){
        this.$router.push('/uploadVideo');
      },
      goResult(){
        this.$router.push('/resultpage');
      }
    },

  }
</script>
