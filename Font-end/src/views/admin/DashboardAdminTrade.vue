<template>
  <v-app>
    <!-- Toolbar -->
    <AdminToolbar />

    <!-- Drawer -->
    <AdminDrawer />

    <!-- Conteudo -->
    <v-card flat>
      <v-card-title>
        <span>Vendas</span>
        <v-spacer></v-spacer>
        <v-text-field
          v-model="search"
          append-icon="search"
          label="Pesquisar"
          single-line
          hide-details
          data-cy="pesquisar"
        ></v-text-field>
      </v-card-title>
      <v-data-table
        :headers="headers"
        :items="vendas"
        :search="search"
        :hide-default-footer="true"
        fixed-header
        height="490px"
        items-per-page="10000"
      >
        <template v-slot:item.visualizarVenda="{item}">
          <v-icon @click="visualizarCompra(item)" :data-cy="`venda-${item.id}`">edit</v-icon>
        </template>
      </v-data-table>
    </v-card>

    <!-- Modal lista de itens -->
    <v-dialog v-model="dialog" persistent max-width="700">
      <v-card>
        <v-card-title class="headline">
          <v-toolbar dark color="deep-orange accent-4">
            <v-toolbar-title class="headline text-uppercase">Lista de itens da venda</v-toolbar-title>
          </v-toolbar>
        </v-card-title>
        <v-card-text>
          <v-card-title>
            <v-container grid-list-lg class="ma-0 pa-0">
              <v-layout row wrap>
                <v-flex sm4 data-cy="situacao">
                  <v-select
                    :items="['Em processamento','Aprovado', 'Reprovado', 'Em transporte', 'Entregue']"
                    label="Situação"
                    v-model="venda.situacao"
                  ></v-select>
                </v-flex>
                <v-spacer></v-spacer>
                <v-flex sm8>
                  <v-text-field
                    v-model="searchItems"
                    append-icon="search"
                    label="Pesquisar"
                    single-line
                    hide-details
                  ></v-text-field>
                </v-flex>
              </v-layout>
            </v-container>
          </v-card-title>
          <v-data-table
            :headers="headersItems"
            :items="venda.itens"
            :search="searchItems"
            :hide-default-footer="true"
            fixed-header
            height="300px"
          ></v-data-table>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="dialog = false">Cancelar</v-btn>
          <v-btn color="green darken-1" text @click="alterar()" data-cy="confirmar">confirmar</v-btn>
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
import qs from "querystring";
import { mapState } from "vuex";
import { formatteDate, formatteMoney } from "../../util/Formatter.js";

export default {
  data() {
    return {
      dialog: false,
      search: "",
      searchItems: "",
      situacao: "",
      headers: [
        {
          text: "Data de compra",
          align: "left",
          sortable: true,
          value: "dataCadastro"
        },
        { text: "Situação", value: "situacao" },
        { text: "Valor total", value: "total" },
        { text: "Visualizar", value: "visualizarVenda", sortable: false }
      ],
      vendas: [],
      headersItems: [
        {
          text: "Descrição",
          align: "left",
          sortable: true,
          value: "descricao"
        },
        { text: "Valor", value: "valorVenda" },
        { text: "Quantidade", value: "quantidade" },
        { text: "Valor total", value: "valorTotal" }
      ],
      venda: []
    };
  },

  methods: {
    async listar() {
      let dadosVenda = await axios.get("/ConsultarVenda?OPERACAO=CONSULTAR");

      dadosVenda.data.entidades.map(venda => {
        venda.dataCadastro = formatteDate(venda.dataCadastro);
        venda.total = formatteMoney(venda.total);

        venda.itens.map(item => {
          item.valorTotal = formatteMoney(item.valorTotal);
          item.valorVenda = formatteMoney(item.valorVenda);
        });
      });

      this.vendas = dadosVenda.data.entidades;
    },

    async alterar() {
      await axios
        .post("/AlterarVenda?OPERACAO=ALTERAR", qs.stringify(this.venda))
        .then(function(response) {
          console.log();
        })
        .catch(function(error) {
          console.log(error);
        });
      this.dialog = false;
    },

    visualizarCompra(item) {
      this.dialog = true;
      this.venda = item;
    },

    formatteDate(data) {
      let vetData = data.split(" ");
      let newFormatte = `${vetData[1]} ${vetData[2]} ${vetData[5]}`;
      let d = new Date(newFormatte);
      return [d.getDate(), d.getMonth() + 1, d.getFullYear()]
        .map(n => (n < 10 ? `0${n}` : `${n}`))
        .join("/");
    }
  },

  created() {
    this.listar();
  },

  components: {
    AdminDrawer,
    AdminFooter,
    AdminToolbar
  }
};
</script>
