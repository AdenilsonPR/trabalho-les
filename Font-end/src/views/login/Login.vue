<template>
  <v-container fill-height="fill-height">
    <v-layout align-center="align-center" justify-center="justify-center">
      <v-flex xs12 sm8 md4>
        <div class="headline text-uppercase mb-7">
          <span class="display-1 ml-3">Materiais</span>
          <span class="font-weight-light display-1">Construção</span>
        </div>
        <v-card>
          <v-toolbar dark color="deep-orange accent-4" flat>
            <v-toolbar-title>Entrar</v-toolbar-title>
            <v-spacer></v-spacer>
          </v-toolbar>
          <v-card-text>
            <v-form ref="form" v-model="valid" lazy-validation>
              <v-text-field
                v-model="login.email"
                label="E-mail"
                :rules="regrasEmail"
                type="text"
                maxlength="30"
                id="email"
              ></v-text-field>
              <v-text-field
                v-model="login.senha"
                label="Senha"
                :rules="regrasSenha"
                type="password"
                maxlength="16"
                id="senha"
              ></v-text-field>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              @click="verificaLogin()"
              color="deep-orange accent-4"
              dark
              id="confirmar"
            >Confirmar</v-btn>
          </v-card-actions>
        </v-card>
        <div class="ml-7 mt-4">
          <span>Não possui um cadastro ainda?</span>
          <v-btn text :to="{name: 'newClient'}">Cadastre - se</v-btn>
        </div>
        <div class="ml-12">
          <span>Não consegue entrar?</span>
          <v-btn text :to="{name: 'newPassword'}">Esqueci a senha</v-btn>
        </div>
      </v-flex>
    </v-layout>

    <v-snackbar v-model="snackbar">{{msg}}</v-snackbar>
  </v-container>
</template>

<script>
import axios from "axios";
import { mapMutations } from "vuex";

export default {
  props: {
    source: String
  },

  data: () => ({
    snackbar: false,
    msg: "",
    idCliente: "",
    login: {
      email: "",
      senha: ""
    },
    bancoEmail: "",

    regrasEmail: [v => !!v || "E-mail obrigatório."],

    regrasSenha: [v => !!v || "Senha obrigatória."]
  }),

  methods: {
    ...mapMutations(["getUsuario"]),

    async verificaLogin() {
      let dadosLogin = await axios.get(
        `/ConsultarLogin?OPERACAO=CONSULTAR&id=0&email=${this.login.email}&senha=${this.login.senha}`
      );

      if (
        dadosLogin.data.mensagem == "Invalido" ||
        dadosLogin.data.entidades[0].status == "Inativo"
      ) {
        this.snackbar = true;
        this.msg = "E-mail ou senha incorretos";
      } else {
        let dadosUsuario = await axios.get(
          `/ConsultarUsuario?OPERACAO=CONSULTAR&id=${dadosLogin.data.entidades[0].usuario}`
        );

        this.getUsuario(dadosUsuario.data.entidades[0]);

        if (dadosUsuario.data.entidades[0].papel == "Cliente") {
          this.$router.push({ name: "dashboardTrade" });
        } else if (dadosUsuario.data.entidades[0].papel == "Administrador") {
          this.$router.push({ name: "dashboardAdminClient" });
        }
      }
    }
  }
};
</script>
