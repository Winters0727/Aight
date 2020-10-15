<template>
  <div>
    <core-app-bar />

    <core-view />

<!--    <core-footer />-->
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
export default {

  components: {
    CoreAppBar: () => import('@/components/core/AppBar'),
    CoreView: () => import('@/components/core/View'),
  },
}
</script>

<style scoped>

</style>