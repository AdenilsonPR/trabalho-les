
<template>
  <v-app>
    <!-- Toolbar -->
    <ClientToolbar />

    <!-- Drawer -->
    <ClientDrawer />

    <!-- Conteudo -->
    <v-card flat>
      <v-card-title>
        <span>Trocas</span>
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
        :items="trocas"
        :search="search"
        :hide-default-footer="true"
        fixed-header
        height="490px"
      >
        <template v-slot:item.visualizarTroca="{item}">
          <v-icon @click="visualizarTroca(item)">visibility</v-icon>
        </template>
      </v-data-table>
    </v-card>

    <!-- Modal visualizar -->
    <v-dialog v-model="dialog" persistent max-width="625">
      <v-card>
        <v-card-title class="headline">
          <v-toolbar dark color="deep-orange accent-4">
            <v-toolbar-title class="headline text-uppercase">Item</v-toolbar-title>
          </v-toolbar>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex sm10>
                <v-text-field label="Descrição" v-model="troca.descricao" readonly></v-text-field>
              </v-flex>
              <v-flex sm2>
                <v-text-field label="Quantidade" v-model="troca.quantidade" readonly></v-text-field>
              </v-flex>
              <v-flex sm4>
                <v-text-field label="Valor" v-model="troca.valorVenda" readonly></v-text-field>
              </v-flex>
              <v-flex sm4>
                <v-text-field label="Valor total" v-model="troca.valorTotal" readonly></v-text-field>
              </v-flex>
              <v-flex sm4>
                <v-text-field label="Valor total" v-model="troca.situacao" readonly></v-text-field>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="dialog = false" data-cy="cancelar">Cancelar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Footer -->
    <ClientFooter />
  </v-app>
</template>

<script>
import ClientToolbar from "../../components/client/ClientToolbar.vue";
import ClientDrawer from "../../components/client/ClientDrawer.vue";
import ClientFooter from "../../components/client/ClientFooter.vue";
import axios from "axios";
import { mapState } from "vuex";
import { formatteMoney } from "../../util/Formatter.js";

export default {
  data() {
    return {
      dialog: false,
      search: "",
      headers: [
        {
          text: "Descrição",
          align: "left",
          sortable: true,
          value: "descricao"
        },
        { text: "Valor unitário", value: "valorVenda" },
        { text: "Quantidade", value: "quantidade" },
        { text: "Valor total", value: "valorTotal" },
        { text: "Situação", value: "situacao" },
        { text: "Visualizar", value: "visualizarTroca", sortable: false }
      ],
      trocas: [],

      troca: {}
    };
  },

  methods: {
    async listar() {
      let dadosCompra = await axios.get(
        `/ConsultarVenda?OPERACAO=CONSULTAR&usuario=${this.stateUsuario.id}`
      );
      dadosCompra.data.entidades.forEach(venda => {
        this.trocas = this.trocas.concat(
          venda.itens.filter(item => item.papel == "Troca")
        );
      });

      this.trocas.map(troca => {
        troca.valorTotal = formatteMoney(troca.valorTotal);
        troca.valorVenda = formatteMoney(troca.valorVenda);
      });
    },

    visualizarTroca(item) {
      this.troca = item;
      this.dialog = true;
    }
  },

  computed: {
    ...mapState(["stateUsuario"])
  },

  created() {
    this.listar();
  },

  components: {
    ClientToolbar,
    ClientDrawer,
    ClientFooter
  }
};
</script>

