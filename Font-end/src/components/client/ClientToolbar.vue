<template>
  <v-app-bar app clipped-left dark color="deep-orange accent-4">
    <v-toolbar-title class="headline text-uppercase">
      <span>Materiais</span>
      <span class="font-weight-light">Construção</span>
    </v-toolbar-title>
    <v-spacer></v-spacer>
    <v-btn icon :to="{name: 'dashboardTrade'}">
      <v-icon>home</v-icon>
    </v-btn>
    <v-btn icon @click="sair()" data-cy="sair">
      <v-icon>logout</v-icon>
    </v-btn>
  </v-app-bar>
</template>

<script>
export default {
  methods: {
    sair() {
      localStorage.removeItem("usuario");
      this.$router.push({ name: "login" });
    }
  }
};
</script>
