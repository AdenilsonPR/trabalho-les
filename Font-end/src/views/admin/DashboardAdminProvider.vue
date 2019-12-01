<template>
  <v-app>
    <AdminToolbar />
    <AdminDrawer />
    <v-card flat>
      <v-card-title>
        <span>Fornecedores</span>
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
        :items="fornecedores"
        :search="search"
        :hide-default-footer="true"
        fixed-header
        height="490px"
        items-per-page="10000"
      >
        <template v-slot:item.edit="{item}">
          <v-icon @click="visualizar(item)">edit</v-icon>
        </template>

        <template v-slot:item.delete="{item}">
          <v-icon @click="excluir(item)">delete</v-icon>
        </template>
      </v-data-table>
    </v-card>

    <!-- Modal salvar -->
    <v-dialog v-model="dialogSalvar" persistent max-width="800">
      <template v-slot:activator="{ on }">
        <v-btn
          bottom
          dark
          fab
          fixed
          right
          v-on="on"
          color="deep-orange accent-4"
          data-cy="adicionar"
        >
          <v-icon>add</v-icon>
        </v-btn>
      </template>
      <v-card>
        <v-card-title class="headline">
          <v-toolbar dark color="deep-orange accent-4">
            <v-toolbar-title class="headline text-uppercase">Cadastro de fonecedor</v-toolbar-title>
          </v-toolbar>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex xs12 sm6 md6>
                <v-text-field label="Nome" v-model="fornecedor.nome" data-cy="nome"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field label="CNPJ" v-model="fornecedor.cnpj" data-cy="cnpj"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3 data-cy="categoria">
                <v-select
                  :items="['Acabamento', 'Alvenaria', 'Ferragem', 'Tubulação']"
                  label="Categoria fornecida"
                  v-model="fornecedor.categoriaFornecida"
                ></v-select>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="dialogSalvar = false">Cancelar</v-btn>
          <v-btn color="green darken-1" text @click="salvar()" data-cy="confirmar">confirmar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Modal alterar -->
    <v-dialog v-model="dialogAlterar" persistent max-width="800">
      <v-card>
        <v-card-title class="headline">
          <v-toolbar dark color="deep-orange accent-4">
            <v-toolbar-title class="headline text-uppercase">Alterar de fonecedor</v-toolbar-title>
          </v-toolbar>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex xs12 sm6 md4>
                <v-text-field label="Nome" v-model="fornecedor.nome"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field label="CNPJ" v-model="fornecedor.cnpj"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-select
                  :items="['Acabamento', 'Alvenaria', 'Ferragem', 'Tubulação']"
                  label="Categoria fornecida"
                  v-model="fornecedor.categoriaFornecida"
                ></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-select :items="['Ativo', 'Desativo']" label="Status" v-model="fornecedor.status"></v-select>
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

    <v-snackbar v-model="snackbar">{{msg}}</v-snackbar>
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
      snackbar: false,
      msg: "",
      search: "",
      headers: [
        {
          text: "Nome",
          align: "left",
          sortable: true,
          value: "nome"
        },
        { text: "CNPJ", value: "cnpj" },
        { text: "Categoria fornecida", value: "categoriaFornecida" },
        { text: "Status", value: "status" },
        { text: "Editar", value: "edit", sortable: false },
        { text: "Desativar", value: "delete", sortable: false }
      ],
      fornecedores: [],

      fornecedor: {}
    };
  },

  methods: {
    visualizar(item) {
      this.limpar();
      this.dialogAlterar = true;
      this.fornecedor = item;
    },

    limpar() {
      this.fornecedor = {};
      this.getFornecedores();
    },

    async salvar() {
      let myThis = this;
      await axios
        .post(
          "/SalvarFornecedor?OPERACAO=SALVAR",
          qs.stringify(this.fornecedor)
        )
        .then(function(response) {
          myThis.msg = response.data.mensagem;
          myThis.snackbar = true;
        })
        .catch(function(error) {
          myThis.msg = error.data.mensagem;
          myThis.snackbar = true;
        });
      this.getFornecedores();
      this.limpar();
      this.dialogSalvar = false;
    },

    async alterar() {
      let myThis = this;
      await axios
        .post(
          "/AlterarFornecedor?OPERACAO=ALTERAR",
          qs.stringify(this.fornecedor)
        )
        .then(function(response) {
          myThis.msg = response.data.mensagem;
          myThis.snackbar = true;
        })
        .catch(function(error) {
          myThis.msg = error.data.mensagem;
          myThis.snackbar = true;
        });
      this.getFornecedores();
      this.limpar();
      this.dialogAlterar = false;
    },

    async excluir(item) {
      let myThis = this;
      await axios
        .post(`/ExcluirFornecedor?OPERACAO=EXCLUIR&id=${item.id}`)
        .then(function(response) {
          myThis.msg = response.data.mensagem;
          myThis.snackbar = true;
        })
        .catch(function(error) {
          myThis.msg = error.data.mensagem;
          myThis.snackbar = true;
        });
      this.getFornecedores();
    },

    async getFornecedores() {
      let fornecedores = await axios.get(
        "/ConsultarFornecedor?OPERACAO=CONSULTAR"
      );
      this.fornecedores = fornecedores.data.entidades;
    }
  },

  created() {
    this.getFornecedores();
  },

  components: {
    AdminDrawer,
    AdminFooter,
    AdminToolbar
  }
};
</script>
