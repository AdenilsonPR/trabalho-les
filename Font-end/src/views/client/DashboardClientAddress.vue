<template>
  <v-app>
    <!-- Toolbar -->
    <ClientToolbar />

    <!-- Drower -->
    <ClientDrawer />

    <!-- Conteudo -->
    <v-container grid-list-lg>
      <v-layout wrap>
        <v-flex v-for="(card, index) in enderecos" :key="index" v-bind="{[`xs${4}`]: true}">
          <v-card>
            <v-card-text>
              <v-layout wrap>
                <v-flex sm12>
                  <v-text-field label="Logradouro" v-model="card.logradouro" readonly></v-text-field>
                </v-flex>
                <v-flex sm6>
                  <v-text-field label="Nº" v-model="card.numero" readonly></v-text-field>
                </v-flex>
                <v-flex sm6>
                  <v-text-field label="CEP" v-model="card.cep" readonly></v-text-field>
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

          <!-- Modal Excluir -->
          <v-dialog v-model="dialogExcluir" persistent max-width="225">
            <v-card>
              <v-card-text class="pa-0">
                <v-container grid-list-md>
                  <v-layout wrap>
                    <v-flex sm12>
                      <span>
                        <h3>Deseja excluir o endereço?</h3>
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

    <!-- Modal Salvar -->
    <v-dialog v-model="dialogSalvar" persistent max-width="800">
      <template v-slot:activator="{ on }">
        <v-btn bottom dark fab fixed right v-on="on" color="deep-orange accent-4">
          <v-icon>add</v-icon>
        </v-btn>
      </template>
      <v-card>
        <v-card-title class="headline">
          <v-toolbar dark color="deep-orange accent-4">
            <v-toolbar-title class="headline text-uppercase">Novo endereço</v-toolbar-title>
          </v-toolbar>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex xs12 sm6 md2>
                <v-text-field label="CEP" v-model="endereco.cep"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md5>
                <v-text-field label="Logradouro" v-model="endereco.logradouro"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md5>
                <v-text-field label="Bairro" v-model="endereco.bairro"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md5>
                <v-text-field label="Cidade" v-model="endereco.cidade"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-select :items="estados" label="Estado" v-model="endereco.estado"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-select :items="paises" label="País" v-model="endereco.pais"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field label="Nº" v-model="endereco.numero"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-select
                  :items="['Casa', 'Apartamento']"
                  label="Tipo de rêsidencia"
                  v-model="endereco.tipoResidencia"
                ></v-select>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-select
                  :items="['Rua', 'Avenida']"
                  label="Tipo de logradouro"
                  v-model="endereco.tipoLogradouro"
                ></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-select :items="['Sim', 'Não']" label="Cobrança" v-model="endereco.cobranca"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-select :items="['Sim', 'Não']" label="Entrega" v-model="endereco.entrega"></v-select>
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

    <!-- Modal Alterar -->
    <v-dialog v-model="dialogAlterar" persistent max-width="800">
      <v-card>
        <v-card-title class="headline">
          <v-toolbar dark color="deep-orange accent-4">
            <v-toolbar-title class="headline text-uppercase">Editar endereço</v-toolbar-title>
          </v-toolbar>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex xs12 sm6 md2>
                <v-text-field label="CEP" v-model="endereco.cep"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md5>
                <v-text-field label="Logradouro" v-model="endereco.logradouro"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md5>
                <v-text-field label="Bairro" v-model="endereco.bairro"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md5>
                <v-text-field label="Cidade" v-model="endereco.cidade"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-select :items="estados" label="Estado" v-model="endereco.estado"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-select :items="paises" label="País" v-model="endereco.pais"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-text-field label="Nº" v-model="endereco.numero"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-select
                  :items="['Casa', 'Apartamento']"
                  label="Tipo de rêsidencia"
                  v-model="endereco.tipoResidencia"
                ></v-select>
              </v-flex>
              <v-flex xs12 sm6 md3>
                <v-select
                  :items="['Rua', 'Avenida']"
                  label="Tipo de logradouro"
                  v-model="endereco.tipoLogradouro"
                ></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-select :items="['Sim', 'Não']" label="Cobrança" v-model="endereco.cobranca"></v-select>
              </v-flex>
              <v-flex xs12 sm6 md2>
                <v-select :items="['Sim', 'Não']" label="Entrega" v-model="endereco.entrega"></v-select>
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

export default {
  data: () => ({
    dialogSalvar: false,
    dialogAlterar: false,
    dialogExcluir: false,
    snackbar: false,
    msg: "",
    idCliente: "",
    enderecos: [],
    endereco: {
      id: "",
      cep: "",
      logradouro: "",
      bairro: "",
      cidade: "",
      estado: "",
      pais: "",
      numero: "",
      tipoResidencia: "",
      tipoLogradouro: "",
      cobranca: "",
      entrega: "",
      usuario: ""
    },
    estados: ["São Paulo", "Rio de Janeiro", "Minas Gerais"],
    paises: ["Brasil", "Japão", "Estados Unidos", "Canadá"]
  }),

  methods: {
    visualizar(index) {
      this.dialogAlterar = true;
      this.endereco.id = this.enderecos[index].id;
      this.endereco.cep = this.enderecos[index].cep;
      this.endereco.logradouro = this.enderecos[index].logradouro;
      this.endereco.bairro = this.enderecos[index].bairro;
      this.endereco.cidade = this.enderecos[index].cidade;
      this.endereco.estado = this.enderecos[index].estado;
      this.endereco.pais = this.enderecos[index].pais;
      this.endereco.numero = this.enderecos[index].numero;
      this.endereco.tipoResidencia = this.enderecos[index].tipoResidencia;
      this.endereco.tipoLogradouro = this.enderecos[index].tipoLogradouro;
      this.endereco.cobranca = this.enderecos[index].cobranca;
      this.endereco.entrega = this.enderecos[index].entrega;
      this.endereco.usuario = this.stateUsuario.id;
    },

    limparEndereco() {
      this.endereco.id = "";
      this.endereco.cep = "";
      this.endereco.logradouro = "";
      this.endereco.bairro = "";
      this.endereco.cidade = "";
      this.endereco.estado = "";
      this.endereco.pais = "";
      this.endereco.numero = "";
      this.endereco.tipoResidencia = "";
      this.endereco.tipoLogradouro = "";
      this.endereco.cobranca = "";
      this.endereco.entrega = "";
      this.endereco.pessoa = "";
    },

    async salvar() {
      this.endereco.usuario = this.stateUsuario.id;
      let myThis = this;
      await axios
        .post("/SalvarEndereco?OPERACAO=SALVAR", qs.stringify(this.endereco))
        .then(function(response) {
          myThis.msg = response.data.mensagem;
          myThis.snackbar = true;
        })
        .catch(function(error) {
          myThis.msg = response.data.mensagem;
          myThis.snackbar = true;
        });
      this.listar();
      this.limparEndereco();
      this.dialogSalvar = false;
    },

    async alterar() {
      let myThis = this;

      await axios
        .post("/AlterarEndereco?OPERACAO=ALTERAR", qs.stringify(this.endereco))
        .then(function(response) {
          myThis.msg = response.data.mensagem;
          myThis.snackbar = true;
        })
        .catch(function(error) {
          myThis.msg = response.data.mensagem;
          myThis.snackbar = true;
        });
      this.listar();
      this.limparEndereco();
      this.dialogAlterar = false;
    },

    async listar() {
      let dadosEnderecos = await axios.get(
        `/ConsultarEndereco?OPERACAO=CONSULTAR&usuario=${this.stateUsuario.id}`
      );

      this.enderecos = [];
      dadosEnderecos.data.entidades.forEach(endereco => {
        if (endereco.status == "Ativo") {
          this.enderecos.push(endereco);
        }
      });
    },

    async excluir(index) {
      let myThis = this;

      await axios
        .post(
          `/ExcluirEndereco?OPERACAO=EXCLUIR&id=${this.enderecos[index].id}`
        )
        .then(function(response) {
          myThis.msg = response.data.mensagem;
          myThis.snackbar = true;
        })
        .catch(function(error) {
          myThis.msg = response.data.mensagem;
          myThis.snackbar = true;
        });
      this.listar();
      this.dialogExcluir = false;
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
