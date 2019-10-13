<template>
  <v-app>
    <AdminToolbar />
    <AdminDrawer />
    <v-card flat>
      <v-card-text>
        <v-sheet>
          <v-sparkline
            :value="value"
            color="rgba(0, 0, 0, .7)"
            height="138"
            padding="24"
            stroke-linecap="round"
            smooth
          >
            <template v-slot:label="item">R${{ item.value }}</template>
          </v-sparkline>
        </v-sheet>
      </v-card-text>

      <v-divider></v-divider>

      <v-card-actions class="justify-center">
        <v-btn block text>Go to Report</v-btn>
      </v-card-actions>
    </v-card>
    <AdminFooter />
  </v-app>
</template>

<style>
html {
  overflow-y: hidden;
  overflow-x: hidden;
}
</style>

<script>
import AdminDrawer from "../../components/admin/AdminDrawer.vue";
import AdminFooter from "../../components/admin/AdminFooter.vue";
import AdminToolbar from "../../components/admin/AdminToolbar.vue";

export default {
  data: () => ({
    value: [423, 446, 675, 510, 590, 610, 760, 423, 446, 675]
  }),
  components: {
    AdminDrawer,
    AdminFooter,
    AdminToolbar
  }
};
</script>
