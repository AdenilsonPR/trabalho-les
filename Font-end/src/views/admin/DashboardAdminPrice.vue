<template>
  <v-app>
    <AdminToolbar />
    <AdminDrawer />
    <v-card flat>
      <v-card-title>
        <span>Precificações</span>
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
        :items="precificacaoes"
        :search="search"
        :hide-default-footer="true"
        fixed-header
        height="490px"
      >
        <template v-slot:item.edit="{item}">
          <v-icon @click="visualizar(item)">edit</v-icon>
        </template>

        <template v-slot:item.delete="{item}">
          <v-icon @click="excluir(item)">delete</v-icon>
        </template>
      </v-data-table>
    </v-card>

    <!-- Modal excluir -->
    <v-dialog v-model="dialogExcluir" persistent max-width="225">
      <v-card>
        <v-card-text class="pa-0">
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex sm12>
                <span>
                  <h3>Deseja excluir o cartão?</h3>
                </span>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-btn color="green darken-1" text @click="dialogExcluir = false">Cancelar</v-btn>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="excluir(index)">confirmar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Modal salvar -->
    <v-dialog v-model="dialogSalvar" persistent max-width="625">
      <template v-slot:activator="{ on }">
        <v-btn bottom dark fab fixed right v-on="on" color="deep-orange accent-4">
          <v-icon>add</v-icon>
        </v-btn>
      </template>
      <v-card>
        <v-card-title class="headline">
          <v-toolbar dark color="deep-orange accent-4">
            <v-toolbar-title class="headline text-uppercase">Cadastro de precificação</v-toolbar-title>
          </v-toolbar>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex xs12 sm6 md9>
                <v-text-field label="Grupo de precificação" v-model="precificacao.precificacao"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field label="% Lucro" v-model="precificacao.porcentagem"></v-text-field>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="dialogSalvar = false">Cancelar</v-btn>
          <v-btn color="green darken-1" text @click="salvar()">confirmar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Modal alterar -->
    <v-dialog v-model="dialogAlterar" persistent max-width="625">
      <v-card>
        <v-card-title class="headline">
          <v-toolbar dark color="deep-orange accent-4">
            <v-toolbar-title class="headline text-uppercase">Alterar de precificação</v-toolbar-title>
          </v-toolbar>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex xs12 sm6 md7>
                <v-text-field label="Grupo de precificação" v-model="precificacao.precificacao"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field label="% Lucro" v-model="precificacao.porcentagem"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-select
                  :items="['Ativo', 'Desativo']"
                  label="Status"
                  v-model="precificacao.status"
                ></v-select>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="dialogAlterar = false">Cancelar</v-btn>
          <v-btn color="green darken-1" text @click="alterar()">confirmar</v-btn>
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

export default {
  data() {
    return {
      dialogAlterar: false,
      dialogSalvar: false,
      search: "",
      headers: [
        {
          text: "Grupo de precificação",
          align: "left",
          sortable: true,
          value: "precificacao"
        },
        { text: "% Lucro", value: "porcentagem" },
        { text: "Status", value: "status" },
        { text: "Editar", value: "edit", sortable: false },
        { text: "Desativar", value: "delete", sortable: false }
      ],
      precificacaoes: [],
      precificacao: {}
    };
  },

  methods: {
    visualizar(item) {
      this.limpar();
      this.dialogAlterar = true;
      this.precificacao = item;
    },

    limpar() {
      this.precificacao.id = "";
      this.precificacao.precificacao = "";
      this.precificacao.porcentagem = "";
      this.precificacao.status = "";
      this.getPrecificacoes();
    },

    async salvar() {
      await axios.post(
        "/SalvarGrupoPrecificacao?OPERACAO=SALVAR",
        qs.stringify(this.precificacao)
      );
      this.getPrecificacoes();
      this.limpar();
      this.dialogSalvar = false;
    },

    async alterar() {
      await axios.post(
        "/AlterarGrupoPrecificacao?OPERACAO=ALTERAR",
        qs.stringify(this.precificacao)
      );
      this.getPrecificacoes();
      this.limpar();
      this.dialogAlterar = false;
    },

    async excluir(item) {
      await axios.post(
        `/ExcluirGrupoPrecificacao?OPERACAO=EXCLUIR&id=${item.id}`
      );
      this.getPrecificacoes();
    },

    async getPrecificacoes() {
      let precificacaoes = await axios.get(
        "/ConsultarGrupoPrecificacao?OPERACAO=CONSULTAR"
      );
      this.precificacaoes = precificacaoes.data.entidades;
    }
  },

  created() {
    this.getPrecificacoes();
  },

  components: {
    AdminDrawer,
    AdminFooter,
    AdminToolbar
  }
};
</script>
