<template>
  <v-app-bar app clipped-left dark color="deep-orange accent-4">
    <v-toolbar-title class="headline text-uppercase">
      <span>Materiais</span>
      <span class="font-weight-light">Construção</span>
    </v-toolbar-title>
    <v-spacer></v-spacer>
    <v-spacer></v-spacer>
    <v-spacer></v-spacer>
    <v-spacer></v-spacer>
    <v-spacer></v-spacer>
    <v-spacer></v-spacer>
    <v-layout>
      <v-menu transition="slide-y-transition" bottom offset-y>
        <template v-slot:activator="{ on }">
          <v-btn icon v-on="on">
            <v-icon>person</v-icon>
          </v-btn>
        </template>

        <v-list>
          <v-list-item>
            <v-btn text :to="{name: 'dashboardClientData'}">Perfil</v-btn>
          </v-list-item>
          <v-list-item>
            <v-btn text :to="{name: 'login'}">Sair</v-btn>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-layout>
  </v-app-bar>
</template>