<template>
  <v-container fill-height="fill-height">
    <v-layout align-center="align-center" justify-center="justify-center">
      <v-flex xs12 sm8 md4>
        <div class="headline text-uppercase mb-7">
          <span class="display-1 ml-3">Materiais</span>
          <span class="font-weight-light display-1">Construção</span>
        </div>

        <v-card v-if="dadosPessoais">
          <v-toolbar dark color="deep-orange accent-4" flat>
            <v-toolbar-title>Dados pessoais</v-toolbar-title>
            <v-spacer></v-spacer>
          </v-toolbar>
          <v-card-text>
            <form>
              <v-container grid-list-md>
                <v-layout wrap>
                  <v-flex sm12>
                    <v-text-field
                      label="Nome"
                      v-model="cliente.nome"
                      maxlength="30"
                      :rules="regrasNome"
                    ></v-text-field>
                  </v-flex>
                  <v-flex sm6>
                    <v-text-field label="CPF" v-model="cliente.cpf">></v-text-field>
                  </v-flex>
                  <v-flex sm6>
                    <v-select
                      :items="['Feminino', 'Masculino']"
                      label="Gênero"
                      :rules="regrasGenero"
                      v-model="cliente.genero"
                    ></v-select>
                  </v-flex>
                  <v-flex sm12>
                    <v-text-field
                      label="Nascimento"
                      v-model="cliente.nascimento"
                      :rules="regrasNascimento"
                    ></v-text-field>
                  </v-flex>
                </v-layout>
              </v-container>
            </form>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              color="deep-orange accent-4"
              @click="dadosPessoais = false, dadosTelefone = true"
              v-bind:disabled="!validarDadosPessoais"
              v-bind:dark="validarDadosPessoais"
            >Continuar</v-btn>
          </v-card-actions>
        </v-card>

        <v-card v-if="dadosTelefone">
          <v-toolbar dark color="deep-orange accent-4" flat>
            <v-toolbar-title>Dados do telefone</v-toolbar-title>
            <v-spacer></v-spacer>
          </v-toolbar>
          <v-card-text>
            <form>
              <v-container grid-list-md>
                <v-layout wrap>
                  <v-flex sm12>
                    <v-select
                      :items="['Celular', 'Residencial']"
                      label="Tipo"
                      v-model="cliente.tipo"
                      :rules="regrasTipo"
                    ></v-select>
                  </v-flex>
                  <v-flex sm12>
                    <v-text-field label="DDD" v-model="cliente.ddd" maxlength="2"></v-text-field>
                  </v-flex>
                  <v-flex sm12>
                    <v-text-field label="Número" v-model="cliente.numero"></v-text-field>
                  </v-flex>
                </v-layout>
              </v-container>
            </form>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn dark color="primary" @click="dadosTelefone = false, dadosPessoais = true">Voltar</v-btn>
            <v-btn
              color="deep-orange accent-4"
              @click="dadosTelefone = false, dadosLogin = true"
              v-bind:disabled="!validarDadosTelefone"
              v-bind:dark="validarDadosTelefone"
            >Continuar</v-btn>
          </v-card-actions>
        </v-card>

        <v-card v-if="dadosLogin">
          <v-toolbar dark color="deep-orange accent-4" flat>
            <v-toolbar-title>Dados do login</v-toolbar-title>
            <v-spacer></v-spacer>
          </v-toolbar>
          <v-card-text>
            <form>
              <v-container grid-list-md>
                <v-layout wrap>
                  <v-flex sm12>
                    <v-text-field
                      label="E-mail"
                      name="email"
                      type="text"
                      maxlength="30"
                      v-model="cliente.email"
                      :rules="regrasEmail"
                    ></v-text-field>
                  </v-flex>
                  <v-flex sm12>
                    <v-text-field
                      label="Senha"
                      maxlength="16"
                      v-model="cliente.senha"
                      :append-icon="show ? 'visibility' : 'visibility_off'"
                      @click:append="show = !show"
                      :type="show ? 'password' : 'text'"
                      class="input-group--focused"
                    ></v-text-field>
                  </v-flex>
                  <v-flex sm12></v-flex>
                </v-layout>
              </v-container>
            </form>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn dark color="primary" @click="dadosLogin = false, dadosTelefone = true">Voltar</v-btn>
            <v-btn
              color="deep-orange accent-4"
              @click="salvar()"
              v-bind:disabled="!validarDadosLogin"
              v-bind:dark="validarDadosLogin"
              :to="{name: rota}"
            >Confirmar</v-btn>
          </v-card-actions>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import axios from "axios";
import qs from "querystring";

export default {
  data: () => ({
    show: false,
    dadosPessoais: true,
    dadosTelefone: false,
    dadosLogin: false,
    msg: "",

    cliente: {
      nome: "",
      cpf: "",
      genero: "",
      tipo: "",
      ddd: "",
      numero: "",
      nascimento: "",
      email: "",
      senha: "",
      confirmarSenha: "",
      cliente: ""
    },

    regrasNome: [v => !!v || "Nome obrigatório."],
    regrasGenero: [v => !!v || "Gênero obrigatório."],
    regrasNascimento: [v => !!v || "Data de nascimento obrigatória."],
    // regrasCpf: [v => !!v || "CPF obrigatório."],

    regrasTelefone: [v => !!v || "Telefone obrigatório."],
    // regrasDdd: [v => !!v || "DDD obrigatório."],
    // regrasTipo: [v => !!v || "Tipo do telefone obrigatório."],

    regrasEmail: [v => !!v || "E-mail obrigatório."]
    // regrasSenha: [v => !!v || "Senha obrigatória."],
    // regrasConfirmacaoSenha: [v => !!v || "Confirmação de senha obrigatório."]
  }),

  methods: {
    async salvar() {
      await axios
        .post("/SalvarUsuario?OPERACAO=SALVAR", qs.stringify(this.cliente))
        .then(response => {
          this.msg = response.data;
          this.navigateTo();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    navigateTo() {
      this.$router.push({
        name: "login",
        query: { msg: this.msg }
      });
    }
  },

  computed: {
    validarDadosPessoais: function() {
      return (
        this.cliente.nome !== "" &&
        this.cliente.cpf !== "" &&
        this.cliente.genero !== "" &&
        this.cliente.nascimento !== ""
      );
    },

    validarDadosTelefone: function() {
      return (
        this.cliente.tipo !== "" &&
        this.cliente.ddd !== "" &&
        this.cliente.numero !== ""
      );
    },

    validarDadosLogin: function() {
      return this.cliente.email !== "" && this.cliente.senha !== "";
    }
  }
};
</script>
