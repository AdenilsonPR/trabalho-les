<template>
  <v-app>
    <!-- Toolbar -->
    <ClientToolbar />

    <!-- Drawer -->
    <ClientDrawer />

    <!-- Conteudo -->
    <v-card height="565px" flat>
      <v-card-text>
        <v-container grid-list-md pa-10>
          <v-layout wrap>
            <v-flex md6>
              <v-text-field label="Nome" v-model="cliente.nome" readonly></v-text-field>
            </v-flex>
            <v-flex md6></v-flex>
            <v-flex sm2>
              <v-text-field label="CPF" v-model="cliente.cpf" readonly></v-text-field>
            </v-flex>
            <v-flex sm2>
              <v-text-field label="Gênero" v-model="cliente.genero" readonly></v-text-field>
            </v-flex>
            <v-flex sm2>
              <v-text-field label="nascimento" v-model="cliente.nascimento" readonly></v-text-field>
            </v-flex>
            <v-flex md6></v-flex>
            <v-flex sm2>
              <v-text-field label="Tipo telefone" v-model="telefone.tipo" readonly></v-text-field>
            </v-flex>
            <v-flex sm1>
              <v-text-field label="DDD" v-model="telefone.ddd" readonly></v-text-field>
            </v-flex>
            <v-flex sm3>
              <v-text-field label="Telefone" v-model="telefone.numero" readonly></v-text-field>
            </v-flex>
            <v-flex md6></v-flex>
            <v-flex sm6>
              <v-text-field label="E-mail" v-model="login.email" readonly></v-text-field>
            </v-flex>
            <v-flex md6></v-flex>
            <v-flex sm1>
              <v-btn text @click="dialogExcluir = true">
                <span>Excluir</span>
                <v-icon right>delete</v-icon>
              </v-btn>
            </v-flex>
            <v-flex md4></v-flex>
            <v-flex sm1>
              <v-btn text @click="visualizar()">
                <span>Editar</span>
                <v-icon right>edit</v-icon>
              </v-btn>
            </v-flex>
            <v-flex md6></v-flex>
          </v-layout>
        </v-container>
      </v-card-text>
    </v-card>

    <!-- Modal alterar -->
    <v-dialog v-model="dialogAlterar" persistent max-width="565">
      <v-card>
        <v-card-title class="headline">
          <v-toolbar dark color="deep-orange accent-4">
            <v-toolbar-title class="headline text-uppercase">Edição de Dados pessoais</v-toolbar-title>
          </v-toolbar>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex md12>
                <v-text-field label="Nome" v-model="clienteModificado.nome"></v-text-field>
              </v-flex>
              <v-flex sm4>
                <v-select
                  :items="['Celular', 'Residencial']"
                  label="Tipo telefone"
                  v-model="clienteModificado.tipo"
                ></v-select>
              </v-flex>
              <v-flex sm1>
                <v-text-field label="DDD" v-model="clienteModificado.ddd"></v-text-field>
              </v-flex>
              <v-flex sm7>
                <v-text-field label="Telefone" v-model="clienteModificado.numero"></v-text-field>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-btn
            color="green darken-1"
            text
            :to="{name:'changePassword', query:{login: login.id, cliente: cliente.id}}"
          >Alterar senha</v-btn>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="dialogAlterar = false">Cancelar</v-btn>
          <v-btn color="green darken-1" text @click="alterar()">confirmar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Modal excluir -->
    <v-dialog v-model="dialogExcluir" persistent max-width="240">
      <v-card>
        <v-card-text class="pa-0">
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex sm12>
                <span>
                  <h3>Deseja excluir seu cadastro?</h3>
                </span>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-btn color="green darken-1" text @click="dialogExcluir = false">Cancelar</v-btn>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="excluir()">confirmar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <ClientFooter />
  </v-app>
</template>

<style>
html {
  overflow-y: hidden;
  overflow-x: hidden;
}
</style>

<script>
import ClientToolbar from "../../components/client/ClientToolbar.vue";
import ClientDrawer from "../../components/client/ClientDrawer.vue";
import ClientFooter from "../../components/client/ClientFooter.vue";
import axios from "axios";
import qs from "querystring";
import { mapState } from "vuex";

export default {
  data: () => ({
    dialogAlterar: false,
    dialogExcluir: false,
    idCliente: "",
    login: "",
    telefone: "",
    cliente: "",

    clienteModificado: {
      idCliente: "",
      idLogin: "",
      idTelefone: "",
      nome: "",
      tipo: "",
      ddd: "",
      numero: ""
    },
    evento: ""
  }),

  methods: {
    visualizar() {
      this.dialogAlterar = true;
      this.clienteModificado.idUsuario = this.cliente.id;
      this.clienteModificado.nome = this.cliente.nome;

      this.clienteModificado.idTelefone = this.telefone.id;
      this.clienteModificado.tipo = this.telefone.tipo;
      this.clienteModificado.ddd = this.telefone.ddd;
      this.clienteModificado.numero = this.telefone.numero;
    },

    async getCliente() {
      let dadosCliente = await axios.get(
        `/ConsultarUsuario?OPERACAO=CONSULTAR&id=${this.stateUsuario.id}`
      );

      let dadosLogin = await axios.get(
        `/ConsultarLogin?OPERACAO=CONSULTAR&usuario=${this.stateUsuario.id}`
      );

      let dadosTelefone = await axios.get(
        `/ConsultarTelefone?OPERACAO=CONSULTAR&usuario=${this.stateUsuario.id}`
      );

      this.cliente = dadosCliente.data.entidades[0];
      this.login = dadosLogin.data.entidades[0];
      this.telefone = dadosTelefone.data.entidades[0];
    },

    async alterar() {
      await axios.post(
        "/AlterarUsuario?OPERACAO=ALTERAR",
        qs.stringify(this.clienteModificado)
      );
      this.dialogAlterar = false;
      this.getCliente();
    },

    async excluir() {
      await axios
        .post(`/ExcluirUsuario?OPERACAO=EXCLUIR&id=${this.cliente.id}`)
        .then(function(response) {
          console.log(response.data);
        })
        .catch(function(error) {
          console.log(error);
        });

      await axios
        .post(`/ExcluirLogin?OPERACAO=EXCLUIR&id=${this.login.id}`)
        .then(function(response) {
          console.log(response.data);
        })
        .catch(function(error) {
          console.log(error);
        });
      this.dialogExcluir = false;
      this.navigateTo();
    },

    navigateTo() {
      this.$router.push({
        name: "login"
      });
    }
  },

  created() {
    this.getCliente();
  },

  computed: {
    ...mapState(["stateUsuario"])
  },

  components: {
    ClientToolbar,
    ClientDrawer,
    ClientFooter
  }
};
</script>
