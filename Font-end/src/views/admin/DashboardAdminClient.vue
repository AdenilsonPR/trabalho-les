<template>
  <v-app>
    <AdminToolbar />
    <AdminDrawer />
    <v-card flat>
      <v-card-title>
        <span>Clientes</span>
        <v-spacer></v-spacer>
        <v-text-field
          v-model="search"
          append-icon="search"
          label="Pesquisar"
          single-line
          hide-details
        ></v-text-field>
      </v-card-title>
      <v-data-table
        :headers="headers"
        :items="clientes"
        :search="search"
        :hide-default-footer="true"
        items-per-page="1000000"
        fixed-header
        height="490px"
      >
        <template v-slot:item.action="{ item }">
          <v-icon @click="visualizarCliente(item)">visibility</v-icon>
        </template>
      </v-data-table>
    </v-card>

    <v-dialog v-model="dialog" persistent max-width="600">
      <v-card>
        <v-card-title class="headline">
          <v-toolbar dark color="deep-orange accent-4">
            <v-toolbar-title class="headline text-uppercase">Visualização de cliente</v-toolbar-title>
          </v-toolbar>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex xs12 sm6 md8>
                <v-text-field label="Nome" v-model="cliente.nome" readonly></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-text-field label="CPF" v-model="cliente.cpf" readonly></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field label="Gênero" v-model="cliente.genero" readonly></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field label="Nascimento" v-model="cliente.nascimento" readonly></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field label="Rank" v-model="cliente.rank" readonly></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field label="Status" v-model="cliente.status" readonly></v-text-field>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="dialog = false">confirmar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

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
import axios from "axios";

export default {
  data() {
    return {
      dialog: false,
      search: "",
      headers: [
        {
          text: "Nome",
          align: "left",
          sortable: true,
          value: "nome"
        },
        { text: "CPF", value: "cpf" },
        { text: "Gênero", value: "genero" },
        { text: "Nascimento", value: "nascimento" },
        { text: "Rank", value: "rank" },
        { text: "Status", value: "status" },
        { text: "Visualizar", value: "action", sortable: false }
      ],
      clientes: [],
      index: -1,
      cliente: {
        nome: "",
        cpf: "",
        genero: "",
        nascimento: "",
        rank: "",
        status: ""
      }
    };
  },
  methods: {
    async getCliente() {
      let dataCliente = await axios.get("/ConsultarCliente?OPERACAO=CONSULTAR");
      this.clientes = dataCliente.data;
    },

    visualizarCliente(cliente) {
      this.index = this.clientes.indexOf(cliente);
      this.cliente = Object.assign({}, cliente);
      this.dialog = true;
    }
  },
  created() {
    this.getCliente();
  },

  components: {
    AdminDrawer,
    AdminFooter,
    AdminToolbar
  }
};
</script>
