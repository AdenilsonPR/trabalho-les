<template>
  <v-app>
    <AdminToolbar />
    <AdminDrawer />
    <v-card flat>
      <v-card-title>
        <span>Materiais</span>
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
        :items="materiais"
        :search="search"
        :hide-default-footer="true"
        fixed-header
        height="490px"
      >
        <template v-slot:item.edit="{}">
          <v-icon @click="dialog = true">edit</v-icon>
        </template>

        <template v-slot:item.delete="{}">
          <v-icon>delete</v-icon>
        </template>
      </v-data-table>
    </v-card>

    <!-- Modal cadastro de materiais. -->
    <v-dialog v-model="dialog" persistent max-width="625">
      <template v-slot:activator="{ on }">
        <v-btn bottom dark fab fixed right v-on="on" color="deep-orange accent-4">
          <v-icon>add</v-icon>
        </v-btn>
      </template>
      <v-card>
        {{material}}
        <v-card-title class="headline">
          <v-toolbar dark color="deep-orange accent-4">
            <v-toolbar-title class="headline text-uppercase">Cadastro de material</v-toolbar-title>
          </v-toolbar>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex xs12 sm6 md6>
                <v-text-field label="Descrição" v-model="material.descricao"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-select
                  :items="['Contrução', 'Acabamento']"
                  label="Categoria"
                  v-model="material.categoria"
                ></v-select>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-text-field label="Valor de compra" v-model="material.custo"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6>
                <v-select
                  :items="nomesGrupoPrecificacao"
                  label="Grupo de precificação"
                  item-value="value"
                  v-model="material.precificacao"
                ></v-select>
              </v-flex>
              <v-flex xs12 sm6>
                <v-select
                  :items="nomesFornecedores"
                  item-value="value"
                  label="Fornecedor"
                  v-model="material.fornecedor"
                ></v-select>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="dialog = false">Cancelar</v-btn>
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
import qs from "querystring";

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
        { text: "Categorias", value: "categoria" },
        { text: "Custo", value: "custo" },
        { text: "Valor de venda", value: "valorVenda" },
        { text: "Status", value: "status" },
        { text: "Editar", value: "edit", sortable: false },
        { text: "Excluir", value: "delete", sortable: false }
      ],
      materiais: [],
      fornecedores: [],
      nomesFornecedores: [],
      grupoPrecificacao: [],
      nomesGrupoPrecificacao: [],
      material: {}
    };
  },

  methods: {
    async getMateriais() {
      let materiais = await axios.get("/ConsultarMaterial?OPERACAO=CONSULTAR");
      this.materiais = materiais.data.entidades;
    },

    async getFornecedores() {
      let fornecedores = await axios.get(
        "/ConsultarFornecedor?OPERACAO=CONSULTAR"
      );

      fornecedores.data.entidades.forEach(fornecedor => {
        this.nomesFornecedores.push({
          value: fornecedor.id,
          text: fornecedor.nome
        });
      });
      this.fornecedores = fornecedores.data.entidades;
    },

    async getGruposPrecificacao() {
      let grupoPrecificacao = await axios.get(
        "/ConsultarGrupoPrecificacao?OPERACAO=CONSULTAR"
      );

      grupoPrecificacao.data.entidades.forEach(precificacao => {
        this.nomesGrupoPrecificacao.push({
          value: precificacao.id,
          text: precificacao.descricao
        });
      });
      this.grupoPrecificacao = grupoPrecificacao.data.entidades;
    }
  },
  created() {
    this.getMateriais();
    // this.getFornecedores();
    this.getGruposPrecificacao();
  },

  components: {
    AdminDrawer,
    AdminFooter,
    AdminToolbar
  }
};
</script>
