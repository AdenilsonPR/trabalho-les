<template>
  <v-app>
    <!-- Toolbar -->
    <AdminToolbar />

    <!-- Drawer -->
    <AdminDrawer />

    <!-- Conteudo -->
    <v-container grid-list-lg>
      <v-layout wrap>
        <v-flex v-for="(cupom, index) in cupons" :key="index" v-bind="{[`xs${4}`]: true}">
          <v-card>
            <v-card-text>
              <v-layout wrap>
                <v-flex sm6>
                  <v-text-field label="Validade" v-model="cupom.validade" readonly></v-text-field>
                </v-flex>
                <v-flex sm6>
                  <v-text-field label="Valor" v-model="cupom.valor" readonly></v-text-field>
                </v-flex>
                <v-flex sm12>
                  <v-text-field label="Código" v-model="cupom.codigo" readonly></v-text-field>
                </v-flex>
              </v-layout>
            </v-card-text>

            <v-card-actions class="deep-orange accent-4 pa-0">
              <v-btn icon dark>
                <v-icon @click="dialogExcluir = true">delete</v-icon>
              </v-btn>
              <v-spacer></v-spacer>
              <v-btn icon dark>
                <v-icon @click="visualizar(index)">edit</v-icon>
              </v-btn>
            </v-card-actions>
          </v-card>

          <!-- Modal excluir -->
          <v-dialog v-model="dialogExcluir" persistent max-width="225">
            <v-card>
              <v-card-text class="pa-0">
                <v-container grid-list-md>
                  <v-layout wrap>
                    <v-flex sm12>
                      <span>
                        <h3>Deseja excluir o cupom?</h3>
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
        </v-flex>
      </v-layout>
    </v-container>

    <!-- Modal salvar -->
    <v-dialog v-model="dialogSalvar" persistent max-width="500">
      <template v-slot:activator="{ on }">
        <v-btn bottom dark fab fixed right v-on="on" color="deep-orange accent-4">
          <v-icon>add</v-icon>
        </v-btn>
      </template>
      <v-card>
        <v-card-title class="headline">
          <v-toolbar dark color="deep-orange accent-4">
            <v-toolbar-title class="headline text-uppercase">Novo cupom</v-toolbar-title>
          </v-toolbar>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex sm6>
                <v-text-field label="Validade" v-model="cupom.validade" type="date"></v-text-field>
              </v-flex>
              <v-flex sm6>
                <v-text-field label="Valor" v-model="cupom.valor"></v-text-field>
              </v-flex>
              <v-flex sm12>
                <v-text-field label="Código" v-model="cupom.codigo"></v-text-field>
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
    <v-dialog v-model="dialogAlterar" persistent max-width="500">
      <v-card>
        <v-card-title class="headline">
          <v-toolbar dark color="deep-orange accent-4">
            <v-toolbar-title class="headline text-uppercase">Alterar cupom</v-toolbar-title>
          </v-toolbar>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex sm6>
                <v-text-field label="Validade" v-model="cupom.validade" type="date"></v-text-field>
              </v-flex>
              <v-flex sm6>
                <v-text-field label="Valor" v-model="cupom.valor"></v-text-field>
              </v-flex>
              <v-flex sm12>
                <v-text-field label="Código" v-model="cupom.codigo"></v-text-field>
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

    <!-- Footer -->
    <AdminFooter />

    <v-snackbar v-model="snackbar">{{msg}}</v-snackbar>
  </v-app>
</template>

<script>
import AdminToolbar from "../../components/admin/AdminToolbar.vue";
import AdminDrawer from "../../components/admin/AdminDrawer.vue";
import AdminFooter from "../../components/admin/AdminFooter.vue";
import axios from "axios";
import qs from "querystring";

export default {
  data: () => ({
    dialogSalvar: false,
    dialogAlterar: false,
    dialogExcluir: false,
    snackbar: false,
    cupons: [],
    msg: "",
    cupom: {}
  }),

  methods: {
    visualizar(index) {
      this.dialogAlterar = true;
      this.cupom.id = this.cupons[index].id;
      this.cupom.codigo = this.cupons[index].codigo;
      this.cupom.valor = this.cupons[index].valor;
      this.cupom.validade = this.cupons[index].validade;
    },

    limparCupom() {
      this.cupom.id = "";
      this.cupom.codigo = "";
      this.cupom.valor = "";
      this.cupom.validade = "";
    },

    async salvar() {
      let myThis = this;
      await axios
        .post(
          "/SalvarCupomPromocional?OPERACAO=SALVAR",
          qs.stringify(this.cupom)
        )
        .then(function(response) {
          myThis.msg = response.data.mensagem;
          myThis.snackbar = true;
        })
        .catch(function(error) {
          myThis.msg = error.data.mensagem;
          myThis.snackbar = true;
        });
      this.dialogSalvar = false;
      this.listar();
    },

    async alterar() {
      let myThis = this;
      await axios
        .post(
          "/AlterarCupomPromocional?OPERACAO=ALTERAR",
          qs.stringify(this.cupom)
        )
        .then(function(response) {
          myThis.msg = response.data.mensagem;
          myThis.snackbar = true;
        })
        .catch(function(error) {
          myThis.msg = error.data.mensagem;
          myThis.snackbar = true;
        });

      this.limparCupom();
      this.dialogAlterar = false;
      this.listar();
    },

    async listar() {
      let dadosCupons = await axios.get(
        "/ConsultarCupomPromocional?OPERACAO=CONSULTAR"
      );
      this.cupons = dadosCupons.data.entidades.filter(
        cupom => cupom.status == "Ativo"
      );
    },

    async excluir(index) {
      console.log(this.cupons);
      let myThis = this;
      await axios
        .post(
          `/ExcluirCupomPromocional?OPERACAO=EXCLUIR&id=${this.cupons[index].id}`
        )
        .then(function(response) {
          myThis.msg = response.data.mensagem;
          myThis.snackbar = true;
        })
        .catch(function(error) {
          myThis.msg = error.data.mensagem;
          myThis.snackbar = true;
        });
      this.dialogExcluir = false;
      this.listar();
    }
  },

  created() {
    this.listar();
  },

  components: {
    AdminToolbar,
    AdminDrawer,
    AdminFooter
  }
};
</script>
