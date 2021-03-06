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
        items-per-page="10000"
      >
        <template v-slot:item.edit="{item}">
          <v-icon @click="visualizar(item)">edit</v-icon>
        </template>

        <template v-slot:item.delete="{item}">
          <v-icon @click="modalMotivo(item)">delete</v-icon>
        </template>
      </v-data-table>
    </v-card>

    <!-- Modal cadastro. -->
    <v-dialog v-model="dialogSalvar" persistent max-width="700">
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
            <v-toolbar-title class="headline text-uppercase">Cadastro de material</v-toolbar-title>
          </v-toolbar>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex sm6>
                <v-text-field label="Descrição" v-model="material.descricao" data-cy="descricao"></v-text-field>
              </v-flex>
              <v-flex sm3 data-cy="categoria">
                <v-select
                  :items="['Acabamento', 'Alvenaria', 'Ferragem', 'Tubulação']"
                  label="Categoria"
                  v-model="material.categoria"
                ></v-select>
              </v-flex>
              <v-flex sm3>
                <v-text-field label="Valor de compra" v-model="material.custo" data-cy="custo"></v-text-field>
              </v-flex>
              <v-flex sm4 data-cy="precificacao">
                <v-select
                  :items="nomesGrupoPrecificacao"
                  label="Grupo de precificação"
                  item-value="value"
                  v-model="material.precificacao"
                ></v-select>
              </v-flex>
              <v-flex sm5 data-cy="fornecedor">
                <v-select
                  :items="nomesFornecedores"
                  item-value="value"
                  label="Fornecedor"
                  v-model="material.fornecedor"
                ></v-select>
              </v-flex>
              <v-flex sm3>
                <v-text-field
                  label="Quantidade"
                  v-model="material.quantidade"
                  type="number"
                  data-cy="quantidade"
                ></v-text-field>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="dialogSalvar = false">Cancelar</v-btn>
          <v-btn
            v-if="validarQuantidadeMaterial"
            color="green darken-1"
            text
            @click="salvar()"
            data-cy="confirmar"
          >confirmar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Modal alterar. -->
    <v-dialog v-model="dialogAlterar" persistent max-width="700">
      <v-card>
        <v-card-title class="headline">
          <v-toolbar dark color="deep-orange accent-4">
            <v-toolbar-title class="headline text-uppercase">Alterar de material</v-toolbar-title>
          </v-toolbar>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex sm6>
                <v-text-field label="Descrição" v-model="material.descricao"></v-text-field>
              </v-flex>
              <v-flex sm3>
                <v-select
                  :items="['Acabamento', 'Alvenaria', 'Ferragem', 'Tubulação']"
                  label="Categoria"
                  v-model="material.categoria"
                ></v-select>
              </v-flex>
              <v-flex sm3>
                <v-text-field label="Valor de compra" v-model="material.custo"></v-text-field>
              </v-flex>
              <v-flex sm3>
                <v-select
                  :items="nomesGrupoPrecificacao"
                  label="Grupo de precificação"
                  item-value="value"
                  v-model="material.precificacao"
                ></v-select>
              </v-flex>
              <v-flex sm5>
                <v-select
                  :items="nomesFornecedores"
                  item-value="value"
                  label="Fornecedor"
                  v-model="material.fornecedor"
                ></v-select>
              </v-flex>
              <v-flex sm2>
                <v-text-field label="Quantidade" v-model="material.quantidade" type="number"></v-text-field>
              </v-flex>
              <v-flex sm2>
                <v-select
                  :items="[{value: 'Ativo', text: 'Ativo'}, {value: 'Desativo', text: 'Desativo'}]"
                  label="Status"
                  v-model="material.status"
                  item-value="value"
                ></v-select>
              </v-flex>
              <v-flex sm12 v-if="material.status == 'Ativo'">
                <v-text-field label="Motivo de ativação" v-model="material.motivo"></v-text-field>
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

    <!-- Modal excluir. -->
    <v-dialog v-model="dialogMotivo" persistent max-width="700">
      <v-card>
        <v-card-title class="headline">
          <v-toolbar dark color="deep-orange accent-4">
            <v-toolbar-title class="headline text-uppercase">Excluir de material</v-toolbar-title>
          </v-toolbar>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex sm12>
                <v-text-field label="Motivo da exclusão" v-model="materialExcluir.motivo"></v-text-field>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="dialogMotivo = false">Cancelar</v-btn>
          <v-btn color="green darken-1" text @click="motivoExcluir()">confirmar</v-btn>
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
import { formatteMoney, moneyToFloat } from "../../util/Formatter.js";

export default {
  data() {
    return {
      dialogAlterar: false,
      dialogSalvar: false,
      dialogMotivo: false,
      snackbar: false,
      msg: "",
      search: "",
      headers: [
        {
          text: "Descrição",
          align: "left",
          sortable: true,
          value: "descricao"
        },
        { text: "Categoria", value: "categoria" },
        { text: "Custo", value: "custo" },
        { text: "Valor de venda", value: "valorVenda" },
        { text: "Quantidade", value: "quantidade" },
        { text: "Status", value: "status" },
        { text: "Editar", value: "edit", sortable: false },
        { text: "Excluir", value: "delete", sortable: false }
      ],
      materiais: [],
      fornecedores: [],
      nomesFornecedores: [],
      precificacaoes: [],
      nomesGrupoPrecificacao: [],
      material: {},
      materialExcluir: {}
    };
  },

  methods: {
    visualizar(item) {
      this.limpar();
      this.dialogAlterar = true;
      item.motivo = "";
      this.material = item;
    },

    limpar() {
      this.material = {};
      this.getMateriais();
    },

    async salvar() {
      let myThis = this;
      await axios
        .post("/SalvarMaterial?OPERACAO=SALVAR", qs.stringify(this.material))
        .then(function(response) {
          myThis.msg = response.data.mensagem;
          myThis.snackbar = true;
        })
        .catch(function(error) {
          myThis.msg = error.data.mensagem;
          myThis.snackbar = true;
        });
      this.getMateriais();
      this.limpar();
      this.dialogSalvar = false;
    },

    async alterar() {
      this.material.valorVenda = moneyToFloat(this.material.valorVenda);
      this.material.custo = moneyToFloat(this.material.custo);

      let myThis = this;
      await axios
        .post("/AlterarMaterial?OPERACAO=ALTERAR", qs.stringify(this.material))
        .then(function(response) {
          myThis.msg = response.data.mensagem;
          myThis.snackbar = true;
        })
        .catch(function(error) {
          myThis.msg = error.data.mensagem;
          myThis.snackbar = true;
        });
      this.getMateriais();
      this.limpar();
      this.dialogAlterar = false;
    },

    modalMotivo(item) {
      item.motivo = "";
      this.materialExcluir = item;
      this.dialogMotivo = true;
    },

    async motivoExcluir() {
      this.materialExcluir.valorVenda = moneyToFloat(
        this.materialExcluir.valorVenda
      );
      this.materialExcluir.custo = moneyToFloat(this.materialExcluir.custo);

      await axios.post(
        "/AlterarMaterial?OPERACAO=ALTERAR",
        qs.stringify(this.materialExcluir)
      );
      this.excluir();
    },

    async excluir() {
      let myThis = this;
      await axios
        .post(`/ExcluirMaterial?OPERACAO=EXCLUIR&id=${this.materialExcluir.id}`)
        .then(function(response) {
          myThis.msg = response.data.mensagem;
          myThis.snackbar = true;
        })
        .catch(function(error) {
          myThis.msg = error.data.mensagem;
          myThis.snackbar = true;
        });
      this.getMateriais();
      this.materialExcluir = {};
      this.dialogMotivo = false;
    },

    async getMateriais() {
      let materiais = await axios.get("/ConsultarMaterial?OPERACAO=CONSULTAR");

      materiais.data.entidades.map(material => {
        material.custo = formatteMoney(material.custo);
        material.valorVenda = formatteMoney(material.valorVenda);
      });

      this.materiais = materiais.data.entidades;
      this.getFornecedores();
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
      this.getGruposPrecificacao();
    },

    async getGruposPrecificacao() {
      let precificacaoes = await axios.get(
        "/ConsultarGrupoPrecificacao?OPERACAO=CONSULTAR"
      );

      precificacaoes.data.entidades.forEach(precificacao => {
        this.nomesGrupoPrecificacao.push({
          value: precificacao.id,
          text: precificacao.precificacao
        });
      });
      this.precificacaoes = precificacaoes.data.entidades;
    }
  },

  computed: {
    validarQuantidadeMaterial: function() {
      if (this.material.quantidade > 0) {
        return true;
      }
    }
  },

  created() {
    this.getMateriais();
  },

  components: {
    AdminDrawer,
    AdminFooter,
    AdminToolbar
  }
};
</script>
