<template>
  <v-app>
    <!-- Toolbar -->
    <ClientToolbar />

    <!-- Drawer -->
    <ClientDrawer />

    <!-- Conteudo -->
    <v-container grid-list-lg>
      <v-layout wrap>
        <v-flex v-for="(cartao, index) in cartoes" :key="index" v-bind="{[`xs${4}`]: true}">
          <v-card>
            <v-card-text>
              <v-layout wrap>
                <v-flex sm9>
                  <v-text-field label="Número" v-model="cartao.numero" readonly></v-text-field>
                </v-flex>
                <v-flex sm3>
                  <v-text-field label="Bandeira" v-model="cartao.bandeira" readonly></v-text-field>
                </v-flex>
                <v-flex sm9>
                  <v-text-field label="Nome" v-model="cartao.nome" readonly></v-text-field>
                </v-flex>
                <v-flex sm3>
                  <v-text-field label="Código" v-model="cartao.codigo" readonly></v-text-field>
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
            <v-toolbar-title class="headline text-uppercase">Novo cartão</v-toolbar-title>
          </v-toolbar>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex sm12>
                <v-text-field label="Número" v-model="cartao.numero"></v-text-field>
              </v-flex>
              <v-flex sm12>
                <v-text-field label="Nome impresso" v-model="cartao.nome"></v-text-field>
              </v-flex>
              <v-flex sm9>
                <v-select :items="['Bradesco', 'Elo']" label="Bandeira" v-model="cartao.bandeira"></v-select>
              </v-flex>
              <v-flex sm3>
                <v-text-field label="Código" v-model="cartao.codigo"></v-text-field>
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
            <v-toolbar-title class="headline text-uppercase">Alterar cartão</v-toolbar-title>
          </v-toolbar>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex sm12>
                <v-text-field label="Número" v-model="cartao.numero"></v-text-field>
              </v-flex>
              <v-flex sm12>
                <v-text-field label="Nome impresso" v-model="cartao.nome"></v-text-field>
              </v-flex>
              <v-flex sm9>
                <v-select :items="['Bradesco', 'Elo']" label="Bandeira" v-model="cartao.bandeira"></v-select>
              </v-flex>
              <v-flex sm3>
                <v-text-field label="Código" v-model="cartao.codigo"></v-text-field>
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
    idCliente: "",
    cartoes: [],

    cartao: {
      id: 0,
      numero: "",
      nome: "",
      bandeira: "",
      codigo: "",
      usuario: ""
    }
  }),

  methods: {
    visualizar(index) {
      this.dialogAlterar = true;
      this.cartao.id = this.cartoes[index].id;
      this.cartao.nome = this.cartoes[index].nome;
      this.cartao.numero = this.cartoes[index].numero;
      this.cartao.bandeira = this.cartoes[index].bandeira;
      this.cartao.codigo = this.cartoes[index].codigo;
      this.cartao.usuario = this.stateUsuario.id;
    },

    limparCartao() {
      this.cartao.id = "";
      this.cartao.nome = "";
      this.cartao.numero = "";
      this.cartao.bandeira = "";
      this.cartao.codigo = "";
      this.cartao.usuario = "";
    },

    async salvar() {
      this.cartao.cliente = this.stateUsuario.id;
      await axios
        .post("/SalvarCartao?OPERACAO=SALVAR", qs.stringify(this.cartao))
        .then(function(response) {
          console.log(response.data);
        })
        .catch(function(error) {
          console.log(error);
        });
      this.listar();
      this.limparCartao();
      this.dialogSalvar = false;
    },

    async alterar() {
      await axios
        .post("/AlterarCartao?OPERACAO=ALTERAR", qs.stringify(this.cartao))
        .then(function(response) {
          console.log(response.data);
        })
        .catch(function(error) {
          console.log(error);
        });
      this.listar();
      this.limparCartao();
      this.dialogAlterar = false;
    },

    async listar() {
      let dadosCartaoes = await axios.get(
        `/ConsultarCartao?OPERACAO=CONSULTAR&usuario=${this.stateUsuario.id}`
      );

      this.cartoes = [];
      dadosCartaoes.data.entidades.forEach(cartao => {
        if (cartao.status == "Ativo") {
          this.cartoes.push(cartao);
        }
      });
    },

    async excluir(index) {
      await axios
        .post(`/ExcluirCartao?OPERACAO=EXCLUIR&id=${this.cartoes[index].id}`)
        .then(function(response) {
          console.log(response.data);
        })
        .catch(function(error) {
          console.log(error);
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
