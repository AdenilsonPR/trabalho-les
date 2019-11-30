<template>
  <v-app>
    <!-- Toolbar -->
    <ClientToolbar />

    <!-- Drawer -->
    <ClientDrawer />

    <!-- Conteudo -->
    <v-card flat>
      <v-card-title>
        <span>Compras</span>
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
        :items="compras"
        :search="search"
        :hide-default-footer="true"
        fixed-header
        height="490px"
      >
        <template v-slot:item.visualizarCompra="{item}">
          <v-icon @click="visualizarCompra(item)">visibility</v-icon>
        </template>
      </v-data-table>
    </v-card>

    <!-- Modal compra -->
    <v-dialog v-model="dialogCompra" persistent max-width="700">
      <v-card>
        <v-card-title class="headline">
          <v-toolbar dark color="deep-orange accent-4">
            <v-toolbar-title class="headline text-uppercase">Lista de itens da compra</v-toolbar-title>
          </v-toolbar>
        </v-card-title>
        <v-card-text>
          <v-card-title>
            <v-text-field
              v-model="searchItems"
              append-icon="search"
              label="Pesquisar"
              single-line
              hide-details
            ></v-text-field>
          </v-card-title>
          <v-data-table
            :headers="headersItensCompra"
            :items="visualizarItensCompra"
            :search="searchItems"
            :hide-default-footer="true"
            fixed-header
            height="300px"
          >
            <template v-slot:item.visualizarItem="{item}">
              <v-icon @click="visualizarItem(item)" data-cy="visualizar">visibility</v-icon>
            </template>
          </v-data-table>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="green darken-1"
            text
            @click="dialogCompra = false"
            data-cy="cancelar"
          >Cancelar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Modal item comprado -->
    <v-dialog v-model="dialogItem" persistent max-width="625">
      <v-card>
        <v-card-title class="headline">
          <v-toolbar dark color="deep-orange accent-4">
            <v-toolbar-title class="headline text-uppercase">Item comprado</v-toolbar-title>
          </v-toolbar>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex sm10>
                <v-text-field label="Descrição" v-model="item.descricao" readonly></v-text-field>
              </v-flex>
              <v-flex sm2>
                <v-text-field label="Quantidade" v-model="item.quantidade" readonly></v-text-field>
              </v-flex>
              <v-flex sm6>
                <v-text-field label="Valor" v-model="item.valorVenda" readonly></v-text-field>
              </v-flex>
              <v-flex sm6>
                <v-text-field label="Valor total" v-model="item.valorTotal" readonly></v-text-field>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-btn
            color="green darken-1"
            text
            @click="dialogTroca = true, dialogItem = false"
            v-if="item.situacao == 'Entregue'"
            data-cy="trocar"
          >Trocar</v-btn>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="dialogItem = false">Cancelar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Modal troca -->
    <v-dialog v-model="dialogTroca" persistent max-width="625">
      <v-card>
        <v-card-title class="headline">
          <v-toolbar dark color="deep-orange accent-4">
            <v-toolbar-title class="headline text-uppercase">Troca</v-toolbar-title>
          </v-toolbar>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex sm12 data-cy="motivo">
                <v-select
                  :items="['Defeito', 'Insatisfação']"
                  label="Motivo da troca"
                  v-model="motivo"
                ></v-select>
              </v-flex>
              <v-flex sm12>
                <v-textarea label="Descrição" rows="3" v-model="descricao" data-cy="descricao"></v-textarea>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="dialogTroca = false">Cancelar</v-btn>
          <v-btn color="green darken-1" text @click="trocar()" data-cy="confirmar">confirmar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Footer -->
    <ClientFooter />

    <v-snackbar v-model="snackbar">{{msg}}</v-snackbar>
  </v-app>
</template>

<script>
import ClientToolbar from "../../components/client/ClientToolbar.vue";
import ClientDrawer from "../../components/client/ClientDrawer.vue";
import ClientFooter from "../../components/client/ClientFooter.vue";
import axios from "axios";
import qs from "querystring";
import { mapState } from "vuex";
import {
  formatteDate,
  formatteMoney,
  moneyToFloat
} from "../../util/Formatter.js";

export default {
  data() {
    return {
      dialogCompra: false,
      dialogItem: false,
      dialogTroca: false,
      snackbar: false,
      msg: "",
      search: "",
      searchItems: "",
      descricao: "",
      motivo: "",
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
        { text: "Visualizar", value: "visualizarCompra", sortable: false }
      ],
      compras: [],
      headersItensCompra: [
        {
          text: "Descrição",
          align: "left",
          sortable: true,
          value: "descricao"
        },
        { text: "Valor", value: "valorVenda" },
        { text: "Quantidade", value: "quantidade" },
        { text: "Valor total", value: "valorTotal" },
        { text: "Visualizar", value: "visualizarItem", sortable: false }
      ],
      visualizarItensCompra: [],
      item: {}
    };
  },

  methods: {
    async listar() {
      let dadosCompra = await axios.get(
        `/ConsultarVenda?OPERACAO=CONSULTAR&usuario=${this.stateUsuario.id}`
      );

      dadosCompra.data.entidades.map(compra => {
        compra.dataCadastro = formatteDate(compra.dataCadastro);
        compra.total = formatteMoney(compra.total);

        compra.itens.map(item => {
          item.valorTotal = formatteMoney(item.valorTotal);
          item.valorVenda = formatteMoney(item.valorVenda);
        });
      });

      this.compras = dadosCompra.data.entidades;
    },

    async trocar() {
      this.item.valorVenda = moneyToFloat(this.item.valorVenda);
      this.item.valorTotal = moneyToFloat(this.item.valorTotal);
      this.item.situacao = "Em troca";
      this.item.papel = "Troca";
      this.item.motivo = this.motivo;
      this.item.descricaoMotivo = this.descricao;

      let myThis = this;

      await axios
        .post("/AlterarItem?OPERACAO=ALTERAR", qs.stringify(this.item))
        .then(function(response) {
          myThis.msg = response.data.mensagem;
          myThis.snackbar = true;
        })
        .catch(function(error) {
          myThis.msg = response.data.mensagem;
          myThis.snackbar = true;
        });
      this.dialogTroca = false;
      this.descricao = "";
      this.motivo = "";
      this.listar();
    },

    visualizarCompra(compra) {
      this.situacao = compra.situacao;
      this.visualizarItensCompra = compra.itens;
      this.dialogCompra = true;
    },

    visualizarItem(item) {
      item.situacao = this.situacao;
      this.item = item;
      this.dialogItem = true;
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
