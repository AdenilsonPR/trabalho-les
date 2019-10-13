<template>
  <v-navigation-drawer app clipped>
    <v-list>
      <v-list-item>
        <v-list-item-content></v-list-item-content>
      </v-list-item>

      <v-list-item @click="{}">
        <v-list-item-action>
          <v-icon>layers</v-icon>
        </v-list-item-action>
        <v-list-item-content>
          <v-list-item-title>Acabamento</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list-item @click="{}">
        <v-list-item-action>
          <v-icon>home</v-icon>
        </v-list-item-action>
        <v-list-item-content>
          <v-list-item-title>Alvenaria</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list-item @click="{}">
        <v-list-item-action>
          <v-icon>dehaze</v-icon>
        </v-list-item-action>
        <v-list-item-content>
          <v-list-item-title>Ferragem</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list-item @click="{}">
        <v-list-item-action>
          <v-icon>drag_handle</v-icon>
        </v-list-item-action>
        <v-list-item-content>
          <v-list-item-title>Tubulação</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
export default {
  methods: {
    navigateTo(where) {
      this.$router.push({ name: where });
    }
  }
};
</script>
